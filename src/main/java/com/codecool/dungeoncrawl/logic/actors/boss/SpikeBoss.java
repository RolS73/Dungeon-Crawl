package com.codecool.dungeoncrawl.logic.actors.boss;

import com.codecool.dungeoncrawl.logic.Cell;
import com.codecool.dungeoncrawl.logic.actors.Actor;

import java.util.ArrayList;
import java.util.List;

public class SpikeBoss extends Actor {

    List<SpikeForBosses> spikes = new ArrayList<>();

    public SpikeBoss(Cell cell) {
        super(cell);
        this.setAttackPower(10);
        this.setHealth(100);
    }

    @Override
    public String getTileName() {
        return "spikeboss";
    }


    @Override
    public void move(int dx, int dy) {
        for(SpikeForBosses s : spikes){
            s.remover();
        }
    }

    public void spikeAdder(SpikeForBosses spike){
        spikes.add(spike);
    }


}
