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
            killDefender();
            if (defender instanceof Monster) {
                ((Monster) defender).rollForMonsterLoot();
                log.append(defender)
                        .append(" dies!\n");
            }
        } else {
            if (defender instanceof Player) {
                ((Player) defender).playerHit();
            }
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

    private void killDefender() {
        if (defender.getHealth() <= 0) {
            Sounds.playSound("kill1");
            defender.playDeathSound();
            defender.getCell().setActor(null);
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
