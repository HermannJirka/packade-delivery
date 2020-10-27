package cz.intv.bsc.packagedelivery.service.file;

import cz.intv.bsc.packagedelivery.model.PackageFee;
import cz.intv.bsc.packagedelivery.service.validation.ValidationFactoryService;

import java.io.IOException;
import java.math.BigDecimal;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class PackageFeeInputTxtFile implements ReadFile<PackageFee> {

    private final ValidationFactoryService validationFactoryService;

    public PackageFeeInputTxtFile() {
        validationFactoryService = new ValidationFactoryService();
    }

    @Override
    public List<PackageFee> readFile(String filePath) {
        Path path = Paths.get(filePath);
        try (Stream<String> stream = Files.lines(path)) {
            return stream.map(this::toPackageFee)
                         .filter(Objects::nonNull)
                         .collect(Collectors.toList());
        } catch (IOException e) {
            System.err.println("Could not read the file: " + filePath);
            return Collections.emptyList();
        }
    }

    private PackageFee toPackageFee(String s) {
        String[] stringPackage = s.split(" ");
        if (stringPackage.length == 2 &&
                validationFactoryService.validatePackageFee(stringPackage[0]
                        , stringPackage[1])) {
            return PackageFee.builder()
                             .weight(new BigDecimal(stringPackage[0]))
                             .fee(new BigDecimal(stringPackage[1]))
                             .build();
        }
        return null;
    }
}

