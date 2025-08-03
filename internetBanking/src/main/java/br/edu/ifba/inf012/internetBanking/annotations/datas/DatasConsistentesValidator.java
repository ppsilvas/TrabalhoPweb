package br.edu.ifba.inf012.internetBanking.annotations.datas;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.springframework.beans.BeanWrapperImpl;
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

        if (!(dataInicialValue instanceof LocalDateTime) || !(dataFinalValue instanceof LocalDateTime)) {
            throw new IllegalArgumentException("Os campos devem ser do tipo LocalDate.");
        }

        LocalDateTime dataInicial = (LocalDateTime) dataInicialValue;
        LocalDateTime dataFinal = (LocalDateTime) dataFinalValue;

        return dataInicial.isEqual(dataFinal)||dataInicial.isBefore(dataFinal);
    }
}
