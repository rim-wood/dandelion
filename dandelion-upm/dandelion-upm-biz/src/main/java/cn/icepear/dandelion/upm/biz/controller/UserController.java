package cn.icepear.dandelion.upm.biz.controller;

import cn.icepear.dandelion.common.core.utils.R;
import cn.icepear.dandelion.upm.api.domain.vo.UserVO;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author rim-wood
 * @description
 * @date Created on 2019-05-29.
 */
@RestController
@RequestMapping("/user")
public class UserController {

    @GetMapping("/info")
    public R<UserVO> getUserInfo(){
        UserVO user = new UserVO();
        user.setMobile("123123");
        return new R<>(user);
    }

    @GetMapping("/info1")
    public R<UserVO> getUserInfo1() throws Exception {
        throw new Exception("123");
    }
}
