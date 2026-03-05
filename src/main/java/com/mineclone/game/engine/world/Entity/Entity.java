package com.mineclone.game.engine.world.Entity;

import org.joml.Vector3d;
import org.joml.Vector2d;
import org.joml.Vector3f;

public abstract class Entity {
    public Vector3f currentPosition;
    public Vector3f lastPosition;
    // velocity is relative to the player direction, not world coordinates
    public Vector3d velocity = new Vector3d();
    public float yaw = 0f, pitch = 0f;
    public Vector3d moveIntent = new Vector3d();
    public boolean wantsJump = false;
    public double maxSpeed=0.4d;
    public double acceleration=0.2d;
    public double deceleration=0.2d;
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
