package javacommon.datamodifier;

/**
 * 
 * @author badqiu
 *
 */
public class DataModifierException extends RuntimeException
{
    
    private static final long serialVersionUID = 3144095128890934966L;
    
    public DataModifierException()
    {
        super();
    }
    
    public DataModifierException(String msg, Throwable e)
    {
        super(msg, e);
    }
    
    public DataModifierException(String msg)
    {
        super(msg);
    }
    
    public DataModifierException(Throwable e)
    {
        super(e);
    }
    
}
