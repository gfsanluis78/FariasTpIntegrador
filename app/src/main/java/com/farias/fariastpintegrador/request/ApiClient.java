package com.farias.fariastpintegrador.request;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import com.farias.fariastpintegrador.modelo.*;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.security.cert.CertificateException;
import java.util.ArrayList;
import java.util.List;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.PATCH;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;


public class ApiClient {
    private ArrayList<Propietario> propietarios = new ArrayList<>();
    private ArrayList<Inquilino> inquilinos = new ArrayList<>();
    private ArrayList<Inmueble> inmuebles = new ArrayList<>();
    private ArrayList<Contrato> contratos = new ArrayList<>();
    private ArrayList<Pago> pagos = new ArrayList<>();

    // Con Retrofit
    // De aca para abajo descomentar y comentar Datos locales

    private static final String URLBASE="https://192.168.1.111:45455/api/";    //le pongo el nombre de Url base que es mas informativa, termina en /
    private static  PostInterface postInterface;

    private static SharedPreferences sharedPreferences;

    private static SharedPreferences conectar(Context context){
        if (sharedPreferences==null){
            sharedPreferences = context.getSharedPreferences("Usuario",0);
        }
        return  sharedPreferences;
    }

    public static void guardar(Context context, String token){

        SharedPreferences sharedPreferences = conectar(context);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("token", "Bearer " + token);
        Log.d("mensaje/ApiC/guardar", "El token guardado: " + token);
        editor.commit();
    }

    public static String leer(Context context, String ubicacion){
        SharedPreferences sharedPreferences = conectar(context);
        String token = sharedPreferences.getString("token", "No token");
        Log.d("mensaje/ApiC/leer/", "El token leido: ok");


        return token;
    }

    // Construccion del objeto retrofit
    public static PostInterface getMyApiClient(String ubicacion){

        Log.d("mensaje Api", ubicacion);
        Gson gson = new GsonBuilder().setLenient().create();
        Retrofit retrofit=new Retrofit.Builder()
                .baseUrl(URLBASE)
                // agrego para confiar
                .client(getUnsafeOkHttpClient().build())
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();

        postInterface=retrofit.create(PostInterface.class);

        return postInterface;
    }

    public static OkHttpClient.Builder getUnsafeOkHttpClient() {
        try {
            // Create a trust manager that does not validate certificate chains
            final TrustManager[] trustAllCerts = new TrustManager[]{
                    new X509TrustManager() {
                        @Override
                        public void checkClientTrusted(java.security.cert.X509Certificate[] chain, String authType) throws CertificateException {
                        }

                        @Override
                        public void checkServerTrusted(java.security.cert.X509Certificate[] chain, String authType) throws CertificateException {
                        }

                        @Override
                        public java.security.cert.X509Certificate[] getAcceptedIssuers() {
                            return new java.security.cert.X509Certificate[]{};
                        }
                    }
            };

            // Install the all-trusting trust manager
            final SSLContext sslContext = SSLContext.getInstance("SSL");
            sslContext.init(null, trustAllCerts, new java.security.SecureRandom());

            // Create an ssl socket factory with our all-trusting manager
            final SSLSocketFactory sslSocketFactory = sslContext.getSocketFactory();

            OkHttpClient.Builder builder = new OkHttpClient.Builder();
            builder.sslSocketFactory(sslSocketFactory, (X509TrustManager) trustAllCerts[0]);
            builder.hostnameVerifier(new HostnameVerifier() {
                @Override
                public boolean verify(String hostname, SSLSession session) {
                    return true;
                }
            });
            return builder;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    // armar la interface
    public interface  PostInterface{

        @FormUrlEncoded
        @POST("Propietarios/login")
        Call<LoginRetrofit> login(@Field("Usuario") String usuario, @Field("Clave") String clave);

        @GET("Propietarios")
        Call<Propietario> obtenerUsuarioActual(@Header("Authorization") String token);

        // obtnerPropiedades()
        @GET("Inmuebles")
        Call<List<Inmueble>> obtnerPropiedades(@Header("Authorization") String token);

        // obtenerPropiedadesAlquiladas()
        @GET("Inmuebles/Alquilados")
        Call<List<Inmueble>> obtenerPropiedadesAlquiladas(@Header("Authorization")  String token);

        // obtenerContratoVigente(Inmueble inmueble)
        @POST("Contratos")
        Call<Contrato> obtenerContratoVigente(@Header("Authorization")  String token, @Body Inmueble inmueble);


        // obtenerContratosVigentes()
        @GET("Contratos/Vigentes")
        Call<List<Contrato>> obtenerContratosVigentes (@Header("Authorization") String token);


        // obtenerInquilino(Inmueble inmueble)
        @POST("Inquilinos")
        Call<List<Inquilino>> obtenerInquilino (@Header("Authorization") String token,@Body Inmueble inmueble);

        // obtenerInquilinos()
        @GET("Inquilino/Todos")
        Call<List<Inquilino>> obtenerInquilinos (@Header("Authorization") String token);

        // obtenerPagos(Contrato contratoVer)
        @POST("Pagos")
        Call<List<Pago>> obtenerPagos(@Header("Authorization") String token,@Body Contrato contrato);


        // actualizarPerfil(Propietario propietario)
        @PATCH("Propietarios/Editar")
        Call<Propietario> actualizarPerfil(@Header("Authorization") String token, @Body Propietario propietario);


        // actualizarInmueble(Inmueble inmueble)
        @PATCH("Inmuebles/CambioEstado")
        Call<Inmueble> actualizarInmueble(@Header("Authorization") String token, @Body Inmueble inmueble);
    }

}



    // Con datos Locales
    // De aca para abajo descomentar y comentar retrofit
    /*
    private static Propietario usuarioActual = null;
    private static ApiClient api = null;

    // Api con valores locales
    private ApiClient() {
        //Nos conectamos a nuestra "Base de Datos"
        cargaDatos();
    }

    //Método para crear una instancia de ApiClient
    public static ApiClient getApi() {
        if (api == null) {
            api = new ApiClient();
        }
        return api;

    }


    //Servicios
    //Para que pueda iniciar sesion
    public Propietario login(String mail, final String password) {
        for (Propietario propietario : propietarios) {
            if (propietario.getEmail().equals(mail) && propietario.getContraseña().equals(password)) {
                usuarioActual = propietario;
                return propietario;
            }
        }
        return null;

    }


    //Retorna el usuario que inició Sesión
    public Propietario obtenerUsuarioActual() {
        return usuarioActual;
    }

    //Retorna las propiedades del usuario propietario logueado
    public ArrayList<Inmueble> obtnerPropiedades() {
        ArrayList<Inmueble> temp = new ArrayList<>();
        for (Inmueble inmueble : inmuebles) {
            if (inmueble.getPropietario().equals(usuarioActual)) {
                temp.add(inmueble);
            }
        }
        return temp;
    }

    //Lista de inmuebles con contratos vigentes del Propietario logueado
    public ArrayList<Inmueble> obtenerPropiedadesAlquiladas() {
        ArrayList<Inmueble> temp = new ArrayList<>();
        for (Contrato contrato : contratos) {
            if (contrato.getInmueble().getPropietario().equals(usuarioActual)) {
                temp.add(contrato.getInmueble());
            }
        }
        return temp;
    }


//Dado un inmueble retorna el contrato activo de dicho inmueble

    public Contrato obtenerContratoVigente(Inmueble inmueble) {

        for (Contrato contrato : contratos) {
            if (contrato.getInmueble().equals(inmueble)) {
                return contrato;
            }
        }
        return null;
    }

    //Dado un inmueble, retorna el inquilino del ultimo contrato activo de ese inmueble.
    public Inquilino obtenerInquilino(Inmueble inmueble) {
        for (Contrato contrato : contratos) {
            if (contrato.getInmueble().equals(inmueble)) {
                return contrato.getInquilino();
            }
        }
        return null;
    }

    //Dado un Contrato, retorna los pagos de dicho contrato
    public ArrayList<Pago> obtenerPagos(Contrato contratoVer) {
        ArrayList<Pago> temp = new ArrayList<>();
        for (Contrato contrato : contratos) {
            if (contrato.equals(contratoVer)) {
                for (Pago pago : pagos) {
                    if (pago.getContrato().equals(contrato)) {
                        temp.add(pago);
                    }
                }
            }
            break;
        }
        return temp;
    }

    //Actualizar Perfil
    public void actualizarPerfil(Propietario propietario) {
        int posición = propietarios.indexOf(propietario);
        if (posición != -1) {
            propietarios.set(posición, propietario);
        }
    }

    //ActualizarInmueble
    public void actualizarInmueble(Inmueble inmueble) {
        int posicion = inmuebles.indexOf(inmueble);
        if (posicion != -1) {
            inmuebles.set(posicion, inmueble);
        }
    }

    private void cargaDatos() {

        //Propietarios
        Propietario juan = new Propietario(1, 23492012L, "Juan", "Perez", "juan@mail.com", "123", "2664553447", R.drawable.juan);
        Propietario sonia = new Propietario(2, 17495869L, "Sonia", "Lucero", "sonia@mail.com", "123", "266485417", R.drawable.sonia);
        propietarios.add(juan);
        propietarios.add(sonia);

        //Inquilinos
        Inquilino mario = new Inquilino(100, 25340691L, "Mario", "Luna", "Aiello sup.", "luna@mail.com", "2664253411", "Lucero Roberto", "2664851422");
        inquilinos.add(mario);

        //Inmuebles
        Inmueble salon = new Inmueble(501, "Colon 340", "comercial", "salon", 2, 20000, juan, true, "http://www.secsanluis.com.ar/servicios/salon1.jpg");
        Inmueble casa = new Inmueble(502, "Mitre 800", "particular", "casa", 2, 15000, juan, true, "http://www.secsanluis.com.ar/servicios/casa1.jpg");
        Inmueble otraCasa = new Inmueble(503, "Salta 325", "particular", "casa", 3, 17000, sonia, true, "http://www.secsanluis.com.ar/servicios/casa2.jpg");
        Inmueble dpto = new Inmueble(504, "Lavalle 450", "particular", "dpto", 2, 25000, sonia, true, "http://www.secsanluis.com.ar/servicios/departamento1.jpg");
        Inmueble casita = new Inmueble(505, "Belgrano 218", "particular", "casa", 5, 90000, sonia, true, "http://www.secsanluis.com.ar/servicios/casa3.jpg");

        inmuebles.add(salon);
        inmuebles.add(casa);
        inmuebles.add(otraCasa);
        inmuebles.add(dpto);
        inmuebles.add(casita);

        //Contratos
        Contrato uno = new Contrato(701, "05/01/2020", "05/01/2021", 17000, mario, otraCasa);
        contratos.add(uno);
        //Pagos
        pagos.add(new Pago(900, 1, uno, 17000, "10/02/2020"));
        pagos.add(new Pago(901, 2, uno, 17000, "10/03/2020"));
        pagos.add(new Pago(902, 3, uno, 17000, "10/04/2020"));


    }
}
*/