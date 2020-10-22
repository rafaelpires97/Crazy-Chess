package pt.ulusofona.lp2.crazyChess;

import java.util.ArrayList;
import java.util.List;

import static pt.ulusofona.lp2.crazyChess.Simulador.*;

public class Rei extends CrazyPiece {
    int segueTurno;
    Rei(int id, int idTipoPeca, int equipa, String alcunha, int segueTurno,int numeroCapturas,int numeroPontos,int numeroJogadasInvalidasNaquelaPeca,int numeroJogadasValidasNaquelaPeca){
        super(id,idTipoPeca,equipa,alcunha,numeroCapturas,numeroPontos,numeroJogadasInvalidasNaquelaPeca,numeroJogadasValidasNaquelaPeca);
        this.segueTurno = segueTurno;
        valorRelativo=1000;
    }

    Rei(int id, int idTipoPeca, int equipa, String alcunha, int segueTurno){
        super(id,idTipoPeca,equipa,alcunha);
        this.segueTurno =segueTurno;
    }

    public String getImagePNG(){
        if(getEquipa()==10){
            return null;
        }else{
            return "box-rocks-2.png";
        }
    }



    @Override
    public boolean jogada(CrazyPiece p, int xO, int yO, int xD, int yD) {
        for(CrazyPiece pecaComida: pecas) {
            if (Math.abs(xO - xD) <= 1 && Math.abs(yO - yD) <= 1) {
                if (pecaComida.getX() == xD && pecaComida.getY() == yD && pecaComida.getEquipa() != p.getEquipa()) {      //peca come peca
                    JogadaAnterior estaJogada = new JogadaAnterior(getIDPeca(xO, yO), xO, yO, getIDPeca(xD, yD), xD, yD, segueTurno,0);
                    jogadaAnterior.add(estaJogada);
                    p.setX(xD);
                    p.setY(yD);
                    if (pecaComida.getEquipa() == 20) {
                        capturaPreta++;
                    } else {
                        capturaBranca++;
                    }
                    turnoSemCaptura = 0;
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
                            segueQtdPecasBrancas--;
                        }else{
                            segueQtdPecasBrancas--;
                        }
                    }
                    p.setNumeroJogadasValidasNaquelaPeca(p.getNumeroJogadasValidasNaquelaPeca()+1);
                    p.setNumeroPontos(p.getNumeroPontos()+pecaComida.getValorRelativo());
                    p.setNumeroCapturas(p.getNumeroCapturas()+1);
                    return true;
                } else if (getIDPeca(xD, yD) == 0) {       //pode andar mas nao come peca
                    turnoSemCaptura++;
                    JogadaAnterior estaJogada = new JogadaAnterior(getIDPeca(xO, yO), xO, yO, getIDPeca(xD, yD), xD, yD, segueTurno,pecaComida.getValorRelativo());
                    jogadaAnterior.add(estaJogada);
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
        if (x+1 < dimensao && y==yO){
            obterSugestao.add((x+1) + ", " + (y));
        }
        if (y+1 < dimensao && x==xO){
            obterSugestao.add((x) + ", " + (y+1));
        }
        if (x-1 >= 0 && y==yO){
            obterSugestao.add((x-1) + ", " + (y));
        }
        if (y-1 > dimensao && x==xO){
            obterSugestao.add((x) + ", " + (y-1));
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
