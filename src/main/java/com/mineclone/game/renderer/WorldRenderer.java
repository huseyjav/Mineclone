package com.mineclone.game.renderer;

import com.mineclone.ShaderHelper;
import com.mineclone.game.engine.world.Chunk;
import com.mineclone.game.engine.world.ChunkManager;
import com.mineclone.game.engine.world.World;
import org.joml.Matrix4f;
import org.joml.Vector3f;

import java.util.HashMap;
import java.util.Map;

import static com.mineclone.game.engine.Utils.loadImage;
import static org.joml.Math.toRadians;
import static org.lwjgl.opengl.GL46.*;
import static org.lwjgl.stb.STBImage.stbi_image_free;

public class WorldRenderer {
    World world;
    Camera camera;
    ShaderHelper shaders;

    int textureAtlasID;
    public WorldRenderer(World world,Camera camera) {
        this.world = world;
        this.camera = camera;
        shaders = new ShaderHelper("res/vertexshader.vert", "res/fragshader.frag");
        textureAtlasID = glGenTextures();
        glBindTexture(GL_TEXTURE_2D, textureAtlasID);
        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, GL_NEAREST);
        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, GL_NEAREST);
        int [] width = new int[1], height = new int[1], channels = new int[1];
        var image = loadImage("res/textureatlas.png",width, height, channels);
        glTexImage2D(GL_TEXTURE_2D, 0, GL_RGBA, width[0], height[0], 0, GL_RGBA, GL_UNSIGNED_BYTE, image);
        stbi_image_free(image);
        world.chunkManager.setRenderer(this);
    }
    Map<World.ChunkPos, MeshState> meshMap = new HashMap<>();

    void uploadChunk(World.ChunkPos pos, Chunk chunk){
        if(chunk == null) throw new IllegalArgumentException("chunk can not be null");
        // load VBO

        Mesh currentMesh = chunk.getMesh();
        MeshState lastMeshState = meshMap.get(pos);
        if(lastMeshState == null){
            int VAO = glGenVertexArrays();
            int VBO = glGenBuffers();
            int EBO = glGenBuffers();
            glBindVertexArray(VAO);
            glBindBuffer(GL_ARRAY_BUFFER, VBO);
            glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, EBO);
            glVertexAttribPointer(0, 3, GL_FLOAT, false, 5 * Float.BYTES, 0);
            glEnableVertexAttribArray(0);
            glVertexAttribPointer(1, 2, GL_FLOAT, false, 5 * Float.BYTES, 3 * Float.BYTES);
            glEnableVertexAttribArray(1);
            meshMap.put(pos, new MeshState(VAO, VBO, EBO,null));
            lastMeshState = meshMap.get(pos);
        }
        else if(lastMeshState.lastMesh.equals(currentMesh)) return;
        // TODO: find better data struct than array list to avoid copying
        float[] vertexarray = new float[currentMesh.vertices.size()];
        for (int i = 0; i < currentMesh.vertices.size(); i++) {
            vertexarray[i] = currentMesh.vertices.get(i);
        }
        int[] indices = new int[currentMesh.indices.size()];
        for (int i = 0; i < currentMesh.indices.size(); i++) {
            indices[i] = currentMesh.indices.get(i);
        }
        glBindBuffer(GL_ARRAY_BUFFER, lastMeshState.VBO);
        glBufferData(GL_ARRAY_BUFFER, vertexarray, GL_STATIC_DRAW);
        glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, lastMeshState.EBO);
        glBufferData(GL_ELEMENT_ARRAY_BUFFER, indices, GL_STATIC_DRAW);
        lastMeshState.lastMesh = currentMesh;
    }
    private void cleanUpMeshState(MeshState toCleanup){
        glDeleteBuffers(toCleanup.EBO);
        glDeleteBuffers(toCleanup.VBO);
        glDeleteVertexArrays(toCleanup.VAO);
    }
    public void alertRemoval(World.ChunkPos removedChunk){
        if(!meshMap.containsKey(removedChunk)) return;
        cleanUpMeshState(meshMap.get(removedChunk));
        meshMap.remove(removedChunk);
    }
    public void render(double lerpWeight){
        shaders.use();
        float[] matrixarray = new float[16];


        int transformLoc;

        // view matrix
        Matrix4f viewMatrix = camera.getViewMatrix(lerpWeight);
        viewMatrix.get(matrixarray);
        transformLoc = glGetUniformLocation(shaders.getProgramID(), "view");
        glProgramUniformMatrix4fv(shaders.getProgramID(), transformLoc, false, matrixarray);


        //projection matrix
        Matrix4f projectionMatrix = new Matrix4f();
        projectionMatrix.setPerspective(toRadians(90.0f), (float) 1920 / 1080, 0.1f, 10000.f);
        projectionMatrix.get(matrixarray);
        transformLoc = glGetUniformLocation(shaders.getProgramID(), "projection");
        glProgramUniformMatrix4fv(shaders.getProgramID(), transformLoc, false, matrixarray);



        int success = glGetProgrami(shaders.getProgramID(), GL_LINK_STATUS);
        if (success == GL_FALSE) {
            System.err.println(glGetProgramInfoLog(shaders.getProgramID()));
        }


        for(var entry : world.chunkManager.chunkMap.entrySet()){

            Matrix4f modelMatrix = new Matrix4f();
            modelMatrix.translate(new Vector3f(entry.getKey().x * 16, 0, entry.getKey().z * 16));
            modelMatrix.get(matrixarray);
            transformLoc = glGetUniformLocation(shaders.getProgramID(), "model");
            glProgramUniformMatrix4fv(shaders.getProgramID(), transformLoc, false, matrixarray);


            uploadChunk(entry.getKey(), entry.getValue());
            glBindVertexArray(meshMap.get(entry.getKey()).VAO);

            glDrawElements(GL_TRIANGLES, entry.getValue().getMesh().indices.size(), GL_UNSIGNED_INT, 0);
        }
    }
}
