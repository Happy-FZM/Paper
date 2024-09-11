package io.papermc.paper.registry.keys.tags;

import static net.kyori.adventure.key.Key.key;

import io.papermc.paper.generated.GeneratedFrom;
import io.papermc.paper.registry.RegistryKey;
import io.papermc.paper.registry.tag.TagKey;
import net.kyori.adventure.key.Key;
import org.bukkit.block.banner.PatternType;
import org.checkerframework.checker.nullness.qual.NonNull;
import org.jetbrains.annotations.ApiStatus;

/**
 * Vanilla keys for {@link RegistryKey#BANNER_PATTERN}.
 *
 * @apiNote The fields provided here are a direct representation of
 * what is available from the vanilla game source. They may be
 * changed (including removals) on any Minecraft version
 * bump, so cross-version compatibility is not provided on the
 * same level as it is on most of the other API.
 */
@SuppressWarnings({
        "unused",
        "SpellCheckingInspection"
})
@GeneratedFrom("1.21.1")
@ApiStatus.Experimental
public final class BannerPatternTagKeys {
    /**
     * {@code #minecraft:no_item_required}
     *
     * @apiNote This field is version-dependant and may be removed in future Minecraft versions
     */
    public static final TagKey<PatternType> NO_ITEM_REQUIRED = create(key("no_item_required"));

    /**
     * {@code #minecraft:pattern_item/creeper}
     *
     * @apiNote This field is version-dependant and may be removed in future Minecraft versions
     */
    public static final TagKey<PatternType> PATTERN_ITEM_CREEPER = create(key("pattern_item/creeper"));

    /**
     * {@code #minecraft:pattern_item/flow}
     *
     * @apiNote This field is version-dependant and may be removed in future Minecraft versions
     */
    public static final TagKey<PatternType> PATTERN_ITEM_FLOW = create(key("pattern_item/flow"));

    /**
     * {@code #minecraft:pattern_item/flower}
     *
     * @apiNote This field is version-dependant and may be removed in future Minecraft versions
     */
    public static final TagKey<PatternType> PATTERN_ITEM_FLOWER = create(key("pattern_item/flower"));

    /**
     * {@code #minecraft:pattern_item/globe}
     *
     * @apiNote This field is version-dependant and may be removed in future Minecraft versions
     */
    public static final TagKey<PatternType> PATTERN_ITEM_GLOBE = create(key("pattern_item/globe"));

    /**
     * {@code #minecraft:pattern_item/guster}
     *
     * @apiNote This field is version-dependant and may be removed in future Minecraft versions
     */
    public static final TagKey<PatternType> PATTERN_ITEM_GUSTER = create(key("pattern_item/guster"));

    /**
     * {@code #minecraft:pattern_item/mojang}
     *
     * @apiNote This field is version-dependant and may be removed in future Minecraft versions
     */
    public static final TagKey<PatternType> PATTERN_ITEM_MOJANG = create(key("pattern_item/mojang"));

    /**
     * {@code #minecraft:pattern_item/piglin}
     *
     * @apiNote This field is version-dependant and may be removed in future Minecraft versions
     */
    public static final TagKey<PatternType> PATTERN_ITEM_PIGLIN = create(key("pattern_item/piglin"));

    /**
     * {@code #minecraft:pattern_item/skull}
     *
     * @apiNote This field is version-dependant and may be removed in future Minecraft versions
     */
    public static final TagKey<PatternType> PATTERN_ITEM_SKULL = create(key("pattern_item/skull"));

    private BannerPatternTagKeys() {
    }

    /**
     * Creates a typed key for {@link PatternType} in the registry {@code minecraft:banner_pattern}.
     *
     * @param key the value's key in the registry
     * @return a new typed key
     */
    @ApiStatus.Experimental
    public static @NonNull TagKey<PatternType> create(final @NonNull Key key) {
        return TagKey.create(RegistryKey.BANNER_PATTERN, key);
    }
}
