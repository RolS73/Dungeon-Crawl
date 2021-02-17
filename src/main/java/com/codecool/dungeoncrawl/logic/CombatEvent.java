package com.codecool.dungeoncrawl.logic;

import com.codecool.dungeoncrawl.logic.actors.Actor;
import com.codecool.dungeoncrawl.logic.actors.Player;
import com.codecool.dungeoncrawl.logic.actors.Sounds;
import com.codecool.dungeoncrawl.logic.actors.monsters.Monster;

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
        System.out.println(log.toString());
    }

    private void getConsequenceOfAttack() {
        if (defender.getHealth() <= 0) {
            defender.onDeath();
            log.append(defender)
                    .append(" dies!\n");
        } else {
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
                .append(" damage to ")
                .append(defender)
                .append(".")
                .append("\n");
    }

    public StringBuilder getLog() {
        return log;
    }

}
