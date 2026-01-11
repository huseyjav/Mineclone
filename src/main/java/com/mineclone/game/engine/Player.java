package com.mineclone.game.engine;

import org.joml.Vector2d;
import org.joml.Vector2f;
import org.joml.Vector3d;
import org.joml.Vector3f;

import static org.joml.Math.*;

public class Player {

    private Vector3f currentPosition;
    private Vector3f lastPosition;
    //private Vector3f direction;
    private float yaw = 0f, pitch = 0f;
    private Vector3f up = new Vector3f(0.0f,1.0f,0.0f);
    public Player(Vector3f position){
        this.currentPosition = new Vector3f(position);
        this.lastPosition = new Vector3f(position);
    }
    public Vector3f getDirection(){

        Vector3f toReturn = new Vector3f(
                (float)cos(toRadians(yaw)) * (float)cos(toRadians(pitch)),
                (float)sin(toRadians(pitch)),
                (float)sin(toRadians(yaw)) * (float)cos(toRadians(pitch))
        );
        toReturn.normalize(toReturn);
        return toReturn;
    }
    public Vector3f getCurrentPosition(){
        return currentPosition;
    }
    public Vector3f getLastPosition(){
        return lastPosition;
    }

    float cameraSpeed = 1f;
    boolean movingForward= false, movingBackwards = false, movingLeft = false, movingRight = false;
    public void handleForward(){
        movingForward = true;
        //position.add(getDirection().mul(cameraSpeed), position);
    }
    public void handleBackward(){
        movingBackwards = true;
        //position.sub(getDirection().mul(cameraSpeed),position);
    }
    public void handleLeft(){
        movingLeft = true;
        //position.sub(getDirection().cross(up).normalize().mul(cameraSpeed), position);
    }
    public void handleRight(){
        movingRight = true;
        //position.add(getDirection().cross(up).normalize().mul(cameraSpeed), position);
    }
    double sensitivity = 0.1f;

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
        if(movingRight) currentPosition.add(getDirection().cross(up).normalize().mul(cameraSpeed), currentPosition);
        if(movingLeft) currentPosition.sub(getDirection().cross(up).normalize().mul(cameraSpeed), currentPosition);
        if(movingForward) currentPosition.add(getDirection().mul(cameraSpeed), currentPosition);
        if(movingBackwards) currentPosition.sub(getDirection().mul(cameraSpeed),currentPosition);
        movingBackwards = false;
        movingForward = false;
        movingRight = false;
        movingLeft = false;

    }
}
