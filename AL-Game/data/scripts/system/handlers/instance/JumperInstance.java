package instance;

import com.aionemu.gameserver.instance.handlers.GeneralInstanceHandler;
import com.aionemu.gameserver.instance.handlers.InstanceID;
import com.aionemu.gameserver.model.EmotionType;
import com.aionemu.gameserver.model.flyring.FlyRing;
import com.aionemu.gameserver.model.gameobjects.Creature;
import com.aionemu.gameserver.model.gameobjects.Npc;
import com.aionemu.gameserver.model.gameobjects.player.Player;
import com.aionemu.gameserver.model.templates.flyring.FlyRingTemplate;
import com.aionemu.gameserver.model.utils3d.Point3D;
import com.aionemu.gameserver.network.aion.serverpackets.SM_DIE;
import com.aionemu.gameserver.network.aion.serverpackets.SM_EMOTION;
import com.aionemu.gameserver.network.aion.serverpackets.SM_SYSTEM_MESSAGE;
import com.aionemu.gameserver.services.abyss.AbyssPointsService;
import com.aionemu.gameserver.services.player.PlayerReviveService;
import com.aionemu.gameserver.services.teleport.TeleportService2;
import com.aionemu.gameserver.utils.PacketSendUtility;
import com.aionemu.gameserver.world.WorldMapInstance;
import com.aionemu.gameserver.world.WorldMapType;


/**
 * Created by Kill3r
 */
@InstanceID(300260000)
public class JumperInstance extends GeneralInstanceHandler {


    public boolean CheckPoints1 = false;
    public boolean CheckPoints2 = false;
    public boolean CheckPoints3 = false;
    public boolean CheckPoints4 = false;
    public boolean CheckPoints5 = false;



    @Override
    public void onInstanceCreate(WorldMapInstance instance){
        super.onInstanceCreate(instance);
        spawnRings();

    }

    @Override
    public void onEnterInstance(Player player) {
        super.onEnterInstance(player);
        PacketSendUtility.sendYellowMessageOnCenter(player, "** THE CHECKPOINTS WILL WORK, AFTER YOU ACQUIRE THEM , YOU NEED TO DIE TO SPAWN TO LASTEST CHECK POINT **");

        player.setInPvEMode(true);
    }

    private void spawnRings() {
        //startpoint
        FlyRing start = new FlyRing(new FlyRingTemplate("startPoint",mapId,
                new Point3D(488.62982,408.94666,229.92035),
                new Point3D(491.17505,408.6693,230.36478),
                new Point3D(485.9948,407.91498,229.08716), 4),instanceId);

        //Random Messages 1
        FlyRing m1 = new FlyRing(new FlyRingTemplate("M_1",mapId,
                new Point3D(619.7292,460.3563,301.86456),
                new Point3D(571.86176,412.77173,303.10373),
                new Point3D(574.47723,415.31375,303.10373), 5), instanceId);

        //Random Messages 2
        FlyRing m2 = new FlyRing(new FlyRingTemplate("M_2",mapId,
                new Point3D(619.7484,460.4234,301.86456),
                new Point3D(618.6948,459.31595,302.3865),
                new Point3D(620.5383,461.15924,301.58614), 2), instanceId);

        //Random Messages 3
        FlyRing m3 = new FlyRing(new FlyRingTemplate("M_3",mapId,
                new Point3D(667.9763,512.048,296.23526),
                new Point3D(667.254,511.06332,296.23526),
                new Point3D(668.7992,513.00226,296.23526), 5), instanceId);

        //Random Messages 4
        FlyRing m4 = new FlyRing(new FlyRingTemplate("M_4",mapId,
                new Point3D(762.51825,624.2326,283.65195),
                new Point3D(762.1071,623.69916,283.65195),
                new Point3D(764.6402,625.50305,283.52222), 5), instanceId);

        //Random Messages 5
        FlyRing m5 = new FlyRing(new FlyRingTemplate("M_5",mapId,
                new Point3D(957.4132,608.17865,449.5915),
                new Point3D(958.5453,606.6664,449.50757),
                new Point3D(959.3851,609.58057,449.42743), 4), instanceId);

        //chk 1
        FlyRing f1 = new FlyRing(new FlyRingTemplate("Chk_1",mapId,
                new Point3D(537.7162,342.9676,265.019),
                new Point3D(537.7162,342.96765,263.0526),
                new Point3D(541.3484,344.54123,263.0181),4),instanceId);
        //chk 2
        FlyRing f2 = new FlyRing(new FlyRingTemplate("Chk_2",mapId,
                new Point3D(550.72406,387.07135,291.01596),
                new Point3D(550.72406,387.07135,287.67798),
                new Point3D(551.31903,383.68204,287.51486), 5), instanceId);
        //chk 3
        FlyRing f3 = new FlyRing(new FlyRingTemplate("Chk_3",mapId,
                new Point3D(557.71686,400.93616,303.9426),
                new Point3D(557.34564,401.35144,303.9426),
                new Point3D(558.50916,400.1681,303.9426), 5), instanceId);
        //chk 4
        FlyRing f4 = new FlyRing(new FlyRingTemplate("Chk_4",mapId,
                new Point3D(712.34204,562.95264,270.0317),
                new Point3D(711.5846,561.9497,270.0317),
                new Point3D(713.4466,564.3221,270.0317), 5), instanceId);

        //chk 5
        FlyRing f5 = new FlyRing(new FlyRingTemplate("Chk_5",mapId,
                new Point3D(753.89374,616.0002,283.74316),
                new Point3D(753.1616,615.313,283.74316),
                new Point3D(754.9261,616.9717,283.74316), 5), instanceId);

        //chk 6
        FlyRing f6 = new FlyRing(new FlyRingTemplate("Chk_6",mapId,
                new Point3D(776.81665,633.28156,285.56766),
                new Point3D(777.8221,632.5908,285.56766),
                new Point3D(775.6534,633.37854,285.56766), 5), instanceId);

        //finish point 1
        FlyRing p1 = new FlyRing(new FlyRingTemplate("p1",mapId,
                new Point3D(783.9687,668.7973,274.73468),
                new Point3D(784.7185,668.33405,274.73468),
                new Point3D(782.86053,669.8922,274.73468), 5), instanceId);

        //finish point 2
        FlyRing p2 = new FlyRing(new FlyRingTemplate("p2",mapId,
                new Point3D(797.07904,663.84076,275.06848),
                new Point3D(797.47394,663.1475,275.06848),
                new Point3D(796.6031,66508966,275.06848), 5), instanceId);

        //finish point 3
        FlyRing p3 = new FlyRing(new FlyRingTemplate("p3",mapId,
                new Point3D(799.0103,647.3356,274.89664),
                new Point3D(799.59485,646.18915,274.89664),
                new Point3D(798.7788,648.57855,274.89664), 5), instanceId);

        //return
        FlyRing ret = new FlyRing(new FlyRingTemplate("return",mapId,
                new Point3D(970.17847,606.0274,448.68478),
                new Point3D(970.26276,605.00854,448.43646),
                new Point3D(970.3862,607.3022,448.68655), 5), instanceId);

        start.spawn();
        m5.spawn();
        f2.spawn();
        f1.spawn();
        f3.spawn();
        f4.spawn();
        m1.spawn();
        m2.spawn();
        m3.spawn();
        f5.spawn();
        m4.spawn();
        f6.spawn();
        p1.spawn();
        p2.spawn();
        p3.spawn();
        ret.spawn();
    }

    public boolean onPassFlyingRing(Player player, String FlyingRing){
        if(FlyingRing.equals("startPoint")){
            PacketSendUtility.sendWhiteMessageOnCenter(player, "You've started the Jumping Event, Good Luck Pro Jumper!");
        }

        if(FlyingRing.equals("M_1")){
            PacketSendUtility.sendWhiteMessageOnCenter(player, "You better watch you're step!! :D");
        }

        if(FlyingRing.equals("M_2")){
            PacketSendUtility.sendWhiteMessageOnCenter(player, "Huh, It seems you got good Eyes...");
        }

        if(FlyingRing.equals("M_3")){
            PacketSendUtility.sendWhiteMessageOnCenter(player, "Time to Open those Wings and Glide!!");
        }

        if(FlyingRing.equals("M_4")){
            PacketSendUtility.sendWhiteMessageOnCenter(player, "Almost there!! You can do it !!");
            PacketSendUtility.announceAll("'"+player.getName()+"' is almost finishing the Jumping Event!!");
        }

        if(FlyingRing.equals("M_5")){
            PacketSendUtility.sendWhiteMessageOnCenter(player, "You can return by going through the glowing light behind you!!");

        }

        if(FlyingRing.equals("Chk_1")){
            PacketSendUtility.sendWhiteMessageOnCenter(player, "CheckPoint #1 Acquired!!");
            player.setCheckpoints(1);
        }
        if (FlyingRing.equals("Chk_2")){
            PacketSendUtility.sendWhiteMessageOnCenter(player, "Checkpoint #2 Acquired!!");
            player.setCheckpoints(2);
        }
        if(FlyingRing.equals("Chk_3")){
            PacketSendUtility.sendWhiteMessageOnCenter(player, "Checkpoint #3 Acquired!!");
            player.setCheckpoints(3);
        }
        if(FlyingRing.equals("Chk_4")){
            PacketSendUtility.sendWhiteMessageOnCenter(player, "Checkpoint #4 Acquired!!");
            player.setCheckpoints(4);
        }
        if(FlyingRing.equals("Chk_5")){
            PacketSendUtility.sendWhiteMessageOnCenter(player, "Checkpoint #5 Acquired!!");
            player.setCheckpoints(5);
        }
        if(FlyingRing.equals("Chk_6")){
            PacketSendUtility.sendWhiteMessageOnCenter(player, "Checkpoint #6 Acquired!!");
            player.setCheckpoints(6);
        }
        boolean test = false;
        if(FlyingRing.equals("p1")){
            PacketSendUtility.sendWhiteMessageOnCenter(player, "Congratulation!! You've Completed the Event !!");
            PacketSendUtility.sendWhiteMessageOnCenter(player, "Okay, I got some reward for you :)");

            TeleportService2.teleportTo(player, 300260000, 955.99756f,606.7148f,449.70532f);
            PacketSendUtility.announceAll("'"+player.getName()+"' Has Completed the Jumping Event!!! ");
            player.setCheckpoints(99);
            if(test == true){
                PacketSendUtility.sendMessage(player, "You've already completed the instance");
            }
            AbyssPointsService.addGp(player, 4500);
            test = true;
        }

        if(FlyingRing.equals("p2")){
            PacketSendUtility.sendWhiteMessageOnCenter(player, "Congratulation!! You've Completed the Event !!");
            PacketSendUtility.sendWhiteMessageOnCenter(player, "Okay, I got some reward for you :)");

            TeleportService2.teleportTo(player, 300260000, 955.99756f,606.7148f,449.70532f);
            PacketSendUtility.announceAll("'"+player.getName()+"' Has Completed the Jumping Event!!! ");
            player.setCheckpoints(99);
            if(test == true){
                PacketSendUtility.sendMessage(player, "You've already completed the instance");
            }
            AbyssPointsService.addGp(player, 6000);
            test = true;
        }

        if(FlyingRing.equals("p3")){
            PacketSendUtility.sendWhiteMessageOnCenter(player, "Congratulation!! You've Completed the Event !!");
            PacketSendUtility.sendWhiteMessageOnCenter(player, "Okay, I got some reward for you :)");

            TeleportService2.teleportTo(player, 300260000, 955.99756f,606.7148f,449.70532f);
            PacketSendUtility.announceAll("'"+player.getName()+"' Has Completed the Jumping Event!!! ");
            player.setCheckpoints(99);
            if(test == true){
                PacketSendUtility.sendMessage(player, "You've already completed the instance");
            }
            AbyssPointsService.addGp(player, 5000);
            test = true;
        }

        if(FlyingRing.equals("return")){
            PacketSendUtility.announceMeOnly(player, "Returning to bind Point!!");
            test=false;
            TeleportService2.moveToBindLocation(player, true);
        }


        return false;
    }

    public boolean onDie(Player player, Creature lastAttacker){
        PacketSendUtility.broadcastPacket(player, new SM_EMOTION(player, EmotionType.DIE, 0, player.equals(lastAttacker) ? 0 : lastAttacker.getObjectId()), true);

        PacketSendUtility.sendPacket(player, new SM_DIE(false, false, 0, 8));
        return true;
    }


    public boolean onReviveEvent(Player player) {
        PlayerReviveService.revive(player,100,100,false,0);
        player.getGameStats().updateStatsAndSpeedVisually();
        PacketSendUtility.sendPacket(player, SM_SYSTEM_MESSAGE.STR_REBIRTH_MASSAGE_ME);

     //   if(player.getCheckpoints()){
      //      PacketSendUtility.sendMessage(player, "Teleporting to Checkpoint #1");
      //      TeleportService2.teleportTo(player, 300260000, 537.7162f,342.9676f,265.019f);
//
      //  }else if(CheckPoints2 = true){
      //      PacketSendUtility.sendMessage(player, "Teleporting to Checkpoint #2");
       //     TeleportService2.teleportTo(player, 300260000, 551.31903f,383.68204f,287.51486f);
//
       // }
       return true;
    }

    public void onPlayerLogOut(Player player) {
        player.setInPvEMode(false);
        TeleportService2.moveToBindLocation(player,true);
        player.setCheckpoints(0);
    }

    public void onLeaveInstance(Player player){
        player.setInPvEMode(false);
        player.setCheckpoints(0);
    }
}
