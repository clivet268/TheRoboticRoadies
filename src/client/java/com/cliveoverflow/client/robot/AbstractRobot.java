package com.cliveoverflow.client.robot;

import baritone.api.BaritoneAPI;
import baritone.api.IBaritone;
import com.cliveoverflow.client.role.AbstractRole;
import com.cliveoverflow.client.baritonecall.BaritoneCall;
import com.cliveoverflow.client.baritonecall.BaritoneFinishCall;
import com.cliveoverflow.client.task.AbstractTask;
import net.minecraft.client.Minecraft;

import java.util.LinkedList;
import java.util.List;

public abstract class AbstractRobot {
    public int workingRadius = 100;
    public int bufferRadius = 10;
    public boolean axisX = true;
    public int axisOffset = 0;
    public boolean isPositiveDir = true;
    public boolean isEnabled = false;
    public int floorY = 64;
    public int targetPos;
    public static final IBaritone baritone = BaritoneAPI.getProvider().getPrimaryBaritone();
    public String name;

    protected AbstractRole baseRole;
    protected int robotSuccesses = 0;
    public LinkedList<AbstractRole> roleStack = new LinkedList<>();
    protected LinkedList<LinkedList<AbstractTask>> taskStack = new LinkedList<>();
    protected double initialTarget;
    protected BaritoneCall<?> currentBaritoneCall;

    public AbstractRobot(String name, AbstractRole baseRole, int initialTarget){
        taskStack.push(baseRole.getTasklist());
        roleStack.push(baseRole);
        this.initialTarget = initialTarget;
        this.name = name;
    }

    public void become(AbstractRole toBecome){
        taskStack.add(toBecome.getTasklist());
        roleStack.push(toBecome);
    }

    public void advance(Minecraft clint){
        if(!isEnabled){
            return;
        }
        if(isRunning()){
            //TODO log that it was running every so often
            return;
        }
        if(taskStack.isEmpty()){
            taskStack.push(baseRole.getTasklist());
            //initialTarget = axisX ? clint.player.position().x : clint.player.position().z;
            initialTarget = initialTarget + (isPositiveDir ? 1 : -1) * (16 * 4);
        }
        for (AbstractTask at : taskStack.getLast()){
            BaritoneCall<?> toExec = at.optionalExecute(true, clint);
            if(toExec instanceof BaritoneFinishCall){
                taskStack.pop();
                roleStack.pop();
                robotSuccesses++;
                return;
            } else {
                currentBaritoneCall = toExec;
            }
        }
        currentBaritoneCall.execute(baritone);
    }

    public boolean isRunning(){
        return (baritone.getPathingBehavior().isPathing()
                || baritone.getCustomGoalProcess().isActive()
                || baritone.getMineProcess().isActive());
    }
}
