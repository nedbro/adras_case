package com.topdesk.cases.langdetect;

import java.util.Map;

/**
 * models a language by storing character bigram probabilities from the training text data 
 */
public class CharBigramModel implements Model
{

  private static final long serialVersionUID = 1L;

  /**
   * TODO robi: private vagy transient mert a Map nem extendenli a Serializable interfacet (ezt amúgy a sonarlint dobta fel)
   *
   *
   * egyébként szerintem a változó névben sokat nem ad hozzá az, hogy Log, szerintem elég lenne annyi itt a változónévnek,
   * hogy bigramProbabilities
   */
  protected transient Map<String, Double> bigramLogProbs;
  protected String name;

  /**
   * TODO robi: szerintem ha már itt van ez a komment akkor lehetne az a változó név: probabilityOfUnseenCharBigrams
   * kicsit hosszú de szerintem így tényleg olvashatóbb lenne
   */
  //probability of unseen character bigrams
  protected double smooth;

  /**
   * TODO robi: smoothnak más név ugyan úgy és a bigramLogProbs-nak is
   */
  public CharBigramModel(String name, Map<String,Double> bigramLogProbs,double smooth)
  {
    this.bigramLogProbs = bigramLogProbs;
    this.name = name;
    this.smooth = smooth;
  }
  
  @Override
  public String getName()
  {
    return name;
  }

  /**
   * TODO robi: itt végül is a logProbability-t adjuk vissza az adott bigramnak, szóval lehetne az a neve, hogy
   * getProbabilityOfBigram?
   */
  protected double scoreBigram(String bigram)
  {
    Double score = bigramLogProbs.get(bigram);
    if (score == null) return smooth;
    else return score;
  }

  /**
   * TODO robi: getScore? vagy calculateScore?
   */
  @Override
  public double score(String text)
  {
    if (text.length() <= 1) return smooth;
    double score = 0;
    for (int i = 1; i < text.length()-1; i++)
    {
      score += scoreBigram(text.substring(i, i+2));
    }
    return score;
  }

}
