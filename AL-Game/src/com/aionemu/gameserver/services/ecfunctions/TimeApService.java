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

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.aionemu.commons.database.dao.DAOManager;
import com.aionemu.commons.services.CronService;
import com.aionemu.gameserver.configs.main.EternityConfig;
import com.aionemu.gameserver.dao.PlayerDAO;
import com.aionemu.gameserver.model.gameobjects.player.Player;
import com.aionemu.gameserver.services.abyss.AbyssPointsService;
import com.aionemu.gameserver.utils.PacketSendUtility;
import com.aionemu.gameserver.world.World;
import java.util.Random;

/**
 * @author Alex
 * @rework Eloann
 */
public class TimeApService {

    private static final Logger log = LoggerFactory.getLogger(TimeApService.class);
    private Random rand = new Random();
    public int time = rand.nextInt(23) + 12;

    public static TimeApService getInstance() {
        return TimeApService.SingletonHolder.instance;
    }

    public void start() {
        String cron = "0 0 0," + time + " ? * *";
        CronService.getInstance().schedule(new Runnable() {
            @Override
            public void run() {
                for (Player pp : World.getInstance().getAllPlayers()) {
                    int count = EternityConfig.COUNT_TIME * DAOManager.getDAO(PlayerDAO.class).getOnlinePlayerCount();
                    AbyssPointsService.addAp(pp, count);
                    PacketSendUtility.sendYellowMessageOnCenter(pp, "Wow, you're lucky you got " + count + " Abyss Point :)");
                    PacketSendUtility.sendMessage(pp, "Administration of the server thank you for your fidelity and offer you " + count + " Abyss Points.\nEnjoy the game on our server!");
                }
            }
        }, cron);
        log.info("Scheduled Abyss All: based on cron expression: " + cron);
    }

    private static class SingletonHolder {

        protected static final TimeApService instance = new TimeApService();
    }
}
