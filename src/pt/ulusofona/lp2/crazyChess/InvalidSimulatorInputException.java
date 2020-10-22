package pt.ulusofona.lp2.crazyChess;

import java.io.IOException;

    public class InvalidSimulatorInputException extends IOException {

        int linhaErro;
        int x;
        int y;

        InvalidSimulatorInputException(int linhaErro){
            this.linhaErro = linhaErro;
        }

        InvalidSimulatorInputException(int linhaErro, int x, int y) {
            this.linhaErro = linhaErro;
            this.x = x;
            this.y = y;
        }


        public int getLinhaErro() {
            return linhaErro;
        }

        public String getDescricaoProblema() {
            if (x < y) {
                return "DADOS A MAIS (Esperava: " + x + " ; Obtive: " + y + ")";
            } else {
                return "DADOS A MENOS (Esperava: " + x + " ; Obtive: " + y + ")";
            }
        }
    }
