package co.onefi.bankstatement.models;

import lombok.Data;

import java.util.Date;

@Data
public class TransactionModel {

    private Date transactionDate;
    private String referenceNumber;
    private Date valueDate;
    private long amount;
    private long previousBalance;
    private long balance;
    private String remarks;
    private TransactionType transactionType;
    private final static long serialVersionUID = -8806138906900360085L;

}
