package dungeoncrawl.logic;

import dungeoncrawl.logic.actors.Actor;
import dungeoncrawl.logic.actors.Sounds;

public class CombatEvent {

    private final Actor attacker;
    private final Actor defender;
    private StringBuilder log = new StringBuilder();

    public CombatEvent(Actor attacker, Actor defender) {
        this.attacker = attacker;
        this.defender = defender;
    }

    public void attack() {
        playAttackSoundEffect();
        int damage = damageCalculation();
        defender.setHealth(defender.getHealth() - damage);
        logBuilder(damage);
        getConsequenceOfAttack();
    }

    private void getConsequenceOfAttack() {
        if (defender.getHealth() <= 0) {
            defender.onDeath();
            log.append(defender)
                    .append(" dies!\n");
        } else {
            log.append(defender).append(" has " + defender.getHealth()).append(" remaining.\n");
            defender.onHit();
        }
    }

    private void playAttackSoundEffect() {
        String attackSoundFile = attacker.setAttackSoundFile(attacker.getAttackSoundFiles());
        String hitSoundFile = attacker.setAttackSoundFile(attacker.getHitSoundFiles());
        playFightSoundEffects(attackSoundFile);
        playFightSoundEffects(hitSoundFile);
    }

    private int damageCalculation() {
        int damage = attacker.getAttackPower() - defender.getArmor();
        int minimumDamage = 1;
        return damage <= 0 ? minimumDamage : damage;
    }

    private void playFightSoundEffects(String soundFile) {
        try {
            Sounds.playSound(soundFile);
        } catch (NullPointerException e) {
            e.printStackTrace();
        }
    }

    private void logBuilder(int damage) {
        log.append(attacker.toString())
                .append(" deals ")
                .append(damage)
                .append(" damage\nto ")
                .append(defender)
                .append(".")
                .append("\n");
    }

    public StringBuilder getLog() {
        return log;
    }

}
