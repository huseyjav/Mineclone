package com.mineclone.game.renderer;

import com.mineclone.game.engine.world.Chunk;

import java.util.ArrayList;

class MeshState{
    int VAO;
    int VBO;
    int EBO;
    Mesh lastMesh;
    public MeshState(int VAO, int VBO, int EBO, Mesh mesh){
        this.VAO = VAO;
        this.VBO = VBO;
        this.EBO = EBO;
        lastMesh = mesh;
    }
}
public class Mesh{
    public ArrayList<Float> vertices = new ArrayList<Float>();
    public ArrayList<Integer> indices = new ArrayList<Integer>();
}