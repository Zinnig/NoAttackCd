package net.zinnig.main;

import net.zinnig.listener.NoAttackCd;
import org.bukkit.plugin.java.JavaPlugin;

public class Main extends JavaPlugin {
    private static Main instance;
    @Override
    public void onEnable(){
        instance = this;
        System.out.println("Started ....");
        getServer().getPluginManager().registerEvents(new NoAttackCd(), this);
    }
    public void onDisable(){
        System.out.println("Bye ....");
    }

    public static Main getInstance() {
        return instance;
    }
}
