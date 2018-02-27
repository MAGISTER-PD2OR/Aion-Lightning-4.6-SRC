package admincommands;

import java.util.Collection;

import com.aionemu.gameserver.model.Race;
import com.aionemu.gameserver.model.gameobjects.player.Player;
import com.aionemu.gameserver.utils.PacketSendUtility;
import com.aionemu.gameserver.utils.chathandlers.AdminCommand;
import com.aionemu.gameserver.world.World;
import com.aionemu.gameserver.world.WorldMapType;

/**
 * @author Antraxx
 */
public class PlayerList extends AdminCommand {

    public PlayerList() {
        super("playerlist");
    }

    @Override
    public void execute(Player player, String... params) {
        if (params == null || params.length < 1) {
            PacketSendUtility.sendMessage(player, "syntax //playerlist <all|ely|asmo|premium|vip>");
            return;
        }

        // get all currently connected players
        Collection<Player> players = World.getInstance().getAllPlayers();
        PacketSendUtility.sendMessage(player, "Currently connected players:");

<<<<<<< .mine
        for (Player p : players) {
            if (params != null && params.length > 0) {
                String cmd = params[0].toLowerCase().trim();
                if (("ely").startsWith(cmd)) {
                    if (p.getCommonData().getRace() == Race.ASMODIANS) {
                        continue;
                    }
                }
                if (("asmo").startsWith(cmd)) {
                    if (p.getCommonData().getRace() == Race.ELYOS) {
                        continue;
                    }
                }
                if (("premium").startsWith(cmd)) {
                    if (p.getPlayerAccount().getMembership() == 2) {
                        continue;
                    }
                }
                if (("vip").startsWith(cmd)) {
                    if (p.getPlayerAccount().getMembership() == 1) {
                        continue;
                    }
                }
            }
=======
		for (Player p : players) {
			if (params.length > 0) {
				String cmd = params[0].toLowerCase().trim();
				if (("ely").startsWith(cmd)) {
					if (p.getCommonData().getRace() == Race.ASMODIANS) {
						continue;
					}
				}
				if (("asmo").startsWith(cmd)) {
					if (p.getCommonData().getRace() == Race.ELYOS) {
						continue;
					}
				}
				if (("premium").startsWith(cmd)) {
					if (p.getPlayerAccount().getMembership() == 2) {
						continue;
					}
				}
				if (("vip").startsWith(cmd)) {
					if (p.getPlayerAccount().getMembership() == 1) {
						continue;
					}
				}
			}
>>>>>>> .r274

            PacketSendUtility.sendMessage(
                    player,
                    "Char: "
                    + p.getName() + " (" + p.getAcountName() + ") "
                    + " - "
                    + p.getCommonData().getRace().name() + "/"
                    + p.getCommonData().getPlayerClass().name()
                    + " - Location: " + WorldMapType.getWorld(p.getWorldId()).name());
        }
    }

    @Override
    public void onFail(Player player, String message) {
        PacketSendUtility.sendMessage(player, "syntax //playerlist <all|ely|asmo|premium|vip>");
    }
}
