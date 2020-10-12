# -*- coding: utf-8 -*-
from __future__ import unicode_literals

from django.db import models

# Create your models here.
# 创建数据库表结构
class Grade(models.Model):
    #gid = models.AutoField(primary_key=True)
    name = models.CharField(max_length=20, blank=False, null=False, unique=True)

class Class(models.Model):
    #cid = models.AutoField(primary_key=True)
    grade = models.ForeignKey(Grade)
    name = models.CharField(max_length=50, blank=False, null=False, unique=True)
    def __unicode__(self):
        return self

class Student(models.Model):
    #sid = models.AutoField(primary_key=True)
    name = models.CharField(max_length=200)
    age = models.IntegerField(default=0)
    email = models.EmailField()
    class_name = models.ForeignKey('Class')
    def __unicode__(self):
        return self