package com.leeknow.summapp.schedule.schedules;

import com.leeknow.summapp.exchangerates.repository.ExchangeRatesRepository;
import com.leeknow.summapp.log.enums.LogType;
import com.leeknow.summapp.log.service.LogService;
import com.leeknow.summapp.schedule.dto.ExchangeRateAPIScheduleDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.reactive.function.client.WebClient;

import static com.leeknow.summapp.schedule.constant.ScheduleAPIConstant.EXCHANGE_RATE_LATEST_PATH;

@RequiredArgsConstructor
public class ExchangeRatesAPISchedule extends AbstractScheduledTask {

    private final WebClient webClient;
    private final LogService log;
    private final ExchangeRatesRepository exchangeRatesRepository;

    @Value("${exchange.rate.api.key}")
    private String exchangeRateApiKey;

    @Override
    public void run() {
        try {
            ExchangeRateAPIScheduleDTO response = webClient.get()
                    .uri(EXCHANGE_RATE_LATEST_PATH + exchangeRateApiKey)
                    .retrieve()
                    .bodyToMono(ExchangeRateAPIScheduleDTO.class)
                    .block();

            if (response != null) {
                if (response.isSuccess()) {
                    double EUR = response.getRates().get("KZT"); // тенге в одном евро
                    double USD = EUR / response.getRates().get("USD"); // тенге в одном долларе
                    double RUB = EUR / response.getRates().get("RUB"); // тенге в одном рубле

                    exchangeRatesRepository.updateExchangeRates(USD, EUR, RUB);
                }
            }

        } catch (Exception e) {
            log.save(LogType.CRITICAL.getId(), e);
        }
    }
}
