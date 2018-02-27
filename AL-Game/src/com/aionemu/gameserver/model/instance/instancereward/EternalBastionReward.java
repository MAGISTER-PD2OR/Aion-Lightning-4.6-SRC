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


package com.aionemu.gameserver.model.instance.instancereward;

import com.aionemu.gameserver.model.instance.playerreward.EternalBastionPlayerReward;

/**
 * @author Eloann
 */
public class EternalBastionReward extends InstanceReward<EternalBastionPlayerReward> {

<<<<<<< .mine
    private int points;
    private int npcKills;
    private int rank = 7;
    private int scoreAP;
    private int ceramium;
    private int sillus;
    private int favorable;
    private boolean isRewarded = false;
=======
	private int points;
	private int npcKills;
	private int rank = 7;
	private int basicAP;
	private int powerfulBundlewater;
	private int ceraniumMedal;
	private int powerfulBundleessence;
	private int largeBundlewater;
	private int largeBundleessence;
	private int smallBundlewater;
	private boolean isRewarded = false;
>>>>>>> .r274

    public EternalBastionReward(Integer mapId, int instanceId) {
        super(mapId, instanceId);
    }

    public void addPoints(int points) {
        this.points += points;
    }

    public int getPoints() {
        return points;
    }

    public void addNpcKill() {
        npcKills++;
    }

    public int getNpcKills() {
        return npcKills;
    }

    public void setRank(int rank) {
        this.rank = rank;
    }

    public int getRank() {
        return rank;
    }

<<<<<<< .mine
    public int getScoreAP() {
        return scoreAP;
    }

    public void setScoreAP(int ap) {
        this.scoreAP = ap;
    }

=======
>>>>>>> .r274
    public boolean isRewarded() {
        return isRewarded;
    }

    public void setRewarded() {
        isRewarded = true;
    }

<<<<<<< .mine
    public int getCeramium() {
        return ceramium;
    }
=======
	public int getBasicAP() {
		return basicAP;
	}
>>>>>>> .r274

<<<<<<< .mine
    public void setCeramium(int ceramium) {
        this.ceramium = ceramium;
    }
=======
	public void setBasicAP(int ap) {
		this.basicAP = ap;
	}
>>>>>>> .r274

<<<<<<< .mine
    public int getSillus() {
        return sillus;
    }
=======
	public int getCeramiumMedal() {
		return ceraniumMedal;
	}
>>>>>>> .r274

<<<<<<< .mine
    public void setSillus(int sillus) {
        this.sillus = sillus;
    }
=======
	public int getPowerfulBundleWater() {
		return powerfulBundlewater;
	}
>>>>>>> .r274

<<<<<<< .mine
    public int getFavorable() {
        return favorable;
    }
=======
	public int getPowerfulBundleEssence() {
		return powerfulBundleessence;
	}
>>>>>>> .r274

<<<<<<< .mine
    public void setFavorable(int favorable) {
        this.favorable = favorable;
    }
=======
	public int getLargeBundleWater() {
		return largeBundlewater;
	}
>>>>>>> .r274

	public int getLargeBundleEssence() {
		return largeBundleessence;
	}

	public int getSmallBundleWater() {
		return smallBundlewater;
	}

	public void setCeramiumMedal(int ceraniumMedal) {
		this.ceraniumMedal = ceraniumMedal;
	}

	public void setPowerfulBundleWater(int powerfulBundlewater) {
		this.powerfulBundlewater = powerfulBundlewater;
	}

	public void setPowerfulBundleEssence(int powerfulBundleessence) {
		this.powerfulBundleessence = powerfulBundleessence;
	}

	public void setLargeBundleWater(int largeBundlewater) {
		this.largeBundlewater = largeBundlewater;
	}

	public void setLargeBundleEssence(int largeBundleessence) {
		this.largeBundleessence = largeBundleessence;
	}

	public void setSmallBundleWater(int smallBundlewater) {
		this.smallBundlewater = smallBundlewater;
	}
}
