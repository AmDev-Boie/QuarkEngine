package QuarkEngine.Classes.types.Matrices;

// CODE NOT BY ME.

// the code for this class was sourced from this stack overflow post: https://stackoverflow.com/questions/36713659/rotation-matrix-java
// credit to the OP, and the people who answered that question

// and ngl if it wasn't for that post I probably wouldn't have figured out rotation matrices sooner.

public class Matrix4f
{
    private float[][] m;

    //generate a 4X4 array as my inital matrix which will represent homogeneous
    //coordinates: x, y, z, w
    public Matrix4f()
    {
        m = new float[4][4];
    }

    public Matrix4f initRotation(float x, float y, float z)
    {
        //generate rotation matrices for x, y, and z
        Matrix4f rx = new Matrix4f();
        Matrix4f ry = new Matrix4f();
        Matrix4f rz = new Matrix4f();

        //convert x,y, and z to radians for angle calculations
        x = (float)Math.toRadians(x);
        y = (float)Math.toRadians(y);
        z = (float)Math.toRadians(z);

        //calculate rotation matrices for x, y, z

        rz.m[0][0] = (float)Math.cos(z);  rz.m[0][1] = (float)Math.sin(z); rz.m[0][2] = 0;                   rz.m[0][3] = 0;
        rz.m[1][0] = -(float)Math.sin(z); rz.m[1][1] = (float)Math.cos(z); rz.m[1][2] = 0;                   rz.m[1][3] = 0;
        rz.m[2][0] = 0;                   rz.m[2][1] = 0;                  rz.m[2][2] = 1;                   rz.m[2][3] = 0;
        rz.m[3][0] = 0;                   rz.m[3][1] = 0;                  rz.m[3][2] = 0;                   rz.m[3][3] = 1;

        rx.m[0][0] = 1;                   rx.m[0][1] = 0;                  rx.m[0][2] = 0;                   rx.m[0][3] = 0;
        rx.m[1][0] = 0;                   rx.m[1][1] = (float)Math.cos(x); rx.m[1][2] = -(float)Math.sin(x); rx.m[1][3] = 0;
        rx.m[2][0] = 0;                   rx.m[2][1] = -(float)Math.sin(x);rx.m[2][2] = (float)Math.cos(x);  rx.m[2][3] = 0;
        rx.m[3][0] = 0;                   rx.m[3][1] = 0;                  rx.m[3][2] = 0;                   rx.m[3][3] = 1;

        ry.m[0][0] = (float)Math.cos(y);  ry.m[0][1] = 0;                  ry.m[0][2] = -(float)Math.sin(y); ry.m[0][3] = 0;
        ry.m[1][0] = 0;                   ry.m[1][1] = 1;                  ry.m[1][2] = 0;                   ry.m[1][3] = 0;
        ry.m[2][0] = (float)Math.sin(y);  ry.m[2][1] = 0;                  ry.m[2][2] = (float)Math.cos(y);  ry.m[2][3] = 0;
        ry.m[3][0] = 0;                   ry.m[3][1] = 0;                  ry.m[3][2] = 0;                   ry.m[3][3] = 1;

        //calculate the final rotation matrix by multiplying the 3 rotation matrices together
        m = rz.mul(ry.mul(rx)).getM();

        //a simple way to print out the full matrix
        System.out.println("Rotation Matrix:");
        for(int i = 0; i < 4; i++)
        {
            System.out.println("");
            for(int j = 0; j < 4; j++)
            {
                System.out.print(m[i][j] + " ");
            }
        }
        System.out.println("");

        return this;
    }

    //defining the multiplication operation for matrices
    public Matrix4f mul(Matrix4f r) {
        Matrix4f res = new Matrix4f();
        for(int i = 0; i < 4; i++)
        {
            for(int j = 0; j < 4; j++)
            {
                res.set(i, j, m[i][0]*r.get(0, j) +
                        m[i][1]*r.get(1, j) +
                        m[i][2]*r.get(2, j) +
                        m[i][3]*r.get(3, j));
            }
        }

        return res;

    }

    //get the matrix in array form
    public float[][] getM()
    {
        return m;
    }

    //get an individual element of the matrix
    public float get(int x, int y)
    {
        return m[x][y];
    }

    //set the whole matrix equal to a matrix m
    public void setM(float[][] m)
    {
        this.m = m;
    }

    //set an individual element of the matrix to a value
    public void set(int x, int y, float value)
    {
        m[x][y] = value;
    }

}