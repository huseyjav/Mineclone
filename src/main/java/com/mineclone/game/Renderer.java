package com.mineclone.game;

import com.mineclone.ShaderHelper;
import com.mineclone.game.engine.Player;
import com.mineclone.game.engine.world.Chunk;
import com.mineclone.game.engine.world.World;
import com.mineclone.game.renderer.Camera;
import com.mineclone.game.renderer.WorldRenderer;
import org.joml.Matrix4f;
import org.joml.Vector3f;
import org.lwjgl.opengl.GL;
import org.lwjgl.opengl.GLUtil;
import org.lwjgl.stb.STBImage;
import org.lwjgl.system.MemoryStack;

import java.nio.ByteBuffer;
import java.nio.IntBuffer;
import java.util.HashMap;
import java.util.Map;

import static org.joml.Math.*;
import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL43.*;
import static org.lwjgl.stb.STBImage.*;
import static org.lwjgl.system.MemoryStack.stackPush;


public class Renderer {

    private long window;
    private Camera camera;
    private WorldRenderer worldRenderer;
    int textureAtlasID;

    private World world;

    public Renderer(long window, Player followedPlayer, World world) {
        GL.createCapabilities();
        glEnable(GL_CULL_FACE);
        //GLUtil.setupDebugMessageCallback();

        camera = new Camera(followedPlayer);
        this.window = window;
        this.world = world;
        this.worldRenderer = new WorldRenderer(world, camera);
        glEnable(GL_DEPTH_TEST);
        //glPolygonMode(GL_FRONT_AND_BACK, GL_LINE);
    }


    void render(double lerpWeight) {

        //System.out.println(lerpWeight);
        glClearColor(96f/255, 147f/255, 230f/255, 1f);
        glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);

        worldRenderer.render(lerpWeight);

        glfwSwapBuffers(window);
    }
}
