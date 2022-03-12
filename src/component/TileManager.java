package component;

import main.GamePanel;
import resource.Tile;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

public class TileManager
{
    Tile[][] map;
    ArrayList<Tile> tileList = new ArrayList<Tile>();

    public int width;
    public int height;

    public TileManager(int mapIndex, int _height, int _width)
    {
        height = _height;
        width = _width;

        LoadTiles();

        LoadMap(mapIndex);
    }

    public void LoadTiles()
    {
        for(int i = 0; i < 16; i++)
        AddTile(i);
    }

    public void AddTile(int _index)
    {
        tileList.add(new Tile(_index));
    }

    public void LoadMap(int mapIndex)
    {
        map = new Tile[width][height];

        try
        {
            InputStream is = getClass().getResourceAsStream("/map/map" + mapIndex + ".txt");
            BufferedReader br = new BufferedReader(new InputStreamReader(is));

            for (int y = 0; y < width; y++)
            {
                String[] line = br.readLine().split(" ");
                for (int x = 0; x < height; x++)
                {
                    map[y][x] = tileList.get(Integer.parseInt(line[x]));
                }
            }
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }


    }

    public void draw(Graphics2D g2D)
    {
        for (int y = 0; y < width; y++)
        {
            for (int x = 0; x < height; x++)
            {
                g2D.drawImage(map[y][x].image, x * GamePanel.tileSize, y * GamePanel.tileSize, GamePanel.tileSize, GamePanel.tileSize, null);
            }
        }
    }
}
