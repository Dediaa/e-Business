package com.ch.ebusiness;
import org.dromara.x.file.storage.spring.EnableFileStorage;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;


@EnableFileStorage
@SpringBootApplication
//配置扫描MyBatis接口的包路径
@MapperScan(basePackages={"com.ch.ebusiness.repository"})
public class EBusinessApplication {
	public static void main(String[] args)
	{
		SpringApplication.run(EBusinessApplication.class, args);

	}
}
