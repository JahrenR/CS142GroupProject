package zombie;

public class MapTile {
    Entity tileUnit;

    MapTile() {
        tileUnit = null;
    }
    public Entity getTileUnit() {
        return tileUnit;
    }
    public void setTileUnit(Entity tileUnit) {
        this.tileUnit = tileUnit;
    }

}
