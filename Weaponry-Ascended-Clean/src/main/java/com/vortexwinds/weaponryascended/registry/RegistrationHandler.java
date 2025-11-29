package com.vortexwinds.weaponryascended.registry;

import com.vortexwinds.weaponryascended.WeaponryAscended;
import com.vortexwinds.weaponryascended.items.CustomSwordItem;
import com.vortexwinds.weaponryascended.weapons.*;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.flag.FeatureFlagSet;
import net.minecraft.world.flag.FeatureFlags;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.CreativeModeTabs;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
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
            .icon(() -> new ItemStack(new GreatSwordItem(new Item.Properties(), 9.0F, -3.0F,
                BaseWeaponItem.WeaponTier.IRON.getIngredient())))
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
    
    // Two-handed weapons
    public static final Supplier<Item> GREAT_SWORD = ITEMS.register("great_sword",
        () -> new GreatSwordItem(new Item.Properties().stacksTo(1), 9.0F, -3.0F, 
            BaseWeaponItem.WeaponTier.IRON.getIngredient()));
            
    public static final Supplier<Item> SCYTHE = ITEMS.register("scythe",
        () -> new ScytheItem(new Item.Properties().stacksTo(1), 7.0F, -2.8F,
            BaseWeaponItem.WeaponTier.IRON.getIngredient()));
            
    public static final Supplier<Item> GREAT_HAMMER = ITEMS.register("great_hammer",
        () -> new GreatHammerItem(new Item.Properties().stacksTo(1), 11.0F, -3.5F,
            BaseWeaponItem.WeaponTier.IRON.getIngredient()));
            
    public static final Supplier<Item> MORNINGSTAR = ITEMS.register("morningstar",
        () -> new MorningstarItem(new Item.Properties().stacksTo(1), 10.0F, -3.2F,
            BaseWeaponItem.WeaponTier.IRON.getIngredient()));
            
    public static final Supplier<Item> BATTLE_AXE = ITEMS.register("battle_axe",
        () -> new BattleAxeItem(new Item.Properties().stacksTo(1), 8.0F, -3.1F,
            BaseWeaponItem.WeaponTier.IRON.getIngredient()));
            
    public static final Supplier<Item> BOW = ITEMS.register("bow",
        () -> new BowItem(new Item.Properties().stacksTo(1), 30.0F, 1.0F,
            BaseWeaponItem.WeaponTier.WOOD.getIngredient()));
            
    public static final Supplier<Item> CROSSBOW = ITEMS.register("crossbow",
        () -> new CrossbowItem(new Item.Properties().stacksTo(1), 40.0F, 1.2F,
            BaseWeaponItem.WeaponTier.WOOD.getIngredient()));
            
    public static final Supplier<Item> STAFF = ITEMS.register("staff",
        () -> new StaffItem(new Item.Properties().stacksTo(1), 5.0F, -2.0F,
            BaseWeaponItem.WeaponTier.WOOD.getIngredient()));
    
    // One-handed weapons
    public static final Supplier<Item> WAR_GLAIVE = ITEMS.register("war_glaive",
        () -> new WarGlaiveItem(new Item.Properties().stacksTo(1), 7.5F, -2.6F,
            BaseWeaponItem.WeaponTier.IRON.getIngredient()));
            
    public static final Supplier<Item> HAMMER = ITEMS.register("hammer",
        () -> new HammerItem(new Item.Properties().stacksTo(1), 8.5F, -2.8F,
            BaseWeaponItem.WeaponTier.IRON.getIngredient()));
            
    public static final Supplier<Item> MACE = ITEMS.register("mace",
        () -> new MaceItem(new Item.Properties().stacksTo(1), 7.0F, -2.5F,
            BaseWeaponItem.WeaponTier.IRON.getIngredient()));
            
    public static final Supplier<Item> AXE = ITEMS.register("weaponry_axe",
        () -> new AxeItem(new Item.Properties().stacksTo(1), 6.5F, -2.3F,
            BaseWeaponItem.WeaponTier.IRON.getIngredient()));
            
    public static final Supplier<Item> DAGGER = ITEMS.register("weaponry_dagger",
        () -> new DaggerItem(new Item.Properties().stacksTo(1), 3.5F, -1.6F,
            BaseWeaponItem.WeaponTier.IRON.getIngredient()));
            
    public static final Supplier<Item> THROWING_WEAPON = ITEMS.register("throwing_weapon",
        () -> new ThrowingWeaponItem(new Item.Properties().stacksTo(16), 4.0F, -1.5F,
            BaseWeaponItem.WeaponTier.IRON.getIngredient(), true));
            
    public static final Supplier<Item> WAND = ITEMS.register("wand",
        () -> new WandItem(new Item.Properties().stacksTo(1), 4.0F, -1.8F,
            BaseWeaponItem.WeaponTier.WOOD.getIngredient()));
    
    // Defensive items (offhand only)
    public static final Supplier<Item> TOME = ITEMS.register("tome",
        () -> new TomeItem(new Item.Properties().stacksTo(1), 2.0F, 100,
            BaseWeaponItem.WeaponTier.WOOD.getIngredient()));
            
    public static final Supplier<Item> SHIELD = ITEMS.register("weaponry_shield",
        () -> new ShieldItem(new Item.Properties().stacksTo(1), 5.0F, 200,
            BaseWeaponItem.WeaponTier.IRON.getIngredient()));
            
    public static final Supplier<Item> RELIC = ITEMS.register("relic",
        () -> new RelicItem(new Item.Properties().stacksTo(1), 3.0F, 100,
            BaseWeaponItem.WeaponTier.GOLD.getIngredient()));
            
    public static final Supplier<Item> TOTEM = ITEMS.register("totem",
        () -> new TotemItem(new Item.Properties().stacksTo(1), 4.0F, 50,
            BaseWeaponItem.WeaponTier.DIAMOND.getIngredient()));
    
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
        
        // Two-handed weapons
        ITEMS.add(RegistrationHandler.GREAT_SWORD.get());
        ITEMS.add(RegistrationHandler.SCYTHE.get());
        ITEMS.add(RegistrationHandler.GREAT_HAMMER.get());
        ITEMS.add(RegistrationHandler.MORNINGSTAR.get());
        ITEMS.add(RegistrationHandler.BATTLE_AXE.get());
        ITEMS.add(RegistrationHandler.BOW.get());
        ITEMS.add(RegistrationHandler.CROSSBOW.get());
        ITEMS.add(RegistrationHandler.STAFF.get());
        
        // One-handed weapons
        ITEMS.add(RegistrationHandler.WAR_GLAIVE.get());
        ITEMS.add(RegistrationHandler.HAMMER.get());
        ITEMS.add(RegistrationHandler.MACE.get());
        ITEMS.add(RegistrationHandler.AXE.get());
        ITEMS.add(RegistrationHandler.DAGGER.get());
        ITEMS.add(RegistrationHandler.THROWING_WEAPON.get());
        ITEMS.add(RegistrationHandler.WAND.get());
        
        // Defensive items
        ITEMS.add(RegistrationHandler.TOME.get());
        ITEMS.add(RegistrationHandler.SHIELD.get());
        ITEMS.add(RegistrationHandler.RELIC.get());
        ITEMS.add(RegistrationHandler.TOTEM.get());
        
        // Materials and components
        ITEMS.add(RegistrationHandler.WEAPON_CORE.get());
        ITEMS.add(RegistrationHandler.TRAIT_ESSENCE.get());
        ITEMS.add(RegistrationHandler.ENHANCED_COMPONENT.get());
        ITEMS.add(RegistrationHandler.CUSTOMIZATION_STATION_ITEM.get());
        ITEMS.add(RegistrationHandler.WEAPON_FORGE_ITEM.get());
    }
}