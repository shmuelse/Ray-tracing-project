package elements;

import primitives.Color;
import primitives.Point3D;
import primitives.Vector;


/**
 * Class to manage a point lighting extends from Light and implement LightSource
 */
public class PointLight extends Light implements LightSource {

    protected Point3D _position;
    protected double
            _kC,
            _kL,
            _kQ;

    protected double _radius;

    //***************** Constructors **********************//

    /**
     * Constructor to build a Point light with radius
     *
     * @param _colorIntensity
     * @param _position       - Position of the light
     * @param _kC             - Coefficient of Quadratic attenuation of the light in the distance
     * @param _kL             - Coefficient of linear weakening of the light in the distance
     * @param _kQ             - Coefficient of exponential weakening of the light at a distance
     * @param radius          - radius of the point light
     */
    public PointLight(Color _colorIntensity, Point3D _position, double _kC, double _kL, double _kQ, double radius) {
        super(_colorIntensity);
        this._position = new Point3D(_position);
        this._kC = _kC;
        this._kL = _kL;
        this._kQ = _kQ;
        this._radius = radius;
    }

    /**
     * Constructor to build a Point light
     *
     * @param _colorIntensity
     * @param _position       - Position of the light
     * @param _kC             - Coefficient of Quadratic attenuation of the light in the distance
     * @param _kL             - Coefficient of linear weakening of the light in the distance
     * @param _kQ             - Coefficient of exponential weakening of the light at a distance
     */
    public PointLight(Color _colorIntensity, Point3D _position, double _kC, double _kL, double _kQ) {
        this(_colorIntensity, _position, _kC, _kL, _kQ, 0);
    }



    // ***************** Getters/Setters ********************** //

    /**
     * Return PointLight position
     * @return
     */
    public Point3D getPosition() {return this._position;}

    /**
     * Return PointLight radius
     * @return
     */
    public double getRadius() {return this._radius;}



    /**
     * calculate the intensity of color of point on the geometry
     *
     * @param p the lighted point
     * @return intensity of color af point p
     */
    @Override
    public Color getIntensity(Point3D p) {
        double dsquared = p.distanceSquared(_position);
        double d = p.distance(_position);

        return (_intensity.reduce(_kC + _kL * d + _kQ * dsquared));
    }

    /**
     *
     * calculates the direction of the light
     * ray from the light source to the point
     *
     * @param p the lighted point
     * @return The direction of the light rays that hit the point
     */
    @Override
    public Vector getL(Point3D p) {
        if (p.equals(_position)) {
            return null;
        }
        return p.subtract(_position).normalized();
    }

    @Override
    public double getDistance(Point3D point) {
        return _position.distance(point);
    }

}
