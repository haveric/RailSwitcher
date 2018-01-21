package haveric.railSwitcher;

import org.spongepowered.api.block.BlockSnapshot;
import org.spongepowered.api.block.BlockState;
import org.spongepowered.api.block.BlockType;
import org.spongepowered.api.block.BlockTypes;
import org.spongepowered.api.block.trait.BlockTrait;
import org.spongepowered.api.data.key.Keys;
import org.spongepowered.api.data.type.HandTypes;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.entity.living.player.gamemode.GameModes;
import org.spongepowered.api.event.Listener;
import org.spongepowered.api.event.block.ChangeBlockEvent;
import org.spongepowered.api.event.block.InteractBlockEvent;
import org.spongepowered.api.event.cause.Cause;
import org.spongepowered.api.item.ItemType;
import org.spongepowered.api.item.ItemTypes;
import org.spongepowered.api.item.inventory.ItemStack;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.util.Direction;
import org.spongepowered.api.world.Location;
import org.spongepowered.api.world.World;

import java.util.Iterator;
import java.util.Optional;

public class RSPlayerInteract {

    private RailSwitcher plugin;

    public RSPlayerInteract(RailSwitcher railSwitcher) {
        plugin = railSwitcher;
    }

    @Listener
    public void onBlockPlace(ChangeBlockEvent.Place event) {

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
                Direction dir = event.getTargetSide();
                player.sendMessage(Text.of(dir));
                Cause cause = event.getCause();
                player.sendMessage(Text.of(cause));

                Optional<ItemStack> optionalHolding = player.getItemInHand(HandTypes.MAIN_HAND);
                plugin.getLogger().info("Click rail");
                if (optionalHolding.isPresent()) {
                    ItemStack holding = optionalHolding.get();

                    ItemType itemType = holding.getItem();

                    if (itemType.equals(ItemTypes.RAIL) || itemType == ItemTypes.GOLDEN_RAIL || itemType == ItemTypes.DETECTOR_RAIL || itemType == ItemTypes.ACTIVATOR_RAIL || itemType == Config.getRotateTool()) {
                        int index = 0;
                        Object[] traitsArray = state.getTraitValues().toArray();
                        boolean powered = traitsArray.length > 1;
                        if (powered) {
                            String poweredValue = traitsArray[index].toString();

                            index++;
                        }

                        Object shapeValue = traitsArray[index];

                        BlockTrait<?> trait = state.getTrait("shape").get();

                        Iterator<?> iter = trait.getPossibleValues().iterator();
                        Object first = null;
                        Object newValue = null;
                        while(iter.hasNext()) {
                            Object object = iter.next();
                            if (first == null) {
                                first = object;
                            }

                            if (object.equals(shapeValue)) {
                                if (iter.hasNext()) {
                                    newValue = iter.next();
                                } else {
                                    newValue = first;
                                }
                            }
                        }

                        if (newValue != null) {
                            Optional<Location<World>> opLocation = block.getLocation();
                            if (opLocation.isPresent()) {
                                Location<World> location = opLocation.get();

                                Optional<BlockState> opNewState = state.withTrait(trait, newValue);
                                if (opNewState.isPresent()) {
                                    replaceBlock(player, location, opNewState.get(), holding);
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    private void replaceBlock(Player player, Location<World> block, BlockState newBlock, ItemStack holding) {
        // TODO: Call a place event and check for cancellation
        block.setBlock(newBlock);

        removeFromHand(player, holding);
    }

    private void removeFromHand(Player player, ItemStack holding) {
        plugin.getLogger().info("Game Mode: " + player.get(Keys.GAME_MODE).get());
        if (player.get(Keys.GAME_MODE).get() != GameModes.CREATIVE) {
            int amount = holding.getQuantity();

            if (amount > 1) {
                holding.setQuantity(amount - 1);
                player.setItemInHand(HandTypes.MAIN_HAND, holding);
            } else {
                player.setItemInHand(HandTypes.MAIN_HAND, null);
            }
        }
    }
}
