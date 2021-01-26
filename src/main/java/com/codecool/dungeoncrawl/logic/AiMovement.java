package com.codecool.dungeoncrawl.logic;

import com.codecool.dungeoncrawl.logic.actors.*;

import java.util.ArrayList;
import java.util.List;

public class AiMovement {
    
    private GameMap map;

    public AiMovement(GameMap map) {
        this.map = map;
    }

    public void monsterMover() {

        for (int i = 0; i < map.monsters.size(); i++) {
            if (isPlayerNearby(map.monsters.get(i))) {
                if (map.monsters.get(i) instanceof Guardian){} else {
                    map.monsters.get(i).monsterMove(getPlayerXDifference(map.monsters.get(i)), getPlayerYDifference(map.monsters.get(i)));
                    if(map.monsters.get(i).getHealth()<1){
                        map.monsters.remove(i);
                        i--;
                    }
                }
            } else if (map.monsters.get(i) instanceof Skeleton) {
                map.monsters.get(i).move(0, 1);
                if(map.monsters.get(i).getHealth()<1){
                    map.monsters.remove(i);
                    i--;
                }
            } else if (map.monsters.get(i) instanceof Duck) {
                map.monsters.get(i).move(getPlayerXDifference(map.monsters.get(i)), getPlayerYDifference(map.monsters.get(i)));
                if(map.monsters.get(i).getHealth()<1){
                    map.monsters.remove(i);
                    i--;
                }
            }
        }
    }

    private boolean isPlayerNearby(Actor monster){
        if(Math.abs(monster.getX()- map.player.getX())+Math.abs(monster.getY()- map.player.getY())==1){
            return true;
        }
        return false;
    }

    private int getPlayerXDifference(Actor monster){
        int x = monster.getX() - map.player.getX();
        if(x>0){
            return -1;
        } else if(x<0) {
            return 1;
        }
        return 0;
    }

    private int getPlayerYDifference(Actor monster){
        int y = monster.getY() - map.player.getY();
        if(y>0){
             return -1;
        } else if (y<0) {
            return 1;
        }
        return 0;
    }

    
}
