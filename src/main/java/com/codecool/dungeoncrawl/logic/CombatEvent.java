package com.codecool.dungeoncrawl.logic;

import com.codecool.dungeoncrawl.logic.actors.Actor;
import com.codecool.dungeoncrawl.logic.actors.Player;
import com.codecool.dungeoncrawl.logic.actors.Sounds;

public class CombatEvent {

    private final Actor attacker;
    private final Actor defender;

    public CombatEvent(Actor attacker, Actor defender) {
        this.attacker = attacker;
        this.defender = defender;
    }

    public void attack() {
        playAttackSoundEffect();
        int damage = damageCalculation();
        defender.setHealth(defender.getHealth() - damage);
        getConsequenceOfAttack();
    }

    private void getConsequenceOfAttack() {
        if (defender.getHealth() <= 0) {
            killDefender();
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
            defender.playDeathSound();
            defender.getCell().setActor(null);
        }
    }
}
