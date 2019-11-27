package co.onefi.bankstatement.models;

//import lombok.AllArgsConstructor;
import lombok.Data;

@Data
public class UploadFileResponse {

    private String message;
    private ResponseMessage responseMessage;
}
