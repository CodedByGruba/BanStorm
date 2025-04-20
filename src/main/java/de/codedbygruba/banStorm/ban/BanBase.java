package de.codedbygruba.banStorm.ban;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter @Setter
public abstract class BanBase {
    public BanBase(String playerName, UUID playerUUID, String operatorName, UUID operatorUUID, String reason, LocalDateTime banTime) {
        this.playerName = playerName;
        this.playerUUID = playerUUID;
        this.operatorName = operatorName;
        this.operatorUUID = operatorUUID;
        this.reason = reason;
        this.banDate =  banTime;
    }

    private String playerName;
    private UUID playerUUID;
    private String operatorName;
    private UUID operatorUUID;
    private String reason;
    private LocalDateTime banDate;
}
