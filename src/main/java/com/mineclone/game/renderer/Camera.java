package com.mineclone.game.renderer;

import com.mineclone.game.engine.world.Entity.Player;
import org.joml.*;

public class Camera {
    Player followedPlayer;

    public Camera(Player followedPlayer){
        this.followedPlayer = followedPlayer;
    }

    public Matrix4f getViewMatrix(double lerpWeight){
        Matrix4f viewMatrix = new Matrix4f();
        var lerpedPos = new Vector3f();
        followedPlayer.getLastEyePos().lerp(followedPlayer.getCurrentEyePos(), (float)lerpWeight,lerpedPos);
        viewMatrix.lookAt(lerpedPos, followedPlayer.getDirectionVector().add(lerpedPos), new Vector3f(0.0f, 1.0f, 0.0f));
        return viewMatrix;
    }
}
