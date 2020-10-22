package pt.ulusofona.lp2.crazyChess;


import java.util.ArrayList;
import java.util.List;

import static pt.ulusofona.lp2.crazyChess.Simulador.*;

public class Lebre extends CrazyPiece {

    int segueTurno;

    Lebre(int id, int idTipoPeca, int equipa, String alcunha, int segueTurno,int numeroCapturas,int numeroPontos,int numeroJogadasInvalidasNaquelaPeca,int numeroJogadasValidasNaquelaPeca){
        super(id,idTipoPeca,equipa,alcunha,numeroCapturas,numeroPontos,numeroJogadasInvalidasNaquelaPeca,numeroJogadasValidasNaquelaPeca);
        valorRelativo=2;
        this.segueTurno = segueTurno;
    }

    Lebre(int id, int idTipoPeca, int equipa, String alcunha, int segueTurno){
        super(id,idTipoPeca,equipa,alcunha);
        valorRelativo=2;
        this.segueTurno =segueTurno;
    }

    public String getImagePNG(){
        if(getEquipa()==10){
            return "lebre_black.png";
        }else{
            return "lebre_white.png";
        }
    }
    @Override
    public boolean jogada(CrazyPiece p, int xO, int yO, int xD, int yD){
        for(CrazyPiece pecaComida: pecas){
            if(Math.abs(xO-xD)==1 && Math.abs(yO-yD)==1 && p.getEquipa()==10 && Math.abs(xO-xD)==Math.abs(yO-yD)){

                if(pecaComida.getX()==xD && pecaComida.getY()==yD && pecaComida.getEquipa()!= p.getEquipa()){      //peca come peca

                    JogadaAnterior estaJogada=new JogadaAnterior(getIDPeca(xO,yO),xO,yO, getIDPeca(xD,yD),xD,yD,segueTurno,0);
                    jogadaAnterior.add(estaJogada);
                    p.setX(xD);
                    p.setY(yD);
                    if(pecaComida.getEquipa()==20){
                        capturaPreta++;
                    }else{
                        capturaBranca++;
                    }
                    turnoSemCaptura=0;
                    pecaComida.setY(-1);   //peca Foi comida
                    pecaComida.setCaptura(true);
                    if(pecaComida.getEquipa()==10){
                        if(pecaComida.getIdTipoPeca()==0) {
                            segueReisPretos--;
                            segueQtdPecasPretas--;
                        }else{
                            segueQtdPecasPretas--;
                        }
                    }else{
                        if(pecaComida.getIdTipoPeca()==0) {
                            segueReisBrancos--;
                            segueReisBrancos--;
                        }else{
                            segueReisBrancos--;
                        }
                    }
                    p.setNumeroJogadasValidasNaquelaPeca(p.getNumeroJogadasValidasNaquelaPeca()+1);
                    p.setNumeroPontos(p.getNumeroPontos()+pecaComida.getValorRelativo());
                    p.setNumeroCapturas(p.getNumeroCapturas()+1);
                    return true;
                }else if(pecaComida.getIdTipoPeca()==0) {    //pode andar mas nao come peca
                    turnoSemCaptura++;
                    JogadaAnterior estaJogada = new JogadaAnterior(getIDPeca(xO, yO), xO, yO, getIDPeca(xD, yD), xD, yD, segueTurno, pecaComida.getValorRelativo());
                    jogadaAnterior.add(estaJogada);//pode andar mas nao come peca
                    p.setNumeroJogadasValidasNaquelaPeca(p.getNumeroJogadasValidasNaquelaPeca()+1);
                    p.setX(xD);
                    p.setY(yD);
                    return true;
                }
            }
        }
        p.setNumeroJogadasValidasNaquelaPeca(p.getNumeroJogadasInvalidasNaquelaPeca()+1);
        return false;
    }

    public List<String> sugestao(List<CrazyPiece> pecas, int xO, int yO, int dimensao) {
        List<String> obterSugestao = new ArrayList<>();
        if(segueTurno %2 != 0) {  // A lebre só joga em turnos pares
            return obterSugestao;
        }
        if(x+1 < dimensao && y+1 < dimensao) {
            obterSugestao.add((x+1) + ", " + (y+1));
        }
        if (x-1 >= 0 && y-1 >= 0 ) {
            obterSugestao.add((x-1) + ", " + (y-1));
        }
        if (x-1 >= 0 && y+1 < dimensao) {
            obterSugestao.add((x-1) + ", " + (y+1));
        }
        if (x+1 < dimensao && y-1 >= 0) {
            obterSugestao.add((x+1) + ", " + (y-1));
        }
        //remove sugestao se tiver outra peça no mesmo sitio

        for(CrazyPiece sugestaoPeca : pecas) {
            for (int count=0; count < obterSugestao.size(); count++) {
                if (equipa == sugestaoPeca.getEquipa() && obterSugestao.get(count).equals(sugestaoPeca.getX() + ", " + sugestaoPeca.getY())) {
                    obterSugestao.remove(count);
                    count=0;
                }
            }
        }
        return obterSugestao;
    }
}
