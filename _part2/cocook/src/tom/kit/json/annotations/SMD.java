package tom.kit.json.annotations;


import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({java.lang.annotation.ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface SMD
{
  public abstract String version();

  public abstract String objectName();

  public abstract String serviceType();
}

