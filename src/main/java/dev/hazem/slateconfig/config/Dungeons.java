package dev.hazem.slateconfig.config;

import dev.hazem.slateconfig.annotations.Group;
import dev.hazem.slateconfig.annotations.Option;

public class Dungeons {

    @Option(title = "playerAge")
    public boolean playerAge = true;

    @Group(title = "playerData")
    public PlayerData playerData = new PlayerData();

    public static class PlayerData {
        @Option(title = "name")
        public String name = "hello";

        @Option(title = "id")
        public String id = "lol";

        @Option(title = "age")
        public int age = 5;

        @Option(title = "points")
        public double points = 5.5;
    }
}
