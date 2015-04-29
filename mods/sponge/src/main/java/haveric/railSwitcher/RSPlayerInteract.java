package haveric.railSwitcher;

import java.util.Collection;
import java.util.Iterator;

import org.spongepowered.api.block.BlockType;
import org.spongepowered.api.block.BlockTypes;
import org.spongepowered.api.data.DataManipulator;
import org.spongepowered.api.data.manipulators.items.DurabilityData;
import org.spongepowered.api.entity.EntityInteractionType;
import org.spongepowered.api.entity.EntityInteractionTypes;
import org.spongepowered.api.entity.player.Player;
import org.spongepowered.api.event.Subscribe;
import org.spongepowered.api.event.entity.player.PlayerInteractBlockEvent;
import org.spongepowered.api.item.ItemType;
import org.spongepowered.api.item.ItemTypes;
import org.spongepowered.api.item.inventory.ItemStack;
import org.spongepowered.api.world.Location;

import com.google.common.base.Optional;

public class RSPlayerInteract {

    private RailSwitcher plugin;

    public RSPlayerInteract(RailSwitcher railSwitcher) {
        plugin = railSwitcher;
    }

    @Subscribe
    public void onPlayerInteract(PlayerInteractBlockEvent event) {
        //plugin.getLog().info("Interaction Type: " + event.getInteractionType());

        if (event.getInteractionType() == EntityInteractionTypes.USE) {
            Location block = event.getBlock();
            BlockType blockType = block.getType();

            int x = block.getBlockX();
            int y = block.getBlockY();
            int z = block.getBlockZ();
            plugin.getLog().info("Right click: " + blockType + ", X: " + x + ", y: " + y + ", z: " + z);
            if (blockType == BlockTypes.RAIL || blockType == BlockTypes.GOLDEN_RAIL || blockType == BlockTypes.DETECTOR_RAIL || blockType == BlockTypes.ACTIVATOR_RAIL) {
                Player player = event.getPlayer();

                Optional<ItemStack> optionalHolding = player.getItemInHand();
                plugin.getLog().info("Click rail");
                if (optionalHolding.isPresent()) {
                    ItemStack holding = optionalHolding.get();
                    
                    if (holding != null) {
                        
                        ItemType itemType = holding.getItem();
                        if (itemType == ItemTypes.RAIL || itemType == ItemTypes.GOLDEN_RAIL || itemType == ItemTypes.DETECTOR_RAIL || itemType == ItemTypes.ACTIVATOR_RAIL || itemType == Config.getRotateTool()) {
                            /*
                            Optional<BlockProperty<?>> optionalProperty = block.getState().getPropertyByName("shape");
                            plugin.getLog().info("Holding Rail");
                            if (optionalProperty.isPresent()) {
                                BlockProperty<?> blockProperty = optionalProperty.get();
    
                                plugin.getLog().info("Block Property: " + blockProperty);
                            }
                            */
                        }
                    }
                }
            }
        }
    }
}
