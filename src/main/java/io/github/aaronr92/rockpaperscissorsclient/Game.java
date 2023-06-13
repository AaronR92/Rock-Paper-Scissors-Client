package io.github.aaronr92.rockpaperscissorsclient;

import java.util.Scanner;

public class Game {

    private boolean isRunning;
    private boolean isLoggedIn;

    private final Scanner scanner;
    private Network network;

    public Game() {
        this.scanner = new Scanner(System.in);
        this.isLoggedIn = false;
    }

    public void run() {
        isRunning = true;

        System.out.println("Hi there 👋");
        System.out.println("Wanna play 🪨-📄-✂️❓");
        System.out.println("For ℹ️ info type: info");
        System.out.println("First you need to sign in or sign up\n");

        while (isRunning) {
            handleInput();
        }
    }

    public String handleInput() {
        String inputLine = scanner.nextLine().trim();

        if (isLoggedIn) {

        } else {
            handleNotLoggedInInput(inputLine);
        }
        return "";
    }

    public void handleLoggedInInput(String input) {

    }

    public void handleNotLoggedInInput(String input) {
        switch (input) {
            case "info" -> {
                System.out.println("ℹ️ information");
                System.out.println("To sign in type: signin");
                System.out.println("To sign up type: signup\n");
            }
            case "signup" -> {
                this.network = new Network();
            }
            case "signin" -> {
                this.network = new Network();
            }
            case "send" -> {
                this.network.sendPacket();
            }
            default -> {
                System.out.println("❌ First sign in or sign up\n");
            }
        }
    }

}
