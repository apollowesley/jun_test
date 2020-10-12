package javacommon.datamodifier;

/**
 * 
 * @author badqiu
 *
 */
public class DataModifierSyntaxException extends DataModifierException
{
    
    private static final long serialVersionUID = -3699920609515888588L;
    
    public DataModifierSyntaxException()
    {
        super();
    }
    
    public DataModifierSyntaxException(String msg, Throwable e)
    {
        super(msg, e);
    }
    
    public DataModifierSyntaxException(String msg)
    {
        super(msg);
    }
    
    public DataModifierSyntaxException(Throwable e)
    {
        super(e);
    }
    
}
