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

import com.aionemu.gameserver.dataholders.DataManager;
import com.aionemu.gameserver.dataholders.ItemData;
import com.aionemu.gameserver.model.gameobjects.Item;
import com.aionemu.gameserver.model.gameobjects.player.Player;
import com.aionemu.gameserver.model.templates.item.ItemTemplate;
import com.aionemu.gameserver.services.AdminService;
import com.aionemu.gameserver.services.item.ItemService;
import com.aionemu.gameserver.utils.PacketSendUtility;
import com.aionemu.gameserver.utils.Util;
import com.aionemu.gameserver.utils.chathandlers.AdminCommand;
import com.aionemu.gameserver.world.World;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author Phantom, ATracer, Source
 */
public class Add extends AdminCommand {

    public Add() {
        super("add");
    }

    private static int[] opItemsId = {166030005, 166030007, 169670000, 169670001, 100500595,
            100500596, 100500597, 100900970, 100901015, 100901017 ,100901016, 100901018, 100901242 , 100001276, 100001288, 100001289, 100001290, 167020000,
            167020001, 167020002, 167020003, 167020004 ,167020005 ,167020023 , 122001159, 122001158, 122001157, 122001156,
            122001155, 122000933, 122000934, 122000935, 122000936, 122000937, 122000938, 122000939, 122000940, 122000941,
            122000942, 122000943, 122000944, 122000945, 122000946, 122000947, 122000948, 122000949, 122000950, 122000951,
            122000952, 122000953, 122000954, 122000955, 122000956, 122000957, 122000958, 122000959, 122000960, 122000961,
            122000962, 122000963, 122000964, 122000965, 122000966, 122000967, 122000968, 122000969, 122000970, 122000971,
            122000972, 122000973, 122000974, 122000975, 122000976, 122000977, 122000978, 122000979, 122000980, 122000981,
            122000982, 122000983, 122000984, 122000985, 122000986, 122000987, 122000988, 122000989, 122000990, 122000991,
            122000992, 122000993, 122000994, 122000995, 122000996, 122000997, 122000998, 122001002, 122001007, 122001012,
            122001017, 122001022, 122001027, 122001032, 167000577, 187000128, 100001617, 100001618, 100001619, 100001620,
            100101244, 100101245, 100201420, 100501234, 100601337, 100901258, 101301180, 101501268, 101701283, 101801123,
            101901041, 102001149, 102100910, 110101508, 110101509, 110101510, 110101511, 110101512, 120001368, 120001369,
            120001370, 120001371, 120001372, 120001373, 120001374, 120001375, 120001376, 120001377, 187000005, 167000524,
            122001167, 166050004, 166050003, 166050002, 166050001, 166050000, 100600662, 100600663, 100600664, 100600665,
            100600666, 100600667, 100600668, 100600669, 100600670, 100600671, 100600672, 100600673, 100600674, 100600675,
            100600676, 100600677, 100600678, 100600678, 100600679, 100601101, 100601102, 100601103, 100601104, 100000819,
            100200743, 100500635, 100900621, 101300592, 101500635, 101700657, 100601070, 100601074, 100601075, 100601076,
            100601077, 100601078, 100601123, 100601124, 100601125, 100601126, 100601127, 100601128, 100601131, 100601132,
            100601133, 100601134, 100601135, 100601136, 100601145, 100601146, 100601147, 100601168, 100601169, 100601170,
            100601171, 100601172, 100601173, 100601174, 100601175, 100601176, 100601177, 100601178, 100601179, 100601180,
            100601181, 100601182, 100601183, 100601184, 100601185, 100601186, 100601187, 100601188, 100601189, 100601190,
            100601191, 100601192, 164000003, 164000040, 100901216, 100001606, 100001607};

    @Override
    public void execute(Player player, String... params) {
        if ((params.length < 0) || (params.length < 1)) {
            onFail(player, null);
            return;
        }
        int itemId = 0;
        long itemCount = 1;
        Player receiver;

        if(params[0].equals("showblocked")){
            for(int itemidz : opItemsId){
                ItemTemplate titem = DataManager.ITEM_DATA.getItemTemplate(itemidz);
                PacketSendUtility.sendMessage(player, "#Name of item: "  + titem.getName() + "\n#Blocked ItemID: " + itemidz + "\n\n");
            }
            return;
        }

        try {
            String item = params[0];
            // Some item links have space before Id
            if (item.equals("[item:")) {
                item = params[1];
                Pattern id = Pattern.compile("(\\d{9})");
                Matcher result = id.matcher(item);
                if (result.find()) {
                    itemId = Integer.parseInt(result.group(1));
                }

                if (params.length == 3) {
                    itemCount = Long.parseLong(params[2]);
                }
            } else {
                Pattern id = Pattern.compile("\\[item:(\\d{9})");
                Matcher result = id.matcher(item);

                if (result.find()) {
                    itemId = Integer.parseInt(result.group(1));
                } else {
                    itemId = Integer.parseInt(params[0]);
                }

                if (params.length == 2) {
                    itemCount = Long.parseLong(params[1]);
                }
            }
            receiver = player;
        } catch (NumberFormatException e) {
            receiver = World.getInstance().findPlayer(Util.convertName(params[0]));
            if (receiver == null) {
                PacketSendUtility.sendMessage(player, "Could not find a player by that name.");
                return;
            }

            try {
                String item = params[1];
                // Some item links have space before Id
                if (item.equals("[item:")) {
                    item = params[2];
                    Pattern id = Pattern.compile("(\\d{9})");
                    Matcher result = id.matcher(item);
                    if (result.find()) {
                        itemId = Integer.parseInt(result.group(1));
                    }

                    if (params.length == 4) {
                        itemCount = Long.parseLong(params[3]);
                    }
                } else {
                    Pattern id = Pattern.compile("\\[item:(\\d{9})");
                    Matcher result = id.matcher(item);

                    if (result.find()) {
                        itemId = Integer.parseInt(result.group(1));
                    } else {
                        itemId = Integer.parseInt(params[1]);
                    }

                    if (params.length == 3) {
                        itemCount = Long.parseLong(params[2]);
                    }
                }
            } catch (NumberFormatException ex) {
                PacketSendUtility.sendMessage(player, "You must give number to itemid.");
                return;
            } catch (Exception ex2) {
                PacketSendUtility.sendMessage(player, "Occurs an error.");
                return;
            }
        }

        if (DataManager.ITEM_DATA.getItemTemplate(itemId) == null) {
            PacketSendUtility.sendMessage(player, "Item id is incorrect: " + itemId);
            return;
        }

        if(itemId != 0){
            for(int id : opItemsId){
                if(player.getAccessLevel() <= 2){
                    if(id == itemId){
                        PacketSendUtility.sendMessage(player, "Sorry! You are not able to add this item!!");
                        return;
                    }
                }
            }
        }


        if (!AdminService.getInstance().canOperate(player, receiver, itemId, "command //add")) {
            return;
        }

        ItemTemplate county = DataManager.ITEM_DATA.getItemTemplate(itemId);

        if (county != null){
            if(player != receiver){
                if(player.getAccessLevel() > 1){
                    long count = ItemService.addItem(receiver, itemId, itemCount);
                    PacketSendUtility.sendMessage(player, "You successfully gave " + itemCount + " x [item:"
                            + itemId + "] to " + receiver.getName() + ".");
                    PacketSendUtility.sendMessage(receiver, "You successfully received " + itemCount + " x [item:"
                            + itemId + "] from " + player.getName() + ".");
                }else{
                    PacketSendUtility.sendMessage(player, "Please try not to add any stuffs to player's Inventory. They can add by Their self !!");
                }
            }else{
                long count = ItemService.addItem(receiver, itemId, itemCount);
                PacketSendUtility.sendMessage(player, "You successfully received " + itemCount + " x [item:"
                        + itemId + "]");
            }
        }else{
            PacketSendUtility.sendMessage(player, "Item couldn't be added");
        }

    }

    @Override
    public void onFail(Player player, String message) {
        PacketSendUtility.sendMessage(player, "syntax //add <player> <item Id | link> <quantity>");
        PacketSendUtility.sendMessage(player, "syntax //add <item Id | link> <quantity>");
        PacketSendUtility.sendMessage(player, "syntax //add <item Id | link>");
    }
}
