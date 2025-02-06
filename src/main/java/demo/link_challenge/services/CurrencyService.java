package demo.link_challenge.services;

import demo.link_challenge.enums.Currency;
import org.springframework.stereotype.Service;

import java.util.EnumSet;

@Service
public class CurrencyService implements ICurrencyService{
    @Override
    public boolean isSupportedCurrency(Currency currency) {
        return EnumSet.allOf(Currency.class).contains(currency);
    }
}
