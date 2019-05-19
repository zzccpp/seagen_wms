package cn.seagen.base.aop;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import cn.seagen.base.bean.SystemLog;
import cn.seagen.base.dao.SystemLogDao;

/**
 * 日志记录切面,待完善...
 * @author zcp
 */
@Aspect
@Component
public class LoggingAspect {

	private SystemLogDao systemLogDaoImpl;

	@Resource
	public void setSystemLogDaoImpl(SystemLogDao systemLogDaoImpl) {
		this.systemLogDaoImpl = systemLogDaoImpl;
	}
	//该方法没有实际意义,只适用于定义切入点
	@Pointcut(value="execution(* cn.seagen.base.controller.*.*(..))")
	public void init(){};
	
	@Around(value = "init()")
	public Object doAround(ProceedingJoinPoint joinPoint) throws Throwable{
		long startTime = System.currentTimeMillis();
        Object object = joinPoint.proceed();
        //存正常日志
        SystemLog systemLog = createSystemLog(joinPoint,0);
        systemLog.setTime((int) (System.currentTimeMillis()-startTime));
        systemLogDaoImpl.insertSystemLog(systemLog);
        return object;
	}
	@AfterThrowing(value = "init()",throwing="e")
	public void doAfterThrowing(JoinPoint joinPoint, Throwable e){
		//存异常日志
		SystemLog systemLog = createSystemLog(joinPoint,1);
		String error = e.getMessage().length()>100? e.getMessage().substring(0, 100) : e.getMessage();//限制100
		systemLog.setLogMark(error);
		systemLogDaoImpl.insertSystemLog(systemLog);
	}
	
	public SystemLog createSystemLog(JoinPoint point,int type){
		SystemLog systemLog = new SystemLog();
        HttpServletRequest request = ((ServletRequestAttributes)RequestContextHolder.getRequestAttributes()).getRequest();
        //获取当前访问IP
        String ip = request.getRemoteAddr();
        systemLog.setIp(ip);
        //获取当前用户
        String userName = (String) request.getSession().getAttribute("userName");
        systemLog.setUserName(userName);
        //获取方法名称
        String methodName = point.getSignature().getName();
        systemLog.setMethodName(methodName);
		//获取参数
        Object[] args = point.getArgs();
        StringBuffer methodArgs = new StringBuffer("");
        for (Object o :args) {
        	if(!(o.toString().contains(".")&&!(o instanceof Float)&&!(o instanceof String))){//内置对象,或者自定义未写toString的不处理
        		methodArgs.append(o).append("&");
        	}else{
        		methodArgs.append(o.getClass().getSimpleName()).append("&");//只添加简单类名
        	}
		}
        if(methodArgs.length()>0) methodArgs = methodArgs.deleteCharAt(methodArgs.length()-1);
        if(methodArgs.length()>200){//限制200
        	systemLog.setMethodArgs(methodArgs.substring(0, 200));
        }else{
        	systemLog.setMethodArgs(methodArgs.toString());
        }
        systemLog.setType(type);
        return systemLog;
	}
}
