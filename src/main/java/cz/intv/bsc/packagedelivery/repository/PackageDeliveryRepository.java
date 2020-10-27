package cz.intv.bsc.packagedelivery.repository;

import cz.intv.bsc.packagedelivery.model.Package;

import java.math.BigDecimal;
import java.util.Map;

public interface PackageDeliveryRepository {
    void addPackage(Package aPackage);

    Map<String, Package> getAllPackages();

    boolean isNotEmpty();

    void updatePackageFee(String key, BigDecimal packageFee);
}
