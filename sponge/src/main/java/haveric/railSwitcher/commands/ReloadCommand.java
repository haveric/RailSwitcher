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

public class ReloadCommand implements CommandExecutor {
    private RailSwitcher plugin;

    public ReloadCommand(RailSwitcher plugin) {
        this.plugin = plugin;
    }

    @Override
    public CommandResult execute(CommandSource src, CommandContext args) throws CommandException {
        TextColor msgColor = TextColors.DARK_AQUA;

        Text.Builder title = Text.builder("[").color(msgColor).append(Text.builder(plugin.getName()).color(TextColors.GRAY).build()).append(Text.of("]"));

        if (Perms.hasAdmin(src)) {
            //Config.reload(); TODO: Reload configuration
            src.sendMessage(title.append(Text.of("Configuration files reloaded.")).build());
        } else {
            src.sendMessage(title.append(Text.of(Text.builder("You do no have permission to reload the config files.").color(TextColors.RED).build())).build());
        }

        return CommandResult.success();
    }
}
