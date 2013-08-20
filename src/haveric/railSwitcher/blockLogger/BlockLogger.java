package haveric.railSwitcher.blockLogger;

import net.coreprotect.CoreProtectAPI;

import org.bukkit.Location;
import org.bukkit.block.BlockState;

import de.diddiz.LogBlock.Consumer;

public class BlockLogger {

    private static CoreProtectAPI coreProtectAPI;
    private static Consumer logBlockConsumer;


    private static boolean coreProtectEnabled() {
        boolean enabled = false;
        if (coreProtectAPI != null && coreProtectAPI.isEnabled() && coreProtectAPI.APIVersion() >= 2) {
            enabled = true;
        }

        return enabled;
    }

    public static void setCoreProtectAPI(CoreProtectAPI newCoreProtectAPI) {
        coreProtectAPI = newCoreProtectAPI;
    }

    private static boolean logBlockEnabled() {
        return logBlockConsumer != null;
    }

    public static void setLogBlockConsumer(Consumer newLogBlockConsumer) {
        logBlockConsumer = newLogBlockConsumer;
    }

    public static void logBlock(String player, BlockState oldState, BlockState newState) {
        Location location = newState.getLocation();
        int newId = newState.getTypeId();
        byte newData = newState.getData().getData();

        if (coreProtectEnabled()) {
            coreProtectAPI.logPlacement(player, location, newId, newData);
        }

        if (logBlockEnabled()) {
            logBlockConsumer.queueBlockReplace(player, oldState, newState);
        }
    }
}
