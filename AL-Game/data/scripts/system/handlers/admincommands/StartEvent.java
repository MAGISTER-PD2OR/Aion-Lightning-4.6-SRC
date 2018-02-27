package admincommands;

import com.aionemu.gameserver.model.gameobjects.player.Player;
import com.aionemu.gameserver.network.aion.serverpackets.SM_PLAYER_SPAWN;
import com.aionemu.gameserver.services.teleport.TeleportService2;
import com.aionemu.gameserver.utils.PacketSendUtility;
import com.aionemu.gameserver.world.World;
import com.aionemu.gameserver.utils.chathandlers.AdminCommand;

public class StartEvent extends AdminCommand {

    public StartEvent() {
        super("startevent");
    }

    @Override
    public void execute(Player admin, String... params) {

        if (params.length < 3) {
            PacketSendUtility.sendMessage(admin, "syntax : //startevent <Check isLookingforevent 0:False|1:True> <0:Elyos|1:Asmos|3:All> <0:Start|1:End>");
            return;
        }

        int playerscount = 0;

        switch (Integer.parseInt(params[1])) {
            // All Players
            case 3:
                for (final Player p : World.getInstance().getAllPlayers()) {
                    if (Integer.parseInt(params[2]) == 0) {
                        if (!p.equals(admin)) {
                            TeleportService2.teleportTo(p, admin.getWorldId(), admin.getX(), admin.getY(), admin.getZ(), admin.getHeading());
                            playerscount++;
                            PacketSendUtility.sendPacket(p, new SM_PLAYER_SPAWN(p));
                            PacketSendUtility.sendMessage(p, "You have been teleported to the event of " + admin.getName() + ".");
                        }
                    } else {
                        if (p.isLookingForEvent()) {
                            if (!p.equals(admin)) {
                                if (p.getCommonData().getRace().getRaceId() == Integer.parseInt(params[1])) {
                                    TeleportService2.moveToBindLocation(p, true);
                                    playerscount++;
                                    PacketSendUtility.sendMessage(p, "You have been teleported to the event of" + admin.getName() + ".");
                                    p.setLookingForEvent(false);
                                    PacketSendUtility.sendMessage(p, "You do not wait for an event.");
                                }
                            }
                        }
                    }
                }
            //Specified Faction
            default:
                switch (Integer.parseInt(params[0])) {
                    //Not check boolean
                    case 0:
                        for (final Player p : World.getInstance().getAllPlayers()) {
                            if (Integer.parseInt(params[2]) == 0) {
                                if (!p.equals(admin)) {
                                    if (p.getCommonData().getRace().getRaceId() == Integer.parseInt(params[1])) {
                                        TeleportService2.teleportTo(p, admin.getWorldId(), admin.getInstanceId(), admin.getX(), admin.getY(), admin.getZ(), admin.getHeading());
                                        playerscount++;
                                        PacketSendUtility.sendPacket(p, new SM_PLAYER_SPAWN(p));
                                        PacketSendUtility.sendMessage(p, "You have been teleported to the event of" + admin.getName() + ".");
                                    }
                                }
                            } else {
                                if (p.isLookingForEvent()) {
                                    if (!p.equals(admin)) {
                                        if (p.getCommonData().getRace().getRaceId() == Integer.parseInt(params[1])) {
                                            TeleportService2.moveToBindLocation(p, true);
                                            playerscount++;
                                            PacketSendUtility.sendMessage(p, "You have been teleported to the event of " + admin.getName() + ".");
                                            p.setLookingForEvent(false);
                                            PacketSendUtility.sendMessage(p, "You do not wait for an event .");
                                        }
                                    }
                                }
                            }
                        }
                    //Check Boolean
                    case 1:
                        for (final Player p : World.getInstance().getAllPlayers()) {

                            if (Integer.parseInt(params[2]) == 0) {
                                if (p.isLookingForEvent()) {
                                    if (!p.equals(admin)) {
                                        if (p.getCommonData().getRace().getRaceId() == Integer.parseInt(params[1])) {
                                            TeleportService2.teleportTo(p, admin.getWorldId(), admin.getInstanceId(), admin.getX(), admin.getY(), admin.getZ(), admin.getHeading());
                                            playerscount++;
                                            PacketSendUtility.sendPacket(p, new SM_PLAYER_SPAWN(p));
                                            PacketSendUtility.sendMessage(p, "You have been teleported to the event of " + admin.getName() + ".");
                                        }
                                    }
                                }
                            } else {
                                if (p.isLookingForEvent()) {
                                    if (!p.equals(admin)) {
                                        if (p.getCommonData().getRace().getRaceId() == Integer.parseInt(params[0])) {
                                            TeleportService2.moveToBindLocation(p, true);
                                            playerscount++;
                                            PacketSendUtility.sendMessage(p, "You have been teleported to the event of" + admin.getName() + ".");
                                            p.setLookingForEvent(false);
                                            PacketSendUtility.sendMessage(p, "You do not wait for an event .");
                                        }
                                    }
                                }
                            }
                        }
                }
        }
        PacketSendUtility.sendMessage(admin, playerscount + " players were teleported");
    }
}
