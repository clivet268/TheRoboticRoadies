package com.cliveoverflow.client.baritonecall;

import baritone.api.IBaritone;
import com.cliveoverflow.client.role.AbstractRole;
import net.minecraft.world.level.block.Block;

public class BaritoneMineCall extends BaritoneCall<Block>{
    public BaritoneMineCall(Block[] stuff, AbstractRole actingRole) {
        super(stuff, actingRole);
    }

    @Override
    public void execute(IBaritone baritone) {
        baritone.getMineProcess().mine(stuff.toArray(new Block[]{}));
    }
}
