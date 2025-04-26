package de.codedbygruba.banStorm.utils;

import com.mojang.brigadier.Message;
import com.velocitypowered.api.command.CommandSource;
import com.velocitypowered.api.proxy.Player;
import de.codedbygruba.banStorm.BanStorm;
import de.codedbygruba.banStorm.ban.BanBase;
import de.codedbygruba.banStorm.ban.PermBan;
import de.codedbygruba.banStorm.ban.TempBan;
import de.codedbygruba.banStorm.repository.BanRepository;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.minimessage.MiniMessage;

import java.text.MessageFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.UUID;

public class BanManager {
    //TODO: Make operations async
    private static BanManager instance;

    public static BanManager getInstance() {
        if (instance == null) {
            instance = new BanManager();
        }
        return instance;
    }

    private final BanRepository banRepository = BanRepository.getInstance();
    private final MojangAPI mojangAPI = MojangAPI.getInstance();
    private final BanStorm plugin = BanStorm.getInstance();
    private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM.dd.yyyy HH:mm");

    public void ban(CommandSource sender, String playerName, String reason) {
        var senderName = sender instanceof Player ? ((Player) sender).getUsername() : "System";
        var senderUUID = sender instanceof Player ? ((Player) sender).getUniqueId() : null;

        PermBan ban = new PermBan(playerName, UUID.fromString(mojangAPI.getUUID(playerName)), senderName, senderUUID, reason, LocalDateTime.now());

        banRepository.addBan(ban);
        broadcastBan(ban);
    }
    public void ban(CommandSource sender, String playerName, String reason, LocalDateTime unbanDate) {
        var senderName = sender instanceof Player ? ((Player) sender).getUsername() : "System";
        var senderUUID = sender instanceof Player ? ((Player) sender).getUniqueId() : null;

        TempBan ban = new TempBan(playerName, UUID.fromString(mojangAPI.getUUID(playerName)), senderName, senderUUID, reason, LocalDateTime.now(), unbanDate);

        banRepository.addBan(ban);
        broadcastBan(ban);
    }
    public void unban(String playerName, String reason) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    private void broadcastBan(BanBase ban) {
        String broadcastMessage = ban instanceof TempBan tempBan ?
                MessageFormat.format("{0}{1} was banned till {2} by {3}! Reason: {4}",
                        plugin.banStormPrefix,
                        tempBan.getPlayerName(),
                        tempBan.getUnbanDate().format(formatter),
                        tempBan.getOperatorName(),
                        tempBan.getReason())
                :
                MessageFormat.format("{0}{1} was banned permanently by {2}! Reason: {3}",
                    plugin.banStormPrefix,
                    ban.getPlayerName(),
                    ban.getOperatorName(),
                    ban.getReason());

        for (Player p : plugin.getServer().getAllPlayers()) {
            //TODO: Permission control
            p.sendMessage(plugin.mmDeserialize(broadcastMessage));
        }
    }
}
