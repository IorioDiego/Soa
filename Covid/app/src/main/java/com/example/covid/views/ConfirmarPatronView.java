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

import java.util.List;

import io.paperdb.Paper;

public class ConfirmarPatronView extends AppCompatActivity implements  IPatronDesbloqueo.View {

    PatternLockView mPatterLockView;

    String patronFinal = "";
    IPatronDesbloqueo.Presenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_confirmar_patron);

        presenter.inicializarPaper((IPatronDesbloqueo.View) this);
        final String patronGuardado = presenter.leerPaper();
        if (!(patronGuardado != null) && !patronGuardado.equals("null")) {

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
                        Toast.makeText(ConfirmarPatronView.this, "Patron CORRECTO", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(ConfirmarPatronView.this, UserRegister.class);
                        startActivity(intent);
                    } else
                        Toast.makeText(ConfirmarPatronView.this, "Patron INCORRECTO", Toast.LENGTH_SHORT).show();
                }

                @Override
                public void onCleared() {

                }
            });
        }

    }
}