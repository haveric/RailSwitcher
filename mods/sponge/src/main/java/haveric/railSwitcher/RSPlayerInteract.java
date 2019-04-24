package haveric.railSwitcher;

import java.util.Optional;

import org.spongepowered.api.block.BlockSnapshot;
import org.spongepowered.api.block.BlockState;
import org.spongepowered.api.block.BlockType;
import org.spongepowered.api.block.BlockTypes;
import org.spongepowered.api.data.key.Keys;
import org.spongepowered.api.data.type.RailDirection;
import org.spongepowered.api.data.type.RailDirections;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.entity.living.player.gamemode.GameModes;
import org.spongepowered.api.event.Listener;
import org.spongepowered.api.event.block.InteractBlockEvent;
import org.spongepowered.api.event.entity.InteractEntityEvent;
import org.spongepowered.api.item.ItemType;
import org.spongepowered.api.item.ItemTypes;
import org.spongepowered.api.item.inventory.ItemStack;
import org.spongepowered.api.world.Location;
import org.spongepowered.api.world.World;

public class RSPlayerInteract {

    private RailSwitcher plugin;

    public RSPlayerInteract(RailSwitcher railSwitcher) {
        plugin = railSwitcher;
    }

    @Listener
    public void onPlayerInteractEntity(InteractEntityEvent.Secondary event) {
        plugin.getLog().info("Target entity: " + event.getTargetEntity());
    }
    @Listener
    public void onPlayerInteract(InteractBlockEvent.Secondary event) {
        BlockSnapshot block = event.getTargetBlock();
        BlockState state = block.getState();
        BlockType blockType = state.getType();

        if (blockType == BlockTypes.RAIL || blockType == BlockTypes.GOLDEN_RAIL || blockType == BlockTypes.DETECTOR_RAIL || blockType == BlockTypes.ACTIVATOR_RAIL) {
            Optional<Player> opPlayer = event.getCause().first(Player.class);

            if (opPlayer.isPresent()) {
                Player player = opPlayer.get();

                Optional<ItemStack> optionalHolding = player.getItemInHand();
                plugin.getLog().info("Click rail");
                if (optionalHolding.isPresent()) {
                    ItemStack holding = optionalHolding.get();

                    if (holding != null) {
                        ItemType itemType = holding.getItem();
                        plugin.getLog().info("Item Type: " + itemType);
                        plugin.getLog().info("Manipulators: " + state.getManipulators());
                        if (itemType.equals(ItemTypes.RAIL) || itemType.equals(ItemTypes.GOLDEN_RAIL) || itemType.equals(ItemTypes.DETECTOR_RAIL) || itemType.equals(ItemTypes.ACTIVATOR_RAIL) || itemType.equals(Config.getRotateTool())) {
                            int index = 0;

                            Optional<RailDirection> railDir = state.get(Keys.RAIL_DIRECTION);
                            if (railDir.isPresent()) {
                                RailDirection dir = railDir.get();
                                RailDirection next = rotateRail(dir, blockType);// TODO: dir.cycleNext();
                                plugin.getLog().info("New Direction: " + next);
                                state = state.with(Keys.RAIL_DIRECTION, next).get();
                            }

                            Optional<Boolean> poweredOp = state.get(Keys.POWERED);
                            if (poweredOp.isPresent()) {
                                plugin.getLog().info("Powered: " + poweredOp.get());
                            }

                            Optional<Location<World>> opLocation = block.getLocation();
                            if (opLocation.isPresent()) {
                                Location<World> location = opLocation.get();
                                replaceBlock(player, location, state, holding);
                            }
                        }
                    }
                }
            }
        }
    }

    private RailDirection rotateRail(RailDirection dir, BlockType blockType) {
        boolean diagonals = false;

        if (blockType == BlockTypes.RAIL) {
            diagonals = true;
        }

        RailDirection rotated = dir;

        if (dir == RailDirections.ASCENDING_NORTH) {
            rotated = RailDirections.ASCENDING_EAST;
        } else if (dir == RailDirections.ASCENDING_EAST) {
            rotated = RailDirections.ASCENDING_SOUTH;
        } else if (dir == RailDirections.ASCENDING_SOUTH) {
            rotated = RailDirections.ASCENDING_WEST;
        } else if (dir == RailDirections.ASCENDING_WEST) {
            rotated = RailDirections.EAST_WEST;
        } else if (dir == RailDirections.EAST_WEST) {
            rotated = RailDirections.NORTH_SOUTH;
        }

        if (diagonals) {
            if (dir == RailDirections.NORTH_SOUTH) {
                rotated = RailDirections.NORTH_EAST;
            } else if (dir == RailDirections.NORTH_EAST) {
                rotated = RailDirections.SOUTH_EAST;
            } else if (dir == RailDirections.SOUTH_EAST) {
                rotated = RailDirections.SOUTH_WEST;
            } else if (dir == RailDirections.SOUTH_WEST) {
                rotated = RailDirections.NORTH_WEST;
            } else if (dir == RailDirections.NORTH_WEST) {
                rotated = RailDirections.ASCENDING_NORTH;
            }
        } else {
            if (dir == RailDirections.NORTH_SOUTH) {
                rotated = RailDirections.ASCENDING_NORTH;
            }
        }

        return rotated;
    }

    private void replaceBlock(Player player, Location<World> block, BlockState newBlock, ItemStack holding) {
        // TODO: Call a place event and check for cancellation
        block.setBlock(newBlock);

        removeFromHand(player, holding);
    }

    private void removeFromHand(Player player, ItemStack holding) {
        if (player.get(Keys.GAME_MODE).get() != GameModes.CREATIVE) {
            int amount = holding.getQuantity();

            if (amount > 1) {
                holding.setQuantity(amount - 1);
                player.setItemInHand(holding);
            } else {
                player.setItemInHand(null);
            }
        }
    }
}
