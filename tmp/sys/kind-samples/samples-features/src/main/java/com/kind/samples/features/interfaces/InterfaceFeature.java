package com.kind.samples.features.interfaces;

/**
 * 接口可以有默认方法实现，必须default修饰
 * Created by weiguo.liu on 2016/10/18.
 */
public class InterfaceFeature {

    interface Formula {
        double calculate(int a);

        default double sqrt(int a) {
            return Math.sqrt(a);
        }

    }


    class ForumlaImpl implements Formula {
        @Override
        public double calculate(int a) {
            return a;
        }

        @Override
        public double sqrt(int a) {
            return a;
        }

    }

    public static void main(String[] args) {
        Formula formula = new InterfaceFeature().new ForumlaImpl();
        formula.calculate(100);
        formula.sqrt(1);
    }
}