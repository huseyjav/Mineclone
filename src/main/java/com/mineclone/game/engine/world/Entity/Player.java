package com.mineclone.game.engine.world.Entity;

import com.mineclone.game.engine.world.Entity.AABB;
import com.mineclone.game.engine.world.Entity.Entity;
import com.mineclone.game.engine.world.Entity.LiveEntity;
import org.joml.Vector2d;
import org.joml.Vector2f;
import org.joml.Vector3d;
import org.joml.Vector3f;

import static org.joml.Math.*;

public class Player extends Entity {

    private Vector3f up = new Vector3f(0.0f,1.0f,0.0f);
    private Vector3f eyePosOffset = new Vector3f(0.0f, 1.5f, 0.0f);
    double sensitivity = 0.1f;
    InputComponent inputComponent;
    public Player(Vector3f position, InputComponent inputComponent){
        super(new AABB(-0.3f, 0f, -0.3f, 0.3f,1.8f, 0.3f));
        this.currentPosition = new Vector3f(position);
        this.lastPosition = new Vector3f(position);
        this.inputComponent = inputComponent;
    }
    public Vector3f getCurrentEyePos(){
        Vector3f toreturn = new Vector3f(eyePosOffset);
        return toreturn.add(currentPosition);
    }
    public Vector3f getLastEyePos(){
        Vector3f toreturn = new Vector3f(eyePosOffset);
        return toreturn.add(lastPosition);
    }
    public Vector3f getDirectionVector(){
        Vector3f toReturn = new Vector3f(
                (float)cos(toRadians(yaw)) * (float)cos(toRadians(pitch)),
                (float)sin(toRadians(pitch)),
                (float)sin(toRadians(yaw)) * (float)cos(toRadians(pitch))
        );
        toReturn.normalize(toReturn);
        return toReturn;
    }
    public Vector3f getDirectionVectorXZ(){
        Vector3f toReturn = new Vector3f(
                (float)cos(toRadians(yaw)) * (float)cos(toRadians(pitch)),
                0.0f,
                (float)sin(toRadians(yaw)) * (float)cos(toRadians(pitch))
        );
        toReturn.normalize(toReturn);
        return toReturn;
    }
    public Vector3f getRightVector(){
        return getDirectionVector().cross(up).normalize();
    }
    public Vector3f getCurrentPosition(){
        return currentPosition;
    }
    public Vector3f getLastPosition(){
        return lastPosition;
    }



    public void handleMouse(double xOffset, double yOffset){
        xOffset *= sensitivity;
        yOffset *= sensitivity;
        yaw += xOffset;
        pitch += yOffset;

        if(pitch > 89.0f)
            pitch =  89.0f;
        if(pitch < -89.0f)
            pitch = -89.0f;
    }
    public void tick(){
        lastPosition = currentPosition;
        currentPosition = new Vector3f(currentPosition);
        inputComponent.update(this);
    }
}
