/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.onefi.bankstatement.impl;

import co.onefi.bankstatement.config.PartnerBankStatementConfig;
import co.onefi.bankstatement.impl.exceptions.PartnerBankStatementException;
import co.onefi.bankstatement.impl.utility.CustomClientHttpResponse;
import co.onefi.bankstatement.interceptors.HeaderRequestInterceptor;
import co.onefi.bankstatement.models.PartnerBankStatementResponse;
import co.onefi.bankstatement.models.StatementDataRequest;
import co.onefi.bankstatement.services.PartnerBankStatmentService;
import java.io.ByteArrayOutputStream;
import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Base64;
import java.util.Date;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.time.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpRequest;
import org.springframework.http.client.ClientHttpRequestExecution;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

/**
 *
 * @author michael
 */
@Service
@Slf4j
public class PartnerBankStatementServiceImpl implements PartnerBankStatmentService {

    RestTemplate restTemplate;
    @Autowired
    private PartnerBankStatementConfig partnerBankStatementConfig;
    private final int MAX_STATEMENT_LIMIT = 5000;

    public PartnerBankStatementServiceImpl() {
        restTemplate = new RestTemplate();
        if (restTemplate.getInterceptors() == null) {
            restTemplate.setInterceptors(new ArrayList<>());
        }

        restTemplate.getInterceptors().add((ClientHttpRequestInterceptor) (HttpRequest request, byte[] body, ClientHttpRequestExecution execution) -> {
            log.trace("REQUEST {} {} --> {}", request.getMethod(), request.getURI().toString(), new String(body, StandardCharsets.UTF_8));
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
    }

    @Override
    public List<PartnerBankStatementResponse> partnerBankStatement(StatementDataRequest statementDataRequest, String clientId) {
        int limit = 100;
        int offset = 0;
        List<PartnerBankStatementResponse> listOfPartnerBankStatementResponse = new ArrayList<>();
        List<PartnerBankStatementResponse> templistOfPartnerBankStatementResponse = new ArrayList<>();
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        String startDate = format.format(DateUtils.addDays(new Date(), -partnerBankStatementConfig.getStartDays()));
        try {
            do {
                templistOfPartnerBankStatementResponse = callPartnerBankStatement(statementDataRequest, clientId, startDate, limit, offset);
                listOfPartnerBankStatementResponse.addAll(templistOfPartnerBankStatementResponse);
                offset += limit;
            } while (templistOfPartnerBankStatementResponse.size() >= limit && listOfPartnerBankStatementResponse.size() < MAX_STATEMENT_LIMIT);
        } catch (Exception ex) {
            log.error("Partner Banks Statement Error: " + ex.getMessage());
            throw new PartnerBankStatementException(ex.getMessage(), ex);
        }
        return listOfPartnerBankStatementResponse;
    }

    private List<PartnerBankStatementResponse> callPartnerBankStatement(StatementDataRequest statementDataRequest, String clientId, String startDate, int limit, int offset) {
        String endPoint = String.format("/accounts/%s/%s/transactions?clientId=%s&startDate=%s&limit=%s&offset=%s", statementDataRequest.getBankCode(), statementDataRequest.getAccountNumber(), clientId, startDate, limit, offset);
        String url = partnerBankStatementConfig.getBaseUrl() + endPoint;
        byte[] plainCredsBytes = (partnerBankStatementConfig.getUsername() + ":" + partnerBankStatementConfig.getPassword()).getBytes();
        byte[] base64CredsBytes = Base64.getEncoder().encode(plainCredsBytes);
        String base64Creds = new String(base64CredsBytes);
        restTemplate.getInterceptors().add(new HeaderRequestInterceptor("Authorization", "Basic " + base64Creds));
        PartnerBankStatementResponse[] response = restTemplate.getForEntity(url, PartnerBankStatementResponse[].class).getBody();
        return Arrays.asList(response);
    }

}
