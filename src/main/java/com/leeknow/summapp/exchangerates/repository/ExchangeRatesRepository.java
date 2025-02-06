package com.leeknow.summapp.exchangerates.repository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Repository;

@Repository
public class ExchangeRatesRepository {

    @PersistenceContext
    private EntityManager entityManager;

    @Transactional
    public int updateExchangeRates(Double usd, Double eur, Double rub) {
        String sql = "UPDATE exchange_rates SET usd = :usd, eur = :eur, rub = :rub";
        return entityManager.createNativeQuery(sql)
                .setParameter("usd", usd)
                .setParameter("eur", eur)
                .setParameter("rub", rub)
                .executeUpdate();
    }
}
