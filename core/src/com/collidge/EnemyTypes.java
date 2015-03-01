package com.collidge;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Daniel on 30/01/2015.
 */
public class EnemyTypes
{


    private HashMap<String, Enemy> EnemyCatalog = new HashMap();


    EnemyTypes()
    {
        Enemy temp;
        //TODO rebalance and add more enemies (maybe add enemyMoves/ enemyDescriptions)
        //temp = new Enemy(String name, int health, int attack, int defence,int experianceForKilled);
        temp=new Enemy("Fresher",10,5,0,5);
        EnemyCatalog.put(temp.getName(), temp);
        temp=new Enemy("Master Debater",30,10,0,10);
        EnemyCatalog.put(temp.getName(), temp);
        temp=new Enemy("Lecturer",80,15,4,25);
        EnemyCatalog.put(temp.getName(), temp);
        temp=new Enemy("Frat boy",5,15,0,10);
        EnemyCatalog.put(temp.getName(), temp);
        temp=new Enemy("\"Musician\"",50,5,5,20);
        EnemyCatalog.put(temp.getName(), temp);


    }

    public Enemy getEnemy(String nomme)
    {
        for(Map.Entry<String, Enemy> entry: EnemyCatalog.entrySet())
        {
            if(entry.getValue().getName().equals(nomme))
            {
                return entry.getValue();
            }
        }
        System.out.println("No enemy found?");
        return (new Enemy("Fresher",10,2,1,5));
    }


}
