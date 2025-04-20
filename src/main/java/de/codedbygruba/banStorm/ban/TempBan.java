package de.codedbygruba.banStorm.ban;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.UUID;

public class TempBan extends BanBase {
    @Getter @Setter
    private LocalDateTime unbanTime;

    public TempBan(String playerName, UUID playerUUID, String operatorName, UUID operatorUUID, String reason, LocalDateTime banTime, LocalDateTime unbanTime) {
        super(playerName, playerUUID, operatorName, operatorUUID, reason, banTime);
        this.unbanTime = unbanTime;
    }
}
