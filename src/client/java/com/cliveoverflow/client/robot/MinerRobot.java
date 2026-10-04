package com.cliveoverflow.client.robot;

import com.cliveoverflow.client.role.BasicRobotRole;

public class MinerRobot extends AbstractRobot{
    public MinerRobot(int intitialTarget) {
        super("Robot_Miner", new BasicRobotRole(), intitialTarget);
    }

}
