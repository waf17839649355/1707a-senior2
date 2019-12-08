package com.wangafei.test;

import java.util.Date;
import java.util.Random;

import javax.annotation.Resource;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.data.redis.core.BoundHashOperations;
import org.springframework.data.redis.core.BoundSetOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.wangafei.bean.User;
import com.wangafei.utils.TimeRandomUtil;
import com.wangafei.utils.UserRandomUtil;

import redis.clients.jedis.Jedis;

@SuppressWarnings("all")
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations="classpath:spring-context.xml")
public class UserTest {
	
	@Resource
	private RedisTemplate redisTemplate;
	
	@Test
	public void userAdd() {
		
		//ValueOperations opsValue = redisTemplate.opsForValue();
		
		long time1 = System.currentTimeMillis();
		BoundHashOperations ops_hash = redisTemplate.boundHashOps("user_hash");
		
		for(int i=1;i<=100000;i++) {
			User user = new User();
			
			user.setId(i);
			
			user.setName(UserRandomUtil.getChineseName());
			
			user.setSex(getSex());
			
			user.setPhone(getPhone());
			
			user.setEmail(UserRandomUtil.getEmail());
			
			user.setBirthday(TimeRandomUtil.randomDate("1949-01-01 00:00:00","2001-01-01 00:00:00"));
			
			
			//opsValue.set(i+"", user);
			ops_hash.put(i+"", user.toString());
			
		}
		
		long time2 = System.currentTimeMillis();
		
		long time=time2-time1;
		
		System.out.println("格式为:hash,"+"时间为:"+time);
		
		
	}
	public String getSex() {
		return new Random().nextBoolean()?"男":"女";
	}
	public String getPhone() {
		String phone="";
		for(int i=0;i<9;i++) {
			phone += new Random().nextInt(10);
		}
		return "13"+phone;
	}
}
