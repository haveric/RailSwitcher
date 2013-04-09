package haveric.railSwitcher.guard;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;

import com.palmergames.bukkit.towny.Towny;
import com.palmergames.bukkit.towny.object.TownyPermission.ActionType;
import com.palmergames.bukkit.towny.utils.PlayerCacheUtil;
import com.sk89q.worldguard.bukkit.WorldGuardPlugin;

public class Guard {

    private static WorldGuardPlugin worldGuard = null;
    private static Towny towny = null;

    public static void setWorldGuard(WorldGuardPlugin newWorldGuard) {
        worldGuard = newWorldGuard;
    }

    private static boolean worldGuardEnabled() {
        return (worldGuard != null);
    }

    public static void setTowny(Towny newTowny) {
        towny = newTowny;
    }

    private static boolean townyEnabled() {
        return (towny != null);
    }

    public static boolean canPlace(Player player, Location location) {
        boolean canPlace = true;

        if (worldGuardEnabled()) {
            canPlace = worldGuard.canBuild(player, location);
        }

        if (canPlace && townyEnabled()) {
            int railTypeId = 1; // Default to Stone
            byte railData = 0;
            Material item = player.getItemInHand().getType();

            switch (item) {
                case RAILS:
                    railTypeId = 66;
                    break;
                case POWERED_RAIL:
                    railTypeId = 27;
                    break;
                case DETECTOR_RAIL:
                    railTypeId = 28;
                    break;
            }
            canPlace = PlayerCacheUtil.getCachePermission(player, location, railTypeId, railData, ActionType.BUILD);
        }

        return canPlace;
    }
}
