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


package com.aionemu.gameserver.model.instance.instancereward;

import com.aionemu.gameserver.model.Race;
import com.aionemu.gameserver.model.gameobjects.Npc;
import com.aionemu.gameserver.model.gameobjects.player.Player;
import com.aionemu.gameserver.model.instance.playerreward.InstancePlayerReward;

public class TvtReward extends InstanceReward<InstancePlayerReward> {

    private int asmosPoints;
    private int elyosPoints;

    public TvtReward(Integer mapId, int instanceId) {
        super(mapId, instanceId);
    }

    public int getPoints(Race race) {
        switch (race) {
            case ASMODIANS:
                return asmosPoints;
            case ELYOS:
                return elyosPoints;
            default:
                return 0;
        }
    }

    public void addScore(Player player) {
        switch (player.getRace()) {
            case ASMODIANS:
                if (player.getClientConnection().getAccount().getMembership() > 0) {
                    asmosPoints++;
                }
                asmosPoints++;
                break;
            case ELYOS:
                if (player.getClientConnection().getAccount().getMembership() > 0) {
                    elyosPoints++;
                }
                elyosPoints++;
                break;
		default:
			break;
        }
    }

    public void addScore(Npc npc) {
        switch (npc.getObjectTemplate().getRace()) {
            case ASMODIANS:
                asmosPoints++;
                break;
            case ELYOS:
                elyosPoints++;
                break;
		default:
			break;
        }
    }
}
