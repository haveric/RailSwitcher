package haveric.railSwitcher;

import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.block.BlockState;
import org.bukkit.block.data.Rail;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;

public class RSPlayerInteract implements Listener {

    private RailSwitcher plugin;

    public RSPlayerInteract(RailSwitcher rs) {
        plugin = rs;
    }

    @EventHandler
    public void onPlayerInteract(PlayerInteractEvent event) {
        Player player = event.getPlayer();
        PlayerInventory inventory = player.getInventory();
        Material hand = inventory.getItemInMainHand().getType();

        Block block = null;
        if (event.getHand() == EquipmentSlot.HAND && event.getAction() == Action.RIGHT_CLICK_BLOCK) {
            if (hand == Config.getRotateTool() || RSUtil.isRail(hand)) {
                block = event.getClickedBlock();
            }
        }

        if (block != null) {
            World world = player.getWorld();

            Material type = block.getType();

            if (RSUtil.isRail(type)) {
                Rail railData = (Rail) block.getBlockData();
                Rail.Shape newShape = railData.getShape();

                if ((Perms.canRotateByRail(player) && hand == type) || (Perms.canRotateByTool(player) && hand == Config.getRotateTool())) {
                    int bx = block.getX();
                    int by = block.getY();
                    int bz = block.getZ();

                    if (newShape == Rail.Shape.EAST_WEST && !RSUtil.canPlaceRail(world.getBlockAt(bx + 1, by, bz))) {
                        newShape = Rail.Shape.ASCENDING_EAST;
                    }
                    if (newShape == Rail.Shape.ASCENDING_EAST && !RSUtil.canPlaceRail(world.getBlockAt(bx - 1, by, bz))) {
                        newShape = Rail.Shape.ASCENDING_WEST;
                    }
                    if (newShape == Rail.Shape.ASCENDING_WEST && !RSUtil.canPlaceRail(world.getBlockAt(bx, by, bz - 1))) {
                        newShape = Rail.Shape.ASCENDING_NORTH;
                    }
                    if (newShape == Rail.Shape.ASCENDING_NORTH && !RSUtil.canPlaceRail(world.getBlockAt(bx, by, bz + 1))) {
                        newShape = Rail.Shape.ASCENDING_SOUTH;
                    }

                    if (type == Material.RAIL) {
                        if (newShape == Rail.Shape.EAST_WEST) {
                            newShape = Rail.Shape.ASCENDING_EAST;
                        } else if (newShape == Rail.Shape.ASCENDING_EAST) {
                            newShape = Rail.Shape.ASCENDING_WEST;
                        } else if (newShape == Rail.Shape.ASCENDING_WEST) {
                            newShape = Rail.Shape.ASCENDING_NORTH;
                        } else if (newShape == Rail.Shape.ASCENDING_NORTH) {
                            newShape = Rail.Shape.ASCENDING_SOUTH;
                        } else if (newShape == Rail.Shape.ASCENDING_SOUTH) {
                            newShape = Rail.Shape.SOUTH_EAST;
                        } else if (newShape == Rail.Shape.SOUTH_EAST) {
                            newShape = Rail.Shape.SOUTH_WEST;
                        } else if (newShape == Rail.Shape.SOUTH_WEST) {
                            newShape = Rail.Shape.NORTH_WEST;
                        } else if (newShape == Rail.Shape.NORTH_WEST) {
                            newShape = Rail.Shape.NORTH_EAST;
                        } else if (newShape == Rail.Shape.NORTH_EAST) {
                            newShape = Rail.Shape.NORTH_SOUTH;
                        } else if (newShape == Rail.Shape.NORTH_SOUTH) {
                            newShape = Rail.Shape.EAST_WEST;
                        }
                    } else {
                        if (newShape == Rail.Shape.EAST_WEST) {
                            newShape = Rail.Shape.ASCENDING_EAST;
                        } else if (newShape == Rail.Shape.ASCENDING_EAST) {
                            newShape = Rail.Shape.ASCENDING_WEST;
                        } else if (newShape == Rail.Shape.ASCENDING_WEST) {
                            newShape = Rail.Shape.ASCENDING_NORTH;
                        } else if (newShape == Rail.Shape.ASCENDING_NORTH) {
                            newShape = Rail.Shape.ASCENDING_SOUTH;
                        } else if (newShape == Rail.Shape.ASCENDING_SOUTH) {
                            newShape = Rail.Shape.NORTH_SOUTH;
                        } else if (newShape == Rail.Shape.NORTH_SOUTH) {
                            newShape = Rail.Shape.EAST_WEST;
                        }
                    }
                }


                if (hand == Config.getRotateTool() || hand == type) {
                    replaceBlock(player, block, type, newShape, false);
                } else if (Perms.canSwap(player)) {
                    replaceBlock(player, block, hand, newShape, true);
                }
            }
        }
    }

    public void replaceBlock(Player player, Block block, Material mat, Rail.Shape newShape, boolean breakBlock) {
        BlockState state = block.getState();
        state.setType(mat);
        Rail stateData = (Rail)state.getBlockData();
        stateData.setShape(newShape);
        state.setBlockData(stateData);

        BlockPlaceEvent placeEvent = new BlockPlaceEvent(state.getBlock(), state, block, player.getInventory().getItemInMainHand(), player, true, EquipmentSlot.HAND);
        plugin.getServer().getPluginManager().callEvent(placeEvent);
        if (!placeEvent.isCancelled()) {
            if (breakBlock) {
                block.breakNaturally();
                useItemInHand(player);
            }

            state.update(true);

            if (Supports.swingHand()) {
                player.swingMainHand();
            }
        }
    }

    private void useItemInHand(Player player) {
        if (player.getGameMode() != GameMode.CREATIVE) {
            PlayerInventory inventory = player.getInventory();
            ItemStack holding = inventory.getItemInMainHand();

            int amt = holding.getAmount();
            if (amt > 1) {
                holding.setAmount(--amt);
            } else {
                inventory.setItemInMainHand(null);
            }
        }
    }
}
