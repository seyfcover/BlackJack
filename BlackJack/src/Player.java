public class Player {

    private int cash;
    private int win, lose;
    private final String[] card;

    private final String[] splittedcard;

    public Player(int cash, int win, int lose, String[] card, String[] splittedcard) {
        this.cash = cash;
        this.win = win;
        this.lose = lose;
        this.card = card;
        this.splittedcard = splittedcard;
    }

    public int getCash() {
        return cash;
    }

    public void updateCash(int cash) {
        this.cash += cash;
    }

    public int getWin() {
        return win;
    }

    public void updateWin(int win) {
        this.win += win;
    }

    public int getLose() {
        return lose;
    }

    public void updateLose(int lose) {
        this.lose += lose;
    }

    public String[] getCard() {
        return card;
    }

    public String[] getSplittedcard() {
        return splittedcard;
    }

    public void getSplittedcard(String hit, int index) {
        splittedcard[index] = hit;
    }

    public void updateList(String hit, int index) {
        card[index] = hit;
    }
}
