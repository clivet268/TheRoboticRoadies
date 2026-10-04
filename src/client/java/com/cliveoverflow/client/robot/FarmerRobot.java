package com.cliveoverflow.client.robot;

import com.cliveoverflow.client.role.BasicRobotRole;

public class FarmerRobot extends AbstractRobot{
    public FarmerRobot(int intitialTarget) {
        super("Robot_Farmer", new BasicRobotRole(), intitialTarget);
    }

}
