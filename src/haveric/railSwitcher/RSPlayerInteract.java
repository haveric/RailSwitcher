package haveric.railSwitcher;

import java.util.ArrayList;

import net.milkbowl.vault.permission.Permission;

import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;

public class RSPlayerInteract implements Listener {

    private RailSwitcher plugin;

    private ItemStack holding;
    private PlayerInventory inventory;

    public RSPlayerInteract(RailSwitcher rs) {
        plugin = rs;
    }

    @EventHandler
    public void onPlayerInteract(PlayerInteractEvent event) {

        Player player = event.getPlayer();
        holding = player.getItemInHand();
        Material hand = holding.getType();

        if (event.getAction() == Action.RIGHT_CLICK_BLOCK &&
           (hand == Material.SHEARS || hand == Material.RAILS || hand == Material.POWERED_RAIL || hand == Material.DETECTOR_RAIL)) {

            Permission perm = plugin.getPerm();
            if (perm == null || perm.has(player,  "railswitcher.switch")) {

                World world = player.getWorld();
                Block block = event.getClickedBlock();
                Material type = block.getType();
                inventory = player.getInventory();

                int data = block.getData();

                int bx = block.getX();
                int by = block.getY();
                int bz = block.getZ();
                if (type == Material.RAILS) {
                    if (hand == Material.SHEARS || hand == Material.RAILS) {
                        if (data == 9) {
                            block.setData((byte) 0);
                        } else {
                            if (data == 1 && !canPlaceRail(world.getBlockAt(bx+1, by, bz).getType())) {
                                data++;
                            }
                            if (data == 2 && !canPlaceRail(world.getBlockAt(bx-1, by, bz).getType())) {
                                data++;
                            }
                            if (data == 3 && !canPlaceRail(world.getBlockAt(bx, by, bz-1).getType())) {
                                data++;
                            }
                            if (data == 4 && !canPlaceRail(world.getBlockAt(bx,by,bz+1).getType())) {
                                data++;
                            }

                            block.setData((byte) (data + 1));
                        }
                    } else {
                        block.breakNaturally();
                        if (hand == Material.POWERED_RAIL) {
                            block.setType(Material.POWERED_RAIL);
                        } else if (hand == Material.DETECTOR_RAIL) {
                            block.setType(Material.DETECTOR_RAIL);
                        }
                        if (data > 5) {
                            data = 0;
                        }
                        block.setData((byte) data);

                        useItemInHand(player);
                    }
                } else if (type == Material.POWERED_RAIL) {
                    if (hand == Material.SHEARS || hand == Material.POWERED_RAIL) {
                        if (data == 5) {
                            block.setData((byte)0);
                        } else if (data == 13) {
                            block.setData((byte)8);
                        } else {
                            if ((data == 1 || data == 9) && !canPlaceRail(world.getBlockAt(bx+1, by, bz).getType())) {
                                data++;
                            }
                            if ((data == 2 || data == 10) && !canPlaceRail(world.getBlockAt(bx-1, by, bz).getType())) {
                                data++;
                            }
                            if ((data == 3 || data == 11) && !canPlaceRail(world.getBlockAt(bx, by, bz-1).getType())) {
                                data++;
                            }
                            if ((data == 4 || data == 12) && !canPlaceRail(world.getBlockAt(bx, by, bz+1).getType())) {
                                block.setData((byte) 0);
                                return;
                            }
                            block.setData((byte) (data + 1));
                        }
                    } else {
                        block.breakNaturally();
                        if (hand == Material.RAILS) {
                            block.setType(Material.RAILS);
                        } else if (hand == Material.DETECTOR_RAIL) {
                            block.setType(Material.DETECTOR_RAIL);
                        }
                        if (data > 5) {
                            data = 0;
                        }
                        block.setData((byte) data);

                        useItemInHand(player);
                    }
                } else if (type == Material.DETECTOR_RAIL) {
                    if (hand == Material.SHEARS || hand == Material.DETECTOR_RAIL) {
                        if (data == 1 && !canPlaceRail(world.getBlockAt(bx+1, by, bz).getType())) {
                            data++;
                        }
                        if (data == 2 && !canPlaceRail(world.getBlockAt(bx-1, by, bz).getType())) {
                            data++;
                        }
                        if (data == 3 && !canPlaceRail(world.getBlockAt(bx ,by, bz-1).getType())) {
                            data++;
                        }
                        if (data == 4 && !canPlaceRail(world.getBlockAt(bx, by, bz+1).getType())) {
                            data++;
                        }
                        if (data == 5) {
                            block.setData((byte) 0);
                        } else {
                            block.setData((byte) (data + 1));
                        }
                    } else {
                        block.breakNaturally();
                        if (hand == Material.POWERED_RAIL) {
                            block.setType(Material.POWERED_RAIL);
                        } else if (hand == Material.RAILS) {
                            block.setType(Material.RAILS);
                        }
                        if (data > 5) {
                            data = 0;
                        }
                        block.setData((byte) data);

                        useItemInHand(player);
                    }
                } else if (type == Material.RAILS) {
                    block.breakNaturally();
                    if (hand == Material.POWERED_RAIL) {
                        block.setType(Material.POWERED_RAIL);
                    } else if (hand == Material.DETECTOR_RAIL) {
                        block.setType(Material.DETECTOR_RAIL);
                    }
                    if (data > 5) {
                        data = 0;
                    }
                    block.setData((byte) data);

                    if (player.getGameMode() == GameMode.SURVIVAL) {
                        int amt = holding.getAmount();
                        if (amt > 1) {
                            holding.setAmount(--amt);
                        } else {
                            inventory.setItemInHand(null);
                        }
                    }
                } else if (type == Material.POWERED_RAIL) {
                    block.breakNaturally();
                    if (hand == Material.RAILS) {
                        block.setType(Material.RAILS);
                    } else if (hand == Material.DETECTOR_RAIL) {
                        block.setType(Material.DETECTOR_RAIL);
                    }
                    if (data > 5) {
                        data = 0;
                    }
                    block.setData((byte) data);

                    useItemInHand(player);
                } else if (type == Material.DETECTOR_RAIL) {
                    block.breakNaturally();
                    if (hand == Material.POWERED_RAIL) {
                        block.setType(Material.POWERED_RAIL);
                    } else if (hand == Material.RAILS) {
                        block.setType(Material.RAILS);
                    }
                    if (data > 5) {
                        data = 0;
                    }
                    block.setData((byte) data);

                    useItemInHand(player);
                }
            } // end perm check
        }
    }

    private boolean canPlaceRail(Material m) {
        boolean canPlace = true;
        ArrayList<Material> materials = plugin.getMaterials();
        if (materials != null) {
            for (int i = 0; i < materials.size() && canPlace; i++) {
                 if (m == materials.get(i)) {
                     canPlace = false;
                 }
            }
        }

        return canPlace;
    }

    private void useItemInHand(Player player) {
        if (player.getGameMode() == GameMode.SURVIVAL) {
            int amt = holding.getAmount();
            if (amt > 1) {
                holding.setAmount(--amt);
            } else {
                inventory.setItemInHand(null);
            }
        }
    }
}
