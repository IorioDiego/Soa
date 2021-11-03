package com.example.covid.views;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import com.andrognito.patternlockview.PatternLockView;
import com.andrognito.patternlockview.listener.PatternLockViewListener;
import com.andrognito.patternlockview.utils.PatternLockUtils;
import com.example.covid.R;
import com.example.covid.UserRegister;
import com.example.covid.interfaces.IPatronDesbloqueo;
import com.example.covid.presenters.PatronDesbloquePresenter;

import java.util.List;

import io.paperdb.Paper;

public class ConfirmarPatronView extends AppCompatActivity implements IPatronDesbloqueo.View {

    PatternLockView mPatterLockView;
    private static final String PATRON_CORRECTO = "Patron CORRECTO", PATRON_INCORRECTO = "Patron INCORRECTO";
    String patronFinal = "";
    IPatronDesbloqueo.Presenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_confirmar_patron);
        presenter = new PatronDesbloquePresenter((IPatronDesbloqueo.View) this);
        presenter.inicializarPaper((IPatronDesbloqueo.View) ConfirmarPatronView.this);
        final String patronGuardado = presenter.leerPaper();
        if ((patronGuardado != null) && !patronGuardado.equals("null")) {

            setContentView(R.layout.activity_confirmar_patron);
            mPatterLockView = (PatternLockView) findViewById(R.id.pattern_lock_view);
            mPatterLockView.addPatternLockListener(new PatternLockViewListener() {
                @Override
                public void onStarted() {

                }

                @Override
                public void onProgress(List<PatternLockView.Dot> progressPattern) {

                }

                @Override
                public void onComplete(List<PatternLockView.Dot> pattern) {
                    patronFinal = PatternLockUtils.patternToString(mPatterLockView, pattern);
                    if (patronFinal.equals(patronGuardado)) {
                        Toast.makeText(ConfirmarPatronView.this, PATRON_CORRECTO, Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(ConfirmarPatronView.this, UserLogin.class);
                        startActivity(intent);
                    } else
                        Toast.makeText(ConfirmarPatronView.this, PATRON_INCORRECTO, Toast.LENGTH_SHORT).show();
                }

                @Override
                public void onCleared() {

                }
            });
        }

    }
}