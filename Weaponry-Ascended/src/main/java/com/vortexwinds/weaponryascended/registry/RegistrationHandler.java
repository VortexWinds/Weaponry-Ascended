package com.vortexwinds.weaponryascended.registry;

import com.vortexwinds.weaponryascended.WeaponryAscended;
import com.vortexwinds.weaponryascended.items.CustomSwordItem;
import com.vortexwinds.weaponryascended.weapons.BaseWeaponItem;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.flag.FeatureFlagSet;
import net.minecraft.world.flag.FeatureFlags;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.CreativeModeTabs;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.material.MapColor;
import net.minecraft.world.level.material.PushReaction;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.Supplier;

/**
 * Registry class for handling all item, block, and creative tab registrations
 * for the Weaponry Ascended mod.
 */
public class RegistrationHandler {
    
    // Create Deferred Registers
    public static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(Registries.BLOCK, WeaponryAscended.MODID);
    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(Registries.ITEM, WeaponryAscended.MODID);
    public static final DeferredRegister<CreativeModeTab> CREATIVE_MODE_TABS = DeferredRegister.create(Registries.CREATIVE_MODE_TAB, WeaponryAscended.MODID);
    
    // Register the mod's creative tab
    public static final Supplier<CreativeModeTab> WEAPONRY_TAB = CREATIVE_MODE_TABS.register("weaponry_ascended",
        () -> CreativeModeTab.builder()
            .withTabsBefore(CreativeModeTabs.COMBAT)
            .icon(() -> new ItemStack(new CustomSwordItem(BaseWeaponItem.WeaponTier.IRON, 6.0F, -2.4F, 
                new Item.Properties())))
            .displayItems((parameters, output) -> {
                // Add all mod items to the creative tab
                output.acceptAll(ModItems.ITEMS.getAll());
            })
            .build());
    
    // Register blocks
    public static final Supplier<Block> CUSTOMIZATION_STATION = BLOCKS.register("customization_station",
        () -> new Block(BlockBehaviour.Properties.of()
            .mapColor(MapColor.WOOD)
            .strength(2.5F)
            .sound(net.minecraft.world.level.block.SoundType.WOOD)));
            
    public static final Supplier<Block> WEAPON_FORGE = BLOCKS.register("weapon_forge",
        () -> new Block(BlockBehaviour.Properties.of()
            .mapColor(MapColor.METAL)
            .strength(4.0F)
            .sound(net.minecraft.world.level.block.SoundType.ANVIL)));
    
    // Register items
    public static final Supplier<Item> CUSTOMIZATION_STATION_ITEM = ITEMS.register("customization_station",
        () -> new BlockItem(CUSTOMIZATION_STATION.get(), new Item.Properties()));
        
    public static final Supplier<Item> WEAPON_FORGE_ITEM = ITEMS.register("weapon_forge",
        () -> new BlockItem(WEAPON_FORGE.get(), new Item.Properties()));
    
    // Example weapon items
    public static final Supplier<Item> CUSTOM_SWORD = ITEMS.register("custom_sword",
        () -> new CustomSwordItem(BaseWeaponItem.WeaponTier.IRON, 6.0F, -2.4F, 
            new Item.Properties().stacksTo(1)));
            
    public static final Supplier<Item> CUSTOM_DAGGER = ITEMS.register("custom_dagger",
        () -> new CustomSwordItem(BaseWeaponItem.WeaponTier.IRON, 4.0F, -1.8F, 
            new Item.Properties().stacksTo(1)));
            
    public static final Supplier<Item> CUSTOM_WARHAMMER = ITEMS.register("custom_warhammer",
        () -> new CustomSwordItem(BaseWeaponItem.WeaponTier.IRON, 8.0F, -3.2F, 
            new Item.Properties().stacksTo(1)));
    
    // Material and component items
    public static final Supplier<Item> WEAPON_CORE = ITEMS.register("weapon_core",
        () -> new Item(new Item.Properties()));
        
    public static final Supplier<Item> TRAIT_ESSENCE = ITEMS.register("trait_essence",
        () -> new Item(new Item.Properties()));
        
    public static final Supplier<Item> ENHANCED_COMPONENT = ITEMS.register("enhanced_component",
        () -> new Item(new Item.Properties()));
    
    public static void register(IEventBus eventBus) {
        BLOCKS.register(eventBus);
        ITEMS.register(eventBus);
        CREATIVE_MODE_TABS.register(eventBus);
    }
}

/**
 * Container class to hold all registered items for easy access
 */
class ModItems {
    public static final java.util.Set<net.minecraft.world.item.Item> ITEMS = new java.util.HashSet<>();
    
    static {
        // Add items from registration handler
        ITEMS.add(RegistrationHandler.CUSTOM_SWORD.get());
        ITEMS.add(RegistrationHandler.CUSTOM_DAGGER.get());
        ITEMS.add(RegistrationHandler.CUSTOM_WARHAMMER.get());
        ITEMS.add(RegistrationHandler.WEAPON_CORE.get());
        ITEMS.add(RegistrationHandler.TRAIT_ESSENCE.get());
        ITEMS.add(RegistrationHandler.ENHANCED_COMPONENT.get());
        ITEMS.add(RegistrationHandler.CUSTOMIZATION_STATION_ITEM.get());
        ITEMS.add(RegistrationHandler.WEAPON_FORGE_ITEM.get());
    }
}