package haveric.railSwitcher;

import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.data.BlockData;
import org.bukkit.block.data.type.Piston;
import org.bukkit.block.data.type.Slab;
import org.bukkit.block.data.type.Stairs;

public class RSUtil {
    public static boolean isRail(Material material) {
        boolean isRail = false;

        switch (material) {
            case RAIL:
            case POWERED_RAIL:
            case DETECTOR_RAIL:
            case ACTIVATOR_RAIL:
                isRail = true;
                break;
            default:
                break;
        }

        return isRail;
    }

    public static boolean canPlaceRail(Block block) {
        boolean canPlaceRail = true;

        BlockData blockData = block.getBlockData();

        if (blockData instanceof Stairs) {
            Stairs stairsData = (Stairs) blockData;
            if (stairsData.getHalf() == Stairs.Half.BOTTOM) {
                canPlaceRail = false;
            }
        } else if (blockData instanceof Slab) {
            Slab slabData = (Slab) blockData;
            if (slabData.getType() == Slab.Type.BOTTOM) {
                canPlaceRail = false;
            }
        } else if (blockData instanceof Piston) {
            Piston pistonData = (Piston) blockData;
            if (pistonData.isExtended()) {
                canPlaceRail = false;
            }
        } else {
            canPlaceRail = canPlaceRail(block.getType());
        }

        return canPlaceRail;
    }

    private static boolean canPlaceRail(Material material) {
        boolean canPlaceRail = true;

        switch (material) {
            case AIR:
            case ACTIVATOR_RAIL:
            case ANVIL:
            case ARMOR_STAND:

            case BLACK_BED:
            case BLUE_BED:
            case BROWN_BED:
            case CYAN_BED:
            case GRAY_BED:
            case GREEN_BED:
            case LIME_BED:
            case MAGENTA_BED:
            case ORANGE_BED:
            case PINK_BED:
            case PURPLE_BED:
            case RED_BED:
            case WHITE_BED:
            case YELLOW_BED:

            case BREWING_STAND:
            case BROWN_MUSHROOM:
            case CAKE:

            case BLACK_CARPET:
            case BLUE_CARPET:
            case BROWN_CARPET:
            case CYAN_CARPET:
            case GRAY_CARPET:
            case GREEN_CARPET:
            case LIME_CARPET:
            case MAGENTA_CARPET:
            case ORANGE_CARPET:
            case PINK_CARPET:
            case PURPLE_CARPET:
            case RED_CARPET:
            case WHITE_CARPET:
            case YELLOW_CARPET:

            case CHEST:

            case ANDESITE_WALL:
            case BRICK_WALL:
            case COBBLESTONE_WALL:
            case DIORITE_WALL:
            case GRANITE_WALL:
            case PRISMARINE_WALL:
            case SANDSTONE_WALL:
            case MOSSY_COBBLESTONE_WALL:
            case END_STONE_BRICK_WALL:
            case NETHER_BRICK_WALL:
            case STONE_BRICK_WALL:
            case MOSSY_STONE_BRICK_WALL:
            case RED_NETHER_BRICK_WALL:
            case RED_SANDSTONE_WALL:

            case CACTUS:
            case CARROTS:
            case POTATOES:
            case BEETROOTS:
            case NETHER_WART:
            case SUGAR_CANE:
            case SWEET_BERRIES:
            case SWEET_BERRY_BUSH:
            case WHEAT:

            case DAYLIGHT_DETECTOR:
            case DEAD_BUSH:
            case DETECTOR_RAIL:
            case DRAGON_EGG:
            case REPEATER:

            case SUNFLOWER:
            case LILAC:
            case ROSE_BUSH:
            case PEONY:
            case TALL_GRASS:
            case LARGE_FERN:
            case TALL_SEAGRASS:

            case ENCHANTING_TABLE:
            case END_PORTAL_FRAME:
            case END_PORTAL:
            case NETHER_PORTAL:
            case ENDER_CHEST:

            case ACACIA_FENCE:
            case BIRCH_FENCE:
            case JUNGLE_FENCE:
            case OAK_FENCE:
            case SPRUCE_FENCE:
            case DARK_OAK_FENCE:
            case NETHER_BRICK_FENCE:

            case ACACIA_FENCE_GATE:
            case BIRCH_FENCE_GATE:
            case JUNGLE_FENCE_GATE:
            case OAK_FENCE_GATE:
            case SPRUCE_FENCE_GATE:
            case DARK_OAK_FENCE_GATE:

            case FIRE:
            case FLOWER_POT:

            case ACACIA_PRESSURE_PLATE:
            case BIRCH_PRESSURE_PLATE:
            case DARK_OAK_PRESSURE_PLATE:
            case HEAVY_WEIGHTED_PRESSURE_PLATE:
            case JUNGLE_PRESSURE_PLATE:
            case LIGHT_WEIGHTED_PRESSURE_PLATE:
            case OAK_PRESSURE_PLATE:
            case SPRUCE_PRESSURE_PLATE:
            case STONE_PRESSURE_PLATE:

            case ACACIA_DOOR:
            case BIRCH_DOOR:
            case DARK_OAK_DOOR:
            case IRON_DOOR:
            case JUNGLE_DOOR:
            case OAK_DOOR:
            case SPRUCE_DOOR:

            case IRON_BARS:
            case LADDER:
            case LAVA:

            case ACACIA_LEAVES:
            case BIRCH_LEAVES:
            case DARK_OAK_LEAVES:
            case JUNGLE_LEAVES:
            case OAK_LEAVES:
            case SPRUCE_LEAVES:

            case LEVER:

            case PISTON_HEAD:
            case MOVING_PISTON:

            case POWERED_RAIL:

            case ATTACHED_MELON_STEM:
            case ATTACHED_PUMPKIN_STEM:
            case MELON_STEM:
            case PUMPKIN_STEM:

            case RAIL:
            case COMPARATOR:
            case REDSTONE_TORCH:
            case REDSTONE_WALL_TORCH:
            case REDSTONE_WIRE:
            case RED_MUSHROOM:

            case ACACIA_SAPLING:
            case BAMBOO_SAPLING:
            case BIRCH_SAPLING:
            case JUNGLE_SAPLING:
            case OAK_SAPLING:
            case SPRUCE_SAPLING:
            case DARK_OAK_SAPLING:

            case ACACIA_SIGN:
            case BIRCH_SIGN:
            case JUNGLE_SIGN:
            case OAK_SIGN:
            case SPRUCE_SIGN:
            case DARK_OAK_SIGN:

            case ACACIA_WALL_SIGN:
            case BIRCH_WALL_SIGN:
            case JUNGLE_WALL_SIGN:
            case OAK_WALL_SIGN:
            case SPRUCE_WALL_SIGN:
            case DARK_OAK_WALL_SIGN:

            case SKELETON_SKULL:
            case WITHER_SKELETON_SKULL:
            case CREEPER_HEAD:
            case DRAGON_HEAD:
            case PLAYER_HEAD:
            case ZOMBIE_HEAD:
            case SKELETON_WALL_SKULL:
            case WITHER_SKELETON_WALL_SKULL:
            case CREEPER_WALL_HEAD:
            case DRAGON_WALL_HEAD:
            case PLAYER_WALL_HEAD:
            case ZOMBIE_WALL_HEAD:

            case SNOW:
            case SNOW_BLOCK:

            case ACACIA_BUTTON:
            case BIRCH_BUTTON:
            case DARK_OAK_BUTTON:
            case JUNGLE_BUTTON:
            case OAK_BUTTON:
            case SPRUCE_BUTTON:
            case STONE_BUTTON:

            case BLACK_STAINED_GLASS_PANE:
            case BLUE_STAINED_GLASS_PANE:
            case PINK_STAINED_GLASS_PANE:
            case PURPLE_STAINED_GLASS_PANE:
            case BROWN_STAINED_GLASS_PANE:
            case CYAN_STAINED_GLASS_PANE:
            case GLASS_PANE:
            case GRAY_STAINED_GLASS_PANE:
            case GREEN_STAINED_GLASS_PANE:
            case LIGHT_BLUE_STAINED_GLASS_PANE:
            case LIGHT_GRAY_STAINED_GLASS_PANE:
            case LIME_STAINED_GLASS_PANE:
            case MAGENTA_STAINED_GLASS_PANE:
            case ORANGE_STAINED_GLASS_PANE:
            case RED_STAINED_GLASS_PANE:
            case WHITE_STAINED_GLASS_PANE:
            case YELLOW_STAINED_GLASS_PANE:

            case TORCH:
            case WALL_TORCH:
            case TRAPPED_CHEST:

            case ACACIA_TRAPDOOR:
            case BIRCH_TRAPDOOR:
            case DARK_OAK_TRAPDOOR:
            case JUNGLE_TRAPDOOR:
            case OAK_TRAPDOOR:
            case SPRUCE_TRAPDOOR:
            case IRON_TRAPDOOR:

            case TRIPWIRE:
            case TRIPWIRE_HOOK:
            case VINE:
            case WATER:
            case LILY_PAD:
            case COBWEB:

            case GRASS:
            case FERN:
            case SEAGRASS:
            case DANDELION:
            case POPPY:
            case BLUE_ORCHID:
            case ALLIUM:
            case AZURE_BLUET:
            case RED_TULIP:
            case ORANGE_TULIP:
            case WHITE_TULIP:
            case PINK_TULIP:
            case OXEYE_DAISY:
            case CORNFLOWER:
            case LILY_OF_THE_VALLEY:
            case WITHER_ROSE:

            case BLACK_BANNER:
            case BLUE_BANNER:
            case BROWN_BANNER:
            case CYAN_BANNER:
            case GRAY_BANNER:
            case GREEN_BANNER:
            case LIGHT_BLUE_BANNER:
            case LIGHT_GRAY_BANNER:
            case LIME_BANNER:
            case MAGENTA_BANNER:
            case ORANGE_BANNER:
            case PINK_BANNER:
            case PURPLE_BANNER:
            case RED_BANNER:
            case WHITE_BANNER:
            case YELLOW_BANNER:

            case BLACK_WALL_BANNER:
            case BLUE_WALL_BANNER:
            case BROWN_WALL_BANNER:
            case CYAN_WALL_BANNER:
            case GRAY_WALL_BANNER:
            case GREEN_WALL_BANNER:
            case LIGHT_BLUE_WALL_BANNER:
            case LIGHT_GRAY_WALL_BANNER:
            case LIME_WALL_BANNER:
            case MAGENTA_WALL_BANNER:
            case ORANGE_WALL_BANNER:
            case PINK_WALL_BANNER:
            case PURPLE_WALL_BANNER:
            case RED_WALL_BANNER:
            case WHITE_WALL_BANNER:
            case YELLOW_WALL_BANNER:

            case END_ROD:
            case FARMLAND:
            case CHORUS_PLANT:

            case CHIPPED_ANVIL:
            case DAMAGED_ANVIL:

            case BRAIN_CORAL:
            case BUBBLE_CORAL:
            case DEAD_BRAIN_CORAL:
            case DEAD_BUBBLE_CORAL:
            case DEAD_FIRE_CORAL:
            case DEAD_HORN_CORAL:
            case DEAD_TUBE_CORAL:
            case FIRE_CORAL:
            case HORN_CORAL:
            case TUBE_CORAL:

            case FIRE_CORAL_FAN:
            case FIRE_CORAL_WALL_FAN:
            case BRAIN_CORAL_FAN:
            case BUBBLE_CORAL_FAN:
            case HORN_CORAL_FAN:
            case TUBE_CORAL_FAN:
            case BRAIN_CORAL_WALL_FAN:
            case BUBBLE_CORAL_WALL_FAN:
            case DEAD_BRAIN_CORAL_FAN:
            case DEAD_BUBBLE_CORAL_FAN:
            case DEAD_FIRE_CORAL_FAN:
            case DEAD_HORN_CORAL_FAN:
            case DEAD_TUBE_CORAL_FAN:
            case HORN_CORAL_WALL_FAN:
            case TUBE_CORAL_WALL_FAN:
            case DEAD_BRAIN_CORAL_WALL_FAN:
            case DEAD_BUBBLE_CORAL_WALL_FAN:
            case DEAD_FIRE_CORAL_WALL_FAN:
            case DEAD_HORN_CORAL_WALL_FAN:
            case DEAD_TUBE_CORAL_WALL_FAN:

            case PAINTING:
            case LECTERN:
            case CONDUIT:

            case POTTED_ACACIA_SAPLING:
            case POTTED_ALLIUM:
            case POTTED_AZURE_BLUET:
            case POTTED_BAMBOO:
            case POTTED_BIRCH_SAPLING:
            case POTTED_BLUE_ORCHID:
            case POTTED_BROWN_MUSHROOM:
            case POTTED_CACTUS:
            case POTTED_CORNFLOWER:
            case POTTED_DANDELION:
            case POTTED_DARK_OAK_SAPLING:
            case POTTED_DEAD_BUSH:
            case POTTED_FERN:
            case POTTED_JUNGLE_SAPLING:
            case POTTED_LILY_OF_THE_VALLEY:
            case POTTED_OAK_SAPLING:
            case POTTED_ORANGE_TULIP:
            case POTTED_OXEYE_DAISY:
            case POTTED_PINK_TULIP:
            case POTTED_POPPY:
            case POTTED_RED_MUSHROOM:
            case POTTED_RED_TULIP:
            case POTTED_SPRUCE_SAPLING:
            case POTTED_WHITE_TULIP:
            case POTTED_WITHER_ROSE:

            case GRINDSTONE:
            case STONECUTTER:
            case BELL:
            case LANTERN:
            case CAMPFIRE:

            // 1.15
            case HONEY_BLOCK:

            // 1.16
            case CHAIN:
            case CRIMSON_FUNGUS:
            case WARPED_FUNGUS:
            case CRIMSON_ROOTS:
            case WARPED_ROOTS:
            case POTTED_CRIMSON_FUNGUS:
            case POTTED_CRIMSON_ROOTS:
            case POTTED_WARPED_FUNGUS:
            case POTTED_WARPED_ROOTS:
            case NETHER_SPROUTS:
            case SOUL_CAMPFIRE:
            case SOUL_FIRE:
            case SOUL_LANTERN:
            case SOUL_TORCH:
            case TWISTING_VINES:
            case WEEPING_VINES:
            case BLACKSTONE_WALL:
            case POLISHED_BLACKSTONE_BRICK_WALL:
            case POLISHED_BLACKSTONE_WALL:
            case CRIMSON_SIGN:
            case WARPED_SIGN:
            case WARPED_WALL_SIGN:
            case CRIMSON_WALL_SIGN:
            case CRIMSON_FENCE:
            case WARPED_FENCE:
            case CRIMSON_FENCE_GATE:
            case WARPED_FENCE_GATE:
            case CRIMSON_PRESSURE_PLATE:
            case POLISHED_BLACKSTONE_PRESSURE_PLATE:
            case WARPED_PRESSURE_PLATE:
            case CRIMSON_DOOR:
            case WARPED_DOOR:
            case CRIMSON_BUTTON:
            case POLISHED_BLACKSTONE_BUTTON:
            case WARPED_BUTTON:
            case CRIMSON_TRAPDOOR:
            case WARPED_TRAPDOOR:

                canPlaceRail = false;
                break;
            default:
                break;
        }

        return canPlaceRail;
    }
}
