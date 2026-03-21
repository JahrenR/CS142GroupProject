package ZombieSim;

import ZombieSim.tiles.*;

import java.awt.Point;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * MapLoader reads a map file and converts it to a 2D array of MapTile objects.
 *
 * It also creates entities found in the file (humans, zombies, soldiers,
 * generals) and adds them to the provided entities list.
 */
public class MapLoader {

    /**
     *
     * @param filename the map file to load
     * @param entities a list that will be filled with entities created from the map
     *
     * @return a 2D array of MapTile objects representing the map
     */

    public static MapTile[][] loadMap(String filename, List<Entity> entities) {
        List<String> lines = new ArrayList<>();

        try (Scanner scanner = new Scanner(new File(filename))) {
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine().trim();

                // skip completely blank lines
                if (!line.isEmpty()) {
                    lines.add(line);
                }
            }

            //If file cannot be found, stop and report the error
        } catch (FileNotFoundException e) {
            throw new IllegalArgumentException("Could not load map file: " + filename);
        }

        if (lines.isEmpty()) {
            throw new IllegalArgumentException("Map file is empty.");
        }

        int rows = lines.size();
        int cols = lines.get(0).length();

        for (String line : lines) {
            if (line.length() != cols) {
                throw new IllegalArgumentException("All map rows must have the same length.");
            }
        }

        //Create the 2D map array
        MapTile[][] map = new MapTile[rows][cols];

        for (int r = 0; r < rows; r++) {
            String row = lines.get(r);

            for (int c = 0; c < cols; c++) {
                char symbol = row.charAt(c);
                Point p = new Point(c + 1, rows - r);

                MapTile tile;
                Entity entity = null;

                switch (symbol) {
                    // terrain only
                    case 'W' -> tile = new WallTile(p);
                    case 'C' -> tile = new SafeZoneTile(p);
                    case 'A' -> tile = new WaterTile(p);
                    case 'D' -> tile = new DeepWaterTile(p);
                    case '.' -> tile = new GrassTile(p);

                    // entities placed on grass
                    case 'H' -> {
                        tile = new GrassTile(p);
                        entity = new Entity(Unit.HUMAN);
                    }
                    case 'Z' -> {
                        tile = new GrassTile(p);
                        entity = new Entity(Unit.ZOMBIE);
                    }
                    case 'S' -> {
                        tile = new GrassTile(p);
                        entity = new Entity(Unit.SOLDIER);
                    }
                    case 'X' -> {
                        tile = new GrassTile(p);
                        entity = new Entity(Unit.GENERAL);
                    }

                    default -> throw new IllegalArgumentException(
                            "Invalid map symbol '" + symbol + "' at row " + r + ", col " + c
                    );
                }
                //Store tile in map array
                map[r][c] = tile;

                if (entity != null) {
                    entity.setPosition(p);
                    tile.setEntity(entity);
                    entities.add(entity);
                }
            }
        }

        return map;
    }
}