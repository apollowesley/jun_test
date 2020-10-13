package com.jfire.codejson.methodinfo.impl.write.array;

import java.lang.reflect.Method;
import com.jfire.codejson.methodinfo.MethodInfoBuilder;
import com.jfire.codejson.methodinfo.impl.write.AbstractWriteMethodInfo;
import com.jfire.codejson.strategy.WriteStrategy;
import com.jfire.codejson.util.NameTool;

public abstract class AbstractWriteArrayMethodInfo extends AbstractWriteMethodInfo
{
    protected Method method;
    
    public AbstractWriteArrayMethodInfo(Method method, WriteStrategy strategy)
    {
        super(method, strategy);
        this.method = method;
        Class<?> returnType = method.getReturnType();
        int dim = NameTool.getDimension(returnType);
        Class<?> rootType = NameTool.getRootType(returnType);
        String rootName = rootType.getName();
        str = "if(" + getValue + " != null)\n{\n";
        String key = method.getReturnType().getName() + '.' + NameTool.getNameFromMethod(method, strategy);
        if (strategy != null && strategy.containsField(key))
        {
            str += "\twriteStrategy.getWriterByField(\"" + key + "\").write(" + getValue + ",cache);\n";
            str += "\tcache.append(',');\n";
            str += "}\n";
        }
        else
        {
            str += "\t" + NameTool.buildDimTypeName(rootName, dim) + " array" + dim + " = " + getValue + ";\n";
            str += "\tcache.append(\"\\\"" + NameTool.getNameFromMethod(method, strategy) + "\\\":\");\n";
            if (strategy != null && (MethodInfoBuilder.wrapperSet.contains(rootType) ||rootType.isPrimitive()))
            {
                str += "\tJsonWriter baseWriter = writeStrategy.getWriter(" + rootType.getName() + ".class);\n";
            }
            String bk = "\t";
            String nextBk = "\t\t";
            for (int i = 0; i < dim; i++)
            {
                String now = "array" + (dim - i);
                String next = "array" + (dim - i - 1);
                if (i != 0)
                {
                    str += bk + "if(" + now + " != null)\n" + bk + "{\n";
                }
                else
                {
                    nextBk = bk;
                }
                str += nextBk + "cache.append('[');\n";
                String index = ("i" + (dim - i));
                String lengthStr = "h" + (dim - i);
                str += nextBk + "int " + lengthStr + " = " + now + ".length;\n";
                str += nextBk + "for(int " + index + "=0;" + index + "<" + lengthStr + ";" + index + "++)\n" + nextBk + "{\n";
                if (i != dim - 1)
                {
                    str += nextBk + '\t' + NameTool.buildDimTypeName(rootName, dim - i - 1) + " " + next + " = " + now + "[" + index + "];\n";
                }
                bk = nextBk + '\t';
                nextBk = bk + "\t";
            }
            writeOneDim(rootType, bk);
            bk = bk.substring(0, bk.length() - 1);
            for (int i = dim; i > 0; i--)
            {
                nextBk = bk.substring(0, bk.length() - 1);
                str += bk + "}\n";
                str += bk + "if(cache.isCommaLast()){cache.deleteLast();}\n";
                str += bk + "cache.append(\"],\");\n";
                str += nextBk + "}\n";
                if (i != 1)
                {
                    bk = nextBk.substring(0, nextBk.length() - 1);
                }
            }
        }
    }
    
    protected abstract void writeOneDim(Class<?> rootType, String bk);
    
}