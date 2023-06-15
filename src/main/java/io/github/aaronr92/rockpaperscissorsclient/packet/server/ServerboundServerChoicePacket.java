package io.github.aaronr92.rockpaperscissorsclient.packet.server;

import io.github.aaronr92.rockpaperscissorsclient.model.GameStepAction;

public record ServerboundServerChoicePacket(GameStepAction action) {
}
