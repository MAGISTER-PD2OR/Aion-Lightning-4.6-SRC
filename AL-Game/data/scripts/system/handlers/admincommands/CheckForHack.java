package admincommands;

import com.aionemu.gameserver.model.gameobjects.player.Player;
import com.aionemu.gameserver.services.antihack.AntiHackService;
import com.aionemu.gameserver.utils.PacketSendUtility;
import com.aionemu.gameserver.utils.chathandlers.AdminCommand;
import com.aionemu.gameserver.world.World;

/**
 * Created by Kill3r
 */
public class CheckForHack extends AdminCommand {

    public CheckForHack() {
        super("check");
    }

    public void execute(Player admin, String...params){
        if(params.length == 0){
            onFail(admin, "");
        }
        Player player = World.getInstance().findPlayer(params[1]);
        if(params[0].equals("info")){
            PacketSendUtility.sendMessage(admin, "===== INFORMATIONS =====");
            PacketSendUtility.sendMessage(admin, "" +
                    "Account : " + player.getPlayerAccount().getName() + "\n" +
                    "Logged in as : " + player.getName() + "\n" +
                    "Access Level : " + checkAccessLevel(player) + "\n" +
                    "Visual State : " + CheckSeeState(player));
        }
    }
//todo make ur own visual info and give return saying if he can see or not.. :O
    public String checkAccessLevel(Player player){
        int AccessLevel;
        String Rank = null;
        AccessLevel = player.getAccessLevel();
        switch (AccessLevel){
            case 0:
                Rank = "Normal Player";
                break;
            case 1:
                Rank = "TrialGM";
                break;
            case 2:
                Rank = "GM";
                break;
            case 3:
                Rank = "HeadGM";
                break;
            case 4:
                Rank = "Developer";
                break;
            case 5:
                Rank = "InsaneGod";
                break;
        }
        return Rank;
    }

    public String CheckSeeState(Player player){
        int SeeStateLevel;
        String State = null;
        SeeStateLevel = player.getSeeState();
        switch (SeeStateLevel){
            case 0:
                State = "Normal (Level 0)";
                break;
            case 1:
                State = "See-Through: Hide I (Level 1)";
                break;
            case 2:
                State = "See-Through: Hide II (Level 2)";
                break;
            case 5:
                State = "NPC Stealth";
                break;
            case 10:
                State = "NPC Stealth (new)";
                break;
        }
        return State;
    }
}
