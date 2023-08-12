package com.i0dev.inboxes.cmd;

import com.i0dev.inboxes.entity.MPlayer;
import com.i0dev.vouchers.cmd.type.TypeVoucher;
import com.i0dev.vouchers.entity.Voucher;
import com.massivecraft.massivecore.MassiveException;
import com.massivecraft.massivecore.command.Visibility;
import com.massivecraft.massivecore.command.requirement.RequirementIsPlayer;
import com.massivecraft.massivecore.command.type.primitive.TypeInteger;
import com.massivecraft.massivecore.command.type.sender.TypePlayer;
import org.bukkit.entity.Player;

public class CmdInboxesGive extends InboxesCommand {

    public CmdInboxesGive() {
        this.addParameter(TypePlayer.get(), "player");
        this.addParameter(TypeVoucher.get(), "voucher");
        this.addParameter(TypeInteger.get(), "amount", "1");
        this.setVisibility(Visibility.INVISIBLE);
    }

    @Override
    public void perform() throws MassiveException {
        Player player = this.readArg();
        Voucher voucher = this.readArg();
        int amount = this.readArg(1);

        if (voucher == null) {
            msg("&cVoucher not found.");
            return;
        }

        if (amount < 1) {
            msg("&cAmount must be greater than 0.");
            return;
        }

        if (amount > 64) {
            msg("&cAmount must be less than 64 due to Minecraft limitations.");
            return;
        }

        MPlayer.get(player).getInbox().addVoucher(voucher, amount);
        msg("&aAdded " + amount + " " + voucher.getId() + " to " + player.getName() + "'s inbox.");
    }
}
