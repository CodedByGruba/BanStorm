package de.codedbygruba.banStorm.commands;

import com.velocitypowered.api.command.CommandSource;
import com.velocitypowered.api.command.SimpleCommand;
import com.velocitypowered.api.proxy.Player;
import de.codedbygruba.banStorm.BanStorm;
import de.codedbygruba.banStorm.utils.BanManager;

import java.text.MessageFormat;
import java.util.Arrays;

public class BanCommandHandler implements SimpleCommand {

    private final BanStorm plugin = BanStorm.getInstance();
    private final BanManager banManager = BanManager.getInstance();

    @Override
    public void execute(Invocation invocation) {
        //TODO: Permission Control
        CommandSource source = invocation.source();
        Player player = (Player) source;
        String[] args = invocation.arguments();

        if (args.length > 2) {
            source.sendMessage(plugin.mmDeserialize(MessageFormat.format("{0}<red>Usage: /ban [player] [time in days (optional)] [reason]", plugin.banStormPrefix)));
            return;
        }

        if (tryParseInt(args[1])) {
            banManager.ban(source, args[0], String.join(" ", Arrays.copyOfRange(args, 2, args.length)), args[1]);
        } else {
            banManager.ban(source, args[0], String.join(" ", Arrays.copyOfRange(args, 1, args.length)));
        }
    }

    private boolean tryParseInt(String inputString) {
        try {
            Integer.parseInt(inputString);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }
}
