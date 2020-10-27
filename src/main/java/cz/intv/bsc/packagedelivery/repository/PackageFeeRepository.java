package cz.intv.bsc.packagedelivery.repository;

import cz.intv.bsc.packagedelivery.model.PackageFee;

import java.util.List;

public interface PackageFeeRepository {

    List<PackageFee> getAllPackageFee();

    boolean isNotEmpty();

    void saveAll(List<PackageFee> packageFees);
}
