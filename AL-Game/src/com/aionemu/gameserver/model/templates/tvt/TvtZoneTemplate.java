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



package com.aionemu.gameserver.model.templates.tvt;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "tvt_zone_data")
public class TvtZoneTemplate {

    @XmlElement(name = "tvt_world")
    protected List<TvtZoneTemplate.TvtWorld> tvtWorld;

    public List<TvtZoneTemplate.TvtWorld> getTvtWorld() {
        if (tvtWorld == null) {
            tvtWorld = new ArrayList<TvtZoneTemplate.TvtWorld>();
        }
        return tvtWorld;
    }

    @XmlAccessorType(XmlAccessType.FIELD)
    @XmlType(name = "tvt_world")
    public static class TvtWorld {

        @XmlElement(name = "tvt_stage")
        protected List<TvtZoneTemplate.TvtStage> tvtStage;
        @XmlAttribute(name = "mapId")
        private int mapId;

        public List<TvtZoneTemplate.TvtStage> getStage() {
            if (tvtStage == null) {
                tvtStage = new ArrayList<TvtZoneTemplate.TvtStage>();
            }
            return tvtStage;
        }

        public int getMapId() {
            return mapId;
        }

        public int getSize() {
            return tvtStage.size();
        }

        public TvtZoneTemplate.TvtStage getPositionForStage(int value) {
            return tvtStage.get(value);
        }
    }

    @XmlAccessorType(XmlAccessType.FIELD)
    @XmlType(name = "tvt_stage")
    public static class TvtStage {

        @XmlElement(name = "tvt_position")
        protected List<TvtZoneTemplate.TvtPosition> tvtPosition;
        @XmlAttribute(name = "id")
        private int id;
        @XmlAttribute(name = "time")
        private int time;

        public List<TvtZoneTemplate.TvtPosition> getPosition() {
            if (tvtPosition == null) {
                tvtPosition = new ArrayList<TvtZoneTemplate.TvtPosition>();
            }
            return tvtPosition;
        }

        public int getId() {
            return id;
        }

        public int getTime() {
            return time;
        }
    }

    @XmlAccessorType(XmlAccessType.FIELD)
    @XmlType(name = "tvt_position")
    public static class TvtPosition {

        @XmlAttribute(name = "x")
        private float x;
        @XmlAttribute(name = "y")
        private float y;
        @XmlAttribute(name = "z")
        private float z;
        @XmlAttribute(name = "h")
        private byte h;

        public float getX() {
            return x;
        }

        public float getY() {
            return y;
        }

        public float getZ() {
            return z;
        }

        public byte getH() {
            return h;
        }
    }
}
