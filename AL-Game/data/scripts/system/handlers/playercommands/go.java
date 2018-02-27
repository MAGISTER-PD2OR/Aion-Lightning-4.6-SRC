package playercommands;

import com.aionemu.commons.utils.Rnd;
import com.aionemu.gameserver.model.Race;
import com.aionemu.gameserver.model.gameobjects.player.Player;
import com.aionemu.gameserver.services.ecfunctions.ffa.FFaStruct;
import com.aionemu.gameserver.services.instance.InstanceService;
import com.aionemu.gameserver.services.teleport.TeleportService2;
import com.aionemu.gameserver.utils.PacketSendUtility;
import com.aionemu.gameserver.utils.chathandlers.PlayerCommand;
import com.aionemu.gameserver.utils.i18n.CustomMessageId;
import com.aionemu.gameserver.utils.i18n.LanguageHandler;
import com.aionemu.gameserver.world.World;
import com.aionemu.gameserver.world.WorldMap;
import com.aionemu.gameserver.world.WorldMapInstance;
import com.aionemu.gameserver.world.WorldMapType;

import java.util.Calendar;

/**
 * Created by Kill3r
 */
public class go extends PlayerCommand {

    public go() {
        super("go");
    }

    public void execute(Player player, String...param){

        Calendar calendar = Calendar.getInstance();

        if (calendar.get(Calendar.DAY_OF_WEEK) == Calendar.SUNDAY){
            monPvP(player);
            givePvPWelcomeMsg(player, "MonPvP");
        }else if (calendar.get(Calendar.DAY_OF_WEEK) == Calendar.MONDAY){
            sunPvP(player);
            givePvPWelcomeMsg(player, "SunPvP");
        }else if (calendar.get(Calendar.DAY_OF_WEEK) == Calendar.TUESDAY){
            monPvP(player);
            givePvPWelcomeMsg(player, "MonPvP");
        }else if (calendar.get(Calendar.DAY_OF_WEEK) == Calendar.WEDNESDAY){
            sunPvP(player);
            givePvPWelcomeMsg(player, "SunPvP");
        }else if (calendar.get(Calendar.DAY_OF_WEEK) == Calendar.THURSDAY){
            monPvP(player);
            givePvPWelcomeMsg(player, "MonPvP");
        }else if (calendar.get(Calendar.DAY_OF_WEEK) == Calendar.FRIDAY){
            sunPvP(player);
            givePvPWelcomeMsg(player, "SunPvP");
        }else if (calendar.get(Calendar.DAY_OF_WEEK) == Calendar.SATURDAY){
            monPvP(player);
            givePvPWelcomeMsg(player, "MonPvP");
        }
    }
//482.7439 ,496.9774 f1281.2216
    private void sunPvP(Player player){
        checkotherEvents(player);
        if(player.getWorldId() == 600040000){
            PacketSendUtility.sendMessage(player, "You cannot use the command inside the PvP Map!");
            return;
        }
        if (player.getRace() == Race.ELYOS && player.getWorldId() != 600040000 && !player.isInPrison()) {
            if(Rnd.get(1, 10) < 5){
                goTo(player, WorldMapType.TIAMARANTA_EYE_2.getId(), 753, 134, 1196);
            }else{
                goTo(player, WorldMapType.TIAMARANTA_EYE_2.getId(), 495.91855f, 1034.076f, 1280.1158f);
            }
        } else if (player.getRace() == Race.ASMODIANS && player.getWorldId() != 600040000 && !player.isInPrison()) {
            if(Rnd.get(1, 10) < 5){
                goTo(player, WorldMapType.TIAMARANTA_EYE_2.getId(), 754, 1459, 1196);
            }else{
                goTo(player, WorldMapType.TIAMARANTA_EYE_2.getId(), 482.54095f, 496.6196f, 1283.3533f);
            }
        }

    }

    private void monPvP(Player player){
        checkotherEvents(player);
        if(player.getWorldId() == 220070000){
            PacketSendUtility.sendMessage(player, "You cannot use the command inside the PvP Map!");
            return;
        }
        if (player.getRace() == Race.ELYOS && player.getWorldId() != 220070000 && !player.isInPrison()) {
            if(Rnd.get(1, 10) < 5){
                goTo(player, WorldMapType.GELKMAROS.getId(), 1197.3704f,857.05475f, 309.3598f);
            }else{
                goTo(player, WorldMapType.GELKMAROS.getId(), 1553.0142f, 1277.1173f, 414.87878f);
            }
        } else if (player.getRace() == Race.ASMODIANS && player.getWorldId() != 220070000 &&  !player.isInPrison()) {
            if(Rnd.get(1, 10) < 5){
                goTo(player, WorldMapType.GELKMAROS.getId(), 1880.3096f, 1095.7744f, 326.2095f);
            }else{
                goTo(player, WorldMapType.GELKMAROS.getId(), 1483.1908f, 1492.5492f, 435.4895f);
            }
        }
    }
////moveto 220070000 1483.098 1492.68 431.48123
    private void tuePvP(Player player){
        checkotherEvents(player);
        if (player.getRace() == Race.ASMODIANS && player.getWorldId() != 210050000 && !player.isInPrison()) {
            goTo(player, WorldMapType.INGGISON.getId(), 863.8717f,1930.6174f,336.65732f);
        } else if (player.getRace() == Race.ELYOS && player.getWorldId() != 210050000 && !player.isInPrison()) {
            goTo(player, WorldMapType.INGGISON.getId(), 1743.1421f ,2185.2563f, 324.23248f);
        }
    }

    private void wedPvP(Player player){
        checkotherEvents(player);
        if (player.getRace() == Race.ASMODIANS  && player.getWorldId() != 600030000 && !player.isInPrison()) {
            goTo(player, WorldMapType.TIAMARANTA.getId(), 1466.19f,2007.3654f,295.217576f);
        } else if (player.getRace() == Race.ELYOS && player.getWorldId() != 600030000 && !player.isInPrison()) {
            goTo(player, WorldMapType.TIAMARANTA.getId(), 1486.2932f,1041.3167f,284.4673f);
        }
    }

    private void thusPvP(Player player){
        checkotherEvents(player);
        if (player.getRace() == Race.ASMODIANS && !player.isInPrison()) {
            goTo(player, WorldMapType.SILENTERA_CANYON.getId(), 503.3973f,1162.3539f,329.93634f);
        } else if (player.getRace() == Race.ELYOS && !player.isInPrison()) {
            goTo(player, WorldMapType.SILENTERA_CANYON.getId(), 525.7626f,392.47687f,324.5844f);
        }

    }

    private void friPvP(Player player){
        checkotherEvents(player);
        if (player.getRace() == Race.ASMODIANS && player.getWorldId() != 600060000 && !player.isInPrison()) {
            goTo(player, WorldMapType.DANARIA.getId(), 2162.8108f,2076.9736f,374.4204f);
        } else if (player.getRace() == Race.ELYOS && player.getWorldId() != 600060000 && !player.isInPrison()) {
            goTo(player, WorldMapType.DANARIA.getId(), 1858.3137f,1504.4423f,209.15251f);
        }
    }

    private void satPvP(Player player){
        checkotherEvents(player);
        if (player.getRace() == Race.ASMODIANS && player.getWorldId() != 600050000 && !player.isInPrison()) {
            goTo(player, WorldMapType.KATALAM.getId(), 1012.7191f,1221.0043f,232.15218f);
        } else if (player.getRace() == Race.ELYOS && player.getWorldId() != 600050000 && !player.isInPrison()) {
            goTo(player, WorldMapType.KATALAM.getId(), 1484.027f,1623.1929f,197.86057f);
        }
    }


    private void checkotherEvents(Player player){
        if (player.isAttackMode()) {
            PacketSendUtility.sendMessage(player, LanguageHandler.translate(CustomMessageId.NOT_USE_WHILE_FIGHT));
            return;
        }

        if (player.isInFFA() && player.getWorldId() == FFaStruct.worldId) {
            return;
        }
    }

    private static void goTo(final Player player, int worldId, float x, float y, float z) {
        WorldMap destinationMap = World.getInstance().getWorldMap(worldId);
        if (destinationMap.isInstanceType()) {
            TeleportService2.teleportTo(player, worldId, getInstanceId(worldId, player), x, y, z);
        } else {
            TeleportService2.teleportTo(player, worldId, x, y, z);
        }
    }

    private static int getInstanceId(int worldId, Player player) {
        if (player.getWorldId() == worldId) {
            WorldMapInstance registeredInstance = InstanceService.getRegisteredInstance(worldId, player.getObjectId());
            if (registeredInstance != null) {
                return registeredInstance.getInstanceId();
            }
        }
        WorldMapInstance newInstance = InstanceService.getNextAvailableInstance(worldId);
        InstanceService.registerPlayerWithInstance(newInstance, player);
        return newInstance.getInstanceId();
    }

    private void givePvPWelcomeMsg(Player player, String PvPMap){
        String msg = "";
        if(PvPMap.equalsIgnoreCase("SunPvP")){
            PacketSendUtility.sendMessage(player, "sun1");
            if(player.getWorldId() == 600040000){
                PacketSendUtility.sendMessage(player, "sun");
                return;
            }
        }else if(PvPMap.equalsIgnoreCase("MonPvP")){
            PacketSendUtility.sendMessage(player, "mon1");
            if(player.getWorldId() == 220070000){
                PacketSendUtility.sendMessage(player, "mon");
                return;
            }
        }//else if(PvPMap.equalsIgnoreCase("TuePvP")){
        //    return;
        //}else if(PvPMap.equalsIgnoreCase("WedPvP")){
        //    return;
        //}else if(PvPMap.equalsIgnoreCase("ThuPvP")){
        //    return;
        //}else if(PvPMap.equalsIgnoreCase("FriPvP")){
        //    return;
        //}else if(PvPMap.equalsIgnoreCase("SatPvP")){
        //    return;
        //}

        if(player.getRace() == Race.ASMODIANS){
            msg = "all the ELYOS :]";
        }else if(player.getRace() == Race.ELYOS){
            msg = "all the ASMODIANS :]";
        }
        PacketSendUtility.sendYellowMessageOnCenter(player, "[PvP Zone] Welcome to the PvP Zone!!");
        PacketSendUtility.sendYellowMessage(player, "\n[PvP Rules]" +
                "\n # No Camping at Spawn Area" +
                "\n # No Hacking" +
                "\n # No Bug Abusing" +
                "\n # And as always remember to kill "+ msg);
    }
}
