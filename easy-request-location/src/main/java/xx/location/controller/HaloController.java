package xx.location.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import xx.location.aop.Ip;
import xx.location.util.AddressUtil;
import xx.location.util.IPUtil;

import javax.servlet.http.HttpServletRequest;

@RestController
public class HaloController {

    @GetMapping("hello")
    @Ip
    public String hello(){
        return "success";
    }

    @GetMapping("hello2")
    public String hello2(){
        return "success";
    }


    @GetMapping("hello3")
    public String hello3(HttpServletRequest request){
        String ip = IPUtil.getIpAddr(request);
        System.out.println(AddressUtil.getCityInfo(ip));
        return "success";
    }
}
