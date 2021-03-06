/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.onefi.bankstatement.models;

import java.io.Serializable;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 *
 * @author michael
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AccountDetail implements Serializable {

    private String accountNumber;
    private String bankCode;
    private String customerName;
    private String customerEmail;
    private String customerPhoneNumber;
    private String bvn;
    private long currentBalance;

}
