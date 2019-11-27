package co.onefi.bankstatement.entities;

import co.onefi.bankstatement.models.AccountType;
import co.onefi.bankstatement.models.Source;
import lombok.*;
import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "bank_statement")
@Data
@EqualsAndHashCode(callSuper = true)
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class StatementEntity extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "client_id", nullable = false)
    private String clientId;

    @Column(name = "page_count", nullable = false)
    private int pageCount;

    @Column(name = "account_name", nullable = false)
    private String accountName;

    @Column(name = "print_date", nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date printDate;

    @Column(name = "account_number", nullable = false)
    private String accountNumber;

    @Column(name = "start_statement_datetime", nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date startStatementDateTime;

    @Column(name = "end_statement_datetime", nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date endStatementDateTime;

    @Column(name = "address")
    private String address;

    @Column(name = "branch_name")
    private String branchName;

    @Column(name = "branch_address")
    private String branchAddress;

    @Column(name = "currency_type", nullable = false)
    private String currencyType;

    @Column(name = "opening_balance", nullable = false)
    private long openingBalance;

    @Column(name = "closing_balance", nullable = false)
    private long closingBalance;

    @Column(name = "account_type", nullable = false)
    private AccountType accountType = AccountType.SAVINGS;

    @Column(name = "total_debit", nullable = false)
    private int totalDebit;

    @Column(name = "total_credit", nullable = false)
    private int totalCredit;

    @Column(name = "total_debit_amount", nullable = false)
    private long totalDebitAmount;

    @Column(name = "total_credit_amount", nullable = false)
    private long totalCreditAmount;

    @Column(name = "uncleared_balance", nullable = false)
    private long unclearedBalance;

    @Column(name = "filehash", nullable = false)
    private String fileHash;
    @Enumerated(EnumType.STRING)
    private Source source;
    @Column(name = "file_object_name", nullable = false)
    private String fileObjectName;
    @Column(name = "bank_code", nullable = true)
    private String bankCode;
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "statementsEntity", cascade = CascadeType.ALL)
    private List<TransactionEntity> transactions;
    private final static long serialVersionUID = 3850083291510948128L;
}
