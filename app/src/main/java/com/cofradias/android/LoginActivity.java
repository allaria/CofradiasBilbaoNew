package com.cofradias.android;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;
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

    //private static final int REQUEST_SIGNUP = 0;
    private static final String LOG_TAG = LoginActivity.class.getSimpleName();
    private MenuItem menuItem;
    private EditText mUser, mPassword;
    private TextView mLoggedUser;
    private String usuarioFB, userLogged, passwordLogged;
    private Button mButtonLogin, mButtonPreferences;
    int REQUEST_SIGNUP = 5;
    SharedPreferences preferences;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.login_activity);

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

        //mUser.setFocusable(false);
        mUser.setVisibility(View.GONE);
        //mUser.setEnabled(false);
        mPassword.setVisibility(View.GONE);
        //mPassword.setFocusable(false);
        //mPassword.setEnabled(false);
    }

    public void getPreferences(View v) {

        String tipoUsuario = preferences.getString("tipoUsuarioValidado", "n/a");
        Toast.makeText(this, tipoUsuario, Toast.LENGTH_SHORT).show();
    }
}
