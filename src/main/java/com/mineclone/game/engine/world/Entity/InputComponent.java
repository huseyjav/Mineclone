package com.mineclone.game.engine.world.Entity;
import com.mineclone.game.InputController;
import org.lwjgl.glfw.GLFW;

public class InputComponent {
    public void update(Player player){
        player.moveIntent.x=0;
        player.moveIntent.y=0;
        player.moveIntent.z=0;
        if(InputController.getInstance().getKeyState(GLFW.GLFW_KEY_W)){
            player.moveIntent.add(player.getDirectionVectorXZ());
        }
        if(InputController.getInstance().getKeyState(GLFW.GLFW_KEY_S)){
            player.moveIntent.sub(player.getDirectionVectorXZ());
        }
        if(InputController.getInstance().getKeyState(GLFW.GLFW_KEY_A)){
            player.moveIntent.sub(player.getRightVector());
        }
        if(InputController.getInstance().getKeyState(GLFW.GLFW_KEY_D)){
            player.moveIntent.add(player.getRightVector());
        }
//        player.moveIntent.normalize();
    }
}
