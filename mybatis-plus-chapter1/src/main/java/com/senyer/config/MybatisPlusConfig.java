package com.senyer.config;

import java.util.Properties;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import com.baomidou.mybatisplus.extension.plugins.PaginationInterceptor;
import com.baomidou.mybatisplus.extension.plugins.PerformanceInterceptor;

/**
 * 
 * @author kk
 * MybatisPlus配置类
 */
//@EnableTransactionManagement
@Configuration
@MapperScan("com.senyer.mapper")
public class MybatisPlusConfig {

	
	/**
	 * 分页
	 * @return
	 */
	@Bean
	public PaginationInterceptor paginationInterceptor(){
		return new PaginationInterceptor();
	}
   	
	
	/**
     * SQL执行效率插件,很好用！值得推荐，可以在控制台查看执行的sql时间以及执行的语句！！！！！！！
     */
    @Bean
    public PerformanceInterceptor performanceInterceptor() {
        return new PerformanceInterceptor();
    }
}

