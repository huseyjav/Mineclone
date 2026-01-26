package com.mineclone.game.engine.world;

import com.mineclone.game.renderer.WorldRenderer;

import java.util.*;

public class ChunkManager {
    class ChunkEntry implements Comparable{
        World.ChunkPos pos;
        Chunk chunk;
        ChunkEntry(World.ChunkPos pos, Chunk chunk){
            this.chunk = chunk;
            this.pos = pos;
        }

        @Override
        public int compareTo(Object o) {
            assert o instanceof ChunkEntry;
            return pos.compareTo(((ChunkEntry)o).pos);
        }
    }
    class MapGenThread extends Thread {
        Queue<World.ChunkPos> chunkQueu;
        Queue<ChunkEntry> readyChunks;
        Object mutex;
        public MapGenThread(Queue<World.ChunkPos> chunkQueu, Queue<ChunkEntry> readyChunks, Object mutex){
            this.chunkQueu = chunkQueu;
            this.readyChunks = readyChunks;
            this.mutex = mutex;
        }
        @Override
        public void run() {
            while(true){
                World.ChunkPos chunkToGen = null;
                synchronized (mutex){
                    chunkToGen = chunkQueu.poll();
                }
                if(chunkToGen==null) continue;
                Chunk toPush = new Chunk(WorldGen.genChunk(3985, chunkToGen.x, chunkToGen.z));
                synchronized (mutex){
                    readyChunks.add(new ChunkEntry(chunkToGen, toPush));
                }
            }
        }
    }
    int loadDistance=8;
    public Map<World.ChunkPos, Chunk> chunkMap = new HashMap<>();

    // add the queued chunks for generation, so they aren't readded while being processed in worker thread
    private ArrayList<World.ChunkPos> inProduction = new ArrayList<>();
    private WorldRenderer rendererToAlert;
    Queue<World.ChunkPos> chunkQueue = new PriorityQueue<World.ChunkPos>();
    Queue<ChunkEntry> readyChunks = new PriorityQueue<ChunkEntry>();
    Object mutex = new Object();
    Thread workerThread = new MapGenThread(chunkQueue, readyChunks, mutex);
    public void setRenderer(WorldRenderer rendererToAlert){
        this.rendererToAlert = rendererToAlert;
    }

    public ChunkManager(int chunkX, int chunkZ){
        World.ChunkPos pos = new World.ChunkPos(chunkX,chunkZ);
        chunkMap.put(pos, new Chunk(WorldGen.genChunk(3985, chunkX, chunkZ)));
        workerThread.setDaemon(true);
        workerThread.start();

    }
    public boolean isSolidBlock(int x, int y, int z){
        World.ChunkPos chunkPos = new World.ChunkPos(x/Chunk.chunkXSize, z/Chunk.chunkZSize);
        if(!chunkMap.containsKey(chunkPos)) return true;
        return chunkMap.get(chunkPos).chunk[x%Chunk.chunkXSize][y][z%Chunk.chunkZSize]!=null;
    }
    void unloadOutOfBoundsChunks(int chunkX, int chunkZ){
        List<World.ChunkPos> toRemove = new ArrayList<World.ChunkPos>();
        for(var i : chunkMap.entrySet()){
            if(i.getKey().x - chunkX > loadDistance || i.getKey().x - chunkX < -loadDistance ||
                    i.getKey().z - chunkZ > loadDistance || i.getKey().z - chunkZ < -loadDistance){
                // TODO implement save
                rendererToAlert.alertRemoval(i.getKey());
                toRemove.add(i.getKey());
            }
        }
        for(var i : toRemove){
            chunkMap.remove(i);
        }
    }

    void queueChunks(int chunkX, int chunkZ){
        for(int x = -loadDistance ; x <= loadDistance; x++){
            if(x+chunkX < 0) continue;
            for(int z = -loadDistance ; z <= loadDistance; z++){
                if(z+chunkZ < 0) continue;

                World.ChunkPos pos = new World.ChunkPos(x+chunkX,z+chunkZ);
                if(chunkMap.containsKey(pos) || inProduction.contains(pos)) continue;

                synchronized (mutex){
                    chunkQueue.add(pos);
                    inProduction.add(pos);
                }
            }
        }
    }

    void dequeueChunks(){
        synchronized (mutex){
            while(readyChunks.peek() != null){
                ChunkEntry entry = readyChunks.remove();
                chunkMap.put(entry.pos, entry.chunk);
                inProduction.remove(entry.pos);
            }
        }
    }

    //Arguments represent coordinates of center
    void tick(int chunkX, int chunkZ){
        unloadOutOfBoundsChunks(chunkX,chunkZ);
        queueChunks(chunkX, chunkZ);
        dequeueChunks();
    }
}
