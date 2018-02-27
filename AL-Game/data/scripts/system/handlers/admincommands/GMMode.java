package admincommands;

import com.aionemu.gameserver.model.gameobjects.player.Player;
import com.aionemu.gameserver.network.aion.serverpackets.SM_MOTION;
import com.aionemu.gameserver.network.aion.serverpackets.SM_PLAYER_INFO;
import com.aionemu.gameserver.utils.PacketSendUtility;
import com.aionemu.gameserver.utils.audit.GMService;
import com.aionemu.gameserver.utils.chathandlers.AdminCommand;

/**
 * @author Eloann
 *
 */
public class GMMode extends AdminCommand {

    public GMMode() {
        super("gm");
    }

    @Override
    public void execute(Player admin, String... params) {
        if (admin.getAccessLevel() < 1) {
            PacketSendUtility.sendMessage(admin, "You cannot use this command.");
            return;
        }

        if (params.length != 2) {
            onFail(admin, null);
            return;
        }

        if (params[0].toLowerCase().equals("on")) {
            if (params[1].equals("y")){
                GMService.getInstance().onPlayerAvailable(admin); //send available message
                admin.setWispable();
            } else if (params[1].toLowerCase().equals("n")) {
                PacketSendUtility.sendMessage(admin, "You are Back Online");
                admin.setWispable();
            } else {
                admin.setWispable();
                PacketSendUtility.sendMessage(admin, "You are Back Online with GM Tag");
            }

            if (!admin.isGmMode()) {
                admin.setGmMode(true);


                GMService.getInstance().onPlayerLogin(admin); //put gm into gmlist

                admin.clearKnownlist();
                PacketSendUtility.sendPacket(admin, new SM_PLAYER_INFO(admin, false));
                PacketSendUtility.sendPacket(admin, new SM_MOTION(admin.getObjectId(), admin.getMotions().getActiveMotions()));
                admin.updateKnownlist();
                PacketSendUtility.sendMessage(admin, "you are now Available and Wispable by players");

            }
        }
        if (params[0].equals("off")) {
            if (params[1].toLowerCase().equals("y")){
                GMService.getInstance().onPlayerUnavailable(admin); //send unavailable message
                admin.setUnWispable();
                GMService.getInstance().onPlayerLogedOut(admin); //remove gm into gmlist
            } else if (params[1].toLowerCase().equals("n")) {
                PacketSendUtility.sendMessage(admin, "You are in Offline Status");
                PacketSendUtility.sendMessage(admin, "you are now Unavailable but can be Whisperable by players");
            } else {
                PacketSendUtility.sendMessage(admin, "You are Offline without GM Tag, But people can Whisper you.");
            }
            if (admin.isGmMode()) {
                admin.setGmMode(false);




                admin.clearKnownlist();
                PacketSendUtility.sendPacket(admin, new SM_PLAYER_INFO(admin, false));
                PacketSendUtility.sendPacket(admin, new SM_MOTION(admin.getObjectId(), admin.getMotions().getActiveMotions()));
                admin.updateKnownlist();
                PacketSendUtility.sendMessage(admin, "You are unavailable to players now.");
            }
        }
    }

    @Override
    public void onFail(Player admin, String message) {
        String syntax = "syntax //gm <on|off> <y/n>\n y = You want to announce the players, that you are On\nAlso your Whisperable state changes to 'Whisperable'\n n = You don't want to announce the players, + You 'Whisperable' State goes Off";
        PacketSendUtility.sendMessage(admin, syntax);
    }
}
