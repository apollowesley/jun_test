/**
 * Copyright 2017 KiWiPeach
 * <p>
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * <p>
 * http://www.apache.org/licenses/LICENSE-2.0
 */
package cn.kiwipeach.design.create.prototype;

import java.io.*;
import java.util.Date;

/**
 * Create Date: 2017/11/15 
 * Description: 学生类
 * @author kiwipeach [1099501218@qq.com]
 */
public class Student implements Cloneable,Serializable{
    private String id;
    private String name;
    private Date birthday;
    private Deptment deptment;

    public Student(String id, String name, Date birthday) {
        this.id = id;
        this.name = name;
        this.birthday = birthday;
    }

    public Deptment getDeptment() {
        return deptment;
    }

    public void setDeptment(Deptment deptment) {
        this.deptment = deptment;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Date getBirthday() {
        return birthday;
    }

    public void setBirthday(Date birthday) {
        this.birthday = birthday;
    }

    /**
     * 方法一:每一个子类都需要克隆方法，并且调用super.clone()；
     * 克隆的方法名不做要求，重要的是super.clone()调用native方法进行克隆
     * @return
     * @throws CloneNotSupportedException
     */
//    public Object deepClone() throws CloneNotSupportedException {
//        Student student = (Student) super.clone();
//        student.setBirthday((Date) birthday.clone());
//        student.setDeptment((Deptment) deptment.deepClone());
//        return student;
//    }

    /**
     * 方法二：克隆的对象只需要实现序列化即可
     * 这种方法要求对象序列化
     * @return
     * @throws IOException
     * @throws ClassNotFoundException
     */
    public Object deepClone() throws IOException, ClassNotFoundException {
        //把当前对象this封装成输出流ByteArrayOutputStream
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        ObjectOutputStream objectOutputStream = new ObjectOutputStream(bos);
        objectOutputStream.writeObject(this);
        //把封装的ByteArrayOutputStream在此封装成对象输入流ObjectInputStream
        ByteArrayInputStream boi = new ByteArrayInputStream(bos.toByteArray());
        ObjectInputStream objectInputStream = new ObjectInputStream(boi);
        return objectInputStream.readObject();
    }
}
