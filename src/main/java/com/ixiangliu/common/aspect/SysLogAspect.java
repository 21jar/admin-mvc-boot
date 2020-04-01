package com.ixiangliu.common.aspect;

import com.alibaba.fastjson.JSONObject;
import com.ixiangliu.common.annotation.SysLog;
import com.ixiangliu.common.utils.HttpContextUtils;
import com.ixiangliu.common.utils.IPUtils;
import com.ixiangliu.modules.sys.entity.Log;
import com.ixiangliu.modules.sys.entity.User;
import com.ixiangliu.modules.sys.service.ILogService;
import org.apache.shiro.SecurityUtils;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Method;
import java.util.Date;

/**
 * 系统日志，切面处理类
 */
@Aspect
@Component
public class SysLogAspect {
	@Autowired
	private ILogService iLogService;
	
//	@Pointcut("execution(* com.ixiangliu.modules..*.*(..))")
	@Pointcut("@annotation(com.ixiangliu.common.annotation.SysLog)")
	public void logPointCut() {
		
	}

	@Around("logPointCut()")
	public Object around(ProceedingJoinPoint point) throws Throwable {
		long beginTime = System.currentTimeMillis();
		//执行方法
		Object result = point.proceed();
		//执行时长(毫秒)
		long time = System.currentTimeMillis() - beginTime;
		//保存日志
		saveSysLog(point, time);
		return result;
	}

	private void saveSysLog(ProceedingJoinPoint joinPoint, long time) {
		MethodSignature signature = (MethodSignature) joinPoint.getSignature();
		Method method = signature.getMethod();
		Log log = new Log();
		SysLog syslog = method.getAnnotation(SysLog.class);
		if(syslog != null){
			//注解上的描述
			log.setOperation(syslog.value());
		}
		//请求的方法名
		String className = joinPoint.getTarget().getClass().getName();
		String methodName = signature.getName();
		log.setMethod(className + "." + methodName + "()");
		//请求的参数
		Object[] args = joinPoint.getArgs();
		try{
			String params = JSONObject.toJSONString(args[0]);
			log.setParams(params);
		}catch (Exception e){

		}
		//获取request
		HttpServletRequest request = HttpContextUtils.getHttpServletRequest();
		//设置IP地址
		log.setIp(IPUtils.getIpAddr(request));
		//用户名
		User user = ((User) SecurityUtils.getSubject().getPrincipal());
		String username = user == null ? null : user.getUsername();
		log.setUsername(username);
		log.setTime(time);
		log.setCreateDate(new Date());
		request.getRequestURI();
		request.getMethod();
		//保存系统日志
		iLogService.save(log);
	}
}
