/* 
 *  Copyright (C) 2016 Ivan1pl
 * 
 *  This file is part of Animations.
 * 
 *  Animations is free software: you can redistribute it and/or modify
 *  it under the terms of the GNU General Public License as published by
 *  the Free Software Foundation, either version 3 of the License, or
 *  (at your option) any later version.
 *
 *  Animations is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  GNU General Public License for more details.
 *
 *  You should have received a copy of the GNU General Public License
 *  along with Animations.  If not, see <http://www.gnu.org/licenses/>.
 */
package com.ivan1pl.animations.data;

import org.bukkit.Location;
import org.bukkit.configuration.Configuration;
import org.bukkit.configuration.ConfigurationSection;

import java.io.Serializable;

/**
 *
 * @author Ivan1pl
 */
public class Selection implements Serializable {

    private static final long serialVersionUID = 3063220770448203490L;
    
    private AnimationsLocation point1;
    private AnimationsLocation point2;
    
    private boolean validate() {
        return (point1 != null && point2 != null && point1.getWorld().getUID().equals(point2.getWorld().getUID()));
    }
    
    public static boolean isValid(Selection sel) {
        return sel != null && sel.validate();
    }
    
    public void setPoint1(Location loc) {
        point1 = AnimationsLocation.fromLocation(loc);
    }
    
    public void setPoint2(Location loc) {
        point2 = AnimationsLocation.fromLocation(loc);
    }
    
    public int getVolume() {
        if (!validate()) {
            return 0;
        }
        
        int dx = Math.abs(point1.getBlockX() - point2.getBlockX()) + 1;
        int dy = Math.abs(point1.getBlockY() - point2.getBlockY()) + 1;
        int dz = Math.abs(point1.getBlockZ() - point2.getBlockZ()) + 1;
        return dx*dy*dz;
    }
    
    public void expand(int dx, int dy, int dz) {
        if (dx < 0) {
            if (point1.getBlockX() < point2.getBlockX()) {
                point1.add(dx, 0, 0);
            } else {
                point2.add(dx, 0, 0);
            }
        } else {
            if (point1.getBlockX() < point2.getBlockX()) {
                point2.add(dx, 0, 0);
            } else {
                point1.add(dx, 0, 0);
            }
        }
        
        if (dy < 0) {
            if (point1.getBlockY() < point2.getBlockY()) {
                point1.add(0, dy, 0);
            } else {
                point2.add(0, dy, 0);
            }
        } else {
            if (point1.getBlockY() < point2.getBlockY()) {
                point2.add(0, dy, 0);
            } else {
                point1.add(0, dy, 0);
            }
        }
        
        if (dz < 0) {
            if (point1.getBlockZ() < point2.getBlockZ()) {
                point1.add(0, 0, dz);
            } else {
                point2.add(0, 0, dz);
            }
        } else {
            if (point1.getBlockZ() < point2.getBlockZ()) {
                point2.add(0, 0, dz);
            } else {
                point1.add(0, 0, dz);
            }
        }
    }
    
    public double getDistance(Location l) {
        double maxX = Math.max(point1.getX(), point2.getX());
        double minX = Math.min(point1.getX(), point2.getX());
        double maxY = Math.max(point1.getY(), point2.getY());
        double minY = Math.min(point1.getY(), point2.getY());
        double maxZ = Math.max(point1.getZ(), point2.getZ());
        double minZ = Math.min(point1.getZ(), point2.getZ());
        double dx = Math.max(Math.max(minX - l.getX(), l.getX() - maxX), 0);
        double dy = Math.max(Math.max(minY - l.getY(), l.getY() - maxY), 0);
        double dz = Math.max(Math.max(minZ - l.getZ(), l.getZ() - maxZ), 0);
        return Math.sqrt(dx * dx + dy * dy + dz * dz);
    }

    public Location getCenter() {
        double cX = (point1.getX() + point2.getX())/2.;
        double cY = (point1.getY() + point2.getY())/2.;
        double cZ = (point1.getZ() + point2.getZ())/2.;
        return new Location(point1.getWorld(), cX, cY, cZ);
    }

    public Location getLowerCorner() {
        return new Location(point1.getWorld(), Math.min(point1.getBlockX(), point2.getBlockX()),
                Math.min(point1.getBlockY(), point2.getBlockY()),
                Math.min(point1.getBlockZ(), point2.getBlockZ()));
    }

    public AnimationsLocation getPoint1() {
        return point1;
    }

    public AnimationsLocation getPoint2() {
        return point2;
    }

    public void save(ConfigurationSection config) {
        ConfigurationSection section = config.createSection("Selection");
        point1.save("Point1",section);
        point2.save("Point2",section);
    }

    public static Selection load(ConfigurationSection config) {
        ConfigurationSection section = config.getConfigurationSection("Selection");
        Selection selection = new Selection();
        selection.point1 = (AnimationsLocation.load("Point1",section));
        selection.point2 = (AnimationsLocation.load("Point2",section));
        return selection;
    }
}
