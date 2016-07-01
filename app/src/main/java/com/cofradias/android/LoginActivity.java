package com.cofradias.android;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.cofradias.android.model.help.Constants;
import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.Query;
import com.firebase.client.ValueEventListener;

/**
 * Created by alaria on 28/06/2016.
 */
public class LoginActivity extends AppCompatActivity {

    private static final String LOG_TAG = LoginActivity.class.getSimpleName();
    private Toolbar toolbar;
    private EditText mUser, mPassword;
    private TextView mLoggedUser;
    private String usuarioFB, userLogged, passwordLogged;
    private Button mButtonLogin, mButtonPreferences;
    SharedPreferences preferences;
    private int REQUEST_SIGNUP = 5;
    private final int DRAWABLE_LEFT = 0;
    private final int DRAWABLE_TOP = 1;
    private final int DRAWABLE_RIGHT = 2;
    private final int DRAWABLE_BOTTOM = 3;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.login_activity);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_login);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("");

        // Initialize preferences
        preferences = PreferenceManager.getDefaultSharedPreferences(this);

        mUser = (EditText) findViewById(R.id.input_user);
        mPassword = (EditText) findViewById(R.id.input_password);
        mLoggedUser = (TextView) findViewById(R.id.logged_user);
        mButtonLogin = (Button) findViewById(R.id.btn_login);
        mButtonPreferences = (Button) findViewById(R.id.preferences_button);

        mButtonPreferences.setVisibility(View.INVISIBLE);

        if(preferences.getString("tipoUsuarioValidado", "").equals("admin")) {
            //mButtonPreferences.setVisibility(View.VISIBLE);
            setUserData();
            //mButtonLogin.setBackgroundResource(R.drawable.ic_background_log_out);
            //mButtonLogin.setText(getResources().getString(R.string.boton_salir));
            //mLoggedUser.setText(preferences.getString("tipoUsuarioValidado", ""));
        } else {
            mButtonPreferences.setVisibility(View.INVISIBLE);
            if(!preferences.getString("tipoUsuarioValidado", "").equals("")){
                setUserData();
                //mButtonLogin.setBackgroundResource(R.drawable.ic_background_log_out);
                //mButtonLogin.setText(getResources().getString(R.string.boton_salir));
                //mLoggedUser.setText(preferences.getString("tipoUsuarioValidado", ""));
            }
        }

        mUser.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_UP) {
                    int leftEdgeOfRightDrawable = mUser.getRight()
                            - mUser.getCompoundDrawables()[DRAWABLE_RIGHT].getBounds().width();
                    // when EditBox has padding, adjust leftEdge like
                    // leftEdgeOfRightDrawable -= getResources().getDimension(R.dimen.edittext_padding_left_right);
                    if (event.getRawX() >= leftEdgeOfRightDrawable) {
                        // clicked on clear icon
                        mUser.setText("");
                        return true;
                    }
                }
                return false;
            }
        });

        mPassword.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_UP) {
                    int leftEdgeOfRightDrawable = mPassword.getRight()
                            - mPassword.getCompoundDrawables()[DRAWABLE_RIGHT].getBounds().width();
                    // when EditBox has padding, adjust leftEdge like
                    // leftEdgeOfRightDrawable -= getResources().getDimension(R.dimen.edittext_padding_left_right);
                    if (event.getRawX() >= leftEdgeOfRightDrawable) {
                        // clicked on clear icon
                        mPassword.setText("");
                        return true;
                    }
                }
                return false;
            }
        });

        mPassword.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (event.getAction() == KeyEvent.ACTION_DOWN)
                    if ((keyCode == KeyEvent.KEYCODE_DPAD_CENTER) ||
                            (keyCode == KeyEvent.KEYCODE_ENTER)) {
                        //do something
                        //true because you handle the event
                        mButtonLogin.performClick();
                        return true;
                    }
                return false;
            }
        });

        mButtonLogin.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        if(preferences.getString("tipoUsuarioValidado", "").equals("")) {

                            userLogged = mUser.getText().toString();
                            passwordLogged = mPassword.getText().toString();
                            //Log.v(LOG_TAG, "USUARIO LOGEADO:"+userLogged);

                            Firebase myFirebaseRef = new Firebase(Constants.ConfigFireBase.FIREBASE_URL + Constants.ConfigFireBase.FIREBASE_CHILD_USUARIOS);
                            Query queryRef = myFirebaseRef.orderByChild("user").equalTo(userLogged);
                            queryRef.addValueEventListener(new ValueEventListener() {
                                @Override
                                public void onDataChange(DataSnapshot snapshot) {

                                    //Log.v(LOG_TAG, "onDataChange"+snapshot.getChildrenCount());
                                    if (snapshot.getChildrenCount() != 0) {
                                        for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                                            //Log.v(LOG_TAG, "onDataChange - FOR");

                                            usuarioFB = (String) dataSnapshot.child("user").getValue();
                                            if (userLogged.equals(usuarioFB) &
                                                    passwordLogged.equals(usuarioFB)) {

                                                Log.v(LOG_TAG, "onDataChange - IF");
                                                //Se actualizan las preferencias de usuario
                                                SharedPreferences.Editor edit = preferences.edit();

                                                //Log.v(LOG_TAG, "USUARIO LOGEADO:"+mUser.getText().toString());
                                                if (userLogged.equals("admin")) {
                                                    edit.putString("tipoUsuarioValidado", "admin");
                                                    //mButtonPreferences.setVisibility(View.VISIBLE);
                                                } else {
                                                    edit.putString("tipoUsuarioValidado", "cofrade");
                                                    mButtonPreferences.setVisibility(View.INVISIBLE);
                                                }
                                                edit.commit();

                                                mButtonLogin.setBackgroundResource(R.drawable.ic_background_log_out);
                                                mButtonLogin.setText(R.string.boton_salir);
                                                mLoggedUser.setText(userLogged);

                                                Toast.makeText(getApplicationContext(), R.string.login_message_ok, Toast.LENGTH_SHORT).show();

                                                finish();
                                            } else {

                                                //Log.v(LOG_TAG, "onDataChange - ELSE DENTRO");
                                                int intentos = REQUEST_SIGNUP - 1;
                                                String mensaje = getResources().getString(R.string.login_message_ko_1)
                                                        + " " + intentos
                                                        + " " + getResources().getString(R.string.login_message_ko_2);
                                                Toast.makeText(getApplicationContext(), String.valueOf(mensaje), Toast.LENGTH_SHORT).show();
                                                REQUEST_SIGNUP--;
                                                if (REQUEST_SIGNUP == 0) {
                                                    mButtonLogin.setEnabled(false);

                                                    //Se actualizan las preferencias de usuario
                                                    SharedPreferences.Editor edit = preferences.edit();
                                                    edit.putString("tipoUsuarioValidado", "");
                                                    edit.commit();

                                                    finish();
                                                }
                                            }
                                        }
                                    } else {
                                        //Log.v(LOG_TAG, "onDataChange - ELSE");
                                        int intentos = REQUEST_SIGNUP - 1;
                                        String mensaje = getResources().getString(R.string.login_message_ko_1)
                                                + " " + intentos
                                                + " " + getResources().getString(R.string.login_message_ko_2);
                                        Toast.makeText(getApplicationContext(), String.valueOf(mensaje), Toast.LENGTH_SHORT).show();
                                        REQUEST_SIGNUP--;
                                        if (REQUEST_SIGNUP == 0) {
                                            mButtonLogin.setEnabled(false);

                                            //Se actualizan las preferencias de usuario
                                            SharedPreferences.Editor edit = preferences.edit();
                                            edit.putString("tipoUsuarioValidado", "");
                                            edit.commit();

                                            finish();
                                        }
                                    }
                                }

                                @Override
                                public void onCancelled(FirebaseError error) {
                                }
                            });
                        } else {

                            //Se actualizan las preferencias de usuario
                            SharedPreferences.Editor edit = preferences.edit();
                            edit.putString("tipoUsuarioValidado", "");
                            edit.commit();

                            String mensaje = getResources().getString(R.string.login_message_salir);
                            Toast.makeText(getApplicationContext(), String.valueOf(mensaje), Toast.LENGTH_SHORT).show();

                            finish();
                        }
                    }
                }
        );
    }

    public void setUserData () {

        mButtonLogin.setBackgroundResource(R.drawable.ic_background_log_out);
        mButtonLogin.setText(getResources().getString(R.string.boton_salir));
        mLoggedUser.setText(preferences.getString("tipoUsuarioValidado", ""));

        mUser.setVisibility(View.GONE);
        mPassword.setVisibility(View.GONE);
    }

    public void getPreferences(View v) {

        String tipoUsuario = preferences.getString("tipoUsuarioValidado", "n/a");
        Toast.makeText(this, tipoUsuario, Toast.LENGTH_SHORT).show();
    }

    public void Volver(View v) {

        finish();
    }
}
