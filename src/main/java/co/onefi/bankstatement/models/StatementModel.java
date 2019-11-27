package co.onefi.bankstatement.models;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class StatementModel implements Serializable {

    private int pageCount;
    private String accountName;
    private Date printDate;
    private String accountNumber;
    private Date startStatementDateTime;
    private Date endStatementDateTime;
    private String address;
    private String branchName;
    private String branchAddress;
    private String currencyType;
    private long openingBalance;
    private long closingBalance;
    private AccountType accountType;
    private int totalDebit;
    private int totalCredit;
    private long totalDebitAmount;
    private long totalCreditAmount;
    private long unclearedBalance;
    private String clientId;
    private String fileObjectName;
    private ResponseMessage responseMessage;
    private String bankCode;
    private List<TransactionModel> transactions = new LinkedList<TransactionModel>();
    private final static long serialVersionUID = 3850022291510948128L;
    private Source source;
}
