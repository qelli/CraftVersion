package dev.qelli.minecraft.gitcraft.commands;

import java.util.Arrays;
import java.util.List;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.command.PluginCommand;
import org.bukkit.command.TabCompleter;

import dev.qelli.minecraft.gitcraft.GitCraft;
import dev.qelli.minecraft.gitcraft.config.Constants;

public class GitCraftCommands implements CommandExecutor, TabCompleter {

    GitCraft plugin;

    public GitCraftCommands(GitCraft plugin) {
        this.plugin = plugin;
        register();
    }
    
    public void register() {
        PluginCommand command = plugin.getCommand(Constants.Commands.GitCraft.Main);
        command.setExecutor(this);
        command.setAliases(Arrays.asList(Constants.Commands.GitCraft.ConsoleAlias));
        command.setPermission(Constants.Commands.GitCraft.Permission);
        command.setTabCompleter(this);
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
        if (args.length == 0) {
            return Arrays.asList();
        }
        if (args.length == 1) {
            return Arrays.asList(
                Constants.Commands.GitCraft.SubCommands.Info
            );
        }
        return Arrays.asList();
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        // When using the git alias from console, it passes the args directly to git (only for users with GIT knowledge)
        if (label.equalsIgnoreCase(Constants.Commands.GitCraft.ConsoleAlias) && sender instanceof ConsoleCommandSender) {
            plugin.getGitManager().safeExec(args);
            return true;
        }

        // Otherwise use GitCraft subcommands
        if (args.length == 0) {
            return false;
        }

        // TODO: Implement subcommands for non-git users
        switch (args[0]) {
            case Constants.Commands.GitCraft.SubCommands.Status:
                this.runStatus();
                return true;
        }

        return false;
    }

    private void runStatus() {
        this.plugin.getGitManager().safeExec("status");
    }

    // private void runTestCommand() {
    //     this.plugin.getGitManager().safeExec("rev-parse", "--is-inside-work-tree");
    // }

    // private void runListRemote() {
    //     this.plugin.getGitManager().safeExec("ls-remote", this.plugin.getGitConfig().getRemotes().get(0));
    // }

}
