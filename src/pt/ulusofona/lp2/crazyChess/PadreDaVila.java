package pt.ulusofona.lp2.crazyChess;

import java.util.ArrayList;
import java.util.List;

import static pt.ulusofona.lp2.crazyChess.Simulador.*;
import static pt.ulusofona.lp2.crazyChess.Simulador.capturaPreta;

public class PadreDaVila  extends CrazyPiece {
    int segueTurno;
    PadreDaVila(int id, int idTipoPeca, int equipa, String alcunha, int segueTurno,int numeroCapturas,int numeroPontos,int numeroJogadasInvalidasNaquelaPeca,int numeroJogadasValidasNaquelaPeca){
        super(id,idTipoPeca,equipa,alcunha,numeroCapturas,numeroPontos,numeroJogadasInvalidasNaquelaPeca,numeroJogadasValidasNaquelaPeca);
        valorRelativo=3;
        this.segueTurno = segueTurno;
    }

    PadreDaVila(int id, int idTipoPeca, int equipa, String alcunha, int segueTurno){
        super(id,idTipoPeca,equipa,alcunha);
        valorRelativo=3;
        this.segueTurno =segueTurno;
    }

    public String getImagePNG(){
        if(getEquipa()==10){
            return "padre_vila_black.png";
        }else{
            return "padre_vila_white.png";
        }
    }

    public boolean podeAndar(int xO, int yO, int xD, int yD){
        if(xO<xD && yO<yD){             // sentido cima/baixo e para a direita
            return movimentoDiagonalEsquerdaDireita(xO, xD, yO, yD);
        }else if(xO<xD && yO>yD){       // sentido baixo/cima e para a direita
            return movimentoDiagonalEsquerdaDireita(xD,xO,yD,yO);
        }else if(xO>xD && yO>yD){       // sentido baixo/cima e para a esquerda
            return movimentoDiagonalDireitaEsquerda(xD,xO,yD,yO);
        }else if(xO>xD && yO<yD) {       // sentido cima/baixo para a esquerda
            return movimentoDiagonalDireitaEsquerda(xO, xD, yO, yD);
        }
        return false;
    }



    @Override
    public boolean jogada(CrazyPiece p, int xO, int yO, int xD, int yD){
        for(CrazyPiece pecaComida: pecas){
            if(Math.abs(xO - xD) <= 3 && Math.abs(yO - yD) <= 3 && Math.abs(yO - yD)==Math.abs(xO - xD) ){

                if(pecaComida.getX()==xD && pecaComida.getY()==yD && pecaComida.getEquipa()!= p.getEquipa() && testeRainhaPerto(xD,yD)){//peca come peca

                    if (podeAndar(xO,yO,xD,yD)){
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
                }else if(getIDPeca(xD,yD)==0 && testeRainhaPerto(xD,yD)){//pode andar mas nao come peca
                    if(podeAndar(xO,yO,xD,yD)){
                        turnoSemCaptura++;
                        JogadaAnterior estaJogada=new JogadaAnterior(getIDPeca(xO,yO),xO,yO, getIDPeca(xD,yD),xD,yD,segueTurno,pecaComida.getValorRelativo());
                        jogadaAnterior.add(estaJogada);
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
        for(int count=3; 1<=count;count--){
            if(x+count < dimensao && y+count < dimensao) {
                obterSugestao.add((x+count) + ", " + (y+count));
            }
            if (x-count >= 0 && y-count >= 0 ) {
                obterSugestao.add((x-count) + ", " + (y-count));
            }
            if (x-count >= 0 && y+count < dimensao) {
                obterSugestao.add((x-count) + ", " + (y+count));
            }
            if (x+count < dimensao && y-count >= 0) {
                obterSugestao.add((x+count) + ", " + (y-count));
            }
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
