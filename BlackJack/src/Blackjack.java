
import java.util.Arrays;
import java.util.Objects;
import java.util.Scanner;

public class Blackjack {
    static Game game1 = new Game();
    static Table table1 = new Table(game1.openTable());
    static Player player1 = new Player(100,0,0, game1.openPlayer());
    static Scanner scan = new Scanner(System.in);
    static int betValue=0;

    public static void main(String[] args) {
        System.out.println("WELCOME TO MY BLACKJACK");
        play();
    }
    public static void reset()
    {
        game1 = new Game();
        table1 = new Table(game1.openTable());
        player1 = new Player(player1.getCash(), player1.getWin(), player1.getLose(), game1.openPlayer());
        play();
    }
    public static void play()
    {
        if (table1.getCash() <= 0)
        {
            System.out.println("YOU BEAT THE TABLE");
            end();
        }
        else if (player1.getCash()==0) {
            System.out.println("YOU LOST THEM ALL");
            end();
        }
        else
        {
            System.out.println("Money:" + player1.getCash());
            System.out.println("Enter your BET :");
            betValue = scan.nextInt();
            while (betValue > player1.getCash()) {
                System.out.println("NOT ENOUGH MONEY!");
                betValue = scan.nextInt();
            }
            start();
            System.out.println("HIT or STAY");
            int answer = scan.nextInt();
            while (answer != 0) {
                hit();
                answer = scan.nextInt();
            }
            stay();
            System.out.println("CONTINUE or CLOSE");
            answer = scan.nextInt();
            while (answer != 0) {
                reset();
                answer = scan.nextInt();
            }
            end();
        }
    }
    public static void start()
    {
        System.out.println("\nTABLE CARDS");
        System.out.println("*"+","+table1.getCard()[1]);
        System.out.println("\n");
        System.out.println("PLAYER CARDS");
        System.out.println(player1.getCard()[0]+","+player1.getCard()[1]);
        System.out.println("Player's hand: "+game1.calcuteSum(player1.getCard(),game1.lenY));
        System.out.println("\n");
    }
    public  static  void  hit()
    {
        game1.hit(player1);
        Arrays.stream(player1.getCard())
                .filter(Objects::nonNull)
                .forEach(e -> System.out.print(e + " "));
        int player = game1.calcuteSum(player1.getCard(), game1.lenY);
        if (player > 21)
        {
            System.out.println("\nBusted :"+player);
            System.out.println("Please press 0");
        }
        else if (player == 21)
        {
            System.out.println("Please press 0");
        }
        else
        {
            System.out.println("\nSum:"+player);
            System.out.println("HIT or STAY");
        }
    }
    public  static  void  stay()
    {
        System.out.println("TABLE PLAYING..");
        while (game1.calcuteSum(table1.getCard(), game1.lenX)<17)
        {
            game1.hit(table1);
        }
        Arrays.stream(table1.getCard())
                .filter(Objects::nonNull)
                .forEach(e -> System.out.print(e + " "));

        int table = game1.calcuteSum(table1.getCard(), game1.lenX);
        int player = game1.calcuteSum(player1.getCard(), game1.lenY);
        System.out.println("\nTable's hand:"+table);
        System.out.println("Player's hand:"+player);
        if (((table>player)&&(table<=21))||((player>21)&&(table!=player)))
        {
            System.out.println("LOST");
            player1.updateLose(1);
            player1.updateCash(-betValue);
            table1.updateCash(betValue);
        }
        else if (((table>=21)&&(player>=21))||(table==player))
        {
            System.out.println("DRAW");
        }
        else
        {
            System.out.println("WON!");
            player1.updateWin(1);
            player1.updateCash(betValue);
            table1.updateCash(-betValue);
        }
    }
    public static void end()
    {
        System.out.println("Table Cash : " + table1.getCash());
        System.out.println("Cash : " + player1.getCash());
        System.out.println("Win : " + player1.getWin());
        System.out.println("Lose : " + player1.getLose());
        System.out.println("THANKS FOR PLAYING");
        System.out.println("CLOSED");
        System.exit(1);
    }
}