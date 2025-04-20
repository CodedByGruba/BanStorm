package de.codedbygruba.banStorm.ban;

import java.time.LocalDateTime;
import java.util.UUID;

public class PermBan extends BanBase {
    public PermBan(String playerName, UUID playerUUID, String operatorName, UUID operatorUUID, String reason, LocalDateTime banTime) {
        super(playerName, playerUUID, operatorName, operatorUUID, reason, banTime);
    }
}
