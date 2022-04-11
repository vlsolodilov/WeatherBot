package ru.yandexweatherservice.requester;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.yandexweatherservice.config.YandexWeatherConfig;

@Service
@Slf4j
@RequiredArgsConstructor
public class YandexWeatherRequesterImpl implements YandexWeatherRequester {

    private final YandexWeatherConfig yandexWeatherConfig;

    @Override
    public String getWeatherAsXml(String url) {
        try {
            log.info("request for url:{}", url);
            HttpClient client = HttpClient.newHttpClient();
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(url))
                    .header(yandexWeatherConfig.getHeaderKey(), yandexWeatherConfig.getHeaderValue())
                    .build();

            HttpResponse<String> response =
                    client.send(request, HttpResponse.BodyHandlers.ofString());
            return response.body();
        } catch (Exception ex) {
            if (ex instanceof InterruptedException) {
                Thread.currentThread().interrupt();
            }
            log.error("YandexWeather request error, url:{}", url, ex);
            throw new RequesterException(ex);
        }
    }
}
