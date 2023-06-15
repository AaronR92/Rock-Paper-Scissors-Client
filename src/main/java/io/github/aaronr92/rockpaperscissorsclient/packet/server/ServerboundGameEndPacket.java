package io.github.aaronr92.rockpaperscissorsclient.packet.server;

import io.github.aaronr92.rockpaperscissorsclient.model.FinishState;

public record ServerboundGameEndPacket(FinishState gameResult) {}
