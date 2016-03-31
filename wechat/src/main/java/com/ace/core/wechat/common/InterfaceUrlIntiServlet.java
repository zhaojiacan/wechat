package com.ace.core.wechat.common;
 
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
 
/**
 * 
 * ClassName: InterfaceUrlIntiServlet
 * 
 * @Description: 項目文件初始化
 * @author dapengniao
 * @date 2015/10/13
 */
public class InterfaceUrlIntiServlet extends HttpServlet {
 
    private static final long serialVersionUID = 1L;
 
    @Override
    public void init(ServletConfig config) throws ServletException {
        InterfaceUrlInti.init();
    }
 
}