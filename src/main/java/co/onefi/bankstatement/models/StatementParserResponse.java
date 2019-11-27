/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.onefi.bankstatement.models;

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
public class StatementParserResponse {

    private ResponseMessage responseMessage;
    private String bankCode;
    private StatementModel customerStatement;
}
