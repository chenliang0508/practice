package cn.com.upcard.mgateway.annotion;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import cn.com.upcard.mgateway.common.enums.Required;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface HttpParamName {
	String name();

	Required param() default Required.OPTIONAL;

	String restrict() default "";

	String hint() default "参数不合法";

	int maxLen() default Integer.MAX_VALUE;

	int minLen() default Integer.MIN_VALUE;

	long minValue() default Long.MIN_VALUE;

	long maxValue() default Long.MAX_VALUE;

	boolean unSignedInt() default false;
	
	String date() default "";

}
