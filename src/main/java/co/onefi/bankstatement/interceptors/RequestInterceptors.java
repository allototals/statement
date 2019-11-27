/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.onefi.bankstatement.interceptors;

import java.util.ArrayList;
import java.util.List;
import org.springframework.http.MediaType;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.web.client.RestTemplate;

/**
 *
 * @author michael
 */
public class RequestInterceptors {

    private RestTemplate restTemplate;
    private List<ClientHttpRequestInterceptor> interceptors;

    public RequestInterceptors() {
        restTemplate = new RestTemplate();
        if (interceptors == null) {
            interceptors = new ArrayList<>();
        }
    }

    public RestTemplate getRestTemplate(HeaderRequestInterceptor headerRequestInterceptor) {
        if (headerRequestInterceptor != null) {
            interceptors.add(headerRequestInterceptor);
            restTemplate.setInterceptors(interceptors);
        }
        return restTemplate;
    }
}
