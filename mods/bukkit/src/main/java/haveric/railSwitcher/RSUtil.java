package haveric.railSwitcher;

import org.bukkit.Material;
import org.bukkit.block.Block;

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
        return canPlaceRail(block.getType());
    }

    public static boolean canPlaceRail(Material material) {
        boolean canPlaceRail = true;

        switch (material) {
            case AIR:
                canPlaceRail = false;
                break;
            default:
                break;
        }

        return canPlaceRail;
    }
}
