package pt.ulusofona.lp2.crazyChess;

import org.junit.Test;

import java.io.File;
import java.io.IOException;

import static junit.framework.TestCase.assertEquals;
import static junit.framework.TestCase.assertTrue;
import static org.junit.Assert.assertFalse;

public class TestesUnitarios {

     @Test
    public void teste1ProcessaJogada(){
        Simulador sim = new Simulador();
        sim.setDimensao(8);
        assertEquals(sim.dimensao,8);
    }

    @Test
    public void teste2Rei(){
         Simulador sim = new Simulador();
         sim.setDimensao(8);
         CrazyPiece rei = new Rei(1,0,10,"Rei",0);
         rei.setY(0);
         rei.setX(0);
         assertEquals(false,rei.jogada(rei, rei.getX(), rei.getY(), 5, 1));
    }

    @Test
    public void teste3Rainha(){
        Simulador sim = new Simulador();
        sim.setDimensao(8);
        CrazyPiece rainha = new Rainha(1,0,10,"Rei",0);
        rainha.setY(0);
        rainha.setX(0);
        assertEquals(false,rainha.jogada(rainha, rainha.getX(), rainha.getY(), 6, 1));
    }

    @Test
    public void teste4Padre(){
        Simulador sim = new Simulador();
        sim.setDimensao(8);
        CrazyPiece padre = new PadreDaVila(1,0,10,"Rei",0);
        padre.setY(0);
        padre.setX(0);
        assertEquals(false,padre.jogada(padre, padre.getX(), padre.getY(), 6, 0));
    }

    @Test
    public void teste5Ponei(){
        Simulador sim = new Simulador();
        sim.setDimensao(8);
        CrazyPiece ponei = new PoneiMagico(1,0,10,"Rei",0);
        ponei.setY(0);
        ponei.setX(0);
        assertEquals(false,ponei.jogada(ponei, ponei.getX(), ponei.getY(), 6, 0));
    }

    @Test
    public void teste6TorreH(){
        Simulador sim = new Simulador();
        sim.setDimensao(8);
        CrazyPiece torre = new TorreHor(1,0,10,"Rei",0);
        torre.setY(0);
        torre.setX(0);
        assertEquals(false,torre.jogada(torre, torre.getX(), torre.getY(), 0, 5));
    }

    @Test
    public void teste7TorreV(){
        Simulador sim = new Simulador();
        sim.setDimensao(8);
        CrazyPiece torre = new TorreVert(1,0,10,"Rei",0);
        torre.setY(0);
        torre.setX(0);
        assertEquals(false,torre.jogada(torre, torre.getX(), torre.getY(), 5, 0));
    }


    /*@Test
    public void testPadreDaVila() throws IOException {
         Simulador sim = new Simulador();
         File ficheiro = new File("input2.txt");
         sim.iniciaJogo(ficheiro);
         boolean resultado = sim.processaJogada(2,1,4,3);
         assertFalse(resultado);
    }*/




}