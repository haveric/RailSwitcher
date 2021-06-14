package haveric.railSwitcher;

import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.data.BlockData;
import org.bukkit.block.data.type.*;

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

        String blockType = block.getType().toString().toUpperCase();
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
        } else if (blockData instanceof AmethystCluster) {
            canPlaceRail = false;
        } else if (blockData instanceof Bed) {
            canPlaceRail = false;
        } else if (blockData instanceof Campfire) {
            canPlaceRail = false;
        } else if (blockData instanceof Candle) {
            canPlaceRail = false;
        } else if (blockData instanceof Door) {
            canPlaceRail = false;
        } else if (blockData instanceof Fire) {
            canPlaceRail = false;
        } else if (blockData instanceof Fence) {
            canPlaceRail = false;
        } else if (blockData instanceof Gate) {
            canPlaceRail = false;
        } else if (blockData instanceof GlassPane) {
            canPlaceRail = false;
        } else if (blockData instanceof Sapling) {
            canPlaceRail = false;
        } else if (blockData instanceof Sign) {
            canPlaceRail = false;
        } else if (blockData instanceof Switch) { // buttons
            canPlaceRail = false;
        } else if (blockData instanceof TrapDoor) {
            canPlaceRail = false;
        } else if (blockData instanceof Wall) {
            canPlaceRail = false;
        } else if (blockData instanceof WallSign) {
            canPlaceRail = false;
        } else if (blockType.contains("PRESSURE_PLATE")) {
            canPlaceRail = false;
        } else if (blockType.contains("_CARPET")) {
            canPlaceRail = false;
        } else if (blockType.contains("POTTED_")) {
            canPlaceRail = false;
        } else if (blockType.contains("_BANNER")) {
            canPlaceRail = false;
        } else if (blockType.contains("_CORAL")) {
            canPlaceRail = false;
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

            case BREWING_STAND:
            case BROWN_MUSHROOM:
            case CAKE:

            case CHEST:

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

            case FLOWER_POT:

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

            case TORCH:
            case WALL_TORCH:
            case TRAPPED_CHEST:

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

            case END_ROD:
            case FARMLAND:
            case CHORUS_PLANT:

            case CHIPPED_ANVIL:
            case DAMAGED_ANVIL:

            case PAINTING:
            case LECTERN:
            case CONDUIT:

            case GRINDSTONE:
            case STONECUTTER:
            case BELL:
            case LANTERN:

            // 1.15
            case HONEY_BLOCK:

            // 1.16
            case CHAIN:
            case CRIMSON_FUNGUS:
            case CRIMSON_ROOTS:
            case NETHER_SPROUTS:
            case SOUL_LANTERN:
            case SOUL_TORCH:
            case TWISTING_VINES:
            case TWISTING_VINES_PLANT:
            case WARPED_FUNGUS:
            case WARPED_ROOTS:
            case WEEPING_VINES:
            case WEEPING_VINES_PLANT:

            // 1.17
            case BIG_DRIPLEAF:
            case BIG_DRIPLEAF_STEM:
            case CAVE_VINES_PLANT:
            case GLOW_LICHEN:
            case HANGING_ROOTS:
            case LIGHT:
            case LIGHTNING_ROD:
            case POINTED_DRIPSTONE:
            case POWDER_SNOW:
            case SCULK_SENSOR:
            case SMALL_DRIPLEAF:
            case SPORE_BLOSSOM:
                canPlaceRail = false;
                break;
            default:
                break;
        }

        return canPlaceRail;
    }
}
