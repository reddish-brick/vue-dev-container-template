/**
 * 严肃声明： 开源版本请务必保留此注释头信息，若删除我方将保留所有法律责任追究！ 本系统已申请软件著作权，受国家版权局知识产权以及国家计算机软件著作权保护！ 可正常分享和学习源码，不得用于违法犯罪活动，违者必究！ Copyright (c) 2019-2021 十三 all
 * rights reserved. 版权所有，侵权必究！
 */
package com.example.mall.api.admin;

import com.example.mall.api.admin.param.AdminLoginParam;
import com.example.mall.api.admin.param.UpdateAdminNameParam;
import com.example.mall.api.admin.param.UpdateAdminPasswordParam;
import com.example.mall.common.ServiceResultEnum;
import com.example.mall.config.annotation.TokenToAdminUser;
import com.example.mall.entity.AdminUser;
import com.example.mall.entity.AdminUserToken;
import com.example.mall.service.AdminUserService;
import com.example.mall.util.Result;
import com.example.mall.util.ResultGenerator;
import io.swagger.annotations.Api;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.Valid;

/**
 * @author 13
 * @qq交流群 796794009
 * @email 2449207463@qq.com
 * @link https://github.com/newbee-ltd
 */
@RestController
@Api(value = "v1", tags = "8-0.后台管理系统管理员模块接口")
@RequestMapping("/manage-api/v1")
public class NewBeeAdminManageUserAPI {

  @Resource
  private AdminUserService adminUserService;

  private static final Logger logger = LoggerFactory.getLogger(NewBeeAdminManageUserAPI.class);

  @RequestMapping(value = "/adminUser/login", method = RequestMethod.POST)
  public Result<String> login(@RequestBody @Valid AdminLoginParam adminLoginParam) {
    String loginResult = adminUserService.login(adminLoginParam.getUserName(), adminLoginParam.getPasswordMd5());
    logger.info("manage login api,adminName={},loginResult={}", adminLoginParam.getUserName(), loginResult);

    //登录成功
    if (loginResult != ServiceResultEnum.LOGIN_ERROR.getResult()) {
      Result result = ResultGenerator.genSuccessResult();
      result.setData(loginResult);
      return result;
    }
    //登录失败
    return ResultGenerator.genFailResult(loginResult);
  }

  @RequestMapping(value = "/adminUser/profile/{adminUserId}", method = RequestMethod.GET)
  public Result profile(@PathVariable Long adminUserId) {
    logger.info("adminUser:{}", adminUserId);
    AdminUser adminUserEntity = adminUserService.getUserDetailById(adminUserId);
    if (adminUserEntity != null) {
      adminUserEntity.setLoginPassword("******");
      Result result = ResultGenerator.genSuccessResult();
      result.setData(adminUserEntity);
      return result;
    }
    return ResultGenerator.genFailResult(ServiceResultEnum.DATA_NOT_EXIST.getResult());
  }

  @RequestMapping(value = "/adminUser/{adminUserId}/password", method = RequestMethod.PUT)
  public Result passwordUpdate(@RequestBody @Valid UpdateAdminPasswordParam adminPasswordParam, @PathVariable Long adminUserId) {
    logger.info("adminUser:{}", adminUserId);
    if (adminUserService.updatePassword(adminUserId, adminPasswordParam.getOriginalPassword(),
        adminPasswordParam.getNewPassword())) {
      return ResultGenerator.genSuccessResult();
    } else {
      return ResultGenerator.genFailResult(ServiceResultEnum.DB_ERROR.getResult());
    }
  }

  @RequestMapping(value = "/adminUser/{adminUserId}/name", method = RequestMethod.PUT)
  public Result nameUpdate(@RequestBody @Valid UpdateAdminNameParam adminNameParam, @PathVariable Long adminUserId) {
    logger.info("adminUser:{}", adminUserId);
    if (adminUserService.updateName(adminUserId, adminNameParam.getLoginUserName(), adminNameParam.getNickName())) {
      return ResultGenerator.genSuccessResult();
    } else {
      return ResultGenerator.genFailResult(ServiceResultEnum.DB_ERROR.getResult());
    }
  }

  @RequestMapping(value = "/logout", method = RequestMethod.DELETE)
  public Result logout(@TokenToAdminUser AdminUserToken adminUser) {
    logger.info("adminUser:{}", adminUser.toString());
    adminUserService.logout(adminUser.getAdminUserId());
    return ResultGenerator.genSuccessResult();
  }

}