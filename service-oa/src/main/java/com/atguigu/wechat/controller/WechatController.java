package com.atguigu.wechat.controller;

import com.alibaba.fastjson.JSON;
import com.atguigu.auth.model.system.SysUser;
import com.atguigu.auth.service.SysUserService;
import com.atguigu.auth.vo.wechat.BindPhoneVo;
import com.atguigu.common.jwt.JWTHelper;
import com.atguigu.common.result.Result;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import me.chanjar.weixin.common.api.WxConsts;
import me.chanjar.weixin.common.bean.WxOAuth2UserInfo;
import me.chanjar.weixin.common.bean.oauth2.WxOAuth2AccessToken;
import me.chanjar.weixin.mp.api.WxMpService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.net.URLEncoder;
@Slf4j
@Controller
@RequestMapping("/admin/wechat")
@CrossOrigin
public class WechatController {
    @Resource
    private SysUserService sysUserService;
    @Autowired
    private WxMpService wxMpService;
    @Value("${wechat.userInfoUrl}")
    private String userInfoUrl;
    @GetMapping("/authorize")
    public String authorize(@RequestParam("returnUrl") String returnUrl, HttpServletRequest request) {
        //由于授权回调成功后，要返回原地址路径，原地址路径带“#”号，当前returnUrl获取带“#”的url获取不全，因此前端把“#”号替换为“guiguoa”了，这里要还原一下
        String redirectURL = wxMpService.getOAuth2Service().buildAuthorizationUrl(userInfoUrl, WxConsts.OAuth2Scope.SNSAPI_USERINFO, URLEncoder.encode(returnUrl.replace("guiguoa", "#")));
        log.info("【微信网页授权】获取code,redirectURL={}", redirectURL);
        return "redirect:" + redirectURL;
    }
    @GetMapping("/userInfo")
    public String userInfo(@RequestParam("code") String code,
                           @RequestParam("state") String returnUrl) throws Exception {
        WxOAuth2AccessToken accessToken = wxMpService.getOAuth2Service().getAccessToken(code);
     String openId = accessToken.getOpenId();
        System.out.println("openId"+openId);
        WxOAuth2UserInfo wxOAuth2UserInfo = wxMpService.getOAuth2Service().getUserInfo(accessToken, null);
         System.out.println("微信用户信息："+JSON.toJSONString(wxOAuth2UserInfo));
        LambdaQueryWrapper<SysUser> wrapper=new LambdaQueryWrapper<>();
        wrapper.eq(SysUser::getOpenId,openId);
        SysUser sysUser=sysUserService.getOne(wrapper);
        String token="";
        if (sysUser!=null){
            token= JWTHelper.creatToken(sysUser.getId(),sysUser.getUsername());
        }
        return returnUrl;
    }
    @ApiOperation(value = "微信账号绑定手机")
    @PostMapping("bindPhone")
    @ResponseBody
    public Result bindPhone(@RequestBody BindPhoneVo bindPhoneVo) {
        LambdaQueryWrapper<SysUser> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(SysUser::getPhone, bindPhoneVo.getPhone());
        SysUser sysUser = sysUserService.getOne(wrapper);

        if (sysUser != null) {
            sysUser.setOpenId(bindPhoneVo.getOpenId());
            sysUserService.updateById(sysUser);

        String token = JWTHelper.creatToken(sysUser.getId(), sysUser.getUsername());
        return Result.ok(token);
    }else{
            return Result.fail("手机号码不存在，绑定失败");
        }
    }

    }
