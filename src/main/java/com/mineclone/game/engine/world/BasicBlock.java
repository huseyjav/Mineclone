package com.mineclone.game.engine.world;

import org.joml.Vector2f;

public class BasicBlock {
    public enum BlockType{
        GRASS,
        TNT,
        DIRT,
        END
    }

    BlockType type;
    boolean transparent;

    public BasicBlock(BlockType type, boolean transparent){
        this.type = type;
        this.transparent = transparent;
    }

    static BasicBlock CreateBasicBlock(BlockType type){
        return
        switch (type){
            case GRASS -> new BasicBlock(BlockType.GRASS, false);
            case TNT -> new BasicBlock(BlockType.TNT, false);
            case DIRT -> new BasicBlock(BlockType.DIRT, false);
            case null, default -> throw new IllegalArgumentException("illegal block type");
        };
    }
}
