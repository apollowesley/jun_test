/*
 *  Copyright 2010 The MyBatis Team
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */
package com.roncoo.mybatis.generator.codegen.mybatis3.javamapper.elements.sqlprovider;

import static com.roncoo.mybatis.generator.codegen.mybatis3.MyBatis3FormattingUtilities.getAliasedEscapedColumnName;
import static com.roncoo.mybatis.generator.codegen.mybatis3.MyBatis3FormattingUtilities.getParameterClause;
import static com.roncoo.mybatis.generator.internal.util.StringUtility.escapeStringForJava;

import java.util.List;
import java.util.Set;
import java.util.TreeSet;

import com.roncoo.mybatis.generator.api.IntrospectedColumn;
import com.roncoo.mybatis.generator.api.dom.java.FullyQualifiedJavaType;
import com.roncoo.mybatis.generator.api.dom.java.JavaVisibility;
import com.roncoo.mybatis.generator.api.dom.java.Method;
import com.roncoo.mybatis.generator.api.dom.java.Parameter;
import com.roncoo.mybatis.generator.api.dom.java.TopLevelClass;

/**
 * 
 * @author Jeff Butler
 * 
 */
public class ProviderUpdateByExampleWithoutBLOBsMethodGenerator extends
        AbstractJavaProviderMethodGenerator {

    public ProviderUpdateByExampleWithoutBLOBsMethodGenerator() {
        super();
    }

    @Override
    public void addClassElements(TopLevelClass topLevelClass) {
        Set<String> staticImports = new TreeSet<String>();
        Set<FullyQualifiedJavaType> importedTypes = new TreeSet<FullyQualifiedJavaType>();

        staticImports.add("org.apache.ibatis.jdbc.SqlBuilder.BEGIN"); //$NON-NLS-1$
        staticImports.add("org.apache.ibatis.jdbc.SqlBuilder.UPDATE"); //$NON-NLS-1$
        staticImports.add("org.apache.ibatis.jdbc.SqlBuilder.SET"); //$NON-NLS-1$
        staticImports.add("org.apache.ibatis.jdbc.SqlBuilder.SQL"); //$NON-NLS-1$

        importedTypes.add(new FullyQualifiedJavaType("java.util.Map")); //$NON-NLS-1$
        
        Method method = new Method(getMethodName());
        method.setReturnType(FullyQualifiedJavaType.getStringInstance());
        method.setVisibility(JavaVisibility.PUBLIC);
        method.addParameter(new Parameter(new FullyQualifiedJavaType("java.util.Map<java.lang.String, java.lang.Object>"), //$NON-NLS-1$
                "parameter")); //$NON-NLS-1$
        
        context.getCommentGenerator().addGeneralMethodComment(method,
                introspectedTable);

        method.addBodyLine("BEGIN();"); //$NON-NLS-1$
        
        method.addBodyLine(String.format("UPDATE(\"%s\");", //$NON-NLS-1$
                escapeStringForJava(introspectedTable.getAliasedFullyQualifiedTableNameAtRuntime())));
        method.addBodyLine(""); //$NON-NLS-1$
        
        for (IntrospectedColumn introspectedColumn : getColumns()) {
            StringBuilder sb = new StringBuilder();
            sb.append(getParameterClause(introspectedColumn));
            sb.insert(2, "record."); //$NON-NLS-1$
            
            method.addBodyLine(String.format("SET(\"%s = %s\");", //$NON-NLS-1$
                    escapeStringForJava(getAliasedEscapedColumnName(introspectedColumn)),
                    sb.toString()));
        }
        
        method.addBodyLine(""); //$NON-NLS-1$
        
        FullyQualifiedJavaType example =
            new FullyQualifiedJavaType(introspectedTable.getExampleType());
        importedTypes.add(example);
        method.addBodyLine(String.format("%s example = (%s) parameter.get(\"example\");", //$NON-NLS-1$
                example.getShortName(), example.getShortName()));
        
        method.addBodyLine("applyWhere(example, true);"); //$NON-NLS-1$
        method.addBodyLine("return SQL();"); //$NON-NLS-1$
        
        if (callPlugins(method, topLevelClass)) {
            topLevelClass.addStaticImports(staticImports);
            topLevelClass.addImportedTypes(importedTypes);
            topLevelClass.addMethod(method);
        }
    }
    
    public String getMethodName() {
        return introspectedTable.getUpdateByExampleStatementId();        
    }
    
    public List<IntrospectedColumn> getColumns() {
        return introspectedTable.getNonBLOBColumns();
    }
    
    public boolean callPlugins(Method method, TopLevelClass topLevelClass) {
        return context.getPlugins().providerUpdateByExampleWithoutBLOBsMethodGenerated(method, topLevelClass,
                introspectedTable);
    }
}
