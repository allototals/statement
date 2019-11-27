/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.onefi.bankstatement.impl;

import co.onefi.bankstatement.impl.exceptions.BankStatementException;
import co.onefi.bankstatement.entities.BankInfoEntity;
import co.onefi.bankstatement.entities.StatementEntity;
import co.onefi.bankstatement.entities.TransactionEntity;
import co.onefi.bankstatement.impl.exceptions.AccountValidationException;
import co.onefi.bankstatement.impl.exceptions.BankStatementDuplicateFileException;
import co.onefi.bankstatement.impl.exceptions.BankStatementParseException;
import co.onefi.bankstatement.impl.exceptions.BankStatementQueryException;
import co.onefi.bankstatement.impl.exceptions.BankStatementUploadException;
import co.onefi.bankstatement.impl.utility.CustomClientHttpResponse;
import co.onefi.bankstatement.impl.utility.NameMatcher;
import co.onefi.bankstatement.models.PartnerBankStatementResponse;
import co.onefi.bankstatement.models.ResponseMessage;
import co.onefi.bankstatement.models.Source;
import co.onefi.bankstatement.models.StatementDataRequest;
import co.onefi.bankstatement.models.StatementDataResponse;
import co.onefi.bankstatement.models.StatementInfoRequest;
import co.onefi.bankstatement.models.StatementInfoResponse;
import co.onefi.bankstatement.models.StatementModel;
import co.onefi.bankstatement.models.StatementParserRequest;
import co.onefi.bankstatement.models.StatementParserResponse;
import co.onefi.bankstatement.models.TransactionModel;
import co.onefi.bankstatement.models.UploadFileRequest;
import co.onefi.bankstatement.models.UploadFileResponse;
import com.amazonaws.SdkClientException;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.Date;
import java.util.concurrent.TimeUnit;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import co.onefi.bankstatement.services.StatementService;
import co.onefi.bankstatement.services.BankInfoService;
import co.onefi.bankstatement.services.PartnerBankStatmentService;
import com.amazonaws.auth.DefaultAWSCredentialsProviderChain;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.IOUtils;
import org.joda.time.DateTime;
import org.springframework.beans.BeanUtils;
import org.springframework.http.HttpRequest;
import org.springframework.http.client.ClientHttpRequestExecution;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.util.DigestUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.client.DefaultResponseErrorHandler;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

/**
 *
 * @author michael
 */
@Service
@Slf4j
public class StatementProcessorServiceImpl extends Throwable {

    private final static ObjectMapper OBJECT_MAPPER = new ObjectMapper();

    @Autowired
    private StatementService statementsService;
    @Autowired
    private BankInfoService statementsInfoService;

    @Autowired
    private PartnerBankStatmentService partnerBankStatmentService;
    //Todo Add noOfDays to Config.
    @Value("${app.statements-validity-period}")
    private long statementValidityPeriod = 90;

    @Value("${parserService.baseUrl}")
    private String parserBaseUrl;

    @Value("${parserService.apiKey}")
    private String apiKey;

    @Value("${uploads.s3-bucket-name}")
    private String bucketName;

    @Value("${uploads.s3-region}")
    private String regionName = "us-west-2";

    @Value("${app.bank-statements-email}")
    private String bankStatementEmail;

    private final AmazonS3 s3Client;

    RestTemplate restTemplate;

    public StatementProcessorServiceImpl() {
        restTemplate = new RestTemplate();
        if (restTemplate.getInterceptors() == null) {
            restTemplate.setInterceptors(new ArrayList<>());
        }

        restTemplate.getInterceptors().add((ClientHttpRequestInterceptor) (HttpRequest request, byte[] body, ClientHttpRequestExecution execution) -> {
            log.trace("REQUEST {} {} --> {}", request.getMethod(), request.getURI().toString(), new String(body, StandardCharsets.UTF_8));
            request.getHeaders().add("x-api-key", apiKey);
            final ClientHttpResponse response = execution.execute(request, body);
            byte[] data;
            try (ByteArrayOutputStream bos = new ByteArrayOutputStream()) {
                IOUtils.copy(response.getBody(), bos);
                data = bos.toByteArray();
                String responseBody = new String(data, StandardCharsets.UTF_8);
                log.trace("RESPONSE {} {} <-- {}", request.getMethod(), request.getURI().toString(), responseBody);
            } catch (Exception e) {
                log.error("Logging response failed", e);
                data = new byte[0];
            }

            return new CustomClientHttpResponse(response, data);
        });

        restTemplate.setErrorHandler(new DefaultResponseErrorHandler());
        restTemplate.setRequestFactory(new HttpComponentsClientHttpRequestFactory());
        s3Client = AmazonS3ClientBuilder.standard()
            .withRegion(regionName)
            .withCredentials(new DefaultAWSCredentialsProviderChain())
            .build();
    }

    private boolean saveClientStatements(StatementModel statementModel, UploadFileRequest uploadRequest) throws BankStatementException {

        try {
            String fileHash = computeCheckSum(uploadRequest);
            TransactionEntity transactionEntity;
            List<TransactionEntity> listTransactionEntity = new ArrayList<>();
            StatementEntity statementEntity = new StatementEntity();
            BeanUtils.copyProperties(statementModel, statementEntity);
            for (TransactionModel transactionModel : statementModel.getTransactions()) {
                transactionEntity = new TransactionEntity();
                BeanUtils.copyProperties(transactionModel, transactionEntity);
                transactionEntity.setStatementsEntity(statementEntity);
                listTransactionEntity.add(transactionEntity);
            }
            statementEntity.setFileHash(fileHash);
            statementEntity.setClientId(uploadRequest.getClientId());
            statementEntity.setSource(uploadRequest.getMode());
            statementEntity.setTransactions(listTransactionEntity);
            statementsService.saveBankStatement(statementEntity);
            return true;
        } catch (Exception ex) {
            throw new BankStatementException(ex);
        }
    }

    public StatementInfoResponse checkIfUploadIsRequired(StatementInfoRequest model) throws BankStatementException {

        StatementInfoResponse statementInfoResponse;
        try {
            StatementEntity lastUploadedStatement = statementsService.findTop1ByClientIdOrderByUpdatedAtDesc(model.getClientId());

            if (lastUploadedStatement != null) {
                // no statements has been uploaded by this clientId.
                Date uploadDate = lastUploadedStatement.getUpdatedAt();
                long diffInMillies = Math.abs(uploadDate.getTime() - System.currentTimeMillis());
                long diff = TimeUnit.DAYS.convert(diffInMillies, TimeUnit.MILLISECONDS);
                if (diff < statementValidityPeriod) {
                    statementInfoResponse = new StatementInfoResponse();
                    statementInfoResponse.setMode(Source.NOT_REQUIRED);
                    statementInfoResponse.setResponseMessage(ResponseMessage.SUCCESS);
                    return statementInfoResponse;
                }
            }
            BankInfoEntity bankInformation = statementsInfoService.getStatementsInfoByBankCode(model.getBankCode());
            if (bankInformation == null) {
                statementInfoResponse = new StatementInfoResponse();
                statementInfoResponse.setMode(Source.NOT_REQUIRED);
                statementInfoResponse.setResponseMessage(ResponseMessage.SUCCESS);
                return statementInfoResponse;
            } else if (bankInformation.getUploadType() == Source.EMAIL || bankInformation.getUploadType() == Source.UPLOAD) {
                statementInfoResponse = new StatementInfoResponse();
                String targetEmail = computeClientEmail(model.getClientId());
                statementInfoResponse.setEmail(targetEmail);
                statementInfoResponse.setMode(bankInformation.getUploadType());

                String helpLink = UriComponentsBuilder.fromHttpUrl(bankInformation.getHelpLink())
                    .queryParam("email", targetEmail)
                    .encode()
                    .build()
                    .encode(StandardCharsets.UTF_8)
                    .toString();

                statementInfoResponse.setHelpLink(helpLink);
                statementInfoResponse.setResponseMessage(ResponseMessage.SUCCESS);
                return statementInfoResponse;
            } else {
                statementInfoResponse = new StatementInfoResponse();
                statementInfoResponse.setMode(Source.NOT_REQUIRED);
                statementInfoResponse.setResponseMessage(ResponseMessage.SUCCESS);
                return statementInfoResponse;
            }

        } catch (Exception ex) {
            throw new BankStatementException(ex);
        }
    }

    private String uploadToS3(UploadFileRequest model) throws BankStatementException//String clientId, String bankCode, String file, String mode)
    {

        String fileObjKeyName = model.getClientId() + "_" + model.getBankCode() + "_" + System.currentTimeMillis() + ".pdf";
        try {
            InputStream is = new ByteArrayInputStream(model.getFile());
            ObjectMetadata metadata = new ObjectMetadata();
            metadata.setContentType("application/pdf");
            metadata.addUserMetadata("clientId", model.getClientId());
            PutObjectRequest request = new PutObjectRequest(bucketName, fileObjKeyName, is, metadata);
            request.setMetadata(metadata);
            s3Client.putObject(request);
        } catch (SdkClientException e) {
            throw new BankStatementUploadException(e);
        }
        return fileObjKeyName;
    }

    private StatementParserResponse parseBankStatement(UploadFileRequest uploadFileRequest) {
        StatementParserRequest statementParserRequest = new StatementParserRequest();
        statementParserRequest.setStatement(uploadFileRequest.getFile());
        statementParserRequest.setBankCode(uploadFileRequest.getBankCode());
        try {
            String endPoint = parserBaseUrl + "/parser/pdf-statement";
            return restTemplate.postForObject(endPoint, statementParserRequest, StatementParserResponse.class);
        } catch (HttpClientErrorException | HttpServerErrorException ex) {
            BankStatementParseException pe = new BankStatementParseException("Failed to parse exception", ex);
            pe.addData("response", getResponse(ex.getResponseBodyAsString()));
            pe.setClientError((ex instanceof HttpClientErrorException));
            throw pe;
        } catch (Exception ex) {
            log.error("Failed to call parser", ex);
            throw new BankStatementParseException(ex.getMessage(), ex);
        }
    }

    private Object getResponse(String data) {
        try {
            return OBJECT_MAPPER.readValue(data, new TypeReference<Map<String, Object>>() {
            });
        } catch (IOException ex) {
            log.trace("Parse Response error: ", ex);
            return data;
        }
    }

    public UploadFileResponse processBankStatements(UploadFileRequest uploadFileRequest) throws BankStatementException {

        UploadFileResponse uploadFileResponse = new UploadFileResponse();

        uploadFileRequest = validateUploadFileRequest(uploadFileRequest);
        String fileObjectName = uploadToS3(uploadFileRequest);

        StatementParserResponse statementParserResponse = parseBankStatement(uploadFileRequest);
        if (statementParserResponse == null || statementParserResponse.getResponseMessage() != ResponseMessage.SUCCESS) {
            throw new BankStatementParseException("Bank Statement parsing failed for clientId:" + uploadFileRequest.getClientId() + " and  banKCode:" + uploadFileRequest.getBankCode());
        }
        StatementModel statementModel = statementParserResponse.getCustomerStatement();
        statementModel.setBankCode(statementParserResponse.getBankCode());
        statementModel = validateStatementResponse(statementModel, uploadFileRequest);
        statementModel.setFileObjectName(fileObjectName);
        if (saveClientStatements(statementModel, uploadFileRequest)) {
            uploadFileResponse.setResponseMessage(ResponseMessage.SUCCESS);
            uploadFileResponse.setMessage("Success");
        } else {
            uploadFileResponse.setResponseMessage(ResponseMessage.ERROR);
            uploadFileResponse.setMessage("Failed");
        }
        return uploadFileResponse;
    }

    private UploadFileRequest validateUploadFileRequest(UploadFileRequest uploadFileRequest) {

        String checkSum = computeCheckSum(uploadFileRequest);
        StatementEntity statementEntity = this.statementsService.findOneByFileHash(checkSum);
        if (statementEntity != null) {
            throw new BankStatementDuplicateFileException("This bank statement already exists.");
        }
        return uploadFileRequest;
    }

    private StatementModel validateStatementResponse(StatementModel statementModel, UploadFileRequest uploadFileRequest) {
        if (!uploadFileRequest.getAccountNumber().equals(statementModel.getAccountNumber())) {
            throw new AccountValidationException("Statement Account Numbers does not match with request Account Number");
        }
        if (!NameMatcher.match(statementModel.getAccountName(), uploadFileRequest.getAccountName())) {
            throw new AccountValidationException("Statement Account Names does not match with request Account Names");
        }
        if (!uploadFileRequest.getBankCode().equals(statementModel.getBankCode())) {
            throw new AccountValidationException("Statement bank code does not match with request Bank code");
        }
        return statementModel;
    }

    private String computeCheckSum(UploadFileRequest request) {
        return DigestUtils.md5DigestAsHex(request.getFile());
    }

    private String computeClientEmail(String clientId) {
        //return  bankStatementsEmail+clientId;
        return String.format(bankStatementEmail, clientId);
    }

    public StatementDataResponse getBankStatement(StatementDataRequest statementDataRequest, String clientId) {

        StatementDataResponse statementDataResponse;
        try {
            StatementEntity statementEntity = statementsService.findTop1ByClientIdOrderByUpdatedAtDesc(clientId);
            if (statementEntity == null && !StringUtils.isEmpty(statementDataRequest.getBankCode())) {
                // check partner  banks services here.             
                BankInfoEntity bankInfo = statementsInfoService.getStatementsInfoByBankCode(statementDataRequest.getBankCode());
                if (bankInfo != null && bankInfo.getUploadType().equals(Source.DIRECT_API_ACCESS)) {
                    StatementModel statement;
                    TransactionModel transactionModel;
                    statementDataResponse = new StatementDataResponse();
                    List<TransactionModel> listOfTransactionModels = new ArrayList<>();
                    List<PartnerBankStatementResponse> listOfPartnerBankStatementResponse = partnerBankStatmentService.partnerBankStatement(statementDataRequest, clientId);
                    if (listOfPartnerBankStatementResponse != null && !listOfPartnerBankStatementResponse.isEmpty()) {
                        for (PartnerBankStatementResponse partnerBankStatementResponse : listOfPartnerBankStatementResponse) {
                            transactionModel = new TransactionModel();
                            transactionModel.setAmount(Integer.parseInt(partnerBankStatementResponse.getAmount()));
                            transactionModel.setBalance(Integer.parseInt(partnerBankStatementResponse.getBalance()));
                            transactionModel.setReferenceNumber(partnerBankStatementResponse.getReference());
                            transactionModel.setRemarks(partnerBankStatementResponse.getNarration());
                            transactionModel.setTransactionDate(DateTime.parse(partnerBankStatementResponse.getTransactionDate()).toDate());
                            transactionModel.setTransactionType(partnerBankStatementResponse.getTransactionType());
                            listOfTransactionModels.add(transactionModel);
                        }
                        statement = new StatementModel();
                        statement.setTransactions(listOfTransactionModels);
                        statement.setAccountName(listOfPartnerBankStatementResponse.get(0).getUserAccount().getAccountDetail().getCustomerName());
                        statement.setAccountNumber(listOfPartnerBankStatementResponse.get(0).getUserAccount().getAccountDetail().getAccountNumber());
                        statement.setClientId(listOfPartnerBankStatementResponse.get(0).getUserAccount().getClientId());
                        statement.setClosingBalance(listOfPartnerBankStatementResponse.get(0).getUserAccount().getAccountDetail().getCurrentBalance());
                        statement.setCurrencyType(listOfPartnerBankStatementResponse.get(0).getUserAccount().getCurrency());
                        statement.setSource(Source.DIRECT_API_ACCESS);
                        statementDataResponse.setStatement(statement);
                        return statementDataResponse;
                    }
                } else {
                    statementDataResponse = new StatementDataResponse();
                    statementDataResponse.setResponseMessage(ResponseMessage.NOT_AVAILABLE);
                    return statementDataResponse;
                }
            }

            if (statementEntity == null) {
                statementDataResponse = new StatementDataResponse();
                statementDataResponse.setResponseMessage(ResponseMessage.NOT_AVAILABLE);
                return statementDataResponse;
            }

            statementDataResponse = new StatementDataResponse();
            TransactionModel transactionModel;
            StatementModel statement = new StatementModel();
            List<TransactionModel> listOfTransactionModel = new ArrayList<>();
            BeanUtils.copyProperties(statementEntity, statement);
            for (TransactionEntity transactionEntity : statementEntity.getTransactions()) {
                transactionModel = new TransactionModel();
                BeanUtils.copyProperties(transactionEntity, transactionModel);
                listOfTransactionModel.add(transactionModel);
            }
            statement.setTransactions(listOfTransactionModel);
            statementDataResponse.setStatement(statement);
            statementDataResponse.setResponseMessage(ResponseMessage.SUCCESS);
            return statementDataResponse;
        } catch (Exception ex) {
            throw new BankStatementQueryException(ex);
        }

    }

}
