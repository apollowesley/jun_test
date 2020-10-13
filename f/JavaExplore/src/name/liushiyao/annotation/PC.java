package name.liushiyao.annotation;

/**
 * Created by 北岭山下 on 2017/7/19.
 */
public class PC {

        @PCAnnotation.type(value = "台式")
        private String type;
        @PCAnnotation.cpu(cpu = PCAnnotation.cpu.type.AMD)
        private String cpu;
        @PCAnnotation.country(value = "中国" )
        private String country;

        public String getType ( ) {
                return type;
        }

        public void setType ( String type ) {
                this.type = type;
        }

        public String getCpu ( ) {
                return cpu;
        }

        public void setCpu ( String cpu ) {
                this.cpu = cpu;
        }

        public String getCountry ( ) {
                return country;
        }

        public void setCountry ( String country ) {
                this.country = country;
        }
}
