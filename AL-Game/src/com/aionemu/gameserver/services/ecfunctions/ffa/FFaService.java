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

package com.aionemu.gameserver.services.ecfunctions.ffa;

import com.aionemu.gameserver.configs.main.EventSystem;
import com.aionemu.gameserver.model.Race;
import com.aionemu.gameserver.model.gameobjects.Item;
import com.aionemu.gameserver.model.gameobjects.player.Player;
import com.aionemu.gameserver.model.templates.item.ArmorType;
import com.aionemu.gameserver.utils.PacketSendUtility;
import com.aionemu.gameserver.utils.ThreadPoolManager;
import com.aionemu.gameserver.world.World;
import com.aionemu.gameserver.world.WorldMapInstance;
import com.aionemu.gameserver.world.knownlist.Visitor;

import java.util.*;
import java.util.concurrent.ScheduledFuture;

/**
 * @author Maestross
 * @rework Eloann
 */
public class FFaService implements FFaStruct {

	WorldMapInstance instance = null;
	private static final FFaService service = new FFaService();
	String playerName = "";

	public static FFaService getInstance() {
		return service;
	}

	@Override
	public ScheduledFuture<?> announceTask(int delayInMinutes) {
		return ThreadPoolManager.getInstance().scheduleAtFixedRate(new Runnable() {
			@Override
			public void run() {
				if (getPlayerCount() != 0) {
					World.getInstance().doOnAllPlayers(new Visitor<Player>() {
						@Override
						public void visit(Player object) {
							PacketSendUtility.sendWhiteMessageOnCenter(object, "Free For All Arena is Available !! Get You're Gears Ready !! '.ffa enter' ! \n"
									+ getPlayerCount() + " players have already joined the map.");
						}
					});
				} else {
					World.getInstance().doOnAllPlayers(new Visitor<Player>() {
						@Override
						public void visit(Player object) {
							PacketSendUtility.sendWhiteMessageOnCenter(object, "Free For All Battle is Open, join it using command '.ffa enter' !");
						}
					});
				}
			}
		}, delayInMinutes / 2 * 1000 * 60, delayInMinutes / 2 * 1000 * 60);
	}

	@Override
	public int getPlayerCount() {
        Iterator<Player> ita = World.getInstance().getPlayersIterator();
        ArrayList<Player> playerArrayList = new ArrayList<>(50);
        while(ita.hasNext()){
            Player player = ita.next();
            if(player.getWorldId() == EventSystem.EVENTSYSTEM_FFAMAP && player.isInFFA()){
                playerArrayList.add(player);
            }
        }
        if(playerArrayList.size() > 0){
            return playerArrayList.size();
        }else{
            return 0;
        }
	}

	@Override
	public boolean isEnemy(Player effector, Player effected) {
		return effector.isInFFA() && effected.isInFFA() && effector.getObjectId() != effected.getObjectId() && effector.isCasting() && effected.isCasting();
	}


	public String getName(Player player, Player target) {
		if (player.isGmMode()) {
			return target.getName();
		}

		String FFAplayerName = target.getPlayerClass().name();

		//if (!player.isInGroup2() && !player.isInAlliance2()) {
		//	FFAplayerName += " " + (target.getFFAIndex() + 1);
		//}

		return FFAplayerName;
	}

	public static int getDisplayTemplate(Player player, Item item) {
		if (item.getItemTemplate().isWeapon()) {
			switch (item.getItemTemplate().getWeaponType()) {
				case POLEARM_2H: // Gulare's Polearm
					return 101300983;
				case DAGGER_1H: // Gulare's Dagger
					return 100201189;
				case BOW: // Gulare's Bow
					return 101701064;
				case SWORD_1H: // Gulare's Sword
					return 100001348;
				case SWORD_2H: // Gulare's Greatsword
					return 100901040;
				case MACE_1H: // Gulare's Mace
					return 100101027;
				case STAFF_2H: // Gulare's Staff
					return 101501055;
				case ORB_2H: // Gulare's Orb
					return 100501040;
				case BOOK_2H: // Gulare's Spellbook
					return 100601092;
				case GUN_1H: // Gulare's Pistol
					return 101800837;
				case CANNON_2H: // Gulare's Aethercannon
					return 101900841;
				case HARP_2H: // Gulare's Harp
					return 102000875;
				case KEYBLADE_2H: // Gulare's Cipher-Blade
					return 102100733;
				default:
					return 102100733;
			}
		} else if (item.getEquipmentSlot() == 8) {// Torse
			if (player.getRace() == Race.ELYOS) {
				return 110101255;// Legion suit
			} else {
				return 110101257;// Legion suit
			}
		} else if (item.getItemTemplate().getArmorType() == ArmorType.SHIELD) // bouclier
		{
			return 115001388;// Antro's Shield
		} else {
			return item.getItemSkinTemplate().getTemplateId();
		}
	}
}
