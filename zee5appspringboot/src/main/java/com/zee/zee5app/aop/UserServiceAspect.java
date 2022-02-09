package com.zee.zee5app.aop;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;


@Component
@Aspect //its a container where we hold all our aop code
public class UserServiceAspect {
	
	//point cut expression - criteria of execution
	//jointpoint - method executed or called like addUser
	@Before(value = "execution(* com.zee.zee5app.service.Impl.*.*(..))")
	public void beforeAllServiceMethods(JoinPoint joinPoint) {
		//action - advice
		System.out.println("before hello");
		System.out.println(joinPoint.getTarget());
	}
	
	@After(value = "execution(* com.zee.zee5app.service.Impl.*.*(..))")
	public void afterAllServiceMethods(JoinPoint joinPoint) {
		//action - advice
		System.out.println("after hello");
		System.out.println(joinPoint.getTarget());
	}

}
