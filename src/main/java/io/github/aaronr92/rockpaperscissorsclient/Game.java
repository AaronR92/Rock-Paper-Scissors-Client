package io.github.aaronr92.rockpaperscissorsclient;

import io.github.aaronr92.rockpaperscissorsclient.model.FinishState;
import io.github.aaronr92.rockpaperscissorsclient.model.GameStepAction;

import java.util.Scanner;

public class Game {

    private boolean isRunning;
    public static boolean isLoggedIn;

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
            handleLoggedInInput(inputLine);
        } else {
            handleNotLoggedInInput(inputLine);
        }
        return "";
    }

    public void handleLoggedInInput(String input) {
        switch (input.toLowerCase()) {
            case "info" -> {
                System.out.println("ℹ️ If you are ready, type: start");
                System.out.println("You can also logout at any time \n" +
                        "and your progress will be saved! Type: logout");
            }
            case "start" -> {
                System.out.println("🤔 Looking for a new game...");
                network.sendGameStartPacket();
            }
            case "logout" -> {
                System.out.println("🚪 Logged out!");
                network.logout();
            }
            case "rock", "🪨" -> network.sendGameStepActionPacket(GameStepAction.ROCK);
            case "paper", "📄" -> network.sendGameStepActionPacket(GameStepAction.PAPER);
            case "scissors", "✂️" -> network.sendGameStepActionPacket(GameStepAction.SCISSORS);
        }

    }

    public void handleNotLoggedInInput(String input) {
        String[] inputArr = input.toLowerCase().split("=");
        switch (inputArr[0]) {
            case "info" -> {
                System.out.println("ℹ️ information");
                System.out.println("To sign in type: signin");
                System.out.println("To sign up type: signup\n");
            }
            case "signup" -> {
                if (inputArr.length < 3) {
                    System.out.println("❌ Type signup=yourLogin=yourPassword");
                    return;
                }
                this.network = new Network();
                network.sendSignUpPacket(inputArr[1], inputArr[2]);
            }
            case "signin" -> {
                if (inputArr.length < 3) {
                    System.out.println("❌ Type signup=yourLogin=yourPassword");
                    return;
                }
                this.network = new Network();
                network.sendSignInPacket(inputArr[1], inputArr[2]);
            }
            default -> {
                System.out.println("❌ First you need to sign in or sign up\n");
            }
        }
    }

    public static void printResults(FinishState finishState) {
        System.out.println("\n🎮 GAME IS OVER");
        System.out.println("It is " + finishState.name());

        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            System.out.println("Something went wrong");
        }
        System.out.println("\n-> Menu <- ");
    }

    public static void printRemainingTime(int seconds) {
        System.out.printf("⌛ You have %d seconds left\n", seconds);
    }

    public static void printServerChoice(GameStepAction action) {
        if (action != null)
            System.out.println("🤖 Server choice is " + action.name());
        else
            System.out.println("😑 You skipped round");
    }

    public static void printLogInResult() {
        if (isLoggedIn) {
            System.out.println("\n-> Menu <-");
            System.out.println("ℹ️ If you are ready, type: start");
            System.out.println("You can also logout at any time \n" +
                    "and your progress will be saved! Type: logout");
        } else {
            System.out.println("❌ Wrong credentials");
        }
    }

    public static void printStartGameMessage() {
        System.out.println("🎮 Game has started!");
        System.out.println("Choose 🪨-📄-✂️");
    }

}
