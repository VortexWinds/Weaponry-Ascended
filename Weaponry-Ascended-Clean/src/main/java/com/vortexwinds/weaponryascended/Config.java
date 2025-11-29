package com.vortexwinds.weaponryascended;

import com.mojang.logging.LogUtils;
import net.neoforged.fml.config.ModConfig;
import net.neoforged.fml.event.config.ModConfigEvent;
import net.neoforged.neoforge.common.ModConfigSpec;
import net.neoforged.neoforge.common.ModConfigSpec.ConfigValue;
import org.slf4j.Logger;

public class Config
{
    private static final Logger LOGGER = LogUtils.getLogger();
    // WEAPON SETTINGS
    public static final ModConfigSpec.Builder WEAPON_BUILDER = new ModConfigSpec.Builder()
            .comment("Weapon configuration settings")
            .translation("weaponryascended.config.weapon_settings")
            .push("weapon_settings");

    public static final ModConfigSpec.BooleanValue ENABLE_CUSTOM_WEAPONS = WEAPON_BUILDER
            .comment("Enable custom weapon system")
            .translation("weaponryascended.config.enable_custom_weapons")
            .define("enable_custom_weapons", true);

    public static final ModConfigSpec.IntValue MAX_WEAPON_TRAITS = WEAPON_BUILDER
            .comment("Maximum number of traits a weapon can have")
            .translation("weaponryascended.config.max_weapon_traits")
            .defineInRange("max_weapon_traits", 6, 1, 20);

    public static final ModConfigSpec.DoubleValue TRAIT_STRENGTH_MULTIPLIER = WEAPON_BUILDER
            .comment("Multiplier for trait effects")
            .translation("weaponryascended.config.trait_strength_multiplier")
            .defineInRange("trait_strength_multiplier", 1.0, 0.1, 5.0);

    // CUSTOMIZATION SETTINGS
    public static final ModConfigSpec.Builder CUSTOMIZATION_BUILDER = new ModConfigSpec.Builder()
            .comment("Tool customization settings")
            .translation("weaponryascended.config.customization_settings")
            .push("customization_settings");

    public static final ModConfigSpec.BooleanValue ENABLE_TOOL_CUSTOMIZATION = CUSTOMIZATION_BUILDER
            .comment("Enable tool customization system")
            .translation("weaponryascended.config.enable_tool_customization")
            .define("enable_tool_customization", true);

    public static final ModConfigSpec.IntValue MAX_CUSTOMIZATION_ITEMS = CUSTOMIZATION_BUILDER
            .comment("Maximum number of items that can be used for customization")
            .translation("weaponryascended.config.max_customization_items")
            .defineInRange("max_customization_items", 5, 1, 10);

    // CRAFTING SETTINGS
    public static final ModConfigSpec.Builder CRAFTING_BUILDER = new ModConfigSpec.Builder()
            .comment("Crafting configuration settings")
            .translation("weaponryascended.config.crafting_settings")
            .push("crafting_settings");

    public static final ModConfigSpec.BooleanValue ENABLE_CUSTOM_CRAFTING = CRAFTING_BUILDER
            .comment("Enable custom crafting recipes")
            .translation("weaponryascended.config.enable_custom_crafting")
            .define("enable_custom_crafting", true);

    public static final ModConfigSpec.IntValue CRAFTING_TIME_TICKS = CRAFTING_BUILDER
            .comment("Time in ticks for custom weapon crafting (20 ticks = 1 second)")
            .translation("weaponryascended.config.crafting_time_ticks")
            .defineInRange("crafting_time_ticks", 200, 20, 2000);

    public static final ModConfigSpec SPEC = WEAPON_BUILDER
            .append(CUSTOMIZATION_BUILDER)
            .append(CRAFTING_BUILDER)
            .build();

    public static void loadConfig(final ModConfig config) {
        // Load the configuration when it's loaded
        Config.loadConfigInternal(config);
    }

    private static void loadConfigInternal(final ModConfig config) {
        // This would be called when the config is loaded
        // You can use this to set up data structures based on config values
        LOGGER.info("Loaded config: {}", config.getFileName());
    }
}