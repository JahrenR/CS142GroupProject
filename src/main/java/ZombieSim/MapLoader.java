package ZombieSim;

import ZombieSim.tiles.*;
import ZombieSim.Unit;
import ZombieSim.Entity;

import java.awt.Point;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class MapLoader {

    public static MapTile[][] loadMap(String filename, List<Entity> entities) {

        List<String> lines = new ArrayList<>();

        try {

            Scanner scanner = new Scanner(new File(filename));

            while (scanner.hasNextLine()) {
                lines.add(scanner.nextLine());
            }

            scanner.close();

        } catch (Exception e) {

            System.out.println("Could not load map file.");

        }

        int rows = lines.size();
        int cols = lines.get(0).length();

        MapTile[][] map = new MapTile[rows][cols];

        for (int r = 0; r < rows; r++) {

            String row = lines.get(r);

            for (int c = 0; c < cols; c++) {

                char symbol = row.charAt(c);

                Point p = new Point(c + 1, rows - r);

                MapTile tile;

                switch(symbol) {

                    case 'W':
                        tile = new WallTile(p);
                        break;

                    case 'C':
                        tile = new SafeZoneTile(p);
                        break;

                    case 'A':
                        tile = new WaterTile(p);
                        break;

                    default:
                        tile = new GrassTile(p);
                }

                map[r][c] = tile;

                // Entity spawning

                Entity e = null;

                switch(symbol) {

                    case 'H':
                        e = new Entity(Unit.HUMAN);
                        break;

                    case 'Z':
                        e = new Entity(Unit.ZOMBIE);
                        break;

                    case 'S':
                        e = new Entity(Unit.SOLDIER);
                        break;

                    case 'X':
                        e = new Entity(Unit.GENERAL);
                        break;
                }

                if (e != null) {
                    e.setPosition(p);   // set the location
                    entities.add(e);    // add to simulation
                    tile.setEntity(e);  // place on tile
                }
            }
        }

        return map;
    }
}
