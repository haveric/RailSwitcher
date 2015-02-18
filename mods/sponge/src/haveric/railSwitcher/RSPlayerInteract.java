package haveric.railSwitcher;

import org.spongepowered.api.block.BlockLoc;
import org.spongepowered.api.block.BlockProperty;
import org.spongepowered.api.block.BlockType;
import org.spongepowered.api.block.BlockTypes;
import org.spongepowered.api.entity.EntityInteractionType;
import org.spongepowered.api.entity.player.Player;
import org.spongepowered.api.event.entity.living.player.PlayerInteractBlockEvent;
import org.spongepowered.api.item.ItemTypes;
import org.spongepowered.api.item.inventory.ItemStack;
import org.spongepowered.api.util.event.Subscribe;

import com.google.common.base.Optional;

public class RSPlayerInteract {

    private RailSwitcher plugin;

    public RSPlayerInteract(RailSwitcher railSwitcher) {
        plugin = railSwitcher;
    }

    @Subscribe
    public void onPlayerInteract(PlayerInteractBlockEvent event) {
        if (event.getInteractionType() == EntityInteractionType.RIGHT_CLICK) {
            BlockLoc block = event.getBlock();
            BlockType blockType = block.getType();
            plugin.getLog().info("Right click: " + blockType);
            if (blockType == BlockTypes.RAIL || blockType == BlockTypes.GOLDEN_RAIL || blockType == BlockTypes.DETECTOR_RAIL || blockType == BlockTypes.ACTIVATOR_RAIL) {
                Player player = event.getPlayer();

                Optional<ItemStack> optionalHolding = player.getItemInHand();
                plugin.getLog().info("Click rail");
                if (optionalHolding.isPresent()) {
                    ItemStack holding = optionalHolding.get();

                    if (holding == ItemTypes.RAIL || holding == ItemTypes.GOLDEN_RAIL || holding == ItemTypes.DETECTOR_RAIL || holding == ItemTypes.ACTIVATOR_RAIL || holding == Config.getRotateTool()) {
                        Optional<BlockProperty<?>> optionalProperty = block.getState().getPropertyByName("shape");
                        plugin.getLog().info("Holding Rail");
                        if (optionalProperty.isPresent()) {
                            BlockProperty<?> blockProperty = optionalProperty.get();

                            plugin.getLog().info("Block Property: " + blockProperty);
                        }
                    }
                }
            }
        }
    }
}
