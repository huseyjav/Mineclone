package com.mineclone.game;

import com.mineclone.game.engine.world.Entity.Player;
import com.mineclone.game.engine.world.World;
import com.mineclone.game.renderer.Camera;
import com.mineclone.game.renderer.WorldRenderer;
import org.lwjgl.opengl.GL;

import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL43.*;


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
