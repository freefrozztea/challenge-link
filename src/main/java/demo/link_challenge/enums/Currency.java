package demo.link_challenge.enums;

public enum Currency {
    ARS("Pesos Argentinos"),
    USD("Dólar estadounidense"),
    EUR("Euro"),
    GBP("Libra esterlina"),
    JPY("Yen japonés");

    private final String description;

    Currency(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }
}
