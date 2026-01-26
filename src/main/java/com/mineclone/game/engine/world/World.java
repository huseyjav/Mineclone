package com.mineclone.game.engine.world;

import com.mineclone.game.engine.world.Entity.Player;
import com.mineclone.game.engine.physics.Physics;

import java.util.Objects;

public class World {
    public static class ChunkPos implements Comparable{
        public int x;
        public int z;
        ChunkPos(int x, int z){
            this.x = x;
            this.z = z;
        }
        @Override
        public boolean equals(Object obj) {
            if(this == obj) return true;
            if(!(obj instanceof ChunkPos comparee)) return false;
            return comparee.x == this.x && comparee.z == this.z;
        }
        @Override
        public int hashCode() {
            return Objects.hash(x, z);
        }
        @Override
        public int compareTo(Object o) {
            assert o instanceof ChunkPos;
            ChunkPos other = (ChunkPos) o;
            if(this.x < other.x) return -1;
            if(this.z < other.z) return -1;
            if(this.x == other.x && this.z == other.z) return  0;
            return 1;
        }
    }
    Player localPlayer;
    public ChunkManager chunkManager;
    private Physics physics = new Physics(this);
    public World(Player localPlayer){
        this.localPlayer = localPlayer;
        chunkManager = new ChunkManager((int) (localPlayer.currentPosition.x/Chunk.chunkXSize), (int) (localPlayer.currentPosition.z/Chunk.chunkZSize));
    }
    public void tick(){

        chunkManager.tick((int)localPlayer.getCurrentPosition().x/16, (int)localPlayer.getCurrentPosition().z/16);
        localPlayer.tick();
        physics.tick(localPlayer);
    }

    public boolean isSolidBlock(int x, int y, int z){
        return chunkManager.isSolidBlock(x,y,z);
    }


}
