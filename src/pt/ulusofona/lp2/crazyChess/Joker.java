package pt.ulusofona.lp2.crazyChess;

import java.util.ArrayList;
import java.util.List;


public class Joker extends CrazyPiece  {
    int segueTurno;
    CrazyPiece p;
    Joker(int id, int idTipoPeca, int equipa, String alcunha, int segueTurno,int numeroCapturas,int numeroPontos,int numeroJogadasInvalidasNaquelaPeca,int numeroJogadasValidasNaquelaPeca){
        super(id,idTipoPeca,equipa,alcunha,numeroCapturas,numeroPontos,numeroJogadasInvalidasNaquelaPeca,numeroJogadasValidasNaquelaPeca);
        valorRelativo=4;
        this.segueTurno = segueTurno;
    }

    public String getImagePNG(){
        if(getEquipa()==10){
            return "joker_black.png";
        }else{
            return "joker_white.png";
        }
    }

    public int mudaTurno(int turno) {
        this.segueTurno = turno;
        switch (turno%6) {
            case 0:
                p = new Rainha(this.id,1,this.equipa,this.alcunha,turno,0,0,0,0);
            case 1:
                p = new PoneiMagico(this.id,2,this.equipa,this.alcunha,turno,0,0,0,0);
            case 2:
                p = new PadreDaVila(this.id,3,this.equipa,this.alcunha,turno,0,0,0,0);
            case 3:
                p = new TorreHor(this.id,4,this.equipa,this.alcunha,turno,0,0,0,0);
            case 4:
                p = new TorreVert(this.id,5,this.equipa,this.alcunha,turno,0,0,0,0);
            case 5:
                p = new Lebre(this.id,6,this.equipa,this.alcunha,turno,0,0,0,0);
        }
        return turno;
    }

    @Override
    public boolean jogada(CrazyPiece p, int xO, int yO, int xD, int yD){
        switch (segueTurno % 6) {
            case 0:
                p = new Rainha(this.id,1,this.equipa,this.alcunha,segueTurno,0,0,0,0);
                return p.jogada(p, xO, yO, xD, yD);
            case 1:
                p = new PoneiMagico(this.id,2,this.equipa,this.alcunha,this.segueTurno,0,0,0,0);
                return p.jogada(p, xO, yO, xD, yD);
            case 2:
                p = new PadreDaVila(this.id,3,this.equipa,this.alcunha,this.segueTurno,0,0,0,0);
                return p.jogada(p, xO, yO, xD, yD);
            case 3:
                p = new TorreHor(this.id,4,this.equipa,this.alcunha,this.segueTurno,0,0,0,0);
                return p.jogada(p, xO, yO, xD, yD);
            case 4:
                p = new TorreVert(this.id,5,this.equipa,this.alcunha,this.segueTurno,0,0,0,0);
                return p.jogada(p, xO, yO, xD, yD);
            case 5:
                p = new Lebre(this.id,6,this.equipa,this.alcunha,this.segueTurno,0,0,0,0);
                return p.jogada(p, xO, yO, xD, yD);
        }
        return false;
    }

    public List<String> sugestao(List<CrazyPiece> pecas, int xO, int yO, int dimensao) {
        List<String> obterSugestao = new ArrayList<>();
        CrazyPiece p;
        for(CrazyPiece peca : pecas){
            if(peca.getX()==xO && peca.getY()==yO){
                p=peca;
            }
        }
        switch (segueTurno%6) {
            case 0:
                p= new Rainha(this.id,1,this.equipa,this.alcunha,segueTurno);
                return p.sugestao(pecas,xO,yO,dimensao);
            case 1:
                p= new PoneiMagico(this.id,2,this.equipa,this.alcunha,segueTurno);
                return p.sugestao(pecas,xO,yO,dimensao);
            case 2:
                p= new PadreDaVila(this.id,3,this.equipa,this.alcunha,segueTurno);
                return p.sugestao(pecas,xO,yO,dimensao);
            case 3:
                p= new TorreHor(this.id,4,this.equipa,this.alcunha,segueTurno);
                return p.sugestao(pecas,xO,yO,dimensao);
            case 4:
                p = new TorreVert(this.id,5,this.equipa,this.alcunha,segueTurno);
                return p.sugestao(pecas,xO,yO,dimensao);
            case 5:
                p= new Lebre(this.id,6,this.equipa,this.alcunha,segueTurno);
                return p.sugestao(pecas,xO,yO,dimensao);
        }
        return obterSugestao;
    }

    public String getNomeTurnoPeca(int turno){

        switch(turno%6){
            case 0:
                return "Rainha";
            case 1:
                return "Ponei Magico";
            case 2:
                return "Padre da Vila";
            case 3:
                return "TorreH";
            case 4:
                return "TorreV";
            case 5:
                return "Lebre";
        }
        return null;
    }

    @Override
    public String toString() {   // DONE
        String pecaJoker= getNomeTurnoPeca(segueTurno);
        String mensagem;
        if(captura == false){

            mensagem = id + " | " + "Joker/" + pecaJoker + " | " + valorRelativo + " | " + equipa + " | " + alcunha + " @ (" + x + ", " + y + ")";

        }else{

            mensagem = id + " | " + getNomeIdPeca(idTipoPeca) + " | " + valorRelativo + " | " + equipa + " | " + alcunha + " @ (n/a)";

        }
        return mensagem;
    }
}



