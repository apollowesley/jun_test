package tom.kit.json.annotations;


import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({java.lang.annotation.ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface JSON
{
  public abstract String name();

  public abstract boolean serialize();

  public abstract boolean deserialize();

  public abstract String format();
}

