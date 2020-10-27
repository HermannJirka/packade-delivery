package cz.intv.bsc.packagedelivery.service.validation;

public interface Validation<T> {
    boolean checkField(T field);
}
