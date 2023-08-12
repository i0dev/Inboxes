package com.i0dev.inboxes.action;

import com.i0dev.inboxes.entity.Inboxes;
import com.massivecraft.massivecore.chestgui.ChestActionAbstract;
import lombok.AllArgsConstructor;
import lombok.SneakyThrows;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;

import java.util.List;

@AllArgsConstructor
public class ActionInboxClaim extends ChestActionAbstract {

    private int page;
    private int index;
    private Inboxes inbox;

    @Override
    @SneakyThrows
    public boolean onClick(InventoryClickEvent event, Player player) {
        List<ItemStack> inboxItems = inbox.getItems();

        ItemStack item = inboxItems.get(index + (page == 1 ? 0 : ((page - 1) * 45)));
        inbox.removeVoucher(index);

        player.getInventory().addItem(item);
        return false;
    }

}