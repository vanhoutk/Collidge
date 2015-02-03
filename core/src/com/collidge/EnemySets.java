package com.collidge;

import java.util.HashMap;

/**
 * Created by Daniel on 30/01/2015.
 */
public class EnemySets
{
    private HashMap<String, String[]> EnemyCollections = new HashMap();
    private HashMap<String,int[]> EnemyPopulation=new HashMap();


    EnemySets()
    {
        EnemyCollections.put("Empty", fillArrays("","","",""));
        EnemyPopulation.put("Empty",fillArrays(0,0,0,0));
        EnemyCollections.put("Loner", fillArrays("Fresher","","",""));
        EnemyPopulation.put("Loner",fillArrays(1,0,0,0));
        EnemyCollections.put("Pack", fillArrays("Fresher","Fresher","",""));
        EnemyPopulation.put("Pack",fillArrays(2,2,0,0));
        EnemyCollections.put("Preppy", fillArrays("Fresher","Master Debater","",""));
        EnemyPopulation.put("Preppy",fillArrays(1,1,0,0));
        EnemyCollections.put("Pet",fillArrays("Fresher","Lecturer","",""));
        EnemyPopulation.put("Pet",fillArrays(2,1,0,0));
        EnemyCollections.put("Pledge",fillArrays("Fresher","Frat boy","",""));
        EnemyPopulation.put("Pledge",fillArrays(2,1,0,0));
        EnemyCollections.put("Full Set",fillArrays("Fresher","Master Debater","Lecturer","Frat boy"));



    }

    private String[] fillArrays(String a, String b,String c,String d)
    {
        String[] temp=new String[4];
        temp[0]=a;
        temp[1]=b;
        temp[2]=c;
        temp[3]=d;
        return temp;

    }

    private int[] fillArrays(int i, int j, int k, int l)
    {
        int[] temp=new int[4];
        temp[0]=i;
        temp[1]=j;
        temp[2]=k;
        temp[3]=l;
        return temp;
    }


}
