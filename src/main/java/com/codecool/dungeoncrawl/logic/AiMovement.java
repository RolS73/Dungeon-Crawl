package com.codecool.dungeoncrawl.logic;

import com.codecool.dungeoncrawl.logic.actors.*;

import java.util.ArrayList;
import java.util.List;

public class AiMovement {
    
    private final Player player;
    private List<Actor> monsters = new ArrayList<>();

    public AiMovement(Player player,List<Actor> monsters) {
        this.player = player;
        this.monsters =monsters;
    }

    public void monsterMover() {

        for (int i = 0; i < monsters.size(); i++) {
            if (isPlayerNearby(monsters.get(i))) {
                if (monsters.get(i) instanceof Guardian){} else {
                    monsters.get(i).monsterMove(getPlayerXDifference(monsters.get(i)), getPlayerYDifference(monsters.get(i)));
                }
            } else if (monsters.get(i) instanceof Skeleton) {
                monsters.get(i).move(0, 1);
            } else if (monsters.get(i) instanceof Duck) {
                monsters.get(i).move(getPlayerXDifference(monsters.get(i)), getPlayerYDifference(monsters.get(i)));
            }
        }
    }

    private boolean isPlayerNearby(Actor monster){
        if(Math.abs(monster.getX()- player.getX())+Math.abs(monster.getY()- player.getY())==2 ||
                Math.abs(monster.getX()- player.getX())+Math.abs(monster.getY()- player.getY())==1){
            return true;
        }
        return false;
    }

    private int getPlayerXDifference(Actor monster){
        int x = monster.getX() - player.getX();
        if(x>0){
            return -1;
        } else if(x<0) {
            return 1;
        }
        return 0;
    }

    private int getPlayerYDifference(Actor monster){
        int y = monster.getY() - player.getY();
        if(y>0){
             return -1;
        } else if (y<0) {
            return 1;
        }
        return 0;
    }

    
}
