package co.onefi.bankstatement.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.Serializable;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Data
//@AllArgsConstructor

public class UploadFileRequest implements Serializable {

    @NotEmpty
    private String clientId;
    @NotEmpty
    private String bankCode;
    @NotEmpty
    private String bvn;
    @NotNull
    private byte[] file;
    @NotEmpty
    private String accountName;
    @NotEmpty
    private String accountNumber;
    private Source mode;// email or file.
}
