package com.luv2code.aopdemo.aspect;

import java.awt.Desktop.Action;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import com.luv2code.aopdemo.Account;

@Aspect
@Component
@Order(2)
public class MyDemoLoggingAspect {

	//add a new advice for @AfterReturning on the findAccount method
	
	@AfterReturning(
				pointcut = "execution(* com.luv2code.aopdemo.dao.AccountDAO.findAccount(..))",
				returning = "result"
			)
	public void afterReturningFindAccountAdvice(JoinPoint joinPoint, List<Account> result)
	{
		//print out which method we are advising on
		String method = joinPoint.getSignature().toShortString();
		
		System.out.println("\n=======>>>>> Execution @AfterReturning on method: "+method);
		
		//print out the result of the method call
		System.out.println("\n=======>>>>> Result is: "+result);
		
		//let's post-process the data ---- let;s modify the data
		
		
		//convert the account name to uppercase
		convertAccountNamesToUppercase(result);
	
		System.out.println("\n=======>>>>> After convert Upper case Result is: "+result);
	}
	
	
	
	private void convertAccountNamesToUppercase(List<Account> result) {

		//loop through account
		
		List<String> collect = result.stream().map(theAccount -> theAccount.getName().toUpperCase()).collect(Collectors.toList());
		
		System.out.println(collect);
		
		
		result.get(0).setName(collect.get(0));
		result.get(1).setName(collect.get(1));
		result.get(2).setName(collect.get(2));
		
//		
//		for(Account theAccount : result)
//		{
//			String upperCase = theAccount.getName().toUpperCase();
//			
//			theAccount.setName(upperCase);
//		}
		//get uppercase version of name
		
		//update the name on the account
	}



	@Before("com.luv2code.aopdemo.aspect.util.LuvAopExpressions.forDaoPackageNoGetterSetter()")
	public void beforeAddAccountAdvice(JoinPoint theJoinPoint)
	{
		System.out.println("\n=======>>>>> Execution Logging Aspect on method");
		
		//display the method signature
		MethodSignature methodSig = (MethodSignature)theJoinPoint.getSignature();
		System.out.println("Method: "+methodSig);
		
		//display method argument
		
		Object[] args = theJoinPoint.getArgs();
		
		for(Object arg : args)
		{
			System.out.println(arg);
			
			if(arg instanceof Account)
			{
				//downcast and print Account specific stuff
				
				Account account = (Account) arg;
				
				System.out.println("Account Name: "+account.getName());
				System.out.println("Account Level: "+account.getLevel());
				
			}
			
		}
	}
	
	
}





















