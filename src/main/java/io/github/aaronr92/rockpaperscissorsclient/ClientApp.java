package io.github.aaronr92.rockpaperscissorsclient;

public class ClientApp {

    public static final int TCP_PORT = 54555;

    public static void main(String[] args) {

        var game = new Game();
        game.run();

    }

}
