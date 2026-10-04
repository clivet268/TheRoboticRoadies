package com.cliveoverflow.client.baritonecall;

import baritone.api.IBaritone;
import com.cliveoverflow.client.role.AbstractRole;

import java.util.LinkedList;
import java.util.List;

public abstract class BaritoneCall<T> {
    protected LinkedList<T> stuff;
    protected AbstractRole actingRole;

    public BaritoneCall(T[] stuff, AbstractRole actingRole){
        this.stuff = new LinkedList<>(List.of(stuff));
        this.actingRole = actingRole;
    }

        public abstract void execute(IBaritone baritone);
}
