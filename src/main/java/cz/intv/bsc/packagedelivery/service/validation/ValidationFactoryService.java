package cz.intv.bsc.packagedelivery.service.validation;

public class ValidationFactoryService {
    private final Validation<String> postalCodeValidation = new PostalCodeValidation();
    private final Validation<String> weightValidation = new WeightValidation();
    private final Validation<String> packageFeeValidation = new PackageFeeValidation();

    public boolean validatePackage(String postalCode, String weight) {
        boolean checkPostalCode = postalCodeValidation.checkField(postalCode);
        boolean checkWeight = weightValidation.checkField(weight);
        return checkPostalCode && checkWeight;
    }

    public boolean validatePackageFee(String weight, String packageFee) {
        boolean checkWeight = weightValidation.checkField(weight);
        boolean checkFee = packageFeeValidation.checkField(packageFee);
        return checkWeight && checkFee;
    }
}
