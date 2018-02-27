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
 
package com.aionemu.gameserver.model.base;

import com.aionemu.gameserver.controllers.NpcController;
import com.aionemu.gameserver.model.gameobjects.Creature;
import com.aionemu.gameserver.model.gameobjects.Npc;
import com.aionemu.gameserver.model.templates.npc.NpcTemplate;
import com.aionemu.gameserver.model.templates.spawns.basespawns.BaseSpawnTemplate;

/**
 * @author Himiko
 */
public class BaseNpc
  extends Npc
{
  private int baseId;
  private BaseRace race;
  
  public BaseNpc(int objId, NpcController controller, BaseSpawnTemplate spawnTemplate, NpcTemplate objectTemplate) {
    super(objId, controller, spawnTemplate, objectTemplate, objectTemplate.getLevel());
  }
  
  public int getBaseId() {
    return 0;
  }
  
  public BaseRace getBaseRace() {
    return null;
  }
  
  public BaseSpawnTemplate getSpawn() {
    return null;
  }
  
  public boolean isEnemy(Creature creature) {
    return creature.isEnemyFrom(this);
  }
}
