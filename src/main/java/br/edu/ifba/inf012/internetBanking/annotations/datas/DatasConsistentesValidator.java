package br.edu.ifba.inf012.internetBanking.annotations.datas;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.springframework.beans.BeanWrapperImpl;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class DatasConsistentesValidator implements ConstraintValidator<DatasConsistentes, Object> {

    private String dataInicialField;
    private String dataFinalField;

    @Override
    public void initialize(DatasConsistentes constraintAnnotation) {
        this.dataInicialField = constraintAnnotation.dataInicial();
        this.dataFinalField = constraintAnnotation.dataFinal();
    }

    @Override
    public boolean isValid(Object value, ConstraintValidatorContext context) {
        if (value == null) {
            return true; 
        }

        Object dataInicialValue = new BeanWrapperImpl(value).getPropertyValue(dataInicialField);
        Object dataFinalValue = new BeanWrapperImpl(value).getPropertyValue(dataFinalField);

        if (dataInicialValue == null || dataFinalValue == null) {
            return true;
        }

        if (!(dataInicialValue instanceof LocalDate) || !(dataFinalValue instanceof LocalDate)) {
            throw new IllegalArgumentException("Os campos devem ser do tipo LocalDate.");
        }

        LocalDate dataInicial = (LocalDate) dataInicialValue;
        LocalDate dataFinal = (LocalDate) dataFinalValue;

        return dataInicial.isEqual(dataFinal)||dataInicial.isBefore(dataFinal);
    }
}
