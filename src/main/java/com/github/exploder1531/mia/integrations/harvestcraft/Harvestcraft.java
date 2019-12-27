package com.github.exploder1531.mia.integrations.harvestcraft;

import com.github.exploder1531.mia.integrations.ModIds;
import com.github.exploder1531.mia.integrations.base.IBaseMod;
import com.github.exploder1531.mia.integrations.base.IModIntegration;
import com.pam.harvestcraft.blocks.FruitRegistry;
import com.pam.harvestcraft.item.ItemRegistry;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.oredict.OreDictionary;

import java.util.function.BiConsumer;

import static com.github.exploder1531.mia.config.HarvestcraftConfiguration.enableTeIntegration;
import static com.github.exploder1531.mia.config.HarvestcraftConfiguration.harvestcraftAdditionsEnabled;
import static com.github.exploder1531.mia.integrations.ModLoadStatus.*;

public class Harvestcraft implements IBaseMod
{
    @Override
    public void register(BiConsumer<String, IModIntegration> modIntegration)
    {
        if (enableTeIntegration && thermalExpansionLoaded)
            modIntegration.accept(ModIds.THERMAL_EXPANSION, new ThermalExpansionHarvestcraftIntegration());
        if (jerLoaded)
            modIntegration.accept(ModIds.JER, new JerHarvestcraftIntegration());
        if (jeiLoaded)
            modIntegration.accept(ModIds.JEI, new JeiHarvestcraftIntegration());
    }
    
    @Override
    public void init(FMLInitializationEvent event)
    {
        if (!harvestcraftAdditionsEnabled)
            return;
        
        OreDictionary.registerOre("egg", ItemRegistry.rawtofeegItem);
        
        OreDictionary.registerOre("treeSapling", FruitRegistry.getSapling(FruitRegistry.SPIDERWEB));
        OreDictionary.registerOre("treeSapling", FruitRegistry.getSapling(FruitRegistry.AVOCADO));
        OreDictionary.registerOre("treeSapling", FruitRegistry.getSapling(FruitRegistry.WALNUT));
    }
}
