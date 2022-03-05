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
            Animation animation = null;
            switch(type) {
                case STATIONARY:
                    animation = StationaryAnimation.load(config);
                    break;
                case MOVING:
                    animation = MovingAnimation.load(config);
            }
            animation.setName(file.getParent());
            return animation;
        } catch (IOException | InvalidConfigurationException | InvalidSelectionException e) {
            e.printStackTrace();
        }
        return null;
    }
}