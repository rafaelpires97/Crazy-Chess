package pt.ulusofona.lp2.crazyChess;

public class JogadaAnterior {
    int iDpecaMovimentada;
    int xO;
    int yO;
    int iDPecaPitada; // O se nao comer nenhuma
    int xD;
    int yD;
    int turnoDaJogada;
    int valorRelativoPecaComida;

    JogadaAnterior(int iDpecaMovimentada, int xO, int yO,int iDPecaPitada , int xD, int yD, int turnoDaJogada,int valorRelativoPecaComida){
        this.iDpecaMovimentada=iDpecaMovimentada;
        this.xO=xO;
        this.yO=yO;
        this.iDPecaPitada=iDPecaPitada;
        this.xD=xD;
        this.yD=yD;
        this.turnoDaJogada=turnoDaJogada;
        this.valorRelativoPecaComida=valorRelativoPecaComida;
    }


    public int getValorRelativoPecaComida() {
        return valorRelativoPecaComida;
    }


    public int getTurnoDaJogada(){
        return turnoDaJogada;
    }

    public int getiDpecaMovimentada(){
        return iDpecaMovimentada;
    }

    public int getiDPecaPitada(){
        return iDPecaPitada;
    }

    public int getxO(){
        return xO;
    }

    public int getyO(){
        return yO;
    }

    public int getxD(){
        return xD;
    }

    public int getyD(){
        return yD;
    }

    public void setxO(int xO) {
        this.xO = xO;
    }

    public void setxD(int xD) {
        this.xD = xD;
    }

    public void setyD(int yD) {
        this.yD = yD;
    }

    public void setyO(int yO) {
        this.yO = yO;
    }

}
