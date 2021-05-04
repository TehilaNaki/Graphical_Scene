package elements;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.ParserConfigurationException;
import geometries.Geometries;
import geometries.Sphere;
import geometries.Triangle;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.w3c.dom.Node;
import org.w3c.dom.Element;
import org.xml.sax.SAXException;
import primitives.Color;
import primitives.Point3D;
import scene.Scene;
import java.io.File;
import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ReadXMLFile {

    /**
     * the function get a file name in xml, read it and make a scene from the data
     * @param scene_Name the scene name
     * @param file_name the xml file name
     * @return the scene of the file
     */
    public static Scene ReadFile(String scene_Name, String file_name) {

        Scene scene = new Scene(scene_Name);
        File xmlFile = new File(file_name);

        DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder dBuilder;
        try {

            dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.parse(xmlFile);
            doc.getDocumentElement().normalize();
            Element node = doc.getDocumentElement();

            //get the background color and set in the scene
            Point3D backColor = getP(node.getAttribute("background-color"));
            Color Cb = new Color(backColor.getX(), backColor.getY(), backColor.getZ());
            scene.setBackground(Cb);

            //get the ambient light color and set in the scene
            Point3D ambientColor = getP(((Element) doc.getElementsByTagName("ambient-light").item(0)).getAttribute("color"));
            Color Ca = new Color(ambientColor.getX(), ambientColor.getY(), ambientColor.getZ());
            scene.setAmbientLight(new AmbientLight(Ca, 1));

            //new geometries for the scene
            Geometries geoList = new Geometries();

            //get all the triangle
            NodeList nodeList = doc.getElementsByTagName("triangle");
            for (int i = 0; i < nodeList.getLength(); i++) {
                geoList.add(getTriangle(nodeList.item(i)));
            }

            //get all the sphere
            nodeList = doc.getElementsByTagName("sphere");
            for (int i = 0; i < nodeList.getLength(); i++) {
                geoList.add(getSphere(nodeList.item(i)));
            }

            //set the geometries in the scene
            scene.setGeometries(geoList);

        }
        catch (SAXException | ParserConfigurationException | IOException e1) {
            e1.printStackTrace();
        }

        return scene;
    }

    /**
     * the function create a triangle from the node
     * @param node
     * @return Triangle
     */
    private static Triangle getTriangle(Node node) {

        Triangle geo = null;
        if (node.getNodeType() == Node.ELEMENT_NODE) {
            Element element = (Element) node;
            Point3D p0 = getP(((Element) node).getAttribute("p0"));
            Point3D p1 = getP(((Element) node).getAttribute("p1"));
            Point3D p2 = getP(((Element) node).getAttribute("p2"));
            geo = new Triangle(p0, p1, p2);
        }
        return geo;
    }

    /**
     * the function create a sphere from the node
     * @param node
     * @return Sphere
     */
    private static Sphere getSphere(Node node) {

        Sphere geo=null;
        if (node.getNodeType() == Node.ELEMENT_NODE) {

            Element element = (Element) node;
            Point3D p0 = getP(((Element) node).getAttribute("center"));
           int r= Integer.parseInt( ((Element) node).getAttribute("radius"));
           geo=new Sphere(p0,r);
        }
        return geo;
    }

    private static String getTagValue(String tag, Element element) {
        NodeList nodeList = element.getElementsByTagName(tag).item(0).getChildNodes();
        Node node = (Node) nodeList.item(0);
        return node.getNodeValue();
    }

    /**
     * the function return point from string point
     * @param s string
     * @return Point
     */
    public static Point3D getP(String s) {

        Pattern p = Pattern.compile("-?\\d+");
        Matcher m = p.matcher(s);
        int[] arr = new int[3];
        int i = 0;

        while (m.find()) {
            arr[i++] = Integer.parseInt(m.group());
        }
        return new Point3D(arr[0], arr[1], arr[2]);
    }
}


