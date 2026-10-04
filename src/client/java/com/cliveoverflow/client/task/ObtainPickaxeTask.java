
package com.cliveoverflow.client.task;

import baritone.api.BaritoneAPI;
import baritone.api.cache.IWorldScanner;
import baritone.api.utils.IPlayerContext;
import com.cliveoverflow.client.baritonecall.*;
import com.cliveoverflow.client.robot.AbstractRobot;
import com.cliveoverflow.client.role.AbstractRole;
import net.minecraft.client.Minecraft;
import net.minecraft.core.BlockPos;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.cliveoverflow.client.Util.LOGS;

public class ObtainPickaxeTask extends AbstractTask {
    public ObtainPickaxeTask(AbstractRole actingRole) {
        super("Task_ObtainPickaxe", actingRole); //new LinkedList<BaritoneCall<?>>(List.of(new BaritoneMineCall(LOGS.toArray(LOGS.toArray(new Block[0]))))));
    }

    @Override
    public BaritoneCall<?> optionalExecute(boolean shouldExecute, Minecraft client) {


        IPlayerContext playerContext = AbstractRobot.baritone.getPlayerContext();
        IWorldScanner scanner = BaritoneAPI.getProvider().getWorldScanner();

        // 1. Scan a 2-chunk radius around the player for an existing crafting table
        List<BlockPos> tablesFound = scanner.scanChunkRadius(
                playerContext,
                Collections.singletonList(Blocks.CRAFTING_TABLE),
                1,   // Max number of blocks to find before stopping the scan
                -1,  // Y-level threshold (-1 disables threshold limits)
                2    // Search radius in chunks
        );

        if (tablesFound.isEmpty()) {
            Inventory inv = client.player.getInventory();
            if (inv.contains(new ItemStack(Items.CRAFTING_TABLE))) {
                BlockPos p =playerContext.player().blockPosition();
                return new BaritonePlaceCall(new String[]{p.getX() + "", p.getY() + "", p.getZ() + "", "crafting_table"}, actingRole);
            }


            inv = client.player.getInventory();
            if (inv.contains(ItemTags.LOGS)) {
                return new BaritoneCraftCall(new String[]{Items.CRAFTING_TABLE.getName().getString(), "1"}, actingRole);
            }

            return new BaritoneMineCall(LOGS.toArray(new Block[0]), actingRole);
        }// 1. Map raw log types to their corresponding plank command strings
        Map<Item, String> logTiers = new HashMap<>();
        logTiers.put(Items.OAK_LOG, "oak_planks");
        logTiers.put(Items.BIRCH_LOG, "birch_planks");
        logTiers.put(Items.SPRUCE_LOG, "spruce_planks");
        logTiers.put(Items.JUNGLE_LOG, "jungle_planks");
        logTiers.put(Items.ACACIA_LOG, "acacia_planks");
        logTiers.put(Items.DARK_OAK_LOG, "dark_oak_planks");
        logTiers.put(Items.MANGROVE_LOG, "mangrove_planks");
        logTiers.put(Items.CHERRY_LOG, "cherry_planks");

        // 2. Step 1: Craft planks if we have logs but no planks/sticks
        int stickCount = countItems(client.player.getInventory(), Items.STICK);
        int totalPlanks = countPlanks(client.player.getInventory());

        if (totalPlanks < 3 && stickCount < 2) {
            for (Map.Entry<Item, String> entry : logTiers.entrySet()) {
                if (countItems(client.player.getInventory(), entry.getKey()) > 0) {
                    // Craft a batch of planks from the first log variant found
                    return new BaritoneCraftCall(new String[]{entry.getValue(), "4"}, actingRole);
                }
            }
        }

        // 3. Step 2: Craft sticks if we don't have enough but have planks available
        if (stickCount < 2 && totalPlanks >= 2) {
            return new BaritoneCraftCall(new String[]{"stick", "4"}, actingRole);
        }

        // 4. Step 3: Run the Pickaxe Tier check (Netherite -> Diamond -> Iron -> Stone -> Wood)
        Object[][] pickaxeTiers = {
                {Items.NETHERITE_INGOT, "netherite_pickaxe"},
                {Items.DIAMOND, "diamond_pickaxe"},
                {Items.IRON_INGOT, "iron_pickaxe"},
                {Items.COBBLESTONE, "stone_pickaxe"},
                {Items.OAK_PLANKS, "wooden_pickaxe"} // Will fallback to basic wood tool
        };

        String pickaxeToCraft = null;

        for (Object[] tier : pickaxeTiers) {
            Item materialItem = (Item) tier[0];
            String commandName = (String) tier[1];

            // Custom handling for wooden pickaxe: ensure we have any plank variant
            if (logTiers.containsKey(materialItem)) {
                if (totalPlanks >= 3 && stickCount >= 2) {
                    pickaxeToCraft = commandName;
                    break;
                }
            } else if (countItems(client.player.getInventory(), materialItem) >= 3 && stickCount >= 2) {
                // Standard resource tiers (Diamond, Iron, etc.)
                pickaxeToCraft = commandName;
                break;
            }
        }

        // 5. Execute pickaxe craft
        if (pickaxeToCraft != null) {
            return new BaritoneCraftCall(new String[]{pickaxeToCraft, "1"}, actingRole);
        }
        throw new RuntimeException("fuckd");
    }
    // Helper method to count items across the whole container
    private int countItems(Inventory inventory, Item item) {
        int total = 0;
        for (int i = 0; i < inventory.getContainerSize(); i++) {
            var stack = inventory.getItem(i);
            if (stack.getItem() == item) {
                total += stack.getCount();
            }
        }
        return total;
    }

    // Helper method to count any standard plank items in inventory
    private int countPlanks(Inventory inventory) {
        int total = 0;
        Item[] allPlanks = {Items.OAK_PLANKS, Items.SPRUCE_PLANKS, Items.BIRCH_PLANKS,
                Items.JUNGLE_PLANKS, Items.ACACIA_PLANKS, Items.DARK_OAK_PLANKS,
                Items.MANGROVE_PLANKS, Items.CHERRY_PLANKS};
        for (Item plankType : allPlanks) {
            total += countItems(inventory, plankType);
        }
        return total;
    }
}
