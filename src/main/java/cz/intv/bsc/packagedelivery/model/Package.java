package cz.intv.bsc.packagedelivery.model;

import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;

@Data
@Builder
public class Package {
    private String postalCode;
    private BigDecimal weight;
    private BigDecimal fee;
}
