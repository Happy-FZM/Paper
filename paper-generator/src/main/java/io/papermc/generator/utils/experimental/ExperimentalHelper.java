package io.papermc.generator.utils.experimental;

import java.util.Optional;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.flag.FeatureElement;
import net.minecraft.world.item.BundleItem;

public final class ExperimentalHelper {

    public static SingleFlagHolder findBundleFeatureFlag(ResourceKey<SoundEvent> key) {
        String path = key.location().getPath();
        String[] fragments = path.split("\\.");
        if (fragments.length < 2) {
            return null;
        }

        if (!fragments[0].equals(BuiltInRegistries.ITEM.key().location().getPath())) {
            return null;
        }

        Optional<? extends FeatureElement> optionalElement = BuiltInRegistries.ITEM.getOptional(ResourceLocation.withDefaultNamespace(fragments[1]));
        return optionalElement.map(element -> {
            if (element instanceof BundleItem) {
                return FlagHolders.BUNDLE; // special case since the item is not locked itself just in the creative menu
            }
            return null;
        }).orElse(null);
    }
}
