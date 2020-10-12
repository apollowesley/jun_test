### Lambda表达式执行构造函数、成员方法和静态方法案例

- 
```java
    interface IStudent<T extends Student> {
        T generate();
    }

    interface Setter {
        void setAttribute(String name);
    }

    interface Getter {
        String getAttribute();
    }

    interface IStaticMethod {
        void staticMethodInvoke(Student student);
    }

    @Test
    public void test7() {
        //构造lambda
        IStudent<Student> iStudent = Student::new;
        Student student = iStudent.generate();
        //访问器lambda
        Setter setter = student::setName;
        Getter getter = student::getName;
        setter.setAttribute("kiwipeach");
        System.out.println("访问器:" + getter.getAttribute());
        //静态方法lambada
        IStaticMethod iStaticMethod = Student::staticMethod;
        iStaticMethod.staticMethodInvoke(student);
    }
```