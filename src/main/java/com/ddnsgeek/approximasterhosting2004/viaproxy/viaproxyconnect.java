package com.example.viaproxy;

import com.velocitypowered.api.command.CommandSource;
import com.velocitypowered.api.command.SimpleCommand;
import com.velocitypowered.api.proxy.Player;
import com.velocitypowered.api.proxy.ProxyServer;
import com.velocitypowered.api.proxy.server.RegisteredServer;
import com.velocitypowered.api.event.connection.PreLoginEvent;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.event.ClickEvent;
import net.kyori.adventure.text.event.HoverEvent;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Command to list and switch a player between available servers.
 */
public class ViaProxyConnect implements SimpleCommand {

    private final ProxyServer proxy;

    public CommandServer(ProxyServer proxy) {
        this.proxy = proxy;
    }

    @Override
    public void execute(Invocation invocation) {
        CommandSource source = invocation.source();
        String[] args = invocation.arguments();

        if (args.length == 0) {
            if (source instanceof Player) {
                Player player = (Player) source;
                source.sendMessage(Component.text("VP | Usage: viaproxy address port version");
            } else if (!(source instanceof Player)) {
                return;
            }
            Player player = (Player) source;
            String address = args[0];
            String port = args[1];
            String version = args[2];
            String hostname = "lenni0451.net"; // My Friend's Domain
            String port = 38888 // address_port_version.viaproxy.lenni0451.net:38888 really do exist. if you use it on local server then CaseOh is going to sit on you :skull:

            String server = String.format("%s_%s_%s.viaproxy.%s:%s", address, port, version, hostname, hostport);
            Optional<RegisteredServer> serverOptional = server.getServer(target);

            RegisteredServer server = proxy.getServer(args[0]).orElse(null);
                player.createConnectionRequest(server).connect();
        }
    }

    @Override
    public List<String> suggest(Invocation invocation) {
        String[] args = invocation.arguments();
        if (args.length > 1) {
            return List.of();
        }

        return proxy.getAllServers().stream()
                .map(server -> server.getServerInfo().getName())
                .filter(name -> name.toLowerCase().startsWith(args.length == 0 ? "" : args[0].toLowerCase()))
                .collect(Collectors.toList());
    }
}
