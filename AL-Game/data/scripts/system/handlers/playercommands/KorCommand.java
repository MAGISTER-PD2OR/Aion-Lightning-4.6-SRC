package playercommands;

import com.aionemu.gameserver.model.gameobjects.player.Player;
import com.aionemu.gameserver.services.UEvents.Kor.KorService;
import com.aionemu.gameserver.utils.PacketSendUtility;
import com.aionemu.gameserver.utils.Util;
import com.aionemu.gameserver.utils.chathandlers.PlayerCommand;
import com.aionemu.gameserver.world.World;

/**
 * Created by Kill3r
 */
public class KorCommand extends PlayerCommand{

    public KorCommand(){
        super("kor");
    }

    public void execute(Player player,String...params){

        if(params[0].equalsIgnoreCase("reg")){
            KorService.service.registerPlayer(player);
            return;
        }

        if(params[0].equalsIgnoreCase("unreg")){
            KorService.service.unregisterPlayer(player);
            return;
        }

        if(params[0].equalsIgnoreCase("send")){
            if(player.getWorldId() == KorService.service.worldId && player.getPlayerGroup2().isLeader(player)){
                try{
                    Player sendName = World.getInstance().findPlayer(Util.convertName(params[1]));
                    if(!sendName.getPlayerGroup2().hasMember(player.getObjectId())){
                        PacketSendUtility.sendMessage(player, "You must type a player's name of you're group!");
                        return;
                    }
                    player.setKorSendName(sendName.getName());
                }catch (NumberFormatException e){
                    PacketSendUtility.sendMessage(player, "That player is not Online");
                }
                PacketSendUtility.sendMessage(player, "Player has been registered for port to fight!");

            }else{
                PacketSendUtility.sendMessage(player, "You can only use this command inside the KOR Arena / You need to be Leader to use this!");
                return;
            }
        }

        if(params[0].equalsIgnoreCase("run")){
            if(player.getAccessLevel() > 1){
                KorService.service.AnnounceForRegister();
            }
            return;
        }

        if(params[0].equalsIgnoreCase("stop")){
            if(player.getAccessLevel() > 1){
                KorService.service.forceStopKor();
            }
        }
    }
}
