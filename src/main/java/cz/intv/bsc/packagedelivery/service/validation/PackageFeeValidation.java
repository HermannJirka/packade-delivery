package cz.intv.bsc.packagedelivery.service.validation;

public class PackageFeeValidation implements Validation<String> {

    String regex2DecimalPlaces = "\\d+(\\.\\d{1,2})?";

    @Override
    public boolean checkField(String field) {
        if (!field.matches(regex2DecimalPlaces)) {
            System.err.println("Bad format package fee, maximum 2 decimal places");
            return false;
        }
        return true;
    }
}
