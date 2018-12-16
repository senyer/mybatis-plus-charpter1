package com.senyer.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.senyer.entity.User;
import com.senyer.mapper.UserMapper;
import com.senyer.service.UserService;

import lombok.extern.slf4j.Slf4j;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author Senyer
 * @since 2018-12-16
 */
@RestController
@RequestMapping("/user")
@Slf4j
public class UserController {
	@Autowired
	private UserService userService;
	@Autowired
	private UserMapper userMapper;
	
	@GetMapping("")
	public String selectById() {
		//User user = userService.getById(1);
		User user = userMapper.selectById(1);
		log.info("查询成功：{}+++++++++++++++++++++++"+user.getName());
		return "success";
	}
	
	@GetMapping("/page")
	public IPage<User>  selectPage(@RequestParam(value="pageSize",defaultValue="10")String pageSize,
									@RequestParam(value="pageNum",defaultValue="2")String pageNum) {
		Page<User> page = new Page<>(Integer.valueOf(pageNum),Integer.valueOf(pageSize));// 当前页，总条数 构造 page 对象
		IPage<User> page2 = userService.page(page);
		log.info("查询成功：{}+++++++++++++++++++++++",page2);
		return page2;
	}
	

}

