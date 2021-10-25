package  com.farias.fariastpintegrador.ui.login;

import static androidx.core.content.ContextCompat.getSystemService;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.annotation.StringRes;
import androidx.appcompat.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;
import com.farias.fariastpintegrador.MainActivity;
import com.farias.fariastpintegrador.R;
import com.farias.fariastpintegrador.modelo.Propietario;
import com.farias.fariastpintegrador.databinding.ActivityLoginBinding;

import java.util.Objects;

public class LoginActivity extends AppCompatActivity implements SensorEventListener {

    private LoginViewModel loginViewModel;
    private ActivityLoginBinding binding;
    private  Propietario p;
    public static Context contextOfApplication;


    // Shake llamada
    private SensorManager sensorManager;
    private Sensor accelerometerSensor;
    private boolean isAceelerometerSensorAvailable, itIsNotFirstTime = false;
    private float lecturaX, lecturaY, lecturaZ, ultimaX, ultimaY, ultimaZ;
    private float xDiferencia, yDiferencia, zDiferencia;
    private float shakeTheShold = 2f;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityLoginBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        final EditText usernameEditText = binding.username;
        final EditText passwordEditText = binding.password;
        final Button loginButton = binding.login;
        final ProgressBar loadingProgressBar = binding.loading;

        if(Build.VERSION.SDK_INT>= Build.VERSION_CODES.M
                && checkSelfPermission(android.Manifest.permission.CALL_PHONE)
                != PackageManager.PERMISSION_GRANTED){
            requestPermissions(new String[]{Manifest.permission.CALL_PHONE},1000);
        }

        loginViewModel = ViewModelProvider.AndroidViewModelFactory.getInstance(getApplication()).create(LoginViewModel.class);


        loginViewModel.getLoginFormState().observe(this, new Observer<LoginFormState>() {
            @Override
            public void onChanged(@Nullable LoginFormState loginFormState) {
                if (loginFormState == null) {
                    return;
                }
                loginButton.setEnabled(loginFormState.isDataValid());
                if (loginFormState.getUsernameError() != null) {
                    usernameEditText.setError(getString(loginFormState.getUsernameError()));
                }
                if (loginFormState.getPasswordError() != null) {
                    passwordEditText.setError(getString(loginFormState.getPasswordError()));
                }
            }
        });




        TextWatcher afterTextChangedListener = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                // ignore
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                // ignore
            }

            @Override
            public void afterTextChanged(Editable s) {
                loginViewModel.loginDataChanged(usernameEditText.getText().toString(),
                        passwordEditText.getText().toString());
            }
        };
        usernameEditText.addTextChangedListener(afterTextChangedListener);
        passwordEditText.addTextChangedListener(afterTextChangedListener);
        passwordEditText.setOnEditorActionListener(new TextView.OnEditorActionListener() {

            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_DONE) {
                    loginViewModel.login(usernameEditText.getText().toString(),
                            passwordEditText.getText().toString());
                }
                return false;
            }
        });

        loginViewModel.getSuceso().observe(this, elSuceso ->{
            if(elSuceso == "Exito"){
                Log.d("mensaje: " , "Login Exitoso");
            } else
            {
                Log.d("mensaje: " , "Login Fallido");
                loadingProgressBar.setVisibility( View.INVISIBLE);
                passwordEditText.setText("");
                usernameEditText.setText("");

            }

        });

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loadingProgressBar.setVisibility( View.VISIBLE);
                loginViewModel.login(usernameEditText.getText().toString(),
                        passwordEditText.getText().toString());

                // para cerrar el teclado virtual
                InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(passwordEditText.getWindowToken(), 0);

                //loginViewModel.setPropietario();
                loginViewModel.getPropietario().observe( LoginActivity.this, propietario -> {
                    p = propietario;

                    if (p != null){

                        Intent  MainIntent = new Intent (LoginActivity.this, MainActivity.class);
                        Bundle bundle = new Bundle();
                        bundle.putSerializable("propietario", p);
                        MainIntent.putExtra("propietario", bundle);


                        startActivity(MainIntent);

                        String nombre = p.getNombre();

                        // TODO: se personaliza el saludo de acuerdo a la termincion del nombre para Todas y todos :)
                        char ultimo;
                        ultimo = nombre.charAt(nombre.length()-1);

                        Log.d("mensaje", "updateUiWithUser: " + ultimo);

                        String welcome;
                        welcome = Objects.equals(ultimo, 'a') ? "Bienvenida " +  p.getNombre() : "Bienvenido " +  p.getNombre() ;
                        Toast.makeText(getApplicationContext(), welcome, Toast.LENGTH_LONG).show();
                    }
                });


            }
        });

        // Shake llamada
        sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        if(sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER) != null){
            accelerometerSensor = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
            isAceelerometerSensorAvailable = true;
        } else {
            // xTextView.setTex("Sensor acelerometro no esta disponible");
            isAceelerometerSensorAvailable = false;
        }


    }



    private void showLoginFailed(@StringRes Integer errorString) {
        Toast.makeText(getApplicationContext(), errorString, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onSensorChanged(SensorEvent sensorEvent) {

        lecturaX = sensorEvent.values[0];
        lecturaY = sensorEvent.values[1];
        lecturaZ = sensorEvent.values[2];

        if(itIsNotFirstTime){

            xDiferencia = Math.abs(ultimaX - lecturaX);
            yDiferencia = Math.abs(ultimaY - lecturaY);
            zDiferencia = Math.abs(ultimaZ - lecturaZ);

            if((xDiferencia > shakeTheShold && yDiferencia > shakeTheShold) ||
            (xDiferencia > shakeTheShold && zDiferencia > shakeTheShold) ||
                    (yDiferencia >shakeTheShold && zDiferencia > shakeTheShold)) {

                Intent llamada = new Intent(Intent.ACTION_CALL);
                llamada.setData(Uri.parse("tel:2664123456"));
                startActivity(llamada);

            }
        }

        ultimaX = lecturaX;
        ultimaY = lecturaY;
        ultimaZ = lecturaZ;
        itIsNotFirstTime = true;
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int i) {

    }

    @Override
    protected void onResume() {
        super.onResume();
        if(isAceelerometerSensorAvailable)
            sensorManager.registerListener(this,accelerometerSensor, SensorManager.SENSOR_DELAY_NORMAL);
    }

    @Override
    protected void onPause() {
        super.onPause();
        if(isAceelerometerSensorAvailable)
            sensorManager.unregisterListener(this);
    }

    public static Context getContextOfApplication(){ return contextOfApplication; }


    private void inicializar(){

    }

}




