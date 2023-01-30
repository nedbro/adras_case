package com.topdesk.cases.langdetect;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

public class CharBigramBuilder implements ModelBuilder
{

  /**
   * TODO robi: kicsit fura, hogy build de amúgy trainre utal a komment,
   * szerintem "(i.e. count character pairs)" rész nem vészes szerintem, mert nem annyira egyértelmű első olvasásra a kód,
   * és abban tényleg segítene, hogy összefoglalja, hogy mit is csinál
   *
   *
   * egyébként szerintem ez lehetne egy static function ha csak igy használjuk mert semmi változója nincs, csak ugye ez az
   * interfaceből van
   */
  /**
   * train a char bigram model on the given text (i.e. count character pairs)
   */
  @Override
  public Model build(String modelName, String text)
  {
    if (text.length() < 3) throw new IllegalArgumentException("text too short");


    Map<String,Integer> bigramCounts = new HashMap<String, Integer>();
    /**
     * TODO robi: probs nem egyértelmű első olvasásra, hogy probabilities, szóval szerintem lehetne az a teljes változónév
     */
    Map<String, Double> bigramLogProbs = new HashMap<String, Double>();
    
    for (int i = 1; i < text.length()-1; i++)
    {
      String bigram = text.substring(i, i+2);
      Integer count = bigramCounts.get(bigram);
      if (count == null) count = 0;
      bigramCounts.put(bigram,count+1);
    }

    /**
     * TODO robi: logOfLength?
     */
    double loglength = Math.log(text.length()-1);

    /**
     * TODO robi: stream().map() az entry seten? akkor végül is csak a valuet kellene változatni
     */
    for (Entry<String, Integer> e : bigramCounts.entrySet())
    {
      bigramLogProbs.put(e.getKey(), Math.log(e.getValue()) - loglength);
    }

    /**
     * TODO robi: Math.log(0.5) - loglength nagyon magic szám szerintem, kellene egy változó ami
     * meghatározza, hogy mit is jelöl
     */
    return new CharBigramModel(modelName,bigramLogProbs, Math.log(0.5) - loglength);
  }

}
