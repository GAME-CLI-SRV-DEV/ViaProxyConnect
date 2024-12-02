package com.example.viaproxy;

import com.velocitypowered.api.command.CommandSource;
import com.velocitypowered.api.command.SimpleCommand;
import com.velocitypowered.api.plugin.Plugin;
import com.velocitypowered.api.plugin.annotation.DataDirectory;
import com.velocitypowered.api.proxy.ProxyServer;
import com.velocitypowered.api.proxy.server.RegisteredServer;
import com.velocitypowered.api.proxy.server.ServerInfo;
import org.slf4j.Logger;

import javax.inject.Inject;
import java.nio.file.Path;
import java.util.Optional;

@Plugin(id = "viaproxy", name = "ViaProxy", version = "1.0", description = "A plugin to connect to ViaProxy servers")
public class ViaProxyConnect {

    private final ProxyServer server;
    private final Logger logger;
    private final Path dataDirectory;

    @Inject
    public ViaProxyPlugin(ProxyServer server, Logger logger, @DataDirectory Path dataDirectory) {
        this.server = server;
        this.logger = logger;
        this.dataDirectory = dataDirectory;
    }

    @Subscribe
    public void onProxyInitialization(ProxyInitializeEvent event) {
        server.getCommandManager().register("viaproxy", new ViaProxyCommand(server, logger));
    }

    public static class ViaProxyCommand implements SimpleCommand {

        private final ProxyServer server;
        private final Logger logger;

        public ViaProxyCommand(ProxyServer server, Logger logger) {
            this.server = server;
            this.logger = logger;
        }

        @Override
        public void execute(Invocation invocation) {
            CommandSource source = invocation.source();
            String[] args = invocation.arguments();

            if (args.length != 3) {
                source.sendMessage(Component.text(+ "VP |" + + "Usage: /viaproxy <address> <port> <version>"));
                return;
            }

            String address = args[0];
            String port = args[1];
            String version = args[2];
            String hostname = "lenni0451.net"; // My Friend's Domain
            String port = 38888 // address_port_version.viaproxy.lenni0451.net:38888 really do exist. if you use it on local server then CaseOh is going to sit on you :skull:

            String target = String.format("%s_%s_%s.viaproxy.%s", address, port, version, hostname, hostport);
            Optional<RegisteredServer> serverOptional = server.getServer(target);

            if (serverOptional.isPresent()) {
                source.sendMessage(Component.text("Connecting to " + target));
                player.createConnectionRequest(serverOptional.get()).fireAndForget();
            } else {
                source.sendMessage(Component.text("Server not found, try again." + target));
            }
        }
    }
}
