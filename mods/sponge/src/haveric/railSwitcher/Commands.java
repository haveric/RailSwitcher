package haveric.railSwitcher;

import java.util.ArrayList;
import java.util.List;

import org.spongepowered.api.text.format.TextColor;
import org.spongepowered.api.text.format.TextColors;
import org.spongepowered.api.text.message.Message;
import org.spongepowered.api.text.message.Messages;
import org.spongepowered.api.util.command.CommandCallable;
import org.spongepowered.api.util.command.CommandException;
import org.spongepowered.api.util.command.CommandSource;

import com.google.common.base.Optional;

public class Commands {

    private RailSwitcher plugin;

    public Commands(RailSwitcher railSwitcher) {
        plugin = railSwitcher;

        registerBaseCommand();
    }

    private void registerBaseCommand() {
        final TextColor msgColor = TextColors.DARK_AQUA;

        final Message title = Messages.builder("[").color(msgColor).append(
                Messages.builder(plugin.getName()).color(TextColors.GRAY).build()).append(
                Messages.builder("] ").color(msgColor).build())
            .build();

        CommandCallable callable = new CommandCallable() {

            @Override
            public List<String> getSuggestions(CommandSource source, String arguments) throws CommandException {
                return new ArrayList<String>();
            }

            @Override
            public boolean call(CommandSource source, String arguments, List<String> parents) throws CommandException {
                Message infoLine = Messages.builder("github.com/haveric/RailSwitcher - v" + plugin.getVersion()).color(msgColor).build();

                source.sendMessage(Messages.builder().append(title, infoLine).build());


                return true;
            }

            @Override
            public boolean testPermission(CommandSource source) {

                return false;
            }

            @Override
            public Optional<String> getShortDescription() {
                return Optional.absent();
            }

            @Override
            public Optional<String> getHelp() {

                return Optional.absent();
            }

            @Override
            public String getUsage() {
                return "";
            }
        };

        List<String> aliases = new ArrayList<String>();
        aliases.add("railswitcher");
        aliases.add("rs");

        plugin.getGame().getCommandDispatcher().register(plugin, callable, aliases);
    }
}
