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

import java.util.Collection;
import java.util.Iterator;

import com.aionemu.gameserver.configs.administration.AdminConfig;
import com.aionemu.gameserver.model.gameobjects.player.Player;
import com.aionemu.gameserver.network.aion.AionConnection;
import com.aionemu.gameserver.network.aion.serverpackets.SM_QUESTION_WINDOW;
import com.aionemu.gameserver.network.aion.serverpackets.SM_QUIT_RESPONSE;
import com.aionemu.gameserver.utils.PacketSendUtility;
import com.aionemu.gameserver.utils.ThreadPoolManager;
import com.aionemu.gameserver.world.World;

/**
 * @author Alex
 * @rework Eloann
 */
public class GuardService {

    public static GuardService getInstance() {
        return SingletonHolder.instance;
    }

    public void guard(final Player player, AionConnection client) {
        Collection<Player> asd = World.getInstance().getAllPlayers();
        for (Iterator<Player> it = asd.iterator(); it.hasNext();) {
            final Player players = it.next();

            if (players.getClientConnection().getMacAddress() == null ? client.getMacAddress() == null : players.getClientConnection().getMacAddress().equals(client.getMacAddress())) {
                if (players == player) {
                    return;
                } else if (players != player) {
                    if (player.isGM() || players.isGM()) {
                        return;
                    }

                    ThreadPoolManager.getInstance().schedule(new Runnable() {
                        @Override
                        public void run() {
                            if (AdminConfig.GUARD_WINDOW_TRUE) {
                                String message = "You can not go into the game with more than one window!";
                                PacketSendUtility.sendPacket(player, new SM_QUESTION_WINDOW(902247, player.getObjectId(), 0, message, 0));
                                player.getClientConnection().close(new SM_QUIT_RESPONSE(), false);
                                players.getClientConnection().close(new SM_QUIT_RESPONSE(), false);
                            } else {
                                player.getClientConnection().close(new SM_QUIT_RESPONSE(), false);
                                players.getClientConnection().close(new SM_QUIT_RESPONSE(), false);
                            }
                        }
                    }, AdminConfig.TIME_KICK);
                }
            }
        }
    }

    @SuppressWarnings("synthetic-access")
    private static class SingletonHolder {

        protected static final GuardService instance = new GuardService();
    }
}
