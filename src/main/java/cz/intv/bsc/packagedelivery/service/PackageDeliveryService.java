package cz.intv.bsc.packagedelivery.service;

import cz.intv.bsc.packagedelivery.model.Package;
import cz.intv.bsc.packagedelivery.model.PackageFee;
import cz.intv.bsc.packagedelivery.repository.PackageDeliveryRepositoryImpl;
import cz.intv.bsc.packagedelivery.repository.PackageFeeRepository;
import cz.intv.bsc.packagedelivery.repository.PackageFeeRepositoryImpl;
import cz.intv.bsc.packagedelivery.service.file.FileFactoryService;
import cz.intv.bsc.packagedelivery.service.validation.ValidationFactoryService;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class PackageDeliveryService {

    private final PackageDeliveryRepositoryImpl packageDeliveryRepository =
            new PackageDeliveryRepositoryImpl();
    private final ScheduledExecutorService scheduledExecutorService =
            Executors.newSingleThreadScheduledExecutor();
    private final ValidationFactoryService validationFactoryService =
            new ValidationFactoryService();
    private final FileFactoryService fileFactoryService = new FileFactoryService();
    private final PackageFeeRepository packageFeeRepository = new PackageFeeRepositoryImpl();

    public PackageDeliveryService() {
        printAllPackagesJob();
    }

    public void addPackage(String postalCode, String weight) {
        if (validationFactoryService.validatePackage(postalCode, weight)) {
            packageDeliveryRepository.addPackage(Package.builder()
                                                        .postalCode(postalCode)
                                                        .weight(new BigDecimal(weight))
                                                        .build());
        }
        updatePackageFees();
    }

    public void addPackage(String filePath) {
        packageDeliveryRepository.saveAll(fileFactoryService.readPackageInputTxtFile(filePath));
        updatePackageFees();
    }

    public void addPackageFee(String filePath) {
        packageFeeRepository.saveAll(fileFactoryService.readPackageFeeInputTxtFile(filePath));
        updatePackageFees();
    }

    public Map<String, Package> getAllPackages() {
        return packageDeliveryRepository.getAllPackages();
    }

    public void printAllPackagesJob() {
        scheduledExecutorService.scheduleAtFixedRate(() -> {
            if (packageDeliveryRepository.isNotEmpty()) {
                printAllPackages();
            }
        }, 5, 60, TimeUnit.SECONDS);
    }

    private void updatePackageFees() {
        Map<String, Package> packages = packageDeliveryRepository.getAllPackages();
        if (packageFeeRepository.isNotEmpty()) {
            packages.values().forEach(this::updatePackageFee);
        }
    }

    private void updatePackageFee(Package aPackage) {
        List<PackageFee> packageFees = packageFeeRepository.getAllPackageFee();

        packageFees.stream()
                   .filter(packageFee -> aPackage.getWeight().compareTo(packageFee.getWeight())
                           >= 0)
                   .filter(packageFee -> aPackage.getFee() == null ||
                           packageFee.getFee().compareTo(aPackage.getFee()) > 0)
                   .forEach(packageFee -> packageDeliveryRepository.updatePackageFee(
                           aPackage.getPostalCode(),
                           packageFee.getFee()
                   ));
    }

    private void printAllPackages() {
        System.out.println("-----Packages-----");
        Map<String, Package> allPackages = packageDeliveryRepository.getAllPackages();
        for (Package pckg : allPackages.values()) {
            if (pckg.getFee() == null) {
                System.out.println(pckg.getPostalCode() + " " + pckg.getWeight());
            } else {
                System.out.println(
                        pckg.getPostalCode() + " " + pckg.getWeight() + " " + pckg.getFee());
            }
        }
        System.out.println("------------------");
    }

    public void cancelScheduledJob() {
        scheduledExecutorService.shutdown();
    }

    public List<PackageFee> getAllPackageFees() {
        return packageFeeRepository.getAllPackageFee();
    }
}
