package monopoly.contenido;


import monopoly.plataforma.Operacion;
import monopoly.plataforma.Juego;
import monopoly.plataforma.Tablero;
import monopoly.plataforma.Valor;

import java.util.Iterator;

public final class CartaPagar extends Carta{

    private double cantidad; //Positivo => Pagar , Negativo => Cobrar
    private boolean banca; //True => Banca, False => Todos los jugadores

    public CartaPagar(double cantidad, boolean banca, String descripcion){
        this.cantidad = cantidad;
        this.banca = banca;
        super.setDescripcion(descripcion);
    }

    public double getCantidad(Jugador jugador, Tablero tablero){
        if(!banca) return this.cantidad*(tablero.getAvatares().size() - 1);
        if(this.cantidad == 0) {
            double totalImpuestos = 0;
            for(Propiedades comp : jugador.getPropiedades()){
                if(comp instanceof Solar){
                    totalImpuestos += ((Solar) comp).getConstrucciones("Casa").size() * 4000;
                    totalImpuestos += ((Solar) comp).getConstrucciones("Hotel").size() * 115000;
                    totalImpuestos += ((Solar) comp).getConstrucciones("Piscina").size() * 2000;
                    totalImpuestos += ((Solar) comp).getConstrucciones("Pista").size() * 7500;
                }
            }
            return totalImpuestos;
        }else
            return this.cantidad;
    }

    private void cobrarAccion(Jugador jugador, Tablero tablero, double cantidad) {
        jugador.modificarDinero(cantidad * -1);
        jugador.modificarPremiosInversionesOBote(cantidad * -1);
        if (!banca) {
            Iterator<Jugador> itJug = tablero.getJugadores().values().iterator();
            while (itJug.hasNext()) {
                Jugador jug = itJug.next();
                if (!jug.equals(jugador)) {
                    jug.modificarDinero( this.cantidad);
                    jug.modificarPremiosInversionesOBote(this.cantidad);
                }
            }
        }else{
            if(cantidad > 0)
                Valor.actualizarDineroAcumulado(cantidad);
        }
    }

    public void accionCarta(Jugador jugador, Tablero tablero){
        Juego.consola.imprimir(super.getDescripcion());
        double cantidadTotal = this.getCantidad(jugador,tablero);
        Operacion operacion = new Operacion(tablero);
        if(jugador.getDinero() >= cantidadTotal){
            cobrarAccion(jugador,tablero,cantidadTotal);
        }else{
            Juego.consola.imprimir(jugador.getNombre() + " no tiene dinero suficiente para pagar.");
            if(operacion.menuHipotecar(jugador,tablero,cantidadTotal))
                cobrarAccion(jugador,tablero,cantidadTotal);
        }
    }
}
