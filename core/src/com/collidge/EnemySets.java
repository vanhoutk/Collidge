package com.collidge;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Random;
import java.util.Set;

/**
 * Created by Daniel on 30/01/2015.
 */
public class EnemySets
{
    private HashMap<String, String[]> EnemyCollections = new HashMap();
    private HashMap<String,int[]> EnemyPopulation=new HashMap();

    private EnemyTypes Types=new EnemyTypes();
    Random rand=new Random();

    //allows for up to 4 different types of enemy per fight, and assigns the maximum number of each type of enemy
    EnemySets()
    {
        EnemyCollections.put("Empty", fillArrays("","","",""));     //for example this enemy set is named empty and has blank enemies
        EnemyPopulation.put("Empty",fillArrays(0,0,0,0));       //will always have 0 enemies
        EnemyCollections.put("Loner", fillArrays("Fresher","","",""));
        EnemyPopulation.put("Loner",fillArrays(1,0,0,0));
        EnemyCollections.put("Pack", fillArrays("Fresher","Fresher","Fresher",""));
        EnemyPopulation.put("Pack",fillArrays(3,3,1,0));
        EnemyCollections.put("Preppy", fillArrays("Fresher","Debater","",""));
        EnemyPopulation.put("Preppy",fillArrays(1,1,0,0));
        EnemyCollections.put("Pet",fillArrays("Fresher","Lecturer","",""));
        EnemyPopulation.put("Pet",fillArrays(2,1,0,0));
        EnemyCollections.put("Pledge",fillArrays("Fresher","Frat boy","",""));
        EnemyPopulation.put("Pledge",fillArrays(5,1,0,0));
        EnemyCollections.put("Full Set",fillArrays("Fresher","Debater","Lecturer","Frat boy"));
        EnemyPopulation.put("Full Set",fillArrays(1,1,1,1));

        //NB MAX OF 10 ENEMIES PLEASE



    }

    //puts the names of the enemies in an array
    private String[] fillArrays(String a, String b,String c,String d)
    {
        String[] temp=new String[4];
        temp[0]=a;
        temp[1]=b;
        temp[2]=c;
        temp[3]=d;
        return temp;

    }

    //puts the quantities of enemies in an array
    private int[] fillArrays(int i, int j, int k, int l)
    {
        int[] temp=new int[4];
        temp[0]=i;
        temp[1]=j;
        temp[2]=k;
        temp[3]=l;
        return temp;
    }

    //creates the array of enemies based on the specifications from the chosen EnemyCollection and EnemyPopulation (these must have the same name)
    public Enemy[] getEnemies(String name)
    {
        int temp=0;
        String[] Enemyset=EnemyCollections.get(name);
        int[] pop=EnemyPopulation.get(name);
        Set<Enemy> Enemies = new HashSet();     //creates the hashset Enemies

        //handles up to 4 enemies within the Enemyset. If the enemy at Enemyset[i] isn't blank, it is added to the array.
        for(int i=0;i<4;i++)
        {
            if(!Enemyset[i].equals(""))
            {
                temp=(rand.nextInt(pop[i])) + 1;   //temp will be a random number in the range from 1 to pop[i] - where pop[i] is the number of the specific type of enemy passed in
                //temp = pop[i];  //no randomness, for testing

                for(int j=0;j<temp;j++)
                {
                    Enemies.add(new Enemy(Types.getEnemy(Enemyset[i])));        //adds "temp" number of enemies (so a random no. less than the max) of the type specified in Enemyset[i]
                }
            }
        }

        return Enemies.toArray(new Enemy[Enemies.size()]);
    }


}