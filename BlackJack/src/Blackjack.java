
import java.util.Arrays;
import java.util.Objects;
import java.util.Scanner;

public class Blackjack {
    static Game game1 = new Game();
    static Table table1 = new Table(game1.openTable(), 20000);
    static Player player1 = new Player(100, 0, 0, game1.openPlayer(), game1.splitplayer());
    static Scanner scan = new Scanner(System.in);
    static int betValue = 0;
    static int split = 0;
    static boolean raise = true, splitOn = false;

    public static void main(String[] args) {
        System.out.println("WELCOME TO MY BLACKJACK");
        play();
    }

    public static void reset() {
        game1 = new Game();
        table1 = new Table(game1.openTable(), table1.getCash());
        player1 = new Player(player1.getCash(), player1.getWin(), player1.getLose(), game1.openPlayer(), game1.splitplayer());
        raise = true;
        splitOn = false;
        play();
    }

    public static void play() {
        if (table1.getCash() <= 0) {
            System.out.println("YOU BEAT THE TABLE");
            end();
        } else if (player1.getCash() == 0) {
            System.out.println("YOU LOST THEM ALL");
            end();
        } else {
            System.out.println("**************\nMoney:" + player1.getCash());
            System.out.println("Enter your BET :");
            betValue = scan.nextInt();
            while ((betValue > player1.getCash()) || (betValue < 0)) {
                System.out.println("NOT ENOUGH MONEY!");
                betValue = scan.nextInt();
            }
            start();
            if (((game1.calcuteSum(player1.getCard(), game1.lenY) == 20) &&
                    (!Objects.equals(player1.getCard()[0], "A") &&
                            !Objects.equals(player1.getCard()[1], "A"))) || (Objects.equals(player1.getCard()[0], player1.getCard()[1]))) {
                if (betValue <= (player1.getCash() / 2)) {
                    System.out.println("SPLIT ?");
                    split = scan.nextInt();
                    if (split == 1) {
                        splitOn = true;
                        split();
                    }
                }
            }

            if (split == 0 && game1.calcuteSum(player1.getCard(), game1.lenY) == 21) {
                stay();
                reset();
            }
            if ((betValue <= player1.getCash() / 2) && raise && !splitOn) {
                System.out.println("HIT or STAY or DOUBLE(2X)");
            } else {
                System.out.println("HIT or STAY");
            }
            int answer;
            if (!(splitOn && game1.calcuteSum(player1.getCard(), game1.lenY)==21))
            {
                answer = scan.nextInt();
                while (answer != 0) {
                    if (answer == 2) {
                        raise = false;
                        hit();
                        stay();
                        reset();
                    }
                    hit();
                    if (game1.calcuteSum(player1.getCard(), game1.lenY) > 21) {
                        answer = 0;
                    }
                    else if (game1.calcuteSum(player1.getCard(), game1.lenY) == 21) {
                        answer= 0;

                    } else {
                        System.out.println("HIT or STAY");
                        answer = scan.nextInt();
                    }
                }
            }

            if (split == 1) {
                System.out.println("HIT or STAY");
                int res = scan.nextInt();
                while (res != 0) {
                    sHit();
                    if (game1.calcuteSum(player1.getSplittedcard(), game1.lenZ) > 21) {
                        res = 0;
                    } else if (game1.calcuteSum(player1.getSplittedcard(), game1.lenZ) == 21) {
                        res = 0;
                    } else {
                        System.out.println("HIT or STAY");
                        res = scan.nextInt();
                    }
                }
            }
            stay();
            split = 0;
            System.out.println("CONTINUE or CLOSE");
            answer = scan.nextInt();
            while (answer != 0) {
                reset();
                answer = scan.nextInt();
            }
            end();
        }
    }

    public static void start() {
        System.out.println("\nTABLE CARDS");
        System.out.println("*" + "," + table1.getCard()[1]);
        System.out.println("\n");
        System.out.println("PLAYER CARDS");
        System.out.println(player1.getCard()[0] + "," + player1.getCard()[1]);
        System.out.println("Player's hand: " + game1.calcuteSum(player1.getCard(), game1.lenY));
        System.out.println("\n");
    }

    public static void split() {
        System.out.println("\n Splitting ...");
        player1 = new Player(player1.getCash(), player1.getWin(), player1.getLose(), game1.firsthandPlayer(), game1.splitplayer());
        System.out.println("PLAYER CARDS");
        System.out.println(player1.getCard()[0] + "," + player1.getCard()[1] + "   " + player1.getSplittedcard()[0] + "," + player1.getSplittedcard()[1]);
        System.out.println("Player's hand: \n1. Hand:" + game1.calcuteSum(player1.getCard(), game1.lenY) + "  2. Hand:" + game1.calcuteSum(player1.getSplittedcard(), game1.lenZ));
    }

    public static void hit() {
        game1.hit(player1);
        Arrays.stream(player1.getCard())
                .filter(Objects::nonNull)
                .forEach(e -> System.out.print(e + " "));
        int player = game1.calcuteSum(player1.getCard(), game1.lenY);
        if (player > 21) {
            System.out.println("\nBusted :" + player);
            System.out.println("***************");
        } else if (player == 21) {
            System.out.println("  BlackJack!! ");
            System.out.println("***************");
        } else {
            System.out.println("\nSum:" + player);
        }
    }

    public static void sHit() {
        game1.hitStripped(player1);
        Arrays.stream(player1.getSplittedcard())
                .filter(Objects::nonNull)
                .forEach(e -> System.out.print(e + " "));
        int player = game1.calcuteSum(player1.getSplittedcard(), game1.lenZ);
        if (player > 21) {
            System.out.println("\nBusted :" + player);
        } else if (player == 21) {
            System.out.println("  BlackJack!! ");
            System.out.println("***************");
        } else {
            System.out.println("\nSum:" + player);
        }
    }

    public static void stay() {
        System.out.println("TABLE PLAYING..");
        while (game1.calcuteSum(table1.getCard(), game1.lenX) < 17) {
            game1.hit(table1);
        }
        Arrays.stream(table1.getCard())
                .filter(Objects::nonNull)
                .forEach(e -> System.out.print(e + " "));

        int table = game1.calcuteSum(table1.getCard(), game1.lenX);
        int player = game1.calcuteSum(player1.getCard(), game1.lenY);
        System.out.println("\nTable's hand:" + table);
        System.out.println("Player's hand:" + player);
        if (split == 1) {
            int secHandplayer = game1.calcuteSum(player1.getSplittedcard(), game1.lenZ);
            System.out.println("Player's second hand:" + secHandplayer);
            compare(table, player, raise);
            compare(table, secHandplayer, raise);
        } else {
            compare(table, player, raise);
        }
    }

    public static void compare(int table, int player, boolean raise) {
        if (((table > player) && (table <= 21)) || ((player > 21))) {
            System.out.println("LOST");
            player1.updateLose(1);
            if (!raise) {
                player1.updateCash(-betValue);
                table1.updateCash(betValue);
            }
            player1.updateCash(-betValue);
            table1.updateCash(betValue);
        } else if (table == player) {
            System.out.println("DRAW");
        } else {
            System.out.println("WON!");
            player1.updateWin(1);
            if (player == 21) {
                if (!raise) {
                    player1.updateCash(betValue / 2);
                    table1.updateCash(-betValue / 2);
                }
                player1.updateCash(betValue / 2);
                table1.updateCash(-betValue / 2);
            }
            if (!raise) {
                player1.updateCash(betValue);
                table1.updateCash(-betValue);
            }
            player1.updateCash(betValue);
            table1.updateCash(-betValue);
        }
    }

    public static void end() {
        System.out.println("Table Cash : " + table1.getCash());
        System.out.println("Cash : " + player1.getCash());
        System.out.println("Win : " + player1.getWin());
        System.out.println("Lose : " + player1.getLose());
        System.out.println("THANKS FOR PLAYING");
        System.out.println("CLOSED");
        System.exit(1);
    }
}