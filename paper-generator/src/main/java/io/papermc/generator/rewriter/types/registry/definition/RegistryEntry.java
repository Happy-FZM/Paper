package io.papermc.generator.rewriter.types.registry.definition;

import com.google.common.base.CaseFormat;
import io.papermc.generator.Main;
import io.papermc.generator.utils.ClassHelper;
import net.minecraft.core.Holder;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceKey;
import org.checkerframework.checker.nullness.qual.Nullable;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.lang.reflect.ParameterizedType;
import java.util.Collections;
import java.util.IdentityHashMap;
import java.util.Map;
import java.util.Optional;

public final class RegistryEntry {
    private final ResourceKey<? extends Registry<?>> registryKey;
    private final Class<?> registryElementClass;
    private final @Nullable Class<?> registryConstantClass;
    private final String registryKeyField;

    private final Class<?> apiClass;
    private final String implClass;

    private @Nullable Class<?> apiRegistryBuilder;
    private @Nullable String apiRegistryBuilderImpl;

    private @Nullable String fieldRename;
    private boolean delayed;
    private Optional<String> apiRegistryField = Optional.empty();
    private @Nullable Map<ResourceKey<?>, String> fallbackFieldNames;

    public RegistryEntry(ResourceKey<? extends Registry<?>> registryKey, @Nullable Class<?> registryConstantClass, String registryKeyField, Class<?> apiClass, String implClass) {
        this.registryKey = registryKey;
        this.registryElementClass = Main.REGISTRY_ACCESS.registryOrThrow(registryKey).iterator().next().getClass(); // hummm...
        this.registryConstantClass = registryConstantClass;
        this.registryKeyField = registryKeyField;
        this.apiClass = apiClass;
        this.implClass = implClass;
    }

    public ResourceKey<? extends Registry<?>> registryKey() {
        return this.registryKey;
    }

    public String registryKeyField() {
        return this.registryKeyField;
    }

    public Class<?> apiClass() {
        return this.apiClass;
    }

    public String implClass() {
        return this.implClass;
    }

    public RegistryEntry delayed() {
        this.delayed = true;
        return this;
    }

    public RegistryEntry withSerializationUpdater(String fieldName) {
        this.fieldRename = fieldName;
        return this;
    }

    public boolean isDelayed() {
        return this.delayed;
    }

    public @Nullable String fieldRename() {
        return this.fieldRename;
    }

    public @Nullable Class<?> apiRegistryBuilder() {
        return this.apiRegistryBuilder;
    }

    public @Nullable String apiRegistryBuilderImpl() {
        return this.apiRegistryBuilderImpl;
    }

    public RegistryEntry apiRegistryBuilder(Class<?> builderClass, String builderImplClass) {
        this.apiRegistryBuilder = builderClass;
        this.apiRegistryBuilderImpl = builderImplClass;
        return this;
    }

    public Optional<String> apiRegistryField() {
        return this.apiRegistryField;
    }

    public RegistryEntry apiRegistryField(String registryField) {
        this.apiRegistryField = Optional.of(registryField);
        return this;
    }

    public String keyClassName() {
        if (RegistryEntries.REGISTRY_CLASS_NAME_BASED_ON_API.contains(this.apiClass)) {
            return this.apiClass.getSimpleName();
        }

        return CaseFormat.UPPER_UNDERSCORE.to(CaseFormat.UPPER_CAMEL, this.registryKeyField);
    }

    public boolean allowCustomKeys() {
        return this.apiRegistryBuilder != null || RegistryEntries.DATA_DRIVEN.contains(this);
    }

    public Map<ResourceKey<?>, String> getFallbackNames() {
        if (this.fallbackFieldNames == null) {
            if (this.registryConstantClass == null) {
                this.fallbackFieldNames = Collections.emptyMap();
                return this.fallbackFieldNames;
            }

            final Map<ResourceKey<?>, String> map = new IdentityHashMap<>();
            // Registry<?> registry = Main.REGISTRY_ACCESS.registryOrThrow(this.registryKey);
            try {
                for (final Field field : this.registryConstantClass.getDeclaredFields()) {
                    if (!ResourceKey.class.isAssignableFrom(field.getType()) && !Holder.Reference.class.isAssignableFrom(field.getType()) && !this.registryElementClass.isAssignableFrom(field.getType())) {
                        continue;
                    }

                    if (ClassHelper.isStaticConstant(field, Modifier.PUBLIC)) {
                        @Nullable ResourceKey<?> key = null;
                        if (this.registryElementClass.isAssignableFrom(field.getType())) {
                            // todo maybe if needed
                            // key = registry.getResourceKey(field.get(null)).orElseThrow();
                        } else {
                            if (field.getGenericType() instanceof ParameterizedType complexType && complexType.getActualTypeArguments().length == 1 &&
                                complexType.getActualTypeArguments()[0] == this.registryElementClass) {

                                if (Holder.Reference.class.isAssignableFrom(field.getType())) {
                                    key = ((Holder.Reference<?>) field.get(null)).key();
                                } else {
                                    key = (ResourceKey<?>) field.get(null);
                                }
                            }
                        }
                        if (key != null) {
                            map.put(key, field.getName());
                        }
                    }
                }
            } catch (ReflectiveOperationException ex) {
                throw new RuntimeException(ex);
            }
            this.fallbackFieldNames = Collections.unmodifiableMap(map);
        }
        return this.fallbackFieldNames;
    }

    @Override
    public String toString() {
        return "RegistryEntry[" +
            "resourceKey=" + this.registryKey + ", " +
            "apiRegistryKey=" + this.registryKeyField + ", " +
            "apiClass=" + this.apiClass + ", " +
            "implClass=" + this.implClass + ", " +
            ']';
    }
}
