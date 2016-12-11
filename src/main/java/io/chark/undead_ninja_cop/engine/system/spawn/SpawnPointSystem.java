package io.chark.undead_ninja_cop.engine.system.spawn;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.physics.box2d.Shape;
import com.badlogic.gdx.physics.box2d.World;
import io.chark.undead_ninja_cop.core.BaseGameSystem;
import io.chark.undead_ninja_cop.core.Component;
import io.chark.undead_ninja_cop.core.Entity;
import io.chark.undead_ninja_cop.core.util.Components;
import io.chark.undead_ninja_cop.engine.component.BasicRenderable;
import io.chark.undead_ninja_cop.engine.component.SpawnPoint;
import io.chark.undead_ninja_cop.engine.component.Transform;
import io.chark.undead_ninja_cop.engine.component.physics.FixtureBuilder;
import io.chark.undead_ninja_cop.engine.component.physics.PhysicsBuilder;
import io.chark.undead_ninja_cop.engine.component.player.Player;

import java.util.Arrays;
import java.util.Set;

public class SpawnPointSystem extends BaseGameSystem {

    private static final Set<Class<? extends Component>> TYPES = Components
            .toSet(Transform.class, SpawnPoint.class);

    private static final String PLAYER_TEXTURE = "player.png";
    private static final float HEIGHT = 15;
    private static final float WIDTH = 5;

    private final World world;

    // todo use messaging for this stuff.
    private boolean spawned = false;

    public SpawnPointSystem(World world) {
        this.world = world;
    }

    @Override
    public void updateEntities(float dt) {
        if (!spawned) {
            for (Entity entity : entities) {
                SpawnPoint point = entityManager.getComponent(entity, SpawnPoint.class);

                // Spawn players.
                if (SpawnPoint.Type.PLAYER.equals(point.getType())) {

                    // Transform, where the player should be spawned.
                    Transform spawn = entityManager.getComponent(entity, Transform.class);

                    // Create a new player on the spawn point.
                    Texture playerTexture = resourceLoader.getTexture(PLAYER_TEXTURE);
                    Player player = new Player();

                    entityManager.createEntity(Arrays.asList(
                            new BasicRenderable(playerTexture, 0, -WIDTH / 2),

                            // Player transform scale has to be calculated based on texture size.
                            new Transform(
                                    WIDTH / playerTexture.getWidth() * 2,
                                    (HEIGHT + WIDTH / 2) / playerTexture.getHeight() * 2),
                            player,
                            PhysicsBuilder
                                    .usingWorld(world)
                                    .dynamic()
                                    .bullet()
                                    .fixedRotation()
                                    .userData(player)
                                    .position(spawn.getX(), spawn.getY())
                                    .addFixture(FixtureBuilder.builder()
                                            .density(1)
                                            .dimensions(WIDTH, HEIGHT)
                                            .build(Shape.Type.Polygon))
                                    .addFixture(Player.Part.FEET, FixtureBuilder.builder()
                                            .radius(WIDTH)
                                            .position(0, -HEIGHT)
                                            .build(Shape.Type.Circle))
                                    .build()));

                    spawned = true;
                }
            }
        }
    }

    @Override
    public Set<Class<? extends Component>> getComponentTypes() {
        return TYPES;
    }
}