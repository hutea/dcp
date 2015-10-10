package com.zhgl.util.sfl;

import java.lang.reflect.Method;

import org.apache.log4j.Logger;
import org.springframework.aop.ThrowsAdvice;

/**
 * 异常的抓取类，用来把异常纪录到日志
 * @author Administrator
 *
 */
public class ExceptionAdvisor implements ThrowsAdvice {

	public void afterThrowing(Method method, Object[] args, Object target, Exception ex) throws Throwable {
        Logger log = Logger.getLogger(target.getClass()); 
        log.warn("*****************************开始*****************************");  
        log.warn("发生异常的类: " + target.getClass().getName());
        log.warn("发生异常的方法: " + method.getName());   
        for (int i = 0; i < args.length; i++){
            log.warn("arg[" + i + "]: " + args[i]);  
        }
        log.warn("异常类: " + ex.getClass().getName());
        log.warn("异常消息: " + ex.getMessage());
        StackTraceElement[] stackTraceElements = ex.getStackTrace();//拿到所有的异常堆栈信息
        StringBuilder sb = new StringBuilder();
        String exceptionMethod = target.getClass().getName() + "." + method.getName(); //要返回的异常行信息
        String exceptionLine = "";
        for(StackTraceElement s : stackTraceElements){
        	if(s.toString().lastIndexOf(exceptionMethod) != -1){ //找到异常行
        		exceptionLine = s.toString();
        	}
        	sb.append(s.toString() + "\n");
        }
        log.warn("异常的行信息：" + exceptionLine);
        log.warn("异常的详细信息：" + sb.toString());
        log.warn("*****************************结束*****************************"); 
    }
}
