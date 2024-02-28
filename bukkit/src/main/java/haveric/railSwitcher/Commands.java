package haveric.railSwitcher;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class Commands implements CommandExecutor {

    private RailSwitcher plugin;

    private static String cmdMain = "railswitcher";
    private String cmdMainAlt = "rs";
    private String cmdHelp = "help";
    private String cmdReload = "reload";
    private String cmdPerms = "perms";
    private String cmdPermsAlt = "perm";

    public Commands(RailSwitcher rs) {
        plugin = rs;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String commandLabel, String[] args) {
        ChatColor msgColor = ChatColor.DARK_AQUA;
        ChatColor highlightColor = ChatColor.YELLOW;

        String title = msgColor + "[" + ChatColor.GRAY + plugin.getDescription().getName() + msgColor + "] ";

        boolean op = false;
        if (sender.isOp()) {
            op = true;
        }

        boolean hasAdminPerm = false;
        if (sender instanceof Player player) {
            hasAdminPerm = Perms.hasAdmin(player);
        }

        if (commandLabel.equalsIgnoreCase(cmdMain) || commandLabel.equalsIgnoreCase(cmdMainAlt)) {
            if (args.length == 0 || (args.length == 1 && args[0].equalsIgnoreCase(cmdHelp))) {
                sender.sendMessage(title + "github.com/haveric/RailSwitcher - v" + plugin.getDescription().getVersion());

                if (op || hasAdminPerm) {
                    sender.sendMessage("/" + cmdMain + " " + cmdReload + " - " + msgColor + "Reloads the config files");
                }
            } else if (args.length == 1 && args[0].equalsIgnoreCase(cmdReload)) {
                if (op || hasAdminPerm) {
                    Config.reload();
                    sender.sendMessage(title + "Configuration files reloaded.");
                } else {
                    sender.sendMessage(title + "You do not have permission to reload the config files.");
                }
            } else if (args.length == 1 && (args[0].equalsIgnoreCase(cmdPerms) || args[0].equalsIgnoreCase(cmdPermsAlt))) {
                if (op || hasAdminPerm) {
                    sender.sendMessage(title + "Permission nodes:");
                    sender.sendMessage(Perms.getPermSwitch() + " - " + msgColor + "Sets all swap and rotate permissions to true");
                    sender.sendMessage(Perms.getPermSwap() + " - " + msgColor + "Allows swapping of rail types.");
                    sender.sendMessage(Perms.getPermRotateByTool() + " - " + msgColor + "Allows rotating rails with " + highlightColor + Config.getRotateTool());
                    sender.sendMessage(Perms.getPermRotateByRail() + " - " + msgColor + "Allows rotating rails with the same rail type");
                    sender.sendMessage(Perms.getPermAdmin() + " - " + msgColor + "Allows use of admin commands.");
                } else {
                    sender.sendMessage(title + ChatColor.RED + "You must be an op or have admin perms to see permission nodes.");
                }
            }
        }
        return false;
    }

    public static String getMain() {
        return cmdMain;
    }
}
