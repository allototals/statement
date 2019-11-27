package co.onefi.bankstatement.entities;

import co.onefi.bankstatement.models.TransactionType;
import co.onefi.bankstatement.models.Source;
import lombok.*;

import javax.persistence.*;

@Entity
@Table(name = "bank_info")
@Data
//@EqualsAndHashCode(callSuper = true)
//@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BankInfoEntity extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "help_link", nullable = false)
    private String helpLink;

    @Column(name = "upload_type", nullable = false)
    @Enumerated(EnumType.STRING)
    private Source uploadType; // Email, Upload, etc.

    @Column(name = "bank_code", nullable = false)
    private String bankCode;
}
