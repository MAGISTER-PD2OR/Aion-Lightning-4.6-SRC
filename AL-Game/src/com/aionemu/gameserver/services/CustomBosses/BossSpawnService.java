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


package com.aionemu.gameserver.services.CustomBosses;

import com.aionemu.gameserver.configs.main.CustomConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.aionemu.gameserver.model.gameobjects.Npc;
import com.aionemu.gameserver.model.gameobjects.player.Player;
import com.aionemu.gameserver.utils.PacketSendUtility;
import com.aionemu.gameserver.utils.ThreadPoolManager;
import com.aionemu.gameserver.world.World;
import com.aionemu.gameserver.utils.i18n.CustomMessageId;
import com.aionemu.gameserver.utils.i18n.LanguageHandler;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * @author Waii
 */
 
public class BossSpawnService {
    private static final Logger log = LoggerFactory.getLogger(BossSpawnService.class);
public static final BossSpawnService getInstance() {
        return SingletonHolder.instance;
    }
        int delay = 3600000;
        private List<CustomBossSpawn> bosses = new ArrayList<CustomBossSpawn>();
        List<CustomSpawnLocation> locs = new ArrayList<CustomSpawnLocation>();

        private BossSpawnService()
        {
        if (CustomConfig.CUSTOMBOSSES_ENABLE){
            bosses.add(new CustomBossSpawn(219358,  new Integer[] {400010000}));//Taha 60
            bosses.add(new CustomBossSpawn(230995,  new Integer[] {400010000}));//Lata
            bosses.add(new CustomBossSpawn(231196,  new Integer[] {400010000}));//Kamara
            bosses.add(new CustomBossSpawn(231305,  new Integer[] {400010000}));//Grendal

            log.info("Loaded " + bosses.size() + " Custom Boss Spawns");
            //START RESHENTA
            locs.add(new CustomSpawnLocation(400010000, 2969.841f, 1847.182f, 2882.6973f));
            locs.add(new CustomSpawnLocation(400010000, 2630.527f, 2618.5962f, 2889.845f));
            locs.add(new CustomSpawnLocation(400010000, 1930.7363f, 2738.3733f, 2929.8584f));
            locs.add(new CustomSpawnLocation(400010000, 1369.5917f, 2343.461f, 2922.9697f));
            //END RESHENTA


            log.info("Loaded " + locs.size() + " Custom Boss Spawn Locations");
            ThreadPoolManager.getInstance().scheduleAtFixedRate(new Runnable() {

                @Override
                public void run() {
                    SpawnBoss();
                }

            }, 10000, CustomConfig.CUSTOMBOSSES_DELAY * 60000);
		}
		else
		{
		    log.info("Custom BossSpawnService is disabled");
		}
        }

    public void SpawnBoss()
    {
        Random r = new Random();
        CustomBossSpawn boss;
        CustomSpawnLocation loc;
        if(!isBossFree() || !isLocFree())
            return;
        do
        {
            boss = getBosses().get(r.nextInt(getBosses().size()));

        }while(boss.isSpawned());
        do
        {
            loc = locs.get(r.nextInt(locs.size()));
        }while(loc.isUsed || !boss.isPossibleSpawn(loc.getWorldId()));
        boss.Spawn(loc);
        log.info("[BossSpawn] "+getBossName(boss.getNpc()) + " was spawned in " + getLocation(boss.getLoc().getWorldId()) + " (MAP: "+boss.getLoc().getWorldId()+" X: "+boss.getLoc().getX()+ " Y: "+boss.getLoc().getY()+ " Z: "+boss.getLoc().getZ()+")");

        SendMessage(getBossName(boss.getNpc()) + LanguageHandler.translate(CustomMessageId.CUSTOM_BOSS_SPAWNED) + getLocation(boss.getLoc().getWorldId()));
    }

   public String getBossName(Npc npc)
    {
       switch(npc.getNpcId())
       {

		   case 219358:
		       return LanguageHandler.translate(CustomMessageId.CUSTOM_BOSS_NAME1);//Taha
		   case 230995:
		       return LanguageHandler.translate(CustomMessageId.CUSTOM_BOSS_NAME2);//Tarotran
		   case 231196:
		       return LanguageHandler.translate(CustomMessageId.CUSTOM_BOSS_NAME3);//Raksha Boilheart //Raksha Kochherz
		   case 231305:
		       return LanguageHandler.translate(CustomMessageId.CUSTOM_BOSS_NAME4);//Big Red Orb //Grosse Rote Kugel
           default:
			   return LanguageHandler.translate(CustomMessageId.CUSTOM_BOSS_UNK);//Unk NPC //Unbekannter NPC
       }
   }

   public boolean isBossFree()
    {
        for(CustomBossSpawn boss : getBosses())
            if(!boss.isSpawned())
                return true;
        return false;
   }


   public String getLocation(int worldId)
   {
        switch(worldId)
        {
			case 400010000:
			    return LanguageHandler.translate(CustomMessageId.CUSTOM_BOSS_LOC1);//Reshenta
            default: 
			    return LanguageHandler.translate(CustomMessageId.CUSTOM_BOSS_LOC_UNK);//Nothing to return
        }

   }

   public boolean isLocFree()
    {
        for(CustomSpawnLocation loc : locs)
            if(!loc.isUsed)
                return true;
        return false;
   }

   public void SendMessage(String msg)
    {
       for(Player p : World.getInstance().getAllPlayers())
       {
			PacketSendUtility.sendBrightYellowMessage(p, msg);
        }
   }

   public CustomBossSpawn getCustomBoss(Npc npc)
   {
        for(CustomBossSpawn boss : getBosses())
        {
            if(boss.getNpc() == npc)
                    return boss;
        }
        return null;
   }

    /**
     * @return the bosses
     */
    public List<CustomBossSpawn> getBosses() {
        return bosses;
    }

	@SuppressWarnings("synthetic-access")
    public static class SingletonHolder {
        protected static final BossSpawnService instance = new BossSpawnService();
    }
}
