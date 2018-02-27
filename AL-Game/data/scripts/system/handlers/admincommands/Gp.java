/**
 * This file is part of Aion-Lightning <aion-lightning.org>.
 *
 *  Aion-Lightning is free software: you can redistribute it and/or modify
 *  it under the terms of the GNU General Public License as published by
 *  the Free Software Foundation, either version 3 of the License, or
 *  (at your option) any later version.
 *
 *  Aion-Lightning is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  GNU General Public License for more details. *
 *  You should have received a copy of the GNU General Public License
 *  along with Aion-Lightning.
 *  If not, see <http://www.gnu.org/licenses/>.
 */


package admincommands;

import com.aionemu.commons.utils.Rnd;
import com.aionemu.gameserver.configs.main.FastTrackConfig;
import com.aionemu.gameserver.model.DialogAction;
import com.aionemu.gameserver.model.Race;
import com.aionemu.gameserver.model.gameobjects.player.Player;
import com.aionemu.gameserver.model.utils3d.Point3D;
import com.aionemu.gameserver.network.aion.serverpackets.SM_QUIT_RESPONSE;
import com.aionemu.gameserver.network.aion.serverpackets.SM_SERVER_IDS;
import com.aionemu.gameserver.services.abyss.AbyssPointsService;
import com.aionemu.gameserver.services.ecfunctions.ffa.FFaStruct;
import com.aionemu.gameserver.services.instance.InstanceService;
import com.aionemu.gameserver.services.teleport.TeleportService2;
import com.aionemu.gameserver.skillengine.SkillEngine;
import com.aionemu.gameserver.utils.PacketSendUtility;
import com.aionemu.gameserver.utils.Util;
import com.aionemu.gameserver.utils.chathandlers.AdminCommand;
import com.aionemu.gameserver.world.World;
import com.aionemu.gameserver.world.WorldMapInstance;
import com.aionemu.gameserver.world.WorldMapType;

import java.util.concurrent.ExecutionException;

/**
 * Created by Kill3r
 */
public class Gp extends AdminCommand {
    public Gp() {
        super("gp");
    }

    public void execute(Player admin, String...params){


     if (params.length < 1){
            PacketSendUtility.sendMessage(admin, "Synax : //gp < add | remove > <playername> <value>\n=========================\n < add | remove > : type Add or Remove. \n <playername> : The players name you want to add/remove gp.\n <value> : The amount of GloryPoint you want to add/remove.\n" +
                    "=========================");
            return;
        }

        int value;
        try{
            value = Integer.parseInt(params[2]);
        }catch(NumberFormatException e){
            PacketSendUtility.sendMessage(admin, "You need to right a number on <value>.");
            return;
        }

        if(params[0].equals("add")){
            if (admin.getAccessLevel() > 1){
                    Player player = World.getInstance().findPlayer(Util.convertName(params[1]));
                if (player == null) {
                    PacketSendUtility.sendMessage(admin, "The specified player is not online.");
                    return;
                }
                int gpafter;
                gpafter = player.getAbyssRank().getGp() + value;
                PacketSendUtility.sendMessage(player, "You received "+value+" glory Point(s) from "+admin.getName()+".");
                PacketSendUtility.sendMessage(admin, "Account Name : "+ player.getPlayerAccount().getName() + "\n" + player.getName() + " has " + player.getAbyssRank().getGp() + " GP. Adding " + value +". ( total : "+ gpafter + " GP now)");
                AbyssPointsService.addGp(player, value);
                PacketSendUtility.sendMessage(admin, "Sucessfully added "+value+" Glory Point(s).");
                }
            }


        if (params[0].equals("remove")){
            Player player = World.getInstance().findPlayer(Util.convertName(params[1]));
            if (player == null) {
                PacketSendUtility.sendMessage(admin, "The specified player is not online.");
                return;
            }
            int gpbefore;
            gpbefore = player.getAbyssRank().getGp() - value;
            PacketSendUtility.sendMessage(player, "You're glory points has been reduced by "+value+" from "+admin.getName()+".");
            PacketSendUtility.sendMessage(admin, "Account Name : "+ player.getPlayerAccount().getName() + "\n" + player.getName() + " has " + player.getAbyssRank().getGp() + " GP. Removing " + value+ ". ( total : "+ gpbefore + " GP now)");
            AbyssPointsService.addGp(player, -value);
            PacketSendUtility.sendMessage(admin, "Sucessfully Removed "+value+" Glory Point(s).");
         }




       }

    public void onFail(Player admin, String msg){
        PacketSendUtility.sendMessage(admin, "Synax : //gp < add | remove > <playername> <value>\n=========================\n < add | remove > : type Add or Remove. \n <playername> : The players name you want to add/remove gp.\n <value> : The amount of GloryPoint you want to add/remove.\n" +
                "=========================");
        return;
    }


}
