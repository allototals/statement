/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.onefi.bankstatement.models;

import java.io.Serializable;
import lombok.Data;

/**
 *
 * @author michael
 */
@Data
public class StatementParserRequest implements Serializable {

    private byte[] statement;
    private String bankCode;
}
