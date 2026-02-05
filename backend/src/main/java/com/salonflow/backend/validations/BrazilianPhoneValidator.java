package com.salonflow.backend.validations;

import com.salonflow.backend.validations.interfaces.BrazilianPhone;

import java.util.Objects;

public class BrazilianPhoneValidator implements BrazilianPhone<BrazilianPhone, String> {

    @Override
    public boolean isValid(String phone, BrazilianPhone brazilianPhone) throws Exception {
        if (!Objects.equals(phone, "^\\(?[1-9]{2}\\)? ?(?:[2-8]|9[0-9])[0-9]{3}\\-?[0-9]{4}$")){
            throw new Exception();
        }

        return true;
    }

}
