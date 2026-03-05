package com.mineclone.game.engine.physics;

import com.mineclone.game.engine.Utils;
import com.mineclone.game.engine.world.Entity.Entity;
import com.mineclone.game.engine.world.World;
import org.joml.Quaterniond;
import org.joml.Vector3d;

import static java.lang.Math.*;

public class Physics {
    World world;

    public Physics(World world){
        this.world = world;
    }

    // NOTE: there might be precision errors here due to double comp, but cba fixing them rn, fix them later
    public void tick(Entity ent){
        if(ent.moveIntent.length()==0){
            // decelerate
            if(ent.velocity.x<0){
                ent.velocity.x = Math.min(0, ent.velocity.x + ent.deceleration);
            }
            else if(ent.velocity.x>0){
                ent.velocity.x = Math.max(0, ent.velocity.x - ent.deceleration);
            }
            if(ent.velocity.z<0){
                ent.velocity.z = Math.min(0, ent.velocity.z + ent.deceleration);
            }
            else if(ent.velocity.z>0){
                ent.velocity.z = Math.max(0, ent.velocity.z - ent.deceleration);
            }
        }
        else{
            // accelerate
            ent.velocity.x += ent.moveIntent.x * ent.acceleration;
            ent.velocity.z += ent.moveIntent.z * ent.acceleration;
            Utils.limitVectorLength(ent.velocity, ent.maxSpeed);
        }

        double dy = 0;
        if(ent.isGrounded && ent.wantsJump) dy=3;
        dy-=0.5;
        Vector3d absoluteVelocity = new Vector3d();
//        Quaterniond rotation = new Quaterniond();
        ent.velocity.rotateY(-toRadians(ent.yaw), absoluteVelocity);
//        absoluteVelocity.rotateY(toRadians(ent.pitch));
        ent.currentPosition.x+= (float) sweepX(ent, absoluteVelocity.x);
        ent.currentPosition.y+= (float) sweepY(ent, dy);
        ent.currentPosition.z+= (float) sweepZ(ent, absoluteVelocity.z);
    }

    double sweepX(Entity E, double dx){
        if(dx == 0) return 0;
        int increment = dx > 0 ? 1 : -1;
        int start = dx > 0 ? (int)Math.floor(E.getXMax()) : (int)Math.floor(E.getXMin());
        int end = dx > 0 ? (int)Math.floor(dx + E.getXMax()) :  (int)Math.floor(dx + E.getXMin());
            for(int x = start; x != end+increment; x+=increment){
                for(int y = (int)(E.getYMin()); y < (int)Math.ceil(E.getYMax()); y++){
                    for(int z = (int)(E.getZMin()); z < (int)Math.ceil(E.getZMax()); z++){
                        if(x<0 || y<0 || z<0) return 0;
                        if(!world.isSolidBlock(x,y,z)) continue;
                        if(dx>0) return Math.min(dx, x - E.getXMax());
                        else return Math.max(dx, x+1 - E.getXMin());
                    }
                }
            }
        return dx;
    }
    double sweepY(Entity E, double dy){
        if(dy == 0) return 0;
        int increment = dy > 0 ? 1 : -1;
        int start = dy > 0 ? (int)Math.floor(E.getYMax()) : (int)Math.floor(E.getYMin());
        int end = dy > 0 ? (int)Math.floor(dy + E.getYMax()) :  (int)Math.floor(dy + E.getYMin());
        for(int y = start; y != end+increment; y+=increment){
            for(int x = (int)(E.getXMin()); x < (int)Math.ceil(E.getXMax()); x++){
                for(int z = (int)(E.getZMin()); z < (int)Math.ceil(E.getZMax()); z++){
                    if(x<0 || y<0 || z<0) return 0;
                    if(!world.isSolidBlock(x,y,z)) continue;
                    if(dy>0){
                        return Math.min(dy, y - E.getYMax());
                    }

                    else {
                        if(Math.max(dy, (y+1) - E.getYMin())==0) E.isGrounded=true;
                        else E.isGrounded=false;
                        return Math.max(dy, (y+1) - E.getYMin());
                    }
                }
            }
        }
        if(dy>0) E.isGrounded=false;
        return dy;
    }
    double sweepZ(Entity E, double dz){
        if(dz == 0) return 0;
        int increment = dz > 0 ? 1 : -1;
        int start = dz > 0 ? (int)Math.floor(E.getZMax()) : (int)Math.floor(E.getZMin());
        int end = dz > 0 ? (int)Math.floor(dz + E.getZMax()) :  (int)Math.floor(dz + E.getZMin());
        for(int z = start; z != end+increment; z+=increment){
            for(int x = (int)(E.getXMin()); x < (int)Math.ceil(E.getXMax()); x++){
                for(int y = (int)(E.getYMin()); y < (int)Math.ceil(E.getYMax()); y++){
                    if(x<0 || y<0 || z<0) return 0;
                    if(!world.isSolidBlock(x,y,z)) continue;
                    if(dz>0) return Math.min(dz, z - E.getZMax());
                    else return Math.max(dz, z+1 - E.getZMin());
                }
            }
        }
        return dz;
    }
}
