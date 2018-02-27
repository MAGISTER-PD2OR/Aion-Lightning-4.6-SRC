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
package com.aionemu.gameserver.model.stats.calc.functions;

import com.aionemu.gameserver.model.gameobjects.player.Player;
import com.aionemu.gameserver.model.stats.container.PlayerGameStats;
import com.aionemu.gameserver.model.stats.container.StatEnum;
import java.util.ArrayList;
import java.util.List;

public class PlayerStatFunctions {

  private static final List<IStatFunction> FUNCTIONS = new ArrayList();

  static {
    FUNCTIONS.add(new PhysicalAttackFunction());
    FUNCTIONS.add(new MagicalAttackFunction());
    FUNCTIONS.add(new AttackSpeedFunction());
    FUNCTIONS.add(new BoostCastingTimeFunction());
    FUNCTIONS.add(new PvPAttackRatioFunction());
    FUNCTIONS.add(new PDefFunction());
    FUNCTIONS.add(new MaxHpFunction());
    FUNCTIONS.add(new MaxMpFunction());
    
    FUNCTIONS.add(new AgilityModifierFunction(StatEnum.BLOCK, 0.25F));
    FUNCTIONS.add(new AgilityModifierFunction(StatEnum.PARRY, 0.25F));
    FUNCTIONS.add(new AgilityModifierFunction(StatEnum.EVASION, 0.3F));
  }
  
  public static final List<IStatFunction> getFunctions() {
    return FUNCTIONS;
  }
  
  public static final void addPredefinedStatFunctions(Player player) {
    player.getGameStats().addEffectOnly(null, FUNCTIONS);
  }
}
