//package com.sam.sam_parents.configs;
//
//
//import com.accenture.core.exception.ApiException;
//import com.accenture.core.util.SessionManager;
//import com.sam.biz.mail.service.MailApi;
//import com.sam.common.bean.SysLogBean;
//import com.sam.common.mapper.SysLogMapper;
//import com.sam.util.UserUtil;
//import com.accenture.userauth.bean.SecurityUser;
//import com.alibaba.fastjson.JSON;
//import com.alibaba.fastjson.JSONObject;
//import com.google.common.collect.Lists;
//import jakarta.annotation.Resource;
//import jakarta.servlet.ServletRequest;
//import jakarta.servlet.ServletResponse;
//import jakarta.servlet.http.HttpServletRequest;
//import lombok.extern.slf4j.Slf4j;
//import org.apache.commons.lang3.StringUtils;
//import org.aspectj.lang.JoinPoint;
//import org.aspectj.lang.annotation.*;
//import org.aspectj.lang.reflect.MethodSignature;
//import org.springframework.stereotype.Component;
//import org.springframework.web.context.request.RequestContextHolder;
//import org.springframework.web.context.request.ServletRequestAttributes;
//import org.springframework.web.multipart.MultipartFile;
//
//import java.util.Date;
//import java.util.List;
//import java.util.Objects;
//
///**
// * 项目日志 切面
// */
//@Slf4j
//@Aspect
//@Component
//public class WebLogAspect {
//
//    private static final String CASE_ID = "caseId";
//    private static final String TRANSACTION_ID = "transactionId";
//    public static final String SUCCESS = "success";
//    public static final String CLIENT_LOGIN = "login";
//    public static final String FAIL = "fail";
//    public static final List<String> HEADERS =
//            Lists.newArrayList("x-forwarded-for", "Proxy-Client-IP", "WL-Proxy-Client-IP");
//    public static final String JSON_START = "\"";
//    public static final String JSON_NAME_END = "\":";
//    @Resource
//    private SysLogMapper sysLogMapper;
////    @Resource
////    private MqHistoryActivityProducer mqHistoryActivityProducer;
//    @Resource
//    private MailApi mailApi;
//    /**
//     * 统计时间
//     */
//    ThreadLocal<Long> startTime = new ThreadLocal<>();
//    ThreadLocal<SysLogBean> logBean = new ThreadLocal<>();
//
//    @Pointcut("execution(public * com.sam.biz.po.controller..*.doSubmit(..))" +
//            "||execution(public * com.sam.biz.po.controller..*.doSave(..))" +
//            "||execution(public * com.sam.biz.po.controller..*.detail(..))" +
//            "||execution(public * com.sam.biz.po.controller..*.closeTask(..))" +
//            "||execution(public * com.sam.biz.po.controller..*.changeAssignee*(..))" +
//            "||execution(public * com.sam.api.controller.*.create(..))" +
//            "||execution(public * com.sam.api.controller.*.update(..))" +
//            "||execution(public * com.sam.api.controller.*.updateVouchers(..))" +
//            "||execution(public * com.sam.api.controller..*.getCompletedList(..))" +
//            "||execution(public * com.sam.api.controller.*.del(..))" +
//            "||execution(public * com.sam.common.controller..*.home(..))" +
//            "||execution(public * com.accenture.userauth.controller..*.logout(..))")
//    public void webLog() {
//    }
//
//    /**
//     * 设置操作异常切入点记录异常日志 扫描所有controller包下操作
//     */
//    @Pointcut("execution(* com.accenture..*.controller..*.*(..))")
//    public void pointCutController() {
//    }
//
//    @Before("webLog() || pointCutController()")
//    public void doBefore(JoinPoint joinPoint) throws Throwable {
//
//        try {
//            this.initLog(joinPoint);
//        } catch (Exception e) {
//            log.error("Error={}", e);
//        }
//    }
//
////
////    @AfterReturning(pointcut = "webLog()", returning = "obj")
////    public void doAfterReturning(JoinPoint joinPoint, Object obj) throws Throwable {
////        try {
////            this.mqHistoryActivityProducer.sendMsg(UserUtil.getUser());
////        } catch (Exception e) {
////            log.error("saveHistoryActivity Error={}", e);
////        }
////        try {
////            this.saveLog(logBean.get(), JSON.toJSONString(obj), SUCCESS);
////        } catch (Exception e) {
////            log.error("Error={}", e);
////        }
////    }
//
//    /**
//     * 异常返回通知，用于拦截异常日志信息 连接点抛出异常后执行
//     *
//     * @param joinPoint 切入点
//     * @param e         异常信息
//     */
//    @AfterThrowing(pointcut = "pointCutController()", throwing = "e")
//    public void sysException(JoinPoint joinPoint, Throwable e) {
//        if (e instanceof ApiException) {
//            log.info("API 参数异常");
////            return;
//        }
//        try {
//            SysLogBean sysLog = logBean.get();
//            if (StringUtils.containsIgnoreCase(sysLog.getUrl(), CLIENT_LOGIN)) {
//                return;
//            }
//            saveLog(sysLog, getExceptionMsg(e), FAIL);
//            if (e instanceof ApiException) {
//                log.info("sysException-----API 参数异常");
//            }
//            if (UserUtil.getUser() == null) {
//                log.info("sysException-----用户未登录");
//            }
//            if (UserUtil.getUser() == null && !(e instanceof ApiException)) {
//                log.info("sysException-----用户登陆超时");
//            }
//            // 发送邮件提醒
//            this.mailApi.sysExceptionMail(sysLog, e);
//        } catch (Exception exception) {
//            log.error("Error={}", exception);
//        }
//    }
//
//    private void saveLog(SysLogBean log, String result, String status) {
//        // 尝试获取业务主键
//        if (StringUtils.equals(status, SUCCESS)) {
//            getBizKey(log, result);
//        }
//        log.setStatus(status);
//        log.setResult(result);
//        log.setCostMillis(System.currentTimeMillis() - startTime.get());
//        // 记录日志
//        sysLogMapper.insert(log);
//    }
//
//    private String getParamsInfo(JoinPoint point) {
//        String[] parameterNames = ((MethodSignature) point.getSignature()).getParameterNames();
//        JSONObject args = new JSONObject();
//        if (Objects.isNull(parameterNames)) {
//            return "";
//        }
//        for (int i = 0; i < parameterNames.length; i++) {
//            String parameterName = parameterNames[i];
//            Object arg = point.getArgs()[i];
//            if (arg == null || arg instanceof ServletRequest || arg instanceof ServletResponse || arg instanceof MultipartFile) {
//                continue;
//            }
//            if (arg instanceof String || arg instanceof Integer || arg instanceof Long || arg instanceof List || arg instanceof Date) {
//                args.put(parameterName, arg.toString());
//            } else {
//                try {
//                    String jsonString = JSON.toJSONString(arg);
//                    JSONObject json = JSON.parseObject(jsonString);
//                    args.put(parameterName, json);
//                } catch (Exception e) {
//                    args.put(parameterName, arg.toString());
//                    ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
//                }
//            }
//        }
//        return JSON.toJSONString(args);
//    }
//
//    /**
//     * 初始化日志参数
//     *
//     * @param joinPoint
//     */
//    private void initLog(JoinPoint joinPoint) {
//        try {
//            startTime.set(System.currentTimeMillis());
//            SysLogBean bean = new SysLogBean();
//            String method = joinPoint.getSignature().getName();
//            // 接收到请求，记录请求内容
//            ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
//            HttpServletRequest request = attributes.getRequest();
//            // 记录下请求内容
//            bean.setMethod(method);
//            bean.setClassName(joinPoint.getTarget().getClass().getSimpleName());
//            bean.setUrl(request.getRequestURI());
//            bean.setIp(getIP(request));
//            if("home".equals(method)){
//                SecurityUser user = SessionManager.getValue(com.accenture.core.util.Constants.SESSION_KEY_USER_INFO);
//                bean.setUserName(user.getUsername());
//            }else{
//                bean.setUserName(UserUtil.getUserName());
//            }
//            bean.setCreateTime(new Date());
//            String paramsInfo = getParamsInfo(joinPoint);
//            bean.setParams(paramsInfo);
//            // 尝试获取业务主键
//            getBizKey(bean, paramsInfo);
//            logBean.set(bean);
//        } catch (Exception e) {
//            log.error("Error={}", e);
//        }
//    }
//
//    /**
//     * 转换异常信息为字符串
//     *
//     * @param exception 异常名称
//     */
//    public static String getExceptionMsg(Throwable exception) {
//        StringBuilder errMsg = new StringBuilder();
//        errMsg.append(exception.getClass().getName());
//        errMsg.append(":");
//        errMsg.append(exception.getMessage());
//        errMsg.append("\n\t");
//        for (StackTraceElement item : exception.getStackTrace()) {
//            errMsg.append(item);
//            errMsg.append("\n");
//        }
//        return errMsg.toString();
//    }
//
//    /**
//     * @param request
//     * @return
//     */
//    private static String getIP(HttpServletRequest request) {
//        for (String header : HEADERS) {
//            String ip = request.getHeader(header);
//            if (StringUtils.isNotBlank(ip) && !StringUtils.equals(ip, "unknown")) {
//                return ip;
//            }
//        }
//        return request.getRemoteAddr();
//    }
//
//    private static String findByName(String result, String target) {
//        target = JSON_START + target + JSON_NAME_END;
//        if (!StringUtils.contains(result, target)) {
//            return "";
//        }
//        String valueStr = result.split(target)[1];
//        int endIndex = valueStr.indexOf(JSON_START, valueStr.indexOf(JSON_START) + 1);
//        return valueStr.substring(1, endIndex);
//    }
//
//    private static void getBizKey(SysLogBean logBean, String result) {
//        try {
//            if (StringUtils.isBlank(logBean.getCaseId())) {
//                logBean.setCaseId(findByName(result, CASE_ID));
//            }
//
//            if (StringUtils.isBlank(logBean.getCaseId())) {
//                logBean.setCaseId(findByName(result, TRANSACTION_ID));
//            }
//        } catch (Exception e) {
//            log.error("result = {}", result);
//            log.error("error = {}", e);
//        }
//    }
//
//}
