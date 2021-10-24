package com.example.covid.presenters;

import com.andrognito.patternlockview.PatternLockView;
import com.andrognito.patternlockview.listener.PatternLockViewListener;
import com.example.covid.R;
import com.example.covid.interfaces.IPatronDesbloqueo;
import com.example.covid.models.PatronDesbloqueModel;
import com.example.covid.views.PatronDesbloqueoView;

import java.util.List;

import io.paperdb.Paper;

public class PatronDesbloquePresenter implements IPatronDesbloqueo.Presenter {
    private IPatronDesbloqueo.View view;
    private IPatronDesbloqueo.Model model;

    PatternLockView mPatterLockView;

    public  PatronDesbloquePresenter(IPatronDesbloqueo.View view){
        this.view = view;
        model = (IPatronDesbloqueo.Model) new PatronDesbloqueModel(this);
    }

    @Override
    public void inicializarPaper(IPatronDesbloqueo.View view) {
        model.inicializarPaper(this.view);
    }

    @Override
    public String leerPaper() {
        return model.leerPaper();
    }

    @Override
    public void escribirPaper(String partonFin) {
        model.escribirPaper(partonFin);
    }

}
