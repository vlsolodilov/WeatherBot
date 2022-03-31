package ru.openweatherservice.config;

import java.time.Duration;
import org.ehcache.Cache;
import org.ehcache.CacheManager;
import org.ehcache.config.builders.CacheConfigurationBuilder;
import org.ehcache.config.builders.CacheManagerBuilder;
import org.ehcache.config.builders.ExpiryPolicyBuilder;
import org.ehcache.config.builders.ResourcePoolsBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import ru.openweatherservice.model.OpenWeatherInfo;

@Configuration
@EnableConfigurationProperties(OpenWeatherConfig.class)
public class ApplicationConfig {
    private final CacheManager cacheManager = CacheManagerBuilder.newCacheManagerBuilder().build(true);

    @Bean
    public Cache<String, OpenWeatherInfo> weatherInfoCache(@Value("${app.cache.size}") int cacheSize,
        @Value("${app.cache.expiry}") int cacheExpiry) {
        return cacheManager.createCache("OpenWeather-Cache",
                CacheConfigurationBuilder.newCacheConfigurationBuilder(String.class, OpenWeatherInfo.class,
                        ResourcePoolsBuilder.heap(cacheSize))
                    .withExpiry(ExpiryPolicyBuilder.timeToLiveExpiration(Duration.ofMinutes(cacheExpiry)))
                    .build());
    }
}
