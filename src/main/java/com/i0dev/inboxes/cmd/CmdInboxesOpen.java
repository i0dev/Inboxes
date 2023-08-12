package com.i0dev.inboxes.cmd;

import com.i0dev.inboxes.entity.Inboxes;
import com.i0dev.inboxes.entity.MPlayer;
import com.massivecraft.massivecore.MassiveException;
import com.massivecraft.massivecore.command.requirement.RequirementIsPlayer;
import com.massivecraft.massivecore.command.type.primitive.TypeInteger;

public class CmdInboxesOpen extends InboxesCommand {

    public CmdInboxesOpen() {
        this.addRequirements(RequirementIsPlayer.get());
        this.addParameter(TypeInteger.get(), "page", "1");
    }

    @Override
    public void perform() throws MassiveException {
        int page = this.readArg(1);
        MPlayer mPlayer = MPlayer.get(me);
        Inboxes inbox = mPlayer.getInbox();
        me.openInventory(inbox.getInventory(page, me.getName()));
    }
}
