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

import com.aionemu.gameserver.configs.main.EternityConfig;
import com.aionemu.gameserver.model.items.storage.Storage;
import com.aionemu.gameserver.dataholders.DataManager;
import com.aionemu.gameserver.model.DescriptionId;
import com.aionemu.gameserver.model.gameobjects.Item;
import com.aionemu.gameserver.model.gameobjects.PersistentState;
import com.aionemu.gameserver.model.gameobjects.player.Equipment;
import com.aionemu.gameserver.model.gameobjects.player.Player;
import com.aionemu.gameserver.model.items.ItemSlot;
import com.aionemu.gameserver.model.templates.item.ItemTemplate;
import com.aionemu.gameserver.network.aion.serverpackets.SM_SYSTEM_MESSAGE;
import com.aionemu.gameserver.network.aion.serverpackets.SM_INVENTORY_UPDATE_ITEM;
import com.aionemu.gameserver.network.aion.serverpackets.SM_UPDATE_PLAYER_APPEARANCE;
import com.aionemu.gameserver.utils.PacketSendUtility;
import com.aionemu.gameserver.utils.ThreadPoolManager;

/**
 * @author Eloann
 */
public class ItemRemodelService {

    /**
     * @param player
     * @param keepItemObjId
     * @param extractItemObjId
     */
    public static void remodelItem(Player player, int keepItemObjId, int extractItemObjId) {
        Storage inventory = player.getInventory();
        Item keepItem = inventory.getItemByObjId(keepItemObjId);
        Item extractItem = inventory.getItemByObjId(extractItemObjId);

        long remodelCost = 50000000;

        if (keepItem == null || extractItem == null) { // NPE check.
            return;
        }

        // Check Player Level
        if (player.getLevel() < EternityConfig.ITEM_REMODEL_MINLEVEL) {

            PacketSendUtility.sendPacket(player, SM_SYSTEM_MESSAGE.STR_CHANGE_ITEM_SKIN_PC_LEVEL_LIMIT);
            return;
        }

        // Check Kinah
        if (player.getInventory().getKinahItem().getItemCount() < remodelCost) {
            PacketSendUtility.sendPacket(player, SM_SYSTEM_MESSAGE.STR_CHANGE_ITEM_SKIN_NOT_ENOUGH_GOLD(new DescriptionId(keepItem.getItemTemplate().getNameId())));
            return;
        }

        // Check for using "Pattern Reshaper" (168100000)
        if (extractItem.getItemTemplate().getTemplateId() == 168100000) {
            if (keepItem.getItemTemplate() == keepItem.getItemSkinTemplate()) {
                PacketSendUtility.sendMessage(player, "That item does not have a remodeled skin to remove.");
                return;
            }
            // Remove Money
            player.getInventory().decreaseKinah(remodelCost);

            // Remove Pattern Reshaper
            player.getInventory().decreaseItemCount(extractItem, 1);

            // Revert item to ORIGINAL SKIN
            keepItem.setItemSkinTemplate(keepItem.getItemTemplate());

            // Remove dye color if item can not be dyed.
            if (!keepItem.getItemTemplate().isItemDyePermitted()) {
                keepItem.setItemColor(0);
            }

            // Notify Player
            PacketSendUtility.sendPacket(player, new SM_INVENTORY_UPDATE_ITEM(player, keepItem));
            PacketSendUtility.sendPacket(player, SM_SYSTEM_MESSAGE.STR_CHANGE_ITEM_SKIN_SUCCEED(new DescriptionId(keepItem.getItemTemplate().getNameId())));

            return;
        }

        // Remove Money
        player.getInventory().decreaseKinah(remodelCost);

        // Remove Item
        player.getInventory().decreaseItemCount(extractItem, 1);

        // REMODEL ITEM
        keepItem.setItemSkinTemplate(extractItem.getItemSkinTemplate());

        // Transfer Dye
        keepItem.setItemColor(extractItem.getItemColor());

        // Notify Player
        PacketSendUtility.sendPacket(player, new SM_INVENTORY_UPDATE_ITEM(player, keepItem));
        PacketSendUtility.sendPacket(player, new SM_SYSTEM_MESSAGE(1300483, new DescriptionId(keepItem.getItemTemplate().getNameId())));
    }

    public static void systemRemodelItem(Player player, Item item, ItemTemplate model) {
        item.setItemSkinTemplate(model);

        if (item.isEquipped()) {
            player.getEquipment().setPersistentState(PersistentState.UPDATE_REQUIRED);
        } else {
            player.getInventory().setPersistentState(PersistentState.UPDATE_REQUIRED);
        }

        PacketSendUtility.sendPacket(player, new SM_INVENTORY_UPDATE_ITEM(player, item));
        PacketSendUtility.sendPacket(player, new SM_SYSTEM_MESSAGE(1300483, new DescriptionId(item.getItemTemplate().getNameId())));

        PacketSendUtility.broadcastPacket(player, new SM_UPDATE_PLAYER_APPEARANCE(player.getObjectId(), player.getEquipment().getEquippedItemsWithoutStigma()), true);
    }

    public static boolean commandRemodelItem(Player player, int itemId) {
        ItemTemplate template = DataManager.ITEM_DATA.getItemTemplate(itemId);
        if (template == null) {
            return false;
        }

        Equipment equip = player.getEquipment();
        if (equip == null) {
            return false;
        }

        for (Item item : equip.getEquippedItemsWithoutStigma()) {
            if (item.getEquipmentSlot() == ItemSlot.MAIN_OFF_HAND.getSlotIdMask() || item.getEquipmentSlot() == ItemSlot.SUB_OFF_HAND.getSlotIdMask()) {
                continue;
            }

            if (item.getItemTemplate().isWeapon()) {
                if (item.getItemTemplate().getWeaponType() == template.getWeaponType() && item.getItemSkinTemplate().getTemplateId() != itemId) {
                    ItemRemodelService.systemRemodelItem(player, item, template);
                    return true;
                }
            } else if (item.getItemTemplate().isArmor()) {
                if (item.getItemTemplate().getItemSlot() == template.getItemSlot() && item.getItemSkinTemplate().getTemplateId() != itemId) {
                    ItemRemodelService.systemRemodelItem(player, item, template);
                    return true;
                }
            }
        }

        return false;
    }

    public static boolean commandPreviewRemodelItem(Player player, int itemId, int duration) {
        ItemTemplate template = DataManager.ITEM_DATA.getItemTemplate(itemId);
        if (template == null) {
            return false;
        }

        Equipment equip = player.getEquipment();
        if (equip == null) {
            return false;
        }

        for (Item item : equip.getEquippedItemsWithoutStigma()) {
            if (item.getEquipmentSlot() == ItemSlot.MAIN_OFF_HAND.getSlotIdMask() || item.getEquipmentSlot() == ItemSlot.SUB_OFF_HAND.getSlotIdMask()) {
                continue;
            }

            if (item.getItemTemplate().isWeapon()) {
                if (item.getItemTemplate().getWeaponType() == template.getWeaponType() && item.getItemSkinTemplate().getTemplateId() != itemId) {
                    systemPreviewRemodelItem(player, item, template, duration);
                    return true;
                }
            } else if (item.getItemTemplate().isArmor()) {
                if (item.getItemTemplate().getItemSlot() == template.getItemSlot() && item.getItemSkinTemplate().getTemplateId() != itemId) {
                    systemPreviewRemodelItem(player, item, template, duration);
                    return true;
                }
            }
        }

        return false;
    }

    public static void systemPreviewRemodelItem(final Player player, final Item item,
            ItemTemplate template, int duration) {
        final ItemTemplate oldTemplate = item.getItemSkinTemplate();
        item.setItemSkinTemplate(template);

        PacketSendUtility.sendPacket(player, new SM_INVENTORY_UPDATE_ITEM(player, item));
        PacketSendUtility.sendPacket(player, new SM_SYSTEM_MESSAGE(1300483, new DescriptionId(item.getItemTemplate().getNameId())));

        PacketSendUtility.broadcastPacket(player, new SM_UPDATE_PLAYER_APPEARANCE(player.getObjectId(), player.getEquipment().getEquippedItemsWithoutStigma()), true);

        ThreadPoolManager.getInstance().schedule(new Runnable() {
            @Override
            public void run() {
                item.setItemSkinTemplate(oldTemplate);
            }
        }, 50);

        ThreadPoolManager.getInstance().schedule(new Runnable() {
            @Override
            public void run() {
                PacketSendUtility.sendPacket(player, new SM_INVENTORY_UPDATE_ITEM(player, item));
                PacketSendUtility.broadcastPacket(player, new SM_UPDATE_PLAYER_APPEARANCE(player.getObjectId(), player.getEquipment().getEquippedItemsWithoutStigma()), true);
            }
        }, duration * 1000);
    }
}
