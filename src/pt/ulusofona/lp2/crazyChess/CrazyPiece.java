package pt.ulusofona.lp2.crazyChess;

import java.util.List;

import static pt.ulusofona.lp2.crazyChess.Simulador.*;

abstract public class CrazyPiece implements Comparable<CrazyPiece> {
    int id;
    int idTipoPeca;
    int equipa;
    int valorRelativo;
    String alcunha;
    int x;
    int y;
    boolean captura; //peça para captura
    int numeroCapturas;
    int numeroPontos;
    int numeroJogadasInvalidasNaquelaPeca;
    int numeroJogadasValidasNaquelaPeca;


    CrazyPiece(int id, int idTipoPeca, int equipa, String alcunha){
        this.id=id;
        this.idTipoPeca=idTipoPeca;
        this.equipa=equipa;
        this.alcunha=alcunha;
    }

    CrazyPiece(int id, int idTipoPeca, int equipa, String alcunha, int numeroCapturas,int numeroPontos,int numeroJogadasInvalidasNaquelaPeca,int numeroJogadasValidasNaquelaPeca){
        this.id=id;
        this.idTipoPeca=idTipoPeca;
        this.equipa=equipa;
        this.alcunha=alcunha;
        this.numeroCapturas=numeroCapturas;
        this.numeroPontos=numeroPontos;
        this.numeroJogadasInvalidasNaquelaPeca=numeroJogadasInvalidasNaquelaPeca;
        this.numeroJogadasValidasNaquelaPeca=numeroJogadasValidasNaquelaPeca;

    }

    CrazyPiece(int id, int idTipoPeca, int equipa, String alcunha, int x, int y, boolean captura){
        this.id=id;
        this.idTipoPeca=idTipoPeca;
        this.equipa=equipa;
        this.alcunha=alcunha;
        this.x=x;
        this.y=y;
        this.captura=captura;

    }

    CrazyPiece(){}

    public void setX(int x){//DONE
        this.x = x;
    }


    public int getNumeroCapturas() {
        return numeroCapturas;
    }

    public int getNumeroJogadasInvalidasNaquelaPeca() {
        return numeroJogadasInvalidasNaquelaPeca;
    }

    public int getNumeroJogadasValidasNaquelaPeca() {
        return numeroJogadasValidasNaquelaPeca;
    }

    public int getNumeroPontos() {
        return numeroPontos;
    }


    public void setAlcunha(String alcunha) {
        this.alcunha = alcunha;
    }

    public void setEquipa(int equipa) {
        this.equipa = equipa;
    }

    public void setIdTipoPeca(int idTipoPeca) {
        this.idTipoPeca = idTipoPeca;
    }

    public void setNumeroCapturas(int numeroCapturas) {
        this.numeroCapturas = numeroCapturas;
    }

    public void setNumeroJogadasInvalidasNaquelaPeca(int numeroJogadasInvalidasNaquelaPeca) {
        this.numeroJogadasInvalidasNaquelaPeca = numeroJogadasInvalidasNaquelaPeca;
    }

    public void setNumeroJogadasValidasNaquelaPeca(int numeroJogadasValidasNaquelaPeca) {
        this.numeroJogadasValidasNaquelaPeca = numeroJogadasValidasNaquelaPeca;
    }

    public void setNumeroPontos(int numeroPontos) {
        this.numeroPontos = numeroPontos;
    }

    public void setValorRelativo(int valorRelativo) {
        this.valorRelativo = valorRelativo;
    }

    public int getValorRelativo() {
        return valorRelativo;
    }

    public int getIDTipoPecaJogada(int x, int y){ //DONE
        int id=-1;
        for(CrazyPiece p: pecas){
            if(p.getX()==x && p.getY()==y){
                return p.getIdTipoPeca();
            }
        }
        return id;
    }

    public int compareTo(CrazyPiece obj){
        if(this.getNumeroPontos()==obj.numeroPontos){
            return 0;
        }
        if(this.getNumeroPontos()<obj.numeroPontos){
            return -1;
        }else{
            return 1;
        }
    }

    public boolean testeRainhaPerto(int xD, int yD){
        for(CrazyPiece p: pecas){
            if((p.getIdTipoPeca()==1 && p.getEquipa()!=segueEquipaAJogar)){
                if(Math.abs(p.getY()-yD)==1 && Math.abs(p.getX()-xD)==1){
                    return false;
                }
            }
        }
        return true;
    }

    public String getNomeIdPeca(int idTipoPeca){
        switch(idTipoPeca){
            case 0:
                return "Rei";
            case 1:
                return "Rainha";
            case 2:
                return "Ponei Mágico";
            case 3:
                return "Padre da Vila";
            case 4:
                return "TorreH";
            case 5:
                return "TorreV";
            case 6:
                return "Lebre";
            case 7:
                return "Joker";
        }
        return null;
    }

    public void setY(int y){//DONE
        this.y = y;
    }

    public int getX(){
        return x;
    }

    public int getY(){
        return y;
    }

    public String getAlcunha() {
        return alcunha;
    }

    public boolean getCaptura(){
        return captura;
    }

    public void setCaptura(boolean captura){
        this.captura=captura;
    }

    public void setId(int id){
        this.id=id;
    }

    public int getId(){ // DONE
        return id;
    }

    public int getIdTipoPeca(){
        return idTipoPeca;
    }

    public int getEquipa(){
        return equipa;
    }

    abstract public String getImagePNG();

    public int mudaTurno(int turno) {
        return turno;
    }

    public int getIDPeca(int x, int y){ //DONE
        int id = 0;
        for(CrazyPiece p: pecas){
            if(p.getX()==x && p.getY()==y){
                return p.getId();
            }
        }
        return id;
    }
    public boolean movimentoHorizontal(int pEsq, int pDir, int y){   //da esquerda para a direita
        pEsq +=1;
        while (pEsq<pDir) {
            if (getIDPeca(pEsq, y) != 0) {
                return false;
            }
            pEsq++;
        }
        return true;
    }

    public boolean movimentoVertical(int pCima, int pBaixo, int x ){     //de cima para baixo
        pCima +=1;
        while (pCima<pBaixo) {
            if (getIDPeca(x, pCima) != 0) {
                return false;
            }
            pCima++;
        }
        return true;
    }


    public boolean movimentoDiagonalEsquerdaDireita(int xCima, int xBaixo, int yCima, int yBaixo) { //de cima para baixo
        xCima +=1;
        yCima +=1;
        while (xCima<xBaixo) {
            if (getIDPeca(xCima, yCima) != 0) {
                return false;
            }
            xCima++;
            yCima++;
        }
        return true;
    }

    public boolean movimentoDiagonalDireitaEsquerda(int xCima, int xBaixo, int yCima, int yBaixo){ // de cima para baixo
        xCima -=1;
        yCima +=1;
        while (xCima>xBaixo) {
            if (getIDPeca(xCima, yCima) != 0) {
                return false;
            }
            xCima--;
            yCima++;
        }
        return true;
    }



    public String toString() {   // DONE
        String mensagem;

        if(!captura){
            if(idTipoPeca==0){
                mensagem = id + " | " + getNomeIdPeca(idTipoPeca) + " | " + "(infinito)" + " | " + equipa + " | " + alcunha + " @ (" + x + ", " + y + ")";
            }else{
                mensagem = id + " | " + getNomeIdPeca(idTipoPeca) + " | " + valorRelativo + " | " + equipa + " | " + alcunha + " @ (" + x + ", " + y + ")";
            }
        }else{
            if(idTipoPeca==0){
                mensagem = id + " | " + getNomeIdPeca(idTipoPeca) + " | " + "(infinito)" + " | " + equipa + " | " + alcunha + " @ (n/a)";
            }else{
                mensagem = id + " | " + getNomeIdPeca(idTipoPeca) + " | " + valorRelativo + " | " + equipa + " | " + alcunha + " @ (n/a)";
            }
        }
        return mensagem;
    }

    abstract boolean jogada(CrazyPiece p, int xO, int yO, int xD, int yD);

    abstract List<String> sugestao(List<CrazyPiece> pecas, int xO, int yO, int dimensao);

}
