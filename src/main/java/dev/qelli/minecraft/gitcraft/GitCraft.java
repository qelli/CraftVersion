package dev.qelli.minecraft.gitcraft;

import java.util.List;

import org.bukkit.plugin.java.JavaPlugin;

import dev.qelli.minecraft.gitcraft.commands.GitCraftCommands;
import dev.qelli.minecraft.gitcraft.config.GitConfig;
import dev.qelli.minecraft.gitcraft.managers.GitManager;

public final class GitCraft extends JavaPlugin {

    GitConfig gitConfig;
    GitManager gitManager;
    GitCraftCommands commands;

    @Override
    public void onEnable() {

        if(!getDataFolder().exists()) {
            getDataFolder().mkdir();
            saveDefaultConfig();
        }

        this.gitConfig = new GitConfig(this);

        this.gitManager = new GitManager(this);

        if (!this.gitManager.isReady()) {
            getLogger().severe("Something went wrong while initializing the plugin.");
            getServer().getPluginManager().disablePlugin(this);
            return;
        }

        // Once GIT is successfully working, initialize commands
        this.commands = new GitCraftCommands(this);

        // Fetch remote (if enabled in config)
        if (gitConfig.shouldFetchOnStart()) {
            getLogger().info("Fetching remote...");
            gitManager.safeExec("fetch");
        }

        // Pull remote (if enabled in config)
        if (gitConfig.shouldPullOnStart()) {
            getLogger().info("Pulling from source " + gitConfig.getSource());
            gitManager.safeExec("checkout", gitConfig.getSource());
            gitManager.safeExec("pull");
        }

    }

    @Override
    public void onDisable() {}

    public GitManager getGitManager() {
        return this.gitManager;
    }

    public GitConfig getGitConfig() {
        return this.gitConfig;
    }

    public void onExec(int exitCode, List<String> output, String errorMessage) {
        if (exitCode != 0) {
            this.getLogger().info("Command exited with status code " + exitCode);
        }
        if (output != null) {
            for (String line : output) {
                this.getLogger().info(line);
            }
        }
        if (errorMessage != null) {
            this.getLogger().severe(errorMessage);
        }
    }

}
