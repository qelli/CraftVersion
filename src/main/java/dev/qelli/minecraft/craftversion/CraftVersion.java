package dev.qelli.minecraft.craftversion;

import org.bukkit.plugin.java.JavaPlugin;

import dev.qelli.minecraft.craftversion.commands.CraftVersionCommands;
import dev.qelli.minecraft.craftversion.managers.FilesManager;

public final class CraftVersion extends JavaPlugin {

    FilesManager filesManager;
    CraftVersionCommands commands;

    @Override
    public void onEnable() {

        if(!getDataFolder().exists()) {
            getDataFolder().mkdir();
            saveDefaultConfig();
        }

        this.filesManager = new FilesManager(this);

        if (!this.filesManager.isReady()) {
            getLogger().severe("Something went wrong while initializing the plugin.");
            getServer().getPluginManager().disablePlugin(this);
            return;
        }

        // Once GIT is successfully working, initialize commands
        this.commands = new CraftVersionCommands(this);

        // Check for clean working dir

        // Fetch remote

        // Check for changes

        // Suggest pull
        

    }

    @Override
    public void onDisable() {}

    public FilesManager getFilesManager() {
        return this.filesManager;
    }

}
