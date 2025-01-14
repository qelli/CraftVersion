package dev.qelli.minecraft.craftversion.git.config;

import dev.qelli.minecraft.craftversion.CraftVersion;

public class GitConfig {

    CraftVersion plugin;
    
    private String path;
    private String repo;
    private String branch;

    public GitConfig(CraftVersion plugin) {
        this.plugin = plugin;
        this.path = plugin.getConfig().getString("git.path");
        this.repo = plugin.getConfig().getString("git.repo");
        this.branch = plugin.getConfig().getString("git.branch");
    }

    public String getExecutablePath() {
        return path;
    }

    public String getRepo() {
        return repo;
    }

    public String getBranch() {
        return branch;
    }

}
