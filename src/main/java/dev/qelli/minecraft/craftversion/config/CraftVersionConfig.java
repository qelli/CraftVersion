package dev.qelli.minecraft.craftversion.config;

import java.util.Arrays;
import java.util.List;

public class CraftVersionConfig {
    
    public static final class Commands {
        public static final class CraftVersion {
            public static final String Main = "craftversion";
            public static final String Permission = "craftversion.admin";
            public static final String ConsoleAlias = "git";
            public static final class SubCommands {
                public static final String Fetch = "fetch";
                public static final String Pull = "pull";
                public static final String Version = "version";
                public static final String Status = "status";


                public static final String Info = "info";
            }
        }
    }

}
