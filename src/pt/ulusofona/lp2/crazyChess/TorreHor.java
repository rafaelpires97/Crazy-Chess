package pt.ulusofona.lp2.crazyChess;

import java.util.ArrayList;
import java.util.List;

import static pt.ulusofona.lp2.crazyChess.Simulador.*;

public class TorreHor  extends CrazyPiece {
    int segueTurno;
    TorreHor(int id, int idTipoPeca, int equipa, String alcunha, int segueTurno,int numeroCapturas,int numeroPontos,int numeroJogadasInvalidasNaquelaPeca,int numeroJogadasValidasNaquelaPeca){
        super(id,idTipoPeca,equipa,alcunha,numeroCapturas,numeroPontos,numeroJogadasInvalidasNaquelaPeca,numeroJogadasValidasNaquelaPeca);
        valorRelativo=3;
        this.segueTurno = segueTurno;
    }

    TorreHor(int id, int idTipoPeca, int equipa, String alcunha, int segueTurno){
        super(id,idTipoPeca,equipa,alcunha);
        valorRelativo=3;
        this.segueTurno =segueTurno;
    }



    public String getImagePNG(){
        if(getEquipa()==10){
            return "torre_h_black.png";
        }else{
            return "torre_h_white.png";
        }
    }

    private boolean podeAndar(int xO, int xD, int yO){
        if(xO<xD){
            return movimentoHorizontal(xO, xD, yO);
        }else if (xD<xO){
            return movimentoHorizontal(xD,xO,yO);
        }
        return false;
    }

    @Override
    public boolean jogada(CrazyPiece p, int xO, int yO, int xD, int yD) {
        for (CrazyPiece pecaComida : pecas) {
            if (Math.abs(yO - yD) == 0) {
                if (pecaComida.getX() == xD && pecaComida.getY() == yD && pecaComida.getEquipa() != p.getEquipa()) {//peca come peca
                    if (podeAndar(xO, xD, yO)) {
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
                    if (podeAndar(xO, xD, yO)) {
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
        for(int i=xO; 0<i;i++){
            if (x-i > 0 && y==yO){
                obterSugestao.add((x-i) + ", " + (y));
            }
        }
        for (int count=espacoDir; 0<count; count--) {
            if (x + count < dimensao && y == yO) {
                obterSugestao.add((x + count) + ", " + (y));
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
