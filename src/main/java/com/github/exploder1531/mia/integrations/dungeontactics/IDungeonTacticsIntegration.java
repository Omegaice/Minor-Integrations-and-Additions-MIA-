package com.github.exploder1531.mia.integrations.dungeontactics;

import com.github.exploder1531.mia.integrations.base.IModIntegration;
import com.github.exploder1531.mia.integrations.dungeontactics.DungeonTactics.ILootBagListener;

import javax.annotation.Nullable;

public interface IDungeonTacticsIntegration extends IModIntegration
{
    enum BagTypes
    {
        ARBOUR,
        BOOK,
        FOOD,
        MAGIC,
        ORE,
        POTION,
        QUIVER,
        RECORD,
        SAMHAIN,
        SOLSTICE,
        TOOL
    }
    
    @Nullable
    ILootBagListener registerLootBagListener();
}
