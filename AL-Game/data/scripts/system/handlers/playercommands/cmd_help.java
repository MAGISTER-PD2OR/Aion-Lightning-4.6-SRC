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


package playercommands;

import com.aionemu.gameserver.model.Race;
import com.aionemu.gameserver.model.gameobjects.Item;
import com.aionemu.gameserver.model.gameobjects.player.Player;
import com.aionemu.gameserver.utils.PacketSendUtility;
import com.aionemu.gameserver.utils.chathandlers.PlayerCommand;


/**
 * Created by Unreal
 */
public class cmd_help extends PlayerCommand {

    public cmd_help() {
        super("help");
    }

    @Override
    public void execute(Player player, String... params){
        if (params.length != 0) {
            onFail(player, null);
            return;
        }


        if (player.getRace() == Race.ASMODIANS ||player.getRace() == Race.ELYOS){
            PacketSendUtility.sendWhiteMessage(player, "" +
                    "Available Commands for Players!" +
                    "\n========================\n" +
                    " skills , go , asmo , ely , world , gmlist ," +
                    " help , cube ,  drop , enchant , exchange ," +
                    " faction , ffa , goevent , help , job , marry, divorce , medal ," +
                    " noexp , //add , //addset , skin ," +
                    " givestigma , showgp , augmentme , reskinvip, clean, userinfo, event" +
                    "\n========================" +
                    "\nUse a . [dot] before writing most of the commands.\n\nAvailable Instance Commands!" +
                    "\n========================\n" +
                    "checkpoint\n" +
                    "========================");
        }
    }
    public void onFail(Player player, String msg){
        PacketSendUtility.sendWhiteMessage(player, "Syntax : .help");
    }
}
