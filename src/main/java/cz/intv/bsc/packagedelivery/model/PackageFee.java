package cz.intv.bsc.packagedelivery.model;

import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;

@Data
@Builder
public class PackageFee {
    private BigDecimal weight;
    private BigDecimal fee;
}
