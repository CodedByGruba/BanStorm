package de.codedbygruba.banStorm.repository;

public class BanRepository {
    private static BanRepository instance;


    public static BanRepository getInstance() {
        if (instance == null) {
            instance = new BanRepository();
        }
        return instance;
    }

    public void addBan() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public void removeBan() {
        throw new UnsupportedOperationException("Not supported yet.");
    }
}
