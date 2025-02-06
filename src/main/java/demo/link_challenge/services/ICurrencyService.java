package demo.link_challenge.services;

import demo.link_challenge.enums.Currency;

public interface ICurrencyService {
    boolean isSupportedCurrency(Currency currency);
}
