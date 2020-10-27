package cz.intv.bsc.packagedelivery.service.file;

import cz.intv.bsc.packagedelivery.model.Package;
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

public class PackageInputTxtFile implements ReadFile<Package> {
    private final ValidationFactoryService validationFactoryService;

    public PackageInputTxtFile() {
        this.validationFactoryService = new ValidationFactoryService();
    }

    @Override
    public List<Package> readFile(String filePath) {
        Path path = Paths.get(filePath);
        try (Stream<String> stream = Files.lines(path)) {
            return stream.map(this::toPackage)
                         .filter(Objects::nonNull)
                         .collect(Collectors.toList());
        } catch (IOException e) {
            System.err.println("Could not read the file: " + filePath);
            return Collections.emptyList();
        }
    }

    private Package toPackage(String s) {
        String[] stringPackage = s.split(" ");
        if (stringPackage.length == 2 && validationFactoryService.validatePackage(stringPackage[0]
                , stringPackage[1])) {
            return Package.builder()
                          .postalCode(stringPackage[0])
                          .weight(new BigDecimal(stringPackage[1]))
                          .build();
        }
        return null;
    }
}
