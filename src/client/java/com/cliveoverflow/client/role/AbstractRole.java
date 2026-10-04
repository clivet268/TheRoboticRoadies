package com.cliveoverflow.client.role;

import com.cliveoverflow.client.task.AbstractTask;

import java.util.LinkedList;

public abstract class AbstractRole {
    protected String name;
    protected LinkedList<AbstractTask> tasklist;
    protected int roleSuccesses;

    public AbstractRole(String name){
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void incrementSucesses(){
        this.roleSuccesses++;
    }

    public LinkedList<AbstractTask> getTasklist(){
        return tasklist;
    }
}
