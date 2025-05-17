package dev.qelli.minecraft.gitcraft.config;

import java.util.List;
import java.util.Map;

import dev.qelli.minecraft.gitcraft.GitCraft;

public class GitConfig {

    private String workingDir;
    private String gitExecutablePath;

    private Boolean pullOnStart;
    private Boolean fetchOnStart;

    private String gitSource;
    
    public GitConfig(GitCraft plugin) {
        this.workingDir = plugin.getConfig().getString("git.working-dir");
        this.gitExecutablePath = plugin.getConfig().getString("git.exec-path");

        this.pullOnStart = plugin.getConfig().getBoolean("git.pull-on-startup");
        this.fetchOnStart = plugin.getConfig().getBoolean("git.fetch-on-startup");

        this.gitSource = plugin.getConfig().getString("git.source");
    }

    public String getWorkingDir() {
        return workingDir;
    }

    public Boolean shouldPullOnStart() {
        return pullOnStart;
    }

    public Boolean shouldFetchOnStart() {
        return fetchOnStart;
    }

    public String getExecPath() {
        return gitExecutablePath;
    }

    public String getSource() {
        return gitSource;
    }

}
