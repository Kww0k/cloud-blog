package com.cxcacm.user.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.cxcacm.user.controller.Dto.RegisterDto;
import com.cxcacm.user.entity.ResponseResult;
import com.cxcacm.user.entity.User;
import com.cxcacm.user.mapper.UserMapper;
import com.cxcacm.user.service.VerifyService;
import com.cxcacm.user.utils.BeanCopyUtils;
import com.cxcacm.user.utils.RedisCache;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.util.Objects;
import java.util.Random;
import java.util.concurrent.TimeUnit;
import java.util.regex.Pattern;

import static com.cxcacm.user.constants.UserConstants.VERIFY;
import static com.cxcacm.user.enums.AppHttpCodeEnum.*;

@Service
public class VerifyServiceImpl implements VerifyService {

    private final UserMapper userMapper;
    private final JavaMailSender javaMailSender;
    private final RedisCache redisCache;
    @Autowired
    public VerifyServiceImpl(UserMapper userMapper, JavaMailSender javaMailSender, RedisCache redisCache) {
        this.userMapper = userMapper;
        this.javaMailSender = javaMailSender;
        this.redisCache = redisCache;
    }

    @Value("${spring.mail.username}")
    String from;

    @Override
    public ResponseResult getVerify(String email) {
        Object cacheObject = redisCache.getCacheObject(VERIFY + email);
        if (cacheObject != null)
            return ResponseResult.errorResult(HAVA_CODE);
        if (!Pattern.matches("^(\\w+([-.][A-Za-z0-9]+)*){3,18}@\\w+([-.][A-Za-z0-9]+)*\\.\\w+([-.][A-Za-z0-9]+)*$", email))
            return ResponseResult.errorResult(EMAIL_FALSE);
        LambdaQueryWrapper<User> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(User::getEmail, email);
        User userByEmail = userMapper.selectOne(wrapper);
        if (userByEmail != null)
            return ResponseResult.errorResult(HAVE_BEEN_REGISTER);
        SimpleMailMessage message = new SimpleMailMessage();
        message.setSubject("【猪猪论坛】");
        Random random = new Random();
        int code = random.nextInt(899999) + 100000;
        redisCache.setCacheObject(VERIFY + email, String.valueOf(code), 5, TimeUnit.MINUTES);
        message.setText("尊敬的用户：\n" +
                "\n" +
                "感谢您选择注册猪猪论坛，我们非常欢迎您的加入！\n" +
                "\n" +
                "猪猪论坛是一个开放、友善的社区，我们欢迎任何人的到来。在这里，您可以与来自世界各地的用户分享自己的见解、经验和知识，结交志同道合的朋友。\n" +
                "\n" +
                "在进行注册之前，我们需要您验证您的电子邮箱以确认您的身份。请在下方输入验证码，以完成注册：\n" +
                "\n" +
                "验证码：" + code + "(有效期为5分钟)" + "\n" +
                "\n" +
                "我们非常重视您的隐私和安全，因此，我们承诺在您注册完成之后，不会非法地、滥用地使用您的个人信息。同时，我们也会为您提供一个安全、稳定的环境，确保您的账户能够得到有效的保护。\n" +
                "\n" +
                "如果您有任何问题或疑虑，欢迎随时联系我们的客服团队。同时，如果您愿意，我们也邀请您参加我们的社区，分享您的故事、提出建议、参加活动和竞赛，并与其他用户交流。\n" +
                "\n" +
                "再次感谢您的注册，我们期待着您的到来！\n" +
                "\n" +
                "祝您愉快！\n" +
                "\n" +
                "猪猪论坛团队");
        message.setTo(email);
        message.setFrom(from);
        javaMailSender.send(message);
        return ResponseResult.okResult();
    }

    @Override
    public ResponseResult doRegister(RegisterDto registerDto) {
        if (registerDto.getUsername() == null || registerDto.getPassword() == null ||
                registerDto.getCode() == null || registerDto.getConfirmPassword() == null ||
                registerDto.getEmail() == null)
            return ResponseResult.errorResult(MISSING_PARAM);
        if (!Objects.equals(registerDto.getConfirmPassword(), registerDto.getPassword()))
            return ResponseResult.errorResult(PASSWORD_DIFFERENT);
        String code = redisCache.getCacheObject(VERIFY + registerDto.getEmail());
        if (!Objects.equals(code, registerDto.getCode()))
            return ResponseResult.errorResult(CODE_ERROR);
        User user = BeanCopyUtils.copyBean(registerDto, User.class);
        userMapper.insert(user);
        return ResponseResult.okResult();
    }
}
