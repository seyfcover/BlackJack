public class Table {
    private int cash;
    private final String[] card;

    public Table(String[] card, int cash) {
        this.card = card;
        this.cash = cash;
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

    public void updateList(String hit, int index) {
        card[index] = hit;
    }
}
