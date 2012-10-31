package haveric.railSwitcher;

import haveric.railSwitcher.mcstats.Metrics;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.logging.Logger;

import org.bukkit.Material;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import com.sk89q.worldguard.bukkit.WorldGuardPlugin;

public class RailSwitcher extends JavaPlugin {
    private static final Logger log = Logger.getLogger("Minecraft");
    private final RSPlayerInteract playerInteract = new RSPlayerInteract(this);

    private Commands commands = new Commands(this);

    private static final int BLOCKS_VERSION = 4;
    private File defaultBlocks;
    private File customBlocks;

    private ArrayList<Material> listOfMaterials;

    Metrics metrics;

    @Override
    public void onEnable() {
        PluginManager pm = getServer().getPluginManager();
        pm.registerEvents(playerInteract, this);

        if (!getDataFolder().exists()) {
            getDataFolder().mkdir();
        }
        defaultBlocks = new File(getDataFolder() + "/defaultBlocks.txt");
        customBlocks = new File(getDataFolder() + "/customBlocks.txt");
        createFiles();

        // WorldGuard
        setupWorldGuard(pm);

        getCommand(Commands.getMain()).setExecutor(commands);

        setupMetrics();
    }

    @Override
    public void onDisable() {

    }

    private void setupWorldGuard(PluginManager pm) {
        Plugin worldGuard = pm.getPlugin("WorldGuard");
        if (worldGuard == null || !(worldGuard instanceof WorldGuardPlugin)) {
            log.info(String.format("[%s] WorldGuard not found.", getDescription().getName()));
        } else {
            Guard.setWorldGuard((WorldGuardPlugin) worldGuard);
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

    private void createFiles() {
        if (!defaultBlocks.exists()) {
            try {
                defaultBlocks.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        if (!customBlocks.exists()) {
            try {
                customBlocks.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        try {
            Scanner sc = new Scanner(defaultBlocks);

            if (defaultBlocks.length() > 0) {
                sc.next();
                int fileVersion = sc.nextInt();
                if (fileVersion < BLOCKS_VERSION) {
                    defaultBlocks.delete();
                    defaultBlocks = new File(getDataFolder() + "/defaultBlocks.txt");
                    writeBlocks(defaultBlocks, true);
                }
            } else {
                writeBlocks(defaultBlocks, true);
            }
            sc.close();

            Scanner sc2 = new Scanner(defaultBlocks);
            listOfMaterials = new ArrayList<Material>();
            sc2.next();
            sc2.nextInt();
            while (sc2.hasNextLine()) {
                listOfMaterials.add(Material.getMaterial(sc2.nextLine()));
            }

            sc2.close();
        } catch (FileNotFoundException e) {
            log.warning(String.format("[%s] defaultBlocks.txt not found." , getDescription().getName()));
            e.printStackTrace();
        }

        try {
            Scanner sc3 = new Scanner(customBlocks);

            if (customBlocks.length() > 0) {
                listOfMaterials = new ArrayList<Material>();
                while (sc3.hasNextLine()) {
                    listOfMaterials.add(Material.getMaterial(sc3.nextLine()));
                }
            }

            sc3.close();
        } catch (FileNotFoundException e) {
            log.warning(String.format("[%s] customBlocks.txt not found." , getDescription().getName()));
            e.printStackTrace();
        }
    }

    private void writeBlocks(File f, boolean vers) {
        try {
            FileWriter fstream = new FileWriter(f);
            PrintWriter out = new PrintWriter(fstream);
            if (vers) {
                out.println("Version: " + BLOCKS_VERSION);
            }

            out.println("AIR");
            out.println("ANVIL");
            out.println("BEACON");
            out.println("BED");
            out.println("BED_BLOCK");
            out.println("BEDROCK");
            out.println("BIRCH_WOOD_STAIRS");
            out.println("BREWING_STAND");
            out.println("BRICK_STAIRS");
            out.println("BROWN_MUSHROOM");
            out.println("CACTUS");
            out.println("CAKE_BLOCK");
            out.println("CAULDRON");
            out.println("CHEST");
            out.println("COBBLE_WALL");
            out.println("COBBLESTONE_STAIRS");
            out.println("CROPS");
            out.println("DEAD_BUSH");
            out.println("DETECTOR_RAIL");
            out.println("DISPENSER");
            out.println("DRAGON EGG");
            out.println("DIODE");
            out.println("ENCHANTMENT_TABLE");
            out.println("ENDER_CHEST");
            out.println("FENCE");
            out.println("FENCE_GATE");
            out.println("FIRE");
            out.println("FLOWER_POT");
            out.println("FURNACE");
            out.println("GLASS");
            out.println("ICE");
            out.println("JUNGLE_WOOD_STAIRS");
            out.println("LADDER");
            out.println("LAVA");
            out.println("LEAVES");
            out.println("LEVER");
            out.println("LOCKED_CHEST");
            out.println("LONG_GRASS");
            out.println("NETHER_FENCE");
            out.println("NETHER_STALK");
            out.println("NETHER_WARTS");
            out.println("PISTON_BASE");
            out.println("PISTON_EXTENSION");
            out.println("PISTON_MOVING_PIECE");
            out.println("PISTON_STICKY_BASE");
            out.println("POWERED_RAIL");
            out.println("PUMPKIN_STEM");
            out.println("RAILS");
            out.println("REDSTONE_TORCH_OFF");
            out.println("REDSTONE_TORCH_ON");
            out.println("REDSTONE_WIRE");
            out.println("RED_MUSHROOM");
            out.println("SIGN");
            out.println("SIGN_POST");
            out.println("SKULL");
            out.println("SMOOTH_STAIRS");
            out.println("SPRUCE_WOOD_STAIRS");
            out.println("STATIONARY_LAVA");
            out.println("STATIONARY_WATER");
            out.println("SUGAR_CANE_BLOCK");
            out.println("THIN_GLASS");
            out.println("TNT");
            out.println("TORCH");
            out.println("TRAP_DOOR");
            out.println("TRIPWIRE");
            out.println("TRIPWIRE_HOOK");
            out.println("TRAP_DOOR");
            out.println("WATER");
            out.println("WEB");
            out.println("WHEAT");
            out.println("WORKBENCH");
            out.println("WOOD_STAIRS");
            out.println("YELLOW_FLOWER");

            out.close();
            fstream.close();
        } catch (IOException e) {
            log.warning(String.format("[%s] File %s not found." , getDescription().getName(), f.getName()));
        }
    }

    public ArrayList<Material> getMaterials() {
        return listOfMaterials;
    }
}
