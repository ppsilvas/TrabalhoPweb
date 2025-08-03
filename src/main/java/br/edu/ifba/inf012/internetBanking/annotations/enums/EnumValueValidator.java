package br.edu.ifba.inf012.internetBanking.annotations.enums;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class EnumValueValidator implements ConstraintValidator<ValidEnumValue,String>{

	private Class<? extends Enum<?>> enumClass;
	private boolean ignoreCase;
	
	@Override
	public void initialize(ValidEnumValue annotation) {
		this.enumClass = annotation.enumClass();
		this.ignoreCase = annotation.ignoreCase();
	}
	
	@Override
	public boolean isValid(String value, ConstraintValidatorContext context) {
		if(value == null || value.isBlank())
			return true;
		for(Enum<?> enumConst:this.enumClass.getEnumConstants()) {
			String enumName = enumConst.name();
			if(this.ignoreCase?enumName.equalsIgnoreCase(value):enumName.equals(value))
				return true;
		}
		return false;
	}

}
