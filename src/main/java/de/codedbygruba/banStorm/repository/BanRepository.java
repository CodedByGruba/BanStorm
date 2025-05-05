package de.codedbygruba.banStorm.repository;

import com.fasterxml.jackson.databind.ObjectMapper;
import de.codedbygruba.banStorm.ban.BanBase;

import com.fasterxml.jackson.databind.ObjectWriter;
import com.fasterxml.jackson.databind.type.CollectionType;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

public class BanRepository {

    private static BanRepository instance;

    public static void init(Path path) {
        if (instance == null) {
            instance = new BanRepository(path);
        }
    }

    public static BanRepository getInstance() {
        if (instance == null) {
            System.err.println("BanRepository instance was not created! Have you called BanRepository#init() first?");
        }
        return instance;
    }

    private final File storageFile;
    private final ObjectMapper objectMapper;
    private final Map<UUID, BanBase> banMap = new ConcurrentHashMap<>();

    public BanRepository(Path filePath) {
        try {
            Files.createDirectories(filePath);
        } catch (IOException e) {
            System.err.println("Could not create config folder" + e.getMessage());
        }

        this.storageFile = new File(filePath.resolve("banned_players.json").toString());
        this.objectMapper = new ObjectMapper().registerModule(new JavaTimeModule());
        load();
    }

    public void addBan(BanBase ban) {
        banMap.put(ban.getPlayerUUID(), ban);
        save();
    }

    public void removeBan(UUID playerUUID) {
        banMap.remove(playerUUID);
        save();
    }
    public Collection<BanBase> getAllBans() {
        return Collections.unmodifiableCollection(banMap.values());
    }

    public boolean isBanned(UUID playerUUID) {
        return banMap.containsKey(playerUUID);
    }
    

    private void save() {
        try {
            ObjectWriter writer = objectMapper.writerWithDefaultPrettyPrinter();
            writer.writeValue(storageFile, banMap.values());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void load() {
        if (!storageFile.exists()) return;
        try {
            CollectionType type = objectMapper.getTypeFactory()
                    .constructCollectionType(List.class, BanBase.class);
            List<BanBase> bans = objectMapper.readValue(storageFile, type);
            bans.forEach(ban -> banMap.put(ban.getPlayerUUID(), ban));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}