package com.github.sokyranthedragon.mia.integrations.quark;

import com.github.sokyranthedragon.mia.integrations.ModIds;
import com.github.sokyranthedragon.mia.integrations.jer.ExtraConditional;
import com.github.sokyranthedragon.mia.integrations.jer.IJerIntegration;
import com.github.sokyranthedragon.mia.integrations.jer.ResourceLocationWrapper;
import com.github.sokyranthedragon.mia.utilities.QuarkUtils;
import jeresources.api.IDungeonRegistry;
import jeresources.api.IMobRegistry;
import jeresources.api.conditionals.LightLevel;
import jeresources.api.drop.LootDrop;
import jeresources.util.LootTableHelper;
import jeresources.util.MobTableBuilder;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.monster.EntitySkeleton;
import net.minecraft.entity.monster.EntityZombie;
import net.minecraft.init.Biomes;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.storage.loot.LootTableList;
import net.minecraft.world.storage.loot.LootTableManager;
import net.minecraftforge.common.BiomeDictionary;
import vazkii.quark.world.entity.*;
import vazkii.quark.world.feature.*;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import javax.annotation.ParametersAreNonnullByDefault;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@ParametersAreNonnullByDefault
class JerQuarkIntegration implements IJerIntegration
{
    @Nonnull
    @Override
    public Set<Class<? extends EntityLivingBase>> addMobs(MobTableBuilder builder, Set<Class<? extends EntityLivingBase>> ignoreMobOverrides)
    {
        Set<Class<? extends EntityLivingBase>> entries = new HashSet<>();
        
        if (QuarkUtils.isFeatureEnabled(DepthMobs.class))
        {
            if (DepthMobs.enableAshen)
            {
                builder.add(new ResourceLocationWrapper(LootTableList.ENTITIES_SKELETON, 0), EntityAshen.class);
                entries.add(EntityAshen.class);
            }
            if (DepthMobs.enableDweller)
            {
                builder.add(new ResourceLocationWrapper(LootTableList.ENTITIES_ZOMBIE), EntityDweller.class);
                entries.add(EntityDweller.class);
            }
        }
        if (QuarkUtils.isFeatureEnabled(PirateShips.class) && !PirateShips.onlyHat)
        {
            builder.add(new ResourceLocationWrapper(LootTableList.ENTITIES_SKELETON, 1), EntityPirate.class);
            entries.add(EntityPirate.class);
        }
        if (QuarkUtils.isFeatureEnabled(Crabs.class))
        {
            builder.add(EntityCrab.CRAB_LOOT_TABLE, EntityCrab.class);
            entries.add(EntityCrab.class);
        }
        if (QuarkUtils.isFeatureEnabled(Foxhounds.class))
        {
            builder.add(EntityFoxhound.FOXHOUND_LOOT_TABLE, EntityFoxhound.class);
            entries.add(EntityFoxhound.class);
        }
        if (QuarkUtils.isFeatureEnabled(Frogs.class))
        {
            builder.add(EntityFrog.FROG_LOOT_TABLE, EntityFrog.class);
            entries.add(EntityFrog.class);
        }
        if (QuarkUtils.isFeatureEnabled(Stonelings.class))
        {
            builder.add(EntityStoneling.LOOT_TABLE, EntityStoneling.class);
            entries.add(EntityStoneling.class);
        }
        if (QuarkUtils.isFeatureEnabled(Wraiths.class))
        {
            builder.add(EntityWraith.LOOT_TABLE, EntityWraith.class);
            entries.add(EntityWraith.class);
        }
        
        return entries;
    }
    
    @Override
    public void configureMob(ResourceLocation resource, EntityLivingBase entity, @Nullable LootTableManager manager, IMobRegistry mobRegistry)
    {
        LightLevel lightLevel = LightLevel.any;
        Set<Biome> validBiomes = new HashSet<>();
        List<LootDrop> loot = null;
        if (manager != null)
            loot = LootTableHelper.toDrops(manager.getLootTableFromLocation(resource));
        int experienceMin = 1;
        int experienceMax = 3;
        
        if (entity instanceof EntityAshen)
            Collections.addAll(validBiomes, DepthMobs.getBiomesWithMob(EntitySkeleton.class));
        else if (entity instanceof EntityDweller)
            Collections.addAll(validBiomes, DepthMobs.getBiomesWithMob(EntityZombie.class));
        else if (entity instanceof EntityPirate)
        {
            validBiomes.add(Biomes.OCEAN);
            validBiomes.add(Biomes.DEEP_OCEAN);
            
            if (loot != null)
                loot.add(new LootDrop(PirateShips.pirate_hat, 0.085f));
        }
        else if (entity instanceof EntityCrab)
            validBiomes.add(Biomes.BEACH);
        else if (entity instanceof EntityFoxhound || entity instanceof EntityWraith)
            validBiomes.addAll(BiomeDictionary.getBiomes(BiomeDictionary.Type.NETHER));
        else if (entity instanceof EntityFrog)
            validBiomes.add(Biomes.SWAMPLAND);
        else if (entity instanceof EntityStoneling)
        {
            Collections.addAll(validBiomes, DepthMobs.getBiomesWithMob(EntityZombie.class));
            
            if (loot != null)
            {
                List<LootDrop> carryDrops = LootTableHelper.toDrops(manager.getLootTableFromLocation(EntityStoneling.CARRY_LOOT_TABLE));
                for (LootDrop drop : carryDrops)
                    drop.addConditional(ExtraConditional.carryingItem);
                
                loot.addAll(carryDrops);
            }
        }
        
        if (entity instanceof EntityMob)
            experienceMin = experienceMax = 5;
        
        if (loot == null)
        {
            if (validBiomes.isEmpty())
                mobRegistry.register(entity, lightLevel, experienceMin, experienceMax, resource);
            else
                mobRegistry.register(entity, lightLevel, experienceMin, experienceMax, validBiomes.stream().map(Biome::getBiomeName).toArray(String[]::new), resource);
        }
        else
        {
            LootDrop[] drops = loot.toArray(new LootDrop[0]);
            if (validBiomes.isEmpty())
                mobRegistry.register(entity, lightLevel, experienceMin, experienceMax, drops);
            else
                mobRegistry.register(entity, lightLevel, experienceMin, experienceMax, validBiomes.stream().map(Biome::getBiomeName).toArray(String[]::new), drops);
        }
    }
    
    @Override
    public void addDungeonLoot(IDungeonRegistry dungeonRegistry)
    {
        if (QuarkUtils.isFeatureEnabled(PirateShips.class) && !PirateShips.onlyHat)
        {
            final String pirateShip = "chests/quark_pirate_ship";
            
            dungeonRegistry.registerCategory(pirateShip, "mia.jer.dungeon.quark_pirate_ship");
            dungeonRegistry.registerChest(pirateShip, PirateShips.PIRATE_CHEST_LOOT_TABLE);
        }
        if (QuarkUtils.isFeatureEnabled(BuriedTreasure.class))
        {
            final String buriedTreasure = "chests/quark_buried_treasure";
            
            dungeonRegistry.registerCategory(buriedTreasure, "mia.jer.dungeon.quark_buried_treasure");
            dungeonRegistry.registerChest(buriedTreasure, new ResourceLocationWrapper(LootTableList.CHESTS_SIMPLE_DUNGEON));
        }
        if (QuarkUtils.isFeatureEnabled(NetherObsidianSpikes.class) && NetherObsidianSpikes.bigSpikeSpawners && NetherObsidianSpikes.bigSpikeChance > 0)
        {
            final String obsidianSpikes = "chests/quark_nether_obsidian_spikes";
            
            dungeonRegistry.registerCategory(obsidianSpikes, "mia.jer.dungeon.quark_nether_spikes");
            dungeonRegistry.registerChest(obsidianSpikes, new ResourceLocationWrapper(LootTableList.CHESTS_NETHER_BRIDGE));
        }
        if (QuarkUtils.isFeatureEnabled(VariedDungeons.class) && VariedDungeons.lootTable != null)
            dungeonRegistry.registerChest("chests/quark_simple_dungeon", new ResourceLocationWrapper(VariedDungeons.lootTable, -1));
    }
    
    @Nonnull
    @Override
    public ModIds getModId()
    {
        return ModIds.QUARK;
    }
}
