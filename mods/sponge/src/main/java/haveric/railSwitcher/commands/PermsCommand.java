package haveric.railSwitcher.commands;

import haveric.railSwitcher.Config;
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

public class PermsCommand implements CommandExecutor {
    private RailSwitcher plugin;

    public PermsCommand(RailSwitcher plugin) {
        this.plugin = plugin;
    }

    @Override
    public CommandResult execute(CommandSource src, CommandContext args) throws CommandException {
        TextColor msgColor = TextColors.DARK_AQUA;
        TextColor highlightColor = TextColors.YELLOW;

        Text.Builder title = Text.builder("[").color(msgColor).append(Text.builder(plugin.getName()).color(TextColors.GRAY).build()).append(Text.of("] "));

        if (Perms.hasAdmin(src)) {
            src.sendMessage(title.append(Text.of("Permission nodes:")).build());
            src.sendMessage(Text.builder(Perms.getPermSwitch() + " - ").append(Text.builder("Sets all swap and rotate permissions to true").color(msgColor).build()).build());
            src.sendMessage(Text.builder(Perms.getPermSwap() + " - ").append(Text.builder("Allows swapping of rail types").color(msgColor).build()).build());
            src.sendMessage(Text.builder(Perms.getPermRotateByTool() + " - ").append(Text.builder("Allows rotating rails with ").color(msgColor).build()).append(Text.builder(Config.getRotateTool()).color(highlightColor).build()).build());
            src.sendMessage(Text.builder(Perms.getPermRotateByRail() + " - ").append(Text.builder("Allows rotating rails with the same rail type").color(msgColor).build()).build());
            src.sendMessage(Text.builder(Perms.getPermAdmin() + " - ").append(Text.builder("Allows use of admin commands.").color(msgColor).build()).build());
        } else {
            src.sendMessage(title.append(Text.builder("You do no have permission to see permission nodes.").color(TextColors.RED).build()).build());
        }
        return CommandResult.success();
    }
}
