package haveric.railSwitcher;

import org.bukkit.entity.Player;

public class Perms {

    private static String permSwitch = "railswitcher.switch";

    public static boolean canSwitch(Player player) {
        return player.hasPermission(permSwitch);
    }
}
