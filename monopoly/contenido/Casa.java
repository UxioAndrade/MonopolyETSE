package monopoly.contenido;

public final class Casa extends Edificios{

    public Casa(double precio, Solar comprable){
        super(precio, comprable);
    }

    public void setPrecio(double precio){
        super.setPrecio(precio*0.6);
    }

}
