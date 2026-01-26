package com.mineclone.game;

import com.mineclone.game.engine.world.Entity.InputComponent;
import com.mineclone.game.engine.world.Entity.Player;
import com.mineclone.game.engine.world.World;
import org.joml.Vector2i;
import org.joml.Vector3f;
import org.lwjgl.glfw.*;
import org.lwjgl.system.MemoryStack;

import java.nio.IntBuffer;

import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.glfw.GLFW.glfwShowWindow;
import static org.lwjgl.system.MemoryStack.stackPush;
import static org.lwjgl.system.MemoryUtil.NULL;

public class Game{
    static public long window=0;
    private Renderer renderer;
    private Player localPlayer = new Player(new Vector3f(100,80,100), new InputComponent());
    public double timePerTick = 1f/20f;
    public double lastTick = 0f;
    public double previousTick = 0f;
    public double timePassed = 0f;
    World world = new World(localPlayer);



    class processCursorPos implements GLFWCursorPosCallbackI{
        double lastX = getWindowSize().x/2, lastY = getWindowSize().y/2;
        @Override
        public void invoke(long window, double xpos, double ypos) {
            double xOffset = xpos - lastX;
            double yOffset = ypos - lastY;
            yOffset*=-1;
            lastX = xpos;
            lastY = ypos;
            localPlayer.handleMouse(xOffset, yOffset);
        }
    }

    private Vector2i getWindowSize(){
        //Vector2i windowSize = new Vector2i();
        int[] x = new int[1], y = new int[1];
        glfwGetWindowSize(window,x,y);
        return new Vector2i(x[0], y[0]);
    }
    private void initWindow(){
        // Setup an error callback. The default implementation
        // will print the error message in System.err.
        GLFWErrorCallback.createPrint(System.err).set();

        // Initialize GLFW. Most GLFW functions will not work before doing this.
        if ( !glfwInit() )
            throw new IllegalStateException("Unable to initialize GLFW");

        // Configure GLFW
        glfwDefaultWindowHints(); // optional, the current window hints are already the default
        glfwWindowHint(GLFW_VISIBLE, GLFW_FALSE); // the window will stay hidden after creation
        glfwWindowHint(GLFW_RESIZABLE, GLFW_TRUE); // the window will be resizable
        glfwWindowHint(GLFW_OPENGL_DEBUG_CONTEXT, GLFW_TRUE);
        // Create the window
        window = glfwCreateWindow(1920, 1080, "Hello World!", NULL, NULL);
        if ( window == NULL )
            throw new RuntimeException("Failed to create the GLFW window");


        //GLFWKeyCallback.create(this);
        // Get the thread stack and push a new frame
        try ( MemoryStack stack = stackPush() ) {
            IntBuffer pWidth = stack.mallocInt(1); // int*
            IntBuffer pHeight = stack.mallocInt(1); // int*

            // Get the window size passed to glfwCreateWindow
            glfwGetWindowSize(window, pWidth, pHeight);

            // Get the resolution of the primary monitor
            GLFWVidMode vidmode = glfwGetVideoMode(glfwGetPrimaryMonitor());

            // Center the window
            glfwSetWindowPos(
                    window,
                    2500 +(vidmode.width() - pWidth.get(0)) / 2,
                    (vidmode.height() - pHeight.get(0)) / 2
            );
        } // the stack frame is popped automatically

        // Make the OpenGL context current
        glfwMakeContextCurrent(window);
        //  v-sync
        glfwSwapInterval(0);

        GLFWCursorPosCallback.create(new processCursorPos()).set(window);
        glfwSetInputMode(window, GLFW_CURSOR, GLFW_CURSOR_DISABLED);
        InputController.getInstance().setCallback(window);
        // Make the window visible
        glfwShowWindow(window);
    }
    public void init(){
        initWindow();
        renderer = new Renderer(window, localPlayer, world);

    }
    public void run(){
        int counter=0;
        double lasttime = glfwGetTime();

        while(!glfwWindowShouldClose(window)){
            glfwPollEvents();
            boolean alreadydone = false;
            while(timePassed >= timePerTick){
                if(alreadydone) System.out.println("skipped a tick");
                world.tick();
                previousTick = lastTick;
                lastTick = glfwGetTime();
                timePassed -= timePerTick;
                alreadydone=true;
            }
            counter++;
            renderer.render((glfwGetTime()-lastTick)/timePerTick);
            if(glfwGetTime()-lasttime>=1){
                System.out.println(counter);
                counter=0;
                lasttime=glfwGetTime();
            }
            timePassed = glfwGetTime() - lastTick;
        }
    }


}
