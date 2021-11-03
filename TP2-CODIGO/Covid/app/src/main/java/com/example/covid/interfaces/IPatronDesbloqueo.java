package com.example.covid.interfaces;

public interface IPatronDesbloqueo {
    interface View {


    }

    interface Presenter {
        void inicializarPaper(IPatronDesbloqueo.View view);

        String leerPaper();

        void escribirPaper(String partonFin);

    }

    interface Model {
        void inicializarPaper(IPatronDesbloqueo.View view);

        String leerPaper();

        void escribirPaper(String partonFin);
    }

}
