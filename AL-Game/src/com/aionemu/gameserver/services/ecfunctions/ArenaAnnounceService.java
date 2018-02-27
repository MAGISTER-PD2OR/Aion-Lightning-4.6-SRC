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

import java.util.Iterator;
import org.joda.time.DateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.aionemu.commons.database.dao.DAOManager;
import com.aionemu.commons.services.CronService;
import com.aionemu.gameserver.configs.main.EternityConfig;
import com.aionemu.gameserver.dao.ServerVariablesDAO;
import com.aionemu.gameserver.model.gameobjects.player.Player;
import com.aionemu.gameserver.utils.PacketSendUtility;
import com.aionemu.gameserver.world.World;

public class ArenaAnnounceService {

    /**
     * Just a logger
     */
    private static final Logger log = LoggerFactory.getLogger(ArenaAnnounceService.class);

    /**
     * SingletonHolder
     */
    @SuppressWarnings("synthetic-access")
    private static class SingletonHolder {

        protected static final ArenaAnnounceService instance = new ArenaAnnounceService();
    }

    /**
     * Update rule for spawn
     */
    public void scheduleUpdate() {
        ServerVariablesDAO dao = DAOManager.getDAO(ServerVariablesDAO.class);
        int nextTime = dao.load("abyssRankUpdate");
        if (nextTime < System.currentTimeMillis() / 1000) {
            performUpdate();
        }

        log.info("Starting Arena Announce : " + EternityConfig.ARENA_ANNOUNCE_UPDATE_RULE);
        CronService.getInstance().schedule(new Runnable() {
            @Override
            public void run() {
                performUpdate();
            }
        }, EternityConfig.ARENA_ANNOUNCE_UPDATE_RULE, true);
    }

    /**
     * Get Service
     */
    public static final ArenaAnnounceService getInstance() {
        return ArenaAnnounceService.SingletonHolder.instance;
    }

    public void performUpdate() {
        Iterator<Player> iter = World.getInstance().getPlayersIterator();
        DateTime now = DateTime.now();
        int hour = now.getHourOfDay();
        int day = now.getDayOfWeek();
        if ((day == 6) || (day == 7)) {
            if (((hour == 10) || (hour == 2))) {
                while (iter.hasNext()) {
                    PacketSendUtility.sendMessage(iter.next(), "PvP Arena are now open.");
                }
            } else if ((hour == 0) || (hour == 8) || (hour == 12) || (hour == 16) || (hour == 20)) {
                while (iter.hasNext()) {
                    PacketSendUtility.sendMessage(iter.next(), "PvP Arena are now closed.");
                }
            }
        }
    }
}
