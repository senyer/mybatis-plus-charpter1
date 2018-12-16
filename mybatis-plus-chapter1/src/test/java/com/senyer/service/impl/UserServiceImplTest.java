package com.senyer.service.impl;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.senyer.entity.User;
import com.senyer.mapper.UserMapper;
import com.senyer.service.UserService;

import lombok.extern.slf4j.Slf4j;


@RunWith(SpringRunner.class)
@SpringBootTest
@Slf4j
public class UserServiceImplTest {
	@Autowired
	private UserService userService;
	@Autowired
	private UserMapper userMapper;

	@Test//添加用户
	public void testSave() {
		User user=new User();
		user.setAge(18);
		user.setEmail("hakjshdskja@qq.com");
		user.setName("香香ii");
		userService.save(user);
		//尽量使用{}占位符
		log.info("插入成功 :{}+++++++++++++++++++++++",user.getName());
	}

	@Test//批量添加用户
	public void testSaveBatch() {
		List<User> list=new ArrayList<>();
		list.add(new User(1,"tom",18,"sadas@qq.com"));
		list.add(new User(24,"tom2",17,"sadas@qq.com"));
		list.add(new User(42,"tom2",17,"sadas@qq.com"));
		boolean saveBatch = userService.saveBatch(list);
		log.info("批量添加 :{}+++++++++++++++++++++++",saveBatch);
	}

	@Test//插入或者修改
	public void testSaveOrUpdate() {
		boolean saveOrUpdate = userService.saveOrUpdate(new User(177,"tom",18,"sadas@qq.com"));
		log.info("插入或者修改 :{}+++++++++++++++++++++++",saveOrUpdate);
		
	}

	@Test//批量插入或者修改
	public void testSaveOrUpdateBatch() {
		List<User> list=new ArrayList<>();
		list.add(new User(1,"tomxx",18,"sadas@qq.com"));
		list.add(new User(24,"tomXX",17,"sadas@qq.com"));
		list.add(new User(42,"tomXX",17,"sadas@qq.com"));
		boolean saveOrUpdate = userService.saveOrUpdateBatch(list);
		log.info("批量插入或者修改 :{}+++++++++++++++++++++++",saveOrUpdate);
		/** SQL语句执行如下：
		 *  Time：20 ms - ID：com.senyer.mapper.UserMapper.selectById
			Execute SQL：SELECT id,name,age,email FROM sys_user WHERE id=1
			
			 Time：0 ms - ID：com.senyer.mapper.UserMapper.updateById
			Execute SQL：UPDATE sys_user SET name='tomxx', age=18, email='sadas@qq.com' WHERE id=1
			
			 Time：0 ms - ID：com.senyer.mapper.UserMapper.selectById
			Execute SQL：SELECT id,name,age,email FROM sys_user WHERE id=24
			
			 Time：0 ms - ID：com.senyer.mapper.UserMapper.updateById
			Execute SQL：UPDATE sys_user SET name='tomXX', age=17, email='sadas@qq.com' WHERE id=24
			
			 Time：2 ms - ID：com.senyer.mapper.UserMapper.selectById
			Execute SQL：SELECT id,name,age,email FROM sys_user WHERE id=42
			
			 Time：0 ms - ID：com.senyer.mapper.UserMapper.insert
			Execute SQL：INSERT INTO sys_user ( name, age, email ) VALUES ( 'tomXX', 17, 'sadas@qq.com' )
		 */
	}

	@Test//根据id删除
	public void testRemoveById() {
		boolean removeById = userService.removeById(29);
		log.info("根据id删除 :{}+++++++++++++++++++++++",removeById);
	}

	@Test//根据条件删除：        	#####################前面都是根据id删除的，这个可以不按照id来进行删除！！很有用的语句。######################
	public void testRemoveByMap() {
	     //删除条件
	    Map<String, Object> columnMap1 = new HashMap<>();
	    columnMap1.put("id", 28);
		boolean removeById1 = userService.removeByMap(columnMap1);
		
		
		 Map<String, Object> columnMap2 = new HashMap<>();
		 columnMap2.put("name", "香香3");
		 columnMap2.put("id", 18);
		 boolean removeById2 = userService.removeByMap(columnMap2);
		 //Execute SQL：DELETE FROM sys_user WHERE name = '香香3' AND id = 18
		log.info("根据map删除 :{}+++++++++++++++++++++++",removeById2);
	}

	@Test//利用wrapper实现删除
	public void testRemove() {
		//3.0+版本将EntityWrapper改为了QueryWrapper
       boolean remove = userService.remove(new QueryWrapper<User>().eq("id",1));
       log.info("利用wrapper实现删除 :{}+++++++++++++++++++++++",remove);
       
	}

	@Test//根据id批量删除：
	public void testRemoveByIds() {
		List<Integer> idList = new ArrayList<>();
		 idList.add(16);
		 idList.add(17);
		 boolean removeByIds = userService.removeByIds(idList);
		 log.info("根据id批量删除： :{}+++++++++++++++++++++++",removeByIds);
	}

	@Test//根据id更新
	public void testUpdateById() {
		
		boolean updateById = userService.updateById(new User(24,"tomXX",17,"xxxxxxx@qq.com"));
		log.info("根据id更新（传对象）： :{}+++++++++++++++++++++++",updateById);
	}

	@Test //更新--需要new一个UpdateWrapper对象，来加上where后面的条件。
	public void testUpdate() {
		boolean update = userService.update(new User(24,"tomXX",17,"xxxxxxx@qq.com"), new UpdateWrapper<User>().eq("id", 11).eq("age", 17));
		//Execute SQL：UPDATE sys_user SET name='tomXX', age=17, email='xxxxxxx@qq.com' WHERE id = 11
		//Execute SQL：UPDATE sys_user SET name='tomXX', age=17, email='xxxxxxx@qq.com' WHERE id = 11 AND age = 17
		log.info("更新 :{}+++++++++++++++++++++++",update);
	}

	@Test//根据id批量更新
	public void testUpdateBatchById() {
		List<User> list=new ArrayList<>();
		list.add(new User(1,"tomxx",18,"sadas@qq.com"));
		list.add(new User(24,"tomXX",17,"sadas@qq.com"));
		list.add(new User(42,"tomXX",17,"sadas@qq.com"));
		boolean updateBatchById = userService.updateBatchById(list);
		log.info("根据id批量更新（传对象）： :{}+++++++++++++++++++++++",updateBatchById);
	}

	@Test//根据id查询
	public void testGetById() {
		User user = userService.getById(2);
		log.info("查询成功：{}+++++++++++++++++++++++",user.getName());
	}

	@Test//根据ids批量查询
	public void testListByIds() {
		List<Integer> asList = Arrays.asList(1,2,3,4,5);
		Collection<User> listByIds = userService.listByIds(asList);
		log.info("查询成功：{}+++++++++++++++++++++++",listByIds.toString());
		//Execute SQL：SELECT id,name,age,email FROM sys_user WHERE id IN ( 1 , 2 , 3 , 4 , 5 )
	}

	@Test//条件查询
	public void testListByMap() {
		 Map<String, Object> columnMap2 = new HashMap<>();
		 columnMap2.put("name", "tomXX");
		 columnMap2.put("age", 17);
		 Collection<User> listByMap = userService.listByMap(columnMap2);
		 //Execute SQL：SELECT id,name,age,email FROM sys_user WHERE name = 'tomXX' AND age = 17
		 log.info("查询成功：{}+++++++++++++++++++++++",listByMap.toString());
	}

	@Test//按条件获取一个记录
	public void testGetOne() {
		User one = userService.getOne(new QueryWrapper<User>().eq("id", 17));
		log.info("查询成功：{}+++++++++++++++++++++++",one.toString());
		//Execute SQL：SELECT id,name,age,email FROM sys_user WHERE id = 2
	}

	@Test//按条件查询Map集合
	public void testGetMap() {
		Map<String, Object> map = userService.getMap(new QueryWrapper<User>().eq("id", 17));
		log.info("查询成功：{}+++++++++++++++++++++++",map.get("name"));
		//Execute SQL：SELECT id,name,age,email FROM sys_user WHERE id = 17
	}

	@Test//按条件查询记录数
	public void testCount() {
		int count = userService.count();
		int count2 = userService.count(new QueryWrapper<User>().eq("age", 17));
		log.info("查询成功：{}+++++++++++++++++++++++",count2);
	}

	@Test//按条件查询集合
	public void testList() {
		List<User> list = userService.list(new QueryWrapper<User>().eq("age", 17));
		log.info("查询成功：{}+++++++++++++++++++++++",list.toString());
	}

	@Test//分页查询
	public void testPage() {
		
        
		IPage<User> page = userService.page(new Page<User>(1,3));
		log.info("查询成功：{}+++++++++++++++++++++++",page.getTotal());
		log.info("查询成功：{}+++++++++++++++++++++++",page.getRecords().size());
		log.info("查询成功：{}+++++++++++++++++++++++",page.getRecords());
		//Execute SQL：SELECT id,name,age,email FROM sys_user LIMIT 0,3
	}

	@Test//获取map集合
	public void testListMaps() {
		List<Map<String, Object>> listMaps = userService.listMaps(new QueryWrapper<User>().eq("age", 17));
		log.info("查询成功：{}+++++++++++++++++++++++",listMaps.toString());
	}

	@Test//一般也用不到了，没必要试
	public void testListObjs() {
		fail("Not yet implemented");
	}

	@Test//略....
	public void testPageMaps() {
		fail("Not yet implemented");
	}

}
