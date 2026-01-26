package com.mineclone.game;

import org.lwjgl.glfw.GLFWKeyCallback;
import org.lwjgl.glfw.GLFWKeyCallbackI;

import static org.lwjgl.glfw.GLFW.*;

public class InputController {
    private static InputController instance = null;
    private boolean[] keyFlags = new boolean[GLFW_KEY_LAST + 1];
    class processInput implements GLFWKeyCallbackI {
        @Override
        public void invoke(long window, int key, int scancode, int action, int mods) {
            if (glfwGetKey(window, GLFW_KEY_ESCAPE) == GLFW_PRESS)
                glfwSetWindowShouldClose(window, true);
            if(key < 0 || key > GLFW_KEY_LAST) throw new IndexOutOfBoundsException("key code out of bounds");
            if(action == GLFW_PRESS)
                keyFlags[key] = true;

            else if(action == GLFW_RELEASE)
                keyFlags[key] = false;

        }
    }
    public void setCallback(long window){
        GLFWKeyCallback.create(new processInput()).set(window);
    }
    public static InputController getInstance(){
        if(instance == null) instance = new InputController();
        return instance;
    }
    public boolean getKeyState(int keyCode){
        return keyFlags[keyCode];
    }
}
