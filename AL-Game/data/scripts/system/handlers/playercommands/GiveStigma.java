package playercommands;

import com.aionemu.commons.database.dao.DAOManager;
import com.aionemu.gameserver.dao.AbyssRankDAO;
import com.aionemu.gameserver.dao.PlayerDAO;
import com.aionemu.gameserver.dataholders.DataManager;
import com.aionemu.gameserver.model.PlayerClass;
import com.aionemu.gameserver.model.Race;
import com.aionemu.gameserver.model.gameobjects.Creature;
import com.aionemu.gameserver.model.gameobjects.player.Player;
import com.aionemu.gameserver.model.gameobjects.player.RequestResponseHandler;
import com.aionemu.gameserver.model.house.House;
import com.aionemu.gameserver.network.aion.serverpackets.SM_QUESTION_WINDOW;
import com.aionemu.gameserver.network.aion.serverpackets.SM_QUEST_ACTION;
import com.aionemu.gameserver.questEngine.model.QuestState;
import com.aionemu.gameserver.questEngine.model.QuestStatus;
import com.aionemu.gameserver.services.item.ItemService;
import com.aionemu.gameserver.utils.PacketSendUtility;
import com.aionemu.gameserver.utils.chathandlers.PlayerCommand;

/**
 * Created by K1ll3r
 */
public class GiveStigma extends PlayerCommand {
    public GiveStigma(){
        super("givestigma");
    }

    private static int[] elyosStigmaQuests = {1929, 3930, 3931, 3932, 11049, 11276, 11550, 30217};
    private static int[] asmodianStigmaQuests = {2900, 4934, 4935, 4936, 21049, 21278, 21550, 30317};


    public void execute(final Player player, String...param){
        if(param.length < 1){
            PacketSendUtility.sendMessage(player, " You can always remove the remaining items:\n .givestigma clean\n .givestigma add\n .givestigma unlock");
            return;
        }


        if(param[0].equals("unlock")){
            int stig = player.getCommonData().getAdvancedStigmaSlotSize();
           if(player.getRace() == Race.ELYOS){
               for (int quest_id : elyosStigmaQuests) {
                   completeQuest(player, quest_id);
               }
               if(stig < 12){
                   player.getCommonData().setAdvancedStigmaSlotSize(12);
               }
               PacketSendUtility.sendMessage(player, "You have unlocked all the stigma Slots, Try Relog Now");

            }else if(player.getRace() == Race.ASMODIANS){
               for (int quest_id : asmodianStigmaQuests) {
                   completeQuest(player, quest_id);
               }
               if(stig < 12){
                   player.getCommonData().setAdvancedStigmaSlotSize(12);
               }
               PacketSendUtility.sendMessage(player, "You have unlocked all the stigma Slots, Try Relog Now");
           }
        }

        if(param[0].equals("clean")){
            clean(player);
        }

        if(param[0].equals("add")){
            RequestResponseHandler RequestHim = new RequestResponseHandler(player) {
                @Override
                public void acceptRequest(Creature requester, Player responder) {


                    if (player.getPlayerClass() == PlayerClass.GLADIATOR){
                        glad(player);
                        PacketSendUtility.sendMessage(player, "You have successfully added 'GLADIATOR' stigma list!!");

                    }else if(player.getPlayerClass() == PlayerClass.TEMPLAR){
                        temp(player);
                        PacketSendUtility.sendMessage(player, "You have successfully added 'TEMPLAR' stigma list!!");
                    }else if(player.getPlayerClass() == PlayerClass.ASSASSIN){
                        sin(player);
                        PacketSendUtility.sendMessage(player, "You have successfully added 'ASSASSIN' stigma list!!");
                    }else if(player.getPlayerClass() == PlayerClass.RANGER){
                        ranger(player);
                        PacketSendUtility.sendMessage(player, "You have successfully added 'RANGER' stigma list!!");
                    }else if(player.getPlayerClass() == PlayerClass.SORCERER){
                        sorc(player);
                        PacketSendUtility.sendMessage(player, "You have successfully added 'SORCERER' stigma list!!");
                    }else if(player.getPlayerClass() == PlayerClass.SPIRIT_MASTER){
                        sm(player);
                        PacketSendUtility.sendMessage(player, "You have successfully added 'SPIRITMASTER' stigma list!!");
                    }else if(player.getPlayerClass() == PlayerClass.CLERIC){
                        cleric(player);
                        PacketSendUtility.sendMessage(player, "You have successfully added 'CLERIC' stigma list!!");
                    }else if(player.getPlayerClass() == PlayerClass.CHANTER){
                        chanter(player);
                        PacketSendUtility.sendMessage(player, "You have successfully added 'CHANTER' stigma list!!");
                    }else if(player.getPlayerClass() == PlayerClass.GUNNER){
                        gunner(player);
                        PacketSendUtility.sendMessage(player, "You have successfully added 'GUNNER' stigma list!!");
                    }else if(player.getPlayerClass() == PlayerClass.RIDER){
                        at(player);
                        PacketSendUtility.sendMessage(player, "You have successfully added 'AETHERTECH' stigma list!!");
                    }else if(player.getPlayerClass() == PlayerClass.BARD){
                        bard(player);
                        PacketSendUtility.sendMessage(player, "You have successfully added 'BARD' stigma list!!");

                    }else{
                        PacketSendUtility.sendMessage(player, "Failed to add the items");

                    }
                }
                @Override
                public void denyRequest(Creature requester, Player responder) {

                }
            };
            boolean areyousure = player.getResponseRequester().putRequest(1300564, RequestHim);
            if (areyousure){
                PacketSendUtility.sendPacket(player,new SM_QUESTION_WINDOW(1300564, 0, 0, "You are about to add 30 to 35 items to your inventory ( All the stigma skills to your class ) . Would you like to add them all?\n Be sure to have atleast free 1 cube and half : ) !"));
            }
        }


    }

    private static void completeQuest(Player player, int questId) {
        QuestState qs = player.getQuestStateList().getQuestState(questId);
        if (qs != null) {
            QuestState qState = player.getQuestStateList().getQuestState(questId);
            qState.setStatus(QuestStatus.COMPLETE);
            qState.setQuestVar(1);
            qState.setCompleteCount(qState.getCompleteCount() + 1);
            PacketSendUtility.sendPacket(player, new SM_QUEST_ACTION(questId, qState.getStatus(), qState.getQuestVars().getQuestVars()));

        } else {
            player.getQuestStateList().addQuest(questId, new QuestState(questId, QuestStatus.START, 0, 0, null, 0, null));
            PacketSendUtility.sendPacket(player, new SM_QUEST_ACTION(questId, 3, 0));
            player.getController().updateNearbyQuests();
            completeQuest(player, questId);
        }
    }


    private void glad(Player player){
        ItemService.addItem(player, 140000722, 1); //Whirling Strike
        ItemService.addItem(player, 140000535, 1); //Vicious Blow IV
        ItemService.addItem(player, 140000713, 1); //Vengeful Strike VII
        ItemService.addItem(player, 140000016, 1);
        ItemService.addItem(player, 140000712, 1);
        ItemService.addItem(player, 140000718, 1);
        ItemService.addItem(player, 140000710, 1);
        ItemService.addItem(player, 140000539, 1);
        ItemService.addItem(player, 140000023, 1);
        ItemService.addItem(player, 140000715, 1);
        ItemService.addItem(player, 140001001, 1);
        ItemService.addItem(player, 140000717, 1);
        ItemService.addItem(player, 140000716, 1);
        ItemService.addItem(player, 140000720, 1);
        ItemService.addItem(player, 140000394, 1);
        ItemService.addItem(player, 140000688, 1);
        ItemService.addItem(player, 140000714, 1);
        ItemService.addItem(player, 140000705, 1);
        ItemService.addItem(player, 140000707, 1);
        ItemService.addItem(player, 140000707, 1);
        ItemService.addItem(player, 140000530, 1);
        ItemService.addItem(player, 140000691, 1);
        ItemService.addItem(player, 140000540, 1);
        ItemService.addItem(player, 140000719, 1);
        ItemService.addItem(player, 140000726, 1);
        ItemService.addItem(player, 140000723, 1);
        ItemService.addItem(player, 140000708, 1);
        ItemService.addItem(player, 140000709, 1);
        ItemService.addItem(player, 140000222, 1);
        ItemService.addItem(player, 140000011, 1);
        ItemService.addItem(player, 140000007, 1);
        ItemService.addItem(player, 140000724, 1);
        //Stigma shard
        ItemService.addItem(player, 141000001, 10000);
    }
    private void temp(Player player){
        ItemService.addItem(player, 140000016,1); //Whirling Strike
        ItemService.addItem(player, 140000822,1); //Vicious Blow IV
        ItemService.addItem(player, 140000044,1); //Vengeful Strike VII
        ItemService.addItem(player, 140000562,1);
        ItemService.addItem(player, 140000715,1);
        ItemService.addItem(player, 140000824,1);
        ItemService.addItem(player, 140000237,1);
        ItemService.addItem(player, 140000706,1);
        ItemService.addItem(player, 140000820,1);
        ItemService.addItem(player, 140000812,1);
        ItemService.addItem(player, 140000813,1);
        ItemService.addItem(player, 140000042,1);
        ItemService.addItem(player, 140000823,1);
        ItemService.addItem(player, 140000693,1);
        ItemService.addItem(player, 140000807,1);
        ItemService.addItem(player, 140000815,1);
        ItemService.addItem(player, 140000816,1);
        ItemService.addItem(player, 140000814,1);
        ItemService.addItem(player, 140000817,1);
        ItemService.addItem(player, 140000825,1);
        ItemService.addItem(player, 140000821,1);
        ItemService.addItem(player, 140000563,1);
        ItemService.addItem(player, 140000373,1);
        ItemService.addItem(player, 140000354,1);
        ItemService.addItem(player, 140000238,1);
        ItemService.addItem(player, 140000028,1);
        ItemService.addItem(player, 140000818,1);
        ItemService.addItem(player, 140000027,1);
        ItemService.addItem(player, 140000819,1);
        ItemService.addItem(player, 140000559,1);
        ItemService.addItem(player, 140000558,1);
        ItemService.addItem(player, 140000192,1);
        //Stigma shard
        ItemService.addItem(player, 141000001, 10000);
    }
    private void sin(Player player){
        ItemService.addItem(player, 140000605,1); //Whirling Strike
        ItemService.addItem(player, 140000586,1); //Vicious Blow IV
        ItemService.addItem(player, 140000607,1); //Vengeful Strike VII
        ItemService.addItem(player, 140000596,1);
        ItemService.addItem(player, 140000600,1);
        ItemService.addItem(player, 140000606,1);
        ItemService.addItem(player, 140000198,1);
        ItemService.addItem(player, 140000400,1);
        ItemService.addItem(player, 140000696,1);
        ItemService.addItem(player, 140000200,1);
        ItemService.addItem(player, 140000780,1);
        ItemService.addItem(player, 140000781,1);
        ItemService.addItem(player, 140000783,1);
        ItemService.addItem(player, 140000253,1);
        ItemService.addItem(player, 140000777,1);
        ItemService.addItem(player, 140000077,1);
        ItemService.addItem(player, 140000087,1);
        ItemService.addItem(player, 140000782,1);
        ItemService.addItem(player, 140000199,1);
        ItemService.addItem(player, 140000213,1);
        ItemService.addItem(player, 140000785,1);
        ItemService.addItem(player, 140000695,1);
        ItemService.addItem(player, 140000088,1);
        ItemService.addItem(player, 140000776,1);
        ItemService.addItem(player, 140000779,1);
        ItemService.addItem(player, 140000081,1);
        ItemService.addItem(player, 140000850,1);
        ItemService.addItem(player, 140000786,1);
        ItemService.addItem(player, 140000778,1);
        ItemService.addItem(player, 140000078,1);
        ItemService.addItem(player, 140000503,1);
        ItemService.addItem(player, 140000608,1);
        //Stigma shard
        ItemService.addItem(player, 141000001, 10000);
    }
    private void ranger(Player player){
        ItemService.addItem(player, 140000852,1); //Whirling Strike
        ItemService.addItem(player, 140000853,1); //Vicious Blow IV
        ItemService.addItem(player, 140000245,1); //Vengeful Strike VII
        ItemService.addItem(player, 140000063,1);
        ItemService.addItem(player, 140000763,1);
        ItemService.addItem(player, 140000764,1);
        ItemService.addItem(player, 140000048,1);
        ItemService.addItem(player, 140000769,1);
        ItemService.addItem(player, 140000578,1);
        ItemService.addItem(player, 140000368,1);
        ItemService.addItem(player, 140000049,1);
        ItemService.addItem(player, 140000074,1);
        ItemService.addItem(player, 140000585,1);
        ItemService.addItem(player, 140000768,1);
        ItemService.addItem(player, 140000771,1);
        ItemService.addItem(player, 140000502,1);
        ItemService.addItem(player, 140000247,1);
        ItemService.addItem(player, 140000196,1);
        ItemService.addItem(player, 140000772,1);
        ItemService.addItem(player, 140000761,1);
        ItemService.addItem(player, 140000069,1);
        ItemService.addItem(player, 140000702,1);
        ItemService.addItem(player, 140000762,1);
        ItemService.addItem(player, 140000694,1);
        ItemService.addItem(player, 140000577,1);
        ItemService.addItem(player, 140000197,1);
        ItemService.addItem(player, 140000581,1);
        ItemService.addItem(player, 140000582,1);
        ItemService.addItem(player, 140000570,1);
        ItemService.addItem(player, 140000239,1);
        ItemService.addItem(player, 140000766,1);
        ItemService.addItem(player, 140000767,1);
        ItemService.addItem(player, 140000195,1);
        ItemService.addItem(player, 140000576,1);
        ItemService.addItem(player, 140000765,1);
        ItemService.addItem(player, 140000754,1);
        //Stigma shard
        ItemService.addItem(player, 141000001, 10000);
    }
    private void sorc(Player player){
        ItemService.addItem(player, 140000135,1); //Whirling Strike
        ItemService.addItem(player, 140000731,1); //Vicious Blow IV
        ItemService.addItem(player, 140000729,1); //Vengeful Strike VII
        ItemService.addItem(player, 140000855,1);
        ItemService.addItem(player, 140000139,1);
        ItemService.addItem(player, 140000293,1);
        ItemService.addItem(player, 140000511,1);
        ItemService.addItem(player, 140000737,1);
        ItemService.addItem(player, 140000670,1);
        ItemService.addItem(player, 140000692,1);
        ItemService.addItem(player, 140000295,1);
        ItemService.addItem(player, 140000131,1);
        ItemService.addItem(player, 140000738,1);
        ItemService.addItem(player, 140000742,1);
        ItemService.addItem(player, 140000291,1);
        ItemService.addItem(player, 140000732,1);
        ItemService.addItem(player, 140000733,1);
        ItemService.addItem(player, 140000851,1);
        ItemService.addItem(player, 140000735,1);
        ItemService.addItem(player, 140000741,1);
        ItemService.addItem(player, 140000662,1);
        ItemService.addItem(player, 140000390,1);
        ItemService.addItem(player, 140000465,1);
        ItemService.addItem(player, 140000736,1);
        ItemService.addItem(player, 140000130,1);
        ItemService.addItem(player, 140000141,1);
        ItemService.addItem(player, 140000142,1);
        ItemService.addItem(player, 140000739,1);
        ItemService.addItem(player, 140000144,1);
        ItemService.addItem(player, 140000728,1);
        ItemService.addItem(player, 140000669,1);
        //Stigma shard
        ItemService.addItem(player, 141000001, 10000);
    }
    private void sm(Player player){
        ItemService.addItem(player, 140000685,1); //Whirling Strike
        ItemService.addItem(player, 140000750,1); //Vicious Blow IV
        ItemService.addItem(player, 140000677,1); //Vengeful Strike VII
        ItemService.addItem(player, 140000687,1);
        ItemService.addItem(player, 140000748,1);
        ItemService.addItem(player, 140000749,1);
        ItemService.addItem(player, 140000156,1);
        ItemService.addItem(player, 140000167,1);
        ItemService.addItem(player, 140000306,1);
        ItemService.addItem(player, 140000163,1);
        ItemService.addItem(player, 140000493,1);
        ItemService.addItem(player, 140000212,1);
        ItemService.addItem(player, 140000211,1);
        ItemService.addItem(player, 140000686,1);
        ItemService.addItem(player, 140000152,1);
        ItemService.addItem(player, 140000303,1);
        ItemService.addItem(player, 140000747,1);
        ItemService.addItem(player, 140000753,1);
        ItemService.addItem(player, 140000751,1);
        ItemService.addItem(player, 140000681,1);
        ItemService.addItem(player, 140000486,1);
        ItemService.addItem(player, 140000488,1);
        ItemService.addItem(player, 140000159,1);
        ItemService.addItem(player, 140000149,1);
        ItemService.addItem(player, 140000703,1);
        ItemService.addItem(player, 140000746,1);
        ItemService.addItem(player, 140000701,1);
        ItemService.addItem(player, 140000214,1);
        ItemService.addItem(player, 140000148,1);
        ItemService.addItem(player, 140000743,1);
        ItemService.addItem(player, 140000302,1);
        ItemService.addItem(player, 140000679,1);
        //Stigma shard
        ItemService.addItem(player, 141000001, 10000);
    }
    private void cleric(Player player){
        ItemService.addItem(player, 140000831,1); //Whirling Strike
        ItemService.addItem(player, 140000847,1); //Vicious Blow IV
        ItemService.addItem(player, 140000848,1); //Vengeful Strike VII
        ItemService.addItem(player, 140000843,1);
        ItemService.addItem(player, 140000844,1);
        ItemService.addItem(player, 140000415,1);
        ItemService.addItem(player, 140000840,1);
        ItemService.addItem(player, 140000837,1);
        ItemService.addItem(player, 140000108,1);
        ItemService.addItem(player, 140000839,1);
        ItemService.addItem(player, 140000842,1);
        ItemService.addItem(player, 140000104,1);
        ItemService.addItem(player, 140000633,1);
        ItemService.addItem(player, 140000849,1);
        ItemService.addItem(player, 140000102,1);
        ItemService.addItem(player, 140000699,1);
        ItemService.addItem(player, 140000631,1);
        ItemService.addItem(player, 140000704,1);
        ItemService.addItem(player, 140000504,1);
        ItemService.addItem(player, 140000832,1);
        ItemService.addItem(player, 140000269,1);
        ItemService.addItem(player, 140000098,1);
        ItemService.addItem(player, 140000826,1);
        ItemService.addItem(player, 140000101,1);
        ItemService.addItem(player, 140000845,1);
        ItemService.addItem(player, 140000841,1);
        ItemService.addItem(player, 140000838,1);
        ItemService.addItem(player, 140000846,1);
        ItemService.addItem(player, 140000099,1);
        ItemService.addItem(player, 140000442,1);
        ItemService.addItem(player, 140000302,1);
        ItemService.addItem(player, 140000679,1);
        //Stigma shard
        ItemService.addItem(player, 141000001, 10000);
    }
    private void chanter(Player player){
        ItemService.addItem(player, 140000116,1); //Whirling Strike
        ItemService.addItem(player, 140000801,1); //Vicious Blow IV
        ItemService.addItem(player, 140000123,1); //Vengeful Strike VII
        ItemService.addItem(player, 140000797,1);
        ItemService.addItem(player, 140000802,1);
        ItemService.addItem(player, 140000506,1);
        ItemService.addItem(player, 140000652,1);
        ItemService.addItem(player, 140000654,1);
        ItemService.addItem(player, 140000796,1);
        ItemService.addItem(player, 140000112,1);
        ItemService.addItem(player, 140000800,1);
        ItemService.addItem(player, 140000788,1);
        ItemService.addItem(player, 140000804,1);
        ItemService.addItem(player, 140000856,1);
        ItemService.addItem(player, 140000798,1);
        ItemService.addItem(player, 140000700,1);
        ItemService.addItem(player, 140000286,1);
        ItemService.addItem(player, 140000653,1);
        ItemService.addItem(player, 140000787,1);
        ItemService.addItem(player, 140000805,1);
        ItemService.addItem(player, 140000125,1);
        ItemService.addItem(player, 140000803,1);
        ItemService.addItem(player, 140000793,1);
        ItemService.addItem(player, 140000806,1);
        ItemService.addItem(player, 140000794,1);
        ItemService.addItem(player, 140000799,1);
        ItemService.addItem(player, 140000282,1);
        ItemService.addItem(player, 140000117,1);
        ItemService.addItem(player, 140000655,1);
        ItemService.addItem(player, 140000854,1);
        //Stigma shard
        ItemService.addItem(player, 141000001, 10000);
    }
    private void gunner(Player player){
        ItemService.addItem(player, 140000986,1); //Whirling Strike
        ItemService.addItem(player, 140000927,1); //Vicious Blow IV
        ItemService.addItem(player, 140000966,1); //Vengeful Strike VII
        ItemService.addItem(player, 140000967,1);
        ItemService.addItem(player, 140000920,1);
        ItemService.addItem(player, 140000973,1);
        ItemService.addItem(player, 140000954,1);
        ItemService.addItem(player, 140000980,1);
        ItemService.addItem(player, 140000926,1);
        ItemService.addItem(player, 140000959,1);
        ItemService.addItem(player, 140000912,1);
        ItemService.addItem(player, 140000950,1);
        ItemService.addItem(player, 140000981,1);
        ItemService.addItem(player, 140000919,1);
        ItemService.addItem(player, 140001000,1);
        ItemService.addItem(player, 140000940,1);
        ItemService.addItem(player, 140000993,1);
        ItemService.addItem(player, 140000985,1);
        ItemService.addItem(player, 140000933,1);
        ItemService.addItem(player, 140000928,1);
        //Stigma shard
        ItemService.addItem(player, 141000001, 10000);
    }
    private void at(Player player){
        ItemService.addItem(player, 140001089,1); //Whirling Strike
        ItemService.addItem(player, 140001033,1); //Vicious Blow IV
        ItemService.addItem(player, 140001053,1); //Vengeful Strike VII
        ItemService.addItem(player, 140001023,1);
        ItemService.addItem(player, 140001028,1);
        ItemService.addItem(player, 140001059,1);
        ItemService.addItem(player, 140001046,1);
        ItemService.addItem(player, 140001002,1);
        ItemService.addItem(player, 140001078,1);
        ItemService.addItem(player, 140001068,1);
        ItemService.addItem(player, 140001010,1);
        ItemService.addItem(player, 140001038,1);
        ItemService.addItem(player, 140001096,1);
        ItemService.addItem(player, 140001037,1);
        ItemService.addItem(player, 140001017,1);
        ItemService.addItem(player, 140001075,1);
        ItemService.addItem(player, 140001084,1);
        ItemService.addItem(player, 140001076,1);
        ItemService.addItem(player, 140001035,1);
        ItemService.addItem(player, 140001064,1);
        ItemService.addItem(player, 140001039,1);
        //Stigma shard
        ItemService.addItem(player, 141000001, 10000);
    }
    private void bard(Player player){
        ItemService.addItem(player, 140000877,1); //Whirling Strike
        ItemService.addItem(player, 140000872,1); //Vicious Blow IV
        ItemService.addItem(player, 140000857,1); //Vengeful Strike VII
        ItemService.addItem(player, 140000883,1);
        ItemService.addItem(player, 140000905,1);
        ItemService.addItem(player, 140000890,1);
        ItemService.addItem(player, 140000906,1);
        ItemService.addItem(player, 140000862,1);
        ItemService.addItem(player, 140000873,1);
        ItemService.addItem(player, 140000868,1);
        ItemService.addItem(player, 140000858,1);
        ItemService.addItem(player, 140000861,1);
        ItemService.addItem(player, 140000897,1);
        ItemService.addItem(player, 140000878,1);
        ItemService.addItem(player, 140000859,1);
        ItemService.addItem(player, 140000860,1);
        ItemService.addItem(player, 140000863,1);
        ItemService.addItem(player, 140000867,1);
        ItemService.addItem(player, 140000898,1);
        //Stigma shard
        ItemService.addItem(player, 141000001, 10000);
    }

    private void clean(Player player){
        if (player.getPlayerClass() == PlayerClass.GLADIATOR){
            player.getInventory().decreaseByItemId(140000722,1); //Whirling Strike
            player.getInventory().decreaseByItemId(140000535,1); //Vicious Blow IV
            player.getInventory().decreaseByItemId(140000713,1); //Vengeful Strike VII
            player.getInventory().decreaseByItemId(140000016,1);
            player.getInventory().decreaseByItemId(140000712,1);
            player.getInventory().decreaseByItemId(140000718,1);
            player.getInventory().decreaseByItemId(140000710,1);
            player.getInventory().decreaseByItemId(140000539,1);
            player.getInventory().decreaseByItemId(140000023,1);
            player.getInventory().decreaseByItemId(140000715,1);
            player.getInventory().decreaseByItemId(140001001,1);
            player.getInventory().decreaseByItemId(140000717,1);
            player.getInventory().decreaseByItemId(140000716,1);
            player.getInventory().decreaseByItemId(140000720,1);
            player.getInventory().decreaseByItemId(140000394,1);
            player.getInventory().decreaseByItemId(140000688,1);
            player.getInventory().decreaseByItemId(140000714,1);
            player.getInventory().decreaseByItemId(140000705,1);
            player.getInventory().decreaseByItemId(140000707,1);
            player.getInventory().decreaseByItemId(140000707,1);
            player.getInventory().decreaseByItemId(140000530,1);
            player.getInventory().decreaseByItemId(140000691,1);
            player.getInventory().decreaseByItemId(140000540,1);
            player.getInventory().decreaseByItemId(140000719,1);
            player.getInventory().decreaseByItemId(140000726,1);
            player.getInventory().decreaseByItemId(140000723,1);
            player.getInventory().decreaseByItemId(140000708,1);
            player.getInventory().decreaseByItemId(140000709,1);
            player.getInventory().decreaseByItemId(140000222,1);
            player.getInventory().decreaseByItemId(140000011,1);
            player.getInventory().decreaseByItemId(140000007,1);
            player.getInventory().decreaseByItemId(140000724,1);
            //Stigmashard
            player.getInventory().decreaseByItemId(141000001,10000);
            PacketSendUtility.sendMessage(player, "All the stigma's left over has been cleaned!!");
        }else if(player.getPlayerClass() == PlayerClass.TEMPLAR){
            player.getInventory().decreaseByItemId(140000016,1); //Whirling Strike
            player.getInventory().decreaseByItemId(140000822,1); //Vicious Blow IV
            player.getInventory().decreaseByItemId(140000044,1); //Vengeful Strike VII
            player.getInventory().decreaseByItemId(140000562,1);
            player.getInventory().decreaseByItemId(140000715,1);
            player.getInventory().decreaseByItemId(140000824,1);
            player.getInventory().decreaseByItemId(140000237,1);
            player.getInventory().decreaseByItemId(140000706,1);
            player.getInventory().decreaseByItemId(140000820,1);
            player.getInventory().decreaseByItemId(140000812,1);
            player.getInventory().decreaseByItemId(140000813,1);
            player.getInventory().decreaseByItemId(140000042,1);
            player.getInventory().decreaseByItemId(140000823,1);
            player.getInventory().decreaseByItemId(140000693,1);
            player.getInventory().decreaseByItemId(140000807,1);
            player.getInventory().decreaseByItemId(140000815,1);
            player.getInventory().decreaseByItemId(140000816,1);
            player.getInventory().decreaseByItemId(140000814,1);
            player.getInventory().decreaseByItemId(140000817,1);
            player.getInventory().decreaseByItemId(140000825,1);
            player.getInventory().decreaseByItemId(140000821,1);
            player.getInventory().decreaseByItemId(140000563,1);
            player.getInventory().decreaseByItemId(140000373,1);
            player.getInventory().decreaseByItemId(140000354,1);
            player.getInventory().decreaseByItemId(140000238,1);
            player.getInventory().decreaseByItemId(140000028,1);
            player.getInventory().decreaseByItemId(140000818,1);
            player.getInventory().decreaseByItemId(140000027,1);
            player.getInventory().decreaseByItemId(140000819,1);
            player.getInventory().decreaseByItemId(140000559,1);
            player.getInventory().decreaseByItemId(140000558,1);
            player.getInventory().decreaseByItemId(140000192,1);
            //Stigmashard
            player.getInventory().decreaseByItemId(141000001,10000);
            PacketSendUtility.sendMessage(player, "All the stigma's left over has been cleaned!!");
        }else if(player.getPlayerClass() == PlayerClass.ASSASSIN){
            player.getInventory().decreaseByItemId(140000605,1); //Whirling Strike
            player.getInventory().decreaseByItemId(140000586,1); //Vicious Blow IV
            player.getInventory().decreaseByItemId(140000607,1); //Vengeful Strike VII
            player.getInventory().decreaseByItemId(140000596,1);
            player.getInventory().decreaseByItemId(140000600,1);
            player.getInventory().decreaseByItemId(140000606,1);
            player.getInventory().decreaseByItemId(140000198,1);
            player.getInventory().decreaseByItemId(140000400,1);
            player.getInventory().decreaseByItemId(140000696,1);
            player.getInventory().decreaseByItemId(140000200,1);
            player.getInventory().decreaseByItemId(140000780,1);
            player.getInventory().decreaseByItemId(140000781,1);
            player.getInventory().decreaseByItemId(140000783,1);
            player.getInventory().decreaseByItemId(140000253,1);
            player.getInventory().decreaseByItemId(140000777,1);
            player.getInventory().decreaseByItemId(140000077,1);
            player.getInventory().decreaseByItemId(140000087,1);
            player.getInventory().decreaseByItemId(140000782,1);
            player.getInventory().decreaseByItemId(140000199,1);
            player.getInventory().decreaseByItemId(140000213,1);
            player.getInventory().decreaseByItemId(140000785,1);
            player.getInventory().decreaseByItemId(140000695,1);
            player.getInventory().decreaseByItemId(140000088,1);
            player.getInventory().decreaseByItemId(140000776,1);
            player.getInventory().decreaseByItemId(140000779,1);
            player.getInventory().decreaseByItemId(140000081,1);
            player.getInventory().decreaseByItemId(140000850,1);
            player.getInventory().decreaseByItemId(140000786,1);
            player.getInventory().decreaseByItemId(140000778,1);
            player.getInventory().decreaseByItemId(140000078,1);
            //Stigmashard
            player.getInventory().decreaseByItemId(141000001,10000);
            PacketSendUtility.sendMessage(player, "All the stigma's left over has been cleaned!!");
        }else if(player.getPlayerClass() == PlayerClass.RANGER){
            player.getInventory().decreaseByItemId(140000852,1); //Whirling Strike
            player.getInventory().decreaseByItemId(140000853,1); //Vicious Blow IV
            player.getInventory().decreaseByItemId(140000245,1); //Vengeful Strike VII
            player.getInventory().decreaseByItemId(140000063,1);
            player.getInventory().decreaseByItemId(140000763,1);
            player.getInventory().decreaseByItemId(140000764,1);
            player.getInventory().decreaseByItemId(140000048,1);
            player.getInventory().decreaseByItemId(140000769,1);
            player.getInventory().decreaseByItemId(140000578,1);
            player.getInventory().decreaseByItemId(140000368,1);
            player.getInventory().decreaseByItemId(140000049,1);
            player.getInventory().decreaseByItemId(140000074,1);
            player.getInventory().decreaseByItemId(140000585,1);
            player.getInventory().decreaseByItemId(140000768,1);
            player.getInventory().decreaseByItemId(140000771,1);
            player.getInventory().decreaseByItemId(140000502,1);
            player.getInventory().decreaseByItemId(140000247,1);
            player.getInventory().decreaseByItemId(140000196,1);
            player.getInventory().decreaseByItemId(140000772,1);
            player.getInventory().decreaseByItemId(140000761,1);
            player.getInventory().decreaseByItemId(140000069,1);
            player.getInventory().decreaseByItemId(140000702,1);
            player.getInventory().decreaseByItemId(140000762,1);
            player.getInventory().decreaseByItemId(140000694,1);
            player.getInventory().decreaseByItemId(140000577,1);
            player.getInventory().decreaseByItemId(140000197,1);
            player.getInventory().decreaseByItemId(140000581,1);
            player.getInventory().decreaseByItemId(140000582,1);
            player.getInventory().decreaseByItemId(140000570,1);
            player.getInventory().decreaseByItemId(140000239,1);
            player.getInventory().decreaseByItemId(140000766,1);
            player.getInventory().decreaseByItemId(140000767,1);
            player.getInventory().decreaseByItemId(140000195,1);
            player.getInventory().decreaseByItemId(140000576,1);
            player.getInventory().decreaseByItemId(140000765,1);
            player.getInventory().decreaseByItemId(140000754,1);
            //Stigmashard
            player.getInventory().decreaseByItemId(141000001,10000);
            PacketSendUtility.sendMessage(player, "All the stigma's left over has been cleaned!!");
        }else if(player.getPlayerClass() == PlayerClass.SORCERER){
            player.getInventory().decreaseByItemId(140000135,1); //Whirling Strike
            player.getInventory().decreaseByItemId(140000731,1); //Vicious Blow IV
            player.getInventory().decreaseByItemId(140000729,1); //Vengeful Strike VII
            player.getInventory().decreaseByItemId(140000855,1);
            player.getInventory().decreaseByItemId(140000139,1);
            player.getInventory().decreaseByItemId(140000293,1);
            player.getInventory().decreaseByItemId(140000511,1);
            player.getInventory().decreaseByItemId(140000737,1);
            player.getInventory().decreaseByItemId(140000670,1);
            player.getInventory().decreaseByItemId(140000692,1);
            player.getInventory().decreaseByItemId(140000295,1);
            player.getInventory().decreaseByItemId(140000131,1);
            player.getInventory().decreaseByItemId(140000738,1);
            player.getInventory().decreaseByItemId(140000742,1);
            player.getInventory().decreaseByItemId(140000291,1);
            player.getInventory().decreaseByItemId(140000732,1);
            player.getInventory().decreaseByItemId(140000733,1);
            player.getInventory().decreaseByItemId(140000851,1);
            player.getInventory().decreaseByItemId(140000735,1);
            player.getInventory().decreaseByItemId(140000741,1);
            player.getInventory().decreaseByItemId(140000662,1);
            player.getInventory().decreaseByItemId(140000390,1);
            player.getInventory().decreaseByItemId(140000465,1);
            player.getInventory().decreaseByItemId(140000736,1);
            player.getInventory().decreaseByItemId(140000130,1);
            player.getInventory().decreaseByItemId(140000141,1);
            player.getInventory().decreaseByItemId(140000142,1);
            player.getInventory().decreaseByItemId(140000739,1);
            player.getInventory().decreaseByItemId(140000144,1);
            player.getInventory().decreaseByItemId(140000728,1);
            player.getInventory().decreaseByItemId(140000669,1);
            //Stigmashard
            player.getInventory().decreaseByItemId(141000001,10000);
            PacketSendUtility.sendMessage(player, "All the stigma's left over has been cleaned!!");
        }else if(player.getPlayerClass() == PlayerClass.SPIRIT_MASTER){
            player.getInventory().decreaseByItemId(140000685,1); //Whirling Strike
            player.getInventory().decreaseByItemId(140000750,1); //Vicious Blow IV
            player.getInventory().decreaseByItemId(140000677,1); //Vengeful Strike VII
            player.getInventory().decreaseByItemId(140000687,1);
            player.getInventory().decreaseByItemId(140000748,1);
            player.getInventory().decreaseByItemId(140000749,1);
            player.getInventory().decreaseByItemId(140000156,1);
            player.getInventory().decreaseByItemId(140000167,1);
            player.getInventory().decreaseByItemId(140000306,1);
            player.getInventory().decreaseByItemId(140000163,1);
            player.getInventory().decreaseByItemId(140000493,1);
            player.getInventory().decreaseByItemId(140000212,1);
            player.getInventory().decreaseByItemId(140000211,1);
            player.getInventory().decreaseByItemId(140000686,1);
            player.getInventory().decreaseByItemId(140000152,1);
            player.getInventory().decreaseByItemId(140000303,1);
            player.getInventory().decreaseByItemId(140000747,1);
            player.getInventory().decreaseByItemId(140000753,1);
            player.getInventory().decreaseByItemId(140000751,1);
            player.getInventory().decreaseByItemId(140000681,1);
            player.getInventory().decreaseByItemId(140000486,1);
            player.getInventory().decreaseByItemId(140000488,1);
            player.getInventory().decreaseByItemId(140000159,1);
            player.getInventory().decreaseByItemId(140000149,1);
            player.getInventory().decreaseByItemId(140000703,1);
            player.getInventory().decreaseByItemId(140000746,1);
            player.getInventory().decreaseByItemId(140000701,1);
            player.getInventory().decreaseByItemId(140000214,1);
            player.getInventory().decreaseByItemId(140000148,1);
            player.getInventory().decreaseByItemId(140000743,1);
            player.getInventory().decreaseByItemId(140000302,1);
            player.getInventory().decreaseByItemId(140000679,1);
            //Stigmashard
            player.getInventory().decreaseByItemId(141000001,10000);
            PacketSendUtility.sendMessage(player, "All the stigma's left over has been cleaned!!");
        }else if(player.getPlayerClass() == PlayerClass.CLERIC){
            player.getInventory().decreaseByItemId(140000831,1); //Whirling Strike
            player.getInventory().decreaseByItemId(140000847,1); //Vicious Blow IV
            player.getInventory().decreaseByItemId(140000848,1); //Vengeful Strike VII
            player.getInventory().decreaseByItemId(140000843,1);
            player.getInventory().decreaseByItemId(140000844,1);
            player.getInventory().decreaseByItemId(140000415,1);
            player.getInventory().decreaseByItemId(140000840,1);
            player.getInventory().decreaseByItemId(140000837,1);
            player.getInventory().decreaseByItemId(140000108,1);
            player.getInventory().decreaseByItemId(140000839,1);
            player.getInventory().decreaseByItemId(140000842,1);
            player.getInventory().decreaseByItemId(140000104,1);
            player.getInventory().decreaseByItemId(140000633,1);
            player.getInventory().decreaseByItemId(140000849,1);
            player.getInventory().decreaseByItemId(140000102,1);
            player.getInventory().decreaseByItemId(140000699,1);
            player.getInventory().decreaseByItemId(140000631,1);
            player.getInventory().decreaseByItemId(140000704,1);
            player.getInventory().decreaseByItemId(140000504,1);
            player.getInventory().decreaseByItemId(140000832,1);
            player.getInventory().decreaseByItemId(140000269,1);
            player.getInventory().decreaseByItemId(140000098,1);
            player.getInventory().decreaseByItemId(140000826,1);
            player.getInventory().decreaseByItemId(140000101,1);
            player.getInventory().decreaseByItemId(140000845,1);
            player.getInventory().decreaseByItemId(140000841,1);
            player.getInventory().decreaseByItemId(140000838,1);
            player.getInventory().decreaseByItemId(140000846,1);
            player.getInventory().decreaseByItemId(140000099,1);
            player.getInventory().decreaseByItemId(140000442,1);
            player.getInventory().decreaseByItemId(140000302,1);
            player.getInventory().decreaseByItemId(140000679,1);
            //Stigmashard
            player.getInventory().decreaseByItemId(141000001,10000);
            PacketSendUtility.sendMessage(player, "All the stigma's left over has been cleaned!!");
        }else if(player.getPlayerClass() == PlayerClass.CHANTER){
            player.getInventory().decreaseByItemId(140000116,1); //Whirling Strike
            player.getInventory().decreaseByItemId(140000801,1); //Vicious Blow IV
            player.getInventory().decreaseByItemId(140000123,1); //Vengeful Strike VII
            player.getInventory().decreaseByItemId(140000797,1);
            player.getInventory().decreaseByItemId(140000802,1);
            player.getInventory().decreaseByItemId(140000506,1);
            player.getInventory().decreaseByItemId(140000652,1);
            player.getInventory().decreaseByItemId(140000654,1);
            player.getInventory().decreaseByItemId(140000796,1);
            player.getInventory().decreaseByItemId(140000112,1);
            player.getInventory().decreaseByItemId(140000800,1);
            player.getInventory().decreaseByItemId(140000788,1);
            player.getInventory().decreaseByItemId(140000804,1);
            player.getInventory().decreaseByItemId(140000856,1);
            player.getInventory().decreaseByItemId(140000798,1);
            player.getInventory().decreaseByItemId(140000700,1);
            player.getInventory().decreaseByItemId(140000286,1);
            player.getInventory().decreaseByItemId(140000653,1);
            player.getInventory().decreaseByItemId(140000787,1);
            player.getInventory().decreaseByItemId(140000805,1);
            player.getInventory().decreaseByItemId(140000125,1);
            player.getInventory().decreaseByItemId(140000803,1);
            player.getInventory().decreaseByItemId(140000793,1);
            player.getInventory().decreaseByItemId(140000806,1);
            player.getInventory().decreaseByItemId(140000794,1);
            player.getInventory().decreaseByItemId(140000799,1);
            player.getInventory().decreaseByItemId(140000282,1);
            player.getInventory().decreaseByItemId(140000117,1);
            player.getInventory().decreaseByItemId(140000655,1);
            player.getInventory().decreaseByItemId(140000854,1);
            //Stigmashard
            player.getInventory().decreaseByItemId(141000001,10000);
            PacketSendUtility.sendMessage(player, "All the stigma's left over has been cleaned!!");
        }else if(player.getPlayerClass() == PlayerClass.GUNNER){
            player.getInventory().decreaseByItemId(140000986,1); //Whirling Strike
            player.getInventory().decreaseByItemId(140000927,1); //Vicious Blow IV
            player.getInventory().decreaseByItemId(140000966,1); //Vengeful Strike VII
            player.getInventory().decreaseByItemId(140000967,1);
            player.getInventory().decreaseByItemId(140000920,1);
            player.getInventory().decreaseByItemId(140000973,1);
            player.getInventory().decreaseByItemId(140000954,1);
            player.getInventory().decreaseByItemId(140000980,1);
            player.getInventory().decreaseByItemId(140000926,1);
            player.getInventory().decreaseByItemId(140000959,1);
            player.getInventory().decreaseByItemId(140000912,1);
            player.getInventory().decreaseByItemId(140000950,1);
            player.getInventory().decreaseByItemId(140000981,1);
            player.getInventory().decreaseByItemId(140000919,1);
            player.getInventory().decreaseByItemId(140001000,1);
            player.getInventory().decreaseByItemId(140000940,1);
            player.getInventory().decreaseByItemId(140000993,1);
            player.getInventory().decreaseByItemId(140000985,1);
            player.getInventory().decreaseByItemId(140000933,1);
            player.getInventory().decreaseByItemId(140000928,1);
            //Stigmashard
            player.getInventory().decreaseByItemId(141000001,10000);
            PacketSendUtility.sendMessage(player, "All the stigma's left over has been cleaned!!");
        }else if(player.getPlayerClass() == PlayerClass.RIDER){
            player.getInventory().decreaseByItemId(140001089,1); //Whirling Strike
            player.getInventory().decreaseByItemId(140001033,1); //Vicious Blow IV
            player.getInventory().decreaseByItemId(140001053,1); //Vengeful Strike VII
            player.getInventory().decreaseByItemId(140001023,1);
            player.getInventory().decreaseByItemId(140001028,1);
            player.getInventory().decreaseByItemId(140001059,1);
            player.getInventory().decreaseByItemId(140001046,1);
            player.getInventory().decreaseByItemId(140001002,1);
            player.getInventory().decreaseByItemId(140001078,1);
            player.getInventory().decreaseByItemId(140001068,1);
            player.getInventory().decreaseByItemId(140001010,1);
            player.getInventory().decreaseByItemId(140001038,1);
            player.getInventory().decreaseByItemId(140001096,1);
            player.getInventory().decreaseByItemId(140001037,1);
            player.getInventory().decreaseByItemId(140001017,1);
            player.getInventory().decreaseByItemId(140001075,1);
            player.getInventory().decreaseByItemId(140001084,1);
            player.getInventory().decreaseByItemId(140001076,1);
            player.getInventory().decreaseByItemId(140001035,1);
            player.getInventory().decreaseByItemId(140001064,1);
            player.getInventory().decreaseByItemId(140001039,1);
            //Stigmashard
            player.getInventory().decreaseByItemId(141000001,10000);
            PacketSendUtility.sendMessage(player, "All the stigma's left over has been cleaned!!");
        }else if(player.getPlayerClass() == PlayerClass.BARD){
            player.getInventory().decreaseByItemId(140000877,1); //Whirling Strike
            player.getInventory().decreaseByItemId(140000872,1); //Vicious Blow IV
            player.getInventory().decreaseByItemId(140000857,1); //Vengeful Strike VII
            player.getInventory().decreaseByItemId(140000883,1);
            player.getInventory().decreaseByItemId(140000905,1);
            player.getInventory().decreaseByItemId(140000890,1);
            player.getInventory().decreaseByItemId(140000906,1);
            player.getInventory().decreaseByItemId(140000862,1);
            player.getInventory().decreaseByItemId(140000873,1);
            player.getInventory().decreaseByItemId(140000868,1);
            player.getInventory().decreaseByItemId(140000858,1);
            player.getInventory().decreaseByItemId(140000861,1);
            player.getInventory().decreaseByItemId(140000897,1);
            player.getInventory().decreaseByItemId(140000878,1);
            player.getInventory().decreaseByItemId(140000859,1);
            player.getInventory().decreaseByItemId(140000860,1);
            player.getInventory().decreaseByItemId(140000863,1);
            player.getInventory().decreaseByItemId(140000867,1);
            player.getInventory().decreaseByItemId(140000898,1);
            //Stigmashard
            player.getInventory().decreaseByItemId(141000001,10000);
            PacketSendUtility.sendMessage(player, "All the stigma's left over has been cleaned!!");
       }

    }

    public void onFail(Player player, String msg){
        PacketSendUtility.sendMessage(player, " synax : .givestigma add\n    .givestigma clean\n  .givestigma unlock");
        return;
    }
}
