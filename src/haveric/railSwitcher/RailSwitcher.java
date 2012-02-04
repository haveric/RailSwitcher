package haveric.railSwitcher;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.logging.Logger;

import net.milkbowl.vault.permission.Permission;

import org.bukkit.Material;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;

public class RailSwitcher extends JavaPlugin{
    private static final Logger log = Logger.getLogger("Minecraft");
    private final RSPlayerInteract playerInteract = new RSPlayerInteract(this);
    
    private static final int BLOCKS_VERSION = 1;
    private File defaultBlocks;
    private File customBlocks;
    
    private ArrayList<Material> listOfMaterials;

    // Vault
    private Permission perm;
    
	@Override
	public void onEnable() {
		PluginManager pm = getServer().getPluginManager();
		pm.registerEvents(playerInteract, this);

		if (!getDataFolder().exists()){
			getDataFolder().mkdir();
		}
		defaultBlocks = new File(getDataFolder() + "/defaultBlocks.txt");
		customBlocks = new File(getDataFolder() + "/customBlocks.txt");
		createFiles();
		
		// Vault
		setupVault();
		
		log.info(String.format("[%s] v%s Started",getDescription().getName(), getDescription().getVersion()));
	}

	@Override
	public void onDisable() {
		log.info(String.format("[%s] Disabled",getDescription().getName()));
	}
	
	private void createFiles() {
		if (!defaultBlocks.exists()){
			try {
				defaultBlocks.createNewFile();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		if (!customBlocks.exists()){
			try {
				customBlocks.createNewFile();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		try {
			Scanner sc = new Scanner(defaultBlocks);
			
			if (sc.hasNextLine()){
				sc.next();
				int fileVersion = sc.nextInt();
				if (fileVersion < BLOCKS_VERSION){
					defaultBlocks.delete();
					defaultBlocks = new File(getDataFolder() + "/defaultBlocks.txt");
					writeBlocks(defaultBlocks, true);
				}
			} else {
				writeBlocks(defaultBlocks, true);
			}
			
			sc.close();
		} catch (FileNotFoundException e) {
			log.warning(String.format("[%s] defaultBlocks.txt not found." , getDescription().getName()));
			e.printStackTrace();
		}
		

		try {
			Scanner sc2 = new Scanner(customBlocks);

			if (!sc2.hasNextLine()){
				writeBlocks(customBlocks, false);
			}
			
			listOfMaterials = new ArrayList<Material>();
	
			while(sc2.hasNextLine()){	
				listOfMaterials.add(Material.getMaterial(sc2.nextLine()));
			}
			
			sc2.close();
		} catch (FileNotFoundException e) {
			log.warning(String.format("[%s] customBlocks.txt not found." , getDescription().getName()));
			e.printStackTrace();
		}
		
	}
	
	private void writeBlocks(File f, boolean vers){
		try {
			FileWriter fstream = new FileWriter(f);
			PrintWriter out = new PrintWriter(fstream);
			if (vers){
				out.println("Version: " + BLOCKS_VERSION);
			}
			out.println("AIR");
			out.println("BED");
			out.println("BED_BLOCK");
			out.println("BEDROCK");
			out.println("BREWING_STAND");
			out.println("BROWN_MUSHROOM");
			out.println("CACTUS");
			out.println("CAKE_BLOCK");
			out.println("CAULDRON");
			out.println("CHEST");
			out.println("CROPS");
			out.println("DEAD_BUSH");
			out.println("DETECTOR_RAIL");
			out.println("FENCE");
			out.println("FENCE_GATE");
			out.println("GLASS");
			out.println("LEAVES");
			out.println("LONG_GRASS");
			out.println("PISTON_EXTENSION");
			out.println("POWERED_RAIL");
			out.println("RAILS");
			out.println("RED_MUSHROOM");
			
			out.close();
			fstream.close();
		} catch (IOException e) {
			log.warning(String.format("[%s] File %s not found." , getDescription().getName(),f.getName()));
		}
	}
	
	public ArrayList<Material> getMaterials(){
		return listOfMaterials;
	}
	
	private void setupVault() {       
        RegisteredServiceProvider<Permission> permProvider = getServer().getServicesManager().getRegistration(net.milkbowl.vault.permission.Permission.class);
        if (permProvider != null) {
            perm = permProvider.getProvider();
        }
    }
	
    public Permission getPerm(){
    	return perm;
    }
}
