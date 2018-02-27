package admincommands;

import com.aionemu.gameserver.model.gameobjects.player.Player;
import com.aionemu.gameserver.utils.chathandlers.AdminCommand;
import com.aionemu.gameserver.world.World;

/**
 * Created by Kill3r
 */
public class Misc extends AdminCommand {

    public Misc(){
        super("misc");
    }

    public void execute(Player admin, String...params){
        Player player = World.getInstance().findPlayer(params[0]);

        if(params[1].equals("slap")){
            //TODO
        }
    }
}
