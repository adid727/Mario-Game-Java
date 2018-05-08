package com.humber.MarioLevel;

/**
 * Created by adid on 2016-11-15.
 */
public class BaseTile {

    public enum TileType {
        Ground,
        LeftSlant,
        RightSlant,
        Coin,
        Platform,
        QBlock,
        MusicBlock
    }

    public enum TagType {
        None,
        LowSlant,
        MidSlant,
        HighSlant
    }

    public TileType tileType;
    public TagType tag;
    public int tileX;
    public int tileY;

    public BaseTile(int tileX, int tileY, TileType type, TagType tag) {
        this.tileX = tileX;
        this.tileY = tileY;
        this.tileType = type;
        this.tag = tag;
    }

    public static Vector2 GetTileSize() {
        return new Vector2(MarioLevel.levelMap.getTileWidth(), MarioLevel.levelMap.getTileHeight());
    }
}