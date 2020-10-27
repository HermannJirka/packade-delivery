package cz.intv.bsc.packagedelivery.repository;

import cz.intv.bsc.packagedelivery.model.Package;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

public class PackageDeliveryRepositoryImpl implements PackageDeliveryRepository {

    private final Map<String, Package> packages = new ConcurrentHashMap<>();

    public void addPackage(Package aPackage) {
        String keyPostalCode = aPackage.getPostalCode();
        if (packages.containsKey(keyPostalCode)) {
            packages.computeIfPresent(
                    keyPostalCode,
                    (k, pckg) -> updatePackage(pckg, aPackage)
            );

        } else {
            packages.putIfAbsent(aPackage.getPostalCode(), aPackage);
        }
    }

    public void updatePackageFee(String key, BigDecimal packageFee) {
        if (packages.containsKey(key)) {
            packages.computeIfPresent(key, (k, pckg) -> {
                pckg.setFee(packageFee);
                return pckg;
            });
        }
    }

    private Package updatePackage(Package oldPackage, Package newPackage) {
        if (newPackage.getFee() != null) {
            oldPackage.setFee(newPackage.getFee());
        }
        oldPackage.setWeight(oldPackage.getWeight().add(newPackage.getWeight()));
        return oldPackage;
    }

    public Map<String, Package> getAllPackages() {
        return packages.entrySet().stream()
                       .sorted(Map.Entry.comparingByKey())
                       .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue,
                               (oldValue, newValue) -> oldValue, ConcurrentHashMap::new
                       ));
    }

    public boolean isNotEmpty() {
        return !packages.isEmpty();
    }

    public void saveAll(List<Package> packageList) {
        if (!packageList.isEmpty()) {
            for (Package aPackage : packageList) {
                addPackage(aPackage);
            }
            System.out.println("----- [] Packages have been imported [] -----");
        }
    }
}
