package haveric.railSwitcher;

import java.util.HashSet;
import java.util.List;

import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.block.BlockState;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockPlaceEvent;
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

                //if (Guard.canPlace(player, block.getLocation())) {
                    Material type = block.getType();
                    inventory = player.getInventory();

                    int data = block.getData();
                    int newData = data;
                    boolean swapRail = false;

                    BlockState oldState = block.getState();
                    int bx = block.getX();
                    int by = block.getY();
                    int bz = block.getZ();
                    if (type == Material.RAILS) {
                        if (hand == Material.SHEARS || hand == type) {
                            if (newData == 9) {
                                newData = 0;
                            } else {
                                if (newData == 1 && !canPlaceRail(world.getBlockAt(bx + 1, by, bz).getType())) {
                                    newData++;
                                }
                                if (newData == 2 && !canPlaceRail(world.getBlockAt(bx - 1, by, bz).getType())) {
                                    newData++;
                                }
                                if (newData == 3 && !canPlaceRail(world.getBlockAt(bx, by, bz - 1).getType())) {
                                    newData++;
                                }
                                if (newData == 4 && !canPlaceRail(world.getBlockAt(bx, by, bz + 1).getType())) {
                                    newData++;
                                }

                                newData += 1;
                            }
                        } else {
                            swapRail = true;
                        }
                    } else if (type == Material.POWERED_RAIL || type == Material.DETECTOR_RAIL || type == Material.ACTIVATOR_RAIL) {
                        if (hand == Material.SHEARS || hand == type) {
                            if (newData == 5) {
                                newData = 0;
                            //TODO: Find next actual valid location instead of hard coding values
                            } else if (newData == 13) {
                                //newData = 8;
                                newData = 9;
                            } else {
                                if ((newData == 1 || newData == 9) && !canPlaceRail(world.getBlockAt(bx + 1, by, bz).getType())) {
                                    newData++;
                                }
                                if ((newData == 2 || newData == 10) && !canPlaceRail(world.getBlockAt(bx - 1, by, bz).getType())) {
                                    newData++;
                                }
                                if ((newData == 3 || newData == 11) && !canPlaceRail(world.getBlockAt(bx, by, bz - 1).getType())) {
                                    newData++;
                                }
                                if ((newData == 4 || newData == 12) && !canPlaceRail(world.getBlockAt(bx, by, bz + 1).getType())) {
                                    if (data == 4 || data == 12) {
                                        //newData = 0;
                                        newData = 1;
                                    } else {
                                        newData = 0;
                                    }
                                } else {
                                    newData += 1;
                                }
                            }
                        } else {
                            swapRail = true;
                        }
                    }

                    plugin.log.info("Data: " + data + ", New Data: " + newData);

                    if (swapRail) {
                        block.breakNaturally();

                        if (newData > 5) {
                            newData = 0;
                        }
                        replaceBlock(player, block, hand, newData);
                        useItemInHand(player);
                    } else {
                        replaceBlock(player, block, type, newData);
                    }
                    forceUpdate(block, data, newData);
                    /*
                    if (swapRail) {
                        block.breakNaturally();

                        block.setType(hand);

                        if (newData > 5) {
                            newData = 0;
                        }

                        useItemInHand(player);
                    } else {
                        block.setData((byte) newData);
                    }
                    forceUpdate(block, data, newData);

                    BlockState newState = block.getState();
                    BlockLogger.logBlock(player.getName(), oldState, newState);
                    */
                //} // end Guard check
            } // end perm check
        }
    }

    public boolean replaceBlock(Player player, Block block, Material mat, int data) {
        boolean success = false;
        //BlockBreakEvent breakEvent = new BlockBreakEvent(block, player);
        //plugin.getServer().getPluginManager().callEvent(breakEvent);

        //if (!breakEvent.isCancelled()) {
            //breakEvent.setCancelled(true);

            Block previousBlock = block;

            block.setType(mat);
            block.setData((byte) data);

            BlockPlaceEvent placeEvent = new BlockPlaceEvent(block, previousBlock.getState(), previousBlock, player.getItemInHand(), player, true);
            plugin.getServer().getPluginManager().callEvent(placeEvent);
            if (!placeEvent.isCancelled()) {
                success = true;
            }
        //}

        return success;
    }

    // Force an update to the rail by changing the block it's sitting on and changing it back
    private void forceUpdate(Block block, int oldState, int newState) {
        Block downBlock = block.getRelative(BlockFace.DOWN);
        Material downType = downBlock.getType();

        if (downType == Material.GRASS) {
            downBlock.setType(Material.STONE);
        } else {
            downBlock.setType(Material.GRASS);
        }

        downBlock.setType(downType);

        // Handle switching from a slope
        Block nextBlock = null;
        if (oldState == 2 || oldState == 10) {
            nextBlock = block.getRelative(BlockFace.EAST);
        } else if (oldState == 3 || oldState == 11) {
            nextBlock = block.getRelative(BlockFace.WEST);
        } else if (oldState == 4 || oldState == 12) {
            nextBlock = block.getRelative(BlockFace.NORTH);
        } else if (oldState == 5 || oldState == 13) {
            nextBlock = block.getRelative(BlockFace.SOUTH);
        }

        if (nextBlock != null) {
            Block nextUpBlock = nextBlock.getRelative(BlockFace.UP);
            Material nextUpType = nextUpBlock.getType();
            if (nextUpType == Material.RAILS || nextUpType == Material.POWERED_RAIL || nextUpType == Material.ACTIVATOR_RAIL || nextUpType == Material.DETECTOR_RAIL) {
                Material nextType = nextBlock.getType();
                if (nextType == Material.GRASS) {
                    nextBlock.setType(Material.STONE);
                } else {
                    nextBlock.setType(Material.GRASS);
                }

                nextBlock.setType(nextType);
            }
        }


        // Handle switching to a slope
        nextBlock = null;
        if (newState == 2 || newState == 10) {
            nextBlock = block.getRelative(BlockFace.EAST);
        } else if (newState == 3 || newState == 11) {
            nextBlock = block.getRelative(BlockFace.WEST);
        } else if (newState == 4 || newState == 12) {
            nextBlock = block.getRelative(BlockFace.NORTH);
        } else if (newState == 5 || newState == 13) {
            nextBlock = block.getRelative(BlockFace.SOUTH);
        }

        if (nextBlock != null) {
            Block nextUpBlock = nextBlock.getRelative(BlockFace.UP);
            Material nextUpType = nextUpBlock.getType();
            if (nextUpType == Material.RAILS || nextUpType == Material.POWERED_RAIL || nextUpType == Material.ACTIVATOR_RAIL || nextUpType == Material.DETECTOR_RAIL) {
                Material nextType = nextBlock.getType();
                if (nextType == Material.GRASS) {
                    nextBlock.setType(Material.STONE);
                } else {
                    nextBlock.setType(Material.GRASS);
                }

                nextBlock.setType(nextType);
            }
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
