package io.papermc.generator.rewriter.types.registry.definition;

import io.papermc.generator.Main;
import io.papermc.generator.rewriter.registration.PatternSourceSetRewriter;
import io.papermc.generator.rewriter.types.Types;
import io.papermc.generator.types.SourceGenerator;
import io.papermc.generator.types.registry.GeneratedKeyType;
import io.papermc.generator.types.registry.GeneratedTagKeyType;
import io.papermc.paper.registry.event.RegistryEvents;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceKey;
import java.util.List;

/*
// built-ins
            writable(Registries.GAME_EVENT, RegistryKey.GAME_EVENT, GameEvent.class, CraftGameEvent::new, PaperGameEventRegistryEntry.PaperBuilder::new),
            entry(Registries.INSTRUMENT, RegistryKey.INSTRUMENT, MusicInstrument.class, CraftMusicInstrument::new),
            entry(Registries.MOB_EFFECT, RegistryKey.MOB_EFFECT, PotionEffectType.class, CraftPotionEffectType::new),
            entry(Registries.STRUCTURE_TYPE, RegistryKey.STRUCTURE_TYPE, StructureType.class, CraftStructureType::new),
            entry(Registries.BLOCK, RegistryKey.BLOCK, BlockType.class, CraftBlockType::new),
            entry(Registries.ITEM, RegistryKey.ITEM, ItemType.class, CraftItemType::new),
            entry(Registries.CAT_VARIANT, RegistryKey.CAT_VARIANT, Cat.Type.class, CraftCat.CraftType::new),
            entry(Registries.FROG_VARIANT, RegistryKey.FROG_VARIANT, Frog.Variant.class, CraftFrog.CraftVariant::new),
            entry(Registries.VILLAGER_PROFESSION, RegistryKey.VILLAGER_PROFESSION, Villager.Profession.class, CraftVillager.CraftProfession::new),
            entry(Registries.VILLAGER_TYPE, RegistryKey.VILLAGER_TYPE, Villager.Type.class, CraftVillager.CraftType::new),
            entry(Registries.MAP_DECORATION_TYPE, RegistryKey.MAP_DECORATION_TYPE, MapCursor.Type.class, CraftMapCursor.CraftType::new),

            // data-drivens
            entry(Registries.STRUCTURE, RegistryKey.STRUCTURE, Structure.class, CraftStructure::new).delayed(),
            entry(Registries.TRIM_MATERIAL, RegistryKey.TRIM_MATERIAL, TrimMaterial.class, CraftTrimMaterial::new).delayed(),
            entry(Registries.TRIM_PATTERN, RegistryKey.TRIM_PATTERN, TrimPattern.class, CraftTrimPattern::new).delayed(),
            entry(Registries.DAMAGE_TYPE, RegistryKey.DAMAGE_TYPE, DamageType.class, CraftDamageType::new).delayed(),
            entry(Registries.WOLF_VARIANT, RegistryKey.WOLF_VARIANT, Wolf.Variant.class, CraftWolf.CraftVariant::new).delayed(),
            writable(Registries.ENCHANTMENT, RegistryKey.ENCHANTMENT, Enchantment.class, CraftEnchantment::new, PaperEnchantmentRegistryEntry.PaperBuilder::new).withSerializationUpdater(FieldRename.ENCHANTMENT_RENAME).delayed(),
            entry(Registries.JUKEBOX_SONG, RegistryKey.JUKEBOX_SONG, JukeboxSong.class, CraftJukeboxSong::new).delayed(),
            entry(Registries.BANNER_PATTERN, RegistryKey.BANNER_PATTERN, PatternType.class, CraftPatternType::new).delayed(),

            // api-only
            apiOnly(Registries.BIOME, RegistryKey.BIOME, () -> org.bukkit.Registry.BIOME),
            apiOnly(Registries.PAINTING_VARIANT, RegistryKey.PAINTING_VARIANT, () -> org.bukkit.Registry.ART),
            apiOnly(Registries.ATTRIBUTE, RegistryKey.ATTRIBUTE, () -> org.bukkit.Registry.ATTRIBUTE),
            apiOnly(Registries.ENTITY_TYPE, RegistryKey.ENTITY_TYPE, () -> org.bukkit.Registry.ENTITY_TYPE),
            apiOnly(Registries.PARTICLE_TYPE, RegistryKey.PARTICLE_TYPE, () -> org.bukkit.Registry.PARTICLE_TYPE),
            apiOnly(Registries.POTION, RegistryKey.POTION, () -> org.bukkit.Registry.POTION),
            apiOnly(Registries.SOUND_EVENT, RegistryKey.SOUND_EVENT, () -> org.bukkit.Registry.SOUNDS),
            apiOnly(Registries.MEMORY_MODULE_TYPE, RegistryKey.MEMORY_MODULE_TYPE, () -> (org.bukkit.Registry<MemoryKey<?>>) (org.bukkit.Registry) org.bukkit.Registry.MEMORY_MODULE_TYPE),
            apiOnly(Registries.FLUID, RegistryKey.FLUID, () -> org.bukkit.Registry.FLUID)
 */
public class RegistryDefinitionRewriters {

    private static final String PAPER_REGISTRY_PACKAGE = "io.papermc.paper.registry";

    private static <T, A> SourceGenerator simpleTagKey(final RegistryEntry entry) {
        return new GeneratedTagKeyType<>(entry.keyClassName().concat("TagKeys"), (Class<A>) entry.apiClass(), PAPER_REGISTRY_PACKAGE + ".keys.tags", (ResourceKey<? extends Registry<T>>) entry.registryKey(), entry.registryKeyField(), true);
    }

    public static void bootstrap(List<SourceGenerator> generators) {
        // typed/tag keys
        RegistryEntries.forEach(entry -> {
            generators.add(new GeneratedKeyType<>(PAPER_REGISTRY_PACKAGE + ".keys", entry));
            if (Main.REGISTRY_ACCESS.registryOrThrow(entry.registryKey()).getTags().findAny().isPresent()) {
                generators.add(simpleTagKey(entry));
            }
        });
    }

    public static void bootstrap(PatternSourceSetRewriter apiSourceSet, PatternSourceSetRewriter serverSourceSet) {
        apiSourceSet.register("RegistryEvents", RegistryEvents.class, new RegistryEventsRewriter());
        serverSourceSet.register("RegistryDefinitions", Types.PAPER_REGISTRIES, new PaperRegistriesRewriter());
    }
}
