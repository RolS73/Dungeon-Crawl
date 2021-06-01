package dungeoncrawl.logic.actors.monsters;

import dungeoncrawl.logic.Cell;
import dungeoncrawl.logic.CellType;
import dungeoncrawl.logic.Direction;
import dungeoncrawl.logic.actors.Actor;
import dungeoncrawl.logic.actors.Player;
import dungeoncrawl.logic.actors.Sounds;

public abstract class Monster extends Actor {

    protected String name;
    protected int count = 0;
    protected MonsterMovementType movementType;

    public Monster(Cell cell) {
        super(cell);
    }

    public void monsterMove(int x, int y) {
        if (movementType == MonsterMovementType.PATROL) {
            changeSpriteBasedOnMovementDirection(x, y);
            Cell nextCell = this.getCell().getNeighbor(x, y);

            if (nextCell.getCellType() == CellType.FLOOR && nextCell.getCellType() != CellType.OBJECT) {
                if (nextCell.getActor() instanceof Player) {
                    attack(nextCell);
                } else if (nextCell.getActor() != null) {
                } else {
                    nextCell.setActor(this);
                    this.getCell().setActor(null);
                    this.setCell(nextCell);
                }
            }

        } else if (movementType == MonsterMovementType.GUARD) {
            Cell nextCell = super.getCell().getNeighbor(x, y);

            if (nextCell == null) {
                return;
            }
            if (nextCell.getActor() instanceof Player) {
                count++;
//            System.out.println(count);
                if(count > 2){
//                playAttackSound();
                    attack(nextCell);
//                damageCalculation(nextCell);
//                nextCell.getActor().setHealth(nextCell.getActor().getHealth() - this.getAttackPower());
                    count = 0;
                }
            } else {
                count = 0;
            }
        }
    }

    public void monsterLookat(int x, int y) {
        if (movementType == MonsterMovementType.GUARD) {
            if (Math.abs(x)>Math.abs(y)) {
                if (x<0) {
                    this.name = this.getActorDirectionalSpriteByDirection(Direction.LEFT);
                }
                if (x>0) {
                    this.name = this.getActorDirectionalSpriteByDirection(Direction.RIGHT);
                }
            } else {
                if (y<0) {
                    this.name = this.getActorDirectionalSpriteByDirection(Direction.UP);
                }
                if (y>0) {
                    this.name = this.getActorDirectionalSpriteByDirection(Direction.DOWN);
                }
            }
            if (Math.abs(x) + Math.abs(y) > 1) {
                count = 0;
            }
        }
    }

    public void playAttackSound() {}

    public void playDeathSound() {}

    public void rollForMonsterLoot() {}

    public void changeSpriteBasedOnMovementDirection(int x, int y) {
        if (y<0) {
            this.name = this.getActorDirectionalSpriteByDirection(Direction.UP);
        }
        if (y>0) {
            this.name = this.getActorDirectionalSpriteByDirection(Direction.DOWN);
        }
        if (x<0) {
            this.name = this.getActorDirectionalSpriteByDirection(Direction.LEFT);
        }
        if (x>0) {
            this.name = this.getActorDirectionalSpriteByDirection(Direction.RIGHT);
        }
    }

    @Override
    public void onDeath() {
        super.onDeath();
        Sounds.playSound(getDeathSoundFile());
        rollForMonsterLoot();
    }

    enum MonsterMovementType {
        PATROL,
        GUARD
    }

    public String getTileName() {
        return this.name;
    }

    public int getCount() {
        return count;
    }

    //    @Override
//    protected void attack(Cell nextCell) {
//        super.attack(nextCell);
//        if (nextCell.getActor() != null && nextCell.getActor() instanceof Player) {
//            ((Player) nextCell.getActor()).playerHit();
//        }
//    }

}
