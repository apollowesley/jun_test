# Online player score ranking {#concept_pw5_dsn_tdb .concept}

## Scenario introduction {#section_bhs_nyv_wdb .section}

Compared with Redis, the features of ApsaraDB for Redis are similar. You can use ApsaraDB for Redis to create a ranking list for an online game.

**Sample code**

``` {#codeblock_45x_wuz_zxw}
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.UUID;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.Tuple;
public class GameRankSample {
    static int TOTAL_SIZE = 20;
    public static void main(String[] args) 
    {
        //Connection information. This information can be obtained from the console
        String host = "xxxxxxxxxx.m.cnhz1.kvstore.aliyuncs.com";
        int port = 6379;
        Jedis jedis = new Jedis(host, port);
        try {
            //Instance password
            String authString = jedis.auth("password");//password
            if (! authString.equals("OK"))
            {
                System.err.println("AUTH Failed: " + authString);
                return;
            }
            //Key
            String key = "Game name: Keep Running, Ali!" ; ;
            //Clears any existing data
            jedis.del(key);
            //Generates several simulated players
            List<String> playerList = new ArrayList<String>();
            for (int i = 0; i < TOTAL_SIZE; ++i)&nbsp;
            {
                //Randomly generates an ID for each player
                playerList.add(UUID.randomUUID().toString());
            }
            System.out.println("Inputs all players ");
            //Records the score for each player
            for (int i = 0; i < playerList.size(); i++)&nbsp;
            {
                //Randomly generates numbers as the scores of each simulated player
                int score = (int)(Math.random()*5000);&nbsp;
                String member = playerList.get(i);
                System.out.println("Player ID:" + member + ", Player Score: " + score);
                //Adds the player ID and score to SortedSet of the corresponding key
                jedis.zadd(key, score, member);
            }
            //Prints out the ranking list of all players
            System. out. println ();
            System.out.println(" &nbsp; &nbsp; &nbsp; "+key);
            System.out.println(" Ranking list of all players");
            //Obtains the sorted list of players from SortedSet of the corresponding key
            Set<Tuple> scoreList = jedis.zrevrangeWithScores(key, 0, -1);
            for (Tuple item : scoreList) {  
                System.out.println("Player ID:"+item.getElement()+", Player Score:"+Double.valueOf(item.getScore()).intValue());
            }  
            //Prints out the top five players
            System. out. println ();
            System.out.println(" &nbsp; &nbsp; &nbsp; "+key);
            System.out.println(" &nbsp; &nbsp; &nbsp; Top players");
            scoreList = jedis.zrevrangeWithScores(key, 0, 4);
            for (Tuple item : scoreList) {  
                System.out.println("Player ID:"+item.getElement()+", Player Score:"+Double.valueOf(item.getScore()).intValue());
            }
            //Prints out a list of specific players
            System. out. println ();
            System.out.println(" &nbsp; &nbsp; &nbsp; &nbsp; "+key);
            System.out.println(" Players with scores from 1,000 to 2,000");
            //Obtains a list of players with scores from 1,000 to 2,000 from SortedSet of the corresponding key
            scoreList = jedis.zrangeByScoreWithScores(key, 1000, 2000);
            for (Tuple item : scoreList) {  
                System.out.println("Player ID："+item.getElement()+", Player Score:"+Double.valueOf(item.getScore()).intValue());&nbsp;
            } 
        } catch (Exception e) {
            e.printStackTrace();
        }finally{
            jedis.quit();
            jedis.close();
        }
    }
}
```

## Results {#section_j3s_nyv_wdb .section}

After you access the ApsaraDB for Redis instance with the correct address and password and run the Java code, the following output is displayed:

``` {#codeblock_npa_q7s_7xk}
Input all players 
Player ID: 9193e26f-6a71-4c76-8666-eaf8ee97ac86. Score: 3,860
Player ID: db03520b-75a3-48e5-850a-071722ff7afb. Score: 4,853
Player ID: d302d24d-d380-4e15-a4d6-84f71313f27a. Score: 2,931
Player ID: bee46f9d-4b05-425e-8451-8aa6d48858e6. Score: 1,796
Player ID: ec24fb9e-366e-4b89-a0d5-0be151a8cad0. Score: 2,263
Player ID: e11ecc2c-cd51-4339-8412-c711142ca7aa. Score: 1,848
Player ID: 4c396f67-da7c-4b99-a783-25919d52d756. Score: 958
Player ID: a6299dd2-4f38-4528-bb5a-aa2d48a9f94a. Score: 2,428
Player ID: 2e4ec631-1e4e-4ef0-914f-7bf1745f7d65. Score: 4,478
Player ID: 24235a85-85b9-476e-8b96-39f294f57aa7. Score: 1,655
Player ID: e3e8e1fa-6aac-4a0c-af80-4c4a1e126cd1. Score: 4,064
Player ID: 99bc5b4f-e32a-4295-bc3a-0324887bb77e.  Score: 4,852
Player ID: 19e2aa6b-a2d8-4e56-bdf7-8b59f64bd8e0. Score: 3,394
Player ID: cb62bb24-1318-4af2-9d9b-fbff7280dbec. Score: 3,405
Player ID: ec0f06da-91ee-447b-b935-7ca935dc7968. Score: 4,391
Player ID: 2c814a6f-3706-4280-9085-5fe5fd56b71c. Score: 2,510
Player ID: 9ee2ed6d-08b8-4e7f-b52c-9adfe1e32dda. Score: 63
Player ID: 0293b43a-1554-4157-a95b-b78de9edf6dd. Score: 1,008
Player ID: 674bbdd1-2023-46ae-bbe6-dfcd8e372430. Score: 2,265
Player ID: 34574e3e-9cc5-43ed-ba15-9f5405312692. Score: 3,734
              Game name: Keep Running, Ali!                     
              Ranking list of all players                    
Player ID: db03520b-75a3-48e5-850a-071722ff7afb. Score: 4,853
Player ID: 99bc5b4f-e32a-4295-bc3a-0324887bb77e. Score: 4,852
Player ID: 2e4ec631-1e4e-4ef0-914f-7bf1745f7d65. Score: 4,478
Player ID: ec0f06da-91ee-447b-b935-7ca935dc7968. Score: 4,391
Player ID: e3e8e1fa-6aac-4a0c-af80-4c4a1e126cd1. Score: 4,064
Player ID: 9193e26f-6a71-4c76-8666-eaf8ee97ac86. Score: 3,860
Player ID: 34574e3e-9cc5-43ed-ba15-9f5405312692. Score: 3,734
Player ID: cb62bb24-1318-4af2-9d9b-fbff7280dbec. Score: 3,405
Player ID: 19e2aa6b-a2d8-4e56-bdf7-8b59f64bd8e0. Score: 3,394
Player ID: d302d24d-d380-4e15-a4d6-84f71313f27a. Score: 2,931
Player ID: 2c814a6f-3706-4280-9085-5fe5fd56b71c. Score: 2,510
Player ID: a6299dd2-4f38-4528-bb5a-aa2d48a9f94a. Score: 2,428
Player ID: 674bbdd1-2023-46ae-bbe6-dfcd8e372430. Score: 2,265
Player ID: ec24fb9e-366e-4b89-a0d5-0be151a8cad0. Score: 2,263
Player ID: e11ecc2c-cd51-4339-8412-c711142ca7aa. Score: 1,848
Player ID: bee46f9d-4b05-425e-8451-8aa6d48858e6. Score: 1,796
Player ID: 24235a85-85b9-476e-8b96-39f294f57aa7. Score: 1,655
Player ID: 0293b43a-1554-4157-a95b-b78de9edf6dd. Score: 1,008
Player ID: 4c396f67-da7c-4b99-a783-25919d52d756. Score: 958
Player ID:9ee2ed6d-08b8-4e7f-b52c-9adfe1e32dda. Score:63
      Game name: Keep Running, Ali!                     
         Top players                    
Player ID: db03520b-75a3-48e5-850a-071722ff7afb. Score: 4,853
Player ID: 99bc5b4f-e32a-4295-bc3a-0324887bb77e. Score: 4,852
Player ID: 2e4ec631-1e4e-4ef0-914f-7bf1745f7d65. Score: 4,478
Player ID: ec0f06da-91ee-447b-b935-7ca935dc7968. Score: 4,391
Player ID: e3e8e1fa-6aac-4a0c-af80-4c4a1e126cd1. Score: 4,064
          Game name: Keep Running, Ali!                     
          Players scored between 1,000 and 2,000              
Player ID: 0293b43a-1554-4157-a95b-b78de9edf6dd. Score: 1,008
Player ID: 24235a85-85b9-476e-8b96-39f294f57aa7. Score:1,655
Player ID: bee46f9d-4b05-425e-8451-8aa6d48858e6. Score: 1,796
Player ID: e11ecc2c-cd51-4339-8412-c711142ca7aa. Score: 1,848
```

