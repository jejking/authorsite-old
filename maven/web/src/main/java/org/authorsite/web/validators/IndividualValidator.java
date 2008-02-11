/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.authorsite.web.validators;

import org.authorsite.domain.Individual;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

/**
 *
 * @author jejking
 */
public class IndividualValidator implements Validator {

    @Override
    public boolean supports(Class clazz) {
        return Individual.class.isAssignableFrom(clazz);
    }

    @Override
    public void validate(Object obj, Errors errors) {
        Individual individual = (Individual) obj;
        if (individual.getName() == null || individual.getName().trim().isEmpty()) {
            errors.rejectValue("name", "errors.individual.name.empty", "Name should not be empty");
        }
    }

}
