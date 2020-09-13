package com.github.sokyranthedragon.mia.integrations.jei.categories.lootbag;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.Set;

public class LootBagRegistry
{
    private Set<LootBagEntry> registry;
    private static LootBagRegistry instance;
    
    private LootBagRegistry()
    {
    }
    
    public static LootBagRegistry getInstance()
    {
        if (instance != null)
            return instance;
        instance = new LootBagRegistry();
        instance.registry = new LinkedHashSet<>();
        return instance;
    }
    
    @SuppressWarnings("UnusedReturnValue")
    public boolean registerLootBagRecipe(@Nullable LootBagEntry entry)
    {
        return entry != null && registry.add(entry);
    }
    
    @Nonnull
    public Set<LootBagEntry> getRecipes()
    {
        return registry;
    }
    
    @Nonnull
    public static Set<LootBagEntry> getRecipesOrEmpty()
    {
        return getInstance() != null ? getInstance().getRecipes() : new HashSet<>();
    }
    
    public void clear()
    {
        registry.clear();
    }
}
