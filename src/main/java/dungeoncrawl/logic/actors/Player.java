package dungeoncrawl.logic.actors;

import dungeoncrawl.logic.Cell;
import dungeoncrawl.logic.Direction;
import dungeoncrawl.logic.RandomGenerator;

public class Player extends Actor {

    private static int[] stats = {15 , 15, 0, 1, 0};

    private String name = "playerD";
    private String nameGivenByPlayer;
    private int maxHealth = 15;
    private int strength = 1;
    private int armor;
    private int moneyAmount;
    private final String[] attackSoundFiles = new String[] {"Sword1"};
    private final String[] hitSoundFiles = new String[] {"Blank"};

    public Player(Cell cell) {
        super(cell);
        this.setAttackPower(stats[3]); // ez új
        this.setHealth(stats[0]);
        this.setMaxHealth(stats[1]);
        this.setArmor(stats[2]);
        this.setMoneyAmount(stats[4]);
        super.actorFacingDirection = Direction.DOWN;
        super.cellInFrontOfActor = this.getCell().getNeighbor(0, 1);
    }

    public void saveStats() {
        stats[0] = this.getHealth();
        stats[1] = this.maxHealth;
        stats[2] = this.armor;
        stats[3] = this.getAttackPower();
        stats[4] = this.moneyAmount;
    }

    public void loadStats() {
        this.setHealth(stats[0]);
        this.setMaxHealth(stats[1]);
        this.setArmor(stats[2]);
        this.setAttackPower(stats[3]);
        this.setMoneyAmount(stats[4]);
    }

    public void setMaxHealth(int maxHealth) {
        this.maxHealth = maxHealth;
    }

    public String getTileName() {
        return name;
    }

    public int getMaxHealth() {
        return maxHealth;
    }

    public void raiseMaxHealth(int maxHealthRaisedBy) {
        this.maxHealth = this.maxHealth + maxHealthRaisedBy;
    }

    public void setTileName(String newName) {
        this.name = newName;
    }

//    public void raiseArmor(int armorUpgrade) {
//        this.armor = this.armor + armorUpgrade;
//    }


    public int getMoneyAmount() {
        return moneyAmount;
    }

    public void setMoneyAmount(int moneyAmount) {
        this.moneyAmount = moneyAmount;
    }

    public void raiseMoneyAmount(int moneyAmount) {
        this.moneyAmount += moneyAmount;
    }

    public int getStrength() {
        return strength;
    }

    public void restoreHealth(int healAmount) {
        super.setHealth(Math.min(getHealth() + healAmount, getMaxHealth()));
    }

    public void lowerHealth(int damageAmount) {
        super.setHealth(getHealth() - damageAmount);
    }

    @Override
    public void playDeathSound() {
        Sounds.playSound("Hdead");
    }

    public String[] getAttackSoundFiles() {
        return attackSoundFiles;
    }

    public String[] getHitSoundFiles() {
        return hitSoundFiles;
    }

    public void playerHit() {
        int randomNum = RandomGenerator.nextInt(2);
        if (randomNum == 0) {
            Sounds.playSound("playerHurt1");
        } else {
            Sounds.playSound("playerHurt2");
        }
    }

    public void updateActorOrientation() {
        switch (name) {
            case "playerU":
                setCellInFrontOfActor(this.getCell().getNeighbor(0,-1));
                super.actorFacingDirection = Direction.UP;
                break;
            case "playerR":
                setCellInFrontOfActor(this.getCell().getNeighbor(1,0));
                super.actorFacingDirection = Direction.RIGHT;
                break;
            case "playerD":
                setCellInFrontOfActor(this.getCell().getNeighbor(0,1));
                super.actorFacingDirection = Direction.DOWN;
                break;
            case "playerL":
                setCellInFrontOfActor(this.getCell().getNeighbor(-1,0));
                super.actorFacingDirection = Direction.LEFT;
                break;
        }
    }

    @Override
    public void onHit() {
        super.onHit();
        playerHit();
    }

    public void setNameGivenByPlayer(String nameGivenByPlayer) {
        this.nameGivenByPlayer = nameGivenByPlayer;
    }

    @Override
    public String toString() {
        return nameGivenByPlayer;
    }

    //    public void playHurtSound() {
//
//    }
}
