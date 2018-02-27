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

package com.aionemu.gameserver.services.item;

import com.aionemu.commons.utils.Rnd;
import com.aionemu.gameserver.model.gameobjects.Item;
import com.aionemu.gameserver.model.gameobjects.player.Player;
import com.aionemu.gameserver.model.items.storage.Storage;
import com.aionemu.gameserver.model.templates.item.ItemTemplate;

/**
 * @author Himiko
 */
public class ItemCompostieService {

  public static void Compostie(Player player, int itemobj, int targetItem1, int targetItem2) {
  
    Item aItem = player.getInventory().getItemByObjId(targetItem1);
    Item bItem = player.getInventory().getItemByObjId(targetItem2);
    if ((aItem == null) || (bItem == null)) {
      return;
    }
    if ((aItem.getItemTemplate().getLevel() >= 90) || (bItem.getItemTemplate().getLevel() >= 90)) {
      return;
    }
    int maxItemId = Math.max(aItem.getItemId(), bItem.getItemId());
    int minItemId = Math.min(aItem.getItemId(), bItem.getItemId());
    int rndnum = 0;
    if (maxItemId - minItemId < 10) {
        rndnum = 10;
    } else {
      rndnum = maxItemId - minItemId;
    }
    int newItemId = minItemId + Rnd.get(1, rndnum);
    boolean deleteItem = player.getInventory().decreaseByObjectId(itemobj, 1L);
    boolean deletetarget1 = player.getInventory().decreaseByObjectId(targetItem1, 1L);
    boolean deletetarget2 = player.getInventory().decreaseByObjectId(targetItem2, 1L);
    if ((deleteItem) && (deletetarget1) && (deletetarget2)) {
      ItemService.addItem(player, newItemId, 1L);
    }
  }
}