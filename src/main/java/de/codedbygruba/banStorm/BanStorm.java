package de.codedbygruba.banStorm;

import com.google.inject.Inject;
import com.velocitypowered.api.command.CommandManager;
import com.velocitypowered.api.command.CommandMeta;
import com.velocitypowered.api.event.proxy.ProxyInitializeEvent;
import com.velocitypowered.api.event.Subscribe;
import com.velocitypowered.api.plugin.Plugin;
import com.velocitypowered.api.plugin.annotation.DataDirectory;
import com.velocitypowered.api.proxy.ProxyServer;
import de.codedbygruba.banStorm.commands.BanCommandHandler;
import de.codedbygruba.banStorm.repository.BanRepository;
import lombok.Getter;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.minimessage.MiniMessage;
import org.slf4j.Logger;

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

    @Getter
    private final ProxyServer server;
    private final Logger logger;
    private final Path path;

    @Inject
    public BanStorm(ProxyServer server, Logger logger, @DataDirectory Path path) {
        this.server = server;
        this.logger = logger;
        this.path = path;
    }
/*    @Inject
    private Logger logger;

    @Inject @Getter
    private ProxyServer server;

    @Inject @DataDirectory
    private Path path;*/

    @Subscribe
    public void onProxyInitialization(ProxyInitializeEvent event) {
        instance = this;
        BanRepository.init(path);
        registerCommands();
    }

    public Component mmDeserialize(String message) {
        return MiniMessage.miniMessage().deserialize(message);
    }

    private void registerCommands() {
        CommandManager cm = server.getCommandManager();

        CommandMeta banMeta = cm.metaBuilder("ban").plugin(this).build();
        cm.register(banMeta, new BanCommandHandler());
    }
}
