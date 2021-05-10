package elements;

import primitives.Color;

/**
 * Light class with intensity color
 */
 abstract class Light {

    private Color intensity;

    /**
     * create the intensity of the light
     * @param intensity of the light
     */
    protected Light(Color intensity) {
        this.intensity = intensity;
    }

    /**
     * Returns intensity of the light.
     * @return A shallow copy of the color.
     */
    public Color getIntensity() {
        return intensity;
    }


}
