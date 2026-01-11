package com.mineclone.game.engine.world;

import org.joml.Vector2f;
import com.mineclone.game.engine.world.BasicBlock.BlockType;
import com.mineclone.game.engine.world.Chunk.Faces;
public class TextureAtlasManager {
    private static TextureAtlasManager instance = null;
    private static int atlasSize = 512;
    private static int tileSize = 16;
    public static TextureAtlasManager getInstance(){
        if(instance == null) instance = new TextureAtlasManager();
        return instance;
    }
    Vector2f[][] uvArray;
    Vector2f getUVFromID(int id){

        int texturesPerRow = atlasSize / tileSize; // 32

        int x = id % texturesPerRow;      // column
        int y = id / texturesPerRow;      // row

        float u = (float)(x * tileSize) / atlasSize;
        float v = (float)(y * tileSize) / atlasSize;

        return new Vector2f(u, v);
    }
    TextureAtlasManager(){
        uvArray = new Vector2f[BlockType.END.ordinal()][6];
        uvArray[BlockType.GRASS.ordinal()][Faces.DOWN.ordinal()] = getUVFromID(169);
        uvArray[BlockType.GRASS.ordinal()][Faces.UP.ordinal()] = getUVFromID(120);
        uvArray[BlockType.GRASS.ordinal()][Faces.LEFT.ordinal()] = getUVFromID(359);
        uvArray[BlockType.GRASS.ordinal()][Faces.RIGHT.ordinal()] = getUVFromID(359);
        uvArray[BlockType.GRASS.ordinal()][Faces.BACK.ordinal()] = getUVFromID(359);
        uvArray[BlockType.GRASS.ordinal()][Faces.FRONT.ordinal()] = getUVFromID(359);

        uvArray[BlockType.TNT.ordinal()][Faces.DOWN.ordinal()] = getUVFromID(310);
        uvArray[BlockType.TNT.ordinal()][Faces.UP.ordinal()] = getUVFromID(374);
        uvArray[BlockType.TNT.ordinal()][Faces.LEFT.ordinal()] = getUVFromID(342);
        uvArray[BlockType.TNT.ordinal()][Faces.RIGHT.ordinal()] = getUVFromID(342);
        uvArray[BlockType.TNT.ordinal()][Faces.BACK.ordinal()] = getUVFromID(342);
        uvArray[BlockType.TNT.ordinal()][Faces.FRONT.ordinal()] = getUVFromID(342);


        uvArray[BlockType.DIRT.ordinal()][Faces.DOWN.ordinal()] = getUVFromID(169);
        uvArray[BlockType.DIRT.ordinal()][Faces.UP.ordinal()] = getUVFromID(169);
        uvArray[BlockType.DIRT.ordinal()][Faces.LEFT.ordinal()] = getUVFromID(169);
        uvArray[BlockType.DIRT.ordinal()][Faces.RIGHT.ordinal()] = getUVFromID(169);
        uvArray[BlockType.DIRT.ordinal()][Faces.BACK.ordinal()] = getUVFromID(169);
        uvArray[BlockType.DIRT.ordinal()][Faces.FRONT.ordinal()] = getUVFromID(169);

    }
    Vector2f getUVfromBlockType(BlockType type, Faces face){
        return uvArray[type.ordinal()][face.ordinal()];
    }

}
