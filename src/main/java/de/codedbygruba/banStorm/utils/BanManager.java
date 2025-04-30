package de.codedbygruba.banStorm.utils;

import com.velocitypowered.api.command.CommandSource;
import com.velocitypowered.api.proxy.Player;
import de.codedbygruba.banStorm.BanStorm;
import de.codedbygruba.banStorm.ban.BanBase;
import de.codedbygruba.banStorm.ban.PermBan;
import de.codedbygruba.banStorm.ban.TempBan;
import de.codedbygruba.banStorm.repository.BanRepository;

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

    public void ban(CommandSource source, String playerName, String reason) {
        handleBan(source, playerName, reason, null);
    }

    public void ban(CommandSource source, String playerName, String reason, String unbanTimeDaysString) {
        handleBan(source, playerName, reason, unbanTimeDaysString);
    }

    public void unban(CommandSource sender, String playerName) {
        banRepository.removeBan(mojangAPI.getUUID(playerName));
        broadcastUnBan(sender, playerName);
    }

    private void handleBan(CommandSource source, String playerName, String reason, String unbanTimeDaysString) {
        String senderName = (source instanceof Player player) ? player.getUsername() : "System";
        UUID senderUUID = (source instanceof Player player) ? player.getUniqueId() : null;

        UUID playerUUID = mojangAPI.getUUID(playerName);
        if (playerUUID == null) {
            source.sendMessage(plugin.mmDeserialize(MessageFormat.format(
                    "{0}<red>Player {1} not found!", plugin.banStormPrefix, playerName)));
            return;
        }

        BanBase ban;
        if (unbanTimeDaysString == null) {
            ban = new PermBan(playerName, playerUUID, senderName, senderUUID, reason, LocalDateTime.now());
        } else {
            int days;
            try {
                days = Integer.parseInt(unbanTimeDaysString);
            } catch (NumberFormatException e) {
                source.sendMessage(plugin.mmDeserialize(MessageFormat.format(
                        "{0}<red>Usage: /ban [player] [reason] [time in days]", plugin.banStormPrefix)));
                return;
            }
            ban = new TempBan(playerName, playerUUID, senderName, senderUUID, reason,
                    LocalDateTime.now(), LocalDateTime.now().plusDays(days));
        }

        banRepository.addBan(ban);
        broadcastBan(ban);
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
                    ban.getReason()
                );
        broadcastMessage(broadcastMessage);
    }

    private void broadcastUnBan(CommandSource sender, String playerName) {
        String broadcastMessage = MessageFormat.format("{0}{1} was unbanned by {2}",
                plugin.banStormPrefix,
                playerName,
                sender instanceof  Player ? ((Player) sender).getUsername() : "System"
        );
        broadcastMessage(broadcastMessage);
    }
    private void broadcastMessage(String message) {
        for (Player p : plugin.getServer().getAllPlayers()) {
            //TODO: Permission control
            p.sendMessage(plugin.mmDeserialize(message));
        }
    }
}
