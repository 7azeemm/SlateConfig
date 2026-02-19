package dev.hazem.slateconfig.config;

import dev.hazem.slateconfig.annotations.Description;
import dev.hazem.slateconfig.annotations.Group;
import dev.hazem.slateconfig.annotations.Option;
import dev.hazem.slateconfig.annotations.Tab;

public class Slayers {

    @Tab(title = "General")
    public General general = new General();

    @Tab(title = "Zombie")
    public Zombie zombie = new Zombie();

    @Tab(title = "Spider")
    public Spider spider = new Spider();

    @Tab(title = "Wolf")
    public Wolf wolf = new Wolf();

    @Tab(title = "Enderman")
    public Enderman enderman = new Enderman();

    @Tab(title = "Blaze")
    public Blaze blaze = new Blaze();

    @Tab(title = "Vampire")
    public Vampire vampire = new Vampire();

    public static class General {
        @Description("Displays slayer progress and information on your HUD")
        @Option(title = "Slayer HUD")
        public boolean enableSlayerHud = true;

        @Description("Makes slayer bosses glow through walls for easier tracking")
        @Option(title = "Glow Boss")
        public Glow glowBoss = Glow.GLOW;

        @Description("Highlights minibosses during slayer quests for better visibility")
        @Option(title = "Highlight Minibosses")
        public Glow glowMinibosses = Glow.GLOW;

        public enum Glow {
            GLOW,
            HITBOX,
            OFF
        }

        @Description("Shows a boss bar at the top of your screen during slayer fights")
        @Option(title = "Enable BossBar")
        public boolean enableBossBar = false;

        @Group(title = "Personal Best")
        public PersonalBest personalBest = new PersonalBest();

        public static class PersonalBest {
            @Description("Display a title on screen when you beat your personal best time")
            @Option(title = "Show Title")
            public boolean showTitle = true;

            @Description("Send a chat message when you achieve a new personal best")
            @Option(title = "Show In Chat")
            public boolean showInChat = false;

            @Description("Play a special animation when breaking your personal best record")
            @Option(title = "Amazing")
            public boolean amazing = false;
        }

        @Description("Enable additional slayer features and functionality")
        @Option(title = "Enable Smth")
        public boolean enableSmth = true;

        @Description("Toggle visibility of slayer-related information")
        @Option(title = "Show Something")
        public boolean showSomething = true;
    }

    public static class Zombie {

    }

    public static class Spider {

    }

    public static class Wolf {

    }

    public static class Enderman {

    }

    public static class Blaze {

    }

    public static class Vampire {

    }
}
