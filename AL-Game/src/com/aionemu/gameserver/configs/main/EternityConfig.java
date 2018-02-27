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


package com.aionemu.gameserver.configs.main;

import com.aionemu.commons.configuration.Property;

/**
 * @author Eloann
 */
public class EternityConfig {

    @Property(key = "gameserver.advanced.stigma.onlevel", defaultValue = "true")
    public static boolean ADVANCED_STIGMA_ON_LEVEL;
    /**
     * Enable PK Switch command
     */
    @Property(key = "gameserver.pk.switch.enable", defaultValue = "false")
    public static boolean ENABLE_PK_SWITCH;
    @Property(key = "gameserver.pk.tag", defaultValue = "\u2620 %s")
    public static String TAG_PK;
    /**
     * Enable PVE Switch command
     */
    @Property(key = "gameserver.pve.switch.enable", defaultValue = "false")
    public static boolean ENABLE_PVE_SWITCH;
    @Property(key = "gameserver.pve.tag", defaultValue = "\u26E8 %s")
    public static String TAG_PVE;
    /**
     * Battleground System
     */
    @Property(key = "gameserver.battleground.enable", defaultValue = "false")
    public static boolean BATTLEGROUNDS_ENABLED;
    /**
     * FFA System
     */
    @Property(key = "gameserver.ffa.enable", defaultValue = "false")
    public static boolean FFA_ENABLE;
    /**
     * TvT Event configuration
     */
    @Property(key = "gameserver.tvtevent.enable", defaultValue = "true")
    public static boolean TVT_ENABLE;
    @Property(key = "gameserver.tvtevent.min.players", defaultValue = "6")
    public static int TVT_MIN_PLAYERS;
    @Property(key = "gameserver.tvtevent.skill.use", defaultValue = "9833")
    public static int TVT_SKILL_USE;
    @Property(key = "gameserver.tvtevent.winner.reward", defaultValue = "188051136")
    public static int TVT_WINNER_REWARD;
    @Property(key = "gameserver.tvtevent.winner.dublereward", defaultValue = "188051135")
    public static int TVT_WINNER_DOUBLEREWARD;
    @Property(key = "gameserver.tvtevent.winner.number", defaultValue = "1")
    public static int TVT_WINNER_NUMBER;
    /**
     * Crazy Daeva Event
     */
    @Property(key = "gameserver.crazy.daeva.enable", defaultValue = "false")
    public static boolean ENABLE_CRAZY;
    @Property(key = "gameserver.crazy.daeva.tag", defaultValue = "<Crazy>")
    public static String CRAZY_TAG;
    @Property(key = "gameserver.crazy.daeva.lowest.rnd", defaultValue = "10")
    public static int CRAZY_LOWEST_RND;
    @Property(key = "gameserver.crazy.daeva.endtime", defaultValue = "300000")
    public static int CRAZY_ENDTIME;
    /**
     * Advanced Craft NPC Spawn
     */
    @Property(key = "gameserver.craft_npc.time", defaultValue = "0 0 15 ? * *")
    public static String CRAFT_NPC_SPAWN_RULE;
    @Property(key = "gameserver.craft.npc.enable", defaultValue = "false")
    public static boolean CRAFT_NPC_SPAWN_ENABLE;
    /**
     * Invasion Rift
     */
    @Property(key = "gameserver.invasion.rift.enable", defaultValue = "false")
    public static boolean INVASION_RIFT_ENABLE;
    @Property(key = "gameserver.invasion.rift.elyos", defaultValue = "80")
    public static int MAX_INFLUENCE_ELYOS;
    @Property(key = "gameserver.invasion.rift.asmo", defaultValue = "80")
    public static int MAX_INFLUENCE_ASMO;
    /**
     * Balaur Invade
     */
    @Property(key = "gameserver.invasion.enable", defaultValue = "false")
    public static boolean INVASION_ENABLED;
    @Property(key = "gameserver.starter.hour", defaultValue = "17")
    public static int HOUR;
    @Property(key = "gameserver.starter.hour2", defaultValue = "19")
    public static int HOUR2;
    @Property(key = "gameserver.starter.hour3", defaultValue = "21")
    public static int HOUR3;
    @Property(key = "gameserver.starter.hour4", defaultValue = "23")
    public static int HOUR4;
    /**
     * PvPGrind System
     */
    @Property(key = "gameserver.pvpgrind.update", defaultValue = "0 0 0 ? * *")
    public static String PVP_GRIND_UP_RULE;
    @Property(key = "gameserver.pvpgrind.enable", defaultValue = "true")
    public static boolean PVP_GRIND_ENABLE;
    /**
     * Arena Announce Service
     */
    @Property(key = "gameserver.arena.announce.enable", defaultValue = "true")
    public static boolean ARENA_ANNOUNCE_ENABLE;
    @Property(key = "gameserver.dimensional.vortex.despawn.rule", defaultValue = "0 0 00 ? * *")
    public static String ARENA_ANNOUNCE_UPDATE_RULE;
    /**
     * TimeAP
     */
    @Property(key = "gameserver.timerap.enable", defaultValue = "false")
    public static boolean TIMEAPENABLE;
    @Property(key = "gameserver.count.time", defaultValue = "1000")
    public static int COUNT_TIME;
    /**
     * Minimum level for item remodelisation
     */
    @Property(key = "gameserver.itemremodel.minlevel", defaultValue = "20")
    public static int ITEM_REMODEL_MINLEVEL;
    /**
     * Forbidden Words
     */
    @Property(key = "gameserver.word.revoke", defaultValue = "")
    public static String WORD_REVOKE;
}
