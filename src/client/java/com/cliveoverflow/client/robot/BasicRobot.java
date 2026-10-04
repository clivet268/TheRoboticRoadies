package com.cliveoverflow.client.robot;

import com.cliveoverflow.client.role.BasicRobotRole;

public class BasicRobot extends AbstractRobot{
    public BasicRobot(int intitialTarget) {
        super("Robot_Basic", new BasicRobotRole(), intitialTarget);
    }

}
