package dungeoncrawl.logic.actors.boss;

import dungeoncrawl.logic.Cell;
import dungeoncrawl.logic.CellType;
import dungeoncrawl.logic.actors.Player;
import dungeoncrawl.logic.actors.monsters.Monster;

public class SpikeForBosses extends Monster {

    //private SpikeBoss mainBody;
    int count = 0;
    boolean stunned = false;
    private String name = "spikeforbossesL";



    public SpikeForBosses(Cell cell) {
        super(cell);
       // mainBody = Main.cheatingMapGetter().getBoss1();
        setAttackPower(7);
        setHealth(100000);
        this.setAttackSoundFiles(new String[] {"genericSwing"}); //PLACEHOLDER
    }

    @Override
    public String getTileName() {
        return this.name;
    }

    @Override
    public void monsterMove(int x, int y) {

        if (x<=0) {
            this.name = "spikeforbossesL";
        }
        if (x>0) {
            this.name = "spikeforbossesR";
        }

        Cell nextCell = this.getCell().getNeighbor(x, y);
        if(nextCell.getActor() instanceof SpikeBoss){
            nextCell = this.getCell().getNeighbor(3*x, 3*y);
            nextCell.setActor(this);
            this.getCell().setActor(null);
            this.setCell(nextCell);
            return;
        }

        if(stunned){
            count++;
            if (count>5){
                stunned = false;
            }
            return;
        }

        if(nextCell.getCellType()== CellType.STUNNER){
            if(count>5){
                count = 0;
            } else if(count==0) {
                stunned = true;
                return;
            }
        }

        if (nextCell.getCellType() == CellType.BOSSFLOOR || nextCell.getCellType() == CellType.STUNNER) {
            if (nextCell.getActor() instanceof Player) {
                attack(nextCell);
//                damageCalculation(nextCell);
//                nextCell.getActor().setHealth(nextCell.getActor().getHealth() - this.getAttackPower());
                if (this.getHealth() < 1) {
                    this.getCell().setActor(null);
                }
            } else if (nextCell.getActor() != null) {

            } else {
                nextCell.setActor(this);
                this.getCell().setActor(null);
                this.setCell(nextCell);
            }

        }
    }

    public void remover(){
        this.getCell().setActor(null);
    }

}
