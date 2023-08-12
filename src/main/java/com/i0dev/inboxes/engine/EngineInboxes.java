package com.i0dev.inboxes.engine;

import com.i0dev.inboxes.util.Utils;
import com.massivecraft.massivecore.Engine;
import com.massivecraft.massivecore.chestgui.ChestGui;
import com.massivecraft.massivecore.pager.Pager;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.List;

public class EngineInboxes extends Engine {

    public static EngineInboxes i = new EngineInboxes();

    public static EngineInboxes get() {
        return i;
    }



}
