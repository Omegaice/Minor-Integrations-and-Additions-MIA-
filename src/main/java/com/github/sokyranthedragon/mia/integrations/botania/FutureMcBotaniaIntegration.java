package com.github.sokyranthedragon.mia.integrations.botania;

import com.github.sokyranthedragon.mia.integrations.ModIds;
import com.github.sokyranthedragon.mia.integrations.futuremc.IFutureMcIntegration;
import net.minecraft.item.ItemStack;
import vazkii.botania.common.block.ModBlocks;
import vazkii.botania.common.block.ModFluffBlocks;

import javax.annotation.Nonnull;

import static com.github.sokyranthedragon.mia.integrations.futuremc.FutureMc.addStonecutterRecipes;

class FutureMcBotaniaIntegration implements IFutureMcIntegration
{
    @Override
    public void addRecipes()
    {
        // Livingrock
        addStonecutterRecipes(new ItemStack(ModBlocks.livingrock),
                new ItemStack(ModBlocks.livingrock, 1, 1),
                new ItemStack(ModBlocks.livingrock, 1, 4),
                new ItemStack(ModFluffBlocks.livingrockSlab, 2),
                new ItemStack(ModFluffBlocks.livingrockStairs),
                new ItemStack(ModFluffBlocks.livingrockWall),
                new ItemStack(ModFluffBlocks.livingrockBrickSlab, 2),
                new ItemStack(ModFluffBlocks.livingrockBrickStairs));
        addStonecutterRecipes(new ItemStack(ModBlocks.livingrock, 1, 1),
                new ItemStack(ModFluffBlocks.livingrockBrickSlab, 2),
                new ItemStack(ModFluffBlocks.livingrockBrickStairs));
        
        // All the quartz types
        addStonecutterRecipes(new ItemStack(ModFluffBlocks.darkQuartz),
                new ItemStack(ModFluffBlocks.darkQuartz, 1, 1),
                new ItemStack(ModFluffBlocks.darkQuartz, 1, 2),
                new ItemStack(ModFluffBlocks.darkQuartzSlab, 2),
                new ItemStack(ModFluffBlocks.darkQuartzStairs));
        addStonecutterRecipes(new ItemStack(ModFluffBlocks.manaQuartz),
                new ItemStack(ModFluffBlocks.manaQuartz, 1, 1),
                new ItemStack(ModFluffBlocks.manaQuartz, 1, 2),
                new ItemStack(ModFluffBlocks.manaQuartzSlab, 2),
                new ItemStack(ModFluffBlocks.manaQuartzStairs));
        addStonecutterRecipes(new ItemStack(ModFluffBlocks.blazeQuartz),
                new ItemStack(ModFluffBlocks.blazeQuartz, 1, 1),
                new ItemStack(ModFluffBlocks.blazeQuartz, 1, 2),
                new ItemStack(ModFluffBlocks.blazeQuartzSlab, 2),
                new ItemStack(ModFluffBlocks.blazeQuartzStairs));
        addStonecutterRecipes(new ItemStack(ModFluffBlocks.lavenderQuartz),
                new ItemStack(ModFluffBlocks.lavenderQuartz, 1, 1),
                new ItemStack(ModFluffBlocks.lavenderQuartz, 1, 2),
                new ItemStack(ModFluffBlocks.lavenderQuartzSlab, 2),
                new ItemStack(ModFluffBlocks.lavenderQuartzStairs));
        addStonecutterRecipes(new ItemStack(ModFluffBlocks.redQuartz),
                new ItemStack(ModFluffBlocks.redQuartz, 1, 1),
                new ItemStack(ModFluffBlocks.redQuartz, 1, 2),
                new ItemStack(ModFluffBlocks.redQuartzSlab, 2),
                new ItemStack(ModFluffBlocks.redQuartzStairs));
        addStonecutterRecipes(new ItemStack(ModFluffBlocks.elfQuartz),
                new ItemStack(ModFluffBlocks.elfQuartz, 1, 1),
                new ItemStack(ModFluffBlocks.elfQuartz, 1, 2),
                new ItemStack(ModFluffBlocks.elfQuartzSlab, 2),
                new ItemStack(ModFluffBlocks.elfQuartzStairs));
        addStonecutterRecipes(new ItemStack(ModFluffBlocks.sunnyQuartz),
                new ItemStack(ModFluffBlocks.sunnyQuartz, 1, 1),
                new ItemStack(ModFluffBlocks.sunnyQuartz, 1, 2),
                new ItemStack(ModFluffBlocks.sunnyQuartzSlab, 2),
                new ItemStack(ModFluffBlocks.sunnyQuartzStairs));
        
        // Metamorphic stones
        for (int i = 0; i < 8; i++)
        {
            addStonecutterRecipes(new ItemStack(ModFluffBlocks.biomeStoneA, 1, i + 8),
                    new ItemStack(ModFluffBlocks.biomeStoneStairs[i + 8]),
                    new ItemStack(ModFluffBlocks.biomeStoneSlabs[i + 8], 2),
                    new ItemStack(ModFluffBlocks.biomeStoneWall, 1, i));
            addStonecutterRecipes(new ItemStack(ModFluffBlocks.biomeStoneA, 1, i),
                    new ItemStack(ModFluffBlocks.biomeStoneB, 1, i),
                    new ItemStack(ModFluffBlocks.biomeStoneB, 1, i + 8),
                    new ItemStack(ModFluffBlocks.biomeStoneStairs[i]),
                    new ItemStack(ModFluffBlocks.biomeStoneSlabs[i], 2),
                    new ItemStack(ModFluffBlocks.biomeStoneStairs[i + 16]),
                    new ItemStack(ModFluffBlocks.biomeStoneSlabs[i + 16], 2));
            addStonecutterRecipes(new ItemStack(ModFluffBlocks.biomeStoneB, 1, i),
                    new ItemStack(ModFluffBlocks.biomeStoneB, 1, i + 8),
                    new ItemStack(ModFluffBlocks.biomeStoneStairs[i + 16]),
                    new ItemStack(ModFluffBlocks.biomeStoneSlabs[i + 16], 2));
        }
        
        // Pavement
        for (int i = 0; i < ModFluffBlocks.pavementSlabs.length; i++)
        {
            addStonecutterRecipes(new ItemStack(ModFluffBlocks.pavement, 1, i),
                    new ItemStack(ModFluffBlocks.pavementStairs[i]),
                    new ItemStack(ModFluffBlocks.pavementSlabs[i], 2));
        }
        
        // Shimmerrock
        addStonecutterRecipes(new ItemStack(ModBlocks.shimmerrock),
                new ItemStack(ModFluffBlocks.shimmerrockStairs),
                new ItemStack(ModFluffBlocks.shimmerrockSlab, 2));
    }
    
    @Override
    @Nonnull
    public ModIds getModId()
    {
        return ModIds.BOTANIA;
    }
}
