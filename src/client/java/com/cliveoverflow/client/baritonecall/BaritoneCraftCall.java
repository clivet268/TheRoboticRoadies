package com.cliveoverflow.client.baritonecall;

import baritone.api.IBaritone;
import baritone.api.pathing.goals.GoalBlock;
import baritone.api.process.IGetToBlockProcess;
import com.cliveoverflow.client.role.AbstractRole;
import net.minecraft.core.BlockPos;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;

public class BaritoneCraftCall extends BaritoneCall<String>{
    public BaritoneCraftCall(String[] stuff, AbstractRole actingRole) {
        super(stuff, actingRole);
    }

    @Override
    public void execute(IBaritone baritone) {
        baritone.getCommandManager().execute("craft " + stuff.get(0) + stuff.get(1));
    }
}
