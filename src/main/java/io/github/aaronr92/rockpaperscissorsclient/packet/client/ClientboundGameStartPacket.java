package io.github.aaronr92.rockpaperscissorsclient.packet.client;

public record ClientboundGameStartPacket(String login, String password) {
}
