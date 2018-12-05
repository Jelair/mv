package com.uduck.mv;

import com.uduck.mv.entity.dto.VideoUserDTO;
import com.uduck.mv.util.ConvertVideo;
import com.uduck.mv.util.ThumbUtil;
import com.uduck.mv.util.VFileUtil;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.IOException;

@RunWith(SpringRunner.class)
@SpringBootTest
public class MvApplicationTests {

	@Test
	public void contextLoads() {
		String name = "test.jpg";
		boolean b = VFileUtil.checkFile(name);
		System.out.println("checkFile: " + b);
		String n = VFileUtil.extractName(name);
		System.out.println("extractName: " + n);
		String fmt = VFileUtil.extractFmt(name);
		System.out.println("extractFmt: " + fmt);
		int status = VFileUtil.checkContentType(name);
		System.out.println(status);
	}

	@Test
	public void videoConvert(){
		try {
			ConvertVideo.formatConversion("E:/upload/videos/9c47703bdc58436ab959099c1abbef71.flv","F:/test_ffmpeg/flv2mp4.mp4");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Test
	public void compressImage(){
		try {
			ThumbUtil.compressImageByOriginalScale("F:/test_ffmpeg/test.jpg","F:/test_ffmpeg/header.jpg",300,300);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Test
	public void thumbResize(){
		String inputStr = "E:/upload/avatars/defaultAvatar.jpeg";
		String outputStr = "E:/upload/avatars/defaultAvatar.jpeg";
		try {
			ThumbUtil.compressImageByOriginalScale(inputStr,outputStr,60,60);
		} catch (IOException e) {
			e.printStackTrace();
		}
		System.out.println("over============>");
	}

	@Autowired
	private StringRedisTemplate stringRedisTemplate;
	@Autowired
	private RedisTemplate redisTemplate;

	@Test
	public void testRedisString(){
		//stringRedisTemplate.opsForValue().set("userSize", "128");
        //String userSize = stringRedisTemplate.opsForValue().get("userSize");
        //System.out.println(userSize);
        VideoUserDTO o = new VideoUserDTO();
        o.setAvatar("http://www.baidu.com");
        o.setId(123321);
        o.setNickname("Test");
        //redisTemplate.opsForValue().set("videoUser", o);
        Object videoUser = redisTemplate.opsForValue().get("videoUser");
        System.out.println(videoUser.toString());
    }
}
