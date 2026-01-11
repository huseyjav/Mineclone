package com.mineclone;

import com.mineclone.game.Game;
import org.lwjgl.glfw.*;

import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.glfw.GLFW.glfwSetWindowShouldClose;
import static org.lwjgl.opengl.GL46.*;

public class Main {

    public static void main(String[] args){
        Game newgame = new Game();
        newgame.init();
        newgame.run();
    }

}
