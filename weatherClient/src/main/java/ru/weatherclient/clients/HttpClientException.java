package ru.weatherclient.clients;

public class HttpClientException extends RuntimeException {
    public HttpClientException(String msg) {
        super(msg);
    }
}
