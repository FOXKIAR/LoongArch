package cn.foxkiar.loongarch.validation;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
@Documented
@Constraint(validatedBy = IsPhoneValidator.class)
public @interface Phone {
    String message() default "不是合法的手机号";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
