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

package com.aionemu.gameserver.dao;

import com.aionemu.commons.database.dao.DAO;
import com.aionemu.gameserver.model.base.BaseLocation;

import java.util.Map;

/**
 * @author Himiko
 */  
  public abstract class BaseDAO implements DAO {
  
  @Override   
  public final String getClassName() {   
      return BaseDAO.class.getName();
  }

  public abstract void LoadBases(Map<Integer, BaseLocation> paramMap);
  
  public abstract void updateBase(BaseLocation paramBaseLocation);
  
  public abstract boolean insertBase(BaseLocation paramBaseLocation);
}
   