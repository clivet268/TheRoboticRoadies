package com.cliveoverflow.client;

import com.mojang.authlib.minecraft.client.MinecraftClient;
import net.fabricmc.fabric.api.client.rendering.v1.HudRenderCallback;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.network.chat.Component;

import static com.cliveoverflow.client.TheRoboticRoadiesClient.abby;

public class StateHUDOverlay {// Inside your HudRenderCallback lookups, change the state name string evaluation call:

    public static void register() {
        HudRenderCallback.EVENT.register((guiGraphics, delta) -> {
            Minecraft client = Minecraft.getInstance();

            // 1. Thread Safety: Ensure we are only rendering on the main Render Thread
            if (!client.isSameThread()) return;

            // 2. Lifecycle Safety: Ensure the player world is loaded and HUD is visible
            if (client.player == null || client.options.hideGui) return;

            // 3. Engine Safety: Only query your mod state when it is safe
            // (Assuming 'abby' is a safe static reference from your main mod class)
            if(abby == null) return;
            if (abby.roleStack == null || abby.roleStack.isEmpty()) return;


            Font font = client.font;

            // Gather structural information from the manager pipeline
            String currentRole = "§fActive Role: §b" + abby.roleStack.getLast().getName();
            String stackDepth = "§Role History: §7[" + abby.roleStack.size() + " roles queued]";
            //String totalSuccess = "§fSuccess Milestones: §a+" + abby.successCount;

            String axisConfig = String.format("§fAxis: §e%s §7| §fAnchor: §e%d",
                    abby.axisX ? "X" : "Z", abby.targetPos);
            String dirConfig = "§fDirection: §e" + (abby.isPositiveDir ? "+" : "-");

            // Define box coordinates
            int startX = 10;
            int startY = 10;
            int boxWidth = 260;
            int boxHeight = 70;

            int x = 10; // 10 pixels from the left edge
            int y = 10; // 10 pixels from the top edge
            int lineHeight = 12; // Vertical spacing between lines
            int color = 0xFFFFFF; // White text (HEX format)

            // Render a semi-transparent slate backing for clean visual contrast
            guiGraphics.fill(startX, startY, startX + boxWidth, startY + boxHeight, 0x95000000);

            // Draw header bar
            guiGraphics.drawString(font, Component.literal("§6§l[TheRoboticRoadies Stack HUD]"), startX + 8, startY + 6, 0xFFFFFF, false);

            // Draw role & layer variables
            guiGraphics.drawString(font, Component.literal(currentRole), startX + 8, startY + 20, 0xFFFFFF, false);
            guiGraphics.drawString(font, Component.literal(stackDepth), startX + 8, startY + 31, 0xFFFFFF, false);

            // Draw positioning rules and success metric track
            guiGraphics.drawString(font, Component.literal(axisConfig + " " + dirConfig), startX + 8, startY + 44, 0xFFFFFF, false);
            //guiGraphics.drawString(font, Component.literal(totalSuccess), startX + 8, startY + 55, 0xFFFFFF, false);
        });
    }
}


/*
State flow:
RobotTasks are like a stack, when you !become a new robot, add the last role to the stack.
Once complete as that robot, go back

* routines mean that any bot can do them

BasicBot Base:
dont Have wood? -> get wood with fist
dont have crafting table in range? -> make crafting table
dont have any kind of pickaxe? -> craft pickaxe
dont have 6 stacks cobble? -> mine for it
no furnace nearby? -> craft furnace and place
no fuel in furnace? -> search nearby chests for fuel or make own fuel
then -> craft stack of chests, an axe, a pickaxe, and a hoe
then -> craft extra axe, extra pickaxe, and an extra hoe
then -> place chest and fill with 6 stacks of cobble, the extra axe, pickaxe and hoe
then -> Set new target along axis, +1 success

MinerRobot Base:
Hungry? -> eat and replenish food routine
Inventory Full? -> find and dump into chest routine
No diamond pick? -> get diamond pick routine
Dosent have a stack of diamonds? -> mine for diamonds routine
then -> Fill chest with diamond picks near target spot
then -> Set new target along axis, +1 success

FarmerRobot Base:
Hungry? -> eat food if in inv
Inventory full? -> dump nonessentials to chest
Dont have hoe? -> search nearby chests hoe or crafing one
Dirt near water within range? -> hoe it
dont have farmable crops? -> get farmable crops
ripe harvestable crop in range? -> harvest
no nearby crafting table nearby? -> !Become a Basicbot for 1 success
no nearby furnace nearby? -> !Become a Basicbot for 1 success
have craftable crops in inv? -> find nearby crafting table and craft
have smeltable crops in inv? -> find nearby furnace and smelt
then -> place crafted or smelted crops into nearby chest until the chest has at least 3 stacks of food
then -> Set new target along axis, +1 success

*:

* Eat and replenish:
Food in inv? -> eat
Food in chest near axis? -> search and place it in inv, then eat
!Become a FarmerRobot for 1 success

 */