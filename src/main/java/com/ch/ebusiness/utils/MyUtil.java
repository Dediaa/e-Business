package com.ch.ebusiness.utils;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.servlet.http.HttpSession;

import com.ch.ebusiness.entity.dto.UserDto;

public class MyUtil {
	/**
	 * 将实际的文件名重命名
	 */
	public static String getNewFileName(String oldFileName) {
		int lastIndex = oldFileName.lastIndexOf(".");
		String fileType = oldFileName.substring(lastIndex);
		Date now = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("YYYYMMDDHHmmssSSS");
		String time = sdf.format(now);
		String newFileName = time + fileType;
		return newFileName;
	}
	/**
	 * 获得用户信息
	 */
	public static UserDto getUser(HttpSession session) {
		return SecurityUtils.getUser(session);
	}
}
