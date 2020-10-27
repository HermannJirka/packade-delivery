package cz.intv.bsc.packagedelivery.service.validation;

public class WeightValidation implements Validation<String> {

    String regex3DecimalPlaces = "\\d+(\\.\\d{1,3})?";

    @Override
    public boolean checkField(String field) {
        if (!field.matches(regex3DecimalPlaces)) {
            System.err.println("Bad format weight, maximum decimal places are 3");
            return false;
        }
        return true;
    }
}
