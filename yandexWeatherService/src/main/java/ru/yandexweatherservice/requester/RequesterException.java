package ru.yandexweatherservice.requester;

public class RequesterException extends RuntimeException {
    public RequesterException(Throwable cause) {
        super(cause);
    }
}
