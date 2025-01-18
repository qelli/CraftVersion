package dev.qelli.minecraft.craftversion.commands;

import java.util.Arrays;
import java.util.List;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.command.PluginCommand;
import org.bukkit.command.TabCompleter;

import dev.qelli.minecraft.craftversion.CraftVersion;
import dev.qelli.minecraft.craftversion.config.CraftVersionConfig;

public class CraftVersionCommands implements CommandExecutor, TabCompleter {

    CraftVersion plugin;

    public CraftVersionCommands(CraftVersion plugin) {
        this.plugin = plugin;
        register();
    }
    
    public void register() {
        PluginCommand command = plugin.getCommand(CraftVersionConfig.Commands.CraftVersion.Main);
        command.setExecutor(this);
        command.setAliases(Arrays.asList(CraftVersionConfig.Commands.CraftVersion.ConsoleAlias));
        command.setPermission(CraftVersionConfig.Commands.CraftVersion.Permission);
        command.setTabCompleter(this);
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
        if (args.length == 0) {
            return Arrays.asList();
        }
        if (args.length == 1) {
            return Arrays.asList(
                CraftVersionConfig.Commands.CraftVersion.SubCommands.Info
            );
        }
        return Arrays.asList();
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        // When using the git alias from console, it should pass the args directly to git
        if (label.equalsIgnoreCase(CraftVersionConfig.Commands.CraftVersion.ConsoleAlias) && sender instanceof ConsoleCommandSender) {
            plugin.getFilesManager().executeGitCommand(args);
            return true;
        }

        // Otherwise use craftversion subcommands
        if (args.length == 0) {
            return false;
        }

        // TODO: Implement subcommands for non-git users
        switch (args[0]) {
            case CraftVersionConfig.Commands.CraftVersion.SubCommands.Status:
                plugin.getLogger().info("Should get git status to see if there's no changes");
                return true;
        }

        return false;
    }

}
