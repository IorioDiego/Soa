package com.example.covid.models;

import android.content.Context;

import com.example.covid.interfaces.IPatronDesbloqueo;
import com.example.covid.presenters.PatronDesbloquePresenter;

import io.paperdb.Paper;

public class PatronDesbloqueModel implements  IPatronDesbloqueo.Model {

    String patronBase = "patron";
    //String patronFinal = "";

    private IPatronDesbloqueo.Presenter presenter ;
    public PatronDesbloqueModel(IPatronDesbloqueo.Presenter presenter){
        this.presenter = presenter;
    }

    @Override
    public void inicializarPaper(IPatronDesbloqueo.View view) {
        Paper.init((Context) view);
    }

    @Override
    public String leerPaper() {
       return Paper.book().read(patronBase);
    }

    @Override
    public void escribirPaper(String partonFin) {
        Paper.book().write(patronBase,partonFin);
    }
}
