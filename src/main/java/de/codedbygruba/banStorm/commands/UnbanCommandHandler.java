package de.codedbygruba.banStorm.commands;

import com.velocitypowered.api.command.CommandSource;
import com.velocitypowered.api.command.SimpleCommand;
import de.codedbygruba.banStorm.BanStorm;
import de.codedbygruba.banStorm.utils.BanManager;

import java.text.MessageFormat;

public class UnbanCommandHandler implements SimpleCommand {

    private final BanManager banManager = BanManager.getInstance();
    private final BanStorm plugin = BanStorm.getInstance();

    @Override
    public void execute(Invocation invocation) {
        //TODO: Permission Control

        CommandSource source = invocation.source();
        String[] args = invocation.arguments();

        if (args.length != 1) {
            source.sendMessage(plugin.mmDeserialize(MessageFormat.format("{0}<red>Usage: /unban [Player]", plugin.banStormPrefix)));
        }

        banManager.unban(source, args[0]);
    }
}
