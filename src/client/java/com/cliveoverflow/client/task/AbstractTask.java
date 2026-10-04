package com.cliveoverflow.client.task;

import com.cliveoverflow.client.role.AbstractRole;
import com.cliveoverflow.client.baritonecall.BaritoneCall;
import net.minecraft.client.Minecraft;

import java.util.LinkedList;

public abstract class AbstractTask {
    protected String name;
    protected AbstractRole actingRole;
    //protected LinkedList<BaritoneCall<?>> taskBaritoneCalls;
    public AbstractTask(String name, AbstractRole actingRole){//, LinkedList<BaritoneCall<?>> taskBaritoneCalls){
        this.name = name;
        this.actingRole = actingRole;
        //this.taskBaritoneCalls = taskBaritoneCalls;
    }
    public abstract BaritoneCall<?> optionalExecute(boolean shouldExecute, Minecraft client);

}
