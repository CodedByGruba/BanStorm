package de.codedbygruba.banStorm.commands;

import com.velocitypowered.api.command.CommandSource;
import com.velocitypowered.api.command.SimpleCommand;
import com.velocitypowered.api.proxy.Player;
import de.codedbygruba.banStorm.BanStorm;
import de.codedbygruba.banStorm.utils.BanManager;
import net.kyori.adventure.text.Component;

import java.text.MessageFormat;

public class BanCommandHandler implements SimpleCommand {

    private final BanStorm plugin = BanStorm.getInstance();
    private final BanManager banManager = BanManager.getInstance();

    @Override
    public void execute(Invocation invocation) {
        //TODO: Permission Conntrol
        CommandSource source = invocation.source();
        Player player = (Player) source;
        String[] args = invocation.arguments();

        switch (args.length) {
            case 2:
                banManager.ban(source, args[0], args[1]);
                break;
            case 3:
                banManager.ban(source, args[0], args[1], args[2]);
                break;
            default:
                source.sendMessage(plugin.mmDeserialize(MessageFormat.format("{0}<red>Usage: /ban [player] [reason] [time in days (optional)]", plugin.banStormPrefix)));
        }
    }
}
