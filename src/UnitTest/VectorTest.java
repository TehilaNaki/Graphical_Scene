package UnitTest;

import org.testng.annotations.Test;
import primitives.Point3D;
import primitives.Vector;

import static java.lang.System.out;
import static org.junit.Assert.*;
import static org.testng.Assert.assertThrows;
import static primitives.Util.isZero;

public class VectorTest {

    @Test
    public void testAdd() {
        // ============ Equivalence Partitions Tests ==============
        Vector v0=new Vector(2,-1,4);
        Vector v1=new Vector(3,2,4);
        Vector v2=v0.add(v1);
        Vector v=new Vector(5,1,8);
        assertEquals("add() wrong result of adding",v2,v);
        // =============== Boundary Values Tests ==================
        //not checks the Zero vector because can not build the zero vector
    }

    @Test
    public void testSubtract() {
        // ============ Equivalence Partitions Tests ==============
        Vector v0=new Vector(1,3,-2);
        Vector v1=new Vector(-4,2,-7);
        Vector v2=new Vector(5,1,5);
        Vector v=v0.subtract(v1);
        assertEquals("Subtract() wrong result of sub",v,v2);

        // =============== Boundary Values Tests ==================
        //sub vector by itself
        try {
             v=v2.subtract(v2);
            assertTrue("Subtract() wrong result of sub vector by itself",v.IsZero());
            fail("Didn't throw zero exception!");
        } catch ( IllegalArgumentException e) {
            assertTrue(true);
        }
    }

    @Test
    public void testScale() {
        // ============ Equivalence Partitions Tests ==============

        Vector v0=new Vector(1,-3,-2);
        Vector v1=new Vector(-1,3,2);
        Vector v2=new Vector(0.5,-1.5,-1);
        Vector v3=new Vector(5,-15,-10);

        //multiply in number<0
        Vector v=v0.scale(-1);
        assertEquals("scale() wrong result",v1,v);

        //multiply in 0<number<1
         v=v0.scale(0.5);
        assertEquals("scale() wrong result",v2,v);

        //multiply in 1<number
         v=v0.scale(5);
        assertEquals("scale() wrong result",v3,v);


        // =============== Boundary Values Tests ==================
       //multiply in zero
        try {
             v=v0.scale(0);
            assertTrue("scale() wrong result",v.IsZero());
            fail("Didn't throw zero exception!");
        } catch ( IllegalArgumentException e) {
            assertTrue(true);
        }
        //multiply in 1
             v=v0.scale(1);
            assertEquals("scale() wrong result",v,v0);
    }

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

    @Test
    public void testCrossProduct() {


        Vector v1 = new Vector(1, 2, 3);
        Vector v2 = new Vector(0, 3, -2);
        Vector v3 = new Vector(-2, -4, -6);
        // ============ Equivalence Partitions Tests ==============

        Vector vr = v1.crossProduct(v2);

        // TC01: Test that length of cross-product is proper (orthogonal vectors taken for simplicity)
        assertEquals("crossProduct() wrong result length", v1.length() * v2.length(), vr.length(), 0.00001);

        // TC02: Test cross-product result orthogonality to its operands
        assertTrue("crossProduct() result is not orthogonal to 1st operand", isZero(vr.dotProduct(v1)));
        assertTrue("crossProduct() result is not orthogonal to 2nd operand", isZero(vr.dotProduct(v2)));

        // =============== Boundary Values Tests ==================
         try {
             v1.crossProduct(v3);
             fail("crossProduct() for parallel vectors does not throw an exception");
         } catch (Exception ignored) {}

    }

    @Test
    public void testLength() {

        Vector v1 = new Vector(1, 2, 3);
        assertEquals("ERROR: lengthSquared() wrong value",v1.lengthSquared(),14,0.00001);
        assertEquals("ERROR: length() wrong value",new Vector(0, 3, 4).length() , 5,0.00001);
    }

    @Test
    public void testNormalize() {

        Vector v = new Vector(1, 2, 3);
        Vector vCopy = new Vector(v.getHead());
        Vector vCopyNormalize = vCopy.normalize();
        //check if the normalize function creates a new vector
        assertSame("ERROR: normalize() function creates a new vector", vCopy, vCopyNormalize);
        //check if the vector returned is the unit vector
        assertTrue("ERROR: normalize() result is not a unit vector",isZero(vCopyNormalize.length() - 1));


    }

    @Test
    public void testNormalized() {

        Vector v = new Vector(1, 2, 3);
        //check if normalized function creates a new vector
        Vector u = v.normalized();
        assertNotSame("ERROR: normalized() function does not create a new vector", u, v);
    }
}