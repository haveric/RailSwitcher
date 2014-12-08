package haveric.railSwitcher;

import haveric.railSwitcher.fileWriter.CustomFileWriter;
import haveric.railSwitcher.mcstats.Metrics;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import org.bukkit.Material;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

public class RailSwitcher extends JavaPlugin {
    public Logger log;

    private Commands commands = new Commands(this);

    public static final int ANY_DATA = Short.MAX_VALUE;
    private static final int BLOCKS_VERSION = 9;

    private CustomFileWriter fileWriter;
    private List<String> materialList;


    private Metrics metrics;

    @Override
    public void onEnable() {
        log = getLogger();

        PluginManager pm = getServer().getPluginManager();
        pm.registerEvents(new RSPlayerInteract(this), this);

        Config.init(this);
        Config.setup();

        fileWriter = new CustomFileWriter(this, "Blocks");
        reload();

        getCommand(Commands.getMain()).setExecutor(commands);

        setupMetrics();
    }

    @Override
    public void onDisable() {

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
        fileWriter.reloadFiles(BLOCKS_VERSION, materialList);
        materialList = fileWriter.getMatList();
    }

    private void loadDefaultList() {
        materialList = new ArrayList<String>();
        try {
            String acaciaStairs = Material.ACACIA_STAIRS.name();
            materialList.add(acaciaStairs + ":0");
            materialList.add(acaciaStairs + ":1");
            materialList.add(acaciaStairs + ":2");
            materialList.add(acaciaStairs + ":3");
            materialList.add(Material.ACTIVATOR_RAIL.name());
            materialList.add(Material.AIR.name());
            materialList.add(Material.ANVIL.name());
            materialList.add(Material.BEACON.name());
            materialList.add(Material.BED.name());
            materialList.add(Material.BED_BLOCK.name());
            String birchStairs = Material.BIRCH_WOOD_STAIRS.name();
            materialList.add(birchStairs + ":0");
            materialList.add(birchStairs + ":1");
            materialList.add(birchStairs + ":2");
            materialList.add(birchStairs + ":3");
            materialList.add(Material.BREWING_STAND.name());
            String brickStairs = Material.BRICK_STAIRS.name();
            materialList.add(brickStairs + ":0");
            materialList.add(brickStairs + ":1");
            materialList.add(brickStairs + ":2");
            materialList.add(brickStairs + ":3");
            materialList.add(Material.BROWN_MUSHROOM.name());
            materialList.add(Material.CACTUS.name());
            materialList.add(Material.CAKE_BLOCK.name());
            materialList.add(Material.CARPET.name());
            materialList.add(Material.CAULDRON.name());
            materialList.add(Material.CHEST.name());
            materialList.add(Material.COBBLE_WALL.name());
            String cobbleStairs = Material.COBBLESTONE_STAIRS.name();
            materialList.add(cobbleStairs + ":0");
            materialList.add(cobbleStairs + ":1");
            materialList.add(cobbleStairs + ":2");
            materialList.add(cobbleStairs + ":3");
            materialList.add(Material.CROPS.name());
            String darkOakStairs = Material.DARK_OAK_STAIRS.name();
            materialList.add(darkOakStairs + ":0");
            materialList.add(darkOakStairs + ":1");
            materialList.add(darkOakStairs + ":2");
            materialList.add(darkOakStairs + ":3");
            materialList.add(Material.DAYLIGHT_DETECTOR.name());
            materialList.add(Material.DEAD_BUSH.name());
            materialList.add(Material.DETECTOR_RAIL.name());
            materialList.add(Material.DRAGON_EGG.name());
            materialList.add(Material.DIODE_BLOCK_OFF.name());
            materialList.add(Material.DIODE_BLOCK_ON.name());
            materialList.add(Material.DOUBLE_PLANT.name());
            materialList.add(Material.ENCHANTMENT_TABLE.name());
            materialList.add(Material.ENDER_CHEST.name());
            materialList.add(Material.FENCE.name());
            materialList.add(Material.FENCE_GATE.name());
            materialList.add(Material.FIRE.name());
            materialList.add(Material.FLOWER_POT.name());
            materialList.add(Material.GLASS.name());
            materialList.add(Material.GLOWSTONE.name());
            materialList.add(Material.GOLD_PLATE.name());
            materialList.add(Material.ICE.name());
            materialList.add(Material.IRON_DOOR_BLOCK.name());
            materialList.add(Material.IRON_FENCE.name());
            materialList.add(Material.IRON_PLATE.name());
            String jungleStairs = Material.JUNGLE_WOOD_STAIRS.name();
            materialList.add(jungleStairs + ":0");
            materialList.add(jungleStairs + ":1");
            materialList.add(jungleStairs + ":2");
            materialList.add(jungleStairs + ":3");
            materialList.add(Material.LADDER.name());
            materialList.add(Material.LAVA.name());
            materialList.add(Material.LEAVES.name());
            materialList.add(Material.LEAVES_2.name());
            materialList.add(Material.LEVER.name());
            materialList.add(Material.LONG_GRASS.name());
            String netherStairs = Material.NETHER_BRICK_STAIRS.name();
            materialList.add(netherStairs + ":0");
            materialList.add(netherStairs + ":1");
            materialList.add(netherStairs + ":2");
            materialList.add(netherStairs + ":3");
            materialList.add(Material.NETHER_FENCE.name());
            materialList.add(Material.NETHER_STALK.name());
            materialList.add(Material.NETHER_WARTS.name());
            materialList.add(Material.PISTON_BASE.name());
            materialList.add(Material.PISTON_EXTENSION.name());
            materialList.add(Material.PISTON_MOVING_PIECE.name());
            materialList.add(Material.PISTON_STICKY_BASE.name());
            materialList.add(Material.POWERED_RAIL.name());
            materialList.add(Material.PUMPKIN_STEM.name());
            String quartzStairs = Material.QUARTZ_STAIRS.name();
            materialList.add(quartzStairs + ":0");
            materialList.add(quartzStairs + ":1");
            materialList.add(quartzStairs + ":2");
            materialList.add(quartzStairs + ":3");
            materialList.add(Material.RAILS.name());
            materialList.add(Material.RED_ROSE.name());
            materialList.add(Material.REDSTONE_COMPARATOR_OFF.name());
            materialList.add(Material.REDSTONE_COMPARATOR_ON.name());
            materialList.add(Material.REDSTONE_TORCH_OFF.name());
            materialList.add(Material.REDSTONE_TORCH_ON.name());
            materialList.add(Material.REDSTONE_WIRE.name());
            materialList.add(Material.RED_MUSHROOM.name());
            String sandStairs = Material.SANDSTONE_STAIRS.name();
            materialList.add(sandStairs + ":0");
            materialList.add(sandStairs + ":1");
            materialList.add(sandStairs + ":2");
            materialList.add(sandStairs + ":3");
            materialList.add(Material.SAPLING.name());
            materialList.add(Material.SIGN.name());
            materialList.add(Material.SIGN_POST.name());
            materialList.add(Material.SKULL.name());
            String smoothStairs = Material.SMOOTH_STAIRS.name();
            materialList.add(smoothStairs + ":0");
            materialList.add(smoothStairs + ":1");
            materialList.add(smoothStairs + ":2");
            materialList.add(smoothStairs + ":3");
            materialList.add(Material.SNOW.name());
            String spruceStairs = Material.SPRUCE_WOOD_STAIRS.name();
            materialList.add(spruceStairs + ":0");
            materialList.add(spruceStairs + ":1");
            materialList.add(spruceStairs + ":2");
            materialList.add(spruceStairs + ":3");
            materialList.add(Material.STAINED_GLASS.name());
            materialList.add(Material.STAINED_GLASS_PANE.name());
            materialList.add(Material.STATIONARY_LAVA.name());
            materialList.add(Material.STATIONARY_WATER.name());
            String step = Material.STEP.name();
            materialList.add(step + ":0");
            materialList.add(step + ":1");
            materialList.add(step + ":2");
            materialList.add(step + ":3");
            materialList.add(step + ":4");
            materialList.add(step + ":5");
            materialList.add(step + ":6");
            materialList.add(step + ":7");
            materialList.add(Material.STONE_BUTTON.name());
            materialList.add(Material.STONE_PLATE.name());
            materialList.add(Material.SUGAR_CANE_BLOCK.name());
            materialList.add(Material.THIN_GLASS.name());
            materialList.add(Material.TNT.name());
            materialList.add(Material.TORCH.name());
            materialList.add(Material.TRAPPED_CHEST.name());
            materialList.add(Material.TRAP_DOOR.name());
            materialList.add(Material.TRIPWIRE.name());
            materialList.add(Material.TRIPWIRE_HOOK.name());
            materialList.add(Material.VINE.name());
            materialList.add(Material.WATER.name());
            materialList.add(Material.WATER_LILY.name());
            materialList.add(Material.WEB.name());
            materialList.add(Material.WHEAT.name());
            materialList.add(Material.WOODEN_DOOR.name());
            materialList.add(Material.WOOD_BUTTON.name());
            materialList.add(Material.WOOD_PLATE.name());
            String woodStairs = Material.WOOD_STAIRS.name();
            materialList.add(woodStairs + ":0");
            materialList.add(woodStairs + ":1");
            materialList.add(woodStairs + ":2");
            materialList.add(woodStairs + ":3");
            String woodStep = Material.WOOD_STEP.name();
            materialList.add(woodStep + ":0");
            materialList.add(woodStep + ":1");
            materialList.add(woodStep + ":2");
            materialList.add(woodStep + ":3");
            materialList.add(woodStep + ":4");
            materialList.add(woodStep + ":5");
            materialList.add(Material.YELLOW_FLOWER.name());
        } catch (NoSuchFieldError e) {
            log.warning("Missing Materials. Please update your server.");
        }

        try {
            String redSandStairs = Material.RED_SANDSTONE_STAIRS.name();
            materialList.add(redSandStairs + ":0");
            materialList.add(redSandStairs + ":1");
            materialList.add(redSandStairs + ":2");
            materialList.add(redSandStairs + ":3");

            materialList.add(Material.STANDING_BANNER.name());
            materialList.add(Material.IRON_TRAPDOOR.name());
            materialList.add(Material.SEA_LANTERN.name());

            materialList.add(Material.ACACIA_FENCE.name());
            materialList.add(Material.BIRCH_FENCE.name());
            materialList.add(Material.DARK_OAK_FENCE.name());
            materialList.add(Material.JUNGLE_FENCE.name());
            materialList.add(Material.SPRUCE_FENCE.name());

            materialList.add(Material.ACACIA_FENCE_GATE.name());
            materialList.add(Material.BIRCH_FENCE_GATE.name());
            materialList.add(Material.DARK_OAK_FENCE_GATE.name());
            materialList.add(Material.JUNGLE_FENCE_GATE.name());
            materialList.add(Material.SPRUCE_FENCE_GATE.name());

            materialList.add(Material.ACACIA_DOOR.name());
            materialList.add(Material.BIRCH_DOOR.name());
            materialList.add(Material.DARK_OAK_DOOR.name());
            materialList.add(Material.JUNGLE_DOOR.name());
            materialList.add(Material.SPRUCE_DOOR.name());

            materialList.add(Material.STONE_SLAB2 + ":0");
        } catch (NoSuchFieldError e) {
            log.warning("1.8 blocks not found.");
        }
    }

    public List<String> getMaterials() {
        return materialList;
    }
}