package co.onefi.bankstatement.models;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

@Data
@AllArgsConstructor
public class UploadToS3Request {

    @NotNull
    private byte[] file;
    @NotEmpty
    private String clientId;
    @NotEmpty
    private String bankCode;
    @NotEmpty
    private String accountNo;
}
