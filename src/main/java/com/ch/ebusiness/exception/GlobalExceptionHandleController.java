package com.ch.ebusiness.exception;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.sql.SQLException;
/**
 * 统一异常处理
 */
@ControllerAdvice
public class GlobalExceptionHandleController
{
	@ExceptionHandler(value=Exception.class)
	public String exceptionHandler(Exception e, Model model)
	{
		String message = "";
		//数据库异常
		if (e instanceof SQLException)
		{
			message = "数据库异常";
		} else if (e instanceof NoLoginException)
		{
			message = "未登录异常";
		} else if (e instanceof EmailExistException)
		{
			message = "邮箱已存在, 请返回重新注册或者登录";
		}
		else if (e instanceof NoCartException)
		{
			message = "购物车为空, 请先添加商品到购物车";
		}
		else if (e instanceof IsClearCartException)
		{
			message = "购物车已清空, 请先添加商品到购物车";
		}
		else
		{//未知异常
			System.out.println(e);
			message =  "未知异常";
		}
		model.addAttribute("mymessage",message);
		return "myError";
	}
}