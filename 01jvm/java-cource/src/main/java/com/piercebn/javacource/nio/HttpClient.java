package com.piercebn.javacource.nio;

import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import java.io.IOException;

public class HttpClient {
    public static void main(String[] args) throws IOException {
        CloseableHttpClient httpclient = HttpClients.createDefault();
        HttpGet httpGet = new HttpGet("http://localhost:8801/");
        CloseableHttpResponse response = httpclient.execute(httpGet);
        try {
            System.out.println("Status: "+response.getStatusLine());
            HttpEntity entity = response.getEntity();
            if (entity != null) {
                System.out.println("Result: " + EntityUtils.toString(entity));
            }
        } finally {
            response.close();
        }
    }
}
