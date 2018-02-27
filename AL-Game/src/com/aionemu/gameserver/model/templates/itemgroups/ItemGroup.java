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


package com.aionemu.gameserver.model.templates.itemgroups;

import javax.xml.bind.annotation.*;

import com.aionemu.gameserver.model.templates.rewards.BonusType;
import com.aionemu.gameserver.model.templates.rewards.IdReward;

/**
 * @author Rolandas
 *
 */
/**
 * <p>
 * Java class for ItemGroup complex type.
 * <p>
 * The following schema fragment specifies the expected content contained within
 * this class.
 *
 * <pre>
 * &lt;complexType name="ItemGroup">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;attribute name="bonusType" use="required" type="{}BonusType" />
 *       &lt;attribute name="chance" type="{http://www.w3.org/2001/XMLSchema}float" default="0" />
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ItemGroup")
@XmlSeeAlso({CraftItemGroup.class, CraftRecipeGroup.class, ManastoneGroup.class, FoodGroup.class, MedicineGroup.class,
    OreGroup.class, GatherGroup.class, EnchantGroup.class, BossGroup.class})
public abstract class ItemGroup {

    @XmlAttribute(name = "bonusType", required = true)
    protected BonusType bonusType;
    @XmlAttribute(name = "chance")
    protected Float chance;

    /**
     * Gets the value of the bonusType property.
     *
     * @return possible object is {@link BonusType }
     */
    public BonusType getBonusType() {
        return bonusType;
    }

    /**
     * Gets the value of the chance property.
     *
     * @return possible object is {@link Float }
     */
    public float getChance() {
        if (chance == null) {
            return 0.0F;
        } else {
            return chance;
        }
    }

    public abstract IdReward[] getRewards();
}
/*
public enum ItemGroup {

    NONE,
    NOWEAPON(3, ItemSubType.TWO_HAND),
    SWORD(3, ItemSubType.ONE_HAND, new int[]{1, 8}),
    GREATSWORD(3, ItemSubType.TWO_HAND, new int[]{15}),
    EXTRACT_SWORD(0, ItemSubType.NONE),
    DAGGER(3, ItemSubType.ONE_HAND, new int[]{30, 9}),
    MACE(3, ItemSubType.ONE_HAND, new int[]{3, 10}),
    ORB(3, ItemSubType.TWO_HAND, new int[]{64}),
    SPELLBOOK(3, ItemSubType.TWO_HAND, new int[]{64}),
    POLEARM(3, ItemSubType.TWO_HAND, new int[]{16}),
    STAFF(3, ItemSubType.TWO_HAND, new int[]{53}),
    BOW(3, ItemSubType.TWO_HAND, new int[]{17}),
    HARP(3, ItemSubType.TWO_HAND, new int[]{92, 78}),
    GUN(3, ItemSubType.ONE_HAND, new int[]{83, 76}),
    CANNON(3, ItemSubType.TWO_HAND, new int[]{77}),
    KEYBLADE(3, ItemSubType.TWO_HAND, new int[]{76, 79}),
    SHIELD(1 << 1, ItemSubType.SHIELD, new int[]{7, 14}),
    RB_TORSO(1 << 3, ItemSubType.ROBE, new int[]{67, 70}),
    RB_PANTS(1 << 12, ItemSubType.ROBE, new int[]{67, 70}),
    RB_SHOULDER(1 << 11, ItemSubType.ROBE, new int[]{67, 70}),
    RB_GLOVES(1 << 4, ItemSubType.ROBE, new int[]{67, 70}),
    RB_SHOES(1 << 5, ItemSubType.ROBE, new int[]{67, 70}),
    LT_TORSO(1 << 3, ItemSubType.LEATHER, new int[]{5, 12}),
    LT_PANTS(1 << 12, ItemSubType.LEATHER, new int[]{5, 12}),
    LT_SHOULDER(1 << 11, ItemSubType.LEATHER, new int[]{5, 12}),
    LT_GLOVES(1 << 4, ItemSubType.LEATHER, new int[]{5, 12}),
    LT_SHOES(1 << 5, ItemSubType.LEATHER, new int[]{5, 12}),
    CH_TORSO(1 << 3, ItemSubType.CHAIN, new int[]{6, 13}),
    CH_PANTS(1 << 12, ItemSubType.CHAIN, new int[]{6, 13}),
    CH_SHOULDER(1 << 11, ItemSubType.CHAIN, new int[]{6, 13}),
    CH_GLOVES(1 << 4, ItemSubType.CHAIN, new int[]{6, 13}),
    CH_SHOES(1 << 5, ItemSubType.CHAIN, new int[]{6, 13}),
    PL_TORSO(1 << 3, ItemSubType.PLATE, new int[]{18}),
    PL_PANTS(1 << 12, ItemSubType.PLATE, new int[]{18}),
    PL_SHOULDER(1 << 11, ItemSubType.PLATE, new int[]{18}),
    PL_GLOVES(1 << 4, ItemSubType.PLATE, new int[]{18}),
    PL_SHOES(1 << 5, ItemSubType.PLATE, new int[]{18}),
    CL_TORSO(1 << 3, ItemSubType.CLOTHES, new int[]{4}),
    CL_PANTS(1 << 12, ItemSubType.CLOTHES, new int[]{4}),
    CL_SHOULDER(1 << 11, ItemSubType.CLOTHES, new int[]{4}),
    CL_GLOVES(1 << 4, ItemSubType.CLOTHES, new int[]{4}),
    CL_SHOES(1 << 5, ItemSubType.CLOTHES, new int[]{4}),
    CL_MULTISLOT(10, ItemSubType.CLOTHES, new int[]{4}),
    CL_SHIELD(1 << 1, ArmorType.ACCESSORY),
    WINGS(1 << 15, ItemSubType.WING),
    REMODEL_WINGS(1 << 15, ItemSubType.CLOTHES),
    POWER_SHARDS(24576, ArmorType.ACCESSORY),
    EARRINGS(192, ArmorType.ACCESSORY),
    RINGS(768, ArmorType.ACCESSORY),
    NECKLACES(1 << 10, ArmorType.ACCESSORY),
    BELTS(1 << 16, ArmorType.ACCESSORY),
    HEADS(1 << 2, ArmorType.ACCESSORY),
    LT_HEADS(1 << 2, ItemSubType.LEATHER),
    CL_HEADS(1 << 2, ItemSubType.CLOTHES),
    DECORATIVE_HEADS(1 << 2, ArmorType.DECORATIVE),
    TGFSL_ROBE(10, ItemSubType.ROBE),
    STIGMA((long) 7E003F << 30, ItemSubType.STIGMA),
    // other
    ARROW(0, ItemSubType.ARROW),
    NPC_MACE(1, ItemSubType.ONE_HAND), // keep it above TOOLHOES, for search picking it up
    TOOLRODS(3, ItemSubType.TWO_HAND),
    TOOLHOES(1, ItemSubType.ONE_HAND),
    TOOLPICKS(3, ItemSubType.TWO_HAND),
    // non equip
    MANASTONE,
    ANCIENT_MANASTONE,
    RECIPE,
    ENCHANTMENT,
    PACK_SCROLL,
    FLUX,
    BALIC_EMOTION,
    BALIC_MATERIAL,
    RAWHIDE,
    SOULSTONE,
    GATHERABLE,
    GATHERABLE_BONUS,
    DROP_MATERIAL,
    COINS,
    MEDALS,
    QUEST,
    KEY,
    CRAFT_BOOST,
    TAMPERING,
    COMBINATION,
    SKILLBOOK,
    GODSTONE,
    STIGMA_SHARD;
}

public enum ItemSubType {

    NONE(EquipType.NONE),
    CHAIN(ArmorType.GENERAL),
    CLOTHES(ArmorType.GENERAL),
    LEATHER(ArmorType.GENERAL),
    PLATE(ArmorType.GENERAL),
    ROBE(ArmorType.GENERAL),
    SHIELD(ArmorType.GENERAL),
    ARROW(EquipType.NONE),
    WING(ArmorType.GENERAL),
    ONE_HAND(EquipType.WEAPON),
    TWO_HAND(EquipType.WEAPON),
    STIGMA(EquipType.STIGMA);
}
*/
