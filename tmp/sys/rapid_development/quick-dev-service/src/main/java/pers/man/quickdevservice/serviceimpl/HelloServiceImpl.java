package pers.man.quickdevservice.serviceimpl;

import org.springframework.stereotype.Service;
import pers.man.quickdevservice.service.HelloService;

/**
 * @author MAN
 * @version 1.0
 * @date 2020-04-03 13:34
 * @project quick-dev
 */
@Service
public class HelloServiceImpl implements HelloService {
    @Override
    public void test() {
        System.out.println("Test AOP");
    }
}
