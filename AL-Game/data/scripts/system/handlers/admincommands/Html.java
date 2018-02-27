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

import com.aionemu.gameserver.cache.HTMLCache;
import com.aionemu.gameserver.model.gameobjects.player.Player;
import com.aionemu.gameserver.services.HTMLService;
import com.aionemu.gameserver.utils.PacketSendUtility;
import com.aionemu.gameserver.utils.chathandlers.AdminCommand;
import com.aionemu.gameserver.world.World;
import com.aionemu.gameserver.world.knownlist.Visitor;

import java.util.Iterator;


/**
 * @author lord_rex
 */
public class Html extends AdminCommand {

	public Html() {
		super("html");
	}

	@Override
	public void execute(Player player, String... params) {
		if (params == null || params.length < 1) {
			PacketSendUtility.sendMessage(player, "Usage: //html <reload|show>");
			return;
		}

        if(params[0].equals("test")){
            if(params.length >= 2){
                try{
                    String url = params[1];

                    Iterator<Player> ita = World.getInstance().getPlayersIterator();

                    while(ita.hasNext()){
                        Player p1 = ita.next();
                        if(player.getWorldId() == p1.getWorldId()){
                            HTMLService.showHTML(p1, "<poll>\n" +
                                    "\t<poll_introduction>\n" +
                                    "\t\t\n" +
                                    "\t\t<![CDATA[<h1 style=\"text-align: center;\"><span style=\"color:#FF0000;\">Insane Aion URL Maker!</span></h1>]]>\n" +
                                    "\t</poll_introduction>\n" +
                                    "\t<poll_title>\n" +
                                    "\t\t<font color='ffc519'></font>\n" +
                                    "\t</poll_title>\n" +
                                    "\t<questions><question>\n" +
                                    "\t<title>\n" +
                                    "\t\t<![CDATA[\n" +
                                    "<p style=\"margin-left: 40px;\"><span style=\"color:#000000;\"><strong>Here&#39;s the URL that was Created :</strong></span></p>\n" +
                                    "\n" +
                                    "<p style=\"margin-left: 40px;\"><span style=\"color:#000000;\"><strong>You can go to this URL by just simply clicking it. :)</strong></span></p>\n" +
                                    "\n" +
                                    "<p style=\"margin-left: 40px;\"><strong><span style=\"color:#0000FF;\"><a href=\""+url+"\">"+url+"</a></span></strong></p>\n" +
                                    "\n" +
                                    "<p style=\"margin-left: 40px;\"><strong><span style=\"color:#FF0000;\">Thanks Insane PvPers!!</span></strong></p>" +
                                    "\t\t]]>\n" +
                                    "\t</title>\n" +
                                    "\t<select>\n" +
                                    "\t\t<input type='radio'>Thanks</input>\n" +
                                    "\t</select>\n" +
                                    "\t</question></questions>\n" +
                                    "</poll>\n");
                            PacketSendUtility.sendMessage(player, "This player got: " +p1.getName());
                        }
                    }

                }catch (NumberFormatException e){
                    PacketSendUtility.sendMessage(player, "Wrong URL");
                    return;
                }


                PacketSendUtility.sendMessage(player, "done");
            }

            return;
        }

		if (params[0].equals("reload")) {
			HTMLCache.getInstance().reload(true);
			PacketSendUtility.sendMessage(player, HTMLCache.getInstance().toString());
		} else if (params[0].equals("show")) {
			if (params.length >= 2) {
				HTMLService.showHTML(player, HTMLCache.getInstance().getHTML(params[1] + ".xhtml"));
			} else {
				PacketSendUtility.sendMessage(player, "Usage: //html show <filename>");
			}
		}
	}

	@Override
	public void onFail(Player player, String message) {
		PacketSendUtility.sendMessage(player, "Usage: //html <reload|show>");
	}
}
