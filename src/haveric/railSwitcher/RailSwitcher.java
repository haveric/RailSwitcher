package haveric.railSwitcher;

import haveric.railSwitcher.blockLogger.BlockLogger;
import haveric.railSwitcher.fileWriter.CustomFileWriter;
import haveric.railSwitcher.guard.Guard;
import haveric.railSwitcher.mcstats.Metrics;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import net.coreprotect.CoreProtect;
import net.coreprotect.CoreProtectAPI;

import org.bukkit.Material;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import com.palmergames.bukkit.towny.Towny;
import com.sk89q.worldguard.bukkit.WorldGuardPlugin;

import de.diddiz.LogBlock.Consumer;
import de.diddiz.LogBlock.LogBlock;

public class RailSwitcher extends JavaPlugin {
    public Logger log;

    private Commands commands = new Commands(this);

    private static final int BLOCKS_VERSION = 7;

    private CustomFileWriter fileWriter;
    private List<Material> listOfMaterials;

    private Metrics metrics;

    @Override
    public void onEnable() {
        log = getLogger();

        PluginManager pm = getServer().getPluginManager();
        pm.registerEvents(new RSPlayerInteract(this), this);

        fileWriter = new CustomFileWriter(this, "Blocks");
        reload();

        // WorldGuard
        setupWorldGuard(pm);
        // Towny
        setupTowny(pm);
        // CoreProtect
        setupCoreProtect(pm);
        // LogBlock
        setupLogBlock(pm);

        getCommand(Commands.getMain()).setExecutor(commands);

        setupMetrics();
    }

    @Override
    public void onDisable() {

    }

    private void setupWorldGuard(PluginManager pm) {
        Plugin worldGuard = pm.getPlugin("WorldGuard");
        if (worldGuard == null || !(worldGuard instanceof WorldGuardPlugin)) {
            log.info("WorldGuard not found.");
        } else {
            Guard.setWorldGuard((WorldGuardPlugin) worldGuard);
        }
    }

    private void setupTowny(PluginManager pm) {
        Plugin towny = pm.getPlugin("Towny");
        if (towny ==  null || !(towny instanceof Towny)) {
            log.info("Towny not found.");
        } else {
            Guard.setTowny((Towny) towny);
        }
    }

    private void setupCoreProtect(PluginManager pm) {
        Plugin coreProtect = pm.getPlugin("CoreProtect");
        if (coreProtect == null || !(coreProtect instanceof CoreProtect)) {
            // CoreProtect not found
        } else {
            CoreProtectAPI coreProtectAPI = ((CoreProtect) coreProtect).getAPI();
            BlockLogger.setCoreProtectAPI(coreProtectAPI);
        }
    }

    private void setupLogBlock(PluginManager pm) {
        Plugin logBlock = pm.getPlugin("LogBlock");
        if (logBlock == null || !(logBlock instanceof LogBlock)) {
            // LogBlock not found
        } else {
            Consumer logBlockConsumer = ((LogBlock) logBlock).getConsumer();
            BlockLogger.setLogBlockConsumer(logBlockConsumer);
        }
    }

    private void setupMetrics() {
        try {
            metrics = new Metrics(this);

            metrics.start();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void reload() {
        loadDefaultList();
        fileWriter.reloadFiles(BLOCKS_VERSION, listOfMaterials);
        listOfMaterials = fileWriter.getMatList();
    }

    private void loadDefaultList() {
        listOfMaterials = new ArrayList<Material>();
        listOfMaterials.add(Material.ACTIVATOR_RAIL);
        listOfMaterials.add(Material.AIR);
        listOfMaterials.add(Material.ANVIL);
        listOfMaterials.add(Material.BEACON);
        listOfMaterials.add(Material.BED);
        listOfMaterials.add(Material.BED_BLOCK);
        listOfMaterials.add(Material.BIRCH_WOOD_STAIRS);
        listOfMaterials.add(Material.BREWING_STAND);
        listOfMaterials.add(Material.BRICK_STAIRS);
        listOfMaterials.add(Material.BROWN_MUSHROOM);
        listOfMaterials.add(Material.CACTUS);
        listOfMaterials.add(Material.CAKE_BLOCK);
        listOfMaterials.add(Material.CARPET);
        listOfMaterials.add(Material.CAULDRON);
        listOfMaterials.add(Material.CHEST);
        listOfMaterials.add(Material.COBBLE_WALL);
        listOfMaterials.add(Material.COBBLESTONE_STAIRS);
        listOfMaterials.add(Material.CROPS);
        listOfMaterials.add(Material.DAYLIGHT_DETECTOR);
        listOfMaterials.add(Material.DEAD_BUSH);
        listOfMaterials.add(Material.DETECTOR_RAIL);
        listOfMaterials.add(Material.DRAGON_EGG);
        listOfMaterials.add(Material.DIODE_BLOCK_OFF);
        listOfMaterials.add(Material.DIODE_BLOCK_ON);
        listOfMaterials.add(Material.ENCHANTMENT_TABLE);
        listOfMaterials.add(Material.ENDER_CHEST);
        listOfMaterials.add(Material.FENCE);
        listOfMaterials.add(Material.FENCE_GATE);
        listOfMaterials.add(Material.FIRE);
        listOfMaterials.add(Material.FLOWER_POT);
        listOfMaterials.add(Material.GLASS);
        listOfMaterials.add(Material.GLOWSTONE);
        listOfMaterials.add(Material.GOLD_PLATE);
        listOfMaterials.add(Material.ICE);
        listOfMaterials.add(Material.IRON_FENCE);
        listOfMaterials.add(Material.IRON_PLATE);
        listOfMaterials.add(Material.JUNGLE_WOOD_STAIRS);
        listOfMaterials.add(Material.LADDER);
        listOfMaterials.add(Material.LAVA);
        listOfMaterials.add(Material.LEAVES);
        listOfMaterials.add(Material.LEVER);
        listOfMaterials.add(Material.LOCKED_CHEST);
        listOfMaterials.add(Material.LONG_GRASS);
        listOfMaterials.add(Material.NETHER_BRICK_STAIRS);
        listOfMaterials.add(Material.NETHER_FENCE);
        listOfMaterials.add(Material.NETHER_STALK);
        listOfMaterials.add(Material.NETHER_WARTS);
        listOfMaterials.add(Material.PISTON_BASE);
        listOfMaterials.add(Material.PISTON_EXTENSION);
        listOfMaterials.add(Material.PISTON_MOVING_PIECE);
        listOfMaterials.add(Material.PISTON_STICKY_BASE);
        listOfMaterials.add(Material.POWERED_RAIL);
        listOfMaterials.add(Material.PUMPKIN_STEM);
        listOfMaterials.add(Material.QUARTZ_STAIRS);
        listOfMaterials.add(Material.RAILS);
        listOfMaterials.add(Material.REDSTONE_COMPARATOR_OFF);
        listOfMaterials.add(Material.REDSTONE_COMPARATOR_ON);
        listOfMaterials.add(Material.REDSTONE_TORCH_OFF);
        listOfMaterials.add(Material.REDSTONE_TORCH_ON);
        listOfMaterials.add(Material.REDSTONE_WIRE);
        listOfMaterials.add(Material.RED_MUSHROOM);
        listOfMaterials.add(Material.SANDSTONE_STAIRS);
        listOfMaterials.add(Material.SAPLING);
        listOfMaterials.add(Material.SIGN);
        listOfMaterials.add(Material.SIGN_POST);
        listOfMaterials.add(Material.SKULL);
        listOfMaterials.add(Material.SMOOTH_STAIRS);
        listOfMaterials.add(Material.SNOW);
        listOfMaterials.add(Material.SPRUCE_WOOD_STAIRS);
        listOfMaterials.add(Material.STATIONARY_LAVA);
        listOfMaterials.add(Material.STATIONARY_WATER);
        listOfMaterials.add(Material.STEP);
        listOfMaterials.add(Material.STONE_BUTTON);
        listOfMaterials.add(Material.STONE_PLATE);
        listOfMaterials.add(Material.SUGAR_CANE_BLOCK);
        listOfMaterials.add(Material.THIN_GLASS);
        listOfMaterials.add(Material.TNT);
        listOfMaterials.add(Material.TORCH);
        listOfMaterials.add(Material.TRAPPED_CHEST);
        listOfMaterials.add(Material.TRAP_DOOR);
        listOfMaterials.add(Material.TRIPWIRE);
        listOfMaterials.add(Material.TRIPWIRE_HOOK);
        listOfMaterials.add(Material.VINE);
        listOfMaterials.add(Material.WATER);
        listOfMaterials.add(Material.WATER_LILY);
        listOfMaterials.add(Material.WEB);
        listOfMaterials.add(Material.WHEAT);
        listOfMaterials.add(Material.WOOD_BUTTON);
        listOfMaterials.add(Material.WOOD_PLATE);
        listOfMaterials.add(Material.WOOD_STAIRS);
        listOfMaterials.add(Material.WOOD_STEP);
        listOfMaterials.add(Material.YELLOW_FLOWER);
    }

    public List<Material> getMaterials() {
        return listOfMaterials;
    }
}
