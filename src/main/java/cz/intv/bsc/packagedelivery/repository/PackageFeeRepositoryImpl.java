package cz.intv.bsc.packagedelivery.repository;

import cz.intv.bsc.packagedelivery.model.PackageFee;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

public class PackageFeeRepositoryImpl implements PackageFeeRepository {
    private final CopyOnWriteArrayList<PackageFee> packageFees = new CopyOnWriteArrayList<>();

    @Override
    public CopyOnWriteArrayList<PackageFee> getAllPackageFee() {
        return packageFees;
    }

    @Override
    public boolean isNotEmpty() {
        return !packageFees.isEmpty();
    }

    @Override
    public void saveAll(List<PackageFee> newPackageFees) {
        if (!newPackageFees.isEmpty()) {
            packageFees.clear();
            packageFees.addAll(newPackageFees);
            System.out.println("----- [$] The fees have been imported [$] -----");
        }
    }
}
