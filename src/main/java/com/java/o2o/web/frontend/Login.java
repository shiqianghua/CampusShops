package com.java.o2o.web.frontend;


import com.java.o2o.entity.User;
import com.java.o2o.enums.Code;
import com.java.o2o.service.UserService;
import com.java.o2o.util.HttpServletRequestUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.Map;

@Controller
@RequestMapping("/frontend")
public class Login {

    @Autowired
    UserService userService;

    @RequestMapping(value = "/islogin")
    @ResponseBody
    public Map login(HttpServletRequest request)
    {
        Map<String,Object> modelMap=new HashMap<String, Object>();
//        从前端获取parentId
        String phone = HttpServletRequestUtil.getString(request, "phone");
        String password= HttpServletRequestUtil.getString(request, "password");

        User user = new User();
        user.setPhone(phone);
        user.setPassword(password);

        User user2 = userService.selectUser_2(user);

        if (user2==null)
        {
            Integer code = Code.ERROR.getCode();
            modelMap.put("code",code);
            modelMap.put("isLogin",false);
        }else
        {
            Integer code = Code.SUCCESS.getCode();
            modelMap.put("code",code);
            modelMap.put("isLogin",true);
        }

        return modelMap;
    }


//    @RequestMapping(value = "/islogin")
//    @ResponseBody
//    public Map isLogin(HttpServletRequest request)
//    {
//        Map modelMap=new HashMap<>();
//        HttpSession session = request.getSession();
//        if (session.getId()!=null)
//        {
//            Integer code = Code.SUCCESS.getCode();
//            boolean islogin = Code.SUCCESS.isLogin();
//            modelMap.put("code",code);
//            modelMap.put("isLogin",islogin);
//        }
//        else
//        {
//            Integer code = Code.ERROR.getCode();
//            boolean islogin = Code.ERROR.isLogin();
//            modelMap.put("code",code);
//            modelMap.put("isLogin",islogin);
//        }
//
//        return modelMap;
//    }
}
