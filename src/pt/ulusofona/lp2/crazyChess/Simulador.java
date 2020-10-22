package pt.ulusofona.lp2.crazyChess;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;

import static java.util.stream.Collectors.toList;

public class Simulador {

    int dimensao; //
    int pecasNoTabuleiro;//
    static ArrayList<CrazyPiece> pecas = null;
    int quantidadePecas; //
    String resultado= "";
    int equipaAJogar=10; //
    static int segueEquipaAJogar; //
    int turno=0; //
    static int segueTurno=0; //
    static int turnoSemCaptura = 0; //
    //Pretas
    int quantidadePretas; //
    static int capturaPreta; //
    int validoPreta; //
    int invalidoPreta; //
    //Brancas
    int quantidadeBrancas;
    static int capturaBranca;
    int validoBranca;
    int invalidoBranca ;
    //
    static ArrayList<JogadaAnterior> jogadaAnterior = new ArrayList<>();
    //
    int quantidadeDeReisPretos;
    int quantidadeDeReisBrancos;
    static int segueReisPretos;
    static int segueReisBrancos;
    static int segueQtdPecasPretas;
    static int segueQtdPecasBrancas;


    public void iniciaJogo(File ficheiroInicial) throws InvalidSimulatorInputException, IOException { // IT'S OKEY NOW


        pecas = new ArrayList<>(); //Mais vale inciar no inicio pois nao ha erros caso inserimos outro ficheiro.txt
        dimensao = 0;
        pecasNoTabuleiro = 0;
        quantidadePecas = 0;
        quantidadePretas = 0;
        quantidadeBrancas = 0;
        quantidadeDeReisPretos=0;
        quantidadeDeReisBrancos=0;
        turno=0;
        capturaBranca=0;
        capturaPreta=0;
        validoPreta=0;
        validoBranca=0;
        invalidoBranca=0;
        invalidoPreta=0;


        try {
            Scanner leitorFicheiro = new Scanner(ficheiroInicial);
            int numLinha=1;
            int count = 0;
            int count1 = 0;
            int coluna = 0;

            while (leitorFicheiro.hasNextLine()) { //Percorre enquanto houver linha do ficheiro
                String linha = leitorFicheiro.nextLine();

                if (count == 0){
                    if (Integer.parseInt(linha)>3 && Integer.parseInt(linha)<13) {
                        dimensao = Integer.parseInt(linha);
                        count++;
                    }
                } else if (count == 1) {
                    if (Integer.parseInt(linha) < (dimensao * dimensao)) {
                        pecasNoTabuleiro = Integer.parseInt(linha);
                        count++;
                    }
                } else if (count1 < pecasNoTabuleiro) { // Para criar as peças com alcunha

                    String dados[] = linha.split(":");
                    if(dados.length!=4){
                        throw new InvalidSimulatorInputException(numLinha,4,dados.length);
                    }
                    int id = Integer.parseInt(dados[0]);
                    int idTipoPeca = Integer.parseInt(dados[1]);
                    int equipa = Integer.parseInt(dados[2]);
                    String alcunha = dados[3];
                    if(id>=1) {
                        switch (idTipoPeca) {
                            case 0:
                                CrazyPiece rei = new Rei(id, idTipoPeca, equipa, alcunha,segueTurno,0,0,0,0);
                                pecas.add(rei);
                                break;
                            case 1:
                                CrazyPiece rainha = new Rainha(id, idTipoPeca, equipa, alcunha,segueTurno,0,0,0,0);
                                pecas.add(rainha);
                                break;
                            case 2:
                                CrazyPiece poneiMagico = new PoneiMagico(id, idTipoPeca, equipa, alcunha,segueTurno,0,0,0,0);
                                pecas.add(poneiMagico);
                                break;

                            case 3:
                                CrazyPiece padreDaVila = new PadreDaVila(id, idTipoPeca, equipa, alcunha,segueTurno,0,0,0,0);
                                pecas.add(padreDaVila);
                                break;
                            case 4:
                                CrazyPiece torreHor = new TorreHor(id, idTipoPeca, equipa, alcunha,segueTurno,0,0,0,0);
                                pecas.add(torreHor);
                                break;
                            case 5:
                                CrazyPiece torreVert = new TorreVert(id, idTipoPeca, equipa, alcunha,segueTurno,0,0,0,0);
                                pecas.add(torreVert);
                                break;
                            case 6:
                                CrazyPiece lebre = new Lebre(id, idTipoPeca, equipa, alcunha,segueTurno,0,0,0,0);
                                pecas.add(lebre);
                                break;
                            case 7:
                                CrazyPiece joker = new Joker(id, idTipoPeca, equipa, alcunha,segueTurno,0,0,0,0);
                                pecas.add(joker);
                                break;
                        }
                        quantidadePecas++;
                        count1++;
                    }
                } else {
                    String dados[] = linha.split(":");
                        if(dados.length!=dimensao){
                            throw new InvalidSimulatorInputException(numLinha,dimensao,dados.length);
                        }
                        for (int linhas = 0; linhas < dimensao; linhas++) { // Associar as coordenadas às peças,  coluna = x  , linha = y

                            if (Integer.parseInt(dados[linhas]) != 0) {
                               for (int j = 0; j < pecas.size(); j++) {
                                    CrazyPiece pecaMestre;
                                    pecaMestre = pecas.get(j);
                                    int ajuda = Integer.parseInt(dados[linhas]);
                                    if (pecaMestre.getId() == ajuda) {
                                       pecaMestre.setX(linhas);
                                       pecaMestre.setY(coluna);
                                        if (pecaMestre.getEquipa() == 10) { // 10 = pretas
                                            quantidadePretas++;
                                        } else {
                                            quantidadeBrancas++;
                                        }
                                        break;
                                    }
                               }
                            }

                        if(Integer.parseInt(dados[0]) ==10 || Integer.parseInt(dados[0]) ==20){
                            equipaAJogar = Integer.parseInt(dados[0]);
                            validoPreta = Integer.parseInt(dados[1]);
                            capturaPreta = Integer.parseInt(dados[2]);
                            invalidoPreta = Integer.parseInt(dados[3]);
                            validoBranca = Integer.parseInt(dados[4]);
                            capturaBranca = Integer.parseInt(dados[5]);
                            String invalidoBrancaETurnoSemCaptura[] = dados[6].split(",");
                                invalidoBranca=Integer.parseInt(invalidoBrancaETurnoSemCaptura[0]);
                                turnoSemCaptura= Integer.parseInt(invalidoBrancaETurnoSemCaptura[1]);
                            turno = validoPreta + validoBranca;
                        }
                    }
                    coluna++;
                }
                numLinha++;
            }
            contaReisPorEquipa();
            segueReisPretos=quantidadeDeReisPretos;
            segueReisBrancos=quantidadeDeReisBrancos;
            segueQtdPecasBrancas=quantidadeBrancas;
            segueQtdPecasPretas=quantidadePretas;
            leitorFicheiro.close();

        } catch (FileNotFoundException exception) {
            throw new IOException();
        }

    }

    public int getTamanhoTabuleiro() { //DONE
        return dimensao;
    }

    public void contaReisPorEquipa(){
        for(CrazyPiece r : pecas){
            if(r.getIdTipoPeca()==0){
                if (r.getEquipa()==10){
                    quantidadeDeReisPretos++;
                }else{
                    quantidadeDeReisBrancos++;
                }

            }
        }
    }

    public int equipaDaPeca(int x, int y) {
        int pecaEquipa = 0;
        for (CrazyPiece p : pecas) {
            if (p.getX() == x && p.getY() == y) {
                return p.getEquipa();
            }
        }
        return pecaEquipa;
    }

    public boolean fazJogada(int xO, int yO, int xD, int yD) {
        for (CrazyPiece p : pecas) {
            if (xO == p.getX() && yO == p.getY() && p.getEquipa() == equipaAJogar) {
                System.out.println(pecas);
               return p.jogada(p, xO, yO, xD, yD);

            }
        }
        return false;
    }

    public void mudaEquipa() {
        if (equipaAJogar == 10) {
            equipaAJogar = 20;
        } else if (equipaAJogar == 20) {
            equipaAJogar = 10;
        }
        for (CrazyPiece p : pecas) {

            p.mudaTurno(turno);
        }
        turno++;
        segueTurno=turno;
        segueEquipaAJogar=equipaAJogar;
        quantidadeDeReisPretos=segueReisPretos;
        quantidadeDeReisBrancos= segueReisBrancos;
        quantidadeBrancas= segueQtdPecasBrancas;
        quantidadePretas= segueQtdPecasPretas;
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

    public boolean processaJogada(int xO, int yO, int xD, int yD) {
        boolean turnoValido= false;
        if ((xO != xD || yO != yD)
                && (xO >= 0 && xO < dimensao && yO >= 0 && yO < dimensao && xD >= 0 && xD < dimensao && yD >= 0 && yD < dimensao)
                && (getIDPeca(xO, yO) != 0)) {
            System.out.println("-+" + segueTurno);
            boolean jogada = fazJogada(xO, yO, xD, yD);
            System.out.println(segueTurno);
            if (jogada && equipaAJogar == 10) {
                validoPreta++;
                mudaEquipa();
                turnoValido=true;
            } else if (jogada && equipaAJogar == 20) {
                validoBranca++;
                mudaEquipa();
                turnoValido=true;
            } else if (!jogada && equipaAJogar == 20) {
                invalidoBranca++;
                turnoValido=false;
            } else if (!jogada && equipaAJogar == 10) {
                invalidoPreta++;
                turnoValido=false;
            }
        }else{
            if (equipaAJogar == 20) {
                invalidoBranca++;
                turnoValido=false;
            }else {
                invalidoPreta++;
                turnoValido=false;
            }
        }
        for (CrazyPiece peca: pecas) {
            System.out.println(peca);
        }
        return turnoValido;
    }

    public List<CrazyPiece> getPecasMalucas() {   //DONE
        return pecas;
    }

    public boolean jogoTerminado() {
        if (quantidadeDeReisPretos > 0 && quantidadeDeReisBrancos == 0 ) {
            resultado = "VENCERAM AS PRETAS";
            return true;
        }
        if (quantidadeDeReisPretos == 0 && quantidadeDeReisBrancos > 0) {
            resultado = "VENCERAM AS BRANCAS";
            return true;
        }
        if (quantidadeDeReisBrancos == 1 && quantidadeDeReisPretos == 1 && quantidadePretas ==1 && quantidadeBrancas==1) {
            resultado = "EMPATE";
            return true;
        }
        if (capturaBranca + capturaPreta > 0 && turnoSemCaptura == 10) {
            resultado = "EMPATE";
            return true;
        }
        return false;
    }

    public List<String> getAutores() {  //DONE
        List<String> autores = new ArrayList<>();
        autores.add("Rafael Reto 21702113");
        autores.add("Constantino Raptis 21702363");

        return autores;
    }

    public List<String> getResultados() {

        List<String> resultados = new ArrayList<>();
        resultados.add("JOGO DE CRAZY CHESS");
        resultados.add("Resultado: " + resultado);
        resultados.add("---");
        resultados.add("Equipa das Pretas");
        resultados.add(" Capturas: " + capturaPreta);
        resultados.add(" Jogadas válidas: " + validoPreta);
        resultados.add(" Tentativas inválidas: " + invalidoPreta);
        resultados.add("Equipa das Brancas");
        resultados.add(" Capturas: " + capturaBranca);
        resultados.add(" Jogadas válidas: " + validoBranca);
        resultados.add(" Tentativas inválidas: " + invalidoBranca);
        return resultados;
    }

    public int getTurno() {
        return segueTurno;
    }

    public int getIDEquipaAJogar() {
        return equipaAJogar;
    }

    public void setDimensao(int dimensao) {
        this.dimensao = dimensao;
    }

    public List<Comparable> obterSugestoesJogada(int xO, int yO) {
        List<String> obterSugestao = new ArrayList<>();
        for (CrazyPiece p : pecas) {
            if (p.getX() == xO && p.getY() == yO && p.getEquipa() == getIDEquipaAJogar()) {
                obterSugestao = p.sugestao(pecas, xO, yO, dimensao);
            }
        }
        if(obterSugestao.size()==0){
            obterSugestao.add("Pedido inválido");
        }
        return null;
    }

    public void anularJogadaAnterior() {
        for (JogadaAnterior j : jogadaAnterior) {
            if (j.getTurnoDaJogada() == turno - 1) {
                if (j.getiDPecaPitada() == 0) {
                    for (CrazyPiece p : pecas) {
                        if (p.getId() == j.getiDpecaMovimentada() && p.getX() == j.getxD() && p.getY() == j.getyD()) {
                            p.setX(j.getxO());
                            p.setY(j.getyO());
                            p.setNumeroJogadasValidasNaquelaPeca(p.getNumeroJogadasValidasNaquelaPeca()-1);
                            if (p.getEquipa() == 10) {
                                validoPreta--;
                            } else {
                                validoBranca--;
                            }
                            mudaEquipa();
                            turno -= 2;
                            segueTurno=turno;
                            break;
                        }
                    }
                } else {
                    for (CrazyPiece p : pecas) {
                        if (p.getId() == j.getiDpecaMovimentada() && p.getX() == j.getxD() && p.getY() == j.getyD()) {
                            p.setX(j.getxO());
                            p.setY(j.getyO());
                            p.setNumeroJogadasValidasNaquelaPeca(p.getNumeroJogadasValidasNaquelaPeca()-1);
                            p.setNumeroCapturas(p.getNumeroCapturas()-1);
                            p.setNumeroPontos(p.getNumeroPontos()-p.getValorRelativo());
                        }
                        if (p.getId() == j.getiDPecaPitada() && p.getX() == j.getxD() && p.getY() == -1) {
                            p.setY(j.getyD());

                            if (p.getEquipa() == 10) {
                                capturaPreta--;
                                validoPreta--;
                            } else {
                                capturaBranca--;
                                validoBranca--;
                            }
                            mudaEquipa();
                            turno -= 2;
                            segueTurno=turno;
                            break;
                        }
                    }
                }
            }
        }

    }

    public Map<String, List<String>> getEstatisticas(){


       Map<String, List<String>> estatisticasFinais = new HashMap();
        List<String> top5capturasString= new ArrayList<>();
        List<String> top5pontosString= new ArrayList<>();
        List<String> pecasMais5CapturasString= new ArrayList<>();
        List<String> pecasMaisBaralhadasString= new ArrayList<>();
        List<String> tiposPecaCapturadosString= new ArrayList<>();


       top5capturasString= pecas.stream()
                .sorted((p1,p2)-> p2.getNumeroCapturas() - p1.getNumeroCapturas())
                .limit(5)
                .map((peca) -> peca.getEquipa() + ":" + peca.getAlcunha() + ":" + peca.getNumeroPontos() + ":" + peca.getNumeroCapturas())
                .collect(toList());



         top5pontosString = pecas.stream()
               .sorted((p1,p2) -> p2.getNumeroPontos() - p1.getNumeroPontos())
               .map((peca) -> peca.getEquipa() + ":" + peca.getAlcunha() + ":" + peca.getNumeroPontos() + ":" + peca.getNumeroCapturas())
               .limit(5)
               .collect(toList());

       pecasMais5CapturasString = pecas.stream()
                .filter((p) -> p.getNumeroCapturas() > 5)
                .map((peca) -> peca.getEquipa() + ":" + peca.getAlcunha() + ":" + peca.getNumeroPontos() + ":" + peca.getNumeroCapturas())
                .collect(toList());


         pecasMaisBaralhadasString = pecas.stream()
                .filter((p)->p.getNumeroCapturas()>5)
                .sorted((p1, p2) -> Double.compare(p2.getNumeroJogadasInvalidasNaquelaPeca()/(turno),p1.getNumeroJogadasInvalidasNaquelaPeca()/(turno)))
                .map((peca) -> peca.getEquipa() + ":" + peca.getAlcunha() + ":" + peca.getNumeroPontos() + ":" + peca.getNumeroCapturas())
                .limit(3)
                .collect(toList());


       tiposPecaCapturadosString = pecas.stream()
                .filter((p) -> p.getNumeroCapturas() > 0)
                .sorted((p1,p2) -> p2.getNumeroCapturas() - p1.getNumeroCapturas())
                .map((peca) -> peca.getId() + ":" + peca.getNumeroCapturas())
                .collect(toList());


        estatisticasFinais.put("top5Capturas", top5capturasString);
        estatisticasFinais.put("top5Pontos",top5pontosString );
        estatisticasFinais.put("pecasMais5Capturas", pecasMais5CapturasString);
        estatisticasFinais.put("3PecasMaisBaralhadas",pecasMaisBaralhadasString );
        estatisticasFinais.put("tiposPecaCapturados", tiposPecaCapturadosString);
        return estatisticasFinais;
    }

    public boolean gravarJogo(File ficheiroDestino) {

        String newLine = System.getProperty("line.separator");
        int tabuleiroX = 0;
        int linha = 1; // Linhas do ficheiro

        try {

            FileWriter writer = new FileWriter(ficheiroDestino);

            if (linha == 1) { // escreve dimensao do tabuleiro
                writer.write(dimensao + "");
                writer.write(newLine);
                linha++;
            }
            if(linha == 2) {// escreve pecas do tabuleiro
                writer.write(pecasNoTabuleiro + "");
                writer.write(newLine);
                linha++;
            }
            while (linha >= 3 && linha <= pecasNoTabuleiro + 2) { // escreve tudo a cerca das peças do tabuleiro
                for (CrazyPiece p : pecas) {
                    writer.write(p.getId() + ":" + p.getIdTipoPeca() + ":" + p.getEquipa() + ":" + p.getAlcunha());
                    writer.write(newLine);
                    linha++;
                }
            }
            while (linha >= pecasNoTabuleiro + 3 && linha <= pecasNoTabuleiro + 2 + dimensao) { //coordenadas das pecas

                for (int coluna = 0; coluna < dimensao; coluna++){
                    int count = 0;
                    for (CrazyPiece p : pecas){
                        if (p.getX() == coluna && p.getY()== tabuleiroX){
                            if (coluna == dimensao - 1){
                                writer.write(p.getId() + "");
                            }else{
                                writer.write(p.getId() + ":");
                            }
                        }else{
                            count++;
                        }
                    }
                    if (count == pecas.size()){
                        if (coluna == dimensao - 1){
                            writer.write(Integer.toString(0));
                        }else{
                            writer.write("0:");
                        }
                    }
                }
                writer.write(newLine);
                tabuleiroX++;
                linha++;
            }
            String invalidoBrancaErondasSemcaptura= invalidoBranca+ ","+turnoSemCaptura;
            if (linha == pecasNoTabuleiro + 3 + dimensao) { //  equipa a jogar e resultados
                writer.write(equipaAJogar + ":" + validoPreta + ":" + capturaPreta + ":" + invalidoPreta + ":" + validoBranca + ":" + capturaBranca + ":" + invalidoBrancaErondasSemcaptura);
            }
            writer.close();
        } catch (IOException e) {
            System.out.println("Ocorreu um erro.");
            return false;
        }
        return true;
    }
}

   /*

    public void setPecas ( int id, int idTipoPeca, int equipa, String alcunha, int x, int y, boolean captura){

       pecas.add( new CrazyPiece(id, idTipoPeca, equipa, alcunha, x, y, captura));

    }
*/