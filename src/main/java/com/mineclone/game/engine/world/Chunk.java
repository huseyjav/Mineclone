package com.mineclone.game.engine.world;

import org.joml.Vector2f;
import org.joml.Vector3i;

import java.util.ArrayList;
import java.util.Vector;
import com.mineclone.game.renderer.Mesh;

public class Chunk {
    static final int chunkZSize = 16;
    static final int chunkYSize = 256;
    static final int chunkXSize = 16;
    boolean dirty = true;
    BasicBlock[][][] chunk = new BasicBlock[chunkXSize][chunkYSize][chunkZSize];
    private Mesh lastBuiltMesh;
    public Chunk(){
        for(int x = 0 ; x < 16 ; x++){
            for(int y = 0; y < 256 ; y++){
                for(int z = 0; z < 16 ; z++){
                    //chunk[x][y][z] = BasicBlock.CreateBasicBlock(BasicBlock.BlockType.GRASS);
                    if(y==128 && !(x==8 && z==8)) chunk[x][y][z] = BasicBlock.CreateBasicBlock(BasicBlock.BlockType.TNT);
                    else chunk[x][y][z] = null;
                }
            }
        }
    }
    // loads newly generated chunk
    public Chunk(BasicBlock.BlockType[][][] chunkToLoad){
        for(int x = 0 ; x < 16 ; x++){
            for(int y = 0; y < 256 ; y++){
                for(int z = 0; z < 16 ; z++){
                    //chunk[x][y][z] = BasicBlock.CreateBasicBlock(BasicBlock.BlockType.GRASS);
                    if(chunkToLoad[x][y][z] == BasicBlock.BlockType.END) chunk[x][y][z] = null;
                    else {
                        chunk[x][y][z] = BasicBlock.CreateBasicBlock(chunkToLoad[x][y][z]);
                    }
                }
            }
        }
    }
    // TODO: make new constructor to load an existing chunk, with blocks that have states(furnaces etc)


    // +Y (top)
    static final float[][] FACE_UP_VERTS = {
            {0, 1, 1},
            {1, 1, 1},
            {1, 1, 0},
            {0, 1, 0}
    };

    // -Y (bottom)
    static final float[][] FACE_BOTTOM_VERTS = {
            {0, 0, 0},
            {1, 0, 0},
            {1, 0, 1},
            {0, 0, 1}
    };

    // -X (left)
    static final float[][] FACE_LEFT_VERTS = {
            {0, 0, 0},
            {0, 0, 1},
            {0, 1, 1},
            {0, 1, 0}
    };

    // +X (right)
    static final float[][] FACE_RIGHT_VERTS = {
            {1, 0, 1},
            {1, 0, 0},
            {1, 1, 0},
            {1, 1, 1}
    };

    // +Z (front)
    static final float[][] FACE_FRONT_VERTS = {
            {0, 0, 1},
            {1, 0, 1},
            {1, 1, 1},
            {0, 1, 1}
    };

    // -Z (back)
    static final float[][] FACE_BACK_VERTS = {
            {1, 0, 0},
            {0, 0, 0},
            {0, 1, 0},
            {1, 1, 0}
    };
//    private BlockType getNeighbour(Vector3i origin, Vector3i direction){
//        return getBlock(origin.add(direction));
//    }
    private BasicBlock getBlock(int x, int y, int z){
        if(x < 0 || x>15) return null;
        if(y < 0 || y>255) return null;
        if(z < 0 || z>15) return null;
        return chunk[x][y][z];
    }
    enum Faces{
        UP,
        DOWN,
        LEFT,
        RIGHT,
        FRONT,
        BACK
    }
    private static float[][] uvOffsets = new float[][]{
            {0f,0.031250f},
            {0.031250f,0.031250f},
            {0.031250f, 0f},
            {0f,0f},
    };

    private void addFace(int x, int y, int z, Faces face){
        float[][] faceArray = switch (face){
            case UP -> FACE_UP_VERTS;
            case DOWN -> FACE_BOTTOM_VERTS;
            case LEFT -> FACE_LEFT_VERTS;
            case RIGHT -> FACE_RIGHT_VERTS;
            case FRONT -> FACE_FRONT_VERTS;
            case BACK -> FACE_BACK_VERTS;
        };
        Vector2f textureUV = TextureAtlasManager.getInstance().getUVfromBlockType(chunk[x][y][z].type, face);
        for(int i = 0 ; i < 4 ; i++){
            lastBuiltMesh.vertices.add((float) (faceArray[i][0]+x)); // 0
            lastBuiltMesh.vertices.add((float) (faceArray[i][1]+y));
            lastBuiltMesh.vertices.add((float) (faceArray[i][2]+z));
            lastBuiltMesh.vertices.add(textureUV.x + uvOffsets[i][0]);
            lastBuiltMesh.vertices.add(1 - (textureUV.y + uvOffsets[i][1]));
        }
        int vertexIdx = (lastBuiltMesh.vertices.size() - 20)/5;
        lastBuiltMesh.indices.add(vertexIdx+0);
        lastBuiltMesh.indices.add(vertexIdx+1);
        lastBuiltMesh.indices.add(vertexIdx+2);
        lastBuiltMesh.indices.add(vertexIdx+2);
        lastBuiltMesh.indices.add(vertexIdx+3);
        lastBuiltMesh.indices.add(vertexIdx+0);
    }
    private void buildMesh(){
        lastBuiltMesh = new Mesh();
        for(int x = 0 ; x < 16 ; x++){
            for(int y = 0; y < 256 ; y++){
                for(int z = 0; z < 16 ; z++){
                    if(chunk[x][y][z] == null) {
                        continue;
                    }
                    if(getBlock(x,y-1,z)==null || getBlock(x,y-1,z).transparent)
                        addFace(x,y,z,Faces.DOWN);
                    if(getBlock(x,y+1,z)==null || getBlock(x,y+1,z).transparent)
                        addFace(x,y,z,Faces.UP);
                    if(getBlock(x,y,z-1)==null || getBlock(x,y,z-1).transparent)
                        addFace(x,y,z,Faces.BACK);
                    if(getBlock(x,y,z+1)==null || getBlock(x,y,z+1).transparent)
                        addFace(x,y,z,Faces.FRONT);
                    if(getBlock(x-1,y,z)==null || getBlock(x-1,y,z).transparent)
                        addFace(x,y,z,Faces.LEFT);
                    if(getBlock(x+1,y,z)==null || getBlock(x+1,y,z).transparent)
                        addFace(x,y,z,Faces.RIGHT);
                }
            }
        }
        dirty = false;
    }
    public Mesh getMesh(){
        if(dirty) buildMesh();
        return lastBuiltMesh;
    }
}
