package com.github.sokyranthedragon.mia.events;

import baubles.api.BaublesApi;
import baubles.api.cap.IBaublesItemHandler;
import com.github.sokyranthedragon.mia.Mia;
import com.github.sokyranthedragon.mia.capabilities.MusicPlayerCapabilityProvider;
import com.github.sokyranthedragon.mia.client.input.MiaKeyBindings;
import com.github.sokyranthedragon.mia.core.MiaItems;
import com.github.sokyranthedragon.mia.gui.GuiHandler;
import com.github.sokyranthedragon.mia.handlers.MusicPlayerStackHandler;
import com.github.sokyranthedragon.mia.integrations.ModIds;
import com.github.sokyranthedragon.mia.network.MessageSyncMusicPlayer;
import com.github.sokyranthedragon.mia.utilities.InventoryUtils;
import com.github.sokyranthedragon.mia.utilities.MusicUtils;
import net.minecraft.client.Minecraft;
import net.minecraft.client.audio.PositionedSoundRecord;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import org.apache.commons.lang3.tuple.ImmutableTriple;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Mod.EventBusSubscriber(modid = Mia.MODID)
public class ClientEvents
{
    @SubscribeEvent(priority = EventPriority.LOWEST)
    @SideOnly(Side.CLIENT)
    public static void keyInput(TickEvent.ClientTickEvent event)
    {
        EntityPlayerSP player = Minecraft.getMinecraft().player;
        
        if (player == null)
            return;
        
        if (Minecraft.getMinecraft().gameSettings.keyBindDrop.isKeyDown())
        {
            MusicPlayerStackHandler capability = player.getHeldItemMainhand().getCapability(MusicPlayerCapabilityProvider.ITEM_HANDLER_CAPABILITY, null);
            
            if (capability != null)
                MusicUtils.stopSong(capability);
        }
        
        boolean openGuiPressed = MiaKeyBindings.openGui.isPressed();
        boolean musicTogglePressed = MiaKeyBindings.musicToggle.isPressed();
        boolean nextSongPressed = MiaKeyBindings.nextSong.isPressed();
        boolean previousSongPressed = MiaKeyBindings.previousSong.isPressed();
        
        if (openGuiPressed || musicTogglePressed || nextSongPressed || previousSongPressed)
        {
            ImmutableTriple<ItemStack, Integer, Integer> itemInInventory = InventoryUtils.findItemInInventory(player, MiaItems.musicPlayer);
            MusicPlayerStackHandler capability = itemInInventory.left.getCapability(MusicPlayerCapabilityProvider.ITEM_HANDLER_CAPABILITY, null);
            
            if (!itemInInventory.left.isEmpty() && capability != null)
            {
                if (openGuiPressed)
                    //noinspection SuspiciousNameCombination
                    player.openGui(Mia.instance, GuiHandler.MUSIC_PLAYER, player.world, itemInInventory.middle, itemInInventory.right, 0);
                
                if (musicTogglePressed)
                {
                    MusicUtils.toggleSong(capability);
                    Mia.network.sendToServer(new MessageSyncMusicPlayer(itemInInventory.middle, itemInInventory.right, capability, false));
                }
                else if (nextSongPressed)
                {
                    MusicUtils.playNext(capability);
                    Mia.network.sendToServer(new MessageSyncMusicPlayer(itemInInventory.middle, itemInInventory.right, capability, false));
                }
                else if (previousSongPressed)
                {
                    MusicUtils.playPrevious(capability);
                    Mia.network.sendToServer(new MessageSyncMusicPlayer(itemInInventory.middle, itemInInventory.right, capability, false));
                }
            }
        }
        
        if (player.ticksExisted % 20 == 0)
            return;
        
        MusicUtils.listener.updateTimers();
        
        Set<UUID> uuidList = new HashSet<>();
        
        if (ModIds.BAUBLES.isLoaded)
        {
            IBaublesItemHandler baubles = BaublesApi.getBaublesHandler(player);
            
            for (int i = 0; i < baubles.getSlots(); i++)
            {
                ItemStack stack = baubles.getStackInSlot(i);
                if (stack.getItem() == MiaItems.musicPlayer)
                    handleMusicPlayerVerification(stack, uuidList, 3, i);
            }
        }
        
        for (int i = 0; i < player.inventory.mainInventory.size(); i++)
        {
            ItemStack stack = player.inventory.mainInventory.get(i);
            if (stack.getItem() == MiaItems.musicPlayer)
                handleMusicPlayerVerification(stack, uuidList, 2, i);
        }
        
        if (player.getHeldItemOffhand().getItem() == MiaItems.musicPlayer)
            handleMusicPlayerVerification(player.getHeldItemOffhand(), uuidList, 1, 0);
    }
    
    @SideOnly(Side.CLIENT)
    private static void handleMusicPlayerVerification(ItemStack item, Set<UUID> uuidList, int type, int slot)
    {
        MusicPlayerStackHandler capability = item.getCapability(MusicPlayerCapabilityProvider.ITEM_HANDLER_CAPABILITY, null);
        
        if (capability != null)
        {
            if (uuidList.contains(capability.itemUuid))
            {
                // I can't really think of any other place where I used do...while loops instead of while/for...
                do
                    capability.itemUuid = UUID.randomUUID();
                while (uuidList.contains(capability.itemUuid));
                Mia.network.sendToServer(new MessageSyncMusicPlayer(type, slot, capability, true));
            }
            else if (MusicUtils.listener.startedPlaying(capability.itemUuid))
            {
                PositionedSoundRecord currentSong = MusicUtils.currentlyPlayedSongs.get(capability.itemUuid);
                
                if (currentSong != null && !Minecraft.getMinecraft().getSoundHandler().isSoundPlaying(currentSong))
                {
                    if (capability.autoplay)
                    {
                        if (capability.repeat)
                            MusicUtils.toggleSong(capability);
                        else if (capability.shuffle)
                            MusicUtils.randomNext(capability);
                        else
                            MusicUtils.playNext(capability);
                        
                        Mia.network.sendToServer(new MessageSyncMusicPlayer(type, slot, capability, false));
                    }
                    else
                        MusicUtils.stopSong(capability);
                }
            }
            
            uuidList.add(capability.itemUuid);
        }
    }
}