package com.sam.sam_parents.exceptions;

import com.sam.sam_parents.utils.Constants;
import com.sam.sap_commons.exchange.ApiMsg;
import com.sam.sap_commons.utils.AjaxUtils;
import com.sam.sap_commons.utils.ExceptionUtils;
import com.sam.sap_commons.utils.SysDefaults;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.log4j.Log4j2;
import org.apache.catalina.connector.ClientAbortException;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.SystemUtils;
import org.apache.tomcat.util.http.fileupload.impl.SizeLimitExceededException;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.multipart.MultipartException;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.resource.NoResourceFoundException;
import org.springframework.web.servlet.view.json.MappingJackson2JsonView;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Log4j2
@Order(3)
@ControllerAdvice
public class GlobalExceptionHandler {


    /**
     * ApiException的处理
     *
     * @param req
     * @return
     */
    @ExceptionHandler({ApiException.class})
    @ResponseBody
    public ApiMsg<?> handleApiException(HttpServletRequest req, Exception ex) {
        ex.printStackTrace();
        log.error(ExceptionUtils.getExceptionStackTrace(ex));

        ApiException apiex = (ApiException) ex;
        return ApiMsg.error(apiex.getMessage(), apiex.getErrorcode());
    }

    /**
     * SystemException的处理
     *
     * @param req
     * @param ex
     * @return
     */
    @ExceptionHandler({SystemException.class})
    public ModelAndView handleSystemException(HttpServletRequest req, Exception ex) {
        ex.printStackTrace();
        log.error(ExceptionUtils.getExceptionStackTrace(ex));

        SystemException sysex = (SystemException) ex;
        ModelAndView view = new ModelAndView("/error/system");
        view.addObject("message", sysex.getMessage());
        return view;

    }



    /**
     * MethodArgumentNotValidException
     *
     * @return
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public void handleMethodArgumentNotValidException(MethodArgumentNotValidException e, HttpServletRequest request, HttpServletResponse response) {
        String message = request.getRequestURL().toString() + " : "+ e.getMessage();
        log.error("handleMethodArgumentNotValidException-error={}", message);
        log.error("error={}", e);
        if (AjaxUtils.isAjaxRequest(request)) {
            fieldErrors(response, e.getBindingResult().getFieldErrors()
                    .stream().map(FieldError::getDefaultMessage).collect(Collectors.toList()));
        }
    }

    @ExceptionHandler(AjaxErrorException.class)
    public void handleAjaxErrorException(AjaxErrorException e, HttpServletRequest request, HttpServletResponse response) {
        String message = request.getRequestURL().toString() + " : "+ e.getMessage();
        log.error("handleAjaxErrorException-error={}", message);
        log.error("error={}", e);
        if (AjaxUtils.isAjaxRequest(request)) {
            fieldErrors(response, e.getErrorList());
        }
    }

    private void fieldErrors(HttpServletResponse response, List<String> errorList) {
        if (CollectionUtils.isEmpty(errorList)) {
            return;
        }
        Map<String, Object> attrMap = new HashMap<>();
        attrMap.put("fieldErrors", errorList);
        AjaxUtils.writeJson(attrMap, response);
    }

//    @ResponseBody
//    @ExceptionHandler(UncategorizedSQLException.class)
//    @ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR)
//    public ApiMsg<String> UncategorizedSQLException(HttpServletRequest request, Exception e) {
//        String message = request.getRequestURL().toString() + " : "+ e.getMessage();
//        log.error("uncategorizedSQLException-error={}", message);
//        log.error("error={}", e);
//        return ApiMsg.error("System Error");
//    }

//    @ResponseBody
//    @ExceptionHandler(RequestRejectedException.class)
//    @ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR)
//    public ApiMsg<String> RequestRejectedException(HttpServletRequest request, Exception e) {
//        String message = request.getRequestURL().toString() + " : "+ e.getMessage();
//        log.error("RequestRejectedException-error={}", message);
//        log.error("error={}", e);
//        return ApiMsg.error("System Error");
//
//    }


    @ResponseBody
    @ExceptionHandler(NoResourceFoundException.class)
    @ResponseStatus(value = HttpStatus.NOT_FOUND)
    public ModelAndView noResourceFoundException(HttpServletRequest request, HttpServletResponse response, Exception e) throws Exception{
        String message = request.getRequestURL().toString() + " : "+ e.getMessage();
        log.error("NoResourceFoundException-error={}", message);
        log.error("error={}", e);
        e.printStackTrace();
//        log.error(ExceptionUtils.getExceptionStackTrace(e));

        ModelAndView model = null;
        if (checkAPIrequest(request)) {
            model = new ModelAndView(new MappingJackson2JsonView());
            model.addObject("error", ApiMsg.error("System Error", 9999));
        } else {
            model = new ModelAndView("/error/404");
            model.addObject("errorMsg", message);
            model.addObject("evnInfo", getSystemInfo(request));
            model.addObject("errorTime", SysDefaults.now());
            response.sendError(HttpServletResponse.SC_NOT_FOUND, "system error");
        }
        return model;

    }

    @ResponseBody
    @ExceptionHandler(ClientAbortException.class)
    @ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR)
    public ApiMsg<String> clientAbortException(HttpServletRequest request, Exception e) {
        String message = request.getRequestURL().toString() + " : "+ e.getMessage();
        log.error("clientAbortException-error={}", message);
        log.error("error={}", e);
        return ApiMsg.error("System Error");

    }

    @ResponseBody
    @ExceptionHandler(MultipartException.class)
    @ResponseStatus(value = HttpStatus.REQUEST_ENTITY_TOO_LARGE)
    public ApiMsg<String> handleSizeLimitExceeded(HttpServletRequest request, HttpServletResponse response, MultipartException e) {
        String message = request.getRequestURL().toString() + " : "+ e.getMessage();
        log.error("handleSizeLimitExceeded-error={}", message);
        log.error("error={}", e);
        if (e.getCause().getCause() instanceof SizeLimitExceededException) {
            return ApiMsg.error("file too big");
        } else {
            throw e;
        }
    }

    /**
     * 所有異常
     *
     * @param request
     * @param ex
     * @return
     */
    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ModelAndView handleAllException(HttpServletRequest request, HttpServletResponse response, Exception ex) throws Exception {
        ex.printStackTrace();
        log.info(ExceptionUtils.getExceptionStackTrace(ex));

        ModelAndView model = null;
        if (checkAPIrequest(request)) {
            model = new ModelAndView(new MappingJackson2JsonView());
            model.addObject("error", ApiMsg.error("System Error", 9999));
        } else {
            model = new ModelAndView("/error/500");
            model.addObject("errorMsg", ex.getClass().getName());
            model.addObject("evnInfo", getSystemInfo(request));
            model.addObject("errorTime", SysDefaults.now());
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "system error");
        }
        return model;
    }
//    @ResponseBody
//    @ExceptionHandler(Exception.class)
//    @ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR)
//    public ApiMsg<String> exceptionHandler(HttpServletRequest request, HttpServletResponse response, Exception e) {
//        String message = request.getRequestURL().toString() + " : "+ e.getMessage();
//        log.error("error={}", message);
//        log.error("error={}", e);
//        return ApiMsg.error("System Error");
//    }
    private String getSystemInfo(HttpServletRequest req) {
        StringBuilder ibuf = new StringBuilder();
        // OS
        ibuf.append("OS： " + SystemUtils.OS_NAME);
        ibuf.append(" " + SystemUtils.OS_ARCH);
        ibuf.append(" " + SystemUtils.OS_VERSION);
        ibuf.append("\r\n");
        // Java
        ibuf.append("Java： " + SystemUtils.JAVA_VERSION);
        ibuf.append(" " + SystemUtils.JAVA_VENDOR);
        ibuf.append("\r\n");
        // Server
        ibuf.append("Server Info： " + req.getSession().getServletContext().getServerInfo());
        ibuf.append("\r\n");
        return ibuf.toString();
    }

    private boolean checkAPIrequest(HttpServletRequest req) {
        if (req.getRequestURI().indexOf("api") == -1) {
            return false;
        } else {
            return true;
        }
    }
}
