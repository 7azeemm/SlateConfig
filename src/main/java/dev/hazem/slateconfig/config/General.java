package dev.hazem.slateconfig.config;

import dev.hazem.slateconfig.annotations.Group;
import dev.hazem.slateconfig.annotations.Option;
import dev.hazem.slateconfig.annotations.Tab;

public class General {

    @Option(title = "playerAge")
    public boolean playerAge = true;

    @Tab(title = "playerData")
    public PlayerData playerData = new PlayerData();

    @Option(title = "type")
    public Type type = Type.CLIENT;

    public enum Type {
        GAME,
        CLIENT,
        SERVER
    }

    public static class PlayerData {
        @Option(title = "name")
        public String name = "hello";

        @Option(title = "id")
        public String id = "lol";

        @Option(title = "age")
        public int age = 5;

        @Option(title = "points")
        public double points = 5.5;

        @Option(title = "type")
        public Type type = Type.SERVER;

        @Group(title = "playerGroup")
        public PlayerGroup playerGroup = new PlayerGroup();

        public static class PlayerGroup {
            @Option(title = "groups")
            public boolean groups = false;
        }

        @Group(title = "players")
        public Players players = new Players();

        public static class Players {
            @Option(title = "lot")
            public boolean lot = false;
        }
    }
}
