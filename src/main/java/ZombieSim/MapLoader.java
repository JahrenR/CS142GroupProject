package ZombieSim;

import ZombieSim.Entities.*;
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

                    default:
                        tile = new GrassTile(p);
                }

                map[r][c] = tile;

                // Entity spawning

                switch(symbol) {

                    case 'H':
                        entities.add(new Entity(Unit.HUMAN, Point p));
                        break;

                    case 'Z':
                        entities.add(new Entity(Unit.ZOMBIE, p));
                        break;

                    case 'S':
                        entities.add(new Entity(Unit.SOLDIER, p));
                        break;

                    case 'X':
                        entities.add(new Entity(Unit.GENERAL, p));
                        break;
                }
            }
        }

        return map;
    }
}
