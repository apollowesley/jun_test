# Correlation analysis on E-commerce store items {#concept_gcf_bzv_wdb .concept}

## Scenario introduction {#section_stl_2zv_wdb .section}

Compared with Redis, the features of ApsaraDB for Redis are similar. You can use ApsaraDB for Redis to perform a correlation analysis on E-commerce store items.

The correlation between items is the case where multiple items are added to the same shopping cart. The analysis results are crucial for the E-commerce industry and can be used to analyze shopping behaviors. For example:

-   On the details page of a specific item, recommend related items to the user who is browsing this page.
-   Recommend related items to a user who just added an item to the shopping cart.
-   Place highly correlated items together on the shelf.

You can use ApsaraDB for Redis to create a sorted set for each item. For a specific item, the set consists of items that are added with this item to the shopping cart. Members of the set are scored based on how often they appear in the same cart with that specific item. Each time item A and item B appear in the same shopping cart, the respective sorted sets for item A and item B in ApsaraDB for Redis are updated.

**Sample code**

``` {#codeblock_i7o_6ip_mah}
package shop.kvstore.aliyun.com;
import java.util.Set;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.Tuple;
    public class AliyunShoppingMall {
        public static void main(String[] args) 
        {
            //ApsaraDB for Redis connection. This information can be obtained from the console
            String host = "xxxxxxxx.m.cnhza.kvstore.aliyuncs.com";
            int port = 6379;
            Jedis jedis = new Jedis(host, port);
            try {
                //ApsaraDB for Redis instance password
                String authString = jedis.auth("password");//password
                if (! authString.equals("OK"))
                {
                    System.err.println("AUTH Failed: " + authString);
                    return;
                }
                //Products
                String key0="Alibaba Cloud: Product: Beer";
                String key1 = "Alibaba Cloud: Product: Chocolate";
                String key2 = "Alibaba Cloud: Product: Cola";
                String key3 = "Alibaba Cloud: Product: Gum";
                String key4 = "Alibaba Cloud: Product: Beef Jerky";
                String key5="Alibaba Cloud: Product: Chicken Wings";
                final String[] aliyunProducts=new String[]{key0,key1,key2,key3,key4,key5};
                //Initialize to clear the possible existing data
                for (int i = 0; i < aliyunProducts.length; i++) { 
                    jedis.del(aliyunProducts[i]);
                }
                //Simulated shopping behaviors
                for (int i = 0; i < 5; i++) { //Simulates the shopping behaviors of multiple customers
                    customersShopping(aliyunProducts,i,jedis);
                }
                System.out.println();
                //Uses ApsaraDB for Redis to generate the correlated relationship between items
                for (int i = 0; i < aliyunProducts.length; i++) { 
                    System.out.println(">>>>>>>>>>and"+aliyunProducts[i]+"was purchased with <<<<<<<<<<<<<<<");
                    Set<Tuple> relatedList = jedis.zrevrangeWithScores(aliyunProducts[i], 0, -1);
                    for (Tuple item : relatedList) {  
                        System.out.println("Item name:"+item.getElement()+", Purchased together times:"+Double.valueOf(item.getScore()).intValue());
                    }  
                    System.out.println();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }finally{
                jedis.quit();
                jedis.close();
            }
        }
        private static void customersShopping(String[] products, int i, Jedis jedis) {
            //Simulates three simple shopping behaviors and randomly selects one as the behavior of the user
            int bought=(int)(Math.random()*3);
            if(bought==1){
                //Simulated business logic: the user has purchased the following products:
                System.out.println("User"+i+"purchased"+products[0]+","+products[2]+","+products[1]);
                //Records the correlations between the items to SortSet in ApsaraDB for Redis
                jedis.zincrby(products[0], 1, products[1]);
                jedis.zincrby(products[0], 1, products[2]);
                jedis.zincrby(products[1], 1, products[0]);
                jedis.zincrby(products[1], 1, products[2]);
                jedis.zincrby(products[2], 1, products[0]);
                jedis.zincrby(products[2], 1, products[1]);
            }else if(bought==2){
                //Simulated business logic: the user has purchased the following products
                System. out. println ("user" + i + "purchased" + products [4] + ", "+ products [2] +", "+ products [3]);
                //Records the correlations between the items to SortSet in ApsaraDB for Redis
                jedis.zincrby(products[4], 1, products[2]);
                jedis.zincrby(products[4], 1, products[3]);
                jedis.zincrby(products[3], 1, products[4]);
                jedis.zincrby(products[3], 1, products[2]);
                jedis.zincrby(products[2], 1, products[4]);
                jedis.zincrby(products[2], 1, products[3]);
            }else if(bought==0){
                //Simulated business logic: the user has purchased the following products:
                System.out.println("user"+i+"purchased"+products[1]+","+products[5]);
                //Records the correlations between the items to SortSet in ApsaraDB for Redis
                jedis.zincrby(products[5], 1, products[1]);
                jedis.zincrby(products[1], 1, products[5]);
            }
        }
    }
```

## Results {#section_dvl_2zv_wdb .section}

After you access the ApsaraDB for Redis instance with the correct address and password and run the Java code, the following output is displayed:

``` {#codeblock_pn0_k5h_dwb}
User 0 purchased Alibaba Cloud: Product: Chocolate, Alibaba Cloud: Product: Chicken Wings
User 1 purchased Alibaba Cloud: Product: Beef Jerky, Alibaba Cloud: Product: Cola, Alibaba Cloud: Product: Gum
User 2 purchased Alibaba Cloud: Product: Beer, Alibaba Cloud: Product: Cola, Alibaba Cloud: product: Chocolate
User 3 purchased Alibaba Cloud: Product: Beef Jerky, Alibaba Cloud: Product: Cola, Alibaba Cloud: Product: Gum
User 4 purchased Alibaba Cloud: Product: Chocolate, Alibaba Cloud: Product: Chicken Wings
>>>>>>>>>>Alibaba Cloud: Product: Beer was purchased with<<<<<<<<<<<<<<<
Item Name: Alibaba Cloud: Product: Chocolate. Purchased together times: 1
Item name: Alibaba Cloud: Product:Cola. Purchased together times: 1
>>>>>>>>>>Alibaba Cloud: Product: Chocolate was purchased with<<<<<<<<<<<<<<<<<<<<
Item name: Alibaba Cloud: Product: Chicken Wings. Purchased together times: 2
Item name: Alibaba Cloud: Product: Beer. Purchased together times: 1
Item name: Alibaba Cloud: Product: Cola. Purchased together times: 1
>>>>>>>>>>Alibaba Cloud: Product: Cola was purchased with<<<<<<<<<<<<<<<<<
Item name: Alibaba Cloud:Product: Beef Jerky. Purchased together times: 2
Item name: Alibaba Cloud: Product: Gum. Purchased together times: 2
Item name: Alibaba Cloud: Product: Chocolate. Purchased together times: 1
Item name: Alibaba Cloud: Product: Beer. Purchased together times: 1
>>>>>>>>>>Alibaba Cloud: Product: Gum was purchased with<<<<<<<<<<<<<<<<<<<<
Item name: Alibaba Cloud: Product: Beef Jerky. Purchased together times: 2
Item name: Alibaba Cloud: Product: Cola. Purchased together times: 2
>>>>>>>>>>Alibaba Cloud: Product: Beef Jerky was purchased with<<<<<<<<<<<<<<<
Item name: Alibaba Cloud: Product: Cola. Purchased together times: 2
Item name: Alibaba Cloud: Product: Gum. Purchased together times: 2
>>>>>>>>>>Alibaba Cloud: Product: Chicken Wings was purchased with<<<<<<<<<<<<<<
Item name: Alibaba Cloud: Product: Chocolate. Purchased together times: 2
```

