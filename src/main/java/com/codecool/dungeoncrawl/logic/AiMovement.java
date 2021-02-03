package com.codecool.dungeoncrawl.logic;

import com.codecool.dungeoncrawl.logic.actors.*;
import com.codecool.dungeoncrawl.logic.actors.monsters.Duck;
import com.codecool.dungeoncrawl.logic.actors.monsters.Guardian;
import com.codecool.dungeoncrawl.logic.actors.monsters.Skeleton;
import com.codecool.dungeoncrawl.logic.actors.monsters.TheThing;

public class AiMovement {
    
    private GameMap map;
    private int x;
    private int y;
    private int count;

    public AiMovement(GameMap map) {
        this.map = map;
    }

    public void monsterMover() {

        for (int i = 0; i < map.monsters.size(); i++) {
            if(map.monsters.get(i).getHealth()<1){
                map.monsters.remove(i);
                i--;
            } else if (isPlayerNearby(map.monsters.get(i))) {
                if(map.monsters.get(i) instanceof TheThing){
                    count++;
                }
                    map.monsters.get(i).monsterMove(getPlayerXDifference(map.monsters.get(i)), getPlayerYDifference(map.monsters.get(i)));
            } else if (map.monsters.get(i) instanceof Skeleton) {
                setRandom();
                map.monsters.get(i).monsterMove(x, y);
            } else if (map.monsters.get(i) instanceof Duck) {
                map.monsters.get(i).monsterMove(getPlayerXDifference(map.monsters.get(i)), getPlayerYDifference(map.monsters.get(i)));
            } else if(map.monsters.get(i) instanceof Guardian){
                map.monsters.get(i).monsterMove(1,0);
            } else if (map.monsters.get(i) instanceof TheThing){
                count++;
                if(count>6){
                    coordinateGenerator();
                    ((TheThing) map.monsters.get(i)).teleport(x,y);
                    count = 0;
                }else {
                    setRandom();
                    map.monsters.get(i).monsterMove(x,y);
                }
            }
        }
    }

    private boolean isPlayerNearby(Actor monster){
        if(Math.abs(monster.getX()- map.player.getX())+Math.abs(monster.getY()- map.player.getY())==1){
            return true;
        }
//        else if (Math.abs(monster.getX()- map.player.getX())+Math.abs(monster.getY()- map.player.getY())==2){
//            return true;
//        }
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

    private void setRandom(){
        double random = Math.random()*10;
        if(random<2.5){
            this.x = 1; this.y = 0;
        } else if(random>=2.5 && random<5){
            this.x = 0; this.y = 1;
        }else if(random>=5 && random<7.5){
            this.x = -1; this.y = 0;
        } else if(random>=7.5){
            this.x = 0; this.y = -1;
        }
    }

    private void coordinateGenerator(){
        int[] lolz = new int[2];
        while(true){
            lolz[0] = (int) (map.getWidth() * Math.random());
            lolz[1] = (int) (map.getHeight() * Math.random());
            if(map.getCell(lolz[0], lolz[1]).getCellType()==CellType.FLOOR && map.getCell(lolz[0], lolz[1]).getActor()==null){
                x = lolz[0];
                y = lolz[1];
                break;
            }
        }
    }
    
}
