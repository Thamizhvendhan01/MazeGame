package com.example.maze;

public class Level {
    int level;
    Things[][] maze;
    public Level(int level,Things[][] maze){
        this.level = level;
        this.maze = maze;
    }

    public int getLevel() {
        return level;
    }

    public Things[][] getMaze() {
        return maze;
    }
}
