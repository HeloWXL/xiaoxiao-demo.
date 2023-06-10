package xx.location.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import xx.location.util.AddressUtil;
import xx.location.util.IPUtil;

import javax.servlet.http.HttpServletRequest;

@RestController
public class HaloController {

    @GetMapping("hello")
    public String hello(HttpServletRequest request){
        // 获取请求访问地址
        String ip = IPUtil.getIpAddr(request);
        // 返回请求城市信息
        return AddressUtil.getCityInfo(ip);
    }
}
