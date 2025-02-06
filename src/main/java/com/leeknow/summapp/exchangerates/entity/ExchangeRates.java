package com.leeknow.summapp.exchangerates.entity;

import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Table(name = "exchange_rates")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class ExchangeRates {

    private Double usd;
    private Double eur;
    private Double rub;
}
