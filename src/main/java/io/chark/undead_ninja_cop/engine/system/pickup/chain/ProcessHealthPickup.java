package io.chark.undead_ninja_cop.engine.system.pickup.chain;

import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.math.MathUtils;
import io.chark.undead_ninja_cop.core.config.Configuration;
import io.chark.undead_ninja_cop.engine.component.Pickup;
import io.chark.undead_ninja_cop.engine.component.player.Player;
import io.chark.undead_ninja_cop.engine.system.pickup.TouchPickupEvent;

public class ProcessHealthPickup implements TouchPickupEventChain {

    private static final int MAX_HEALTH = 100;
    private static final float SOUND_VOLUME = Configuration
            .getInstance()
            .getSettings()
            .getSoundVolume();

    private final Sound healthSound;
    private final TouchPickupEventChain next;

    public ProcessHealthPickup(Sound healthSound,
                               TouchPickupEventChain next) {

        this.healthSound = healthSound;
        this.next = next;
    }

    @Override
    public void process(TouchPickupEvent event) {
        if (Pickup.Type.HEALTH == event.getPickup().getType()) {
            Player player = event.getPlayer();

            int health = player.getHealth() + event.getPickup().getValue();
            if (health > MAX_HEALTH) {
                health = MAX_HEALTH;
            }

            if (health <= 0) {
                // ded :(
            }

            healthSound.play(SOUND_VOLUME, MathUtils.random(0.9f, 1f), 0);
            player.setHealth(health);

        } else if (next != null) {
            next.process(event);
        }
    }
}
