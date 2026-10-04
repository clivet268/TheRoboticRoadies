package com.cliveoverflow.client.baritonecall;

import baritone.api.IBaritone;
import baritone.api.pathing.goals.GoalBlock;
import com.cliveoverflow.client.role.AbstractRole;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.Block;

public class BaritoneGotoCall extends BaritoneCall<BlockPos>{
    public BaritoneGotoCall(BlockPos[] stuff, AbstractRole actingRole) {
        super(stuff, actingRole);
    }

    @Override
    public void execute(IBaritone baritone) {
        baritone.getCustomGoalProcess().setGoal(new GoalBlock(stuff.getFirst()));
    }
}
