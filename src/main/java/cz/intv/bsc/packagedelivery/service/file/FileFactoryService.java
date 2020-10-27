package cz.intv.bsc.packagedelivery.service.file;

import cz.intv.bsc.packagedelivery.model.Package;
import cz.intv.bsc.packagedelivery.model.PackageFee;

import java.util.List;

public class FileFactoryService {
    private final PackageInputTxtFile packageInputTxtFile = new PackageInputTxtFile();
    private final PackageFeeInputTxtFile packageFeeInputTxtFile = new PackageFeeInputTxtFile();

    public List<Package> readPackageInputTxtFile(String filePath) {
        return packageInputTxtFile.readFile(filePath);
    }

    public List<PackageFee> readPackageFeeInputTxtFile(String filePath) {
        return packageFeeInputTxtFile.readFile(filePath);
    }
}
