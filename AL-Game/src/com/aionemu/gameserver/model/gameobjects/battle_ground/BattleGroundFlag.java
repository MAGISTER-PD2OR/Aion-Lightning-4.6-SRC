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


package com.aionemu.gameserver.model.gameobjects.battle_ground;

import com.aionemu.gameserver.controllers.battle_ground.BattleGroundFlagController;
import com.aionemu.gameserver.model.Race;
import com.aionemu.gameserver.model.gameobjects.Npc;
import com.aionemu.gameserver.model.gameobjects.NpcObjectType;
import com.aionemu.gameserver.model.gameobjects.Summon;
import com.aionemu.gameserver.model.gameobjects.VisibleObject;
import com.aionemu.gameserver.model.gameobjects.player.Player;
import com.aionemu.gameserver.model.templates.npc.NpcTemplate;
import com.aionemu.gameserver.model.templates.spawns.SpawnTemplate;

/**
 * @author Eloann
 */
public class BattleGroundFlag extends Npc {

    public enum BattleGroundFlagState {

        ON_FIELD,
        AT_BASE
    }
    public BattleGroundFlagState state = BattleGroundFlagState.AT_BASE;
    private Player flagHolder = null;

    public BattleGroundFlag(int objId, BattleGroundFlagController controller, SpawnTemplate spawnTemplate, NpcTemplate objectTemplate) {
        super(objId, controller, spawnTemplate, objectTemplate, objectTemplate.getLevel());
    }

    @Override
    public BattleGroundFlagController getController() {
        return (BattleGroundFlagController) super.getController();
    }

    public BattleGroundFlag getOwner() {
        return (BattleGroundFlag) this;
    }

    public boolean isEnemy(VisibleObject visibleObject) {
        return false;
    }

    protected boolean isEnemyNpc(Npc visibleObject) {
        return false;
    }

    protected boolean isEnemyPlayer(Player visibleObject) {
        return false;
    }

    protected boolean isEnemySummon(Summon summon) {
        return false;
    }

    @Override
    public NpcObjectType getNpcObjectType() {
        return NpcObjectType.NORMAL;
    }
    private Race race;

    public Race getRace() {
        return race;
    }

    public void setRace(Race race) {
        this.race = race;
    }

    public Player getFlagHolder() {
        return flagHolder;
    }

    public void setFlagHolder(Player flagHolder) {
        this.flagHolder = flagHolder;
    }
}
