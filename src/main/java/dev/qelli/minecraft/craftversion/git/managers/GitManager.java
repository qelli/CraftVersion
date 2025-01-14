package dev.qelli.minecraft.craftversion.git.managers;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.List;
import java.util.stream.Collectors;

import dev.qelli.minecraft.craftversion.CraftVersion;
import dev.qelli.minecraft.craftversion.git.config.GitConfig;

public class GitManager {
    
    private CraftVersion plugin;
    private GitConfig config;

    public GitManager(CraftVersion plugin) {
        this.plugin = plugin;
        this.config = new GitConfig(plugin);
    }

    public void init() {
        plugin.getLogger().info("Initializing GIT...");

    }

    public String getVersion() {
        return command("--version");
    }

    // public void update() {
    //     return List.of(
    //         command("checkout", this.config.getBranch()),
    //         command("pull")
    //     ).toString();
    // }

    /**
     * Executes a git command with the provided args
     * @param args
     * @return OUTPUT
     */
    private String command(String... args) {

        // Build the command args
        String[] command = new String[args.length + 1];
        command[0] = this.config.getExecutablePath();
        System.arraycopy(args, 0, command, 1, args.length);
        ProcessBuilder processBuilder = new ProcessBuilder(command);

        // Configure the builder
        processBuilder.directory(plugin.getDataFolder());
        processBuilder.redirectErrorStream(true);

        try {
            Process process = processBuilder.start();
            try (BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()))) {
                String output = reader.lines().collect(Collectors.joining("\n"));
                plugin.getLogger().info(output);
                return output;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return "Something went wrong while executing the command.";
        }
    }

    // private boolean checkGitInstallation() {

    //     return false;
    // }

}
