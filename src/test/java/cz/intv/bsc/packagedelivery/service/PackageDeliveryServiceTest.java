package cz.intv.bsc.packagedelivery.service;

import cz.intv.bsc.packagedelivery.model.Package;
import cz.intv.bsc.packagedelivery.model.PackageFee;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertIterableEquals;

public class PackageDeliveryServiceTest {

    @Test
    public void addPackages() {
        PackageDeliveryService packageDeliveryService = new PackageDeliveryService();
        packageDeliveryService.addPackage("01234", "12.23");
        packageDeliveryService.addPackage("02345", "2.5");
        packageDeliveryService.addPackage("03456", "3.354");
        packageDeliveryService.addPackage("01234", "12.2333");
        Map<String, Package> allPackages = packageDeliveryService.getAllPackages();
        assertEquals(allPackages.size(), 3);
        assertEquals(BigDecimal.valueOf(12.23), allPackages.get("01234").getWeight());
        assertEquals(BigDecimal.valueOf(2.5), allPackages.get("02345").getWeight());
        assertEquals(BigDecimal.valueOf(3.354), allPackages.get("03456").getWeight());
    }

    @Test
    public void addPackageByInputFile() {
        String file = "src/test/resources/packages.txt";
        PackageDeliveryService packageDeliveryService = new PackageDeliveryService();
        packageDeliveryService.addPackage(file);
        Map<String, Package> packages = packageDeliveryService.getAllPackages();
        assertEquals(packages.get("08801").getWeight(), new BigDecimal("15.960"));
        assertEquals(packages.get("08079").getWeight(), new BigDecimal("5.500"));
        assertEquals(packages.get("09300").getWeight(), new BigDecimal("3.200"));
        assertEquals(packages.get("90005").getWeight(), new BigDecimal("2.000"));
    }

    @Test
    public void addPackageFeeByInputFile() {
        String filePath = "src/test/resources/package_fees.txt";
        PackageDeliveryService packageDeliveryService = new PackageDeliveryService();
        packageDeliveryService.addPackageFee(filePath);
        List<PackageFee> packageFeesList = packageDeliveryService.getAllPackageFees();

        List<BigDecimal> packageFees = packageFeesList.stream()
                                              .map(PackageFee::getFee)
                                              .collect(Collectors.toList());
        List<BigDecimal> packageFeeWeights = packageFeesList.stream()
                                                      .map(PackageFee::getWeight)
                                                      .collect(Collectors.toList());

        assertEquals(7, packageFees.size());
        assertIterableEquals(packageFees,initPackageFees());
        assertIterableEquals(packageFeeWeights,initPackageFeeWeights());
    }

    @Test
    public void addPackagesAndPackageFeeFromFile(){
        PackageDeliveryService packageDeliveryService = new PackageDeliveryService();
        String file = "src/test/resources/packages.txt";
        String filePath = "src/test/resources/package_fees.txt";
        packageDeliveryService.addPackage(file);
        packageDeliveryService.addPackageFee(filePath);
        Map<String, Package> packages = packageDeliveryService.getAllPackages();
        assertEquals(packages.get("08801").getWeight(), new BigDecimal("15.960"));
        assertEquals(packages.get("08079").getWeight(), new BigDecimal("5.500"));
        assertEquals(packages.get("09300").getWeight(), new BigDecimal("3.200"));
        assertEquals(packages.get("90005").getWeight(), new BigDecimal("2.000"));
        assertEquals(packages.get("08801").getFee(), new BigDecimal("5.00"));
        assertEquals(packages.get("08079").getFee(), new BigDecimal("2.50"));
        assertEquals(packages.get("09300").getFee(), new BigDecimal("2.00"));
        assertEquals(packages.get("90005").getFee(), new BigDecimal("1.50"));
    }

    public List<BigDecimal> initPackageFees(){
        List<BigDecimal> fees = new ArrayList<>();
        fees.add(new BigDecimal("5.00"));
        fees.add(new BigDecimal("2.50"));
        fees.add(new BigDecimal("2.00"));
        fees.add(new BigDecimal("1.50"));
        fees.add(new BigDecimal("1.00"));
        fees.add(new BigDecimal("0.70"));
        fees.add(new BigDecimal("0.50"));
        return fees;
    }

    public List<BigDecimal> initPackageFeeWeights(){
        List<BigDecimal> weights = new ArrayList<>();
        weights.add(new BigDecimal("10"));
        weights.add(new BigDecimal("5"));
        weights.add(new BigDecimal("3"));
        weights.add(new BigDecimal("2"));
        weights.add(new BigDecimal("1"));
        weights.add(new BigDecimal("0.5"));
        weights.add(new BigDecimal("0.2"));
        return weights;
    }
}
