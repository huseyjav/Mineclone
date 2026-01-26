package com.mineclone.game.engine.world.Entity;

import org.joml.Vector3f;

public abstract class Entity {
    public Vector3f currentPosition;
    public Vector3f lastPosition;
    public Vector3f velocity = new Vector3f();
    public Vector3f direction = new Vector3f();
    public Vector3f moveIntent = new Vector3f();
    public boolean wantsJump = false;
    public float moveSpeed=0.2f;
    private AABB hitbox;
    public boolean hasGravity;
    public boolean isGrounded;
    public boolean hitWall;
    public Entity(AABB hitbox){
        this.hitbox = new AABB(hitbox);
    }
    public float getXMin(){
        return currentPosition.x + hitbox.xMin;
    }
    public float getXMax(){
        return currentPosition.x + hitbox.xMax;
    }
    public float getYMin(){
        return currentPosition.y + hitbox.yMin;
    }
    public float getYMax(){
        return currentPosition.y + hitbox.yMax;
    }
    public float getZMin(){
        return currentPosition.z + hitbox.zMin;
    }
    public float getZMax(){
        return currentPosition.z + hitbox.zMax;
    }
}
