package com.mineclone.game.engine.world;
import com.mineclone.OpenSimplex2.*;
public class WorldGen {
    static BasicBlock.BlockType[][][] genChunk(int seed, int chunkX, int chunkZ){
        var generatedChunk = new BasicBlock.BlockType[Chunk.chunkXSize][Chunk.chunkYSize][Chunk.chunkZSize];
        double scale = 0.01d;
        for(int x = 0 ; x < Chunk.chunkXSize ; x++){
            for(int z = 0 ; z < Chunk.chunkZSize ; z++){
                int worldX = chunkX * Chunk.chunkXSize + x;
                int worldZ = chunkZ * Chunk.chunkZSize + z;
                int height = (int)(OpenSimplex2.noise2(seed, worldX * scale, worldZ * scale)*20+64);
                for(int y = 0 ; y < Chunk.chunkYSize ; y++){
                    if(y > height) generatedChunk[x][y][z] = BasicBlock.BlockType.END;
                    else if(y==height) generatedChunk[x][y][z] = BasicBlock.BlockType.GRASS;
                    else generatedChunk[x][y][z] = BasicBlock.BlockType.DIRT;
                }
            }
        }
        return generatedChunk;
    }
}
