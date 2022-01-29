package com.ivan1pl.animations.data;

import com.ivan1pl.animations.exceptions.InvalidSelectionException;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;

public class AnimationFactory {

    public static Animation loadAnimation(File file) {
        YamlConfiguration config = new YamlConfiguration();
        try {
            config.load(file);
            AnimationType type = AnimationType.valueOf(config.getString("AnimationType"));
            switch(type) {
                case STATIONARY:
                    return StationaryAnimation.load(config);
                case MOVING:
                    return MovingAnimation.load(config);
            }
        } catch (IOException | InvalidConfigurationException | InvalidSelectionException e) {
            e.printStackTrace();
        }
        return null;
    }
}
