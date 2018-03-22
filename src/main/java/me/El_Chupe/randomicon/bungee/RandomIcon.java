package me.El_Chupe.randomicon.bungee;

import net.md_5.bungee.api.Favicon;
import net.md_5.bungee.api.event.ProxyPingEvent;
import net.md_5.bungee.event.EventHandler;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class RandomIcon extends net.md_5.bungee.api.plugin.Plugin implements net.md_5.bungee.api.plugin.Listener {
    List<Favicon> icons = new ArrayList<Favicon>();
    @Override
    public void onEnable() {
        getProxy().getPluginManager().registerListener(this, this);

        File iconsDir = new File(getDataFolder() + "/icons");
        if(!iconsDir.exists()) iconsDir.mkdirs();
        File[] iconFiles = iconsDir.listFiles();
        if(iconFiles != null) {
            for(File file : iconFiles)
                try {
                    BufferedImage bimg = ImageIO.read(file);
                    if (bimg.getHeight() == 64 && bimg.getWidth() == 64) {
                        icons.add(Favicon.create(bimg));
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
    public void onPing(ProxyPingEvent event) {
        if(icons.size() > 0)
            event.getResponse().setFavicon(icons.get((int)(Math.random() * icons.size())));
    }
}
