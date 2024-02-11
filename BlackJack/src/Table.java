public class Table {
    private int cash = 2000000;
    private final String[] card;

    public  Table(String [] card)
    {
        this.card = card;
    }

    public int getCash() {
        return cash;
    }

    public void updateCash(int cash) {
        this.cash += cash;
    }

    public String[] getCard() {
        return card;
    }

    public void updateList(String hit, int index)
    {
        card[index]= hit;
    }
}
