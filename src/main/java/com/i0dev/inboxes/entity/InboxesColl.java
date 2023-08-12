package com.i0dev.inboxes.entity;

import com.massivecraft.massivecore.store.Coll;

public class InboxesColl extends Coll<Inboxes> {

    private static final InboxesColl i = new InboxesColl();

    public static InboxesColl get() {
        return i;
    }

    @Override
    public void onTick()
    {
        super.onTick();
    }

}
