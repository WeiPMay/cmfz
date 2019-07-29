package com.baizhi.aspect;

import com.baizhi.annotation.AopAnnotation;
import com.baizhi.util.SerializeUtils;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.core.StringRedisTemplate;

/*@Configuration
@Aspect*/
public class AopCache {
   /* @Autowired
    private StringRedisTemplate stringRedisTemplate;
    @Around("execution(* com.baizhi.service.*.query*(..))")
    public Object around(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
        //是否有当前注解 如果有 切 没有 放行
        MethodSignature methodSignature = (MethodSignature) proceedingJoinPoint.getSignature();
        AopAnnotation annotation = methodSignature.getMethod().getAnnotation(AopAnnotation.class);
        if(annotation!=null){
            System.out.println("当前方法有助解，执行切面方法");
            //获得namespace
            String id=proceedingJoinPoint.getTarget().getClass().getName();
            //获取key
            String method = proceedingJoinPoint.getSignature().getName();
            //获取参数
            Object[] args = proceedingJoinPoint.getArgs();
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append(method);
            for (int i = 0; i < args.length; i++) {
                Object arg = args[i];
                stringBuilder.append(arg);
            }
            String key = stringBuilder.toString();
            String value = (String) stringRedisTemplate.opsForHash().get(id, key);
            if(value==null){
                System.out.println("缓存中没有值");
                Object proceed = proceedingJoinPoint.proceed();
                System.out.println("存入缓存");
                stringRedisTemplate.opsForHash().put(id,key, SerializeUtils.serialize(proceed));
                return proceed;
            }else {
                System.out.println("缓存中有直接拿");
                return SerializeUtils.serializeToObject(value);
            }
        }else {
            System.out.println("当前方法没有注解，不存入缓存");
            return proceedingJoinPoint.proceed();
        }

    }
    @AfterReturning("execution(* com.baizhi.service.*.*(..)) && !execution(* com.baizhi.service.*.query*(..)) ")
    public void after(JoinPoint joinPoint){
        System.out.println("清除当前namespace下所有缓存");
        String id = joinPoint.getTarget().getClass().getName();
        stringRedisTemplate.delete(id);
    }*/

}
