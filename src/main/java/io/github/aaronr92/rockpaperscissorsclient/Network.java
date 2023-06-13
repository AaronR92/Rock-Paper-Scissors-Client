package io.github.aaronr92.rockpaperscissorsclient;

import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;
import io.github.aaronr92.rockpaperscissorsclient.packet.Packet;

public class Network {

    private com.esotericsoftware.kryonet.Client client;

    public Network() {
        this.client = new com.esotericsoftware.kryonet.Client();
        Kryo kryo = client.getKryo();

        kryo.register(Packet.class);

        client.addListener(new Listener() {
            @Override
            public void received(Connection connection, Object object) {
                if (object instanceof Packet packet) {
                    System.out.println("Packet is " + packet);
                }
            }
        });

        try {
            client.start();
            client.connect(5000, "127.0.0.1", ClientApp.TCP_PORT);
            System.out.println("Connected ✅");
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public void sendPacket() {
        Packet packet = new Packet("Lets start!");
        client.sendTCP(packet);
    }
}
