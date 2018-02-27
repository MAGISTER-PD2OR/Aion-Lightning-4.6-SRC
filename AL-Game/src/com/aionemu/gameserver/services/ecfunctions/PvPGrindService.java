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


package com.aionemu.gameserver.services.ecfunctions;

import java.util.Calendar;
import java.util.Iterator;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.aionemu.commons.database.dao.DAOManager;
import com.aionemu.commons.services.CronService;
import com.aionemu.gameserver.configs.main.EternityConfig;
import com.aionemu.gameserver.dao.ServerVariablesDAO;
import com.aionemu.gameserver.model.gameobjects.Npc;
import com.aionemu.gameserver.model.gameobjects.player.Player;
import com.aionemu.gameserver.spawnengine.SpawnEngine;
import com.aionemu.gameserver.utils.PacketSendUtility;
import com.aionemu.gameserver.world.World;

/**
 * @author Eloann
 */
public class PvPGrindService {

    private static final Logger log = LoggerFactory.getLogger(PvPGrindService.class);

    @SuppressWarnings("synthetic-access")
    private static class SingletonHolder {

        protected static final PvPGrindService instance = new PvPGrindService();
    }

    public void scheduleUpdate() {
        ServerVariablesDAO dao = DAOManager.getDAO(ServerVariablesDAO.class);
        int nextTime = dao.load("abyssRankUpdate");
        if (nextTime < System.currentTimeMillis() / 1000) {
            performUpdate();
        }

        log.info("Starting PvP Grind Checker: " + EternityConfig.PVP_GRIND_UP_RULE);
        CronService.getInstance().schedule(new Runnable() {
            @Override
            public void run() {
                performUpdate();
            }
        }, EternityConfig.PVP_GRIND_UP_RULE, true);
    }

    public void performUpdate() {
        Calendar calendar = Calendar.getInstance();
        if (EternityConfig.PVP_GRIND_ENABLE && calendar.get(Calendar.DAY_OF_WEEK) == Calendar.MONDAY || EternityConfig.PVP_GRIND_ENABLE && calendar.get(Calendar.DAY_OF_WEEK) == Calendar.WEDNESDAY || EternityConfig.PVP_GRIND_ENABLE && calendar.get(Calendar.DAY_OF_WEEK) == Calendar.FRIDAY) {
            spawnGrindPhase1();
            log.info("PvP Grind Mobs spawned");
            Iterator<Player> iter = World.getInstance().getPlayersIterator();
            while (iter.hasNext()) {
                PacketSendUtility.sendBrightYellowMessage(iter.next(), "The PvP grind mobs have been spotted!");
            }
        } else if (EternityConfig.PVP_GRIND_ENABLE && calendar.get(Calendar.DAY_OF_WEEK) == Calendar.TUESDAY || EternityConfig.PVP_GRIND_ENABLE && calendar.get(Calendar.DAY_OF_WEEK) == Calendar.THURSDAY) {
            spawnGrindPhase2();
            log.info("PvP Grind Mobs spawned");
            Iterator<Player> iter = World.getInstance().getPlayersIterator();
            while (iter.hasNext()) {
                PacketSendUtility.sendBrightYellowMessage(iter.next(), "The PvP grind mobs have been spotted!");
            }
        } else if (EternityConfig.PVP_GRIND_ENABLE && calendar.get(Calendar.DAY_OF_WEEK) == Calendar.SATURDAY) {
            spawnGrindPhase3();
            log.info("PvP Grind Mobs spawned");
            Iterator<Player> iter = World.getInstance().getPlayersIterator();
            while (iter.hasNext()) {
                PacketSendUtility.sendBrightYellowMessage(iter.next(), "The PvP grind mobs have been spotted!");
            }
        } else if (EternityConfig.PVP_GRIND_ENABLE && calendar.get(Calendar.DAY_OF_WEEK) == Calendar.SUNDAY) {
            spawnGrindPhase4();
            log.info("PvP Grind Mobs spawned");
            Iterator<Player> iter = World.getInstance().getPlayersIterator();
            while (iter.hasNext()) {
                PacketSendUtility.sendBrightYellowMessage(iter.next(), "The PvP grind mobs have been spotted!");
            }
        }
    }

    public void spawnGrindPhase1() {
        SpawnEngine.addNewSpawn(600040000, 218557, 886.3675f, 560.71173f, 1203.5981f, (byte) 10, 255);
        SpawnEngine.addNewSpawn(600040000, 218554, 873.6774f, 552.9351f, 1203.5981f, (byte) 10, 255);
        SpawnEngine.addNewSpawn(600040000, 218556, 876.8906f, 546.64105f, 1203.5981f, (byte) 99, 255);
        SpawnEngine.addNewSpawn(600040000, 218559, 887.8906f, 552.32166f, 1203.5981f, (byte) 10, 255);
    }

    public void spawnGrindPhase2() {
        SpawnEngine.addNewSpawn(600040000, 218557, 581.3665f, 957.95764f, 1203.5981f, (byte) 10, 255);
        SpawnEngine.addNewSpawn(600040000, 218554, 566.97577f, 943.5789f, 1203.5981f, (byte) 10, 255);
        SpawnEngine.addNewSpawn(600040000, 218556, 575.54016f, 943.5789f, 1203.5981f, (byte) 99, 255);
        SpawnEngine.addNewSpawn(600040000, 218559, 592.0601f, 957.95764f, 1203.5981f, (byte) 10, 255);
    }

    public void spawnGrindPhase3() {
        SpawnEngine.addNewSpawn(600040000, 218557, 590.69037f, 604.991f, 1203.5981f, (byte) 10, 255);
        SpawnEngine.addNewSpawn(600040000, 218554, 597.50134f, 604.991f, 1203.5981f, (byte) 10, 255);
        SpawnEngine.addNewSpawn(600040000, 218556, 587.25055f, 604.991f, 1203.5981f, (byte) 99, 255);
        SpawnEngine.addNewSpawn(600040000, 218559, 579.4435f, 598.25684f, 1203.5981f, (byte) 10, 255);
    }

    public void spawnGrindPhase4() {
        SpawnEngine.addNewSpawn(600040000, 218557, 581.3665f, 957.95764f, 1203.5981f, (byte) 10, 255);
        SpawnEngine.addNewSpawn(600040000, 218554, 566.97577f, 943.5789f, 1203.5981f, (byte) 10, 255);
        SpawnEngine.addNewSpawn(600040000, 218556, 575.54016f, 943.5784f, 1203.5981f, (byte) 99, 255);
        SpawnEngine.addNewSpawn(600040000, 218559, 592.0601f, 957.95764f, 1203.5981f, (byte) 10, 255);
    }

    protected void despawnNpc(Npc npc) {
        if (npc != null) {
            npc.getController().onDelete();
        }
    }

    protected void despawnNpcs(List<Npc> npcs) {
        for (Npc npc : npcs) {
            npc.getController().onDelete();
        }
    }

    public static final PvPGrindService getInstance() {
        return SingletonHolder.instance;
    }
}
