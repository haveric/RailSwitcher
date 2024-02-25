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
        return material.isSolid();
    }
}
