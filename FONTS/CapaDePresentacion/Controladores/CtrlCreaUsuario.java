package FONTS.CapaDePresentacion.Controladores;

public class CtrlCreaUsuario {
    private CtrlPresentacion CP;

    public CtrlCreaUsuario() {
        CP = new CtrlPresentacion();
    }

    //0 := se ha realizado el sign up correctamente
    //1 := el username ya existe
    //2 := username o password son null
    public int signup(String username, String password) {
        return CP.signup(username, password);
    }
}

