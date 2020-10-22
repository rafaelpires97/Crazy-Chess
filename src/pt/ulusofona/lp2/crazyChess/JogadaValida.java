package pt.ulusofona.lp2.crazyChess;

public class JogadaValida implements Comparable<JogadaValida> {
    int x;
    int y;
    int nrPontos;
    int valorRelativo;
    boolean captura;

    JogadaValida(int x, int y, int nrPontos, int valorRelativo, boolean captura) {
        this.x = x;
        this.y = y;
        this.nrPontos = nrPontos;
        this.valorRelativo=valorRelativo;
        this.captura=captura;
    }

    public int compareTo(JogadaValida j){
        if(this.captura==true){
            if(this.valorRelativo > j.valorRelativo){
                return 1;

            }else if(this.nrPontos < j.nrPontos){
                return -1;
            }else{
                return 0;
            }
        }else{
            return 0;
        }
    }

    public int getNrPontos() {
        return nrPontos;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public void setNrPontos(int nrPontos) {
        this.nrPontos = nrPontos;
    }

    public void setX(int x) {
        this.x = x;
    }

    public void setY(int y) {
        this.y = y;
    }



    public String toString() {
        return x + ", " + y + ", " + nrPontos;

    }
}

