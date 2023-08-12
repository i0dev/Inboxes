package com.i0dev.inboxes.entity;

import com.i0dev.inboxes.action.ActionInboxClaim;
import com.i0dev.inboxes.util.Utils;
import com.i0dev.vouchers.entity.Voucher;
import com.i0dev.vouchers.util.ItemBuilder;
import com.i0dev.vouchers.util.Pair;
import com.massivecraft.massivecore.chestgui.ChestGui;
import com.massivecraft.massivecore.collections.MassiveList;
import com.massivecraft.massivecore.pager.Pager;
import com.massivecraft.massivecore.store.Entity;
import lombok.Getter;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.HumanEntity;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.List;

public class Inboxes extends Entity<Inboxes> {

    public static Inboxes get(Object oid) {
        return InboxesColl.get().get(oid);
    }

    public static Inboxes createNewInbox() {
        Inboxes inbox = InboxesColl.get().create();
        return inbox;
    }

    public record InboxInventory(Inventory inventory, int page, String displayName) {
    }


    // voucher, amount
    List<Pair<String, Integer>> vouchers = new ArrayList<>();
    @Getter
    public transient List<InboxInventory> inventoryViewers = new MassiveList<>();


    public List<ItemStack> getItems() {
        List<ItemStack> ret = new MassiveList<>();
        for (Pair<String, Integer> voucher : vouchers) {
            ret.add(Voucher.get(voucher.getKey()).getItemStack(voucher.getValue()));
        }
        return ret;
    }

    public void addVoucher(Voucher voucher, int amount) {
        vouchers.add(new Pair<>(voucher.getId(), amount));
        refreshInboxInventory();
        this.changed();
    }

    public void removeVoucher(int index) {
        vouchers.remove(index);
        refreshInboxInventory();
        this.changed();
    }

    public Inventory getInventory(int page, String displayName) {
        List<ItemStack> inbox = getItems();

        int maxPage = inbox.isEmpty() ? 1 : (int) Math.ceil(inbox.size() / 45.0);
        if (page > maxPage) {
            page = maxPage;
        } else if (page < 1) {
            page = 1;
        }

        Pager<ItemStack> pager = new Pager<>(null, "", page);
        pager.setItems(inbox);
        pager.setHeight(45);

        Inventory inventory = Bukkit.createInventory(null, 54, Utils.color("&2&l" + displayName + "'s Inbox &8(page " + page + "/" + maxPage + ")"));
        ChestGui chestGui = ChestGui.getCreative(inventory);

        chestGui.setAutoclosing(false);
        chestGui.setAutoremoving(true);
        chestGui.setSoundOpen(null);
        chestGui.setSoundClose(null);

        List<ItemStack> items = pager.getPage(page);

        final int tempPage = page;
        for (int i = 45; i < 54; i++) {
            chestGui.getInventory().setItem(i, new ItemBuilder(Material.BLACK_STAINED_GLASS_PANE).addGlow(true).name("&7"));
        }

        chestGui.getInventory().setItem(53, new ItemBuilder(Material.SPECTRAL_ARROW).name("&a&lNext page"));
        chestGui.setAction(53, event -> {
            event.getWhoClicked().openInventory(getInventory(tempPage + 1, displayName));
            return false;
        });

        chestGui.getInventory().setItem(45, new ItemBuilder(Material.ARROW).name("&c&lPrevious page"));
        chestGui.setAction(45, event -> {
            event.getWhoClicked().openInventory(getInventory(tempPage - 1, displayName));
            return false;
        });

        chestGui.getInventory().setItem(49, new ItemBuilder(Material.BARRIER).name("&c&lClose"));
        chestGui.setAction(49, event -> {
            event.getWhoClicked().closeInventory();
            return false;
        });

        if (items != null) {
            for (int i = 0; i < items.size(); i++) {
                ItemStack itemStack = items.get(i);

                chestGui.getInventory().setItem(i, itemStack);
                chestGui.setAction(i, new ActionInboxClaim(page, i, this));
            }
        }

        inventoryViewers.add(new InboxInventory(inventory, page, displayName));
        return inventory;
    }


    private record ToReOpen(List<HumanEntity> people, Integer page, String displayName) {
    }

    public void refreshInboxInventory() {
        List<InboxInventory> toRemove = new ArrayList<>();
        List<ToReOpen> toReOpen = new ArrayList<>();
        for (InboxInventory inventoryViewer : getInventoryViewers()) {
            if (inventoryViewer.inventory().getViewers().isEmpty()) {
                toRemove.add(inventoryViewer);
                continue;
            }
            toReOpen.add(new ToReOpen(new ArrayList<>(inventoryViewer.inventory().getViewers()), inventoryViewer.page(), inventoryViewer.displayName()));
        }

        toReOpen.forEach(obj -> {
            int page = obj.page();
            String displayName = obj.displayName();
            for (HumanEntity humanEntity : obj.people()) {
                humanEntity.openInventory(getInventory(page, displayName));
            }
        });

        inventoryViewers.removeAll(toRemove);
    }


}