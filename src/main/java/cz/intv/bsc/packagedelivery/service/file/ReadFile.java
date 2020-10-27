package cz.intv.bsc.packagedelivery.service.file;

import java.util.List;

public interface ReadFile<T> {
    List<T> readFile(String path);
}
