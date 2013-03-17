package haveric.railSwitcher;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class Commands implements CommandExecutor {

    RailSwitcher plugin;

    private static String cmdMain = "railswitcher";
    private static String cmdHelp = "help";
    private static String cmdReload = "reload";

    public Commands(RailSwitcher rs) {
        plugin = rs;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String commandLabel, String[] args) {
        ChatColor msgColor = ChatColor.DARK_AQUA;

        String title = msgColor + "[" + ChatColor.GRAY + plugin.getDescription().getName() + msgColor + "] ";

        boolean op = false;
        if (sender.isOp()) {
            op = true;
        }

        boolean canAdjust = false;
        if (sender instanceof Player) {
            Player player = (Player) sender;
            canAdjust = Perms.canAdjust(player);
        }

        if (commandLabel.equalsIgnoreCase(cmdMain)) {
            if (args.length == 0 || (args.length == 1 && args[0].equalsIgnoreCase(cmdHelp))) {
                sender.sendMessage(title + "github.com/haveric/RailSwitcher - v" + plugin.getDescription().getVersion());

                if (op || canAdjust) {
                    sender.sendMessage("/" + cmdMain + " " + cmdReload + " - " + msgColor + "Reloads the config files");
                }
            } else if (args.length == 1 && args[0].equalsIgnoreCase(cmdReload)) {
                if (op || canAdjust) {
                    plugin.reload();
                    sender.sendMessage(title + "Configuration files reloaded.");
                }
            }
        }
        return false;
    }

    public static String getMain() {
        return cmdMain;
    }
}
