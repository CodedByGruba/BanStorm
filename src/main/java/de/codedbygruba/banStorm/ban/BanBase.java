package de.codedbygruba.banStorm.ban;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter @Setter
@JsonTypeInfo(
        use = JsonTypeInfo.Id.NAME,
        property = "type",
        visible = true
)
@JsonSubTypes({
        @JsonSubTypes.Type(value = PermBan.class, name = "PERM"),
        @JsonSubTypes.Type(value = TempBan.class, name = "TEMP")
})
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
