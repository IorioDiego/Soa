package com.example.covid.views;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.andrognito.patternlockview.PatternLockView;
import com.andrognito.patternlockview.listener.PatternLockViewListener;
import com.andrognito.patternlockview.utils.PatternLockUtils;
import com.example.covid.IUserRegister;
import com.example.covid.R;
import com.example.covid.UserRegister;
import com.example.covid.UserRegisterPresenter;
import com.example.covid.interfaces.IPatronDesbloqueo;
import com.example.covid.presenters.PatronDesbloquePresenter;

import java.util.List;

import io.paperdb.Paper;

public class PatronDesbloqueoView extends AppCompatActivity implements IPatronDesbloqueo.View {

    PatternLockView mPatterLockView;

    String patronFinal = "";
    IPatronDesbloqueo.Presenter presenter;
    private static final String PATRON_CORRECTO = "Patron CORRECTO", PATRON_INCORRECTO = "Patron INCORRECTO",
            PATRON_GUARDADO = "Patron guardado correctamente";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        presenter = new PatronDesbloquePresenter((IPatronDesbloqueo.View) this);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_patron_desbloqueo);
        presenter.inicializarPaper((IPatronDesbloqueo.View) this);

        final String patronGuardado = presenter.leerPaper();
        if (patronGuardado != null && !patronGuardado.equals("null")) {

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
                    String final_pattern = PatternLockUtils.patternToString(mPatterLockView, pattern);
                    if (final_pattern.equals(patronGuardado)) {
                        Toast.makeText(PatronDesbloqueoView.this, PATRON_CORRECTO, Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(PatronDesbloqueoView.this, UserLogin.class);//podria ir en el presenter
                        startActivity(intent);
                    } else
                        Toast.makeText(PatronDesbloqueoView.this, PATRON_INCORRECTO, Toast.LENGTH_SHORT).show();
                }

                @Override
                public void onCleared() {

                }
            });

        } else {

            setContentView(R.layout.activity_patron_desbloqueo);
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
                }

                @Override
                public void onCleared() {

                }
            });

            Button btnGuardar = (Button) findViewById(R.id.btnGuardarPatron);
            btnGuardar.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    presenter.escribirPaper(patronFinal);
                    //  Paper.book().write(patronBase,patronFinal);
                    Toast.makeText(PatronDesbloqueoView.this, PATRON_GUARDADO, Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(PatronDesbloqueoView.this, ConfirmarPatronView.class);
                    startActivity(intent);////////ESTO ROMPE COSAASSS

                }
            });

        }


    }


}