package com.vortexwinds.weaponryascended;

import com.mojang.logging.LogUtils;
import com.vortexwinds.weaponryascended.registry.RegistrationHandler;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.item.CreativeModeTabs;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.config.ModConfig;
import net.neoforged.fml.event.lifecycle.FMLClientSetupEvent;
import net.neoforged.fml.event.lifecycle.FMLCommonSetupEvent;
import net.neoforged.fml.javafmlmod.FMLJavaModLoadingContext;
import net.neoforged.neoforge.event.BuildCreativeModeTabContentsEvent;
import org.slf4j.Logger;

// The value here should match an entry in the META-INF/mods.toml file
@Mod(WeaponryAscended.MODID)
public class WeaponryAscended
{
    // Define mod id in a common place for everything to reference
    public static final String MODID = "weaponryascended";

    // Directly reference a slf4j logger
    private static final Logger LOGGER = LogUtils.getLogger();

    // Registry system is handled by RegistrationHandler class

    // The constructor for the mod class is the first code that runs when the mod is loaded.
    // FML will parse some annotations and give us an instance to work with, IMODBUS is that instance
    public WeaponryAscended(IEventBus modEventBus, ModContainer container)
    {
        // Register the commonSetup method for modloading
        modEventBus.addListener(this::commonSetup);
        
        // Register the registry handler
        RegistrationHandler.register(modEventBus);

        // Register ourselves for server and other game events we are interested in.
        // Note that you should refer to Minecaft itself to see what events are fired during game loop

        // Register the item to a creative tab
        modEventBus.addListener(this::addCreative);

        // Register our mod's ForgeConfigSpec so that Forge can create and load the config file for us
        container.registerConfig(ModConfig.Type.COMMON, Config.SPEC);
    }

    // The common setup method is used to set up initial configuration and setup the static instance.
    private void commonSetup(final FMLCommonSetupEvent event)
    {
        // Some common setup code
        LOGGER.info("HELLO FROM COMMON SETUP");

        LOGGER.info("DIRT BLOCK >> {}", Registries.BLOCK.getKey(net.minecraft.world.level.Blocks.DIRT));
    }

    // Add items to creative tabs
    private void addCreative(BuildCreativeModeTabContentsEvent event)
    {
        if (event.getTabKey() == CreativeModeTabs.COMBAT) {
            event.accept(RegistrationHandler.CUSTOM_SWORD);
            event.accept(RegistrationHandler.CUSTOM_DAGGER);
            event.accept(RegistrationHandler.CUSTOM_WARHAMMER);
        }
        
        // Add blocks to building blocks tab
        if (event.getTabKey() == CreativeModeTabs.FUNCTIONAL_BLOCKS) {
            event.accept(RegistrationHandler.CUSTOMIZATION_STATION_ITEM);
            event.accept(RegistrationHandler.WEAPON_FORGE_ITEM);
        }
        
        // Add materials to materials tab
        if (event.getTabKey() == CreativeModeTabs.MATERIALS) {
            event.accept(RegistrationHandler.WEAPON_CORE);
            event.accept(RegistrationHandler.TRAIT_ESSENCE);
            event.accept(RegistrationHandler.ENHANCED_COMPONENT);
        }
    }

    // You can use EventBusSubscriber to automatically register all static methods in the class annotated with @SubscribeEvent
    @net.neoforged.fml.event.lifecycle.ModEventBusSubscriber(modid = MODID, bus = net.neoforged.fml.event.lifecycle.ModEventBusSubscriber.Bus.MOD, value = net.minecraft.world.Distance.Dist.CLIENT)
    public static class ClientModEvents
    {
        @net.neoforged.fml.event.lifecycle.SubscribeEvent
        public static void onClientSetup(FMLClientSetupEvent event)
        {
            // Some client setup code
            LOGGER.info("HELLO FROM CLIENT SETUP");
            LOGGER.info("MINECRAFT VERSION >> {}", net.minecraft.MinecraftVersion.getInstance().getReleaseTarget());
        }
    }
}