package com.mineclone;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.nio.IntBuffer;
import java.nio.charset.StandardCharsets;

import static org.lwjgl.opengl.GL20.*;

public class ShaderHelper {
    private int programID;
    public ShaderHelper(String vertexpath, String fragmentpath){
        String vertexshaderstr, fragmentshaderstr;

        try {
            FileInputStream vertexstream = new FileInputStream(vertexpath);
            FileInputStream fragmentstream = new FileInputStream(fragmentpath);
            vertexshaderstr = new String(vertexstream.readAllBytes(), StandardCharsets.UTF_8);
            fragmentshaderstr = new String(fragmentstream.readAllBytes(), StandardCharsets.UTF_8);;
        } catch ( java.io.IOException e) {
            throw new RuntimeException(e);
        }

        int vertexShader = glCreateShader(GL_VERTEX_SHADER);
        int fragmentshader = glCreateShader(GL_FRAGMENT_SHADER);

        glShaderSource(vertexShader, vertexshaderstr);
        glCompileShader(vertexShader);
        //int[] success={0};
        //glGetShaderiv(vertexShader, GL_COMPILE_STATUS, success);
        //System.out.println(success);

        glShaderSource(fragmentshader, fragmentshaderstr);
        glCompileShader(fragmentshader);

        programID = glCreateProgram();

        glAttachShader(programID, vertexShader);
        glAttachShader(programID, fragmentshader);
        glLinkProgram(programID);
        glDeleteShader(vertexShader);
        glDeleteShader(fragmentshader);

    }
    public void use(){
        glUseProgram(programID);
    }

    public int getProgramID(){
        return programID;
    }
}
