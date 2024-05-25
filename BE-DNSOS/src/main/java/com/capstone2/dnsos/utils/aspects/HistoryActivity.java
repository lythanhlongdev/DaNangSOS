package com.capstone2.dnsos.utils.aspects;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.util.logging.Logger;

//@Component
//@Aspect
public class HistoryActivity {
//    private final Logger logger = Logger.getLogger(getClass().getName());
//
//    @Pointcut("within(@org.springframework.web.bind.annotation.RestController *)")
//    public void controllerMethods() {}
//
//    @Around("controllerMethods() && execution(* com.capstone2.dnsos.controllers.HistoryController.createHistory(..))")
//    public Object logHistoryActivity(ProceedingJoinPoint joinPoint) throws Throwable {
//        String methodName = joinPoint.getSignature().getName();
//        String remoteAddress = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes())
//                .getRequest().getRemoteAddr();
//        logger.info("User activity started: " + methodName + ", IP address: " + remoteAddress);
//        // trước khi gọi controller
//        Object result = joinPoint.proceed();
//        // sau khi gọi controller
//        // socket  sent mess
//        logger.info("User activity finished: " + methodName);
//        return result;
//    }


}
