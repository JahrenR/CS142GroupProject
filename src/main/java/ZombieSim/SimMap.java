package ZombieSim;

import ZombieSim.Entities.Entity;

public class SimMap {
    private final int size;
    private MapTile[][] simMap;
    public SimMap(int size) {
        this.size = size;
        createMap();
    }
    private void createMap() {
        simMap = new MapTile[size][size];
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                simMap[i][j] = new MapTile(i+1, j+1);
            }
        }
    }
    public Entity getUnit(int x, int y) {
        if(outBounds(x, y)) {return null;}
        return simMap[x][y].getEntity();
    }
    public boolean spawn(Entity unit, int x, int y) {
        if(outBounds(x, y)) {return false;}
        if(getUnit(x, y) != null) {return false;}
        simMap[x][y].setEntity(unit);
        unit.setPosition(x, y);
        return true;
    }
    public boolean outBounds(int x, int y) {
        return x < 0 || x >= simMap.length ||
                y < 0 || y >= simMap[0].length;
    }
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                if(getUnit(i, j) != null)
                    sb.append(getUnit(i, j).toString());
                else
                    sb.append("-");
            }
            if(i < size - 1) {sb.append("\n");}
        }
        return sb.toString();
    }


}
