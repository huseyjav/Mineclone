package com.mineclone.game.engine.world.Entity;

public class AABB {
    public float xMin;      // left
    public float yMin;      // top
    public float zMin;
    public float xMax;
    public float yMax;
    public float zMax;

    public AABB(float xMin, float yMin, float zMin, float xMax, float yMax, float zMax) {
        this.xMin = xMin;
        this.yMin = yMin;
        this.zMin = zMin;
        this.xMax = xMax;
        this.yMax = yMax;
        this.zMax = zMax;
    }

    public AABB(AABB other) {
        this.xMin = other.xMin;
        this.yMin = other.yMin;
        this.zMin = other.zMin;
        this.xMax = other.xMax;
        this.yMax = other.yMax;
        this.zMax = other.zMax;
    }

    // Check collision with another AABB
//    public boolean intersects(AABB other) {
//        return this.x < other.x + other.width &&
//                this.x + this.width > other.x &&
//                this.y < other.y + other.height &&
//                this.y + this.height > other.y &&
//                this.z < other.z + other.depth &&
//                this.z + this.depth > other.z;
//    }
}
