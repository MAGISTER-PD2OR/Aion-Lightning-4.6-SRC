package playercommands;

import com.aionemu.gameserver.model.gameobjects.player.Player;
import com.aionemu.gameserver.utils.PacketSendUtility;
import com.aionemu.gameserver.utils.chathandlers.PlayerCommand;

public class cmd_goevent extends PlayerCommand {

    public cmd_goevent() {
        super("goevent");
    }

    @Override
    public void execute(final Player player, String... params) {

        if (player.isLookingForEvent()) {
            player.setLookingForEvent(false);
            PacketSendUtility.sendMessage(player, "You have leave the event waiting list.");
        } else {
            player.setLookingForEvent(true);
            PacketSendUtility.sendMessage(player, "You are in event waiting list.");
        }
    }

    @Override
    public void onFail(Player player, String message) {
        PacketSendUtility.sendMessage(player, "Syntax: .goevent ");
    }
}
