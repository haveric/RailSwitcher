package haveric.railSwitcher.commands;

import haveric.railSwitcher.RailSwitcher;
import org.spongepowered.api.Sponge;
import org.spongepowered.api.command.spec.CommandSpec;
import org.spongepowered.api.text.Text;

public class Commands {
    public static String CMD_MAIN = "railswitcher";
    public static String CMD_MAIN_ALT = "rs";
    public static String CMD_HELP = "help";
    public static String CMD_RELOAD = "reload";
    public static String CMD_PERMS = "perms";
    public static String CMD_PERMS_ALT = "perm";

    private RailSwitcher plugin;

    public Commands(RailSwitcher railSwitcher) {
        plugin = railSwitcher;

        registerBaseCommand();
    }

    private void registerBaseCommand() {
        CommandSpec helpCommand = CommandSpec.builder()
                .description(Text.of("RailSwitcher Help"))
                .executor(new HelpCommand(plugin))
                .build();

        CommandSpec reloadCommand = CommandSpec.builder()
                .description(Text.of("Reloads the configuration"))
                .executor(new ReloadCommand(plugin))
                .build();

        CommandSpec permsCommand = CommandSpec.builder()
                .description(Text.of("List plugin permissions"))
                .executor(new PermsCommand(plugin))
                .build();

        CommandSpec baseCommandSpec = CommandSpec.builder()
                .description(Text.of("Base RailSwitcher command"))
                .executor(new HelpCommand(plugin))
                .child(helpCommand, CMD_HELP)
                .child(reloadCommand, CMD_RELOAD)
                .child(permsCommand, CMD_PERMS, CMD_PERMS_ALT)
                .build();

        Sponge.getCommandManager().register(plugin, baseCommandSpec, CMD_MAIN, CMD_MAIN_ALT);
    }
}
