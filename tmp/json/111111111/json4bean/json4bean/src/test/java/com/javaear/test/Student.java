/*
 * Copyright 2016-2016 Javaear Group Holding Ltd.
 */
package com.javaear.test;

import java.util.List;

/**
 * @author aooer
 */
public class Student {
    private Long id;
    private String name;
    private Firend firend;
    private List<Subjects> subjects;

    public static class Firend {
        private String fid;
        private String fname;

        /**
         * @return fid
         */
        public String getFid() {
            return this.fid;
        }

        /**
         * @param fid fid
         */
        public void setFid(String fid) {
            this.fid = fid;
        }

        /**
         * @return fname
         */
        public String getFname() {
            return this.fname;
        }

        /**
         * @param fname fname
         */
        public void setFname(String fname) {
            this.fname = fname;
        }
    }

    public static class Subjects {
        private String sid;
        private String sname;

        /**
         * @return sid
         */
        public String getSid() {
            return this.sid;
        }

        /**
         * @param sid sid
         */
        public void setSid(String sid) {
            this.sid = sid;
        }

        /**
         * @return sname
         */
        public String getSname() {
            return this.sname;
        }

        /**
         * @param sname sname
         */
        public void setSname(String sname) {
            this.sname = sname;
        }
    }

    /**
     * @return id
     */
    public Long getId() {
        return this.id;
    }

    /**
     * @param id id
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * @return name
     */
    public String getName() {
        return this.name;
    }

    /**
     * @param name name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * @return firend
     */
    public Firend getFirend() {
        return this.firend;
    }

    /**
     * @param firend firend
     */
    public void setFirend(Firend firend) {
        this.firend = firend;
    }

    /**
     * @return subjects
     */
    public List<Subjects> getSubjects() {
        return this.subjects;
    }

    /**
     * @param subjects subjects
     */
    public void setSubjects(List<Subjects> subjects) {
        this.subjects = subjects;
    }
}