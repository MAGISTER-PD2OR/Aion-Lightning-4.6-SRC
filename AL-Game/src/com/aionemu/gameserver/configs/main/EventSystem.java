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
 * Created by Kill3r
 */
public class EventSystem {

    /**
     * FFA Map
     * Default : AlquimiaResearchCenter : 320110000 MapID
     * After writing the Map ID , you need to go to here and change this part at top
     * \AL-Game\data\scripts\system\handlers\instance\AlquimiaResearchCenterInstance.java -- @InstanceID(MAP ID HERE)
     */
    @Property(key = "gameserver.eventsystem.ffa.mapid", defaultValue = "320110000")
    public static int EVENTSYSTEM_FFAMAP;

    /**
     * FFA Map's Spawn Points, You need atleast 10 points
     * X:
     * Y:
     * Z:
     * Float values required
     */

    /**
     * Point One
     */
    @Property(key = "gameserver.eventsystem.ffa.cordinates.onex", defaultValue = "282.82547")
    public static float FFA_SPAWNPOINT_1X;
    @Property(key = "gameserver.eventsystem.ffa.cordinates.oney", defaultValue = "501.17563")
    public static float FFA_SPAWNPOINT_1Y;
    @Property(key = "gameserver.eventsystem.ffa.cordinates.onez", defaultValue = "211.67049")
    public static float FFA_SPAWNPOINT_1Z;

    /**
     * Point Two
     */
    @Property(key = "gameserver.eventsystem.ffa.cordinates.twox", defaultValue = "603")
    public static float FFA_SPAWNPOINT_2X;
    @Property(key = "gameserver.eventsystem.ffa.cordinates.twoy", defaultValue = "527")
    public static float FFA_SPAWNPOINT_2Y;
    @Property(key = "gameserver.eventsystem.ffa.cordinates.twoz", defaultValue = "200")
    public static float FFA_SPAWNPOINT_2Z;

    /**
     * Point Three
     */
    @Property(key = "gameserver.eventsystem.ffa.cordinates.threex", defaultValue = "528.2479")
    public static float FFA_SPAWNPOINT_3X;
    @Property(key = "gameserver.eventsystem.ffa.cordinates.threey", defaultValue = "207.35722")
    public static float FFA_SPAWNPOINT_3Y;
    @Property(key = "gameserver.eventsystem.ffa.cordinates.threez", defaultValue = "497.95782")
    public static float FFA_SPAWNPOINT_3Z;

    /**
     * Point Four
     */
    @Property(key = "gameserver.eventsystem.ffa.cordinates.fourx", defaultValue = "342.00424")
    public static float FFA_SPAWNPOINT_4X;
    @Property(key = "gameserver.eventsystem.ffa.cordinates.foury", defaultValue = "515.91003")
    public static float FFA_SPAWNPOINT_4Y;
    @Property(key = "gameserver.eventsystem.ffa.cordinates.fourz", defaultValue = "209.9201")
    public static float FFA_SPAWNPOINT_4Z;

    /**
     * Point Five
     */
    @Property(key = "gameserver.eventsystem.ffa.cordinates.fivex", defaultValue = "341.80997")
    public static float FFA_SPAWNPOINT_5X;
    @Property(key = "gameserver.eventsystem.ffa.cordinates.fivey", defaultValue = "487.36844")
    public static float FFA_SPAWNPOINT_5Y;
    @Property(key = "gameserver.eventsystem.ffa.cordinates.fivez", defaultValue = "209.9201")
    public static float FFA_SPAWNPOINT_5Z;

    /**
     * Point Six
     */
    @Property(key = "gameserver.eventsystem.ffa.cordinates.sixx", defaultValue = "455.51602")
    public static float FFA_SPAWNPOINT_6X;
    @Property(key = "gameserver.eventsystem.ffa.cordinates.sixy", defaultValue = "560.1399")
    public static float FFA_SPAWNPOINT_6Y;
    @Property(key = "gameserver.eventsystem.ffa.cordinates.sixz", defaultValue = "204.69067")
    public static float FFA_SPAWNPOINT_6Z;

    /**
     * Point Seven
     */
    @Property(key = "gameserver.eventsystem.ffa.cordinates.sevenx", defaultValue = "444.06195")
    public static float FFA_SPAWNPOINT_7X;
    @Property(key = "gameserver.eventsystem.ffa.cordinates.seveny", defaultValue = "505.53363")
    public static float FFA_SPAWNPOINT_7Y;
    @Property(key = "gameserver.eventsystem.ffa.cordinates.sevenz", defaultValue = "204.69067")
    public static float FFA_SPAWNPOINT_7Z;

    /**
     * Point Eight
     */
    @Property(key = "gameserver.eventsystem.ffa.cordinates.eightx", defaultValue = "407.76617")
    public static float FFA_SPAWNPOINT_8X;
    @Property(key = "gameserver.eventsystem.ffa.cordinates.eighty", defaultValue = "533.2193")
    public static float FFA_SPAWNPOINT_8Y;
    @Property(key = "gameserver.eventsystem.ffa.cordinates.eightz", defaultValue = "211.37567")
    public static float FFA_SPAWNPOINT_8Z;

    /**
     * Point Nine
     */
    @Property(key = "gameserver.eventsystem.ffa.cordinates.ninex", defaultValue = "476.5301")
    public static float FFA_SPAWNPOINT_9X;
    @Property(key = "gameserver.eventsystem.ffa.cordinates.niney", defaultValue = "556.405")
    public static float FFA_SPAWNPOINT_9Y;
    @Property(key = "gameserver.eventsystem.ffa.cordinates.ninez", defaultValue = "204.69067")
    public static float FFA_SPAWNPOINT_9Z;

    /**
     * Point Ten
     */
    @Property(key = "gameserver.eventsystem.ffa.cordinates.tenx", defaultValue = "477.02695")
    public static float FFA_SPAWNPOINT_10X;
    @Property(key = "gameserver.eventsystem.ffa.cordinates.teny", defaultValue = "493.03632")
    public static float FFA_SPAWNPOINT_10Y;
    @Property(key = "gameserver.eventsystem.ffa.cordinates.tenz", defaultValue = "204.69067")
    public static float FFA_SPAWNPOINT_10Z;

    /**
     * FFA Entering and Leaving Messages
     */

    /**
     * Welcome Message
     * Default : Welcome to FFA
     */
    @Property(key = "gameserver.eventsystem.ffa.entermessage", defaultValue = "Welcome to FFA")
    public static String FFA_WELCOME_MSG;

    /**
     * Leaving Message
     * Default : Leaving so Soon?
     */
    @Property(key = "gameserver.eventsystem.ffa.leavemessage", defaultValue = "Leaving so soon?")
    public static String FFA_LEAVE_MSG;

    /**
     * Killing Spree Settings and Announce Name
     * AP Reward / GP Reward
     * Reward increases on each 5 kills
     */

    /**
     * Announcer Name for FFA
     * Default : FFA
     * Example : [FFA] Player1 has just killed Player2
     */
    @Property(key = "gameserver.eventsystem.ffa.announcername", defaultValue = "FFA")
    public static String FFA_ANNOUNCER_NAME;


    /**
     * After 5th Kill Reward
     * First AP then GP
     * Default : AP = 5000
     * Default : GP = 150
     */
    @Property(key = "gameserver.eventsystem.ffa.reward.ap1", defaultValue = "5000")
    public static int FFA_REWARD_AP1;
    @Property(key = "gameserver.eventsystem.ffa.reward.gp1", defaultValue = "150")
    public static int FFA_REWARD_GP1;

    /**
     * After 10th Kill Reward
     * First AP then GP
     * Default : AP = 7500
     * Default : GP = 250
     */
    @Property(key = "gameserver.eventsystem.ffa.reward.ap2", defaultValue = "7500")
    public static int FFA_REWARD_AP2;
    @Property(key = "gameserver.eventsystem.ffa.reward.gp2", defaultValue = "250")
    public static int FFA_REWARD_GP2;

    /**
     * After 15th Kill Reward
     * First AP then GP
     * Default : AP = 10000
     * Default : GP = 450
     */
    @Property(key = "gameserver.eventsystem.ffa.reward.ap3", defaultValue = "10000")
    public static int FFA_REWARD_AP3;
    @Property(key = "gameserver.eventsystem.ffa.reward.gp3", defaultValue = "450")
    public static int FFA_REWARD_GP3;

    /**
     * After 20th Kill Reward
     * First AP then GP
     * Default : AP = 15000
     * Default : GP = 650
     */
    @Property(key = "gameserver.eventsystem.ffa.reward.ap4", defaultValue = "15000")
    public static int FFA_REWARD_AP4;
    @Property(key = "gameserver.eventsystem.ffa.reward.gp4", defaultValue = "650")
    public static int FFA_REWARD_GP4;

    /**
     * After 25th Kill Reward
     * First AP then GP
     * Default : AP = 20000
     * Default : GP = 850
     */
    @Property(key = "gameserver.eventsystem.ffa.reward.ap5", defaultValue = "20000")
    public static int FFA_REWARD_AP5;
    @Property(key = "gameserver.eventsystem.ffa.reward.gp5", defaultValue = "850")
    public static int FFA_REWARD_GP5;

    /**
     * After 30th Kill Reward
     * First AP then GP
     * Default : AP = 25000
     * Default : GP = 1000
     */
    @Property(key = "gameserver.eventsystem.ffa.reward.ap6", defaultValue = "25000")
    public static int FFA_REWARD_AP6;
    @Property(key = "gameserver.eventsystem.ffa.reward.gp6", defaultValue = "1000")
    public static int FFA_REWARD_GP6;

    /**
     * After 35th Kill Reward
     * First AP then GP
     * Default : AP = 30000
     * Default : GP = 1250
     */
    @Property(key = "gameserver.eventsystem.ffa.reward.ap7", defaultValue = "30000")
    public static int FFA_REWARD_AP7;
    @Property(key = "gameserver.eventsystem.ffa.reward.gp7", defaultValue = "1250")
    public static int FFA_REWARD_GP7;

    /**
     * After 40th Kill Reward
     * First AP then GP
     * Default : AP = 35000
     * Default : GP = 1500
     */
    @Property(key = "gameserver.eventsystem.ffa.reward.ap8", defaultValue = "35000")
    public static int FFA_REWARD_AP8;
    @Property(key = "gameserver.eventsystem.ffa.reward.gp8", defaultValue = "1500")
    public static int FFA_REWARD_GP8;

    /**
     * After 45th Kill Reward
     * First AP then GP
     * Default : AP = 40000
     * Default : GP = 1750
     */
    @Property(key = "gameserver.eventsystem.ffa.reward.ap9", defaultValue = "40000")
    public static int FFA_REWARD_AP9;
    @Property(key = "gameserver.eventsystem.ffa.reward.gp9", defaultValue = "1750")
    public static int FFA_REWARD_GP9;

    /**
     * After 50th Kill Reward
     * First AP then GP
     * Default : AP = 50000
     * Default : GP = 2000
     */
    @Property(key = "gameserver.eventsystem.ffa.reward.ap10", defaultValue = "50000")
    public static int FFA_REWARD_AP10;
    @Property(key = "gameserver.eventsystem.ffa.reward.gp10", defaultValue = "2000")
    public static int FFA_REWARD_GP10;

    /**
     * After 50 kills and higher
     * First AP then GP
     * Default : AP = 55000
     * Default : GP = 2250
     */
    @Property(key = "gameserver.eventsystem.ffa.reward.ap11", defaultValue = "55000")
    public static int FFA_REWARD_AP11;
    @Property(key = "gameserver.eventsystem.ffa.reward.gp11", defaultValue = "2250")
    public static int FFA_REWARD_GP11;

    /**
     *  Returning Settings ( .ffa leave)
     *  Default : False
     *  False - The player will be ported to Pandaemonium/Sanctum
     *  True - The player will be ported to the location where he typed .ffa enter
     */
    @Property(key = "gameserver.eventsystem.ffa.returnposition", defaultValue = "false")
    public static boolean FFA_RETURN_TO_PREVLOCK;

}
