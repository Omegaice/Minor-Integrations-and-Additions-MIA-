package com.github.exploder1531.mia.block;

import com.gendeathrow.hatchery.Hatchery;
import com.github.exploder1531.mia.Mia;
import com.github.exploder1531.mia.gui.GuiHandler;
import com.github.exploder1531.mia.tile.TileEggSorter;
import com.github.exploder1531.mia.utilities.InventoryUtils;
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

@SuppressWarnings("WeakerAccess")
public class BlockEggSorter extends BlockBase implements ITileEntityProvider
{
    public BlockEggSorter()
    {
        this(Material.WOOD);
    }
    
    // It's here just in case there's ever need to override this block
    public BlockEggSorter(Material material)
    {
        super(material, "egg_sorter");
        
        setHardness(1.5f);
        setHarvestLevel("axe", 0);
        setCreativeTab(Hatchery.hatcheryTabs);
    }
    
    @Nullable
    @Override
    public TileEntity createNewTileEntity(@Nonnull World worldIn, int meta)
    {
        return new TileEggSorter();
    }
    
    @Override
    public boolean hasTileEntity(IBlockState state)
    {
        return true;
    }
    
    @Override
    public void breakBlock(@Nonnull World worldIn, @Nonnull BlockPos pos, @Nonnull IBlockState state)
    {
        TileEntity tile = worldIn.getTileEntity(pos);
        
        if (tile instanceof IInventory)
            InventoryUtils.dropInventoryItems(worldIn, pos, ((IInventory) tile));
        
        super.breakBlock(worldIn, pos, state);
        worldIn.removeTileEntity(pos);
    }
    
    @Override
    public boolean onBlockActivated(World world, BlockPos pos, IBlockState state, EntityPlayer player, EnumHand hand, EnumFacing side, float hitX, float hitY, float hitZ)
    {
        if (!world.isRemote)
            player.openGui(Mia.instance, GuiHandler.EGG_SORTER, world, pos.getX(), pos.getY(), pos.getZ());
        
        return true;
    }
}
