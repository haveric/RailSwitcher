package haveric.railSwitcher;

import org.spongepowered.api.command.CommandSource;

public class Perms {
    private static String permSwitch = "railswitcher.switch";
    private static String permSwap = "railswitcher.swap";
    private static String permRotateByTool = "railswitcher.rotate.tool";
    private static String permRotateByRail = "railswitcher.rotate.rail";
    private static String permAdmin = "railswitcher.admin";

    public static boolean canSwap(CommandSource src) {
        return src.hasPermission(permSwap);
    }

    public static boolean canRotateByTool(CommandSource src) {
        return src.hasPermission(permRotateByTool);
    }

    public static boolean canRotateByRail(CommandSource src) {
        return src.hasPermission(permRotateByRail);
    }

    public static boolean hasAdmin(CommandSource src) {
        return src.hasPermission(permAdmin);
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
