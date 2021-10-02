package  com.farias.fariastpintegrador.ui.login;

/**
 * Class exposing authenticated user details to the UI.
 */
class LoggedInUserView {
    private Integer id;
    private Long dni;
    private String nombre;
    private String apellido;
    private String contraseña;
    private String telefono;
    private String email;
    private int avatar;

    private String displayName;
    //... other data fields that may be accessible to the UI

    LoggedInUserView(String displayName) {
        this.id = id;
        this.dni = dni;
        this.apellido = apellido;
        this.contraseña = contraseña;
        this.telefono = telefono;
        this.email = email;
        this.avatar = avatar;
        this.displayName = displayName;
    }

    public Integer getId() {
        return id;
    }

    public Long getDni() {
        return dni;
    }

    public String getNombre() {
        return nombre;
    }

    public String getApellido() {
        return apellido;
    }

    public String getContraseña() {
        return contraseña;
    }

    public String getTelefono() {
        return telefono;
    }

    public String getEmail() {
        return email;
    }

    int getAvatar() { return avatar; }


    String getDisplayName() {
        return displayName;
    }
}