package com.cliveoverflow.client.baritonecall;

import baritone.api.IBaritone;
import com.cliveoverflow.client.role.AbstractRole;

public class BaritonePlaceCall extends BaritoneCall<String>{
    public BaritonePlaceCall(String[] stuff, AbstractRole actingRole) {
        super(stuff, actingRole);
    }

    @Override
    public void execute(IBaritone baritone) {
        baritone.getCommandManager().execute(String.format("click block %s %s %s %s", stuff.get(0), stuff.get(1), stuff.get(2), stuff.get(3)));
    }
}
