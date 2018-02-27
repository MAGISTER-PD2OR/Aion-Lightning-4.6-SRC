package admincommands;

import com.aionemu.gameserver.utils.chathandlers.AdminCommand;
import com.aionemu.gameserver.model.gameobjects.player.Player;
import com.aionemu.gameserver.utils.PacketSendUtility;
import com.aionemu.gameserver.utils.Util;
import com.aionemu.gameserver.world.World;
import pirate.events.EventManager;
import pirate.events.enums.EventType;


/**
 * @author flashman
 * @convered2aionemu Kill3r
 */
public class EventUtil extends AdminCommand {

    private static final StringBuilder info = new StringBuilder();

    static {
        info.append("Information About EManager:\n");
        info.append("//emanager - You'll see the information you're reading! o.o\n");
        info.append("//emanager start <event type> - If the event is not running, You start an event using this, specifying with event!\n");
        info.append("//emanager rcd all - removes the cooldown of all players in the whole event, that who attended to an event!\n");
        info.append("//emanager rcd <event type> <player name> - removes the cooldown of event for the specified target player\n");
        info.append("Available Event Types(event type):\n");
        for (EventType et : EventType.values()) {
            if (et.IsDone()) {
                info.append(et.getEventTemplate().getCmdName()).append("\n");
            }
        }
    }

    public EventUtil() {
        super("emanager");
    }




    @Override
    public void execute(Player admin, String... params) {
        if (params.length == 0) {
            showCommandInfo(admin);
        } else {
            // start event cmd
            if (params[0].equals("start")) {
                if (admin.getAccessLevel() < 1) {
                    PacketSendUtility.sendMessage(admin, "You cannot use this command!.");
                    return;
                }
                EventType et = parseType(params[1]);
                if (et == null) {
                    PacketSendUtility.sendMessage(admin, "Wrong Event Type.");
                    return;
                }
                PacketSendUtility.sendMessage(admin, EventManager.getInstance().CMD_StartEvent(et));
                return;
            }
            // remove event cd cmd
            if (params[0].equals("rcd")) {
                Player p;
                EventType type;
                if (params.length == 2 && params[1].equals("all")) {
                    for (EventType et : EventType.values()) {
                        EventManager.getInstance().createNewEventSession(et);
                    }
                } else if (params.length == 3) {
                    type = parseType(params[1]);
                    p = World.getInstance().findPlayer(Util.convertName(params[2]));
                    if (type == null) {
                        PacketSendUtility.sendMessage(admin, "Wrong Event Type.");
                        return;
                    }
                    EventManager.getInstance().removePlayerFromPlayedList(p, type);
                }
                return;
            }
            PacketSendUtility.sendMessage(admin, "Player cannot found.");
        }
    }

    private void showCommandInfo(Player p) {
        PacketSendUtility.sendMessage(p, info.toString());
    }

    private EventType parseType(String str) {
        for (EventType et : EventType.values()) {
            if (!et.IsDone()) {
                continue;
            }
            if (str.equals(et.getEventTemplate().getCmdName())) {
                return et;
            }
        }
        return null;
    }
}
