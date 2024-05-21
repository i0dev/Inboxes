package com.i0dev.inboxes;

import com.i0dev.inboxes.entity.*;
import com.i0dev.inboxes.integration.PlaceholderAPI;
import com.massivecraft.massivecore.MassivePlugin;
import com.massivecraft.massivecore.collections.MassiveList;
import com.sk89q.worldedit.bukkit.WorldEditPlugin;
import org.bukkit.Bukkit;
import org.bukkit.plugin.Plugin;

import java.util.List;

public class InboxesPlugin extends MassivePlugin {

    private static InboxesPlugin i;

    public InboxesPlugin() {
        InboxesPlugin.i = this;
    }

    public static InboxesPlugin get() {
        return i;
    }

    @Override
    public void onEnableInner() {
        this.activateAuto();
    }


    @Override
    public void onEnable() {
        super.onEnable();
        if (Bukkit.getPluginManager().getPlugin("PlaceholderAPI") != null) {
            new PlaceholderAPI(this).register();
        } else {
            getLogger().warning("Could not find PlaceholderAPI! This plugin is required.");
        }
    }

    @Override
    public List<Class<?>> getClassesActiveColls() {
        return new MassiveList<>(
                MConfColl.class,
                MLangColl.class,
                MPlayerColl.class,
                InboxesColl.class
        );
    }
}