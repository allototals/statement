/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.onefi.bankstatement.impl.utility;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.client.ClientHttpResponse;

/**
 *
 * @author michael
 */
public class CustomClientHttpResponse implements ClientHttpResponse {

    private final ClientHttpResponse response;
    private final byte[] data;

    public CustomClientHttpResponse(ClientHttpResponse response, byte[] data) {
        this.response = response;
        this.data = data;
    }

    @Override
    public HttpStatus getStatusCode() throws IOException {
        return response.getStatusCode();
    }

    @Override
    public int getRawStatusCode() throws IOException {
        return response.getRawStatusCode();
    }

    @Override
    public String getStatusText() throws IOException {
        return response.getStatusText();
    }

    @Override
    public void close() {
        response.close();
    }

    @Override
    public InputStream getBody() throws IOException {
        return new ByteArrayInputStream(data);
    }

    @Override
    public HttpHeaders getHeaders() {
        return response.getHeaders();
    }
};
