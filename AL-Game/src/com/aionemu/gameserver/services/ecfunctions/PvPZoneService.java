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

import com.aionemu.gameserver.dataholders.DataManager;
import com.aionemu.gameserver.model.Race;
import com.aionemu.gameserver.model.gameobjects.Npc;
import com.aionemu.gameserver.model.gameobjects.VisibleObject;
import com.aionemu.gameserver.model.gameobjects.player.Player;
import com.aionemu.gameserver.model.templates.spawns.SpawnTemplate;
import com.aionemu.gameserver.services.teleport.TeleportService2;
import com.aionemu.gameserver.spawnengine.SpawnEngine;
import com.aionemu.gameserver.utils.PacketSendUtility;
import com.aionemu.gameserver.world.World;
import com.aionemu.gameserver.world.knownlist.Visitor;

/**
 * @author Luno from AionExtreme
 * @rework Eloann
 */
public class PvPZoneService {

    private static VisibleObject ELYGATE;
    private static VisibleObject ASMOGATE;
    private static VisibleObject CANNON;
    private static VisibleObject[] eventnpc;
    private static boolean opened = false;

    public static boolean Spawn(int ELYGATEnpcID, int ASMOGATEnpcID, int CANNONnpcID) {
        if (!opened) {
            //Gelkmaros Gate
            SpawnTemplate spawn = SpawnEngine.addNewSingleTimeSpawn(220070000, ASMOGATEnpcID, 1812.5325f, 2929.5986f, 554.7982f, (byte) 0);
            VisibleObject visibleObject = SpawnEngine.spawnObject(spawn, 1);

            //Inggison Gate
            SpawnTemplate spawn2 = SpawnEngine.addNewSingleTimeSpawn(210050000, ELYGATEnpcID, 1272.4163f, 330.46143f, 597.85114f, (byte) 0);
            VisibleObject visibleObject2 = SpawnEngine.spawnObject(spawn2, 1);

            //Cannon
            SpawnTemplate spawn3 = SpawnEngine.addNewSingleTimeSpawn(300100000, CANNONnpcID, 681.724f, 550.67f, 1023.79f, (byte) 2);
            VisibleObject visibleObject3 = SpawnEngine.spawnObject(spawn3, 1);

            World.getInstance().doOnAllPlayers(new Visitor<Player>() {
                @Override
                public void visit(Player p) {
                    PacketSendUtility.sendWhiteMessageOnCenter(p, "SteelRake Event Zone PvPvE is now open !");
                }
            });

            ELYGATE = visibleObject;
            ASMOGATE = visibleObject2;
            CANNON = visibleObject3;
            opened = true;

            int mapId = 300100000;
            eventnpc = new VisibleObject[34];

            //Eventnpc
            SpawnTemplate spawn4 = SpawnEngine.addNewSingleTimeSpawn(mapId, 204510, 505.083f, 522.940f, 1033.27f, (byte) 103);
            SpawnEngine.spawnObject(spawn4, 1);
            SpawnTemplate spawn5 = SpawnEngine.addNewSingleTimeSpawn(mapId, 204510, 493.386f, 523.142f, 1033.27f, (byte) 95);
            SpawnEngine.spawnObject(spawn5, 1);
            SpawnTemplate spawn6 = SpawnEngine.addNewSingleTimeSpawn(mapId, 204510, 478.550f, 522.938f, 1033.27f, (byte) 119);
            SpawnEngine.spawnObject(spawn6, 1);
            SpawnTemplate spawn7 = SpawnEngine.addNewSingleTimeSpawn(mapId, 204510, 494.086f, 543.000f, 1034.76f, (byte) 29);
            SpawnEngine.spawnObject(spawn7, 1);
            SpawnTemplate spawn8 = SpawnEngine.addNewSingleTimeSpawn(mapId, 204510, 493.904f, 535.081f, 1034.75f, (byte) 90);
            SpawnEngine.spawnObject(spawn8, 1);
            SpawnTemplate spawn9 = SpawnEngine.addNewSingleTimeSpawn(mapId, 204510, 486.043f, 522.754f, 1033.27f, (byte) 89);
            SpawnEngine.spawnObject(spawn9, 1);
            SpawnTemplate spawn10 = SpawnEngine.addNewSingleTimeSpawn(mapId, 204510, 515.335f, 523.375f, 1033.27f, (byte) 91);
            SpawnEngine.spawnObject(spawn10, 1);
            SpawnTemplate spawn11 = SpawnEngine.addNewSingleTimeSpawn(mapId, 204711, 618.918f, 541.987f, 1031.07f, (byte) 57);
            SpawnEngine.spawnObject(spawn11, 1);
            SpawnTemplate spawn12 = SpawnEngine.addNewSingleTimeSpawn(mapId, 204711, 618.587f, 550.355f, 1031.06f, (byte) 62);
            SpawnEngine.spawnObject(spawn12, 1);
            SpawnTemplate spawn13 = SpawnEngine.addNewSingleTimeSpawn(mapId, 204711, 615.788f, 545.923f, 1031.05f, (byte) 61);
            SpawnEngine.spawnObject(spawn13, 1);
            SpawnTemplate spawn14 = SpawnEngine.addNewSingleTimeSpawn(mapId, 204711, 638.013f, 469.111f, 1031.04f, (byte) 61);
            SpawnEngine.spawnObject(spawn14, 1);
            SpawnTemplate spawn15 = SpawnEngine.addNewSingleTimeSpawn(mapId, 204711, 637.519f, 475.227f, 1031.05f, (byte) 63);
            SpawnEngine.spawnObject(spawn15, 1);
            SpawnTemplate spawn16 = SpawnEngine.addNewSingleTimeSpawn(mapId, 204711, 626.466f, 469.339f, 1031.04f, (byte) 66);
            SpawnEngine.spawnObject(spawn16, 1);
            SpawnTemplate spawn17 = SpawnEngine.addNewSingleTimeSpawn(mapId, 204711, 626.797f, 476.795f, 1031.05f, (byte) 73);
            SpawnEngine.spawnObject(spawn17, 1);
            SpawnTemplate spawn18 = SpawnEngine.addNewSingleTimeSpawn(mapId, 798920, 463.071f, 559.572f, 1032.98f, (byte) 105);
            SpawnEngine.spawnObject(spawn18, 1);
            SpawnTemplate spawn19 = SpawnEngine.addNewSingleTimeSpawn(mapId, 799219, 686.955f, 465.914f, 1022.67f, (byte) 57);
            SpawnEngine.spawnObject(spawn19, 1);
            SpawnTemplate spawn20 = SpawnEngine.addNewSingleTimeSpawn(mapId, 730207, 482.904f, 540.082f, 1034.74f, (byte) 53);
            SpawnEngine.spawnObject(spawn20, 1);
            SpawnTemplate spawn21 = SpawnEngine.addNewSingleTimeSpawn(mapId, 250146, 411.274f, 544.287f, 1072.08f, (byte) 91);
            SpawnEngine.spawnObject(spawn21, 1);
            SpawnTemplate spawn22 = SpawnEngine.addNewSingleTimeSpawn(mapId, 250146, 416.153f, 473.500f, 1072.08f, (byte) 31);
            SpawnEngine.spawnObject(spawn22, 1);
            SpawnTemplate spawn23 = SpawnEngine.addNewSingleTimeSpawn(mapId, 700554, 624.570f, 541.108f, 936.094f, (byte) 60);
            SpawnEngine.spawnObject(spawn23, 1);
            SpawnTemplate spawn24 = SpawnEngine.addNewSingleTimeSpawn(mapId, 700473, 609.516f, 481.285f, 936.027f, (byte) 28);
            SpawnEngine.spawnObject(spawn24, 1);
            SpawnTemplate spawn25 = SpawnEngine.addNewSingleTimeSpawn(mapId, 700554, 578.419f, 514.348f, 944.670f, (byte) 88);
            SpawnEngine.spawnObject(spawn25, 1);
            SpawnTemplate spawn26 = SpawnEngine.addNewSingleTimeSpawn(mapId, 700554, 351.892f, 587.142f, 948.015f, (byte) 77);
            SpawnEngine.spawnObject(spawn26, 1);
            SpawnTemplate spawn27 = SpawnEngine.addNewSingleTimeSpawn(mapId, 700554, 283.347f, 452.374f, 952.558f, (byte) 90);
            SpawnEngine.spawnObject(spawn27, 1);
            SpawnTemplate spawn28 = SpawnEngine.addNewSingleTimeSpawn(mapId, 700554, 239.519f, 523.133f, 948.674f, (byte) 80);
            SpawnEngine.spawnObject(spawn28, 1);
            SpawnTemplate spawn29 = SpawnEngine.addNewSingleTimeSpawn(mapId, 700554, 428.203f, 536.419f, 946.658f, (byte) 39);
            SpawnEngine.spawnObject(spawn29, 1);
            SpawnTemplate spawn30 = SpawnEngine.addNewSingleTimeSpawn(mapId, 700554, 474.668f, 573.676f, 958.078f, (byte) 114);
            SpawnEngine.spawnObject(spawn30, 1);
            SpawnTemplate spawn31 = SpawnEngine.addNewSingleTimeSpawn(mapId, 700554, 516.533f, 533.960f, 958.744f, (byte) 6);
            SpawnEngine.spawnObject(spawn31, 1);
            SpawnTemplate spawn32 = SpawnEngine.addNewSingleTimeSpawn(mapId, 281443, 242.355f, 506.127f, 948.674f, (byte) 119);
            SpawnEngine.spawnObject(spawn32, 1);
            SpawnTemplate spawn33 = SpawnEngine.addNewSingleTimeSpawn(mapId, 700473, 237.316f, 506.174f, 948.674f, (byte) 62);
            SpawnEngine.spawnObject(spawn33, 1);
            SpawnTemplate spawn34 = SpawnEngine.addNewSingleTimeSpawn(mapId, 250147, 233.975f, 489.493f, 948.674f, (byte) 89);
            SpawnEngine.spawnObject(spawn34, 1);
            SpawnTemplate spawn35 = SpawnEngine.addNewSingleTimeSpawn(mapId, 281938, 413.900f, 510.320f, 1071.85f, (byte) 62);
            SpawnEngine.spawnObject(spawn35, 1);
            SpawnTemplate spawn36 = SpawnEngine.addNewSingleTimeSpawn(mapId, 217778, 264.693f, 527.494f, 947.018f, (byte) 26);
            SpawnEngine.spawnObject(spawn36, 1);
            SpawnTemplate spawn37 = SpawnEngine.addNewSingleTimeSpawn(mapId, 700554, 457.228f, 511.480f, 952.556f, (byte) 6);
            SpawnEngine.spawnObject(spawn37, 1);
        }
        return false;
    }

    public static void AdminReset() {
        try {
            Delete();
        } catch (Exception ex) {
        }
        ELYGATE = null;
        ASMOGATE = null;
        CANNON = null;
    }

    public static boolean Delete() {
        if (opened) {
            DataManager.SPAWNS_DATA2.getTemplates().remove(ELYGATE);
            DataManager.SPAWNS_DATA2.getTemplates().remove(ASMOGATE);
            DataManager.SPAWNS_DATA2.getTemplates().remove(CANNON);

            for (VisibleObject npcs : eventnpc) {
                Npc spawn = (Npc) npcs;
                spawn.getController().delete();
            }

            World.getInstance().doOnAllPlayers(new Visitor<Player>() {
                @Override
                public void visit(Player p) {
                    if (p.getWorldId() == 300100000) {
                        if (p.getX() <= 1000f && p.getX() >= 100f && p.getY() <= 1000f && p.getY() >= 100f) {
                            if (p.getCommonData().getRace().equals(Race.ELYOS)) {
                                TeleportService2.teleportTo(p, 210050000, 1275.1191f, 328.14645f, 597.85114f, (byte) 0); // Inggison
                                PacketSendUtility.sendMessage(p, "SteelRake Event Zone PvPvE is now close ! Teleported to Inggison !");
                            } else if (p.getCommonData().getRace().equals(Race.ASMODIANS)) {
                                TeleportService2.teleportTo(p, 220070000, 1808.944f, 2931.2979f, 554.8001f, (byte) 0); // Gelkmaros
                                PacketSendUtility.sendMessage(p, "SteelRake Event Zone PvPvE is now close ! Teleported to Gelkmaros !");
                            }
                        }
                    }
                    PacketSendUtility.sendYellowMessageOnCenter(p, "SteelRake Event Zone PvPvE is now close !");
                }
            });
            opened = false;
            return true;
        } else {
            return false;
        }
    }
}
