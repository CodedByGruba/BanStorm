package de.codedbygruba.banStorm.commands;

import com.velocitypowered.api.command.CommandSource;
import com.velocitypowered.api.command.SimpleCommand;
import de.codedbygruba.banStorm.ban.TempBan;
import de.codedbygruba.banStorm.repository.BanRepository;
import net.kyori.adventure.text.Component;

public class DebugCommandHandler implements SimpleCommand {
    private final BanRepository banRepository = BanRepository.getInstance();

    @Override
    public void execute(Invocation invocation) {
        CommandSource source = invocation.source();

        var bans = banRepository.getAllBans();

        for (var ban : bans) {
            source.sendMessage(Component.text(ban instanceof TempBan));
        }
    }
}
