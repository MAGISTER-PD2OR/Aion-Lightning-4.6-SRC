package admincommands;

import com.aionemu.gameserver.model.gameobjects.VisibleObject;
import com.aionemu.gameserver.model.gameobjects.player.Player;
import com.aionemu.gameserver.model.templates.spawns.SpawnTemplate;
import com.aionemu.gameserver.services.teleport.TeleportService2;
import com.aionemu.gameserver.spawnengine.SpawnEngine;
import com.aionemu.gameserver.utils.PacketSendUtility;
import com.aionemu.gameserver.utils.chathandlers.AdminCommand;
import com.aionemu.gameserver.world.World;
import com.aionemu.gameserver.world.WorldMap;
import com.aionemu.gameserver.world.WorldMapInstance;
import com.aionemu.gameserver.world.WorldMapType;

/**
 * Created by Kill3r
 */
public class Recharger extends AdminCommand {

    public Recharger(){
        super("recharger");
    }
        private static VisibleObject RECHARGER;
        private static boolean isOpened = false;

    public void execute(Player player, String...params){
        if(params[0].equals("off")){
            if(isOpened){
                //DataManager.SPAWNS_DATA2.getTemplates().remove(RECHARGER);
                PacketSendUtility.sendMessage(player,"Recharger Closing!");
                isOpened = false;

            }
        }else if(params[0].equals("on")){
            float x = player.getX();
            float y = player.getY();
            float z = player.getZ();
            byte heading = player.getHeading();
            int worldId = player.getWorldId();
            if(!isOpened){
                SpawnTemplate spawn = SpawnEngine.addNewSpawn(worldId, 730397, x, y, z, heading, 0);
                VisibleObject visibleObject = SpawnEngine.spawnObject(spawn, player.getInstanceId());
                PacketSendUtility.sendMessage(player, visibleObject.getName() + " has been Summoned!");
                isOpened = true;
            }else{
                PacketSendUtility.sendMessage(player, "Already Open");
            }
        }
    }
}
