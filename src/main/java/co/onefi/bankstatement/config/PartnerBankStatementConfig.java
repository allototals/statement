/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.onefi.bankstatement.config;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PositiveOrZero;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;
import org.springframework.validation.annotation.Validated;

/**
 *
 * @author michael
 */
@Component
@ConfigurationProperties(prefix = "pbs")
@Data
@Validated
public class PartnerBankStatementConfig {

    @NotEmpty
    private String baseUrl;

    @NotEmpty
    private String username;

    @NotEmpty
    private String password;

    @PositiveOrZero
    private int startDays;
}
