package br.edu.ifba.inf012.internetBanking.annotations.datas;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;


@Constraint(validatedBy = DatasConsistentesValidator.class)
@Target({ ElementType.TYPE })
@Retention(RetentionPolicy.RUNTIME)
public @interface DatasConsistentes {

    String message() default "A data inicial deve ser anterior à data final";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

    String dataInicial(); 

    String dataFinal();
}
