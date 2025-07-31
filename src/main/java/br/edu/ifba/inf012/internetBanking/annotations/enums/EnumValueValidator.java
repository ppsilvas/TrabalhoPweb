package br.edu.ifba.inf012.internetBanking.annotations.enums;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class EnumValueValidator implements ConstraintValidator<ValidEnumValue, String>{

	private Class<? extends Enum<?>> enumClass;
	private boolean ignoreClass;
	
	@Override
	public void initialize(ValidEnumValue annotation) {
		this.enumClass = annotation.enumClass();
		this.ignoreClass = annotation.ignoreCase();
	}
	
	@Override
	public boolean isValid(String value, ConstraintValidatorContext context) {
		if(value.isBlank())
			return true;
		for(Enum<?> enumConst:this.enumClass.getEnumConstants()) {
			String enumName = enumConst.name();
			if(this.ignoreClass?enumName.equalsIgnoreCase(enumName):enumName.equals(enumName))
				return true;
		}
		return false;
	}

}
