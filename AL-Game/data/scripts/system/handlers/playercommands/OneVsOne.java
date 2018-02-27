package playercommands;

import com.aionemu.gameserver.model.Race;
import com.aionemu.gameserver.model.gameobjects.player.Player;
import com.aionemu.gameserver.services.teleport.TeleportService2;
import com.aionemu.gameserver.skillengine.effect.AbnormalState;
import com.aionemu.gameserver.utils.PacketSendUtility;
import com.aionemu.gameserver.utils.ThreadPoolManager;
import com.aionemu.gameserver.utils.chathandlers.PlayerCommand;
import com.aionemu.gameserver.world.World;

import java.util.Iterator;

/**
 * Created by Kill3r
 */
public class OneVsOne extends PlayerCommand {

    public OneVsOne() {
        super("1v1");
    }

    private boolean isRegedAndStartSearching = false;

    public void execute(final Player player,String...params){
        if(params.length == 0){
            onFail(player, "Its under progress!");
            return;
        }

        if(player.getAccessLevel() < 1){
            PacketSendUtility.sendMessage(player, "Only GM's Can get into Test Rounds!");
            return;
        }

        if(params[0].equals("test")){
            if(params[1].equalsIgnoreCase("turnRegforOnevOne off")){
                player.setRegisteredForOneVOne(false);
            }else if(params[1].equalsIgnoreCase("turnRegforOneVOne on")){
                player.setRegisteredForOneVOne(true);
            }

        }

        if(params[0].equals("register")){
            player.setRegisteredForOneVOne(true);
            isRegedAndStartSearching = true;
            PacketSendUtility.sendMessage(player, "You've registered for One vs One Event!\nAnd Started Looking for players!");
        }else if(params[0].equals("unregister")){
            player.setRegisteredForOneVOne(false);
            isRegedAndStartSearching = false;
            PacketSendUtility.sendMessage(player, "You've unregistered from the One V One Event!");
            return;
        }



        }
    private void CheckForOpponent(final Player player){
        if(player.isRegisteredForOneVOne()){
            ThreadPoolManager.getInstance().schedule(new Runnable() {
                @Override
                public void run() {
                    Iterator<Player> ita = World.getInstance().getPlayersIterator();
                    while(ita.hasNext()){
                        Player opponent = ita.next();
                        if(opponent.isRegisteredForOneVOne() && opponent.getRace() != player.getRace()){
                            PacketSendUtility.sendMessage(player, "Found a Registered Player!");
                            PacketSendUtility.sendMessage(player, "Putting both to sleep!");
                            player.getEffectController().setAbnormal(AbnormalState.SLEEP.getId());
                            player.getEffectController().updatePlayerEffectIcons();
                            player.getEffectController().broadCastEffects();
                            PacketSendUtility.sendMessage(player, "You'll be ported to Arena in 5 seconds!");
                            player.setOneVoneOpponentFound(true);
                            return;
                        }
                    }
                    if(player.isOneVoneOpponentFound()){
                        ThreadPoolManager.getInstance().schedule(new Runnable() {
                            @Override
                            public void run() {
                                player.getEffectController().unsetAbnormal(AbnormalState.SLEEP.getId());
                                player.getEffectController().updatePlayerEffectIcons();
                                player.getEffectController().broadCastEffects();
                                if(player.getRace() == Race.ASMODIANS){
                                    TeleportService2.teleportTo(player, 300230000, 1, 661.85925f, 774.5506f, 216.26215f);
                                }else{
                                    TeleportService2.teleportTo(player, 300230000, 1, 596.01654f, 774.23364f, 215.58362f);
                                }
                            }
                        }, 5000);
                    }
                    PacketSendUtility.sendMessage(player, "Sorry, No opponent found for Event!");
                    PacketSendUtility.sendMessage(player, "The System will keep retrying untill you give .1v1 unregister");
                }
            },20000);
        }
    }

}
