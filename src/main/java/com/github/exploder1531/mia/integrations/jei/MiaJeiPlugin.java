package com.github.exploder1531.mia.integrations.jei;

import com.github.exploder1531.mia.config.DungeonTacticsConfiguration;
import com.github.exploder1531.mia.core.MiaBlocks;
import com.github.exploder1531.mia.integrations.ModLoadStatus;
import com.github.exploder1531.mia.integrations.dungeontactics.jei.*;
import mezz.jei.api.IJeiRuntime;
import mezz.jei.api.IModPlugin;
import mezz.jei.api.IModRegistry;
import mezz.jei.api.JEIPlugin;
import mezz.jei.api.ingredients.VanillaTypes;
import mezz.jei.api.recipe.IRecipeCategoryRegistration;
import net.minecraft.item.ItemStack;
import pegbeard.dungeontactics.handlers.DTBlocks;
import pegbeard.dungeontactics.handlers.DTItems;

import java.util.Arrays;

@SuppressWarnings("unused")
@JEIPlugin
public class MiaJeiPlugin implements IModPlugin
{
    private static IJeiRuntime jeiRuntime;
    
    @Override
    public void register(IModRegistry registry)
    {
        if (ModLoadStatus.dungeonTacticsLoaded)
        {
            registry.handleRecipes(CauldronEntry.class, CauldronWrapper::new, Categories.DUNGEON_TACTICS_CAULDRON);
            registry.handleRecipes(LootBagEntry.class, LootBagWrapper::new, Categories.DUNGEON_TACTICS_LOOT_BAG);
            registry.addRecipes(CauldronRegistry.getRecipesOrEmpty(), Categories.DUNGEON_TACTICS_CAULDRON);
            registry.addRecipes(LootBagRegistry.getRecipesOrEmpty(), Categories.DUNGEON_TACTICS_LOOT_BAG);
            
            registry.addRecipeCatalyst(new ItemStack(DTBlocks.ALCHEMYCAULDRON), Categories.DUNGEON_TACTICS_CAULDRON);
            registry.addRecipeCatalyst(new ItemStack(DTItems.BAG_ARBOUR), Categories.DUNGEON_TACTICS_LOOT_BAG);
            registry.addRecipeCatalyst(new ItemStack(DTItems.BAG_BOOK), Categories.DUNGEON_TACTICS_LOOT_BAG);
            registry.addRecipeCatalyst(new ItemStack(DTItems.BAG_FOOD), Categories.DUNGEON_TACTICS_LOOT_BAG);
            registry.addRecipeCatalyst(new ItemStack(DTItems.BAG_MAGIC), Categories.DUNGEON_TACTICS_LOOT_BAG);
            registry.addRecipeCatalyst(new ItemStack(DTItems.BAG_ORE), Categories.DUNGEON_TACTICS_LOOT_BAG);
            registry.addRecipeCatalyst(new ItemStack(DTItems.BAG_POTION), Categories.DUNGEON_TACTICS_LOOT_BAG);
            registry.addRecipeCatalyst(new ItemStack(DTItems.BAG_QUIVER), Categories.DUNGEON_TACTICS_LOOT_BAG);
            registry.addRecipeCatalyst(new ItemStack(DTItems.BAG_RECORD), Categories.DUNGEON_TACTICS_LOOT_BAG);
            registry.addRecipeCatalyst(new ItemStack(DTItems.BAG_SAMHAIN), Categories.DUNGEON_TACTICS_LOOT_BAG);
            registry.addRecipeCatalyst(new ItemStack(DTItems.BAG_SOLSTICE), Categories.DUNGEON_TACTICS_LOOT_BAG);
            registry.addRecipeCatalyst(new ItemStack(DTItems.BAG_TOOL), Categories.DUNGEON_TACTICS_LOOT_BAG);
            
            registry.addIngredientInfo(Arrays.asList(
                    new ItemStack(DTItems.FISH_MUSCLE),
                    new ItemStack(DTItems.FISH_OBSIDIAN),
                    new ItemStack(DTItems.FISH_LAVA),
                    new ItemStack(DTItems.FISH_TUNNEL),
                    new ItemStack(DTItems.FISH_FLYING),
                    new ItemStack(DTItems.FISH_SWIFT),
                    new ItemStack(DTItems.FISH_LUNG)),
                    VanillaTypes.ITEM, "mia.jei.info.dt.fishing");
            
            registry.addIngredientInfo(Arrays.asList(
                    new ItemStack(DTItems.SLINGSHOT),
                    new ItemStack(DTItems.IRONRING)),
                    VanillaTypes.ITEM, "mia.jei.info.dt.fishing_treasure");
            
            registry.addIngredientInfo(new ItemStack(DTItems.REXO_LEGGINGS), VanillaTypes.ITEM, "mia.jei.dt.info.leggings");
            registry.addIngredientInfo(new ItemStack(DTItems.REXO_GOGGLES), VanillaTypes.ITEM, "mia.jei.dt.info.goggles");
            registry.addIngredientInfo(new ItemStack(DTItems.REXO_BOOTS), VanillaTypes.ITEM, "mia.jei.dt.info.boots");
            
            registry.addIngredientInfo(new ItemStack(DTItems.ESCAPEROPE), VanillaTypes.ITEM, "mia.jei.dt.info.rope");
            registry.addIngredientInfo(new ItemStack(DTItems.PHYLACTERY), VanillaTypes.ITEM, "mia.jei.dt.info.phylactery");
            registry.addIngredientInfo(new ItemStack(DTItems.ENDERBAG), VanillaTypes.ITEM, "mia.jei.dt.info.bag_of_hoarding");
            registry.addIngredientInfo(new ItemStack(DTItems.DUCT_TAPE), VanillaTypes.ITEM, "mia.jei.dt.info.duct_tape");
            registry.addIngredientInfo(new ItemStack(DTItems.HEART_DROP), VanillaTypes.ITEM, "mia.jei.dt.info.heart_drop");
            registry.addIngredientInfo(new ItemStack(DTBlocks.LANTERN_IRON), VanillaTypes.ITEM, "mia.jei.dt.info.lantern_iron");
            registry.addIngredientInfo(new ItemStack(DTBlocks.LANTERN_MAGIC), VanillaTypes.ITEM, "mia.jei.dt.info.lantern_magic");
            registry.addIngredientInfo(new ItemStack(DTItems.ENGINEERS_DUNGAREES), VanillaTypes.ITEM, "mia.jei.dt.info.engineer_pants");
            registry.addIngredientInfo(new ItemStack(DTItems.PEG_HAMMER), VanillaTypes.ITEM, "mia.jei.dt.info.peg_hammer");
        }
        if (ModLoadStatus.hatcheryLoaded)
            registry.addIngredientInfo(new ItemStack(MiaBlocks.egg_sorter), VanillaTypes.ITEM, "mia.jei.info.egg_sorter");
        if (ModLoadStatus.iceAndFireLoaded)
            registry.addIngredientInfo(new ItemStack(MiaBlocks.pixie_dust_extractor), VanillaTypes.ITEM, "mia.jei.info.pixie_dust_extractor");
        if (ModLoadStatus.thaumcraftLoaded)
            registry.addIngredientInfo(new ItemStack(MiaBlocks.void_creator), VanillaTypes.ITEM, "mia.jei.info.void_creator");
    }
    
    @Override
    public void onRuntimeAvailable(IJeiRuntime jeiRuntime)
    {
        MiaJeiPlugin.jeiRuntime = jeiRuntime;
        
        if (!DungeonTacticsConfiguration.enableJeiIntegration)
            hideCategories(Categories.DUNGEON_TACTICS_CAULDRON, Categories.DUNGEON_TACTICS_LOOT_BAG);
    }
    
    @Override
    public void registerCategories(IRecipeCategoryRegistration registry)
    {
        if (ModLoadStatus.dungeonTacticsLoaded)
        {
            registry.addRecipeCategories(new CauldronCategory(registry.getJeiHelpers().getGuiHelper()));
            registry.addRecipeCategories(new LootBagCategory(registry.getJeiHelpers().getGuiHelper()));
        }
    }
    
    public static void hideCategories(String... categories)
    {
        if (categories != null)
        {
            for (String category : categories)
                jeiRuntime.getRecipeRegistry().hideRecipeCategory(category);
        }
    }
    
    public static void unhideCategories(String... categories)
    {
        if (categories != null)
        {
            for (String category : categories)
                jeiRuntime.getRecipeRegistry().unhideRecipeCategory(category);
        }
    }
    
    public static class Categories
    {
        private Categories()
        {
        }
    
        public static final String DUNGEON_TACTICS_CAULDRON = "mia.alchemical_cauldron";
        public static final String DUNGEON_TACTICS_LOOT_BAG = "mia.loot_bag";
    }
}
