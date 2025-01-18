package dev.qelli.minecraft.craftversion.git.repositories;

import java.io.File;

import dev.qelli.minecraft.craftversion.CraftVersion;
import dev.qelli.minecraft.craftversion.git.config.GitConfig;
import dev.qelli.minecraft.craftversion.utils.CommandExecutor;
import dev.qelli.minecraft.craftversion.utils.ProcessResult;

public class GitRepository {
    
    private CraftVersion plugin;
    private GitConfig config;

    public GitRepository(CraftVersion plugin) {
        this.plugin = plugin;
        this.config = new GitConfig(plugin);
    }

    public boolean isGitInstallationPathValid() {
        ProcessResult result = getVersion();
        if (result.isSuccess()) {
            plugin.getLogger().info("GIT version: " + result.getOutput());
            return true;
        }
        return false;
    }

    public boolean isPluginsFolderAGitRepo() {
        return command("rev-parse", "--is-inside-work-tree").isSuccess();
    }

    public boolean isRemoteRepoAccessible() {
        return command("ls-remote", config.getRepo()).isSuccess();
    }

    public ProcessResult getVersion() {
        return command("--version");
    }

    public ProcessResult getRemote() {
        return command("remote", "--v");
    }

    public ProcessResult fetchOrigin() {
        return command("fetch");
    }

    public ProcessResult getStatus() {
        return command("status");
    }

    /**
     * Executes a git command with the provided args
     * @param args
     * @return ProcessResult
     */
    public ProcessResult command(String... args) {
        // getDataFolder is /plugins/CraftVersion/
        // so doing ../ sets us at /plugins/
        return CommandExecutor.git(
            config.getPath(),
            new File(
                plugin.getDataFolder(),
                "../"
            ),
            args
        );
    }

}
