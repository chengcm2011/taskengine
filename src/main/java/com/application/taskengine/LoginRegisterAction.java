package com.application.taskengine;

import com.application.taskengine.action.BusinessCommonAction;
import com.application.taskengine.vo.TaskSession;
import com.cheng.lang.exception.BusinessException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 */
@Controller
public class LoginRegisterAction extends BusinessCommonAction {

	@RequestMapping("/loginout")
	public String loginout(HttpServletRequest request, HttpServletResponse response, Model model) throws BusinessException {
		removeUserFromSession(request,response);
		return redirect(response,"/index") ;
	}

	@RequestMapping("/login")
	public String index(HttpServletRequest request, HttpServletResponse response, Model model) throws BusinessException {
		return redirect(response, "management/index");
//		return "login" ;
	}
	@RequestMapping("/loginaction")
	public String loginaction(HttpServletRequest request, HttpServletResponse response, Model model) throws BusinessException {
			String username = request.getParameter("username");
			String password = request.getParameter("password");
//			ValidateUtil v = new ValidateUtil(getParamFromReq(request));
//			v.start("username").required().minlength(4).maxlength(16);
//			v.start("password").required().minlength(6).maxlength(16);
//			if(!v.isVal()){
//				return forwardError(model,"用户名或者密码有误！");
//			}
			try {
				if("cheng".equals(username) && "124817".equals(password)){
					TaskSession taskSession = new TaskSession();
					taskSession.setShowname(username);
					taskSession.setId(1);
					setUserToSession(request, response, taskSession);
				}else {
					return forwardError(model,"用户名或者密码有误！");
				}
			}catch (Exception e){
				e.printStackTrace();
				return forwardError(model,"用户名或者密码有误！");
			}
			return redirect(response,"/management/index");
		}
}
