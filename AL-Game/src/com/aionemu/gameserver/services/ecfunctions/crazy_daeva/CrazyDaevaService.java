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


package com.aionemu.gameserver.services.ecfunctions.crazy_daeva;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.aionemu.commons.services.CronService;
import com.aionemu.commons.utils.Rnd;
import com.aionemu.gameserver.configs.main.EternityConfig;
import com.aionemu.gameserver.configs.schedule.CrazySchedule;
import com.aionemu.gameserver.configs.schedule.CrazySchedule.Schedule;
import com.aionemu.gameserver.model.Race;
import com.aionemu.gameserver.model.TeleportAnimation;
import com.aionemu.gameserver.model.gameobjects.Creature;
import com.aionemu.gameserver.model.gameobjects.player.Player;
import com.aionemu.gameserver.network.aion.serverpackets.SM_ATTACK_STATUS.TYPE;
import com.aionemu.gameserver.services.PvpService;
import com.aionemu.gameserver.services.abyss.AbyssPointsService;
import com.aionemu.gameserver.services.teleport.TeleportService2;
import com.aionemu.gameserver.utils.PacketSendUtility;
import com.aionemu.gameserver.utils.ThreadPoolManager;
import com.aionemu.gameserver.world.World;
import com.aionemu.gameserver.world.knownlist.Visitor;

/**
 * @author Cloudious
 */
public class CrazyDaevaService {

    private static final Logger log = LoggerFactory.getLogger("CRAZY_DAEVA_LOG");
    private CrazySchedule crazySchedule;
    int crazyCount = 0;

    //calculate time
    public void startTimer() {
        crazySchedule = CrazySchedule.load();

        for (Schedule schedule : crazySchedule.getScheduleList()) {
            for (String crazyTime : schedule.getScheduleTimes()) {
                CronService.getInstance().schedule(new CrazyDaevaStartRunnable(schedule.getId()), crazyTime);
                log.info("[CRAZY] Scheduled Crazy Daeva " + schedule.getId() + " based on cron expression: " + crazyTime);
            }
        }
    }

    //check time start
    public void checkStart() {
        startChoose();
        clearCrazy();
        log.info("[CRAZY] Crazy Daeva start choose random player and calculate end time.");
    }

    //start choose rnd
    public void startChoose() {
        PacketSendUtility.event("CRAZY Event is about to get Started... Choosing the CRAZY ONE!!");
        World.getInstance().doOnAllPlayers(new Visitor<Player>() {
            @Override
            public void visit(final Player player) {

                int rnd = Rnd.get(1, 100);
                player.setRndCrazy(rnd);
                if (player.getRndCrazy() >= 50 && player.getLevel() >= 65) {
                    crazyCount++;
                    if (crazyCount == 1) {
                        TeleportService2.teleportTo(player, player.getWorldId(), player.getInstanceId(), player.getX(), player.getY(), player.getZ(), player.getHeading(), TeleportAnimation.BEAM_ANIMATION);
                        PacketSendUtility.event("A target has been choose ! It's " + player.getName() + " ! You have 30 min to find him, and kill him (you can kill him as many times as desired during this time). HAVE FUN :)");
                        log.info("[CRAZY] System choose " + player.getName() + ".");
                        player.setInCrazy(true);
                        PvpService.getInstance().doReward(player);
                    }
                }
                log.info("[CRAZY] Player " + player.getName() + " got random " + rnd + "");
            }
        });
    }

    //increase kill count
    public void increaseRawKillCount(Player winner) {
        int currentCrazyKillCount = winner.getCrazyKillCount();
        winner.setCrazyKillCount(currentCrazyKillCount + 1);
        int newCrazyKillCount = currentCrazyKillCount + 1;

        if (newCrazyKillCount >= 0 && newCrazyKillCount <= 10) {
            if(newCrazyKillCount >= 5 && newCrazyKillCount <=10){
                PacketSendUtility.event("[CRAZY Player] " + winner.getName() + " has "+ newCrazyKillCount + " kills and he is about to change to Crazy Level 2 !!");

            } else if (newCrazyKillCount == 2){
                PacketSendUtility.event("[CRAZY Player] " + winner.getName() + " has just took "+ newCrazyKillCount + " kills and that [color:CRAZ;1 0 0][color:Y;1 0 0] Daeva is Gaining !!");
            } else if (newCrazyKillCount == 3){
                PacketSendUtility.event("[CRAZY Player] TRIIPPPLEE KILL !! " + winner.getName() + " took "+ newCrazyKillCount + " kills and that [color:CRAZ;1 0 0][color:Y;1 0 0] is STILL GOING ON HIGHER !!");
            } else if (newCrazyKillCount == 4){
                PacketSendUtility.event("[CRAZY Player] " + winner.getName() + " has just took "+ newCrazyKillCount + " kills and that [color:CRAZ;1 0 0][color:Y;1 0 0] Daeva is Gaining !!");

            } else if (newCrazyKillCount == 9){
                PacketSendUtility.event("[CRAZY Player] " + winner.getName() + " needs to take only ONE kill to get to CRAZY Level 2, " + winner.getName() + " has " + newCrazyKillCount + " !");
            } else if (newCrazyKillCount == 1){
                PacketSendUtility.event("[CRAZY Player] " + winner.getName() + " has just took "+ newCrazyKillCount + " kills and he is now on Crazy Level 1 !!");
            }

            PacketSendUtility.event("[CRAZY Player] Kill "+winner.getName()+" and get [color:300;1 1 0] [color:GP;1 1 0] as Bounty !!!");
            updateCrazyLevel(winner, 1);
        }
        if (newCrazyKillCount >= 10 && newCrazyKillCount <= 20) {
            if(newCrazyKillCount >= 15 && newCrazyKillCount <=20){
                PacketSendUtility.event("[CRAZY Player] " + winner.getName() + " has "+ newCrazyKillCount + " kills and he is about to change to Crazy Level 3 !!");

            } else if (newCrazyKillCount == 12){
                PacketSendUtility.event("[CRAZY Player] ULLLTRRRAAA KILLL !! "+winner.getName() + " just took "+newCrazyKillCount+" kills !!");
            } else if (newCrazyKillCount == 13){
                PacketSendUtility.event("[CRAZY Player] MEGGGAAA KILLL !! "+winner.getName() + " just took "+newCrazyKillCount+" kills !!");
            } else if (newCrazyKillCount == 14){
                PacketSendUtility.event("[CRAZY Player] CHUU CHUU MOTAFAKAAA !! "+winner.getName() + " just took "+newCrazyKillCount+" kills !!");

            } else if (newCrazyKillCount == 19){
                PacketSendUtility.event("[CRAZY Player] " + winner.getName() + " needs to take only ONE kill to get to CRAZY Level 3, " + winner.getName() + " has " + newCrazyKillCount + " !");
            } else if (newCrazyKillCount == 11){
                PacketSendUtility.event("[CRAZY Player] " + winner.getName() + " has just took "+ newCrazyKillCount + " kills and he is now on Crazy Level 2 !!");
            }

            PacketSendUtility.event("[CRAZY Player] Kill "+winner.getName()+" and get [color:500;0 1 0] [color:GP;0 1 0] as Bounty before its too LATE !!!");
            updateCrazyLevel(winner, 2);
        }
        if (newCrazyKillCount >= 20 && newCrazyKillCount <= 30) {
            if(newCrazyKillCount >= 25 && newCrazyKillCount <=30){
                PacketSendUtility.event("[CRAZY Player] " + winner.getName() + " has "+ newCrazyKillCount + " kills and he is about become the CRAZIEST DAEVA EVER !!");

            } else if (newCrazyKillCount == 22){
                PacketSendUtility.event("[CRAZY Player] MMMMMMONSTER KILLLLLAA !! "+winner.getName() + " just took "+newCrazyKillCount+" kills !!");
            } else if (newCrazyKillCount == 23){
                PacketSendUtility.event("[CRAZY Player] INSANE KILLLERRRR !! "+winner.getName() + " just took "+newCrazyKillCount+" kills !!");
            } else if (newCrazyKillCount == 24){
                PacketSendUtility.event("[CRAZY Player] WIICKKEEDD SIICCKKK !!!! "+winner.getName() + " just took "+newCrazyKillCount+" kills !!");

            } else if (newCrazyKillCount == 29){
                PacketSendUtility.event("[CRAZY Player] " + winner.getName() + " needs to take only ONE kill to get to CRAZY Level 4, " + winner.getName() + " has " + newCrazyKillCount + " !");
            } else if (newCrazyKillCount == 21){
                PacketSendUtility.event("[CRAZY Player] " + winner.getName() + " has just took "+ newCrazyKillCount + " kills and he is now on Crazy Level 3 !!");
            }
            PacketSendUtility.event("[CRAZY Player] Kill "+winner.getName()+" and get [color:1500;0 1 0] [color:GP;0 1 0] as Bounty before its too LATE !!!");
            updateCrazyLevel(winner, 3);
        }
        if (newCrazyKillCount > 31){
            PacketSendUtility.event("[CRAZY] >>"+winner.getName()+"<< [color:is;0 1 0] [color:on;0 1 0] [color:WANT;0 1 0][color:ED;0 1 0] !!");
            PacketSendUtility.event("[CRAZY] >>"+winner.getName()+"<< [color:has;0 1 0] [color:a;0 1 0] [color:boun;0 1 0][color:ty;0 1 0] [color:of;0 1 0] [color:2500;0 1 0] !!");
            updateCrazyLevel(winner, 4);
        }
        log.info("[CRAZY] Killed " + newCrazyKillCount + " player.");

    }

    //update kill level
    private void updateCrazyLevel(Player winner, int level) {
        winner.setCrazyLevel(level);
    }

    //when die
    public void crazyOnDie(Player victim, Creature killer, boolean isPvPDeath) {
        if (victim.isInCrazy()) {
            victim.setCrazyLevel(0);
            victim.setCrazyKillCount(0);
            sendEndSpreeMessage(victim, killer, isPvPDeath);
        }
    }

    //killer reward + announce
    private void sendEndSpreeMessage(Player victim, Creature killer, boolean isPvPDeath) {
        if (killer instanceof Player) {
            if (killer.getRace() == Race.ELYOS && victim.getRace() == Race.ASMODIANS) {
                String spreeEnder = isPvPDeath ? ((Player) killer).getName() : "Killer";
                PacketSendUtility.event("[CRAZY Victim] " + victim.getName() + " was killed by " + spreeEnder + "!");
                PacketSendUtility.event("[CRAZY Victim] " + victim.getName() + " has died with Crazy Level : "+ victim.getCrazyLevel() +" !");

                if(victim.getCrazyLevel() == 1){
                    AbyssPointsService.addAp((Player) killer, 20000);
                    AbyssPointsService.addGp((Player) killer, 300);
                }
                if (victim.getCrazyLevel() == 2){
                    AbyssPointsService.addAp((Player) killer, 40000);
                    AbyssPointsService.addGp((Player) killer, 500);
                }
                if(victim.getCrazyLevel() == 3){
                    AbyssPointsService.addAp((Player) killer, 50000);
                    AbyssPointsService.addGp((Player) killer, 1500);
                }
                if (victim.getCrazyLevel() == 4){
                    AbyssPointsService.addAp((Player) killer, 100000);
                    AbyssPointsService.addGp((Player) killer, 2500);
                }
                log.info("[CRAZY] Crazier " + victim.getName() + " was killed by " + spreeEnder + "");
            }
        }
    }

    //end event clear all and reward
    public void clearCrazy() {
        ThreadPoolManager.getInstance().schedule(new Runnable() {
            @Override
            public void run() {
                World.getInstance().doOnAllPlayers(new Visitor<Player>() {
                    @Override
                    public void visit(final Player player) {
                        if (player.isInCrazy()) {
                            TeleportService2.teleportTo(player, player.getWorldId(), player.getInstanceId(), player.getX(), player.getY(), player.getZ(), player.getHeading(), TeleportAnimation.BEAM_ANIMATION);
                            if (player.getCrazyLevel() == 1) {
                                AbyssPointsService.addAp(player, 1000);
                                AbyssPointsService.addGp(player, 100);
                                log.info("[CRAZY] Target " + player.getName() + " killed " + player.getCrazyKillCount() + " and get reward 1000 AP.");
                                PacketSendUtility.event("[CRAZY] "+player.getName()+" killed "+player.getCrazyKillCount() + " and got 1000 ap and 100 gp as Reward!");
                            }
                            if (player.getCrazyLevel() == 2) {
                                AbyssPointsService.addAp(player, 5000);
                                AbyssPointsService.addGp(player, 250);
                                log.info("[CRAZY] Target " + player.getName() + " killed " + player.getCrazyKillCount() + " and get reward 5000 AP.");
                                PacketSendUtility.event("[CRAZY] "+player.getName()+" killed "+player.getCrazyKillCount() + " and got 5000 ap and 250 gp as Reward!");
                            }
                            if (player.getCrazyLevel() == 3) {
                                AbyssPointsService.addAp(player, 10000);
                                AbyssPointsService.addGp(player, 500);
                                log.info("[CRAZY] Target " + player.getName() + " killed " + player.getCrazyKillCount() + " and get reward 10000 AP.");
                                PacketSendUtility.event("[CRAZY] "+player.getName()+" killed "+player.getCrazyKillCount() + " and got 10000 ap and 500 gp as Reward!");
                            }
                            if (player.getCrazyLevel() == 4){
                                AbyssPointsService.addAp(player, 20000);
                                AbyssPointsService.addGp(player, 1000);
                                log.info("[CRAZY] Target " + player.getName() + " killed " + player.getCrazyKillCount() + " and get reward 20000 AP.");
                                PacketSendUtility.event("[CRAZY] "+player.getName()+" killed "+player.getCrazyKillCount() + " and got 10000 ap and 1000 gp as Reward!");
                            }
                            player.setCrazyKillCount(0);
                            player.setCrazyLevel(0);
                            player.setInCrazy(false);
                            player.setRndCrazy(0);
                        }
                        player.setInCrazy(false);
                        player.setRndCrazy(0);
                        player.getLifeStats().increaseHp(TYPE.HP, player.getLifeStats().getMaxHp() + 5000);
                    }
                });
                PacketSendUtility.event("All On A Daeva Event finished!");
                log.info("[CRAZY] All On A Daeva cleared.");
            }
        }, EternityConfig.CRAZY_ENDTIME); //time stop
    }

    public static final CrazyDaevaService getInstance() {
        return SingletonHolder.instance;
    }



    @SuppressWarnings("synthetic-access")
    private static class SingletonHolder {

        protected static final CrazyDaevaService instance = new CrazyDaevaService();
    }
}
