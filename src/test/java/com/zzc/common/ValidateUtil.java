package com.zzc.common;

import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import com.zzc.modules.sysmgr.product.entity.Category;

/**
 * 
 * @author apple
 *
 */
public class ValidateUtil {
	public static void main(String[] args) {
		ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
		Validator validator = factory.getValidator();
		Category category = new Category();
		Set<ConstraintViolation<Category>> constraintViolations = validator.validate(category);
		for (ConstraintViolation<Category> p : constraintViolations) {
			System.err.println(p.getMessage());
		}
	}
}
