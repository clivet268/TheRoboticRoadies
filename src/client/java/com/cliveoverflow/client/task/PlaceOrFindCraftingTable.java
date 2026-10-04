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
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;

import java.util.Collections;
import java.util.List;

import static com.cliveoverflow.client.Util.LOGS;

public class PlaceOrFindCraftingTable extends AbstractTask {
    public PlaceOrFindCraftingTable(AbstractRole actingRole) {
        super("Task_PlaceOrFindCraftingTable", actingRole);
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
                return new BaritoneCraftCall(new String[]{Items.CRAFTING_TABLE.getName().getString()}, actingRole);
            }

            return new BaritoneMineCall(LOGS.toArray(new Block[0]), actingRole);
        }
        return new BaritoneFinishCall();
    }
}
