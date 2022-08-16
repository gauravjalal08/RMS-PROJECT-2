package entities;

import java.util.ArrayList;
import java.util.List;

public class Account {
    // total sales
    private int sales = 0;
    private double amount;

    // profit will not include initial amount
    private double profit = 0;
    private double expenses = 0;

    private List<Transaction> transactions = new ArrayList<>();

    public Account (double amount) {
        this.amount = amount;
        this.profit = 0;
    }

    public void addTransaction (Transaction transaction) throws Exception {
        this.transactions.add(transaction);
        if (transaction.getTransactionType() == TransactionType.CREDIT) {
            this.amount += transaction.getAmount();
            this.profit += transaction.getAmount();
            this.sales += 1;
        } else if (transaction.getTransactionType() == TransactionType.DEBIT) {
            this.amount -= transaction.getAmount();
            this.profit -= transaction.getAmount();
            this.expenses += transaction.getAmount();
        } else {
            throw new Exception("Invalid Transaciton made!");
        }
    }

    public double getProfit() {
        return profit;
    }

    public double getAmount() {
        return amount;
    }

    public int getSales() {
        return sales;
    }

    public double getExpenses() {
        return expenses;
    }
}