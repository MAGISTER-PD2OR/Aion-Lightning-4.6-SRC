package admincommands;

import com.aionemu.gameserver.model.gameobjects.player.Player;
import com.aionemu.gameserver.model.templates.battle_ground.BattleGroundTemplate;
import com.aionemu.gameserver.model.templates.battle_ground.BattleGroundType;
import com.aionemu.gameserver.utils.PacketSendUtility;
import com.aionemu.gameserver.utils.chathandlers.AdminCommand;
import com.aionemu.gameserver.world.World;

import java.util.Collection;
import java.util.Iterator;

/**
 * Created by Kill3r
 */
public class EventCaller extends AdminCommand {

    public EventCaller(){
        super("eventcaller");
    }

    public void execute(Player player, String...params){
        if(params.length == 0){
            onFail(player, "" +
                    "--Syntax--" +
                    "\n//eventcaller show - shows registered players." +
                    "\n//eventcaller start - starts the event to calling to players." +
                    "\n//eventcaller stop - stops/ends the registeration queue and ports the registered players to you.");
            return;
        }
          if(params[0].equals("show")){
            PacketSendUtility.sendMessage(player, "\nRegistered Players for the following Event!");
            PacketSendUtility.sendMessage(player, "==================================");
              Collection<Player> players = World.getInstance().getAllPlayers();

              for (Player p : players){
                    if(p.isRegedEvent()){
                        PacketSendUtility.sendMessage(player, "# " + p.getName() + " - " + p.getRace() + " - " + p.getPlayerClass());
                    }
              }
          }else if(params[0].equals("start")){
              player.setEventStarted(true);
              Iterator<Player> iter = World.getInstance().getPlayersIterator();
              while(iter.hasNext()){
                  Player p1 = iter.next();
                  PacketSendUtility.sendSys2Message(p1, player.getName(), "[EVENT] Registering for Event has Started! Type .queue to register to the event!");
              }
          }else if(params[0].equals("stop")){
              AdminCommand test = new MoveToMe();
              player.setEventStarted(false);
              Iterator<Player> iter = World.getInstance().getPlayersIterator();
              while(iter.hasNext()){
                  Player p1 = iter.next();

                  if(p1.isRegedEvent()){
                      test.execute(player, p1.getName());
                      PacketSendUtility.sendMessage(player, "Player : "+p1.getName()+" has been ported and added to reward list!");
                      player.setQueuedPlayers(p1);
                  }
                  PacketSendUtility.sendSys2Message(p1, player.getName(), "[EVENT] Event is Closed! Better luck next time!!");
                  p1.setRegedEvent(false);
              }

              PacketSendUtility.sendMessage(player, "All the players have been added to you're queue list for rewarding!");
          }
    }
}
