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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.aionemu.gameserver.configs.main.EternityConfig;
import com.aionemu.gameserver.model.Race;
import com.aionemu.gameserver.model.gameobjects.Creature;
import com.aionemu.gameserver.model.gameobjects.VisibleObject;
import com.aionemu.gameserver.model.gameobjects.player.Player;
import com.aionemu.gameserver.model.siege.Influence;
import com.aionemu.gameserver.model.templates.spawns.SpawnTemplate;
import com.aionemu.gameserver.spawnengine.SpawnEngine;
import com.aionemu.gameserver.utils.PacketSendUtility;
import com.aionemu.gameserver.utils.i18n.CustomMessageId;
import com.aionemu.gameserver.utils.i18n.LanguageHandler;
import com.aionemu.gameserver.world.World;
import com.aionemu.gameserver.world.knownlist.Visitor;

/**
 * @author Eloann
 */
public class CapitalInvadeService {

    private static Logger log = LoggerFactory.getLogger(CapitalInvadeService.class);
    private Map<Integer, List<Integer>> riftObjectId = new HashMap<Integer, List<Integer>>();
    private int worldId = 0;
    private static CapitalInvadeService instance = new CapitalInvadeService();

    private CapitalInvadeService() {
        if (EternityConfig.INVASION_RIFT_ENABLE) {
            invasionRift();
            log.info("CapitalInvadeService started... OK.");
        }
    }

    @SuppressWarnings({"rawtypes", "unchecked"})
    private void spawnRift(Race race) {
        SpawnTemplate st;
        if (race == Race.ELYOS) {
            st = SpawnEngine.addNewSpawn(210050000, 296534, 1431.1177F, 397.7012F, 552.29791F, (byte) 16, 0);
        } else {
            st = SpawnEngine.addNewSpawn(220070000, 296534, 1867.6262F, 2748.5344F, 531.97144F, (byte) 39, 0);
        }
        if (st == null) {
            return;
        }
        worldId = (race == Race.ELYOS ? 210050000 : 220070000);
        if (riftObjectId.containsKey(Integer.valueOf(worldId))) {
            riftObjectId.remove(Integer.valueOf(worldId));
        }
        riftObjectId.put(Integer.valueOf(worldId), new ArrayList());
        VisibleObject visibleObject = SpawnEngine.spawnObject(st, 1);
        if ((visibleObject != null) && (!((List) riftObjectId.get(Integer.valueOf(worldId))).contains(visibleObject.getObjectId()))) {
            ((List) riftObjectId.get(Integer.valueOf(worldId))).add(visibleObject.getObjectId());
        }
    }

    @SuppressWarnings("rawtypes")
    public void removeSpawn(int ObjectId) {
        List list = (List) riftObjectId.get(Integer.valueOf(ObjectId));
        if (list != null) {
            Iterator iterator = list.iterator();
            while (iterator.hasNext()) {
                Integer integer = (Integer) iterator.next();
                VisibleObject visibleObject = World.getInstance().findVisibleObject(integer.intValue());
                if ((visibleObject != null) && ((visibleObject instanceof Creature))) {
                    Creature creature = (Creature) visibleObject;
                    if (creature.isSpawned()) {
                        World.getInstance().despawn(creature);
                    }
                    creature.getController().delete();
                }
            }
        }
        if (!riftObjectId.isEmpty()) {
            ((List) riftObjectId.get(Integer.valueOf(ObjectId))).clear();
        }
    }

    private Race influenceCheck() {
        if (Math.round(100.0F * Influence.getInstance().getGlobalElyosInfluence()) >= EternityConfig.MAX_INFLUENCE_ELYOS) { //ELYOS INFLUENCE
            return Race.ASMODIANS;
        }
        if (Math.round(100.0F * Influence.getInstance().getGlobalAsmodiansInfluence()) >= EternityConfig.MAX_INFLUENCE_ASMO) { //ASMO INFLUENCE
            return Race.ELYOS;
        }
        return Race.NONE;
    }

    public final void invasionRift() {
        if (influenceCheck() == Race.NONE) {
            removeSpawn(worldId);
            return;
        }
        if (worldId != 0) {
            removeSpawn(this.worldId);
        }
        spawnRift(influenceCheck());
        sendMsg(influenceCheck());
        log.info("[Capital Invade] Rift spawn for [" + influenceCheck() + "]");
    }

    private void sendMsg(final Race race) {

        World.getInstance().doOnAllPlayers(new Visitor<Player>() {
            @Override
            public void visit(Player p) {
                if (race == Race.ELYOS) {
                    PacketSendUtility.sendMessage(p, LanguageHandler.translate(CustomMessageId.INVASION_RIFT_ELYOS));
                } else {
                    PacketSendUtility.sendMessage(p, LanguageHandler.translate(CustomMessageId.INVASION_RIFT_ASMOS));
                }
            }
        });
    }

    public static final CapitalInvadeService getInstance() {
        return instance;
    }
}
