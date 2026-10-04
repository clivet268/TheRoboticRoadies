package com.cliveoverflow.client;

import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;

import java.util.LinkedList;
import java.util.List;

public class Util {

    public static LinkedList<Block> LOGS = new LinkedList<>(
            List.of(Blocks.OAK_LOG,
                    Blocks.BIRCH_LOG,
                    Blocks.DARK_OAK_LOG,
                    Blocks.SPRUCE_LOG,
                    Blocks.PALE_OAK_LOG,
                    Blocks.ACACIA_LOG,
                    Blocks.CHERRY_LOG,
                    Blocks.JUNGLE_LOG,
                    Blocks.MANGROVE_LOG,
                    Blocks.STRIPPED_OAK_LOG,
                    Blocks.STRIPPED_BIRCH_LOG,
                    Blocks.STRIPPED_DARK_OAK_LOG,
                    Blocks.STRIPPED_SPRUCE_LOG,
                    Blocks.STRIPPED_PALE_OAK_LOG,
                    Blocks.STRIPPED_ACACIA_LOG,
                    Blocks.STRIPPED_CHERRY_LOG,
                    Blocks.STRIPPED_JUNGLE_LOG,
                    Blocks.STRIPPED_MANGROVE_LOG));
}
