package demo.link_challenge.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;

public class MerchantDTO implements Serializable {
    @JsonProperty("name")
    private String name;

    @JsonProperty("merchant_id")
    private String merchantId;

    public MerchantDTO() {}

    public MerchantDTO(String name, String merchantId) {
        this.name = name;
        this.merchantId = merchantId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMerchantId() {
        return merchantId;
    }

    public void setMerchantId(String merchantId) {
        this.merchantId = merchantId;
    }
}
