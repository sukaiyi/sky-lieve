package com.sukaiyi.skylieve.global.annotation;

import java.lang.annotation.*;

/**
 * @author sukaiyi
 */
@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface APIKeyRequired {
}
