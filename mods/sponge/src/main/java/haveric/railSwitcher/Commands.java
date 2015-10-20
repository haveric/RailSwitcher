package haveric.railSwitcher;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.spongepowered.api.text.Text;
import org.spongepowered.api.util.command.CommandCallable;
import org.spongepowered.api.util.command.CommandException;
import org.spongepowered.api.util.command.CommandResult;
import org.spongepowered.api.util.command.CommandSource;

public class Commands {

    private RailSwitcher plugin;

    public Commands(RailSwitcher railSwitcher) {
        plugin = railSwitcher;

        registerBaseCommand();
    }

    private void registerBaseCommand() {
        CommandCallable callable = new CommandCallable() {

            @Override
            public CommandResult process(CommandSource source, String arguments) throws CommandException {
                // TODO Auto-generated method stub
                return CommandResult.success();
            }

            @Override
            public List<String> getSuggestions(CommandSource source, String arguments) throws CommandException {
                // TODO Auto-generated method stub
                return null;
            }

            @Override
            public boolean testPermission(CommandSource source) {
                // TODO Auto-generated method stub
                return false;
            }

            @Override
            public Optional<Text> getShortDescription(CommandSource source) {
                // TODO Auto-generated method stub
                return null;
            }

            @Override
            public Optional<Text> getHelp(CommandSource source) {
                // TODO Auto-generated method stub
                return null;
            }

            @Override
            public Text getUsage(CommandSource source) {
                // TODO Auto-generated method stub
                return null;
            }

        };

        List<String> aliases = new ArrayList<String>();
        aliases.add("railswitcher");
        aliases.add("rs");

        plugin.getGame().getCommandDispatcher().register(plugin, callable, aliases);
    }
}
