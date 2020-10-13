package name.liushiyao.annotation;

/***********注解使用***************/

public class Apple {

        @MyAnnotation.FruitName ( "Apple" )
        private String appleName;

        @MyAnnotation.FruitColor ( fruitColor = MyAnnotation.FruitColor.Color.RED )
        private String appleColor;

        @MyAnnotation.FruitProvider ( id = 1, name = "陕西红富士集团", address = "陕西省西安市延安路89号红富士大厦" )
        private String appleProvider;

        public void setAppleColor ( String appleColor ) {
                this.appleColor = appleColor;
        }

        public String getAppleColor ( ) {
                return appleColor;
        }

        public void setAppleName ( String appleName ) {
                this.appleName = appleName;
        }

        public String getAppleName ( ) {
                return appleName;
        }

        public void setAppleProvider ( String appleProvider ) {
                this.appleProvider = appleProvider;
        }

        public String getAppleProvider ( ) {
                return appleProvider;
        }

        public void displayName ( ) {
                System.out.println ( "水果的名字是：苹果" );
        }
}