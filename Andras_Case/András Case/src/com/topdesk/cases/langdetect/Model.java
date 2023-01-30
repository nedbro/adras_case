package com.topdesk.cases.langdetect;

import java.io.Serializable;

/**
 * TODO robi: szerintem itt a komment nem ad érdemben semmit hozzá az interfacehez
 * ha már model of a language akkor miért nem LanguageModel a neve?
 */

/**
 * the model of a language
 */
public interface Model extends Serializable {
    String getName();

    /**
     * TODO robi: getScore? vagy calculateScore?
     */
    double score(String text);
}
