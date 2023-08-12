package com.i0dev.inboxes.entity;

import com.massivecraft.massivecore.store.SenderEntity;
import lombok.Getter;

@Getter
public class MPlayer extends SenderEntity<MPlayer> {

    public static MPlayer get(Object oid) {
        return MPlayerColl.get().get(oid);
    }

    String inboxId;

    public Inboxes getInbox() {
        Inboxes inbox;
        if (getInboxId() == null) {
            inbox = Inboxes.createNewInbox();
            setInboxId(inbox.getId());
        }
        return Inboxes.get(getInboxId());
    }


    public void setInboxId(String inboxId) {
        this.inboxId = inboxId;
        this.changed();
    }
}