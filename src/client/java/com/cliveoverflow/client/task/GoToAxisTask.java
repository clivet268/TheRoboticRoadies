package com.cliveoverflow.client.task;

import com.cliveoverflow.client.baritonecall.BaritoneCall;
import com.cliveoverflow.client.baritonecall.BaritoneGotoCall;
import com.cliveoverflow.client.role.AbstractRole;
import net.minecraft.client.Minecraft;
import net.minecraft.core.BlockPos;

import static com.cliveoverflow.client.TheRoboticRoadiesClient.abby;

public class GoToAxisTask extends AbstractTask {
    public GoToAxisTask(AbstractRole actingRole) {
        super("Task_GoToAxis", actingRole);
    }

    @Override
    public BaritoneCall<?> optionalExecute(boolean shouldExecute, Minecraft client) {
        BaritoneGotoCall b = new BaritoneGotoCall(new BlockPos[]{nearestValidToTarget(client)}, actingRole);

        return b;
    }
    private BlockPos nearestValidToTarget(Minecraft client) {
        BlockPos pos = client.player.blockPosition();
        int currentOpposite = abby.axisX ? pos.getZ() : pos.getX();
        int nearbyaxis = Math.clamp(abby.axisX ? pos.getZ() : pos.getX(), abby.axisOffset - abby.workingRadius, abby.axisOffset + abby.workingRadius);
        if(nearbyaxis < abby.axisOffset){
            nearbyaxis = Math.min(nearbyaxis, abby.axisOffset - abby.bufferRadius);
        } else{
            nearbyaxis = Math.max(nearbyaxis, abby.axisOffset + abby.bufferRadius);
        }
        if(abby.axisX){
            return new BlockPos(abby.targetPos, abby.floorY, nearbyaxis);
        }
        return new BlockPos(nearbyaxis, abby.floorY, abby.targetPos);
    }
}
