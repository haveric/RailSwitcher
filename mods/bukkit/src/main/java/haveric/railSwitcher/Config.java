package haveric.railSwitcher;

import org.bukkit.Material;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

public class Config {

    private static RailSwitcher plugin;

    private static String cfgRotateTool = "Rotate_Tool";

    private static Material ROTATE_TOOL_DEFAULT = Material.SHEARS;

    private static FileConfiguration config;
    private static File configFile;

    public static void init(RailSwitcher rs) {
        plugin = rs;
        configFile = new File(plugin.getDataFolder() + File.separator + "config.yml");
        config = YamlConfiguration.loadConfiguration(configFile);

        saveConfig();
    }

    public static void reload() {
        try {
            config.load(configFile);
        } catch (FileNotFoundException e) {
            plugin.log.warning("config.yml not found. Creating a new one.");
            saveConfig();
        } catch (IOException | InvalidConfigurationException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    public static void setup() {
        config.addDefault(cfgRotateTool, ROTATE_TOOL_DEFAULT.name());

        if (!config.isSet(cfgRotateTool)) {
            config.options().copyDefaults(true);
            saveConfig();
        }
    }

    /**
     * Saves the configuration to the file.
     */
    private static void saveConfig() {
        try {
            config.save(configFile);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void setRotateTool(Material newRotateTool) {
        config.set(cfgRotateTool, newRotateTool.name());
        saveConfig();
    }

    public static Material getRotateTool() {
        String toolName = config.getString(cfgRotateTool, ROTATE_TOOL_DEFAULT.name());
        Material tool = Material.getMaterial(toolName);

        if (tool == null) {
            tool = ROTATE_TOOL_DEFAULT;
        }

        return tool;
    }
}
