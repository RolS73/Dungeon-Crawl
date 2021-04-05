package dungeoncrawl.logic.actors.monsters;

import dungeoncrawl.Main;
import dungeoncrawl.logic.Cell;
import dungeoncrawl.logic.CellType;
import dungeoncrawl.logic.RandomGenerator;
import dungeoncrawl.logic.actors.Player;
import dungeoncrawl.logic.actors.Sounds;
import dungeoncrawl.logic.actors.items.looting.LootTable;

public class Skeleton extends Monster {

    private String name = "skeletonD";

    public Skeleton(Cell cell) {
        super(cell);
        this.setAttackPower(2);
        this.setHealth(10);
        this.setAttackSoundFiles(new String[] {"ZombieAttack2", "ZombieAttack1"});
    }

    @Override
    public void playDeathSound() {
        Sounds.playSound("SkeletonDeath");
    }

    public void playAttackSound() {
        int randomNum = RandomGenerator.nextInt(2);
        if (randomNum == 0) {
            Sounds.playSound("ZombieAttack2");
        } else {
            Sounds.playSound("ZombieAttack1");
        }
    }

    @Override
    public String getTileName() {
        return this.name;
    }

    @Override
    public void monsterMove(int x, int y) {
        if (y < 0) {
            this.name = "skeletonU";
        }
        if (y > 0) {
            this.name = "skeletonD";
        }
        if (x < 0) {
            this.name = "skeletonL";
        }
        if (x > 0) {
            this.name = "skeletonR";
        }
        Cell nextCell = this.getCell().getNeighbor(x, y);

        if (nextCell.getCellType() == CellType.FLOOR && nextCell.getCellType() != CellType.OBJECT) {
            if (nextCell.getActor() instanceof Player) {
//                damageCalculation(nextCell);
                attack(nextCell);
//                playAttackSound();
//                nextCell.getActor().setHealth(nextCell.getActor().getHealth() - this.getAttackPower());
//                if (this.getHealth() < 1) {
//                    this.getCell().setActor(null);
//                }
            } else if (nextCell.getActor() != null) {
            } else {
                nextCell.setActor(this);
                this.getCell().setActor(null);
                this.setCell(nextCell);
            }

        }

    }

    @Override
    public void rollForMonsterLoot() {
        int tableRoll = RandomGenerator.nextInt(100);
        if (tableRoll > 37) {
        } else if (tableRoll > 2 && tableRoll < 37) {
            if (this.getCell().getItem() == null) {
                this.getCell().setItem(new LootTable().getMonsterCommonLoot().get(0));
            } else {
                Main.getCurrentMap().getPlayer().getCell().setItem(new LootTable().getMonsterCommonLoot().get(0));
            }

        } else {
            if (this.getCell().getItem() == null) {
                this.getCell().setItem(new LootTable().getMonsterUniqueLoot().get(0));
            } else {
                Main.getCurrentMap().getPlayer().getCell().setItem(new LootTable().getMonsterUniqueLoot().get(0));
            }
        }
    }
}
