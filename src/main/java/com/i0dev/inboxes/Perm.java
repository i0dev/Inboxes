package com.i0dev.inboxes;

import com.massivecraft.massivecore.Identified;
import com.massivecraft.massivecore.util.PermissionUtil;
import org.bukkit.permissions.Permissible;

public enum Perm implements Identified {

    BASECOMMAND("basecommand"),

    GIVE("give"),
    OPEN("open"),

    VERSION("version");

    private final String id;

    Perm(String id) {
        this.id = "inboxes." + id;
    }

    @Override
    public String getId() {
        return id;
    }

    public boolean has(Permissible permissible, boolean verboose) {
        return PermissionUtil.hasPermission(permissible, this, verboose);
    }

    public boolean has(Permissible permissible) {
        return PermissionUtil.hasPermission(permissible, this);
    }

}
