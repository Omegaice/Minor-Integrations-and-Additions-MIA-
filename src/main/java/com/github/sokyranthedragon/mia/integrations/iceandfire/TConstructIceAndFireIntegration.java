package com.github.sokyranthedragon.mia.integrations.iceandfire;

import com.github.alexthe666.iceandfire.compat.tinkers.TinkersCompat;
import com.github.alexthe666.iceandfire.item.IafItemRegistry;
import com.github.sokyranthedragon.mia.integrations.ModIds;
import com.github.sokyranthedragon.mia.integrations.tconstruct.ITConstructIntegration;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import slimeknights.mantle.util.RecipeMatch;
import slimeknights.tconstruct.library.TinkerRegistry;
import slimeknights.tconstruct.library.materials.Material;
import slimeknights.tconstruct.library.smeltery.MeltingRecipe;
import slimeknights.tconstruct.shared.TinkerFluids;

import javax.annotation.Nonnull;

class TConstructIceAndFireIntegration implements ITConstructIntegration
{
    // Can be obtained by either smelting sapphires, its block or ore, or by killing ice villagers
//    public static FluidMolten moltenSapphire;
    
    @Override
    public void init(FMLInitializationEvent event)
    {
        // Styphalian feathers
        TinkerRegistry.registerMelting(new MeltingRecipe(RecipeMatch.of(IafItemRegistry.stymphalian_bird_feather, Material.VALUE_Nugget), TinkerFluids.bronze));
        TinkerRegistry.registerMelting(new MeltingRecipe(RecipeMatch.of(IafItemRegistry.stymphalian_feather_bundle, Material.VALUE_Nugget * 8), TinkerFluids.bronze));
        TinkerRegistry.registerMelting(new MeltingRecipe(RecipeMatch.of(IafItemRegistry.stymphalian_feather_dagger, Material.VALUE_Nugget * 2), TinkerFluids.bronze));
        
        // Chain
        TinkerRegistry.registerMelting(new MeltingRecipe(RecipeMatch.of(IafItemRegistry.chain, Material.VALUE_Nugget * 4 * 4), TinkerFluids.iron));
        TinkerRegistry.registerMelting(new MeltingRecipe(RecipeMatch.of(IafItemRegistry.chain_sticky, Material.VALUE_Nugget * 4 * 4), TinkerFluids.iron));
        
        // Hippogryph armor
        TinkerRegistry.registerMelting(new MeltingRecipe(RecipeMatch.of(IafItemRegistry.iron_hippogryph_armor, Material.VALUE_Ingot * 4), TinkerFluids.iron));
        TinkerRegistry.registerMelting(new MeltingRecipe(RecipeMatch.of(IafItemRegistry.gold_hippogryph_armor, Material.VALUE_Ingot * 4), TinkerFluids.gold));
        
        // Fire Dragonsteel tools
        TinkerRegistry.registerMelting(new MeltingRecipe(RecipeMatch.of(IafItemRegistry.dragonsteel_fire_axe, Material.VALUE_Ingot * 3), TinkersCompat.MOLTEN_FIRE_DRAGONSTEEL));
        TinkerRegistry.registerMelting(new MeltingRecipe(RecipeMatch.of(IafItemRegistry.dragonsteel_fire_hoe, Material.VALUE_Ingot * 2), TinkersCompat.MOLTEN_FIRE_DRAGONSTEEL));
        TinkerRegistry.registerMelting(new MeltingRecipe(RecipeMatch.of(IafItemRegistry.dragonsteel_fire_pickaxe, Material.VALUE_Ingot * 3), TinkersCompat.MOLTEN_FIRE_DRAGONSTEEL));
        TinkerRegistry.registerMelting(new MeltingRecipe(RecipeMatch.of(IafItemRegistry.dragonsteel_fire_shovel, Material.VALUE_Ingot), TinkersCompat.MOLTEN_FIRE_DRAGONSTEEL));
        TinkerRegistry.registerMelting(new MeltingRecipe(RecipeMatch.of(IafItemRegistry.dragonsteel_fire_sword, Material.VALUE_Ingot * 2), TinkersCompat.MOLTEN_FIRE_DRAGONSTEEL));
        
        // Ice Dragonsteel tools
        TinkerRegistry.registerMelting(new MeltingRecipe(RecipeMatch.of(IafItemRegistry.dragonsteel_ice_axe, Material.VALUE_Ingot * 3), TinkersCompat.MOLTEN_ICE_DRAGONSTEEL));
        TinkerRegistry.registerMelting(new MeltingRecipe(RecipeMatch.of(IafItemRegistry.dragonsteel_ice_hoe, Material.VALUE_Ingot * 2), TinkersCompat.MOLTEN_ICE_DRAGONSTEEL));
        TinkerRegistry.registerMelting(new MeltingRecipe(RecipeMatch.of(IafItemRegistry.dragonsteel_ice_pickaxe, Material.VALUE_Ingot * 3), TinkersCompat.MOLTEN_ICE_DRAGONSTEEL));
        TinkerRegistry.registerMelting(new MeltingRecipe(RecipeMatch.of(IafItemRegistry.dragonsteel_ice_shovel, Material.VALUE_Ingot), TinkersCompat.MOLTEN_ICE_DRAGONSTEEL));
        TinkerRegistry.registerMelting(new MeltingRecipe(RecipeMatch.of(IafItemRegistry.dragonsteel_ice_sword, Material.VALUE_Ingot * 2), TinkersCompat.MOLTEN_ICE_DRAGONSTEEL));


//        TinkerRegistry.registerEntityMelting(EntitySnowVillager.class, moltenSapphire);
    }
    
    @Nonnull
    @Override
    public ModIds getModId()
    {
        return ModIds.ICE_AND_FIRE;
    }
}
