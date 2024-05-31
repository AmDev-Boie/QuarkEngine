package QuarkEngine.Classes.Handlers.JMath;

public class InterpolationOperation {
    public static double Lerp(double a, double b, double f)
    {
        return (a * (1.0 - f)) + (b * f);
    }
}
