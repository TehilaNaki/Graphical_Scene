package UnitTest;

import org.testng.annotations.Test;
import primitives.Point3D;
import primitives.Vector;

import static org.junit.Assert.*;

public class VectorTest {

    @Test
    public void testAdd() {
        // ============ Equivalence Partitions Tests ==============
        Vector v0=new Vector(2,-1,4);
        Vector v1=new Vector(3,2,4);
        Vector v2=v0.add(v1);
        Point3D v=new Point3D(5,1,8);
        assertEquals("add() wrong result of adding",v2,v);
    }

    @Test
    public void testSubtract() {
    }

    @Test
    public void testScale() {
    }

    @Test
    public void testDotProduct() {
    }

    @Test
    public void testCrossProduct() {
    }

    @Test
    public void testLengthSquared() {
    }

    @Test
    public void testLength() {
    }

    @Test
    public void testNormalize() {
    }

    @Test
    public void testNormalized() {
    }
}