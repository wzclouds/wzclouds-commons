package com.github.wzclouds.injection.aspect;

import com.github.wzclouds.injection.annonation.InjectionResult;
import com.github.wzclouds.injection.core.InjectionCore;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;

/**
 * InjectionResult 注解的 AOP 工具
 *
 * @author wzclouds
 * @create 2020年01月19日09:27:41
 */
@Aspect
@AllArgsConstructor
@Slf4j
public class InjectionResultAspect {
    private InjectionCore injectionCore;


    @Pointcut("@annotation(com.github.wzclouds.injection.annonation.InjectionResult)")
    public void methodPointcut() {
    }


    @Around("methodPointcut()&&@annotation(anno)")
    public Object interceptor(ProceedingJoinPoint pjp, InjectionResult anno) throws Throwable {
        Object proceed = pjp.proceed();
        try {
            injectionCore.injection(proceed, anno.isUseCache());
        } catch (Exception e) {
            log.error("AOP拦截@RemoteResult出错", e);
        }
        return proceed;
    }
}
