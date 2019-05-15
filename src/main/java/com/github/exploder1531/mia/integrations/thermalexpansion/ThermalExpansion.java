package com.github.exploder1531.mia.integrations.thermalexpansion;

import cofh.thermalexpansion.util.managers.machine.SmelterManager;
import com.github.exploder1531.mia.Mia;
import com.github.exploder1531.mia.config.TeConfiguration;
import com.github.exploder1531.mia.integrations.base.IBaseMod;
import com.github.exploder1531.mia.integrations.base.IModIntegration;
import com.google.common.collect.Lists;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;

import java.util.List;

import static com.github.exploder1531.mia.config.TeConfiguration.teAdditionsEnabled;

public class ThermalExpansion implements IBaseMod
{
    private List<IThermalExpansionIntegration> modIntegrations = Lists.newLinkedList();
    
    @Override
    public void addIntegration(IModIntegration integration)
    {
        if (!TeConfiguration.externalIntegrationsEnabled)
            return;
        
        if (integration instanceof IThermalExpansionIntegration)
            modIntegrations.add((IThermalExpansionIntegration)integration);
    
        Mia.LOGGER.warn("Incorrect Thermal Foundation integration with id of " + integration.getModId() + ": " + integration.toString());
    }
    
    @Override
    public void init(FMLInitializationEvent event)
    {
        if (teAdditionsEnabled)
        {
            int energy = 6_000;
            ItemStack ingot = new ItemStack(Items.IRON_INGOT);
            SmelterManager.addRecycleRecipe(energy, new ItemStack(Items.CHAINMAIL_HELMET), ingot, 2);
            SmelterManager.addRecycleRecipe(energy, new ItemStack(Items.CHAINMAIL_CHESTPLATE), ingot, 4);
            SmelterManager.addRecycleRecipe(energy, new ItemStack(Items.CHAINMAIL_LEGGINGS), ingot, 3);
            SmelterManager.addRecycleRecipe(energy, new ItemStack(Items.CHAINMAIL_BOOTS), ingot, 2);
        }
        
        for (IThermalExpansionIntegration integration : modIntegrations)
            integration.addRecipes();
    }
}
