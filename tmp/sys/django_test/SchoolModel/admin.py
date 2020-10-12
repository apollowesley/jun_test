# -*- coding: utf-8 -*-
from __future__ import unicode_literals

from django.contrib import admin
from SchoolModel.models import Grade, Student, Class


class Gradeinline(admin.TabularInline):
    model = Grade

class GradeAdmin(admin.ModelAdmin):
    list_display = ('id', 'name')  # list
    fields = ('gid','name')

class Classinline(admin.TabularInline):
    model = Class

class ClassAdmin(admin.ModelAdmin):
    list_display = ('name', 'grade')  # list
    fields = ('name', 'grade')

class StudentAdmin(admin.ModelAdmin):
    # 显示3个字段: 'name', 'age', 'email'
    list_display = ('name', 'age', 'email')  # list
    # 设置分组表单, 一共分了两组，
    fieldsets = (
        ['Main', {
            'fields': ('name', 'email'),
        }],
        ['Advance', {
            'classes': ('collapse',),
            'fields': ('age',),
        }]
    )

# Register your models here.
admin.site.register(Student, StudentAdmin)
admin.site.register([Grade, Class])
