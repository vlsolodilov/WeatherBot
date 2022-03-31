package ru.weatherclient.clients;

public class WeatherClientException extends RuntimeException {
    public WeatherClientException(String msg) {
        super(msg);
    }
}
