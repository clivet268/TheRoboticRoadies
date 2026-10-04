package com.cliveoverflow.client.role;

import com.cliveoverflow.client.task.GoToAxisTask;
import com.cliveoverflow.client.task.ObtainOrFindCraftingTableTask;
import com.cliveoverflow.client.task.PlaceOrFindCraftingTable;

import java.util.LinkedList;

public class BasicRobotRole extends AbstractRole{
    public BasicRobotRole() {
        super("Role_Basic");
        tasklist = new LinkedList<>();
        tasklist.push(new ObtainOrFindCraftingTableTask(this));
        tasklist.push(new GoToAxisTask(this));
        tasklist.push(new PlaceOrFindCraftingTable(this));

    }


}
