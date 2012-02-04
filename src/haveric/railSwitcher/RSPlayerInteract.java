package haveric.railSwitcher;

import java.util.ArrayList;

import net.milkbowl.vault.permission.Permission;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

public class RSPlayerInteract implements Listener{

	public static RailSwitcher plugin;
	
	public RSPlayerInteract(RailSwitcher rs){
		plugin = rs;
	}
	
	@EventHandler
	public void onPlayerInteract(PlayerInteractEvent event) {
		Permission perm = plugin.getPerm();
		Player player = event.getPlayer();

		Block block = event.getClickedBlock();
		
		
		ItemStack holding = player.getItemInHand();
		
		World world = player.getWorld();
		
		if (perm == null || perm.has(player,  "railswitcher.switch")){
			if (event.getAction() == Action.RIGHT_CLICK_BLOCK && holding.getType() == Material.SHEARS){
				Material type = block.getType();
				
				int data = block.getData();
	
				int bx = block.getX();
				int by = block.getY();
				int bz = block.getZ();
				if (type == Material.RAILS){
					if (data < 9){
						if (data == 1 && canPlaceRail(player,world.getBlockAt(bx+1,by,bz).getType())){		
							data++;
						}
						if (data == 2 && canPlaceRail(player,world.getBlockAt(bx-1,by,bz).getType())){
							data++;
						}
						if (data == 3 && canPlaceRail(player,world.getBlockAt(bx,by,bz-1).getType())){
							data++;
						}
						if (data == 4 && canPlaceRail(player,world.getBlockAt(bx,by,bz+1).getType())){
							data++;
						}
						
						block.setData((byte)(data+1));
	
					} else {
						block.setData((byte)0);
					}
				} else if (type == Material.POWERED_RAIL){
					if (data == 5){
						block.setData((byte)0);
					} else if (data == 13){
						block.setData((byte)8);
					} else {
						block.setData((byte)(data+1));
					}
				} else if (type == Material.DETECTOR_RAIL){
					if (data < 5){
						block.setData((byte)(data+1));
					} else {
						block.setData((byte)0);
					}
				}
			}
		} else {
			player.sendMessage("[RailSwitcher] " + ChatColor.RED + "You do not have permission to switch rails.");
		}
	}
	
	private boolean canPlaceRail(Player player,Material m) {
		boolean canPlace = false;
		ArrayList<Material> materials = plugin.getMaterials();
		if (materials != null){
			for (int i = 0; i < materials.size() && !canPlace; i++) {
				 if (m == materials.get(i)){
					 canPlace = true;
				 }
			}
		}
		
		return canPlace;
	}
}
