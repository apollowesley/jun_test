package com.itmuch.gen.entity;


public class ConfigDetail {
    private String targetPackage;
    private String targetProject;

    public String getTargetPackage() {
        return this.targetPackage;
    }

    public String getTargetProject() {
        return this.targetProject;
    }

    public void setTargetPackage(String targetPackage) {
        this.targetPackage = targetPackage;
    }

    public void setTargetProject(String targetProject) {
        this.targetProject = targetProject;
    }

    @Override
    public String toString() {
        return "ConfigDetail [targetPackage=" + this.targetPackage + ", targetProject=" + this.targetProject + "]";
    }
}
