/**
 * This file is part of Aion-Lightning <aion-lightning.org>.
 *
 *  Aion-Lightning is free software: you can redistribute it and/or modify
 *  it under the terms of the GNU General Public License as published by
 *  the Free Software Foundation, either version 3 of the License, or
 *  (at your option) any later version.
 *
 *  Aion-Lightning is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  GNU General Public License for more details. *
 *  You should have received a copy of the GNU General Public License
 *  along with Aion-Lightning.
 *  If not, see <http://www.gnu.org/licenses/>.
 */

package com.aionemu.gameserver.services.UEvents;


import com.aionemu.commons.utils.Rnd;
import com.aionemu.gameserver.instance.InstanceEngine;
import com.aionemu.gameserver.model.Race;
import com.aionemu.gameserver.model.TeleportAnimation;
import com.aionemu.gameserver.model.gameobjects.player.Player;
import com.aionemu.gameserver.model.team2.TeamType;
import com.aionemu.gameserver.model.team2.group.PlayerGroup;
import com.aionemu.gameserver.model.team2.group.PlayerGroupMember;
import com.aionemu.gameserver.model.team2.group.PlayerGroupService;
import com.aionemu.gameserver.network.aion.serverpackets.SM_QUEST_ACTION;
import com.aionemu.gameserver.services.instance.InstanceService;
import com.aionemu.gameserver.services.teleport.TeleportService2;
import com.aionemu.gameserver.skillengine.model.SkillTargetSlot;
import com.aionemu.gameserver.spawnengine.SpawnEngine;
import com.aionemu.gameserver.utils.PacketSendUtility;
import com.aionemu.gameserver.utils.ThreadPoolManager;
import com.aionemu.gameserver.utils.idfactory.IDFactory;
import com.aionemu.gameserver.world.World;
import com.aionemu.gameserver.world.WorldMap;
import com.aionemu.gameserver.world.WorldMapInstance;
import com.aionemu.gameserver.world.WorldMapInstanceFactory;
import com.aionemu.gameserver.world.knownlist.Visitor;
import javolution.util.FastList;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;

/**
 * Created by Kill3r
 */
public class BattleGroundService {

    private static final Logger log = LoggerFactory.getLogger(BattleGroundService.class);
    WorldMapInstance bgInstance = null;
    public boolean TeamVsTeamOpenForReg = false;
    private FastList<Player> players = new FastList<Player>();
    public static ArrayList<Player> asmoList = new ArrayList<>();
    public static ArrayList<Player> elyosList = new ArrayList<>();
    private FastList<PlayerGroup> asmosGroupsReadyToGo = new FastList<PlayerGroup>();
    private FastList<PlayerGroup> elyosGroupsReadyToGo = new FastList<PlayerGroup>();
    private FastList<PlayerGroup> groups = new FastList<PlayerGroup>();
    private int asmos;
    private int elyos;
    private int asmoReg;
    private int elyosReg;
    private PlayerGroupMember member;
    public static final BattleGroundService BGService = new BattleGroundService();

    public static BattleGroundService getBGService(){
        return BGService;
    }

    public void announceForBGRegistering(int delayInMin){
        ThreadPoolManager.getInstance().scheduleAtFixedRate(new Runnable() {
            @Override
            public void run() {
                CallForRegistering();
            }
        }, delayInMin / 2 * 1000 * 60, delayInMin / 2 * 1000 * 60);
    }

    public void CallForRegistering(){
        if(TeamVsTeamOpenForReg){
            return;
        }
        log.info("BG TeamVSTeam Starting For registration!");
        announceAll("InsaneBG", "There is a BattleGround Arena ready for registration. Type '.bg reg' in the next 2min.");
            TeamVsTeamOpenForReg = true;
            ThreadPoolManager.getInstance().schedule(new Runnable() {
                @Override
                public void run() {
                    removeTheTimeLeftFromReggedPlayers();
                    bgInstance = (WorldMapInstance) createInstance();
                    BGService.makeGroup();
                }
            }, 120000);
    }

    public void sendTimePacket(Player player, int Time){
        PacketSendUtility.sendPacket(player, new SM_QUEST_ACTION(0, Time));
    }

    public void removeTheTimeLeftFromReggedPlayers(){
        World.getInstance().doOnAllPlayers(new Visitor<Player>() {
            @Override
            public void visit(Player object) {
                if(object.battlegroundWaiting){
                    sendTimePacket(object, 0);
                }
            }
        });
    }

    public void forceStopBG(){
        announceAll("InsaneBG", "BattleGround Arena has been Stopped and is no longer able to register!");
        log.info("BG BattleGRound Force Stop has been initialized!");
        TeamVsTeamOpenForReg = false;
        players.clear();
        asmoList.clear();
        elyosList.clear();
    }


    public void makeGroup() {
        Collections.shuffle(players);
        FastList<Player> asmodians = new FastList<Player>();
        FastList<Player> elyos = new FastList<Player>();
        if (players == null) {
            BGService.forceStopBG();
            return;
        }
        log.info("BG Making Groups");
        for (Player player : players) {
            if (player.isInGroup2()) {
                PlayerGroupService.removePlayer(player);
            }
            switch (player.getRace()) {
                case ASMODIANS:
                    asmodians.add(player);
                    addAsmo();
                    break;
                case ELYOS:
                    elyos.add(player);
                    addElys();
                    break;
                default:
                    break;
            }
        }
        log.info("BG Shuffling and Adjusting groups for asmo and elyos");
        Collections.shuffle(asmodians);
        Collections.shuffle(elyos);
        if (getAsmos() > getElyos()) {
            int total = getAsmos() - getElyos();
            for (int i = 0; i < total; i++) {
                Player asmos = asmodians.get(i);
                unregisterPlayer(asmos);
                asmodians.remove(asmos);
            }
        } else if (getElyos() > getAsmos()) {
            int total = getElyos() - getAsmos();
            for (int i = 0; i < total; i++) {
                Player elyosTmp = elyos.get(i);
                unregisterPlayer(elyosTmp);
                elyos.remove(elyosTmp);
            }
        }
        if (!ReadyToStart()) {
            log.info("BG Not enough players");
            sendFailMessage();
            BGService.forceStopBG();
            return;
        }
        clearAsmoAndElys();
        makeNewGroupAndAddMemberForAsmos(asmodians);
        makeNewGroupAndAddMemberForElyos(elyos);
        log.info("BG Putting all in groups");
        for (PlayerGroup asmoGroup : asmosGroupsReadyToGo) {
            groups.add(asmoGroup);
        }
        asmosGroupsReadyToGo.clear();

        for (PlayerGroup elyosGroup : elyosGroupsReadyToGo) {
            groups.add(elyosGroup);
        }
        elyosGroupsReadyToGo.clear();
        asmodians.clear();
        elyos.clear();
        for (Player player : players) {
            if (!player.isInGroup2() && !player.isGM()) {
                PacketSendUtility.sendMessage(player, "You've successfully unregistered!");
                unregisterPlayer(player);
            }
        }
        log.info("BG Begin Porting all groups");
        clearAsmoAndElys();
        BGService.portPlayer();
        TeamVsTeamOpenForReg = false;
    }


    public void portPlayer() {
        int e = 0;
        int a = 6;

        if (BGService.getGroups() != null) {
            for (PlayerGroup group : BGService.getGroups()) {
                InstanceService.registerGroupWithInstance(bgInstance, group);
            }
        } else {
            for (Player player : BGService.getPlayers()) {
                InstanceService.registerPlayerWithInstance(bgInstance, player);
            }
        }

        for (Player player : BGService.getPlayers()) {
            if (!player.isInGroup2()  || player == null) {
                continue;
            }

            if (player.getKisk() != null) {
                player.getKisk().removePlayer(player);
                player.setKisk(null);
            }
            log.info("BG Teleporting players");
            switch (player.getRace()) {
                case ELYOS:
                    TeleportService2.teleportTo(player, 300090000, bgInstance.getInstanceId(), 263.1206f ,123.97632f ,103.13934f, (byte) 0, TeleportAnimation.BEAM_ANIMATION);
                    gearUp(player);
                    e++;
                    if (e >= 5) {
                        e = 0;
                    }
                    break;
                case ASMODIANS:
                    TeleportService2.teleportTo(player, 300090000, bgInstance.getInstanceId(), 264.12393f, 320.99896f ,103.13952f, (byte) 0, TeleportAnimation.BEAM_ANIMATION);
                    gearUp(player);
                    a++;
                    if (a >= 11) {
                        a = 0;
                    }
                    break;
                default:
                    break;

            }
        }
        BGService.ClearPlayers();
    }

    private void makeNewGroupAndAddMemberForAsmos(FastList<Player> asmodians) {
        if (asmodians.size() > 1) {
            if (asmodians.get(getAsmos() + 1) == null) {
                return;
            }
            PlayerGroup firstGroup = groups.get(0);
            member = firstGroup.getMember(getAsmos());
            asmosGroupsReadyToGo.add(makeOneGroup(asmodians.get(getAsmos()), asmodians.get(getAsmos() + 1)));
            PlayerGroup groupForAsmos = asmodians.get(0).getPlayerGroup2();

            for (Player player : asmodians) {
                if (player.getPlayerGroup2() == groupForAsmos) {
                    continue;
                }
                if (groupForAsmos.isFull()) {
                    makeNewGroupAndAddMemberForAsmos(asmodians);
                    return;
                }
                PlayerGroupService.addPlayerToGroup(groupForAsmos, player);
                addAsmo();
            }
        }
    }

    private void makeNewGroupAndAddMemberForElyos(FastList<Player> elyos) {
        if (elyos.size() > 1) {
            if (elyos.get(getElyos() + 1) == null) {
                return;
            }
            PlayerGroup firstGroup = groups.get(1);
            member = firstGroup.getMember(getElyos());
            elyosGroupsReadyToGo.add(makeOneGroup(elyos.get(getElyos()), elyos.get(getElyos() + 1)));
            PlayerGroup groupForElyos = elyos.get(0).getPlayerGroup2();

            for (Player player : elyos) {
                if (player.getPlayerGroup2() == groupForElyos) {
                    continue;
                }
                if (groupForElyos.isFull()) {
                    continue;
                }
                PlayerGroupService.addPlayerToGroup(groupForElyos, player);
                addElys();
            }
        }
    }

    private PlayerGroup makeOneGroup(Player inviter, Player invited) {
        member = invited.getPlayerGroup2().getMember(inviter.getObjectId());
        PlayerGroupService.createGroup(inviter, invited, TeamType.GROUP);
        inviter.setPlayerGroup2(new PlayerGroup(member, TeamType.GROUP));
        // inviter.setPlayerGroup(new
        // PlayerGroup(IDFactory.getInstance().nextId(),
        // inviter));
        // original
        inviter.getPlayerGroup2().addMember(member);
        PlayerGroupService.addGroupMemberToCache(inviter);
        PlayerGroupService.addGroupMemberToCache(invited);
        return inviter.getPlayerGroup2();
    }




    public void gearUp(Player player) {
        player.getController().cancelCurrentSkill();
        player.getEffectController().removeAbnormalEffectsByTargetSlot(SkillTargetSlot.DEBUFF);
        player.getEffectController().removeAbnormalEffectsByTargetSlot(SkillTargetSlot.BUFF);
        player.getCommonData().setDp(0);
    }

    protected WorldMapInstance createInstance(){
        WorldMapInstance instance = BGService.getNextBGInstance(300090000);
        return instance;
    }

    public boolean registerPlayer(Player player){
        if(TeamVsTeamOpenForReg){
            if(!players.contains(player)){
                log.info("BG " + player.getName() + " Registered to BG");
                sendTimePacket(player, 120);
                player.battlegroundWaiting = true;
                players.add(player);
                switch(player.getRace()){
                    case ASMODIANS:
                        asmoList.add(player);
                        log.info("BG Adding to ASMO List");
                        break;
                    case ELYOS:
                        elyosList.add(player);
                        log.info("BG Adding to ELYOS List");
                        break;
                    default:
                        break;
                }
                return true;
            }else{
                if(player.battlegroundWaiting){
                    PacketSendUtility.sendMessage(player, "You're Already Registered!");
                    return false;
                }
            }
            return false;
        }else{
            log.info("BG " + player.getName() + " Tried to register when BG is not started!");
            PacketSendUtility.sendMessage(player, "BattleGround Arena is not yet opened for Registration!!");
            return false;
        }
    }

    public void unregisterPlayer(Player player){
        if(players.contains(player)){
            sendTimePacket(player, 0);
            players.remove(player);
            switch(player.getRace()){
                case ASMODIANS:
                    asmoList.remove(player);
                    log.info("BG Unregistering from Event(Asmo Side)");
                    PacketSendUtility.sendMessage(player, "You've successfully unregistered!");
                    break;
                case ELYOS:
                    elyosList.remove(player);
                    log.info("BG Unregistering from Event(ELYOS Side)");
                    PacketSendUtility.sendMessage(player, "You've successfully unregistered!");
                    break;
                default:
                    break;
            }
            if(players.size() == 0){
                players.clear();
            }
        }else{
            PacketSendUtility.sendMessage(player, "You're Already unregistered!");
        }
    }

    public void ShowInfoOfAnyAvailableBG(Player player){
        if(TeamVsTeamOpenForReg){
            PacketSendUtility.sendMessage(player, "Team Vs Team Battleground is Open for Registration. Type .bg reg to register.");
        }else{
            PacketSendUtility.sendMessage(player, "Team Vs Team Battleground is Not yet Opened for Registration. Wait some time till it Opens.(Opens Every 30min)");
        }

    }

    public void announceAll(final String sender, final String msg){
        World.getInstance().doOnAllPlayers(new Visitor<Player>() {
            @Override
            public void visit(Player object) {
                PacketSendUtility.sendSys2Message(object, sender, msg);
            }
        });
    }

    public void announceAllRegged(final String sender, final String msg){
        World.getInstance().doOnAllPlayers(new Visitor<Player>() {
            @Override
            public void visit(Player object) {
                PacketSendUtility.sendSys2Message(object, sender, msg);
            }
        });
    }

    private int getAsmos(){
        return asmos;
    }

    private int getElyos(){
        return elyos;
    }

    public void addAsmo(){
        this.asmos++;
    }

    public void addElys(){
        this.elyos++;
    }

    public int getAsmoReg() {
        return asmoReg;
    }

    public int getElysReg() {
        return elyosReg;
    }

    private void addAsmoReg() {
        this.asmoReg++;
    }

    private void addElyosReg() {
        this.elyosReg++;
    }

    private void clearAsmoAndElys() {
        this.asmos = 0;
        this.elyos = 0;
    }

    public FastList<PlayerGroup> getGroups(){
        return groups;
    }

    public FastList<Player> getPlayers(){
        return players;
    }

    public void ClearPlayers(){
        players.clear();
    }

    public boolean ReadyToStart() {
        if (players.size() >= 2) {  //later make this part as Config >_<
            if (getAsmos() < 1 || getElyos() < 1) {
                return false;
            }
            return true;
        } else {
            return false;
        }
    }

    private WorldMapInstance getNextBGInstance(int worldId) {
        WorldMap map = World.getInstance().getWorldMap(worldId);

        if (!map.isInstanceType()) {
            throw new UnsupportedOperationException("Invalid call for next available instance  of " + worldId);
        }

        int nextInstanceId = map.getNextInstanceId();

        log.info("[Team V Team] Creating new Tvt instance: " + nextInstanceId);

        WorldMapInstance pvpArenaInstance = WorldMapInstanceFactory.createWorldMapInstance(map, nextInstanceId);
        // InstanceService.startInstanceChecker(pvpArenaInstance);
        map.addInstance(nextInstanceId, pvpArenaInstance);
        SpawnEngine.spawnInstance(worldId, pvpArenaInstance.getInstanceId(), (byte) 1);
        InstanceEngine.getInstance().onInstanceCreate(pvpArenaInstance);
        return pvpArenaInstance;
    }

    public void sendFailMessage(){
        for(Player player : players){
            announceAllRegged("InsaneGod", "Sorry Friend!, Not enough Players, Get More players Next Time!!");
        }
    }

    public int SelectWhichBGtoReg(){
        int i = Rnd.get(1, 5);
        switch(i){
            case 1:
                return 1;
            case 2:
                return 2;
            case 3:
                return 3;
            case 4:
                return 4;
            case 5:
                return 5;
            default:
                return 1;
        }
    }
}
