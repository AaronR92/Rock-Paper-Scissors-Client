package io.github.aaronr92.rockpaperscissorsclient.packet.client;

public record ClientboundConnectionPacket(String login, String password, boolean needsRegistration) {}
