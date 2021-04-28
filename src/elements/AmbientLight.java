package elements;

import primitives.Color;

public class AmbientLight {

    private Color intensity;

    public AmbientLight(Color Ia, double Ka) {
        if(Ka<0 || Ka>1)
            return;
        intensity=new Color(Ia.scale(Ka));
    }

    public Color getIntensity() {
        return intensity;
    }

}
