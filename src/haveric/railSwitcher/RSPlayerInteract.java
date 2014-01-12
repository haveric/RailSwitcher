package haveric.railSwitcher;

import haveric.railSwitcher.blockLogger.BlockLogger;
import haveric.railSwitcher.guard.Guard;

import java.util.HashSet;
import java.util.List;

import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.block.BlockState;
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
    private HashSet<Byte> transparentBlocks = new HashSet<Byte>();

    public RSPlayerInteract(RailSwitcher rs) {
        plugin = rs;

        transparentBlocks.add((byte) Material.AIR.getId());
        transparentBlocks.add((byte) Material.POWERED_RAIL.getId());
        transparentBlocks.add((byte) Material.DETECTOR_RAIL.getId());
        transparentBlocks.add((byte) Material.RAILS.getId());
        transparentBlocks.add((byte) Material.ACTIVATOR_RAIL.getId());
    }

    @EventHandler
    public void onPlayerInteract(PlayerInteractEvent event) {
        Player player = event.getPlayer();
        holding = player.getItemInHand();
        Material hand = holding.getType();

        Block block = null;
        if (event.getAction() == Action.RIGHT_CLICK_AIR) {
            if (hand == Material.RAILS || hand == Material.POWERED_RAIL || hand == Material.DETECTOR_RAIL || hand == Material.ACTIVATOR_RAIL) {
                final int dist = 5;

                HashSet<Byte> transparent = new HashSet<Byte>();
                transparent.add((byte) Material.AIR.getId());
                transparent.add((byte) Material.RAILS.getId());
                transparent.add((byte) Material.POWERED_RAIL.getId());
                transparent.add((byte) Material.DETECTOR_RAIL.getId());
                transparent.add((byte) Material.ACTIVATOR_RAIL.getId());
                List<Block> blocks = player.getLineOfSight(transparent, dist);

                for (int l = blocks.size(), i = l; i > 0; i--) {
                    Block tempBlock = blocks.get(i - 1);
                    Material tempType = tempBlock.getType();

                    if (tempType == Material.RAILS || tempType == Material.POWERED_RAIL || tempType == Material.DETECTOR_RAIL || tempType == Material.ACTIVATOR_RAIL) {
                        block = tempBlock;
                        break;
                    }
                }
            }
        } else if (event.getAction() == Action.RIGHT_CLICK_BLOCK) {
            if (hand == Material.SHEARS) {
                block = event.getClickedBlock();
            }
        }

        if (block != null) {
            if (Perms.canSwitch(player)) {
                World world = player.getWorld();

                if (Guard.canPlace(player, block.getLocation())) {
                    Material type = block.getType();
                    inventory = player.getInventory();

                    int data = block.getData();
                    BlockState oldState = block.getState();
                    int bx = block.getX();
                    int by = block.getY();
                    int bz = block.getZ();
                    if (type == Material.RAILS) {
                        if (hand == Material.SHEARS || hand == Material.RAILS) {
                            if (data == 9) {
                                block.setData((byte) 0);
                            } else {
                                if (data == 1 && !canPlaceRail(world.getBlockAt(bx + 1, by, bz).getType())) {
                                    data++;
                                }
                                if (data == 2 && !canPlaceRail(world.getBlockAt(bx - 1, by, bz).getType())) {
                                    data++;
                                }
                                if (data == 3 && !canPlaceRail(world.getBlockAt(bx, by, bz - 1).getType())) {
                                    data++;
                                }
                                if (data == 4 && !canPlaceRail(world.getBlockAt(bx, by, bz + 1).getType())) {
                                    data++;
                                }

                                block.setData((byte) (data + 1));
                            }
                        } else {
                            block.breakNaturally();

                            block.setType(hand);

                            if (data > 5) {
                                data = 0;
                            }
                            block.setData((byte) data);

                            useItemInHand(player);
                        }
                    } else if (type == Material.POWERED_RAIL) {
                        if (hand == Material.SHEARS || hand == Material.POWERED_RAIL) {
                            if (data == 5) {
                                block.setData((byte) 0);
                            } else if (data == 13) {
                                block.setData((byte) 8);
                            } else {
                                if ((data == 1 || data == 9) && !canPlaceRail(world.getBlockAt(bx + 1, by, bz).getType())) {
                                    data++;
                                }
                                if ((data == 2 || data == 10) && !canPlaceRail(world.getBlockAt(bx - 1, by, bz).getType())) {
                                    data++;
                                }
                                if ((data == 3 || data == 11) && !canPlaceRail(world.getBlockAt(bx, by, bz - 1).getType())) {
                                    data++;
                                }
                                if ((data == 4 || data == 12) && !canPlaceRail(world.getBlockAt(bx, by, bz + 1).getType())) {
                                    block.setData((byte) 0);
                                    return;
                                }
                                block.setData((byte) (data + 1));
                            }
                        } else {
                            block.breakNaturally();

                            block.setType(hand);

                            if (data > 5) {
                                data = 0;
                            }
                            block.setData((byte) data);

                            useItemInHand(player);
                        }
                    } else if (type == Material.DETECTOR_RAIL) {
                        if (hand == Material.SHEARS || hand == Material.DETECTOR_RAIL) {
                            if (data == 5) {
                                block.setData((byte) 0);
                            } else if (data == 13) {
                                block.setData((byte) 8);
                            } else {
                                if ((data == 1 || data == 9) && !canPlaceRail(world.getBlockAt(bx + 1, by, bz).getType())) {
                                    data++;
                                }
                                if ((data == 2 || data == 10) && !canPlaceRail(world.getBlockAt(bx - 1, by, bz).getType())) {
                                    data++;
                                }
                                if ((data == 3 || data == 11) && !canPlaceRail(world.getBlockAt(bx, by, bz - 1).getType())) {
                                    data++;
                                }
                                if ((data == 4 || data == 12) && !canPlaceRail(world.getBlockAt(bx, by, bz + 1).getType())) {
                                    block.setData((byte) 0);
                                    return;
                                }
                                block.setData((byte) (data + 1));
                            }
                        } else {
                            block.breakNaturally();

                            block.setType(hand);

                            if (data > 5) {
                                data = 0;
                            }
                            block.setData((byte) data);

                            useItemInHand(player);
                        }
                    } else if (type == Material.ACTIVATOR_RAIL) {
                        if (hand == Material.SHEARS || hand == Material.ACTIVATOR_RAIL) {
                            if (data == 5) {
                                block.setData((byte) 0);
                            } else if (data == 13) {
                                block.setData((byte) 8);
                            } else {
                                if ((data == 1 || data == 9) && !canPlaceRail(world.getBlockAt(bx + 1, by, bz).getType())) {
                                    data++;
                                }
                                if ((data == 2 || data == 10) && !canPlaceRail(world.getBlockAt(bx - 1, by, bz).getType())) {
                                    data++;
                                }
                                if ((data == 3 || data == 11) && !canPlaceRail(world.getBlockAt(bx, by, bz - 1).getType())) {
                                    data++;
                                }
                                if ((data == 4 || data == 12) && !canPlaceRail(world.getBlockAt(bx, by, bz + 1).getType())) {
                                    block.setData((byte) 0);
                                    return;
                                }
                                block.setData((byte) (data + 1));
                            }
                        } else {
                            block.breakNaturally();

                            block.setType(hand);

                            if (data > 5) {
                                data = 0;
                            }
                            block.setData((byte) data);

                            useItemInHand(player);
                        }
                    }
                    BlockState newState = block.getState();
                    BlockLogger.logBlock(player.getName(), oldState, newState);
                } // end Guard check
            } // end perm check
        }
    }


    private boolean canPlaceRail(Material m) {
        boolean canPlace = true;
        List<Material> materials = plugin.getMaterials();
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
