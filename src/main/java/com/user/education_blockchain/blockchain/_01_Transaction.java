package com.user.education_blockchain.blockchain;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter

@Builder
public class _01_Transaction {

    public String from;
    public String to;
    public double amount;

    public _01_Transaction(String from, String to, double amount) {
        this.from = from;
        this.to = to;
        this.amount = amount;
    }


    // ToString
    @Override
    public String toString() {
        return "_01_Transaction {" +
                "from='" + from + '\'' +
                ", to='" + to + '\'' +
                ", amount=" + amount +
                "}";
    }
}
