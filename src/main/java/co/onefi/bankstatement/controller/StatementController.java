package co.onefi.bankstatement.controller;

//import co.onefi.bankstatement.Services.StatementService;
import co.onefi.bankstatement.impl.exceptions.BankStatementException;
import co.onefi.bankstatement.impl.StatementProcessorServiceImpl;
import co.onefi.bankstatement.models.*;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
//import java.util.concurrent.atomic.AtomicLong;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/statement")
public class StatementController {

    @Autowired
    private StatementProcessorServiceImpl statementService;

    @RequestMapping(value = "/info", method = RequestMethod.GET)
    public ResponseEntity<StatementInfoResponse> uploadInfo(@Valid @ModelAttribute StatementInfoRequest statementInfoRequest) {

        StatementInfoResponse response = statementService.checkIfUploadIsRequired(statementInfoRequest);
        if (response.getResponseMessage().equals(ResponseMessage.SUCCESS)) {
            return new ResponseEntity<>(response, HttpStatus.OK);
        }
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    @RequestMapping(value = "/upload-file", method = RequestMethod.POST)
    public ResponseEntity<UploadFileResponse> uploadFile(@Valid @RequestBody UploadFileRequest uploadFileRequest) {
        UploadFileResponse response = statementService.processBankStatements(uploadFileRequest);
        if (response.getResponseMessage().equals(ResponseMessage.SUCCESS)) {
            return new ResponseEntity<>(response, HttpStatus.OK);
        }
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    @GetMapping(value = "/by-client/{clientId}")
    @CrossOrigin
    public StatementDataResponse getBankStatement(@Valid @ModelAttribute StatementDataRequest statementDataRequest, @PathVariable String clientId) {
        StatementDataResponse statementDataResponse = statementService.getBankStatement(statementDataRequest, clientId);
        return statementDataResponse;

    }
}
