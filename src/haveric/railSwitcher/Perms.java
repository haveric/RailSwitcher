package haveric.railSwitcher;

import org.bukkit.entity.Player;

public class Perms {

    private static String permSwitch = "railswitcher.switch";
    private static String permSwap = "railswitcher.swap";
    private static String permRotateByTool = "railswitcher.rotate.tool";
    private static String permRotateByRail = "railswitcher.rotate.rail";
    private static String permAdmin = "railswitcher.admin";

    public static boolean canSwap(Player player) {
        return player.hasPermission(permSwap);
    }

    public static boolean canRotateByTool(Player player) {
        return player.hasPermission(permRotateByTool);
    }

    public static boolean canRotateByRail(Player player) {
        return player.hasPermission(permRotateByRail);
    }

    public static boolean hasAdmin(Player player) {
        return player.hasPermission(permAdmin);
    }

    public static String getPermSwitch() {
        return permSwitch;
    }

    public static String getPermSwap() {
        return permSwap;
    }

    public static String getPermAdmin() {
        return permAdmin;
    }

    public static String getPermRotateByTool() {
        return permRotateByTool;
    }

    public static String getPermRotateByRail() {
        return permRotateByRail;
    }
}
