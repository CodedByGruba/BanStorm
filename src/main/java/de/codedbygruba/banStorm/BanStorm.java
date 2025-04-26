package de.codedbygruba.banStorm;

import com.google.inject.Inject;
import com.velocitypowered.api.event.proxy.ProxyInitializeEvent;
import com.velocitypowered.api.event.Subscribe;
import com.velocitypowered.api.plugin.Plugin;
import com.velocitypowered.api.plugin.annotation.DataDirectory;
import com.velocitypowered.api.proxy.ProxyServer;
import lombok.Data;
import lombok.Getter;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.minimessage.MiniMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.nio.file.Path;

@Plugin(
    id = "banstorm",
    name = "BanStorm",
    version = "1.0"
    ,authors = {"grubabua"}
)
public class BanStorm {
    //TODO: Permissions
    public final String banStormPrefix = "<gray>[</gray><gradient:#8e2de2:#4a00e0>Banstorm</gradient><gray>]</gray>";

    @Getter
    private static BanStorm instance;

    @Inject
    private Logger logger;

    @Inject @Getter
    private ProxyServer server;

    @DataDirectory @Getter
    private Path path;

    @Subscribe
    public void onProxyInitialization(ProxyInitializeEvent event) {
        instance = this;
    }

    public Component mmDeserialize(String message) {
        return MiniMessage.miniMessage().deserialize(message);
    }
}
