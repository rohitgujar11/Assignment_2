package assignment2.rohitgujar.assignment_2;

/**
 * Created by Rohit Gujar on 07-03-2018.
 */

public class Currency {
    public double getCurrencyDenomination() {
        return currencyDenomination;
    }

    public void setCurrencyDenomination(double currencyDenomination) {
        this.currencyDenomination = currencyDenomination;
    }

    public int getCurrencyCount() {
        return currencyCount;
    }

    public void setCurrencyCount(int currencyCount) {
        this.currencyCount = currencyCount;
    }

    public Currency(double currencyDenomination, int currencyCount) {
        this.currencyDenomination = currencyDenomination;
        this.currencyCount = currencyCount;
    }

    private double currencyDenomination;
    private int currencyCount;
}
