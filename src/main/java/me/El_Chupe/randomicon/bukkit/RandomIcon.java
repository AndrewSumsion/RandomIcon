package me.El_Chupe.randomicon.bukkit;

import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.server.ServerListPingEvent;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.util.CachedServerIcon;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class RandomIcon extends JavaPlugin implements Listener {
    private List<CachedServerIcon> icons = new ArrayList<CachedServerIcon>();
    @Override
    public void onEnable() {
        Bukkit.getPluginManager().registerEvents(this, this);
        File iconsDir = new File(getDataFolder() + "/icons");
        if(!iconsDir.exists()) iconsDir.mkdirs();
        File[] iconFiles = iconsDir.listFiles();
        if(iconFiles != null) {
            for(File file : iconFiles)
                try {
                    BufferedImage bimg = ImageIO.read(file);
                    if (bimg.getHeight() == 64 && bimg.getWidth() == 64) {
                        icons.add(Bukkit.getServer().loadServerIcon(bimg));
                    } else {
                        getLogger().warning("Cannot load " + file.getName() + ": incorrect size. Must be 64x64 pixels.");
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                }
            if(icons.size() > 0) {
                getLogger().info(icons.size() + " icons loaded.");
            } else {
                getLogger().warning("No icons found. Using default icon.");
            }
        }
    }
    @EventHandler
    public void onPing(ServerListPingEvent event) {
        if(icons.size() > 0)
            event.setServerIcon(icons.get((int)(Math.random() * icons.size())));
    }
}
