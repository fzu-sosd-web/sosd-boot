package org.sosd;

import cn.dev33.satoken.stp.StpUtil;
import cn.hutool.core.util.RandomUtil;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.sosd.client.domain.User;
import org.sosd.client.service.UserService;
import org.sosd.api.IdCardAuth;
import org.sosd.core.constant.Captcha;
import org.sosd.oss.util.AliyunOSSUtil;
import org.sosd.oss.util.FileService;
import org.sosd.redis.util.RedisUtils;
import org.sosd.sms.util.AliyunSmsUtil;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.Duration;
import java.util.HashMap;

import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest(classes = AdminApplication.class)
@ComponentScan()
class AdminApplicationTests {
    @Autowired
    private  UserService userService;
    @Autowired
    private AliyunSmsUtil AliyunSmsUtil;
    @Autowired
    private FileService fileService;
    @Test
    void idcardauth() {
        String cardno = "350427200408161034";
        String realname = "严文骏";
        String responseString = IdCardAuth.AuthIdCard(cardno, realname);
        // 使用Fastjson解析JSON响应
        JSONObject jsonObj = JSON.parseObject(responseString);

        // 提取 'isok' 和 'birthday' 字段
        boolean isOk = jsonObj.getJSONObject("result").getBoolean("isok");
        String birthday = jsonObj.getJSONObject("result").getJSONObject("IdCardInfor").getString("birthday");

        // 可以根据需要返回这些值，打印它们或进行进一步处理
        System.out.println("Is OK: " + isOk);
        System.out.println("Birthday: " + birthday);
        System.out.println(responseString);
    }

    @Test
    void smsTest() throws Exception {
        String phonenumber = "18750851691";
        String key = Captcha.CAPTCHA_CODE_KEY + phonenumber;
        String code = RandomUtil.randomNumbers(4);

        RedisUtils.setCacheObject(key, code, Duration.ofMinutes(Captcha.CAPTCHA_EXPIRATION));
        HashMap<String, Object> map = new HashMap<>();
        map.put("code", code);
        boolean b = AliyunSmsUtil.sendMsg(phonenumber, map);
        if (b){
            System.out.println(code);
        }
        else System.out.println("失败");
    }

    @Test
    void sendSms() throws Exception {
        String phonenumber = "18750851691";
        String code = RandomUtil.randomNumbers(4);
        HashMap<String, Object> map = new HashMap<>();
        map.put("code",code);
        AliyunSmsUtil.sendMsg(phonenumber,map);
    }
    @Test
    void login(){
        String username = "test";
        String password = "123456";
        QueryWrapper<User> qw = new QueryWrapper<>();
        qw.eq("user_name",username);
        qw.eq("password",password);
        boolean exists = userService.exists(qw);
        if (exists){
            Long id = userService.getOne(qw).getId();
            StpUtil.login(id);
            String tokenValueByLoginId = StpUtil.getTokenValueByLoginId(id);
            System.out.println(tokenValueByLoginId);
        }

        // 检查用户名是否存在
        boolean usernameExists = userService.count(new QueryWrapper<User>().eq("user_name", username)) > 0;
        if (!usernameExists) {
            System.out.println("账户错误用户名不存在");
        }
        System.out.println("密码错误提供的密码不正确");
    }

    @Test
    void testOss() throws IOException {
        // 读取文件作为InputStream
        String filePath = "D:\\SOSD\\TimeBankSystem\\admin\\src\\main\\resources\\static\\test.txt";
        InputStream inputStream = Files.newInputStream(Paths.get(filePath));

        // 创建MockMultipartFile对象
        MultipartFile multipartFile = new MockMultipartFile(
                "file", // 文件参数名称
                "test.txt", // 文件名
                "text/plain", // 文件类型
                inputStream);

        // 调用上传方法
        String fileKey = AliyunOSSUtil.uploadFile(multipartFile);

        // 断言返回的文件key不为空
        assertNotNull(fileKey);
    }

}
