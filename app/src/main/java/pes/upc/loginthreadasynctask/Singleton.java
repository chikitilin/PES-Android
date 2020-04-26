package pes.upc.loginthreadasynctask;

public class Singleton {
    private static final Singleton Instancia= new Singleton();  //Unica instancia de esta clase

    public String usuarioSingleton;  //Variables que queremos compartir entre Activities
    public String passwordSingleton;

    public static Singleton getInstancia(){   //Metodo para obtener la unica instancia de esta clase
        return Instancia;
    }
    private Singleton(){} //Constructor privado para no acceder desde fuera

}
