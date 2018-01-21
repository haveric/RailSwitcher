package haveric.railSwitcher;

import com.google.inject.Inject;
import haveric.railSwitcher.commands.Commands;
import org.slf4j.Logger;
import org.spongepowered.api.Game;
import org.spongepowered.api.event.EventManager;
import org.spongepowered.api.event.Listener;
import org.spongepowered.api.event.game.state.GameStartingServerEvent;
import org.spongepowered.api.event.game.state.GameStoppingServerEvent;
import org.spongepowered.api.plugin.Plugin;
import org.spongepowered.api.plugin.PluginContainer;
import org.spongepowered.api.plugin.PluginManager;

import java.util.Optional;

@Plugin(id = "railswitcher", name = "Rail Switcher", version = "2.0",
        description = "Switch Rails",
        authors = {"haveric"})
public class RailSwitcher {
    @Inject
    private Game game;
    @Inject
    private PluginManager pm;
    @Inject
    private Logger logger;
    @Inject
    private EventManager em;
    @Inject
    private PluginContainer container;
    @SuppressWarnings("unused")
    private Commands commands;
    private Config config;

    @Listener
    public void onStartup(GameStartingServerEvent event) {
        em.registerListeners(this, new RSPlayerInteract(this));

        commands = new Commands(this);
        config = new Config(this);
    }

    @Listener
    public void onShutdown(GameStoppingServerEvent event) {

    }

    public Game getGame() {
        return game;
    }

    public Logger getLogger() {
        return logger;
    }

    public PluginContainer getContainer() {
        return container;
    }

    public String getName() {

        return container.getName();
    }

    public String getVersion() {
        String version = "";
        Optional<String> optVersion = container.getVersion();
        if (optVersion.isPresent()) {
            version = optVersion.get();
        }
        return version;
    }
}
