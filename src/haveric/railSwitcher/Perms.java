package haveric.railSwitcher;

import org.bukkit.entity.Player;

public class Perms {

    private static String permSwitch = "railswitcher.switch";
    private static String permAdmin = "railswitcher.admin";

    public static boolean canSwitch(Player player) {
        return player.hasPermission(permSwitch);
    }

    public static boolean hasAdmin(Player player) {
        return player.hasPermission(permAdmin);
    }

    public static String getPermSwitch() {
        return permSwitch;
    }

    public static String getPermAdmin() {
        return permAdmin;
    }
}
