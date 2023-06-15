package io.github.aaronr92.rockpaperscissorsclient.packet.client;

import io.github.aaronr92.rockpaperscissorsclient.model.GameStepAction;

public record ClientboundPlayerGameStepActionPacket(long playerId, GameStepAction action) {
}
