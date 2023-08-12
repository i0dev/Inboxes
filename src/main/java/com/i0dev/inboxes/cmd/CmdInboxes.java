package com.i0dev.inboxes.cmd;

import com.i0dev.inboxes.InboxesPlugin;
import com.i0dev.inboxes.Perm;
import com.i0dev.inboxes.entity.MConf;
import com.massivecraft.massivecore.command.MassiveCommandVersion;
import com.massivecraft.massivecore.command.requirement.RequirementHasPerm;

import java.util.List;

public class CmdInboxes extends InboxesCommand {

    private static final CmdInboxes i = new CmdInboxes();

    public static CmdInboxes get() {
        return i;
    }

    public CmdInboxesOpen cmdInboxesOpen = new CmdInboxesOpen();
    public CmdInboxesGive cmdInboxesGive = new CmdInboxesGive();
    public MassiveCommandVersion cmdFactionsVersion = new MassiveCommandVersion(InboxesPlugin.get()).setAliases("v", "version").addRequirements(RequirementHasPerm.get(Perm.VERSION));

    @Override
    public List<String> getAliases() {
        return MConf.get().aliasesInboxes;
    }

}
