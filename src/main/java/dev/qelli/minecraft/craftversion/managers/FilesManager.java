package dev.qelli.minecraft.craftversion.managers;

import dev.qelli.minecraft.craftversion.CraftVersion;
import dev.qelli.minecraft.craftversion.git.repositories.GitRepository;

public class FilesManager {

    CraftVersion plugin;
    GitRepository gitRepository;

    public FilesManager(CraftVersion plugin) {
        this.plugin = plugin;
        this.gitRepository = new GitRepository(plugin);
    }

    public boolean isReady() {
        
        // Check if git is correctly installed
        if (!this.gitRepository.isGitInstallationPathValid()) {
            plugin.getLogger().severe("Provided path to GIT is invalid or not working.");
            return false;
        }

        // Check if /plugins/ folder is a git repository
        if (!this.gitRepository.isPluginsFolderAGitRepo()) {
            plugin.getLogger().severe("The /plugins folder is not a GIT repository.");
            // Optional: Create the local repo based on plugin config
            return false;
        }

        // Check if remote repo is accessible
        if (!this.gitRepository.isRemoteRepoAccessible()) {
            plugin.getLogger().severe("The remote repository is not accessible.");
            return false;
        }

        return true;
    }

    public boolean isChangesFree() {
        return this.gitRepository.getStatus().isSuccess();
    }

    public void executeGitCommand(String... args) {
        String output = this.gitRepository.command(args).getOutput();
        for (String line : output.split("\n")) {
            plugin.getLogger().info(line);
        }
    }
    
}
