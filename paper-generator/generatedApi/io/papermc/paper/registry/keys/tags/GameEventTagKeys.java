package io.papermc.paper.registry.keys.tags;

import static net.kyori.adventure.key.Key.key;

import io.papermc.paper.generated.GeneratedFrom;
import io.papermc.paper.registry.RegistryKey;
import io.papermc.paper.registry.tag.TagKey;
import net.kyori.adventure.key.Key;
import org.bukkit.GameEvent;
import org.checkerframework.checker.nullness.qual.NonNull;
import org.jetbrains.annotations.ApiStatus;

/**
 * Vanilla keys for {@link RegistryKey#GAME_EVENT}.
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
public final class GameEventTagKeys {
    /**
     * {@code #minecraft:allay_can_listen}
     *
     * @apiNote This field is version-dependant and may be removed in future Minecraft versions
     */
    public static final TagKey<GameEvent> ALLAY_CAN_LISTEN = create(key("allay_can_listen"));

    /**
     * {@code #minecraft:ignore_vibrations_sneaking}
     *
     * @apiNote This field is version-dependant and may be removed in future Minecraft versions
     */
    public static final TagKey<GameEvent> IGNORE_VIBRATIONS_SNEAKING = create(key("ignore_vibrations_sneaking"));

    /**
     * {@code #minecraft:shrieker_can_listen}
     *
     * @apiNote This field is version-dependant and may be removed in future Minecraft versions
     */
    public static final TagKey<GameEvent> SHRIEKER_CAN_LISTEN = create(key("shrieker_can_listen"));

    /**
     * {@code #minecraft:vibrations}
     *
     * @apiNote This field is version-dependant and may be removed in future Minecraft versions
     */
    public static final TagKey<GameEvent> VIBRATIONS = create(key("vibrations"));

    /**
     * {@code #minecraft:warden_can_listen}
     *
     * @apiNote This field is version-dependant and may be removed in future Minecraft versions
     */
    public static final TagKey<GameEvent> WARDEN_CAN_LISTEN = create(key("warden_can_listen"));

    private GameEventTagKeys() {
    }

    /**
     * Creates a typed key for {@link GameEvent} in the registry {@code minecraft:game_event}.
     *
     * @param key the value's key in the registry
     * @return a new typed key
     */
    @ApiStatus.Experimental
    public static @NonNull TagKey<GameEvent> create(final @NonNull Key key) {
        return TagKey.create(RegistryKey.GAME_EVENT, key);
    }
}
