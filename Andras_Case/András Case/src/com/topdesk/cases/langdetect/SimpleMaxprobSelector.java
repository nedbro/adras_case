package com.topdesk.cases.langdetect;

import java.util.Collection;
import java.util.HashSet;

public class SimpleMaxprobSelector implements ModelGroup {

    protected Collection<Model> models;

    public SimpleMaxprobSelector() {
        models = new HashSet<Model>();
    }

    @Override
    public void add(Model model) {
        models.add(model);
    }

    /**
     * TODO robi: itt nekem zavaró az, hogy a kapcsos zárójelek új sorrendben kezdődnek (ez az egész projektre igaz,
     * csak itt jutott eszembe :D )
     */
    @Override
    public Model select(String text) {
        Model selectedModel = null;
        double maxScore = Double.NEGATIVE_INFINITY;
        for (Model model : models) {
            double score = model.score(text);
            if (score > maxScore) {
                selectedModel = model;
                maxScore = score;
            }
        }
        return selectedModel;
    }

}
