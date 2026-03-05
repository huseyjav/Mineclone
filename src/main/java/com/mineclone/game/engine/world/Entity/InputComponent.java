package com.mineclone.game.engine.world.Entity;
import com.mineclone.game.InputController;
import org.lwjgl.glfw.GLFW;

public class InputComponent {
    public void update(Player player){
        player.moveIntent.x=0;
        player.moveIntent.y=0;
        player.moveIntent.z=0;
        player.wantsJump=false;
        if(InputController.getInstance().getKeyState(GLFW.GLFW_KEY_W)){
            //player.moveIntent.add(player.getDirectionVectorXZ());
            player.moveIntent.x++;
        }
        if(InputController.getInstance().getKeyState(GLFW.GLFW_KEY_S)){
            //player.moveIntent.sub(player.getDirectionVectorXZ());
            player.moveIntent.x--;
        }
        if(InputController.getInstance().getKeyState(GLFW.GLFW_KEY_A)){
            //player.moveIntent.sub(player.getRightVector());
            player.moveIntent.z--;
        }
        if(InputController.getInstance().getKeyState(GLFW.GLFW_KEY_D)){
//            player.moveIntent.add(player.getRightVector());
            player.moveIntent.z++;
        }
        if(InputController.getInstance().getKeyState(GLFW.GLFW_KEY_SPACE)) player.wantsJump = true;
        if(player.moveIntent.length()!=0) player.moveIntent.normalize();
    }
}
