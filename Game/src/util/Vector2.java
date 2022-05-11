package util;

public class Vector2
{
    public float x = 0, y = 0;
    public Vector2(float x, float y)
    {
        this.x = x;
        this.y = y;
    }

    public void Normalize()
    {
        if(x == 0 && y == 0)
        {
            this.x = 0;
            this.y = 0;
        }

        float length = (float)Math.sqrt(x * x + y * y);
        x = x / length;
        y = y / length;
    }


    public void Add(Vector2 _vectorToAdd)
    {
        x += _vectorToAdd.x;
        y += _vectorToAdd.y;
    }

    public String toString()
    {
        return "(" + x + ", " + y + ")";
    }
}
