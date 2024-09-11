package io.papermc.generator.rewriter.types.registry.definition;

import io.papermc.paper.registry.data.EnchantmentRegistryEntry;
import io.papermc.paper.registry.data.GameEventRegistryEntry;
import io.papermc.typewriter.utils.ClassHelper;
import net.minecraft.core.Registry;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.damagesource.DamageTypes;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.memory.MemoryModuleType;
import net.minecraft.world.entity.animal.CatVariant;
import net.minecraft.world.entity.animal.FrogVariant;
import net.minecraft.world.entity.animal.WolfVariants;
import net.minecraft.world.entity.decoration.PaintingVariants;
import net.minecraft.world.entity.npc.VillagerProfession;
import net.minecraft.world.entity.npc.VillagerType;
import net.minecraft.world.item.Instruments;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.JukeboxSongs;
import net.minecraft.world.item.alchemy.Potions;
import net.minecraft.world.item.armortrim.TrimMaterials;
import net.minecraft.world.item.armortrim.TrimPatterns;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.biome.Biomes;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.entity.BannerPatterns;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.level.saveddata.maps.MapDecorationTypes;
import org.bukkit.Art;
import org.bukkit.Fluid;
import org.bukkit.GameEvent;
import org.bukkit.JukeboxSong;
import org.bukkit.MusicInstrument;
import org.bukkit.Particle;
import org.bukkit.Sound;
import org.bukkit.attribute.Attribute;
import org.bukkit.block.Biome;
import org.bukkit.block.BlockType;
import org.bukkit.block.banner.PatternType;
import org.bukkit.damage.DamageType;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Cat;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Frog;
import org.bukkit.entity.Villager;
import org.bukkit.entity.Wolf;
import org.bukkit.entity.memory.MemoryKey;
import org.bukkit.generator.structure.Structure;
import org.bukkit.generator.structure.StructureType;
import org.bukkit.inventory.ItemType;
import org.bukkit.inventory.meta.trim.TrimMaterial;
import org.bukkit.inventory.meta.trim.TrimPattern;
import org.bukkit.map.MapCursor;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.potion.PotionType;
import org.checkerframework.checker.nullness.qual.Nullable;
import java.util.Collections;
import java.util.IdentityHashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.function.Consumer;

public final class RegistryEntries {

    private static <T> RegistryEntry<T> entry(ResourceKey<? extends Registry<T>> resourceKey, @Nullable Class<?> registryConstantClass, String registryKeyField, Class<?> apiClass, String implClass) {
        return new RegistryEntry<>(resourceKey, registryConstantClass, registryKeyField, apiClass, implClass);
    }

    private static <T> RegistryEntry<T> entry(ResourceKey<? extends Registry<T>> resourceKey, @Nullable Class<?> registryConstantClass, String registryKeyField, Class<?> apiClass) {
        String name = ClassHelper.retrieveFullNestedName(apiClass);
        String[] classes = name.split("\\.");
        if (classes.length == 0) {
            return new RegistryEntry<>(resourceKey, registryConstantClass, registryKeyField, apiClass, "Craft".concat(apiClass.getSimpleName()));
        }

        StringBuilder implName = new StringBuilder(name.length() + "Craft".length() * 2);
        implName.append("Craft".concat(classes[0]));
        for (int i = 1; i < classes.length; i++) {
            implName.append('.');
            implName.append("Craft".concat(classes[i]));
        }

        return new RegistryEntry<>(resourceKey, registryConstantClass, registryKeyField, apiClass,implName.toString());
    }

    public static final Set<Class<?>> REGISTRY_CLASS_NAME_BASED_ON_API = Set.of(
        BlockType.class,
        ItemType.class
    );

    public static final List<RegistryEntry<?>> BUILT_IN = List.of(
        entry(Registries.GAME_EVENT, net.minecraft.world.level.gameevent.GameEvent.class, "GAME_EVENT", GameEvent.class).apiRegistryBuilder(GameEventRegistryEntry.Builder.class, "PaperGameEventRegistryEntry.PaperBuilder"),
        entry(Registries.INSTRUMENT, Instruments.class, "INSTRUMENT", MusicInstrument.class),
        entry(Registries.MOB_EFFECT, MobEffects.class, "MOB_EFFECT", PotionEffectType.class),
        entry(Registries.STRUCTURE_TYPE, net.minecraft.world.level.levelgen.structure.StructureType.class, "STRUCTURE_TYPE", StructureType.class),
        entry(Registries.BLOCK, Blocks.class, "BLOCK", BlockType.class),
        entry(Registries.ITEM, Items.class, "ITEM", ItemType.class),
        entry(Registries.CAT_VARIANT, CatVariant.class, "CAT_VARIANT", Cat.Type.class),
        entry(Registries.FROG_VARIANT, FrogVariant.class, "FROG_VARIANT", Frog.Variant.class),
        entry(Registries.VILLAGER_PROFESSION, VillagerProfession.class, "VILLAGER_PROFESSION", Villager.Profession.class),
        entry(Registries.VILLAGER_TYPE, VillagerType.class, "VILLAGER_TYPE", Villager.Type.class),
        entry(Registries.MAP_DECORATION_TYPE, MapDecorationTypes.class, "MAP_DECORATION_TYPE", MapCursor.Type.class)
    );

    public static final List<RegistryEntry<?>> DATA_DRIVEN = List.of(
        entry(Registries.STRUCTURE, null, "STRUCTURE", Structure.class).delayed(),
        entry(Registries.TRIM_MATERIAL, TrimMaterials.class, "TRIM_MATERIAL", TrimMaterial.class).delayed(),
        entry(Registries.TRIM_PATTERN, TrimPatterns.class, "TRIM_PATTERN", TrimPattern.class).delayed(),
        entry(Registries.DAMAGE_TYPE, DamageTypes.class, "DAMAGE_TYPE", DamageType.class).delayed(),
        entry(Registries.WOLF_VARIANT, WolfVariants.class, "WOLF_VARIANT", Wolf.Variant.class).delayed(),
        entry(Registries.ENCHANTMENT, Enchantments.class, "ENCHANTMENT", Enchantment.class).apiRegistryBuilder(EnchantmentRegistryEntry.Builder.class, "PaperEnchantmentRegistryEntry.PaperBuilder").withSerializationUpdater("ENCHANTMENT_RENAME").delayed(),
        entry(Registries.JUKEBOX_SONG, JukeboxSongs.class, "JUKEBOX_SONG", JukeboxSong.class).delayed(),
        entry(Registries.BANNER_PATTERN, BannerPatterns.class, "BANNER_PATTERN", PatternType.class).delayed()
    );

    public static final List<RegistryEntry<?>> API_ONLY = List.of(
        entry(Registries.BIOME, Biomes.class, "BIOME", Biome.class),
        entry(Registries.PAINTING_VARIANT, PaintingVariants.class, "PAINTING_VARIANT", Art.class).apiRegistryField("ART"),
        entry(Registries.ATTRIBUTE, Attributes.class, "ATTRIBUTE", Attribute.class),
        entry(Registries.ENTITY_TYPE, net.minecraft.world.entity.EntityType.class, "ENTITY_TYPE", EntityType.class),
        entry(Registries.PARTICLE_TYPE, ParticleTypes.class, "PARTICLE_TYPE", Particle.class),
        entry(Registries.POTION, Potions.class, "POTION", PotionType.class),
        entry(Registries.SOUND_EVENT, SoundEvents.class, "SOUND_EVENT", Sound.class).apiRegistryField("SOUNDS"),
        entry(Registries.MEMORY_MODULE_TYPE, MemoryModuleType.class, "MEMORY_MODULE_TYPE", MemoryKey.class),
        entry(Registries.FLUID, Fluids.class, "FLUID", Fluid.class)
    );

    public static final Map<ResourceKey<? extends Registry<?>>, RegistryEntry<?>> BY_REGISTRY_KEY;
    static {
        Map<ResourceKey<? extends Registry<?>>, RegistryEntry<?>> byResourceKey = new IdentityHashMap<>(BUILT_IN.size() + DATA_DRIVEN.size() + API_ONLY.size());
        forEach(entry -> {
            byResourceKey.put(entry.registryKey(), entry);
        });
        for (RegistryEntry<?> entry : RegistryEntries.API_ONLY) {
            byResourceKey.put(entry.registryKey(), entry);
        }
        BY_REGISTRY_KEY = Collections.unmodifiableMap(byResourceKey);
    }

    public static <T> RegistryEntry<T> byRegistryKey(ResourceKey<? extends Registry<T>> registryKey) {
        return (RegistryEntry<T>) Objects.requireNonNull(BY_REGISTRY_KEY.get(registryKey));
    }

    // real registries
    public static void forEach(Consumer<RegistryEntry<?>> callback) {
        for (RegistryEntry<?> entry : RegistryEntries.BUILT_IN) {
            callback.accept(entry);
        }
        for (RegistryEntry<?> entry : RegistryEntries.DATA_DRIVEN) {
            callback.accept(entry);
        }
    }

    private RegistryEntries() {
    }
}
