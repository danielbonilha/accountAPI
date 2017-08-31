package br.com.accounts.validation;

import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;

import br.com.accounts.entity.Account;

public class ValidatorUtil {

	private static ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
	private static Validator validator = factory.getValidator();

	public static void validateAccount(Account question) {
		Set<ConstraintViolation<Account>> constraintViolations = validator.validate(question);
		
		if (constraintViolations.size() > 0) {
			StringBuilder sb = new StringBuilder();
			for (ConstraintViolation<Account> cv : constraintViolations) {
				sb.append(cv.getMessage() + ", ");
			}
			
			question.setErrorMessage(sb.substring(0, sb.length() - 2));
		}
	}
	
}
