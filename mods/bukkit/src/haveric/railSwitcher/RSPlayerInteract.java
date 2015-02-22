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

public class RSPlayerInteract implements Listener {

    private RailSwitcher plugin;

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
        Material hand = player.getItemInHand().getType();

        Block block = null;
        if (event.getAction() == Action.RIGHT_CLICK_AIR) {
            if (hand == Material.RAILS || hand == Material.POWERED_RAIL || hand == Material.DETECTOR_RAIL || hand == Material.ACTIVATOR_RAIL) {
                final int dist = 5;

                List<Block> blocks = player.getLineOfSight(transparentBlocks, dist);

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
            if (hand == Config.getRotateTool()) {
                block = event.getClickedBlock();
            }
        }

        if (block != null) {
            World world = player.getWorld();

            Material type = block.getType();

            int data = block.getData();
            int newData = data;

            int bx = block.getX();
            int by = block.getY();
            int bz = block.getZ();
            if ((Perms.canRotateByRail(player) && hand == type) || (Perms.canRotateByTool(player) && hand == Config.getRotateTool())) {
                if (type == Material.RAILS) {
                    if (newData == 9) {
                        newData = 0;
                    } else {
                        if (newData == 1 && !canPlaceRail(world.getBlockAt(bx + 1, by, bz))) {
                            newData++;
                        }
                        if (newData == 2 && !canPlaceRail(world.getBlockAt(bx - 1, by, bz))) {
                            newData++;
                        }
                        if (newData == 3 && !canPlaceRail(world.getBlockAt(bx, by, bz - 1))) {
                            newData++;
                        }
                        if (newData == 4 && !canPlaceRail(world.getBlockAt(bx, by, bz + 1))) {
                            newData++;
                        }

                        newData += 1;
                    }
                } else if (type == Material.POWERED_RAIL || type == Material.DETECTOR_RAIL || type == Material.ACTIVATOR_RAIL) {
                    if (newData == 5) {
                        newData = 0;
                    //TODO: Find next actual valid location instead of hard coding values
                    } else if (newData == 13) {
                        //newData = 8;
                        newData = 9;
                    } else {
                        if ((newData == 1 || newData == 9) && !canPlaceRail(world.getBlockAt(bx + 1, by, bz))) {
                            newData++;
                        }
                        if ((newData == 2 || newData == 10) && !canPlaceRail(world.getBlockAt(bx - 1, by, bz))) {
                            newData++;
                        }
                        if ((newData == 3 || newData == 11) && !canPlaceRail(world.getBlockAt(bx, by, bz - 1))) {
                            newData++;
                        }
                        if ((newData == 4 || newData == 12) && !canPlaceRail(world.getBlockAt(bx, by, bz + 1))) {
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
                }
            }


            boolean success = false;
            if (hand == Config.getRotateTool() || hand == type) {
                success = replaceBlock(player, block, type, newData, false);
            } else if (Perms.canSwap(player)) {
                if (newData > 5) {
                    newData = 0;
                }

                success = replaceBlock(player, block, hand, newData, true);
            }

            if (success) {
                forceUpdate(block, data, newData);
            }
        }
    }

    public boolean replaceBlock(Player player, Block block, Material mat, int data, boolean breakBlock) {
        boolean success = false;

        BlockState state = block.getState();
        block.setType(mat);
        block.setData((byte) data);

        BlockPlaceEvent placeEvent = new BlockPlaceEvent(state.getBlock(), state, block, player.getItemInHand(), player, true);
        plugin.getServer().getPluginManager().callEvent(placeEvent);
        if (placeEvent.isCancelled()) {
            state.update(true);
        } else {
            success = true;
            if (breakBlock) {
                // Reset back to original block so we can break it
                state.update(true);
                block.breakNaturally();

                useItemInHand(player);

                block.setType(mat);
                block.setData((byte) data);
            }
        }

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
        switchToSlope(block, oldState);

        // Handle switching to a slope
        switchToSlope(block, newState);
    }

    private void switchToSlope(Block block, int state) {
        Block nextBlock = null;

        if (state == 2 || state == 10) {
            nextBlock = block.getRelative(BlockFace.EAST);
        } else if (state == 3 || state == 11) {
            nextBlock = block.getRelative(BlockFace.WEST);
        } else if (state == 4 || state == 12) {
            nextBlock = block.getRelative(BlockFace.NORTH);
        } else if (state == 5 || state == 13) {
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

    private boolean canPlaceRail(Block block) {
        String material = block.getType().name();

        short blockData = block.getData();
        boolean canPlace = true;
        List<String> materials = plugin.getMaterials();

        for (String line : materials) {
            String[] item = line.split(":");

            if (material.equals(item[0])) {
                if (item.length > 1) {
                    String[] datas = item[1].split(",");

                    for (String dataString : datas) {
                        String[] ranges = dataString.split("-");

                        if (ranges.length == 2) {
                            int minData = Integer.parseInt(ranges[0]);
                            int maxData = Integer.parseInt(ranges[1]);

                            if (blockData >= minData && blockData <= maxData) {
                                canPlace = false;
                                break;
                            }
                        } else {
                            int data = Integer.parseInt(ranges[0]);

                            if (data == blockData) {
                                canPlace = false;
                                break;
                            }
                        }
                    }
                } else {
                    canPlace = false;
                    break;
                }
            }
        }

        return canPlace;
    }

    private void useItemInHand(Player player) {
        if (player.getGameMode() != GameMode.CREATIVE) {
            ItemStack holding = player.getItemInHand();

            int amt = holding.getAmount();
            if (amt > 1) {
                holding.setAmount(--amt);
            } else {
                player.getInventory().setItemInHand(null);
            }
        }
    }
}