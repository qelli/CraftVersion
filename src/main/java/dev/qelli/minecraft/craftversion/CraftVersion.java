package dev.qelli.minecraft.craftversion;

import org.bukkit.plugin.java.JavaPlugin;

import dev.qelli.minecraft.craftversion.git.managers.GitManager;

public final class CraftVersion extends JavaPlugin {

    GitManager gitManager;

    @Override
    public void onEnable() {

        if(!getDataFolder().exists()) {
            getDataFolder().mkdir();
            saveDefaultConfig();
        }

        this.gitManager = new GitManager(this);
        this.gitManager.init();

        getLogger().info("CraftVersion enabled! Using GIT: " + this.gitManager.getVersion());
    }

    @Override
    public void onDisable() {}

}
