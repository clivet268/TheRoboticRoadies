package com.cliveoverflow.client;

import com.cliveoverflow.client.robot.BasicRobot;
import com.cliveoverflow.client.robot.FarmerRobot;
import com.cliveoverflow.client.robot.MinerRobot;
import net.fabricmc.fabric.api.client.message.v1.ClientSendMessageEvents;
import net.minecraft.client.Minecraft;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Player;

    import static com.cliveoverflow.client.TheRoboticRoadiesClient.abby;

    public class ClientCommandHandler {
        public static void register() {
            ClientSendMessageEvents.ALLOW_CHAT.register(message -> {

                if (message.contains(".startrobot")) {
                    // Isolate the base string boundary cleanly to remove prefixes like ".say"
                    int startIndex = message.indexOf(".startrobot");
                    String cleanMessage = message.substring(startIndex);
                    String[] args = cleanMessage.split(" ");

                    if (args.length < 4) {
                        System.err.println("[RobotControl] CRITICAL: Not enough arguments! Expected 4, got " + args.length);
                        return false;
                    }

                    // NO MORE SILENT CATCHES: Direct array assignment indices mapping parameters explicitly
                    // args[0] = ".startrobot"
                    String roleInput = args[1].toLowerCase();   // args[1] holds "farmer" / "miner" / "basic"
                    String axisInput = args[2].toLowerCase();   // args[2] holds "x" / "z"
                    int coordinateInput = Integer.parseInt(args[3]); // args[3] holds "250"
                    boolean positiveDir = true;

                    if (args.length >= 5) {
                        if (args[4].equals("-")) {
                            positiveDir = false;
                        }
                    }

                    boolean axis;
                    int axisOffset;
                    // 1. Assign the Axis and Anchor Coordinates
                    if (axisInput.equals("x")) {
                        axis = true;
                        axisOffset = coordinateInput;
                    } else if (axisInput.equals("z")) {
                        axis = false;
                        axisOffset = coordinateInput;
                    } else {
                        System.err.println("[RobotControl] CRITICAL: Invalid axis text character: " + axisInput);
                        return false;
                    }

                    Player p = Minecraft.getInstance().player;
                    if (p != null) {
                    // 2. Select and instantiate the requested Robot Role
                    switch (roleInput) {
                        case "miner":
                            abby = new MinerRobot(axis ?  p.blockPosition().getX() : p.blockPosition().getZ());
                            break;
                        case "basic":
                            abby = new BasicRobot(axis ?  p.blockPosition().getX() : p.blockPosition().getZ());
                            break;
                        case "farmer":
                            abby = new FarmerRobot(axis ?  p.blockPosition().getX() : p.blockPosition().getZ());
                            break;
                        default:
                            System.err.println("[RobotControl] CRITICAL: Unrecognized robot configuration role: " + roleInput);
                            return false;
                    }



                    abby.isEnabled = true;
                        abby.axisX = axis;
                        abby.axisOffset = axisOffset;

                        String axisName = abby.axisX ? "X" : "Z";
                        String anchorName = abby.axisX ? "Z" : "X";
                        String dirSign = abby.isPositiveDir ? "+" : "-";
                        Minecraft.getInstance().player.displayClientMessage(
                                Component.literal(String.format("§a[RobotControl] Starting a %s. Operating along %s-axis (Anchored at %s=%d) in %s direction.",
                                        abby.name, axisName, anchorName, abby.targetPos, dirSign)),
                                false
                        );
                    }
                    return false;
                }

                if (message.contains(".stoprobot")) {
                    abby.isEnabled = false;
                    if (Minecraft.getInstance().player != null) {
                        Minecraft.getInstance().player.displayClientMessage(
                                Component.literal("§c[RobotControl] Stopped robot routing and cleared roles."),
                                false
                        );
                    }
                    return false;
                }

                return true;
            });
        }
    }
