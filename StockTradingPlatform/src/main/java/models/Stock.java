package models;

public class Stock {
    private String symbol;
    private int quantity;
    private double purchasePrice;

    // Constructor
    public Stock(String symbol, int quantity, double purchasePrice) {
        this.symbol = symbol;
        this.quantity = quantity;
        this.purchasePrice = purchasePrice;
    }

    // Getters
    public String getSymbol() {
        return symbol;
    }

    public int getQuantity() {
        return quantity;
    }

    public double getPurchasePrice() {
        return purchasePrice;
    }

    // Setters (for future updates like selling or buying more stocks)
    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public void setPurchasePrice(double purchasePrice) {
        this.purchasePrice = purchasePrice;
    }

    // Utility method to calculate total value of stocks
    public double getTotalValue() {
        return quantity * purchasePrice;
    }

    // Display stock details (useful for debugging or logs)
    @Override
    public String toString() {
        return "Stock{" +
               "symbol='" + symbol + '\'' +
               ", quantity=" + quantity +
               ", purchasePrice=" + purchasePrice +
               '}';
    }
}
