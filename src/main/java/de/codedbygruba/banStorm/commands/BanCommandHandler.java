package de.codedbygruba.banStorm.commands;

import com.velocitypowered.api.command.CommandSource;
import com.velocitypowered.api.command.SimpleCommand;
import com.velocitypowered.api.proxy.Player;
import de.codedbygruba.banStorm.utils.BanManager;
import de.codedbygruba.banStorm.utils.MojangAPI;

public class BanCommandHandler implements SimpleCommand {

    private final BanManager banManager = BanManager.getInstance();
    private final MojangAPI api = MojangAPI.getInstance();

    @Override
    public void execute(Invocation invocation) {
        CommandSource source = invocation.source();
        Player player = (Player) source;


    }
}
