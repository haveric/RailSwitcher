package haveric.railSwitcher;

import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.logging.Logger;

public class RailSwitcher extends JavaPlugin {
    public Logger log;

    private Commands commands = new Commands(this);

    @Override
    public void onEnable() {
        log = getLogger();

        Supports.init();

        PluginManager pm = getServer().getPluginManager();
        pm.registerEvents(new RSPlayerInteract(this), this);

        Config.init(this);
        Config.setup();

        getCommand(Commands.getMain()).setExecutor(commands);
    }
}