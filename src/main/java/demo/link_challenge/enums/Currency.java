package demo.link_challenge.enums;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

import java.io.Serializable;

public enum Currency implements Serializable {
    ARS("ARS","Pesos Argentinos"),
    USD("USD","Dólar estadounidense"),
    EUR("EUR","Euro"),
    GBP("GBP","Libra esterlina"),
    JPY("JPY","Yen japonés");

    private final String code;
    private final String displayName;

    Currency(String code, String displayName) {
        this.code = code;
        this.displayName = displayName;
    }

    @JsonValue
    public String getCode() {
        return code;
    }

    @JsonCreator
    public static Currency fromDisplayName(String displayName) {
        for (Currency currency : values()) {
            if (currency.displayName.equalsIgnoreCase(displayName)) {
                return currency;
            }
        }
        throw new IllegalArgumentException("Moneda no válida: " + displayName);
    }

    public String getDisplayName() {
        return displayName;
    }
}
