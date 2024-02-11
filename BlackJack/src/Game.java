import java.util.Objects;
import java.util.Random;

public class Game {
    public String[] cards= {"2","3","4","5","6","7","8","9","10","J","Q","K","A"};
    String[] table = new String[10];
    String[] player = new String[10];
    public int lenX = 2,lenY = 2;
    Random gameRand = new Random();

    public String[] openTable()
    {
        table[0] = cards[gameRand.nextInt(13)];
        table[1] = cards[gameRand.nextInt(13)];
        return table;
    }

    public String[] openPlayer()
    {
        player[0] = cards[gameRand.nextInt(13)];
        player[1] = cards[gameRand.nextInt(13)];
        return player;
    }

    public int calcuteSum(String[]x,int len)
    {
        int numberofA =0;
        int sum = 0;
        for (int i = 0; i<len; i++)
        {
            if(Objects.equals(x[i], "A"))
            {
                numberofA++;
                sum += 11;
            }
            else if (Objects.equals(x[i], "K") || Objects.equals(x[i], "Q") || Objects.equals(x[i], "J"))
            {sum+=10;}

            else
            {sum+= Integer.parseInt(x[i]);}
            if (sum >21)
            {sum-=10*numberofA; numberofA--;}
        }
        return  sum;
    }

    public void hit(Player Y)
    {
        Y.updateList(cards[gameRand.nextInt(13)],lenY);
        lenY++;
    }

    public void hit(Table X)
    {
        X.updateList(cards[gameRand.nextInt(13)],lenX);
        lenX++;
    }
}
