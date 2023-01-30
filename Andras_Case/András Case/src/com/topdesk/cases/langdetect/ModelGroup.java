package com.topdesk.cases.langdetect;

public interface ModelGroup
{
  void add(Model model);

  /**
   * TODO robi: itt a komment nem kell és akkor lehetne a function neve az, hogy
   * selectModelFromText / selectModelUsingText / selectModelBasedOnTest
   */

  /**
   * selects the model of the language the text is written in
   */
  Model select(String text);
}
