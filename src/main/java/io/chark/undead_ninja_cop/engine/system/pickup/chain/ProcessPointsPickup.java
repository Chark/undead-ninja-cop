package io.chark.undead_ninja_cop.engine.system.pickup.chain;

import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.math.MathUtils;
import io.chark.undead_ninja_cop.core.config.Configuration;
import io.chark.undead_ninja_cop.engine.component.Pickup;
import io.chark.undead_ninja_cop.engine.component.player.Player;
import io.chark.undead_ninja_cop.engine.system.pickup.TouchPickupEvent;

public class ProcessPointsPickup implements TouchPickupEventChain {

    private static final float SOUND_VOLUME = Configuration
            .getInstance()
            .getSettings()
            .getSoundVolume();

    private final Sound pointsSound;
    private final TouchPickupEventChain next;

    public ProcessPointsPickup(Sound pointsSound,
                               TouchPickupEventChain next) {

        this.pointsSound = pointsSound;
        this.next = next;
    }

    @Override
    public void process(TouchPickupEvent event) {
        if (Pickup.Type.POINTS == event.getPickup().getType()) {
            Player player = event.getPlayer();

            player.setPoints(player.getPoints() + event.getPickup().getValue());
            pointsSound.play(SOUND_VOLUME, MathUtils.random(0.9f, 1f), 0);

        } else if (next != null) {
            next.process(event);
        }
    }
}