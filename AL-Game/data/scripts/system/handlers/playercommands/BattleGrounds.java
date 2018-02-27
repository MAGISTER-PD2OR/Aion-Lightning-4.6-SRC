package playercommands;

import com.aionemu.gameserver.model.gameobjects.player.Player;
import com.aionemu.gameserver.services.UEvents.BattleGroundService;
import com.aionemu.gameserver.utils.PacketSendUtility;
import com.aionemu.gameserver.utils.ThreadPoolManager;
import com.aionemu.gameserver.utils.chathandlers.PlayerCommand;

import java.util.concurrent.ScheduledFuture;

/**
 * Created by Kill3r
 */
public class BattleGrounds extends PlayerCommand {

    public BattleGrounds(){
        super("bg");
    }

    public void execute(Player player, String...params){
        if(params[0].equalsIgnoreCase("")){
            PacketSendUtility.sendMessage(player, ".bg reg\n.bg unreg\n.bg info");
        }
        if(params[0].equalsIgnoreCase("reg")){
            BattleGroundService.getBGService().registerPlayer(player);
            return;
        }

        if(params[0].equalsIgnoreCase("unreg")){
            BattleGroundService.getBGService().unregisterPlayer(player);
            return;
        }

        if(params[0].equalsIgnoreCase("info")){
            BattleGroundService.getBGService().ShowInfoOfAnyAvailableBG(player);
            return;
        }

        if(params[0].equalsIgnoreCase("test")){
            if(!(player.getAccessLevel() < 1)){
                if(!BattleGroundService.getBGService().TeamVsTeamOpenForReg){
                    BattleGroundService.getBGService().CallForRegistering();
                }else{
                    PacketSendUtility.sendMessage(player, "The BattleGround is Already Opened for Registration!!");
                }

            }
        }

        if(params[0].equalsIgnoreCase("stop")){
            if(!(player.getAccessLevel() < 1)){
                if(BattleGroundService.getBGService().TeamVsTeamOpenForReg){
                    BattleGroundService.getBGService().forceStopBG();
                    PacketSendUtility.sendMessage(player, "Stopped!");
                }else{
                    PacketSendUtility.sendMessage(player, "There is no BattleGround Running to stop!");
                }

            }
        }
    }

}
