package scene;

import elements.AmbientLight;
import geometries.Geometries;
import primitives.Color;

public class Scene {

    public String name;
    public Color background=Color.BLACK;
    public AmbientLight ambientLight=new AmbientLight(new Color(192,192,192),1);
    public Geometries geometries=null;

    public Scene(String name) {
        this.name = name;
        geometries=new Geometries();
    }

    public Scene setBackground(Color background) {
        this.background = background;
        return this;
    }

    public Scene setAmbientLight(AmbientLight ambientLight) {
        this.ambientLight = ambientLight;
        return this;
    }

    public Scene setGeometries(Geometries geometries) {
        this.geometries = geometries;
        return this;
    }
}
