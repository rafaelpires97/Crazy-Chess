package pt.ulusofona.lp2.crazyChess;

import java.util.ArrayList;
import java.util.List;

import static pt.ulusofona.lp2.crazyChess.Simulador.*;

public class PoneiMagico  extends CrazyPiece {
    int segueTurno;
    PoneiMagico(int id, int idTipoPeca, int equipa, String alcunha, int segueTurno,int numeroCapturas,int numeroPontos,int numeroJogadasInvalidasNaquelaPeca,int numeroJogadasValidasNaquelaPeca){
        super(id,idTipoPeca,equipa,alcunha,numeroCapturas,numeroPontos,numeroJogadasInvalidasNaquelaPeca,numeroJogadasValidasNaquelaPeca);
        valorRelativo=5;
        this.segueTurno = segueTurno;
    }

    PoneiMagico(int id, int idTipoPeca, int equipa, String alcunha, int segueTurno){
        super(id,idTipoPeca,equipa,alcunha);
        valorRelativo=5;
        this.segueTurno =segueTurno;
    }

    public String getImagePNG(){
        if(getEquipa()==10){
            return "ponei_magico_black.png";
        }else{
            return "ponei_magico_white.png";
        }
    }

    @Override
    public boolean movimentoHorizontal(int pEsq, int pDir, int y){   //da esquerda para a direita
        pEsq +=1;
        while (pEsq<pDir) {
            if (getIDTipoPecaJogada(pEsq, y) == 0) {
                return false;
            }
            pEsq++;
        }
        return true;
    }

    @Override
    public boolean movimentoVertical(int pCima, int pBaixo, int x ){     //de cima para baixo
        pCima +=1;
        while (pCima<pBaixo) {
            if (getIDTipoPecaJogada(x, pCima) == 0) {
                return false;
            }
            pCima++;
        }
        return true;
    }

    public boolean podeAndar(int xO, int yO, int xD, int yD){
        boolean teste;
        if(xO>xD && yO>yD){
            teste= ((movimentoHorizontal(xD-1,xO,yO) && movimentoVertical(yD,yO+1,xD)) || (movimentoVertical(yD-1,yO,xO) && movimentoHorizontal(xD,xO+1,yD)));    //PARECE TAR BEM
            return teste;
        }else if(xO<xD && yO>yD){
            teste = ((movimentoHorizontal(xO,xD+1,yO) && movimentoVertical(yD,yO+1,xD)) ||  (movimentoVertical(yD-1,yO,xO) && movimentoHorizontal(xO-1,xD,yD)));  //SAME
            return teste;
        }else if(xO>xD && yO<yD){
            teste = ((movimentoHorizontal(xD-1,xO,yO) && movimentoVertical(yO-1,yD,xD)) ||  (movimentoVertical(yO,yD+1,xO) && movimentoHorizontal(xD,xO+1,yD))); //SAME
            return teste;
        }else if(xO<xD && yO<yD){
            teste = ((movimentoHorizontal(xO,xD+1,yO) && movimentoVertical(yO-1,yD,xD)) ||  (movimentoVertical(yO,yD+1,xO) && movimentoHorizontal(xO-1,xD,yD)));        //LOOKS DONE
            return teste;
        }
        return false;
    }

    @Override
    public boolean jogada(CrazyPiece p, int xO, int yO, int xD, int yD){
        for(CrazyPiece pecaComida: pecas){
            if(Math.abs(xO-xD)==2 && Math.abs(xO-xD)==Math.abs(yO-yD)){

                if(pecaComida.getX()==xD && pecaComida.getY()==yD && pecaComida.getIdTipoPeca()!=1 && pecaComida.getEquipa()!= p.getEquipa()){//peca come peca

                    if (podeAndar(xO,yO,xD,yD)){

                        JogadaAnterior estaJogada=new JogadaAnterior(getIDPeca(xO,yO),xO,yO, getIDPeca(xD,yD),xD,yD,segueTurno,0);
                        jogadaAnterior.add(estaJogada);
                        p.setX(xD);
                        p.setY(yD);
                        if(pecaComida.getEquipa()==20){
                            capturaPreta++;
                        }else{
                            capturaBranca++;;
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
                                segueQtdPecasBrancas--;
                            }else{
                                segueQtdPecasBrancas--;
                            }
                        }
                        p.setNumeroJogadasValidasNaquelaPeca(p.getNumeroJogadasValidasNaquelaPeca()+1);
                        p.setNumeroPontos(p.getNumeroPontos()+pecaComida.getValorRelativo());
                        p.setNumeroCapturas(p.getNumeroCapturas()+1);
                        return true;
                    }
                }else if(getIDPeca(xD,yD)==0){//pode andar mas nao come peca
                    if(podeAndar(xO,yO,xD,yD)){
                        turnoSemCaptura++;
                        JogadaAnterior estaJogada=new JogadaAnterior(getIDPeca(xO,yO),xO,yO, getIDPeca(xD,yD),xD,yD,segueTurno,pecaComida.getValorRelativo());
                        jogadaAnterior.add(estaJogada);;
                        p.setNumeroJogadasValidasNaquelaPeca(p.getNumeroJogadasValidasNaquelaPeca()+1);
                        p.setX(xD);
                        p.setY(yD);
                        return true;
                    }
                }
            }
        }
        p.setNumeroJogadasValidasNaquelaPeca(p.getNumeroJogadasInvalidasNaquelaPeca()+1);
        return false;
    }

    public List<String> sugestao(List<CrazyPiece> pecas, int xO, int yO, int dimensao) {
        List<String> obterSugestao = new ArrayList<>();
        if(x+2 < dimensao && y+2 < dimensao) {
            obterSugestao.add((x+2) + " , " + (y+2));
        }
        if (x-1 >= 0 && y-1 >= 0 ) {
            obterSugestao.add((x-1) + " , " + (y-1));
        }
        if (x-1 >= 0 && y+1 < dimensao) {
            obterSugestao.add((x-1) + " , " + (y+1));
        }
        if (x+1 < dimensao && y-1 >= 0) {
            obterSugestao.add((x+1) + " , " + (y-1));
        }
        //remove sugestao se tiver outra peça no mesmo sitio
        for(int count = 0; count < obterSugestao.size(); count++) {
            for (CrazyPiece sugestaoPeca : pecas) {
                if (equipa == sugestaoPeca.getEquipa() && obterSugestao.get(count).equals(sugestaoPeca.getX()) && obterSugestao.get(count).equals(sugestaoPeca.getY())) {
                    obterSugestao.remove(count);
                    count--;
                }
            }
        }
        return obterSugestao;
    }
}
