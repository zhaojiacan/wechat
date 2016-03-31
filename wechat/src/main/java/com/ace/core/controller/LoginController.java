package com.ace.core.controller;

import javax.servlet.http.HttpServletRequest;

import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.LockedAccountException;
import org.apache.shiro.authc.UnknownAccountException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import com.ace.core.service.user.SysService;

/**
 * 
 * @author Administrator
 * 
 */
@Controller
@RequestMapping(value = { "/", "/user" })
public class LoginController {

	@Autowired
	private SysService sysService;

	// 用户登陆提交方法
	/**
	 * 
	 * <p>
	 * Title: login
	 * </p>
	 * <p>
	 * Description:
	 * </p>
	 * 
	 * @param session
	 * @param randomcode
	 *            输入的验证码
	 * @param usercode
	 *            用户账号
	 * @param password
	 *            用户密码
	 * @return
	 * @throws Exception
	 */
	
	@RequestMapping("/login.do")
	public String login(HttpServletRequest request, ModelMap model)
			throws Exception {

		// 校验验证码，防止恶性攻击
		// 从session获取正确验证码
		// 输入的验证和session中的验证进行对比

		// if(!(imageCaptchaService.validateResponseForID(sessionProvider.getSessionId(request,response),
		// captcha))){
		// //抛出异常
		// throw new CustomException("验证码输入错误");
		// }

		// 如果登陆失败从request中获取认证异常信息，shiroLoginFailure就是shiro异常类的全限定名
		String exceptionClassName = (String) request
				.getAttribute("shiroLoginFailure");

		// 根据shiro返回的异常类路径判断，抛出指定异常信息
		if (exceptionClassName != null) {
			if (UnknownAccountException.class.getName().equals(
					exceptionClassName)) {
				// 最终会抛给异常处理器
				model.addAttribute("msg", "账号不存在");
			} else if (IncorrectCredentialsException.class.getName().equals(
					exceptionClassName)) {
				model.addAttribute("msg", "用户名/密码错误");
			} else if (LockedAccountException.class.getName().equals(
					exceptionClassName)) {
				model.addAttribute("msg", "用户被锁定，请联系管理员开通");
			} else if ("randomCodeError".equals(exceptionClassName)) {
				model.addAttribute("msg", "验证码错误");
			} else if ("randomCodeNull".equals(exceptionClassName)) {
				model.addAttribute("msg", "验证码不能为空");
			} else {
				model.addAttribute("msg", "服务器异常，请稍后再试");
			}
		}
		// 此方法不处理登陆成功（认证成功），shiro认证成功会自动跳转到上一个请求路径
		// 登陆失败还到login页面
		return "login";
	}

	// 用户退出
	// @RequestMapping("/logout")
	// public String logout(HttpSession session)throws Exception{
	//
	// //session失效
	// session.invalidate();
	// //重定向到商品查询页面
	// return "redirect:/login.do";
	//
	// }

}
