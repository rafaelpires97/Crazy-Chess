package pt.ulusofona.lp2.crazyChess;

import java.util.ArrayList;
import java.util.List;

import static pt.ulusofona.lp2.crazyChess.Simulador.*;

public class TorreVert  extends CrazyPiece {
    int segueTurno;
    TorreVert(int id, int idTipoPeca, int equipa, String alcunha, int segueTurno,int numeroCapturas,int numeroPontos,int numeroJogadasInvalidasNaquelaPeca,int numeroJogadasValidasNaquelaPeca) {
        super(id,idTipoPeca,equipa,alcunha,numeroCapturas,numeroPontos,numeroJogadasInvalidasNaquelaPeca,numeroJogadasValidasNaquelaPeca);
        valorRelativo=3;
        this.segueTurno = segueTurno;
    }

    TorreVert(int id, int idTipoPeca, int equipa, String alcunha, int segueTurno){
        super(id,idTipoPeca,equipa,alcunha);
        valorRelativo=3;
        this.segueTurno =segueTurno;
    }

    public String getImagePNG() {
        if (getEquipa() == 10) {
            return "torre_v_black.png";
        } else {
            return "torre_v_white.png";
        }
    }


    private boolean podeAndar(int yO, int yD, int xO) {
        if (yO < yD) {
           return movimentoVertical(yO,yD,xO);
        } else if (yD < yO) {
            return movimentoVertical(yD,yO,xO);
        }
        return false;
    }

    @Override
    public boolean jogada(CrazyPiece p, int xO, int yO, int xD, int yD) {
        if (Math.abs(xO - xD) == 0) {
        for (CrazyPiece pecaComida : pecas) {
                if (pecaComida.getX() == xD && pecaComida.getY() == yD && pecaComida.getEquipa() != p.getEquipa()) {      //peca come peca
                    if (podeAndar(yO, yD, xO)) {
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
                } else if (getIDPeca(xD, yD) == 0) {       //pode andar mas nao come peca
                    if (podeAndar(yO, yD, xO)) {
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
        int espacoEsq= xO;
        int espacoDir = dimensao-xO;
        for(int i=0; i<espacoEsq;i++){
            if (y-i > 0 && x==xO){
                obterSugestao.add((x) + ", " + (y-i));
            }
        }
        for (int count=espacoDir; 0<count; count--) {
            if (y + count < dimensao && x == xO) {
                obterSugestao.add((x + count) + ", " + (y + count));
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
