package playercommands;

import com.aionemu.gameserver.model.gameobjects.player.Player;
import com.aionemu.gameserver.utils.PacketSendUtility;
import com.aionemu.gameserver.utils.chathandlers.PlayerCommand;
import com.aionemu.gameserver.world.World;

import java.util.Collection;
import java.util.Iterator;


/**
 * Created by Kill3r
 */
public class Queue extends PlayerCommand {

    public Queue(){
        super("queue");
    }

    public void execute(Player player,String...params){
        boolean anyEventfound = false;
        if(player.isRegedEvent()){
            PacketSendUtility.sendMessage(player, "You've already registered to the event!");
            return;
        }
        Iterator<Player> ita = World.getInstance().getPlayersIterator();

        while(ita.hasNext()){
            Player player1 = ita.next();

            if(player1.getAccessLevel() >= 1 && player1.isEventStarted()){
                PacketSendUtility.sendMessage(player, "Found an Event! Registering to " + player1.getName() + "'s Event!");
                anyEventfound = true;
            }
        }

        if(anyEventfound == true){
            PacketSendUtility.sendMessage(player, "You've registered to the upcoming event!");
            player.setRegedEvent(true);
        }else{
            PacketSendUtility.sendMessage(player, "Currently there are no event running!");
        }

    }
}
