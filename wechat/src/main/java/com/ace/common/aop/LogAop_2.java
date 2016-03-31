package com.ace.common.aop;



import java.lang.reflect.Method;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.ace.common.annotation.Log;
import com.ace.core.bean.OpRecord;
import com.ace.core.bean.user.ActiveUser;
import com.ace.core.service.OpRecordService;
import com.ace.core.util.StringUtil;
import com.alibaba.fastjson.JSONObject;

@Aspect
@Component
public class LogAop_2 {
	
	@Autowired 
	private OpRecordService opRecordService;

	
	ThreadLocal<Long> time=new ThreadLocal<Long>();
	ThreadLocal<String> tag=new ThreadLocal<String>();
	
	@Pointcut("@annotation(com.ace.common.annotation.Log)")
	public void log(){
		System.out.println("我是一个切入点");
	}
	
	/**
	 * 在所有标注@Log的地方切入
	 * @param joinPoint
	 */
//	@Before("log()")
//	public void beforeExec(JoinPoint joinPoint){
//		
//		time.set(System.currentTimeMillis());
//		tag.set(UUID.randomUUID().toString());
//		
//		info(joinPoint);
//		
//		MethodSignature ms=(MethodSignature) joinPoint.getSignature();
//		Method method=ms.getMethod();
//		System.out.println(method.getAnnotation(Log.class).name()+"标记"+tag.get());
//	}
	
	@SuppressWarnings("rawtypes")
	@After("log()")
	public void afterExec(JoinPoint joinPoint) throws Throwable{
//		MethodSignature ms=(MethodSignature) joinPoint.getSignature();
//		Method method=ms.getMethod();
//		//取出主体信息
//		Subject subject = SecurityUtils.getSubject();
//		//从主体信息取出身份信息
//		ActiveUser activeUser=(ActiveUser) subject.getPrincipal();
//		//写入
//		System.out.println("标记为"+tag.get()+"的方法"+method.getName()+"运行消耗"+(System.currentTimeMillis()-time.get())+"ms");
		System.err.println("拦截方法,进入日志记录");
        // 拦截的实体类
        Object target = joinPoint.getTarget();
        // 拦截的方法名称
        String methodName = joinPoint.getSignature().getName();
        // 拦截的方法参数
        Object[] args = joinPoint.getArgs();
		//取出当前登录用户主题信息
		Subject subject = SecurityUtils.getSubject();
		ActiveUser activeUser=(ActiveUser) subject.getPrincipal(); 
        // 拦截的放参数类型
        Class[] parameterTypes = ((MethodSignature) joinPoint.getSignature())
                .getMethod().getParameterTypes();
        Signature s = joinPoint.getSignature();
        String str = s.toLongString().split(" ")[1];
        System.out.println("LogAop_2.aroundExec()"+str);
//        Object object = null;
         
        //需要转换成Json的HashMap
        Map<String, Object> maps = new HashMap<String, Object>();
        Map<String, Object> parammaps = new HashMap<String, Object>();
        // 获得被拦截的方法
        Method method = target.getClass().getMethod(methodName, parameterTypes);
        if (null != method) {
            // 判断是否包含自定义的注解
            if (method.isAnnotationPresent(Log.class)) {
                // 获取自定义注解实体
                Log myAnnotation = method
                        .getAnnotation(Log.class);
                //日志类实体类
                OpRecord opRecord = new OpRecord();
                
                opRecord.setUid(activeUser.getUserid());
                opRecord.setOperTable(myAnnotation.tableName());
                opRecord.setOperType(myAnnotation.option());
                maps.put("方法名", method.getName());
                parammaps.put("方法名", method.getName());
                //循环获得所有参数对象
                for(int i=0; i<args.length; i++){
                    if (null != args[i]) {
                        parammaps.put("args["+i+"]", args[i]);
                    }
                    else {
                        parammaps.put("args["+i+"]", "空参数");
                    }
                }
                maps.put("参数", parammaps);
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");  
                maps.put("操作时间", sdf.format(new Date()));
                // 获取服务运行结果
                try {
//                    object = joinPoint.proceed();// 执行该方法
                    maps.put("状态", "成功");
                    opRecord.setStatus(1);
                    opRecord.setDescription(myAnnotation.name());
                } catch (Exception e) {
                    System.err.println(e.getMessage());
                    maps.put("状态", "失败");
                    opRecord.setStatus(0);
                    opRecord.setDescription(e.getMessage());
                }
                //将参数转化为Json字符串
                opRecord.setOperParam(new JSONObject(maps).toString());
                opRecord.setOperTime(new Date());
                opRecord.setId(StringUtil.getUUID());
                System.err.println(new JSONObject(maps).toString());  
                 
                //记录相关日志
                if (null != opRecordService) {
//                    try {
//                        if(1 == opRecordService.add(log)){
//                            System.err.println("日志记录成功");
//                        }
//                        else{
//                            System.err.println("日志记录失败");
//                        }
//                         
//                    } catch (Exception e) {
//                        e.printStackTrace();
//                    }
                	int count = opRecordService.add(opRecord);
                	System.out.println("LogAop_2.aroundExec(日志记录成功)"+count);
                }
                else{
                    System.err.println("自动注入失败，日志未记录");
                }
 
            } else { // 没有包含该注解则不进行其他处理
//                object = pjp.proceed();// 执行该方法
            }
 
        } else { // 不需要拦截，直接运行
//            object = joinPoint.proceed(); // 执行该方法
        }
        info(joinPoint);
//        Object object2=pjp.proceed();
//        System.out.println("LogAop_2.aroundExec()"+object2);
	}
	
//	@SuppressWarnings("rawtypes")
//	//主要记录方法是否执行成功，所以采用环绕的方法
//	@Around("log()")
//	public Object aroundExec(ProceedingJoinPoint pjp) throws Throwable{
//		System.err.println("拦截方法,进入日志记录");
//        // 拦截的实体类
//        Object target = pjp.getTarget();
//        // 拦截的方法名称
//        String methodName = pjp.getSignature().getName();
//        // 拦截的方法参数
//        Object[] args = pjp.getArgs();
//		//取出当前登录用户主题信息
//		Subject subject = SecurityUtils.getSubject();
//		ActiveUser activeUser=(ActiveUser) subject.getPrincipal(); 
//        // 拦截的放参数类型
//        Class[] parameterTypes = ((MethodSignature) pjp.getSignature())
//                .getMethod().getParameterTypes();
//        Signature s = pjp.getSignature();
//        String str = s.toLongString().split(" ")[1];
//        System.out.println("LogAop_2.aroundExec()"+str);
//        Object object = null;
//         
//        //需要转换成Json的HashMap
//        Map<String, Object> maps = new HashMap<String, Object>();
//        Map<String, Object> parammaps = new HashMap<String, Object>();
//        // 获得被拦截的方法
//        Method method = target.getClass().getMethod(methodName, parameterTypes);
//        if (null != method) {
//            // 判断是否包含自定义的注解
//            if (method.isAnnotationPresent(Log.class)) {
//                // 获取自定义注解实体
//                Log myAnnotation = method
//                        .getAnnotation(Log.class);
//                //日志类实体类
//                OpRecord opRecord = new OpRecord();
//                
//                opRecord.setUid(activeUser.getUserid());
//                opRecord.setOperTable(myAnnotation.tableName());
//                opRecord.setOperType(myAnnotation.option());
//                maps.put("方法名", method.getName());
//                parammaps.put("方法名", method.getName());
//                //循环获得所有参数对象
//                for(int i=0; i<args.length; i++){
//                    if (null != args[i]) {
//                        parammaps.put("args["+i+"]", args[i]);
//                    }
//                    else {
//                        parammaps.put("args["+i+"]", "空参数");
//                    }
//                }
//                maps.put("参数", parammaps);
//                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");  
//                maps.put("操作时间", sdf.format(new Date()));
//                // 获取服务运行结果
//                try {
//                    object = pjp.proceed();// 执行该方法
//                    maps.put("状态", "成功");
//                    opRecord.setStatus(1);
//                    opRecord.setDescription(myAnnotation.name());
//                } catch (Exception e) {
//                    System.err.println(e.getMessage());
//                    maps.put("状态", "失败");
//                    opRecord.setStatus(0);
//                    opRecord.setDescription(e.getMessage());
//                }
//                //将参数转化为Json字符串
//                opRecord.setOperParam(new JSONObject(maps).toString());
//                opRecord.setOperTime(new Date());
//                opRecord.setId(StringUtil.getUUID());
//                System.err.println(new JSONObject(maps).toString());  
//                 
//                //记录相关日志
//                if (null != opRecordService) {
////                    try {
////                        if(1 == opRecordService.add(log)){
////                            System.err.println("日志记录成功");
////                        }
////                        else{
////                            System.err.println("日志记录失败");
////                        }
////                         
////                    } catch (Exception e) {
////                        e.printStackTrace();
////                    }
//                	int count = opRecordService.add(opRecord);
//                	System.out.println("LogAop_2.aroundExec(日志记录成功)"+count);
//                }
//                else{
//                    System.err.println("自动注入失败，日志未记录");
//                }
// 
//            } else { // 没有包含该注解则不进行其他处理
//                object = pjp.proceed();// 执行该方法
//            }
// 
//        } else { // 不需要拦截，直接运行
//            object = pjp.proceed(); // 执行该方法
//        }
//        info(pjp);
//        Object object2=pjp.proceed();
//        System.out.println("LogAop_2.aroundExec()"+object2);
//        return object;
////		System.out.println("我是Around，来打酱油的");
//		
////		return pjp.proceed();
//	}
	
	private void info(JoinPoint joinPoint){
		System.out.println("--------------------------------------------------");
		System.out.println("King:\t"+joinPoint.getKind());
		System.out.println("Target:\t"+joinPoint.getTarget().toString());
		Object[] os=joinPoint.getArgs();
		System.out.println("Args:");
		for(int i=0;i<os.length;i++){
			System.out.println("\t==>参数["+i+"]:\t"+os[i].toString());
		}
		System.out.println("Signature:\t"+joinPoint.getSignature());
		System.out.println("SourceLocation:\t"+joinPoint.getSourceLocation());
		System.out.println("StaticPart:\t"+joinPoint.getStaticPart());
		System.out.println("--------------------------------------------------");
	}
	
}
