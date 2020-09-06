package com.github.sokyranthedragon.mia.config;

import com.github.sokyranthedragon.mia.Mia;
import net.minecraftforge.common.config.Config;
import net.minecraftforge.common.config.ConfigManager;
import net.minecraftforge.fml.client.event.ConfigChangedEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import static net.minecraftforge.common.config.Config.*;

@Config(modid = Mia.MODID, name = "mia/quark")
@LangKey("mia.config.quark.title")
@Mod.EventBusSubscriber(modid = Mia.MODID)
public class QuarkConfiguration
{
    @Name("Enable Quark additions")
    @Comment("Set to false to completely disable new Quark additions")
    @LangKey("mia.config.quark.additions_enabled")
    @RequiresMcRestart
    public static boolean quarkAdditionsEnabled = true;
    
    @Name("Enable external integrations")
    @Comment("Set to false to prevent other mods from integrating with Quark")
    @LangKey("mia.config.shared.enable_external_integrations")
    @RequiresMcRestart
    public static boolean externalIntegrationsEnabled = true;
    
    @Name("Enable JER integration")
    @Comment("Set to false to completely disable integration with JER")
    @LangKey("mia.config.shared.enable_jer_integration")
    @RequiresMcRestart
    public static boolean enableJerIntegration = true;
    
    @Name("Enable Thermal Expansion integration")
    @Comment("Set to false to completely disable integration with Thermal Expansion")
    @LangKey("mia.config.shared.enable_thermal_expansion_integration")
    @RequiresMcRestart
    public static boolean enableTeIntegration = true;
    
    @Name("Enable XU2 integration")
    @Comment("Set to false to completely disable integration with XU2")
    @LangKey("mia.config.shared.enable_extra_utils_integration")
    @RequiresMcRestart
    public static boolean enableXu2Integration = true;
    
    @Name("Enable Hatchery integration")
    @Comment("Set to false to completely disable integration with Dungeon Tactics")
    @LangKey("mia.config.shared.enable_dungeon_tactics_integration")
    @RequiresMcRestart
    public static boolean enableDungeonTacticsIntegration = true;
    
    @Name("Add supported ancient tomes")
    @Comment("If enabled, all enchantments from supported mods will be automatically added as ancient tomes")
    @LangKey("mia.config.quark.add_ancient_tomes")
    @RequiresMcRestart
    public static boolean addAncientTomes = true;
    
    @Name("Add enchanted book item tooltips for supported mods")
    @Comment("If enabled, items from supported mods will be automatically added as enchanted book tooltips")
    @LangKey("mia.config.quark.add_item_tooltip")
    public static boolean addItemTooltips = true;
    
    @Name("Add ancient tome crafting in supported mods")
    @Comment("If enabled, all supported mods that allow for enchanted book crafting will have ancient tomes recipes added")
    @LangKey("mia.config.quark.ancient_tomes_crafting")
    public static boolean ancientTomesCrafting = true;
    
    @Config.Name("Enable FutureMC integration")
    @Config.Comment("Set to false to completely disable integration with FutureMC")
    @Config.LangKey("mia.config.shared.enable_future_mc_integration")
    @Config.RequiresMcRestart
    public static boolean enableFutureMcIntegration = true;
    
    @Config.Name("Enable Chisel integration")
    @Config.Comment("Set to false to completely disable integration with Chisel")
    @Config.LangKey("mia.config.shared.enable_chisel_integration")
    @Config.RequiresMcRestart
    public static boolean enableChiselIntegration = true;
    
    @Name("Enable Industrial Foregoing integration")
    @Comment("Set to false to completely disable integration with Industrial Foregoing")
    @LangKey("mia.config.shared.enable_if_integration")
    @RequiresMcRestart
    public static boolean enableIFIntegration = true;
    
    @Config.Name("Enable Tinker's Construct integration")
    @Config.Comment("Set to false to completely disable integration with Tinker's Construct")
    @Config.LangKey("mia.config.shared.enable_tconstruct_integration")
    @Config.RequiresMcRestart
    public static boolean enableTConstructIntegration = true;
    
    
    /**
     * Inject the new values and save to the config file when the config has been changed from the GUI.
     *
     * @param event The event
     */
    @SubscribeEvent
    public static void onConfigChanged(final ConfigChangedEvent.OnConfigChangedEvent event)
    {
        if (event.getModID().equals(Mia.MODID))
            ConfigManager.sync(Mia.MODID, Type.INSTANCE);
    }
}
