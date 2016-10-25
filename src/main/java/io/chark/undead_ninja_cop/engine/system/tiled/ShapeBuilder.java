package io.chark.undead_ninja_cop.engine.system.tiled;

import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.MapObjects;
import com.badlogic.gdx.maps.objects.CircleMapObject;
import com.badlogic.gdx.maps.objects.PolygonMapObject;
import com.badlogic.gdx.maps.objects.PolylineMapObject;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import io.chark.undead_ninja_cop.core.config.Configuration;

import java.util.ArrayList;
import java.util.List;

public final class ShapeBuilder {

    private final float ppm = Configuration.getInstance()
            .getGameplay()
            .getPpm();

    ShapeBuilder() {
    }

    /**
     * Create a list of shapes from given map objects.
     *
     * @return list of shapes.
     */
    public List<Shape> build(MapObjects mapObjects) {
        List<Shape> shapes = new ArrayList<>();
        for (MapObject object : mapObjects) {
            Shape shape = object instanceof RectangleMapObject
                    ? create(((RectangleMapObject) object))
                    : object instanceof PolygonMapObject
                    ? create(((PolygonMapObject) object))
                    : object instanceof PolylineMapObject
                    ? create(((PolylineMapObject) object))
                    : object instanceof CircleMapObject
                    ? create(((CircleMapObject) object))
                    : null;

            if (shape == null) {
                continue;
            }
            shapes.add(shape);
        }
        return shapes;
    }

    /**
     * Create shape from rectangle.
     *
     * @return polygon shape.
     */
    private PolygonShape create(RectangleMapObject rectangleObject) {
        Rectangle rectangle = rectangleObject.getRectangle();
        PolygonShape polygon = new PolygonShape();
        Vector2 size = new Vector2((rectangle.x + rectangle.width * 0.5f) / ppm,
                (rectangle.y + rectangle.height * 0.5f) / ppm);
        polygon.setAsBox(rectangle.width * 0.5f / ppm,
                rectangle.height * 0.5f / ppm,
                size,
                0.0f);

        return polygon;
    }

    /**
     * Create shape from circle.
     *
     * @return circle shape.
     */
    private CircleShape create(CircleMapObject circleObject) {
        Circle circle = circleObject.getCircle();
        CircleShape circleShape = new CircleShape();
        circleShape.setRadius(circle.radius / ppm);
        circleShape.setPosition(new Vector2(circle.x / ppm, circle.y / ppm));
        return circleShape;
    }

    /**
     * Create shape from polygon.
     *
     * @return polygon shape.
     */
    private PolygonShape create(PolygonMapObject polygonObject) {
        PolygonShape polygon = new PolygonShape();
        float[] vertices = polygonObject.getPolygon().getTransformedVertices();

        float[] worldVertices = new float[vertices.length];

        for (int i = 0; i < vertices.length; i++) {
            System.out.println(vertices[i]);
            worldVertices[i] = vertices[i] / ppm;
        }

        polygon.set(worldVertices);
        return polygon;
    }

    /**
     * Create shape from polyline.
     *
     * @return chain shape.
     */
    private ChainShape create(PolylineMapObject polylineObject) {
        float[] vertices = polylineObject.getPolyline().getTransformedVertices();

        Vector2[] worldVertices = new Vector2[vertices.length / 2];
        for (int i = 0; i < vertices.length / 2; i++) {
            worldVertices[i] = new Vector2();
            worldVertices[i].x = vertices[i * 2] / ppm;
            worldVertices[i].y = vertices[i * 2 + 1] / ppm;
        }

        ChainShape chain = new ChainShape();
        chain.createChain(worldVertices);
        return chain;
    }
}