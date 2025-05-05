package de.codedbygruba.banStorm.listeners;

import com.velocitypowered.api.event.Subscribe;
import com.velocitypowered.api.event.connection.PostLoginEvent;
import com.velocitypowered.api.proxy.Player;
import de.codedbygruba.banStorm.repository.BanRepository;
import net.kyori.adventure.text.Component;

public class BanListener {

    private final BanRepository banRepository = BanRepository.getInstance();
    
    @Subscribe
    public void onPostLogin(PostLoginEvent event) {
        //TODO: Rework Ban Message
        Player player = event.getPlayer();

        if (banRepository.isBanned(player.getUniqueId())) {
            player.disconnect(Component.text("§cYou have been banned!"));
        }
    }
}
