package com.aionemu.gameserver.services.UEvents.Kor;

import com.aionemu.gameserver.instance.InstanceEngine;
import com.aionemu.gameserver.model.gameobjects.player.Player;
import com.aionemu.gameserver.model.team2.group.PlayerGroup;
import com.aionemu.gameserver.network.aion.serverpackets.SM_QUEST_ACTION;
import com.aionemu.gameserver.services.teleport.TeleportService2;
import com.aionemu.gameserver.skillengine.effect.AbnormalState;
import com.aionemu.gameserver.spawnengine.SpawnEngine;
import com.aionemu.gameserver.utils.PacketSendUtility;
import com.aionemu.gameserver.utils.ThreadPoolManager;
import com.aionemu.gameserver.world.World;
import com.aionemu.gameserver.world.WorldMap;
import com.aionemu.gameserver.world.WorldMapInstance;
import com.aionemu.gameserver.world.WorldMapInstanceFactory;
import com.aionemu.gameserver.world.knownlist.Visitor;
import javolution.util.FastList;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by Kill3r
 */
public class KorService {

    /**
     * TODO make korForceStop
     * TODO make wat happens after round 1 fight finishes
     * TODO after p1 and p3 dies.. p2 or p4 need to get ported to arena and move the p1 or p3 that died to above.. so means : if p1 dies p2 is his group member that needs to be ported down.
     * TODO make instance no break
     * TODO korIsON but not important for now.. if spectator is impleneted
     * TODO
     */


    private static final Logger log = LoggerFactory.getLogger(KorService.class);
    public static final KorService service = new KorService();
    WorldMapInstance korInstance = null;
    public int worldId = 300310000;
    private FastList<PlayerGroup> groups = new FastList<PlayerGroup>();
    private FastList<Player> players = new FastList<Player>();
    private boolean regOpen = false;
    public boolean KorIsOn = false;

    public KorService getInstance(){
        return service;
    }

    public void autoAnnouncer(int delayinMin){
        ThreadPoolManager.getInstance().scheduleAtFixedRate(new Runnable() {
            @Override
            public void run() {
                // call for runn
            }
        }, delayinMin / 2 * 1000 * 60, delayinMin / 2 * 1000 * 60);
    }

    public void AnnounceForRegister(){
        announceAll("A KOR Event has been opened for Registration! Registration will be closed in 2min. Type '.kor reg' to register!");
        regOpen = true;
        ThreadPoolManager.getInstance().schedule(new Runnable() {
            @Override
            public void run() {
                tryStartEvent();
            }
        },120000);
    }

    public void registerPlayer(Player player){
        if(!regOpen){
            PacketSendUtility.sendMessage(player, "There is no KOR event Running at this time!");
            return;
        }
         if(player.getPlayerGroup2().size() == 2 && player.getPlayerGroup2().isLeader(player)){
             if(player.isInFFA()){
                 PacketSendUtility.sendMessage(player, "You cannot register while inside FFA!");
                 return;
             }
             if(player.isInPrison()){
                 PacketSendUtility.sendMessage(player, "Sorry you better stay inside prison and think of what you did wrong.. lol");
                 return;
             }
             PlayerGroup PlayersGroup = player.getPlayerGroup2();
             if(players.contains(player)){
                 PacketSendUtility.sendMessage(player, "You're Already Registered!");
                 return;
             }
             if(groups.size() == 2){
                 PacketSendUtility.sendMessage(player, "Insane Auto Kor Service : KOR is Full..");
                 return;
             }
             players.add(player);
             groups.add(PlayersGroup);
         }else{
             PacketSendUtility.sendMessage(player, "You need only 2 players in group to go to this Event! And Only Leader can register!");
         }
    }

    public void unregisterPlayer(Player player){
        if(players.contains(player)){
            players.remove(player);
            if(groups.contains(player.getPlayerGroup2())){
                groups.remove(player.getPlayerGroup2());
            }
        }else{
            PacketSendUtility.sendMessage(player, "You're not registered on the Event!");
        }
    }

    public void tryStartEvent(){
        regOpen = false;
        if(groups.size() <= 1){
            announceAll("Closing the Event, Not enough Players!");
            return;
        }
        if(groups.size() == 2){
            PlayerGroup firstGroup = groups.get(0);
            PlayerGroup secondGroup = groups.get(1);

            Player grp1Lead = null, grp1Player = null;
            Player grp2Lead = null, grp2Player = null;


            for(Player pg1 : firstGroup.getMembers()){
                if(!pg1.getPlayerGroup2().isLeader(pg1)){
                    grp1Player = pg1;
                }else{
                    grp1Lead = pg1;
                }
            }

            for(Player pg2 : secondGroup.getMembers()){
                if(pg2.getPlayerGroup2().isLeader(pg2)){
                    grp2Player = pg2;
                }else{
                    grp2Lead = pg2;
                }
            }

            if(korInstance == null){
                createNewInstance();
                //First grp ported
                TeleportService2.teleportTo(grp1Lead, worldId, korInstance.getInstanceId(), 1063.388f, 978.6689f, 145.45468f);
                TeleportService2.teleportTo(grp1Player, worldId, korInstance.getInstanceId(), 1063.388f, 978.6689f, 145.45468f);

                //second grp Ported
                TeleportService2.teleportTo(grp2Lead, worldId, korInstance.getInstanceId(), 1075.3994f, 977.4704f, 145.28569f);
                TeleportService2.teleportTo(grp2Player, worldId, korInstance.getInstanceId(), 1075.3994f, 977.4704f, 145.28569f);
            }else{
                TeleportService2.teleportTo(grp1Lead, worldId, korInstance.getInstanceId(), 1063.388f, 978.6689f, 145.45468f);
                TeleportService2.teleportTo(grp1Player, worldId, korInstance.getInstanceId(), 1063.388f, 978.6689f, 145.45468f);

                //second grp Ported
                TeleportService2.teleportTo(grp2Lead, worldId, korInstance.getInstanceId(), 1075.3994f, 977.4704f, 145.28569f);
                TeleportService2.teleportTo(grp2Player, worldId, korInstance.getInstanceId(), 1075.3994f, 977.4704f, 145.28569f);
            }
            makePlayersSleep(grp1Lead, grp1Player, grp2Lead, grp2Player);

            announceInstanceOnly("Welcome To Auto KOR Event!");
            announceInstanceOnly("Please Read the chatwindow on how the Event will be going!");
            announceInstanceChatWind("==== KOR INSTANCE INFO ===");
            announceInstanceChatWind("#The Leader First must give out the name that who will be going first to fight, by typing .kor send [playername]");
            announceInstanceChatWind("#You must give the name in the next '1 minute' after giving the name the System will automatically ports the fighter into the arena");
            announceInstanceChatWind("#You must enter the name AFTER you get ported to watching area ( Sky View/Top Platform )");
            announceInstanceChatWind("#And yeah.. You wont be able to move until you're going to Fight you're Enemy!!");
            announceInstanceChatWind("==== END OF INFO ===");
            giveSomeMinToReadAndPortUp(grp1Lead, grp1Player, grp2Lead, grp2Player);
            clearGroups();
        }
    }

    public void forceStopKor(){
        regOpen = false;

    }

    public void clearGroups(){
        groups.clear();
        players.clear();
    }

    public void cancelAllStatesofPlayers(Player p1, Player p2, Player p3, Player p4){
        p1.battlegroundWaiting = false;
        p2.battlegroundWaiting = false;
        p3.battlegroundWaiting = false;
        p4.battlegroundWaiting = false;
    }

    public void giveSomeMinToReadAndPortUp(final Player p1, final Player p2, final Player p3, final Player p4){
        ThreadPoolManager.getInstance().schedule(new Runnable() {
            @Override
            public void run() {
                p1.battlegroundWaiting = true;
                p2.battlegroundWaiting = true;
                p3.battlegroundWaiting = true;
                p4.battlegroundWaiting = true;
                announceInstanceOnly("Porting to Watching Area!");
                ThreadPoolManager.getInstance().schedule(new Runnable() {
                    @Override
                    public void run() {
                        //group 2
                        TeleportService2.teleportTo(p1, worldId, 1107.9023f, 975.87506f, 193.74881f);
                        TeleportService2.teleportTo(p2, worldId, 1105.7681f, 997.89557f, 193.74887f);

                        //group 2
                        TeleportService2.teleportTo(p1, worldId, 1036.813f, 961.40186f, 186.18481f);
                        TeleportService2.teleportTo(p2, worldId, 1034.3918f, 958.13605f, 186.33684f);

                        sendTimePacket(p1, 1);
                        sendTimePacket(p2, 1);
                        sendTimePacket(p3, 1);
                        sendTimePacket(p4, 1);
                        announceInstanceOnly("Selecting Time Has Begun!! Group Leaders.. Type .kor send [playername] to send to fight!!");
                        announceInstanceOnly("You must give the name before the Time limit is over!!");
                        ThreadPoolManager.getInstance().schedule(new Runnable() {
                            @Override
                            public void run() {
                                portToArenaAfterTimeLimitIsDone(p1, p2, p3, p4);
                            }
                        },60000);
                    }
                }, 3000);
            }
        },20000);
    }

    public void portToArenaAfterTimeLimitIsDone(final Player p1, final Player p2, final Player p3, final Player p4){
        ThreadPoolManager.getInstance().schedule(new Runnable() {
            @Override
            public void run() {
                String group1SendName = p1.getKorSendName();
                String group2SendName = p3.getKorSendName();
                final Player p1fight = World.getInstance().findPlayer(group1SendName);
                final Player p2fight = World.getInstance().findPlayer(group2SendName);

                TeleportService2.teleportTo(p1fight, worldId, 1068.5292f, 961.19165f, 139.62196f);
                TeleportService2.teleportTo(p2fight, worldId, 1062.6915f, 893.2629f, 140.48427f);
                announceInstanceOnly("Starting in...3");
                ThreadPoolManager.getInstance().schedule(new Runnable() {
                    @Override
                    public void run() {
                        announceInstanceOnly("2...");
                        ThreadPoolManager.getInstance().schedule(new Runnable() {
                            @Override
                            public void run() {
                                announceInstanceOnly("1...");
                                p1fight.getEffectController().unsetAbnormal(AbnormalState.SLEEP.getId());
                                p2fight.getEffectController().unsetAbnormal(AbnormalState.SLEEP.getId());
                                ThreadPoolManager.getInstance().schedule(new Runnable() {
                                    @Override
                                    public void run() {
                                        announceInstanceOnly("FIGHT!!!");
                                    }
                                },1000);
                            }
                        },1000);
                    }
                },1000);
            }
        },60000);
    }

    public void sendTimePacket(Player player, int timeleft){
        PacketSendUtility.sendPacket(player, new SM_QUEST_ACTION(0, timeleft));
    }

    public void makePlayersSleep(Player p1, Player p2, Player p3, Player p4){
        //group 1
        p1.getEffectController().setAbnormal(AbnormalState.SLEEP.getId());
        p2.getEffectController().setAbnormal(AbnormalState.SLEEP.getId());

        //group 2
        p3.getEffectController().setAbnormal(AbnormalState.SLEEP.getId());
        p4.getEffectController().setAbnormal(AbnormalState.SLEEP.getId());
    }

    public WorldMapInstance createNewInstance(){
        korInstance = getNextKORInstance(worldId);
        return korInstance;
    }

    private WorldMapInstance getNextKORInstance(int worldId) {
        WorldMap map = World.getInstance().getWorldMap(worldId);

        if (!map.isInstanceType()) {
            throw new UnsupportedOperationException("Invalid call for next available instance  of " + worldId);
        }

        int nextInstanceId = map.getNextInstanceId();

        log.info("[KOR] Creating new KOR instance: " + nextInstanceId);

        WorldMapInstance pvpArenaInstance = WorldMapInstanceFactory.createWorldMapInstance(map, nextInstanceId);
        // InstanceService.startInstanceChecker(pvpArenaInstance);
        map.addInstance(nextInstanceId, pvpArenaInstance);
        SpawnEngine.spawnInstance(worldId, pvpArenaInstance.getInstanceId(), (byte) 1);
        InstanceEngine.getInstance().onInstanceCreate(pvpArenaInstance);
        return pvpArenaInstance;
    }

    public void announceInstanceChatWind(final String msg){
        korInstance.doOnAllPlayers(new Visitor<Player>() {
            @Override
            public void visit(Player object) {
                PacketSendUtility.sendBrightYellowMessage(object, msg);
            }
        });
    }

    public void announceInstanceOnly(final String msg){
        korInstance.doOnAllPlayers(new Visitor<Player>() {
            @Override
            public void visit(Player object) {
                PacketSendUtility.sendSys2Message(object, "AutoKOR", msg);
            }
        });
    }

    public void announceAll(final String msg){
        World.getInstance().doOnAllPlayers(new Visitor<Player>() {
            @Override
            public void visit(Player object) {
                PacketSendUtility.sendSys2Message(object, "AutoKOR", msg);
            }
        });
    }

}
