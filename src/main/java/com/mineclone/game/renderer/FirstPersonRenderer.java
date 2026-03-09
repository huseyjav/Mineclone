package com.mineclone.game.renderer;

import com.mineclone.ShaderHelper;
import com.mineclone.game.engine.world.Entity.Player;
import org.joml.Matrix4f;

import static org.joml.Math.toRadians;
import static org.lwjgl.opengl.GL46.*;
public class FirstPersonRenderer {
    private Player player;
    private ShaderHelper shaders;
    private MeshState meshState;
    private int VAO;
    private int VBO;
    private int EBO;
    public FirstPersonRenderer(Player player){
        this.player = player;
        SetupShaders();
        SetupMesh();
    }

    private void SetupShaders(){
        shaders = new ShaderHelper("res/firstperson.vert", "res/firstperson.frag");
    }

    private void SetupMesh(){
        VAO = glGenVertexArrays();
        VBO = glGenBuffers();
        EBO = glGenBuffers();
        glBindVertexArray(VAO);
        glBindBuffer(GL_ARRAY_BUFFER, VBO);
        glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, EBO);
        glVertexAttribPointer(0, 3, GL_FLOAT, false, 6 * Float.BYTES, 0);
        glEnableVertexAttribArray(0);
        glVertexAttribPointer(1, 3, GL_FLOAT, false, 6 * Float.BYTES, 3 * Float.BYTES);
        glEnableVertexAttribArray(1);


        glBufferData(GL_ARRAY_BUFFER, VERTICES, GL_STATIC_DRAW);
        glBufferData(GL_ELEMENT_ARRAY_BUFFER, INDICES, GL_STATIC_DRAW);

    }
    public void render(){

        float[] matrixarray = new float[16];
        shaders.use();
        Matrix4f transform = new Matrix4f();
        transform

//                .rotateY((float)Math.toRadians(-20))
                //.rotateZ((float)Math.toRadians(-10))
                .translate(1f, -1f, -1f)
                .rotateX((float)Math.toRadians(-45))
                .rotateY((float)Math.toRadians(20))
                .rotateZ((float)Math.toRadians(-20))
                .scale(0.25f,1f,0.25f);
        transform.get(matrixarray);
        int transformLoc = glGetUniformLocation(shaders.getProgramID(), "model");
        glProgramUniformMatrix4fv(shaders.getProgramID(), transformLoc, false, matrixarray);

        Matrix4f projectionMatrix = new Matrix4f();
        projectionMatrix.setPerspective(toRadians(70.0f), (float) 1920 / 1080, 0.1f, 10000.f);
        projectionMatrix.get(matrixarray);
        transformLoc = glGetUniformLocation(shaders.getProgramID(), "projection");
        glProgramUniformMatrix4fv(shaders.getProgramID(), transformLoc, false, matrixarray);

        glBindVertexArray(VAO);
//        glDisable(GL_CULL_FACE);
        glClear(GL_DEPTH_BUFFER_BIT);
//        glDisable(GL_DEPTH_TEST);
        glFrontFace(GL_CW);
        glDrawElements(GL_TRIANGLES, INDICES.length, GL_UNSIGNED_INT, 0);
        glEnable(GL_CULL_FACE);
        glFrontFace(GL_CCW);
        glEnable(GL_DEPTH_TEST);

    }

    // Triangle indices
//    public static final float[] VERTICES = {
//            -0.5f, -0.5f, -0.5f, // 0
//            0.5f, -0.5f, -0.5f, // 1
//            0.5f,  0.5f, -0.5f, // 2
//            -0.5f,  0.5f, -0.5f, // 3
//            -0.5f, -0.5f,  0.5f, // 4
//            0.5f, -0.5f,  0.5f, // 5
//            0.5f,  0.5f,  0.5f, // 6
//            -0.5f,  0.5f,  0.5f  // 7
//    };
//
//    // Triangle indices (12 triangles = 6 faces)
//    public static final int[] INDICES = {
//            // Back face
//            0, 1, 2,
//            2, 3, 0,
//
//            // Front face
//            4, 5, 6,
//            6, 7, 4,
//
//            // Left face
//            0, 3, 7,
//            7, 4, 0,
//
//            // Right face
//            1, 5, 6,
//            6, 2, 1,
//
//            // Bottom face
//            0, 1, 5,
//            5, 4, 0,
//
//            // Top face
//            3, 2, 6,
//            6, 7, 3
//    };
    public static final float[] VERTICES = {
            // front
            -0.5f, -0.5f, -0.5f, 1.0f, 0.0f, 0.0f, // 0
            0.5f, -0.5f, -0.5f, 1.0f, 0.0f, 0.0f,  // 1
            0.5f, 0.5f, -0.5f, 1.0f, 0.0f, 0.0f,   // 2
            -0.5f, 0.5f, -0.5f, 1.0f, 0.0f, 0.0f,  // 3

            // top
            -0.5f, 0.5f, -0.5f, 0.0f, 0.0f, 1.0f, // 4
            0.5f, 0.5f, -0.5f, 0.0f, 0.0f, 1.0f,  // 5
            0.5f, 0.5f, 0.5f, 0.0f, 0.0f, 1.0f,   // 6
            -0.5f, 0.5f, 0.5f, 0.0f, 0.0f, 1.0f,  // 7

            // left
            -0.5f, -0.5f, -0.5f, 0.0f, 1.0f, 0.0f, // 8
            -0.5f, 0.5f, -0.5f, 0.0f, 1.0f, 0.0f,  // 9
            -0.5f, 0.5f, 0.5f, 0.0f, 1.0f, 0.0f,   // 10
            -0.5f, -0.5f, 0.5f, 0.0f, 1.0f, 0.0f,  // 11

            // right
            0.5f, -0.5f, -0.5f, 0.5f, 0.5f, 0.5f, // 12
            0.5f, 0.5f, -0.5f, 0.5f, 0.5f, 0.5f,  // 13
            0.5f, 0.5f, 0.5f, 0.5f, 0.5f, 0.5f,   // 14
            0.5f, -0.5f, 0.5f, 0.5f, 0.5f, 0.5f,  // 15

            // back
            -0.5f, -0.5f, 0.5f, 0.7f, 0.7f, 0.7f, // 16
            0.5f, -0.5f, 0.5f, 0.7f, 0.7f, 0.7f,  // 17
            0.5f, 0.5f, 0.5f, 0.7f, 0.7f, 0.7f,   // 18
            -0.5f, 0.5f, 0.5f, 0.7f, 0.7f, 0.7f,  // 19

            // bottom
            -0.5f, -0.5f, 0.5f, 0.3f, 0.3f, 0.3f, // 20
            0.5f, -0.5f, 0.5f, 0.3f, 0.3f, 0.3f,  // 21
            0.5f, -0.5f, -0.5f, 0.3f, 0.3f, 0.3f, // 22
            -0.5f, -0.5f, -0.5f, 0.3f, 0.3f, 0.3f // 23
    };

    // Triangle indices (12 triangles = 6 faces)
    public static final int[] INDICES = {
            // front
            0, 1, 2, // first triangle
            2, 3, 0, // second triangle

            // top
            4, 5, 6, // first triangle
            6, 7, 4, // second triangle

            // left
            8, 9, 10,  // first triangle
            10, 11, 8, // second triangle

            // right
            14, 13, 12, // 12, 13, 14, // first triangle
            12, 15, 14, // 14, 15, 12, // second triangle

            // back
            18, 17, 16, // 16, 17, 18, // first triangle
            16, 19, 18, // 18, 19, 16, // second triangle

            // bottom
            20, 21, 22, // first triangle
            22, 23, 20  // second triangle
    };

}
