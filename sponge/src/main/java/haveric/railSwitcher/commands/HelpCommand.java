package haveric.railSwitcher.commands;

import haveric.railSwitcher.Perms;
import haveric.railSwitcher.RailSwitcher;
import org.spongepowered.api.command.CommandException;
import org.spongepowered.api.command.CommandResult;
import org.spongepowered.api.command.CommandSource;
import org.spongepowered.api.command.args.CommandContext;
import org.spongepowered.api.command.spec.CommandExecutor;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.text.format.TextColor;
import org.spongepowered.api.text.format.TextColors;

public class HelpCommand implements CommandExecutor {
    private RailSwitcher plugin;


    public HelpCommand(RailSwitcher plugin) {
        this.plugin = plugin;
    }

    @Override
    public CommandResult execute(CommandSource src, CommandContext args) throws CommandException {
        TextColor msgColor = TextColors.DARK_AQUA;

        Text help = Text.builder("[").color(msgColor).append(Text.builder(plugin.getName()).color(TextColors.GRAY).build()).append(Text.of("] - v" + plugin.getVersion())).build();
        src.sendMessage(help);

        if (Perms.hasAdmin(src)) {
            Text reload = Text.builder("/" + Commands.CMD_MAIN_ALT + " " + Commands.CMD_RELOAD + " - ").append(Text.builder("Reloads the config files").color(msgColor).build()).build();
            src.sendMessage(reload);

            Text perms = Text.builder("/" + Commands.CMD_MAIN_ALT + " " + Commands.CMD_PERMS + " - ").append(Text.builder("View Permissions").color(msgColor).build()).build();
            src.sendMessage(perms);
        }

        return CommandResult.success();
    }
}
