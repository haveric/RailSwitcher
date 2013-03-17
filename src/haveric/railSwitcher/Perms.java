package haveric.railSwitcher;

import org.bukkit.entity.Player;

public class Perms {

    private static String permSwitch = "railswitcher.switch";
    private static String permAdjust = "railswitcher.adjust";

    public static boolean canSwitch(Player player) {
        return player.hasPermission(permSwitch);
    }

    public static boolean canAdjust(Player player) {
        return player.hasPermission(permAdjust);
    }
}
