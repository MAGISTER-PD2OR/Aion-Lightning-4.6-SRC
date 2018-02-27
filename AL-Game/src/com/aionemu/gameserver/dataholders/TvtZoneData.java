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



package com.aionemu.gameserver.dataholders;

import gnu.trove.map.hash.TIntObjectHashMap;

import java.util.List;

import javax.xml.bind.Unmarshaller;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import com.aionemu.gameserver.model.templates.tvt.TvtZoneTemplate;

@XmlRootElement(name = "tvt_zone_data")
@XmlAccessorType(XmlAccessType.FIELD)
public class TvtZoneData {

    @XmlElement(name = "tvt_world")
    private List<TvtZoneTemplate.TvtWorld> tvtWorld;
    private TIntObjectHashMap<TvtZoneTemplate.TvtWorld> tvtWorldList = new TIntObjectHashMap<TvtZoneTemplate.TvtWorld>();

    void afterUnmarshal(Unmarshaller u, Object parent) {
        tvtWorldList.clear();
        for (TvtZoneTemplate.TvtWorld list : tvtWorld) {
            tvtWorldList.put(list.getMapId(), list);
        }
    }

    public int size() {
        return tvtWorldList.size();
    }

    public TvtZoneTemplate.TvtWorld getMapId(int value) {
        return tvtWorldList.get(value);
    }
}
