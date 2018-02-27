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


package com.aionemu.gameserver.services.craft;

import javolution.util.FastMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.aionemu.commons.services.CronService;
import com.aionemu.gameserver.configs.main.EternityConfig;
import com.aionemu.gameserver.model.gameobjects.Npc;
import com.aionemu.gameserver.model.gameobjects.VisibleObject;
import com.aionemu.gameserver.model.gameobjects.player.Player;
import com.aionemu.gameserver.spawnengine.SpawnEngine;
import com.aionemu.gameserver.utils.PacketSendUtility;
import com.aionemu.gameserver.utils.ThreadPoolManager;
import com.aionemu.gameserver.world.World;
import com.aionemu.gameserver.world.knownlist.Visitor;

/**
 * @author Maestross
 * @rework Eloann
 */
public class AdvancedCraftSpawn {

    private static final Logger log = LoggerFactory.getLogger(AdvancedCraftSpawn.class);
    private static final String CRAFT_NPC_SPAWN_RULE = EternityConfig.CRAFT_NPC_SPAWN_RULE;
    private FastMap<Integer, VisibleObject> AlchemySpawn = new FastMap<Integer, VisibleObject>();
    private FastMap<Integer, VisibleObject> WeaponsmithSpawn = new FastMap<Integer, VisibleObject>();
    private FastMap<Integer, VisibleObject> ArmorsmithSpawn = new FastMap<Integer, VisibleObject>();
    private FastMap<Integer, VisibleObject> CookSpawn = new FastMap<Integer, VisibleObject>();
    private FastMap<Integer, VisibleObject> FurnitureSpawn = new FastMap<Integer, VisibleObject>();
    private FastMap<Integer, VisibleObject> HandiworkSpawn = new FastMap<Integer, VisibleObject>();
    private FastMap<Integer, VisibleObject> AlchemySpawn2 = new FastMap<Integer, VisibleObject>();
    private FastMap<Integer, VisibleObject> WeaponsmithSpawn2 = new FastMap<Integer, VisibleObject>();
    private FastMap<Integer, VisibleObject> ArmorsmithSpawn2 = new FastMap<Integer, VisibleObject>();
    private FastMap<Integer, VisibleObject> CookSpawn2 = new FastMap<Integer, VisibleObject>();
    private FastMap<Integer, VisibleObject> FurnitureSpawn2 = new FastMap<Integer, VisibleObject>();
    private FastMap<Integer, VisibleObject> HandiworkSpawn2 = new FastMap<Integer, VisibleObject>();

    @SuppressWarnings("synthetic-access")
    private static class SingletonHolder {

        protected static final AdvancedCraftSpawn instance = new AdvancedCraftSpawn();
    }

    public static final AdvancedCraftSpawn getInstance() {
        return SingletonHolder.instance;
    }

    public void spawnNpcs() {
        CronService.getInstance().schedule(new Runnable() {
            @Override
            public void run() {
                AlchemySpawn.put(203304, SpawnEngine.spawnObject(SpawnEngine.addNewSingleTimeSpawn(110010000, 203304, 1843.3129F, 1528.8201F, 590.15826F, (byte) 0), 1));
                WeaponsmithSpawn.put(203305, SpawnEngine.spawnObject(SpawnEngine.addNewSingleTimeSpawn(110010000, 203305, 1845.3689F, 1581.1968F, 590.10864F, (byte) 0), 1));
                ArmorsmithSpawn.put(203306, SpawnEngine.spawnObject(SpawnEngine.addNewSingleTimeSpawn(110010000, 203306, 1887.7933F, 1555.9802F, 590.10864F, (byte) 0), 1));
                CookSpawn.put(203307, SpawnEngine.spawnObject(SpawnEngine.addNewSingleTimeSpawn(110010000, 203307, 1896.1805F, 1460.6974F, 590.0707F, (byte) 0), 1));
                FurnitureSpawn.put(203308, SpawnEngine.spawnObject(SpawnEngine.addNewSingleTimeSpawn(110010000, 203308, 1837.5762F, 1446.2203F, 590.10864F, (byte) 0), 1));
                HandiworkSpawn.put(203309, SpawnEngine.spawnObject(SpawnEngine.addNewSingleTimeSpawn(110010000, 203309, 1890.0148F, 1529.0122F, 590.1345F, (byte) 0), 1));

                AlchemySpawn2.put(203310, SpawnEngine.spawnObject(SpawnEngine.addNewSingleTimeSpawn(120010000, 203310, 1160.826F, 1523.4554F, 214.16386F, (byte) 0), 1));
                WeaponsmithSpawn2.put(203311, SpawnEngine.spawnObject(SpawnEngine.addNewSingleTimeSpawn(120010000, 203311, 1184.7854F, 1594.3524F, 214.13577F, (byte) 0), 1));
                ArmorsmithSpawn2.put(203312, SpawnEngine.spawnObject(SpawnEngine.addNewSingleTimeSpawn(120010000, 203312, 1183.2709F, 1593.7777F, 214.13577F, (byte) 0), 1));
                CookSpawn2.put(203313, SpawnEngine.spawnObject(SpawnEngine.addNewSingleTimeSpawn(120010000, 203313, 1252.5864F, 1554.5419F, 214.13104F, (byte) 0), 1));
                FurnitureSpawn2.put(203314, SpawnEngine.spawnObject(SpawnEngine.addNewSingleTimeSpawn(120010000, 203314, 1172.6512F, 1495.3778F, 214.25528F, (byte) 0), 1));
                HandiworkSpawn2.put(203315, SpawnEngine.spawnObject(SpawnEngine.addNewSingleTimeSpawn(120010000, 203315, 1186.0135F, 1492.5901F, 215.10434F, (byte) 0), 1));
                log.info("Craft Event started");
                World.getInstance().doOnAllPlayers(new Visitor<Player>() {
                    @Override
                    public void visit(Player player) {
                        PacketSendUtility.sendBrightYellowMessage(player, "The crafting event is started");
                    }
                });
                //After three hr the npcs will be despawned
                ThreadPoolManager.getInstance().schedule(new Runnable() {
                    @Override
                    public void run() {
                        for (VisibleObject vo : AlchemySpawn.values()) {
                            if (vo != null) {
                                Npc npc = (Npc) vo;
                                if (!npc.getLifeStats().isAlreadyDead()) {
                                    npc.getController().onDelete();
                                }
                            }
                            AlchemySpawn.clear();
                        }

                        for (VisibleObject vo : AlchemySpawn2.values()) {
                            if (vo != null) {
                                Npc npc = (Npc) vo;
                                if (!npc.getLifeStats().isAlreadyDead()) {
                                    npc.getController().onDelete();
                                }
                            }
                            AlchemySpawn2.clear();
                        }

                        for (VisibleObject vo : WeaponsmithSpawn.values()) {
                            if (vo != null) {
                                Npc npc = (Npc) vo;
                                if (!npc.getLifeStats().isAlreadyDead()) {
                                    npc.getController().onDelete();
                                }
                            }
                            WeaponsmithSpawn.clear();
                        }

                        for (VisibleObject vo : WeaponsmithSpawn2.values()) {
                            if (vo != null) {
                                Npc npc = (Npc) vo;
                                if (!npc.getLifeStats().isAlreadyDead()) {
                                    npc.getController().onDelete();
                                }
                            }
                            WeaponsmithSpawn2.clear();
                        }

                        for (VisibleObject vo : ArmorsmithSpawn.values()) {
                            if (vo != null) {
                                Npc npc = (Npc) vo;
                                if (!npc.getLifeStats().isAlreadyDead()) {
                                    npc.getController().onDelete();
                                }
                            }
                            ArmorsmithSpawn.clear();
                        }

                        for (VisibleObject vo : ArmorsmithSpawn2.values()) {
                            if (vo != null) {
                                Npc npc = (Npc) vo;
                                if (!npc.getLifeStats().isAlreadyDead()) {
                                    npc.getController().onDelete();
                                }
                            }
                            ArmorsmithSpawn2.clear();
                        }

                        for (VisibleObject vo : CookSpawn.values()) {
                            if (vo != null) {
                                Npc npc = (Npc) vo;
                                if (!npc.getLifeStats().isAlreadyDead()) {
                                    npc.getController().onDelete();
                                }
                            }
                            CookSpawn.clear();
                        }

                        for (VisibleObject vo : CookSpawn2.values()) {
                            if (vo != null) {
                                Npc npc = (Npc) vo;
                                if (!npc.getLifeStats().isAlreadyDead()) {
                                    npc.getController().onDelete();
                                }
                            }
                            CookSpawn2.clear();
                        }

                        for (VisibleObject vo : FurnitureSpawn.values()) {
                            if (vo != null) {
                                Npc npc = (Npc) vo;
                                if (!npc.getLifeStats().isAlreadyDead()) {
                                    npc.getController().onDelete();
                                }
                            }
                            FurnitureSpawn.clear();
                        }

                        for (VisibleObject vo : FurnitureSpawn2.values()) {
                            if (vo != null) {
                                Npc npc = (Npc) vo;
                                if (!npc.getLifeStats().isAlreadyDead()) {
                                    npc.getController().onDelete();
                                }
                            }
                            FurnitureSpawn2.clear();
                        }

                        for (VisibleObject vo : HandiworkSpawn.values()) {
                            if (vo != null) {
                                Npc npc = (Npc) vo;
                                if (!npc.getLifeStats().isAlreadyDead()) {
                                    npc.getController().onDelete();
                                }
                            }
                            HandiworkSpawn.clear();
                        }

                        for (VisibleObject vo : HandiworkSpawn2.values()) {
                            if (vo != null) {
                                Npc npc = (Npc) vo;
                                if (!npc.getLifeStats().isAlreadyDead()) {
                                    npc.getController().onDelete();
                                }
                            }
                            HandiworkSpawn2.clear();
                        }
                        log.info("Craft Event ended");
                        World.getInstance().doOnAllPlayers(new Visitor<Player>() {
                            @Override
                            public void visit(Player player) {
                                PacketSendUtility.sendBrightYellowMessage(player, "The crafting event is now closed");
                            }
                        });
                    }
                }, 10800 * 1000);
            }
        }, CRAFT_NPC_SPAWN_RULE);
    }
}
