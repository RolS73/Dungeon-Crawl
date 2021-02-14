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

    public void fight() {
        playFightSoundEffects(attacker.getAttackSoundFile());
        int damage = damageCalculation();
        defender.setHealth(defender.getHealth() - damage);
        if (defender.getHealth() <= 0) {
            killDefender();
        } else {
            if (defender instanceof Player) {
                ((Player) defender).playerHit();
            }
        }
    }

    private int damageCalculation() {
        int damage = attacker.getAttackPower() - defender.getArmor();
        int minimumDamage = 1;
        return damage <= 0 ? minimumDamage : damage;
    }

    private void playFightSoundEffects(String attackSound) {
        try {
            Sounds.playSound(attackSound);
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
