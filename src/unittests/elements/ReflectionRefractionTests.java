package unittests.elements;

import geometries.Plane;
import geometries.Polygon;
import geometries.Tube;
import org.junit.Test;

import elements.*;
import geometries.Sphere;
import geometries.Triangle;
import primitives.*;
import primitives.Color;
import renderer.*;
import scene.Scene;

import java.awt.*;
import java.io.File;
import java.io.IOException;

/**
 * Tests for reflection and transparency functionality, test for partial shadows
 * (with transparency)
 *
 * @author dzilb
 *
 */
public class ReflectionRefractionTests {

    /**
     * Produce a picture of a sphere lighted by a spot light
     */
    @Test
    public void twoSpheres() {
        Scene scene = new Scene("Test scene");
        scene.setCamera(new Camera(new Point3D(0, 0, -1000), new Vector(0, 0, 1), new Vector(0, -1, 0)));
        scene.setDistance(1000);
        scene.setBackground(Color.BLACK);
        scene.setAmbientLight(new AmbientLight(Color.BLACK, 0));

        scene.addGeometries(
                new Sphere(new Color(java.awt.Color.BLUE), new Material(0.4, 0.3, 100, 0.3, 0), 50,
                        new Point3D(0, 0, 50)),
                new Sphere(new Color(java.awt.Color.RED), new Material(0.5, 0.5, 100), 25, new Point3D(0, 0, 50)));

        scene.addLights(new SpotLight(new Color(1000, 600, 0), new Point3D(-100, 100, -500), new Vector(-1, 1, 2), 1,
                0.0004, 0.0000006));

        ImageWriter imageWriter = new ImageWriter("twoSpheres", 150, 150, 500, 500);
        Render render = new Render(imageWriter, scene);

        render.renderImage(false, false, false);
        render.writeToImage();
    }



    /**
     * Produce a picture of a sphere lighted by a spot light
     */
    @Test
    public void twoSpheresOnMirrors() {
        Scene scene = new Scene("Test scene");
        scene.setCamera(new Camera(
                new Point3D(0, 0, -10000),
                new Vector(0, 0, 1),
                new Vector(0, -1, 0)));
        scene.setDistance(10000);
        scene.setBackground(Color.BLACK);
        scene.setAmbientLight(new AmbientLight(new Color(255, 255, 255), 0.1));

        scene.addGeometries(
                new Sphere(
                        new Color(0, 0, 100),
                        new Material(0.25, 0.25, 20, 0.5, 0), 400,
                        new Point3D(-950, 900, 1000)),
                new Sphere(
                        new Color(100, 20, 20),
                        new Material(0.25, 0.25, 20), 200,
                        new Point3D(-950, 900, 1000)),
                new Triangle(
                        new Color(20, 20, 20),
                        new Material(0, 0, 0, 0, 1),
                        new Point3D(1500, 1500, 1500),
                        new Point3D(-1500, -1500, 1500),
                        new Point3D(670, -670, -3000)),
                new Triangle(
                        new Color(20, 20, 20),
                        new Material(0, 0, 0, 0, 0.5),
                        new Point3D(1500, 1500, 1500),
                        new Point3D(-1500, -1500, 1500),
                        new Point3D(-1500, 1500, 2000)));

        scene.addLights(new SpotLight(new Color(1020, 400, 400), new Point3D(-750, 750, 150),
                new Vector(-1, 1, 4), 1, 0.00001, 0.000005));

        ImageWriter imageWriter = new ImageWriter("twoSpheresMirrored", 2500, 2500, 500, 500);
        Render render = new Render(imageWriter, scene);

        render.renderImage(false, false, false);
        render.writeToImage();
    }





    /**
     * Produce a picture of a two triangles lighted by a spot light with a partially transparent Sphere
     *  producing partial shadow
     */
    @Test
    public void trianglesTransparentSphere() {
        Scene scene = new Scene("Test scene");
        scene.setCamera(new Camera(new Point3D(0, 0, -1000), new Vector(0, 0, 1), new Vector(0, -1, 0)));
        scene.setDistance(1000);
        scene.setBackground(Color.BLACK);
        scene.setAmbientLight(new AmbientLight(new Color(java.awt.Color.WHITE), 0.15));

        scene.addGeometries( //
                new Triangle(Color.BLACK, new Material(0.5, 0.5, 60), //
                        new Point3D(-150, 150, 115), new Point3D(150, 150, 135), new Point3D(75, -75, 150)), //
                new Triangle(Color.BLACK, new Material(0.5, 0.5, 60), //
                        new Point3D(-150, 150, 115), new Point3D(-70, -70, 140), new Point3D(75, -75, 150)), //
                new Sphere(new Color(java.awt.Color.BLUE), new Material(0.2, 0.2, 30, 0.6, 0), // )
                        30, new Point3D(60, -50, 50)));

        scene.addLights(new SpotLight(new Color(700, 400, 400), //
                new Point3D(60, -50, 0), new Vector(0, 0, 1), 1, 4E-5, 2E-7));

        ImageWriter imageWriter = new ImageWriter("shadow with transparency", 200, 200, 600, 600);
        Render render = new Render(imageWriter, scene);

        render.renderImage(false, false, false);
        render.writeToImage();
    }

    /**
     * Produce a picture of a many geometries lighted by a directional light with refraction and reflection
     */
    @Test
    public void coronaSphere() {
        Scene scene = new Scene("Test scene");
        scene.setCamera(new Camera(
                new Point3D(0, 0, -200),
                new Vector(0, 0, 1),
                new Vector(0, -1, 0)));
        scene.setDistance(200);
        scene.setBackground(Color.BLACK);
        scene.setAmbientLight(new AmbientLight(new Color(java.awt.Color.black), 0.1));


        scene.addGeometries(new Plane(
                new Color(java.awt.Color.black),
                new Material(0, 1, 150, 0.2, 0.8),
                new Point3D(0, 0, 110),
                new Vector(0, -0.724, 0.6896)
        ));

        scene.addGeometries(new Plane(
                new Color(29, 25, 37),
                new Material(0, 1, 150, 0.2, 0.8),
                new Point3D(0, 0, 110),
                new Vector(-0.0453, -0.417, -0.907)
        ));


        scene.addGeometries(
                new Tube(
                        new Color(new Color(221, 160, 221)),
                        new Material(0.3, 0.3, 250), 2,
                        new Ray(new Point3D(0, 0, 5),
                                new Vector(2, 2, 2))));


        scene.addGeometries(
                new Triangle(
                        new Color(java.awt.Color.RED),
                        new Material(0, 0, 0, 0, 1),
                        new Point3D(23, 0, 1),
                        new Point3D(23, 30, 1),
                        new Point3D(10, 0, 1)));

        scene.addGeometries(
                new Triangle(
                        new Color(java.awt.Color.red),
                        new Material(0, 0, 0, 0, 0.5),
                        new Point3D(-23, 0, 1),
                        new Point3D(-23, 30, 1),
                        new Point3D(-10, 0, 1)));


        scene.addGeometries(new Sphere(
                new Color(java.awt.Color.green),
                new Material(0.5, 0.5, 100), 3.5,
                new Point3D(-15, 0, 3)));

        scene.addGeometries(new Sphere(
                new Color(java.awt.Color.pink),
                new Material(0.5, 0.5, 100), 3.5,
                new Point3D(15, 0, 3)));


        scene.addGeometries(new Sphere(
                new Color(java.awt.Color.green),
                new Material(0.5, 0.5, 100), 3.5,
                new Point3D(-15, 0, 3)));


        //********************************** Spheres around The big sphere ************************************//
        scene.addGeometries(new Sphere(
                new Color(java.awt.Color.red),
                new Material(0.5, 0.5, 100), 5,
                new Point3D(24.89, 43.36, 50)));

        scene.addGeometries(new Sphere(
                new Color(java.awt.Color.YELLOW),
                new Material(0.5, 0.5, 100), 5,
                new Point3D(48.39, 12.57, 50)));

        scene.addGeometries(new Sphere(
                new Color(java.awt.Color.green),
                new Material(0.5, 0.5, 100), 8,
                new Point3D(39.83, 30.23, 50)));

        scene.addGeometries(new Sphere(
                new Color(java.awt.Color.BLUE),
                new Material(0.5, 0.5, 100), 8,
                new Point3D(49.28, -8.44, 50)));

        scene.addGeometries(new Sphere(
                new Color(java.awt.Color.red),
                new Material(0.5, 0.5, 100), 5,
                new Point3D(42.12, -26.94, 50)));

        scene.addGeometries(new Sphere(
                new Color(java.awt.Color.green),
                new Material(0.5, 0.5, 100), 8,
                new Point3D(27.94, -41.47, 50)));

        scene.addGeometries(new Sphere(
                new Color(java.awt.Color.YELLOW),
                new Material(0.5, 0.5, 100), 5,
                new Point3D(7.91, -49.37, 50)));

        scene.addGeometries(new Sphere(
                new Color(java.awt.Color.blue),
                new Material(0.5, 0.5, 100), 8,
                new Point3D(-13.67, -48.1, 50)));

        scene.addGeometries(new Sphere(
                new Color(java.awt.Color.red),
                new Material(0.5, 0.5, 100), 5,
                new Point3D(-31.16, -39.11, 50)));

        scene.addGeometries(new Sphere(
                new Color(java.awt.Color.yellow),
                new Material(0.5, 0.5, 100), 8,
                new Point3D(-43.4, -24.83, 50)));

        scene.addGeometries(new Sphere(
                new Color(java.awt.Color.green),
                new Material(0.5, 0.5, 100), 5,
                new Point3D(-49.48, -7.18, 50)));

        scene.addGeometries(new Sphere(
                new Color(java.awt.Color.BLUE),
                new Material(0.5, 0.5, 100), 8,
                new Point3D(-48.69, 11.37, 50)));

        scene.addGeometries(new Sphere(
                new Color(java.awt.Color.RED),
                new Material(0.5, 0.5, 100), 5,
                new Point3D(-41.58, 27.77, 50)));

        scene.addGeometries(new Sphere(
                new Color(java.awt.Color.yellow),
                new Material(0.5, 0.5, 100), 8,
                new Point3D(-28.85, 40.83, 50)));

        scene.addGeometries(new Sphere(
                new Color(java.awt.Color.green),
                new Material(0.5, 0.5, 100), 5,
                new Point3D(-12.06, 48.52, 50)));

        scene.addGeometries(new Sphere(
                new Color(java.awt.Color.blue),
                new Material(0.5, 0.5, 100), 8,
                new Point3D(6.72, 49.55, 50)));


        //**********************************  The big sphere ************************************//

        scene.addGeometries(
                new Sphere(
                        new Color(java.awt.Color.BLUE),
                        new Material(0.5, 0.5, 100, 0.6, 0.4), 50,
                        new Point3D(0, 0, 50)));


        scene.addLights(new DirectionalLight(new Color(255, 191, 191), new Vector(1, -1, 1)));

        ImageWriter imageWriter = new ImageWriter("coronaSphere", 200, 200, 500, 500);
        Render render = new Render(imageWriter, scene);

        render.renderImage(false, false, false);
        render.writeToImage();
    }
}
