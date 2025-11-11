package nhatm.project.demo.aop;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class PerformanceMonitorAspect {
    
    @Pointcut("execution(* nhatm.project.demo.aop.*.*(..))")
    public void allServiceMethods() {}

    @Around("allServiceMethods()")
    public Object measureExecutionTime(ProceedingJoinPoint joinPoint) throws Throwable {
        long startTime  = System.currentTimeMillis();

        Object result = joinPoint.proceed();

        long endTime = System.currentTimeMillis();

        long duration = endTime - startTime;

        System.out.println(
            "--- AOP MONITOR ---: Phương thức " + joinPoint.getSignature().getName() + 
            " thực thi mất " + duration + "ms"
        );

        return result;
    }
}
