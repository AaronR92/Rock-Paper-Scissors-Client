package io.github.aaronr92.rockpaperscissorsclient;

import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryonet.Client;
import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;
import io.github.aaronr92.rockpaperscissorsclient.model.FinishState;
import io.github.aaronr92.rockpaperscissorsclient.model.GameStepAction;
import io.github.aaronr92.rockpaperscissorsclient.packet.client.ClientboundConnectionPacket;
import io.github.aaronr92.rockpaperscissorsclient.packet.client.ClientboundGameStartPacket;
import io.github.aaronr92.rockpaperscissorsclient.packet.client.ClientboundPlayerGameStepActionPacket;
import io.github.aaronr92.rockpaperscissorsclient.packet.server.*;

import java.io.IOException;

public class Network {

    private static final String SERVER_IP = "127.0.0.1";
    private static final int TCP_PORT = 54555;

    private final Client client;
    private String login;
    private String password;
    private long playerId;

    public Network() {
        this.client = new Client();
        Kryo kryo = client.getKryo();

        // Classes
        kryo.register(GameStepAction.class);
        kryo.register(FinishState.class);

        // Clientbound packets
        kryo.register(ClientboundConnectionPacket.class);
        kryo.register(ClientboundGameStartPacket.class);
        kryo.register(ClientboundPlayerGameStepActionPacket.class);

        // Serverbound packets
        kryo.register(ServerboundConnectionPacket.class);
        kryo.register(ServerboundGameEndPacket.class);
        kryo.register(ServerboundGameStartPacket.class);
        kryo.register(ServerboundRemainingTimePacket.class);
        kryo.register(ServerboundServerChoicePacket.class);

        client.addListener(new Listener() {
            @Override
            public void received(Connection connection, Object object) {
                if (object instanceof ServerboundConnectionPacket packet) {
                    String result = packet.result();
                    Game.isLoggedIn = result.equals("authenticated");
                    Game.printLogInResult();
                } else if (object instanceof ServerboundGameStartPacket packet) {
                    playerId = packet.playerId();
                    Game.printStartGameMessage();
                } else if (object instanceof ServerboundGameEndPacket packet) {
                    Game.printResults(packet.gameResult());
                } else if (object instanceof ServerboundRemainingTimePacket packet) {
                    Game.printRemainingTime(packet.seconds());
                } else if (object instanceof ServerboundServerChoicePacket packet) {
                    Game.printServerChoice(packet.action());
                }
            }
        });

        try {
            client.start();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public void sendSignInPacket(String login, String password) {
        try {
            ClientboundConnectionPacket packet = new ClientboundConnectionPacket(login, password, false);
            client.connect(5000, SERVER_IP, TCP_PORT);
            client.sendTCP(packet);

            this.login = login;
            this.password = password;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void sendSignUpPacket(String login, String password) {
        try {
            ClientboundConnectionPacket packet = new ClientboundConnectionPacket(
                    login,
                    password,
                    true
            );
            client.connect(5000, SERVER_IP, TCP_PORT);
            client.sendTCP(packet);

            this.login = login;
            this.password = password;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void sendGameStartPacket() {
        try {
            ClientboundGameStartPacket packet = new ClientboundGameStartPacket(
                    login,
                    password
            );
            client.connect(5000, SERVER_IP, TCP_PORT);
            client.sendTCP(packet);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void sendGameStepActionPacket(GameStepAction action) {
        var packet = new ClientboundPlayerGameStepActionPacket(playerId, action);
        client.sendTCP(packet);
    }

    public void logout() {
        client.close();
        Game.isLoggedIn = false;
    }

}
