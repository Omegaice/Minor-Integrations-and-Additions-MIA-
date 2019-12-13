package com.github.exploder1531.mia.integrations.dungeontactics;

import com.github.exploder1531.mia.Mia;
import com.github.exploder1531.mia.integrations.ModIds;
import com.github.exploder1531.mia.integrations.base.IBaseMod;
import com.github.exploder1531.mia.integrations.base.IModIntegration;
import com.github.exploder1531.mia.integrations.dungeontactics.jei.CauldronEntry;
import com.github.exploder1531.mia.integrations.dungeontactics.jei.CauldronRegistry;
import com.github.exploder1531.mia.integrations.dungeontactics.jei.LootBagEntry;
import com.github.exploder1531.mia.integrations.dungeontactics.jei.LootBagRegistry;
import com.google.common.collect.Lists;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.storage.loot.LootPool;
import net.minecraftforge.event.LootTableLoadEvent;
import net.minecraftforge.fml.common.Loader;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.oredict.OreDictionary;
import pegbeard.dungeontactics.handlers.DTBlocks;
import pegbeard.dungeontactics.handlers.DTEffects;
import pegbeard.dungeontactics.handlers.DTItems;
import pegbeard.dungeontactics.handlers.DTLoots;

import java.util.List;
import java.util.function.BiConsumer;

import static com.github.exploder1531.mia.config.DungeonTacticsConfiguration.dungeonTacticsAdditionsEnabled;
import static com.github.exploder1531.mia.config.DungeonTacticsConfiguration.registerCustomBagLoot;
import static com.github.exploder1531.mia.integrations.ModLoadStatus.*;

public class DungeonTactics implements IBaseMod
{
    private final List<IDungeonTacticsIntegration> modIntegrations = Lists.newLinkedList();
    
    @Override
    public void register(BiConsumer<String, IModIntegration> modIntegration)
    {
        if (tinkersConstructLoaded)
            modIntegration.accept(ModIds.TINKERS_CONSTRUCT, new TConstructDungeonTacticsIntegration());
        if (thermalExpansionLoaded)
            modIntegration.accept(ModIds.THERMAL_EXPANSION, new ThermalExpansionDungeonTacticsIntegration());
        if (jerLoaded)
            modIntegration.accept(ModIds.JER, new JerDungeonTacticsIntegration());
    }
    
    @Override
    public void addIntegration(IModIntegration integration)
    {
        if (integration instanceof IDungeonTacticsIntegration)
        {
            modIntegrations.add((IDungeonTacticsIntegration) integration);
            return;
        }
        
        Mia.LOGGER.warn("Incorrect DT integration with id of " + integration.getModId() + ": " + integration.toString());
    }
    
    @Override
    public void lootLoad(LootTableLoadEvent event)
    {
        if (!dungeonTacticsAdditionsEnabled || !registerCustomBagLoot)
            return;
        
        LootPool main = event.getTable().getPool("main");
        IDungeonTacticsIntegration.BagTypes bagType = null;
        
        if (event.getName().equals(DTLoots.ARBOUR_LOOT))
            bagType = IDungeonTacticsIntegration.BagTypes.ARBOUR;
        else if (event.getName().equals(DTLoots.BOOK_LOOT))
            bagType = IDungeonTacticsIntegration.BagTypes.BOOK;
        else if (event.getName().equals(DTLoots.FOOD_LOOT))
            bagType = IDungeonTacticsIntegration.BagTypes.FOOD;
        else if (event.getName().equals(DTLoots.MAGIC_LOOT))
            bagType = IDungeonTacticsIntegration.BagTypes.MAGIC;
        else if (event.getName().equals(DTLoots.ORE_LOOT))
            bagType = IDungeonTacticsIntegration.BagTypes.ORE;
        else if (event.getName().equals(DTLoots.POTION_LOOT))
            bagType = IDungeonTacticsIntegration.BagTypes.POTION;
        else if (event.getName().equals(DTLoots.QUIVER_LOOT))
            bagType = IDungeonTacticsIntegration.BagTypes.QUIVER;
        else if (event.getName().equals(DTLoots.RECORD_LOOT))
            bagType = IDungeonTacticsIntegration.BagTypes.RECORD;
        else if (event.getName().equals(DTLoots.SAMHAIN_LOOT))
            bagType = IDungeonTacticsIntegration.BagTypes.SAMHAIN;
        else if (event.getName().equals(DTLoots.SOLSTICE_LOOT))
            bagType = IDungeonTacticsIntegration.BagTypes.SOLSTICE;
        else if (event.getName().equals(DTLoots.TOOL_LOOT))
            bagType = IDungeonTacticsIntegration.BagTypes.TOOL;
        
        if (bagType != null)
        {
            for (IDungeonTacticsIntegration integrations : modIntegrations)
                integrations.insertBagLoot(bagType, main);
        }
    }
    
    @Override
    public void init(FMLInitializationEvent event)
    {
        if (!dungeonTacticsAdditionsEnabled)
            return;
        
        OreDictionary.registerOre("blockGlass", DTBlocks.DUNGEON_GLASS);
        
        OreDictionary.registerOre("listAllberry", DTItems.CHERRYBOMB);
        OreDictionary.registerOre("listAllfruit", DTItems.CHERRYBOMB);
        OreDictionary.registerOre("listAllberry", DTItems.INCINDIBERRY);
        OreDictionary.registerOre("listAllfruit", DTItems.INCINDIBERRY);
        OreDictionary.registerOre("listAllberry", DTItems.GLOWCURRENT);
        OreDictionary.registerOre("listAllfruit", DTItems.GLOWCURRENT);
        
        OreDictionary.registerOre("foodToast", DTItems.TOAST);
        OreDictionary.registerOre("foodToastslice", DTItems.TOASTSLICE);
        OreDictionary.registerOre("foodJamtoast", DTItems.JAMSLICE);
        OreDictionary.registerOre("foodBreadslice", DTItems.BREADSLICE);
    }
    
    @Override
    public void postInit(FMLPostInitializationEvent event)
    {
        if (!dungeonTacticsAdditionsEnabled)
            return;
        
        if (Loader.isModLoaded("jei"))
        {
            CauldronRegistry cauldronRegistry = CauldronRegistry.getInstance();
            
            if (cauldronRegistry != null)
            {
                // Weapon imbuing
                registerImbuing(cauldronRegistry, Items.IRON_SWORD, DTEffects.POISONAILMENT, new ItemStack(DTBlocks.FLOWER_AILMENT));
                registerImbuing(cauldronRegistry, Items.IRON_SWORD, DTEffects.POISONBARK, new ItemStack(DTBlocks.FLOWER_BARK));
                registerImbuing(cauldronRegistry, Items.IRON_SWORD, DTEffects.POISONBRAMBLE, new ItemStack(DTBlocks.FLOWER_BRAMBLE));
                registerImbuing(cauldronRegistry, Items.IRON_SWORD, DTEffects.POISONCINDER, new ItemStack(DTBlocks.FLOWER_CINDER));
                registerImbuing(cauldronRegistry, Items.IRON_SWORD, DTEffects.POISONFADE, new ItemStack(DTBlocks.FLOWER_FADE));
                registerImbuing(cauldronRegistry, Items.IRON_SWORD, DTEffects.POISONFEATHER, new ItemStack(DTBlocks.FLOWER_FEATHER));
                registerImbuing(cauldronRegistry, Items.IRON_SWORD, DTEffects.POISONSANGUINE, new ItemStack(DTBlocks.FLOWER_SANGUINE));
                registerImbuing(cauldronRegistry, Items.IRON_SWORD, DTEffects.POISONTANGLE, new ItemStack(DTBlocks.FLOWER_TANGLE));
                
                // Cooking
                registerCooking(cauldronRegistry, Items.IRON_SHOVEL, Items.GUNPOWDER, new ItemStack(DTItems.CHERRYBOMB, 4), new ItemStack(Items.REDSTONE, 4), new ItemStack(Items.FLINT));
                registerCooking(cauldronRegistry, Items.IRON_SHOVEL, Items.GLOWSTONE_DUST, new ItemStack(DTItems.GLOWCURRENT, 4), new ItemStack(Items.REDSTONE, 4), new ItemStack(Items.BLAZE_POWDER));
                registerCooking(cauldronRegistry, Items.IRON_SHOVEL, Items.BLAZE_POWDER, new ItemStack(DTItems.INCINDIBERRY, 4), new ItemStack(Items.REDSTONE, 4), new ItemStack(Items.COAL));
                // 1 to 3 + fortune level
                registerCooking(cauldronRegistry, Items.IRON_SHOVEL, DTItems.MAGIC_POWDER, new ItemStack(Items.GLOWSTONE_DUST), new ItemStack(Blocks.RED_MUSHROOM), new ItemStack(Blocks.BROWN_MUSHROOM));
                registerCooking(cauldronRegistry, Items.IRON_SHOVEL, new ItemStack(Items.CLAY_BALL, 4), new ItemStack(Items.SLIME_BALL, 3), new ItemStack(Blocks.SAND));
                registerCooking(cauldronRegistry, Items.IRON_SHOVEL, Items.LEATHER, Items.SLIME_BALL, new ItemStack(Items.ROTTEN_FLESH, 3), new ItemStack(Items.SUGAR, 2));
                // Any (vanilla) leaves
                registerCooking(cauldronRegistry, Items.IRON_SHOVEL, new ItemStack(Blocks.DIRT), new ItemStack(Blocks.LEAVES, 2, OreDictionary.WILDCARD_VALUE), new ItemStack(Blocks.SAND));
                registerCooking(cauldronRegistry, Items.IRON_SHOVEL, new ItemStack(Blocks.MYCELIUM), new ItemStack(Blocks.DIRT), new ItemStack(Blocks.RED_MUSHROOM, 2), new ItemStack(Blocks.BROWN_MUSHROOM, 2));
            }
            else
                Mia.LOGGER.error("Could not access Alchemical Cauldron recipe registry, this shouldn't have happened as Dungeon Tactics is loaded. Something is very wrong.");
            
            LootBagRegistry lootBagRegistry = LootBagRegistry.getInstance();
            
            if (lootBagRegistry != null)
            {
                registerLootBag(lootBagRegistry, DTItems.BAG_ARBOUR, DTLoots.ARBOUR_LOOT);
                registerLootBag(lootBagRegistry, DTItems.BAG_BOOK, DTLoots.BOOK_LOOT);
                registerLootBag(lootBagRegistry, DTItems.BAG_FOOD, DTLoots.FOOD_LOOT);
                registerLootBag(lootBagRegistry, DTItems.BAG_MAGIC, DTLoots.MAGIC_LOOT);
                registerLootBag(lootBagRegistry, DTItems.BAG_ORE, DTLoots.ORE_LOOT);
                registerLootBag(lootBagRegistry, DTItems.BAG_POTION, DTLoots.POTION_LOOT);
                registerLootBag(lootBagRegistry, DTItems.BAG_QUIVER, DTLoots.QUIVER_LOOT);
                registerLootBag(lootBagRegistry, DTItems.BAG_RECORD, DTLoots.RECORD_LOOT);
                registerLootBag(lootBagRegistry, DTItems.BAG_SAMHAIN, DTLoots.SAMHAIN_LOOT);
                registerLootBag(lootBagRegistry, DTItems.BAG_SOLSTICE, DTLoots.SOLSTICE_LOOT);
                registerLootBag(lootBagRegistry, DTItems.BAG_TOOL, DTLoots.TOOL_LOOT);
            }
            else if (jerLoaded)
                Mia.LOGGER.error("Could not access Loot Bag recipe registry, this shouldn't have happened as Dungeon Tactics and JER are loaded. Something is very wrong.");
        }
    }
    
    @SuppressWarnings("SameParameterValue")
    private void registerImbuing(CauldronRegistry registry, Item weapon, Enchantment enchantment, ItemStack... input)
    {
        ItemStack output = new ItemStack(weapon);
        output.addEnchantment(enchantment, 1);
        registry.registerCauldronRecipe(new CauldronEntry(new ItemStack(weapon), output, input));
    }
    
    private void registerCooking(CauldronRegistry registry, Item spoon, Item output, ItemStack... input)
    {
        registerCooking(registry, spoon, output, null, input);
    }
    
    private void registerCooking(CauldronRegistry registry, Item spoon, ItemStack output, ItemStack... input)
    {
        registerCooking(registry, spoon, output, null, input);
    }
    
    private void registerCooking(CauldronRegistry registry, Item spoon, Item output, Item byproduct, ItemStack... input)
    {
        registerCooking(registry, spoon, new ItemStack(output), byproduct, input);
    }
    
    private void registerCooking(CauldronRegistry registry, Item spoon, ItemStack output, Item byproduct, ItemStack... input)
    {
        if (byproduct != null)
            registry.registerCauldronRecipe(new CauldronEntry(new ItemStack(spoon), output, new ItemStack(byproduct), input));
        else
            registry.registerCauldronRecipe(new CauldronEntry(new ItemStack(spoon), output, input));
    }
    
    private void registerLootBag(LootBagRegistry registry, Item lootBag, ResourceLocation possibleLoot)
    {
        List<LootBagEntry> entries = LootBagEntry.getEntries(new ItemStack(lootBag), possibleLoot);
        
        for (LootBagEntry entry : entries)
            registry.registerLootBagRecipe(entry);
    }
}
