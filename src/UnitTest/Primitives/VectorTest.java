package UnitTest.Primitives;

import org.testng.annotations.Test;
import primitives.Vector;

import static org.junit.Assert.*;
import static primitives.Util.isZero;

/**
 * Unit tests for {@link primitives.Vector} class.
 */
public class VectorTest {

    /**
     * Test method for {@link primitives.Vector#add(Vector)}.
     */
    @Test
    public void testAdd() {
        // ============ Equivalence Partitions Tests ==============
        Vector v0=new Vector(2,-1,4);
        Vector v1=new Vector(3,2,4);

        assertEquals("add() wrong result of adding",v0.add(v1),new Vector(5,1,8));

        // =============== Boundary Values Tests ==================
        //not checks the Zero vector because can not build the zero vector
    }

    /**
     * Test method for {@link primitives.Vector#subtract(Vector)}.
     */
    @Test
    public void testSubtract() {
        // ============ Equivalence Partitions Tests ==============
        Vector v0=new Vector(1,3,-2);
        Vector v1=new Vector(-4,2,-7);

        assertEquals("Subtract() wrong result of sub",v0.subtract(v1),new Vector(5,1,5));

        // =============== Boundary Values Tests ==================
        //sub vector by itself
        try {
            assertTrue("Subtract() wrong result of sub vector by itself",v0.subtract(v0).IsZero());
            fail("Failed, didn't throw zero exception!");
        } catch ( IllegalArgumentException ignored) {}
    }
    /**
     * Test method for {@link primitives.Vector#scale(double)}.
     */
    @Test
    public void testScale() {
        // ============ Equivalence Partitions Tests ==============
        Vector v0=new Vector(1,-3,-2);

        //multiply in number<0
        assertEquals("scale() wrong result",new Vector(-1,3,2),v0.scale(-1));

        //multiply in 0<number<1
        assertEquals("scale() wrong result",new Vector(0.5,-1.5,-1),v0.scale(0.5));

        //multiply in 1<number
        assertEquals("scale() wrong result",new Vector(5,-15,-10),v0.scale(5));


        // =============== Boundary Values Tests ==================
       //multiply in zero
        try {
            assertTrue("scale() wrong result",v0.scale(0).IsZero());
            fail("Didn't throw zero exception!");
        } catch ( IllegalArgumentException ignored) {}
        //multiply in 1
            assertEquals("scale() wrong result",v0.scale(1),v0);
    }
    /**
     * Test method for {@link primitives.Vector#dotProduct(Vector)}.
     */
    @Test
    public void testDotProduct() {

        Vector v1 = new Vector(1, 2, 3);
        Vector v2 = new Vector(-2, -4, -6);
        Vector v3 = new Vector(1, -5, 0);
        Vector v4 = new Vector(3, 6, 9);
        Vector v5 = new Vector(-5, -1, 0);
        Vector v6 = new Vector(0, 0, 4);

       // ============ Equivalence Partitions Tests ==============
        assertTrue("ERROR: dotProduct() wrong value in opposite direction",isZero(v1.dotProduct(v2) + 28));//opposite direction
        assertTrue("ERROR: dotProduct() wrong value in same direction",isZero(v1.dotProduct(v4)-42));//same direction
        assertTrue("ERROR: dotProduct() wrong value in acute angle",isZero(v1.dotProduct(v6)-12));//acute angle
        assertTrue("ERROR: dotProduct() wrong value in obtuse angle",isZero(v1.dotProduct(v5)+7));//obtuse angle

        // =============== Boundary Values Tests ==================
       assertTrue("ERROR: dotProduct() for orthogonal vectors is not zero",isZero(v5.dotProduct(v3)));//orthogonal vectors
        assertTrue("ERROR: dotProduct() of vector with himself",isZero(v1.dotProduct(v1)-14));//same vector
    }

    /**
     * Test method for {@link primitives.Vector#crossProduct(Vector)}.
     */
    @Test
    public void testCrossProduct() {


        Vector v1 = new Vector(1, 2, 3);
        Vector v2 = new Vector(0, 3, -2);
        Vector v3 = new Vector(-2, -4, -6);
        Vector v4 = new Vector(-5, -1, 0);
        Vector v5 = new Vector(0, 0, 4);

        // ============ Equivalence Partitions Tests ==============

        Vector vr = v1.crossProduct(v2);

        // TC01: Test that length of cross-product is proper (orthogonal vectors taken for simplicity)
        assertEquals("crossProduct() wrong result length", v1.length() * v2.length(), vr.length(), 0.00001);

        // TC02: Test cross-product result orthogonality to its operands
        assertTrue("crossProduct() result is not orthogonal to 1st operand", isZero(vr.dotProduct(v1)));
        assertTrue("crossProduct() result is not orthogonal to 2nd operand", isZero(vr.dotProduct(v2)));

        assertFalse("ERROR: dotProduct() wrong value in opposite direction", v1.crossProduct(v2).IsZero());//opposite direction
        assertFalse("ERROR: dotProduct() wrong value in acute angle", v1.crossProduct(v5).IsZero());//acute angle
        assertFalse("ERROR: dotProduct() wrong value in obtuse angle", v1.crossProduct(v4).IsZero());//obtuse angle

        // =============== Boundary Values Tests ==================
        //check if the vectors is parallel
         try {
             v1.crossProduct(v3);
             fail("crossProduct() for parallel vectors does not throw an exception");
         } catch (IllegalArgumentException ignored) {}

        try {//same vector
            vr=v1.crossProduct(v1);
            assertTrue("ERROR: crossProduct() of vector with himself",vr.IsZero());
            fail("Didn't throw parallel exception!");
        } catch ( IllegalArgumentException ignored) {}

    }
    /**
     * Test method for {@link Vector#length()}.
     */
    @Test
    public void testLength() {

        Vector v1 = new Vector(1, 2, 3);

        assertEquals("ERROR: lengthSquared() wrong value",v1.lengthSquared(),14,0.00001);
        assertEquals("ERROR: length() wrong value",new Vector(0, 3, 4).length() , 5,0.00001);
    }
    /**
     * Test method for {@link Vector#normalize()}.
     */
    @Test
    public void testNormalize() {

        Vector v = new Vector(1, 2, 3);
        Vector vCopy = new Vector(v.getHead());

        //check if the normalize function creates a new vector
        assertSame("ERROR: normalize() function creates a new vector", vCopy, vCopy.normalize());
        //check if the vector returned is the unit vector
        assertTrue("ERROR: normalize() result is not a unit vector",isZero(vCopy.normalize().length() - 1));

    }
    /**
     * Test method for {@link Vector#normalize()}.
     */
    @Test
    public void testNormalized() {

        Vector v = new Vector(1, 2, 3);
        //check if normalized function creates a new vector

        assertNotSame("ERROR: normalized() function does not create a new vector", v.normalized(), v);
    }
}