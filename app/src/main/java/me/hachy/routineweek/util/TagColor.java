package me.hachy.routineweek.util;


import java.util.Arrays;

public enum TagColor {
    None("none"),
    Blue("blue"),
    Red("red"),
    Green("green"),
    Yellow("yellow");

    private String name;

    private TagColor(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public static String[] names() {
        return Arrays.toString(TagColor.values()).replaceAll("^.|.$", "").split(", ");
    }
}
