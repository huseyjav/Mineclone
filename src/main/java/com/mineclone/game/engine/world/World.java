package com.mineclone.game.engine.world;

import com.mineclone.game.engine.Player;
import org.joml.Vector3i;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import static com.mineclone.game.engine.world.WorldGen.genChunk;

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
    public ChunkManager chunkManager = new ChunkManager();
    public World(Player localPlayer){
        this.localPlayer = localPlayer;
    }
    public void tick(){
        chunkManager.tick((int)localPlayer.getCurrentPosition().x/16, (int)localPlayer.getCurrentPosition().z/16);
    }


}
