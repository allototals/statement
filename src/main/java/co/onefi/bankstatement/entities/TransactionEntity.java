/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.onefi.bankstatement.entities;

import co.onefi.bankstatement.models.TransactionType;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "bank_statement_transaction")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TransactionEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @Column(name = "transaction_date", nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date transactionDate;
    @Column(name = "reference_number", nullable = false)
    private String referenceNumber;
    @Column(name = "value_date", nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date valueDate;
    @Column(name = "amount", nullable = false)
    private long amount;
    @Transient
    private long previousBalance;
    @Column(name = "balance")
    private long balance;
    @Column(name = "remarks")
    private String remarks;
    @Column(name = "transaction_type", nullable = false)
    @Enumerated(EnumType.STRING)
    private TransactionType transactionType;

    @JoinColumn(name = "statement_id", insertable = true, updatable = true)
    @ManyToOne(fetch = FetchType.LAZY)
    private StatementEntity statementsEntity;

    private final static long serialVersionUID = -8906138906900360085L;

}
