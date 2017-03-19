package com.application.taskengine.action;

import com.application.taskengine.vo.TaskSession;
import com.cheng.common.AjaxDone;
import com.cheng.common.Constant;
import com.cheng.common.UserSessionVO;
import com.cheng.jdbcspring.IDataBaseService;
import com.cheng.lang.exception.BusinessException;
import com.cheng.util.Predef;
import com.cheng.web.AbstractCommonAction;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.util.WebUtils;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;

@Controller
public class BusinessCommonAction extends AbstractCommonAction {

    public static final String PAGE = "page";
    public static final String Action = "action";
    @Resource
    public IDataBaseService dataBaseService;

    public BusinessCommonAction() {
    }

    protected String getMsgTipPage() {
        return "result/commontips";
    }

    /**
     * ;
     *
     * @param response
     * @return
     * @throws IOException
     */
    protected String redirectLogin(HttpServletResponse response) {
        try {
            response.sendRedirect("/login");
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    protected String redirectLogin(HttpServletResponse response, String url) {
        try {
            response.sendRedirect("/login?url=" + url);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 从session 里获得用户基本信息
     *
     * @param request
     * @return
     */
    protected TaskSession getTaskSession(HttpServletRequest request) {
        return (TaskSession) WebUtils.getSessionAttribute(request, Constant.SESSION_FRONT_KEY);
    }

    /**
     * cheng 2014-1-2
     *
     * @param defalut
     * @param request
     * @return
     */
    protected String getAction(String defalut, HttpServletRequest request) {
        String action = request.getParameter(Action);
        if (!StringUtils.isEmpty(action)) {
            return action;
        }
        return defalut;
    }

    /**
     * 判断用户是否登入系统 cheng 2014-1-3
     *
     * @param request
     * @return
     * @throws SQLException
     */
    protected boolean login(HttpServletRequest request,
                            HttpServletResponse response) throws BusinessException {
        UserSessionVO userSessionVO = getUserFromSession(request);
        if (userSessionVO == null) {
            return false;
        }
        return true;
    }

    protected String getPageNum(HttpServletRequest request) {
        String page = null;
        if (StringUtils.isEmpty(request.getParameter(PAGE))) {
            page = "1";
        } else {
            page = request.getParameter(PAGE);
        }
        return page;
    }

    protected boolean validategraphicscode(HttpServletRequest request) throws BusinessException {
        String codetype = request.getParameter("codetype");
        String codevalue = request.getParameter("codevalue");
        if (StringUtils.isEmpty(codevalue)) {
            return true;
        }
        Object codevalue_t = WebUtils.getSessionAttribute(request, codetype);
        if (codevalue.equals(codevalue_t)) {
            return true;
        }
        return false;
    }

    public AjaxDone AjaxDoneSucc(String message) {
        return new AjaxDone(200, message, "", "", "closeCurrent", "");
    }

    public AjaxDone AjaxDoneSuccNotcloseCurrent(String message) {
        return new AjaxDone(200, message, "", "", "", "");
    }

    public AjaxDone AjaxDoneError(String message) {
        return new AjaxDone(300, message, "", "", "closeCurrent", "");
    }

    public AjaxDone AjaxDoneError(String message, String dialogid) {
        return new AjaxDone(300, message, dialogid, dialogid, "closeCurrent", "");
    }

    @ExceptionHandler(Exception.class)
    @ResponseBody
    public AjaxDone exceptionHandler(Exception e, HttpServletRequest request) {
        e.printStackTrace();
        String errormsg = e.getMessage();
        if (e instanceof BusinessException) {
            BusinessException businessException = (BusinessException) e;
            return AjaxDoneError(Predef.isNotEmpty(errormsg) ? errormsg : "系统出错，请联系管理员！", businessException.getErrorCodeString());
        }
        return AjaxDoneError(Predef.isNotEmpty(errormsg) ? errormsg : "系统出错，请联系管理员！");
    }


}
