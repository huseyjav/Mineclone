package com.mineclone.game.engine.physics;

import com.mineclone.game.engine.world.Entity.Entity;
import com.mineclone.game.engine.world.World;

import static java.lang.Math.floor;

public class Physics {
    World world;

    public Physics(World world){
        this.world = world;
    }

    public void tick(Entity ent){
//        System.out.println(ent.moveIntent);
        double dx = ent.moveIntent.x * ent.moveSpeed;
        double dy = 0;
        double dz = ent.moveIntent.z * ent.moveSpeed;
        dy-=0.3;
        ent.currentPosition.x+=sweepX(ent, dx);
        double returned = sweepY(ent, dy);
//        System.out.println(returned);
        ent.currentPosition.y+=returned;
        ent.currentPosition.z+=sweepZ(ent, dz);
    }

    double sweepX(Entity E, double dx){
        if(dx == 0) return 0;
        int increment = dx > 0 ? 1 : -1;
        int start = dx > 0 ? (int)Math.floor(E.getXMax()) : (int)Math.floor(E.getXMin());
        int end = dx > 0 ? (int)Math.floor(dx + E.getXMax()) :  (int)Math.floor(dx + E.getXMin());
            for(int x = start; x != end+increment; x+=increment){
                for(int y = (int)(E.getYMin()); y <= (int)Math.floor(E.getYMax()); y++){
                    for(int z = (int)(E.getZMin()); z <= (int)Math.floor(E.getZMax()); z++){
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
        System.out.println(dy);
        int increment = dy > 0 ? 1 : -1;
        int start = dy > 0 ? (int)Math.floor(E.getYMax()) : (int)Math.floor(E.getYMin());
        int end = dy > 0 ? (int)Math.floor(dy + E.getYMax()) :  (int)Math.floor(dy + E.getYMin());
        for(int y = start; y != end+increment; y+=increment){
            for(int x = (int)(E.getXMin()); x <= (int)Math.floor(E.getXMax()); x++){
                for(int z = (int)(E.getZMin()); z <= (int)Math.floor(E.getZMax()); z++){
                    if(x<0 || y<0 || z<0) return 0;
                    if(!world.isSolidBlock(x,y,z)) continue;
                    if(dy>0) return Math.min(dy, y - E.getYMax());
                    else return Math.max(dy, (y+1) - E.getYMin());
                }
            }
        }
        return dy;
    }
    double sweepZ(Entity E, double dz){
        if(dz == 0) return 0;
        int increment = dz > 0 ? 1 : -1;
        int start = dz > 0 ? (int)Math.floor(E.getZMax()) : (int)Math.floor(E.getZMin());
        int end = dz > 0 ? (int)Math.floor(dz + E.getZMax()) :  (int)Math.floor(dz + E.getZMin());
        for(int z = start; z != end+increment; z+=increment){
            for(int x = (int)(E.getXMin()); x <= (int)Math.floor(E.getXMax()); x++){
                for(int y = (int)(E.getYMin()); y <= (int)Math.floor(E.getYMax()); y++){
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
