package pl.rspective.survey.dagger;

import dagger.ObjectGraph;

public class Injector {

    private static ObjectGraph objectGraph;

    private Injector(){}

    public static void inject(Object o) {
        objectGraph.inject(o);
    }

    public static void initDaggerGraph(Object[] module) {
        objectGraph = ObjectGraph.create(module);
    }
}
