package monopoly.contenido;

import monopoly.plataforma.Accion;
import monopoly.plataforma.Juego;
import monopoly.plataforma.Tablero;
import monopoly.plataforma.Valor;

import java.util.ArrayList;

public final class Sombrero extends Avatar{

    private double historialDinero;
    private ArrayList<Propiedades> historialCompras;
    private int numTiradas;

    public Sombrero(Jugador jug, Tablero tablero){
        super(jug,tablero);
        this.historialCompras = new ArrayList<>();
        super.numTiradas = 3;
        this.numTiradas = 3;
    }

    public int getNumTiradas() {
        return this.numTiradas;
    }

    public void setNumTiradas(int tiradas){
        if(tiradas >= 0)
            this.numTiradas = tiradas;
    }

    public void restarNumTiradas(){
        this.numTiradas--;
    }

    public double getHistorialDinero() {
        return this.historialDinero;
    }

    public ArrayList<Propiedades> getHistorialCompras() {
        return this.historialCompras;
    }

    public void resetHistorialCompras(){}

    public void moverEnAvanzado(int valor){
        Accion accion = new Accion(super.getTablero());
        if(valor > 4){
            this.moverZigZag(valor);
            this.getCasilla().accionCaer(this.getJugador(), valor, accion);
        }else {
            this.numTiradas = 0;
            Juego.consola.imprimir("El sombrero ya ha acabado sus tiradas este turno");
        }
    }

    private void moverACasilla(int valor){
        this.getCasilla().quitarAvatar(this);
        this.setCasilla(Valor.casillas.get(valor));
        this.getCasilla().anhadirAvatar(this);
    }

    private void moverZigZag(int valor) {
        if (this.getCasilla().getPosicion() < 10){
            this.moverACasilla(12);
            if(valor % 2 == 1)
                this.moverACasilla(10 + ((valor - 1 + this.getCasilla().getPosicion() - 2 - 10) % 10));
            else
                this.moverACasilla(39 - ((valor - 3 + this.getCasilla().getPosicion() - 10) % 10));
        } else if (this.getCasilla().getPosicion() < 20) {
            if(valor % 2 == 0)
                this.moverACasilla(10 + ((valor + this.getCasilla().getPosicion() - 10) % 10));
            else
                this.moverACasilla(39 - ((valor + this.getCasilla().getPosicion() - 10) % 10));
        } else if (this.getCasilla().getPosicion() < 30) {
            this.moverACasilla(32);
            if(valor % 2 == 1)
                this.moverACasilla(30 + ((valor - 1 + this.getCasilla().getPosicion() - 2 - 30) % 10));
            else
                this.moverACasilla(20 - ((valor - 1 + this.getCasilla().getPosicion() - 30 ) % 10));
        } else {
            if(valor % 2 == 0)
                this.moverACasilla(31 + ((valor + this.getCasilla().getPosicion() - 30) % 10));
            else
                this.moverACasilla(20 - ((valor + this.getCasilla().getPosicion() - 30 ) % 10));
        }
    }

}
