package co.onefi.bankstatement.models;

//import com.sun.tools.javac.code.Attribute;
import lombok.Data;
import springfox.documentation.schema.Enums;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.Enumeration;
import javax.validation.constraints.NotEmpty;
//import javax.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class StatementInfoResponse {

    @NotEmpty
    String helpLink;
    @NotEmpty
    Source mode;
    String email;
    ResponseMessage responseMessage;
}
