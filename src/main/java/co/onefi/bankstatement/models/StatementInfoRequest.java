package co.onefi.bankstatement.models;

import javax.validation.constraints.NotEmpty;
import lombok.Data;

@Data
public class StatementInfoRequest {

    @NotEmpty
    private String clientId;
    @NotEmpty
    private String bankCode;
    @NotEmpty
    private String accountNo;

    public StatementInfoRequest() {
    }

    public StatementInfoRequest(String clientId, String bankCode, String accountNo) {
        this.clientId = clientId;
        this.bankCode = bankCode;
        this.accountNo = accountNo;
    }
}
