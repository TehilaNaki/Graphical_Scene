package elements;

import primitives.Color;

/**
 * AmbientLight of the scene
 */
public class AmbientLight extends Light{


    /**
     * create AmbientLight of the scene
     * @param Ia the color of ambientLight
     * @param Ka factor of the ambientLight
     */
    public AmbientLight(Color Ia , double Ka) {
        super(Ia.scale(Ka));
    }

    /**
     * default constructor that create ambientLight in black
     */
    public AmbientLight(){
        super(Color.BLACK);
    }


}
