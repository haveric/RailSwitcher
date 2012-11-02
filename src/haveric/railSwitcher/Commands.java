package haveric.railSwitcher;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class Commands implements CommandExecutor {

    RailSwitcher plugin;

    private static String cmdMain = "railswitcher";
    private static String cmdHelp = "help";

    public Commands(RailSwitcher rs) {
        plugin = rs;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String commandLabel, String[] args) {
        ChatColor msgColor = ChatColor.DARK_AQUA;
        //ChatColor highlightColor = ChatColor.YELLOW;

        String title = msgColor + "[" + ChatColor.GRAY + plugin.getDescription().getName() + msgColor + "] ";

        if (commandLabel.equalsIgnoreCase(cmdMain)) {
            if (args.length == 0 || (args.length == 1 && args[0].equalsIgnoreCase(cmdHelp))) {
                sender.sendMessage(title + "github.com/haveric/RailSwitcher - v" + plugin.getDescription().getVersion());
            }
        }
        return false;
    }

    public static String getMain() {
        return cmdMain;
    }
}
