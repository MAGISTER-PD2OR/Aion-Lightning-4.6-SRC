package admincommands;

import com.aionemu.gameserver.model.ChatType;
import com.aionemu.gameserver.model.gameobjects.Creature;
import com.aionemu.gameserver.model.gameobjects.HouseObject;
import com.aionemu.gameserver.model.gameobjects.VisibleObject;
import com.aionemu.gameserver.model.gameobjects.player.Player;
import com.aionemu.gameserver.model.house.House;
import com.aionemu.gameserver.network.aion.serverpackets.SM_MESSAGE;
import com.aionemu.gameserver.network.aion.serverpackets.SM_RESURRECT;
import com.aionemu.gameserver.services.abyss.AbyssPointsService;
import com.aionemu.gameserver.services.item.HouseObjectFactory;
import com.aionemu.gameserver.services.teleport.TeleportService2;
import com.aionemu.gameserver.skillengine.SkillEngine;
import com.aionemu.gameserver.utils.PacketSendUtility;
import com.aionemu.gameserver.utils.Util;
import com.aionemu.gameserver.utils.chathandlers.AdminCommand;
import com.aionemu.gameserver.world.World;
import com.aionemu.gameserver.world.geo.GeoService;
import javolution.util.FastMap;

import java.util.Iterator;

/**
 * Created by Kill3r
 */
public class EventAi extends AdminCommand {
    public EventAi() {
        super("eventai");
    }


    public void execute(Player admin, String...params){
        if(params.length < 1){
            onFail(admin,null);
            return;
        }

        //TODO IN DISTANCE 100
        if(params[0].equals("rewardall_inzone")){
            int rewardAmount = Integer.parseInt(params[2]);

            Iterator<Player> ita = World.getInstance().getPlayersIterator();
            if(params[1].equals("gp")){

                while(ita.hasNext()){
                    Player player = ita.next();
                    if(player.getWorldId() == admin.getWorldId() && !(player.getName() == admin.getName() && GeoService.getInstance().canSee(admin, player))){
                        AbyssPointsService.addGp(player, rewardAmount);
                        PacketSendUtility.sendMessage(player, "You've rewarded "+rewardAmount+" GP from an Event!");
                        PacketSendUtility.sendMessage(admin, "Player : "+player.getName()+" has been rewarded!");
                    }
                }

                PacketSendUtility.sendMessage(admin, "Every player on the map has been sucessfully rewarded with "+rewardAmount+" GP !");

            }else if(params[1].equals("ap")){
                while(ita.hasNext()){
                    Player player = ita.next();
                    if(player.getWorldId() == admin.getWorldId() && !(player.getName() == admin.getName() && GeoService.getInstance().canSee(admin, player))){
                        AbyssPointsService.addAp(player, rewardAmount);
                        PacketSendUtility.sendMessage(player, "You've rewarded "+rewardAmount+" AP from an Event!");
                    }
                }
                PacketSendUtility.sendMessage(admin, "Every player on the map has been sucessfully rewarded with "+rewardAmount+" AP !");
            }
        }else if(params[0].equalsIgnoreCase("rewardall_all")){
            int rewardAmount = Integer.parseInt(params[2]);

            Iterator<Player> ita = World.getInstance().getPlayersIterator();
            if(params[1].equals("gp")){

                while(ita.hasNext()){
                    Player player = ita.next();
                    if(!(player.getName() == admin.getName() && GeoService.getInstance().canSee(admin, player))){
                        AbyssPointsService.addGp(player, rewardAmount);
                        PacketSendUtility.sendMessage(player, "You've rewarded "+rewardAmount+" GP from an Event!");
                        PacketSendUtility.sendMessage(admin, "Player : "+player.getName()+" has been rewarded!");
                    }
                }

                PacketSendUtility.sendMessage(admin, "Every player on the map has been sucessfully rewarded with "+rewardAmount+" GP !");

            }else if(params[1].equals("ap")){
                while(ita.hasNext()){
                    Player player = ita.next();
                    if(!(player.getName() == admin.getName() && GeoService.getInstance().canSee(admin, player))){
                        AbyssPointsService.addAp(player, rewardAmount);
                        PacketSendUtility.sendMessage(player, "You've rewarded "+rewardAmount+" AP from an Event!");
                    }
                }
                PacketSendUtility.sendMessage(admin, "Every player on the map has been sucessfully rewarded with "+rewardAmount+" AP !");
            }
        }else if(params[0].equals("rewardall_queue")){
            int rewardAmount = Integer.parseInt(params[2]);
            if(admin.getQueuedPlayers().isEmpty()){
                PacketSendUtility.sendMessage(admin, "There is no one in the queued reward list!");
                return;
            }

            Iterator<Player> ita = World.getInstance().getPlayersIterator();
            if(params[1].equals("gp")){

                while(ita.hasNext()){
                    Player player = ita.next();

                    if(admin.QueuedPlayers.contains(player)){
                        PacketSendUtility.sendMessage(admin, "Player : "+player.getName()+" has been rewarded!");
                        AbyssPointsService.addGp(player,rewardAmount);
                    }
                }
                admin.QueuedPlayers.clear();
            }


        }else if(params[0].equals("movetomeall_inzone")){
           Iterator<Player> ita = World.getInstance().getPlayersIterator();
           while(ita.hasNext()){
               Player player = ita.next();
               if(player.getWorldId() == admin.getWorldId() && !(player.getName() == admin.getName())){
                   TeleportService2.teleportTo(player, admin.getWorldId(),admin.getInstanceId(), admin.getX(),admin.getY(),admin.getZ());
                   PacketSendUtility.sendMessage(player, "You have been ported by a GM");
               }
           }
            PacketSendUtility.sendMessage(admin, "Every player in this Map as been gathered to Your location!");

        }else if(params[0].equals("resall_inzone")){
            Iterator<Player> ita = World.getInstance().getPlayersIterator();
            while(ita.hasNext()){
                Player player = ita.next();
                if(player.getWorldId() == admin.getWorldId() && player.getLifeStats().isAlreadyDead()){
                    player.setPlayerResActivate(true);
                    PacketSendUtility.sendPacket(player, new SM_RESURRECT(admin));
                    return;
                }
            }
        }else if(params[0].equals("announce_inzone")){
            String Message;
            String type;
            Message = " ";
            type = params[1];


            for (int i = 2; i < params.length - 1; i++) {
                Message += params[i] + " ";
            }
            // Add the last without the end space
            Message += params[params.length - 1];

            String actual;
            actual = "[ " +type+" ] : " + Message + " ";

            Iterator<Player> ita = World.getInstance().getPlayersIterator();

            while(ita.hasNext()){
                Player player = ita.next();
                if(player.getWorldId() == admin.getWorldId()){
                    PacketSendUtility.sendYellowMessageOnCenter(player, actual);
                }
            }
        }else if(params[0].equals("announce_all")){
            String Message;
            String type;
            Message = " ";
            type = params[1];


            for (int i = 2; i < params.length - 1; i++) {
                Message += params[i] + " ";
            }

            // Add the last without the end space
            Message += params[params.length - 1];


            String actual;
            actual = "[ " +type+" ] : " + Message + " ";

            Iterator<Player> ita = World.getInstance().getPlayersIterator();

            while(ita.hasNext()){
                Player player = ita.next();

                PacketSendUtility.sendYellowMessageOnCenter(player, actual);

            }
        }else if(params[0].equalsIgnoreCase("stop")){
            if(params[1].equalsIgnoreCase("all")){

                Iterator<Player> ita = World.getInstance().getPlayersIterator();

                while(ita.hasNext()){
                    Player player = ita.next();

                    if(player.getWorldId() == admin.getWorldId() && player.getAccessLevel() < 1 && GeoService.getInstance().canSee(admin,player)){
                        SkillEngine.getInstance().applyEffectDirectly(8256, admin, player, (10 * 1000));
                    }
                }
     /*       }else if(params[1].equalsIgnoreCase("near")){
                Iterator<Player> ita = World.getInstance().getPlayersIterator();

                while(ita.hasNext()){
                    Player player = ita.next();
                    if(player.getWorldId() == admin.getWorldId()){

                    }
     }*/
            }else{

            VisibleObject target = admin.getTarget();
            VisibleObject targetsTarget = target.getTarget();

            if (target == null || !(target instanceof Creature)) {
                PacketSendUtility.sendMessage(admin, "You must select a target!");
                return;
            }

            SkillEngine.getInstance().applyEffectDirectly(8256, admin, (Creature) target, (10 * 1000));
            SkillEngine.getInstance().applyEffectDirectly(8256, admin, (Creature) targetsTarget, (10 * 1000));
            }
        }else if(params[0].equalsIgnoreCase("returnall_inzone")){
            Iterator<Player> ita = World.getInstance().getPlayersIterator();

            while(ita.hasNext()){
                Player player = ita.next();

                if(admin.getWorldId() == player.getWorldId() && player.getAccessLevel() < 1 && GeoService.getInstance().canSee(admin,player)){
                    TeleportService2.moveToBindLocation(player, true);
                }

            }
        }else if(params[0].equalsIgnoreCase("port2jumping")){
            Player player = World.getInstance().findPlayer(Util.convertName(params[1]));

            if (player == null) {
                PacketSendUtility.sendMessage(player, "The specified player is not online.");
                return;
            }

            TeleportService2.teleportTo(player, 300260000, 468.792f, 423.079f, 233.494f);
            PacketSendUtility.sendMessage(admin, "Player : "+player.getName()+" Ported to Jumping Instance StartPoint!");


        }else{
            onFail(admin,null);

        }
    }



    public void onFail(Player admin, String Msg){
        PacketSendUtility.sendMessage(admin, "=====EVENT AI====\n//eventai [type]\n" +
                "[type1] : rewardall_inzone - Gives reward to all in map\n" +
                "[type2] : movetomeall_inzone - Moves everyone in map to you\n" +
                "[type3] : resall_inzone - Ressurect all in map\n" +
                "[type4] : announce_inzone  - Announce to all in map Only\n" +
                "[type5] : announce_all - Announce to all in Server\n" +
                "[type6] : returnall_inzone - Moves Everyone back to their BindPoints\n" +
                "[type7] : stop - Stops Rage Players :D ( Extra parameters are  //eventai stop < all | 0 >\n" +
                "[type8] : port2jumping - Moves a player to jumping instance ( //eventai port2jumping [playername]" +
                "[type9] : rewardall_queue - rewards everyone that was queued on your event! //eventai rewardall_queue gp 2000\n");
        PacketSendUtility.sendMessage(admin, "\n[Type1 Info]\n[info] You need to type the reward type you're giving, GP or AP and after that you need to type the value amount\n" +
                "[type1 example] //eventai rewardall_inzone gp 100\n" +
                "[type7 example] //eventai stop 0 <--- will paralyze the player you're targeting and the targets target.\n" +
                "[type7 example] //eventai stop all <--- will paralyze everyone in the map!\n" +
                "[type4 and type5 info]\n" +
                "[info] You need to give the announce type and the message. \n" +
                "[example] //eventai announce_inzone 1-on-1 This will be the message.");
    }

}
