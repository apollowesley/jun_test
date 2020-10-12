package com.coldfeng.webservice;

import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.context.support.SpringBeanAutowiringSupport;

import com.coldfeng.model.Customer;
import com.coldfeng.service.HelloWorldService;

@Produces({MediaType.APPLICATION_JSON,MediaType.APPLICATION_XML})
@Path("/Hello")
public class HellWorldWebService extends SpringBeanAutowiringSupport {

    private Logger logger=LoggerFactory.getLogger(getClass()); 
    @Autowired
    HelloWorldService helloWorldService;

    @POST
    @Path("/getCustomer")
    public Customer getCustomer(Customer customer) {
        logger.info("=====customer=======");
        Customer customer2 = new Customer();
        BeanUtils.copyProperties(customer, customer2);
        logger.info("customer",customer2);
        return customer2;
    }
}
