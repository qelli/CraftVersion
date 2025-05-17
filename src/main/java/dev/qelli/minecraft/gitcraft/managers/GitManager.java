package dev.qelli.minecraft.gitcraft.managers;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import dev.qelli.minecraft.gitcraft.GitCraft;

public class GitManager {

    GitCraft plugin;

    public GitManager(GitCraft plugin) {
        this.plugin = plugin;
    }

    private Boolean isPathValid(String path) {
        File file = new File(this.plugin.getGitConfig().getExecPath());
        return file.exists();
    }

    public boolean isReady() {

        // Check if git is correctly installed
        if (!this.isPathValid(this.plugin.getGitConfig().getExecPath())) {
            this.plugin.getLogger().severe("The provided GIT executable path seems to be not working or found. ("+ this.plugin.getGitConfig().getExecPath() +")");
            return false;
        }

        // Check if working dir is valid
        if (!this.isPathValid(this.plugin.getGitConfig().getWorkingDir())) {
            this.plugin.getLogger().severe("Invalid working dir. ("+ this.plugin.getGitConfig().getWorkingDir() +")");
            return false;
        }

        // Check if working dir has .gitignore
        if (!this.isPathValid(Paths.get(this.plugin.getGitConfig().getWorkingDir()).resolve(".gitignore").toString())) {
            this.plugin.getLogger().severe("No .gitignore was found. It's highly advised that you create one at " + this.plugin.getGitConfig().getWorkingDir() + ".");
            return false;
        }

        // Check if /plugins/ folder is a git repository
        // if (!this.gitRepository.isPluginsFolderAGitRepo()) {
        //     plugin.getLogger().severe("The /plugins folder is not a GIT repository.");
        //     // Optional: Create the local repo based on plugin config
        //     return false;
        // }

        // Check if remote repo is accessible
        // if (!this.gitRepository.isRemoteRepoAccessible()) {
        //     plugin.getLogger().severe("The remote repository is not accessible.");
        //     return false;
        // }

        return true;
    }

    public void safeExec(String... args) {
        // TODO: Sanitize as much as possible the input
        this.exec(args);
    }

    private void exec(String... args) {

        final GitCraft plugin = this.plugin;

        this.plugin.getServer().getScheduler().runTaskAsynchronously(plugin, () -> {

            File directory = new File(this.plugin.getGitConfig().getWorkingDir());
            String gitPath = plugin.getGitConfig().getExecPath();

            List<String> command = new ArrayList<>();
            command.add(gitPath);
            command.addAll(normalizeArgs(args));

            ProcessBuilder processBuilder = new ProcessBuilder(command);

            processBuilder.directory(directory);
            processBuilder.redirectErrorStream(true);
            // TODO: Find a way to prevent input requests from the process
            // processBuilder.redirectInput(ProcessBuilder.Redirect.from(new File("/dev/null")));

            int exitCode = -1;

            try {
                Process process = processBuilder.start();
                process.getOutputStream().close();
                exitCode = process.waitFor();

                try (BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()))) {
                    List<String> output = reader.lines().collect(Collectors.toList());
                    plugin.onExec(exitCode, output, null);
                } catch(Exception e) {
                    plugin.onExec(exitCode, null, e.getMessage());
                    e.printStackTrace();
                }
            } catch (IOException e) {
                plugin.onExec(exitCode, null, e.getMessage());
                e.printStackTrace();
            } catch (InterruptedException e) {
                plugin.onExec(exitCode, null, e.getMessage());
                e.printStackTrace();
            }
        });
    }

    private List<String> normalizeArgs(String... args) {
        List<String> result = new ArrayList<>();
        StringBuilder current = new StringBuilder();
        boolean inQuote = false;
    
        for (String arg : args) {
            if (arg.startsWith("\"")) {
                inQuote = true;
                current.append(arg.substring(1));
            } else if (inQuote) {
                current.append(" ");
                if (arg.endsWith("\"")) {
                    current.append(arg, 0, arg.length() - 1);
                    result.add(current.toString());
                    current.setLength(0);
                    inQuote = false;
                } else {
                    current.append(arg);
                }
            } else {
                result.add(arg);
            }
        }
    
        // In case of unclosed quotes, still add what we have
        if (current.length() > 0) {
            result.add(current.toString());
        }
    
        return result;
    }
    
}
