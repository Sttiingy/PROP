package FONTS.CapaDePresentacion.Controladores;

public class CtrlEditarPerfil {
    private CtrlPresentacion CP;

    public CtrlEditarPerfil() {
        CP = new CtrlPresentacion();
    }

    // retorna 0 si username y pass correctos;
    // retorna 1 si username o pass vacíos
    // retorna 2 si el username ya existe
    // retorna 3 si el username y el password son iguales
    public int saveChanges(String newUsername, String newPassword) {
        String[] activeUser = getActiveUser();
        if (activeUser[1] != newUsername || activeUser[2] != newPassword) {
            return CP.saveChanges(newUsername, newPassword);
        }
        return 3;
    }

    public String[] getActiveUser() {
        return CP.getActiveUser();
    }
}