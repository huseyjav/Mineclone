package com.mineclone.game.renderer;

import com.mineclone.game.engine.Player;
import org.joml.*;

import static org.joml.Math.*;
public class Camera {
    Player followedPlayer;

    public Camera(Player followedPlayer){
        this.followedPlayer = followedPlayer;
    }

    public Matrix4f getViewMatrix(double lerpWeight){
        Matrix4f viewMatrix = new Matrix4f();
        var lerpedPos = new Vector3f();
        followedPlayer.getLastPosition().lerp(followedPlayer.getCurrentPosition(), (float)lerpWeight,lerpedPos);
        viewMatrix.lookAt(lerpedPos, followedPlayer.getDirection().add(lerpedPos), new Vector3f(0.0f, 1.0f, 0.0f));
        return viewMatrix;
    }
}
