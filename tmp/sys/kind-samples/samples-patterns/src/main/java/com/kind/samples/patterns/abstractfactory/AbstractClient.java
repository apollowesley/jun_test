package com.kind.samples.patterns.abstractfactory;

import com.kind.samples.patterns.abstractfactory.impl.BusinessCarFactory;
import com.kind.samples.patterns.abstractfactory.impl.SportCarFactory;

/**
 * Created by weiguo.liu on 2016/10/14.
 */
public class AbstractClient {
    private AbstractFactory factory;

    public void setFactory(AbstractFactory factory) {
        this.factory = factory;
    }

    public void testClient() {
        if (this.factory == null) {
            System.out.println("no factory available!");
            return;
        }

        BmwCarFactory bmw = factory.createBmwCar();
        System.out.println(bmw.createBmwCar());
        BenzCarFactory benz = factory.createBenzCar();
        System.out.println(benz.createBenzCar());
    }


    public static void main(String[] args) {
        AbstractClient abstractClient = new AbstractClient();

        System.out.println("Ready to connect sport car factory********");
        abstractClient.setFactory(new SportCarFactory());
        abstractClient.testClient();
        System.out.println("End of testing sport car factory**********");

        System.out.println("Ready to connect business car factory*****");
        abstractClient.setFactory(new BusinessCarFactory());
        abstractClient.testClient();
        System.out.println("End of testing business car factory*******");
    }

}
