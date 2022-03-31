package ru.openweatherservice.parser;

import ru.openweatherservice.model.OpenweatherInfo;

public interface OpenweatherParser {
    OpenweatherInfo parse(String ratesAsString);
}
