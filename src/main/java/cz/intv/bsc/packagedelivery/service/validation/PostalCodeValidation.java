package cz.intv.bsc.packagedelivery.service.validation;

public class PostalCodeValidation implements Validation<String> {
    @Override
    public boolean checkField(String field) {
        if (!field.matches("[0-9]+")) {
            System.err.println("Bad postal code, [01234]");
            return false;
        }

        if (field.length() != 5) {
            System.err.println("Bad postal code, size must be 5");
            return false;
        }

        return true;
    }
}
