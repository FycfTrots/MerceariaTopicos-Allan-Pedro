/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mercearia;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.sound.midi.SysexMessage;

/**
 *
 * @author Allan
 */
public class FakeBancoDados {
    
    private static Vector<Produto> produtos;
    
    //leitura das informacoes do arquivo excel
    private static void cargaArquivo(){
        
        //ajuste na criacao do vetor de produtos static
        if(produtos == null){
            produtos = new Vector<>();
        }else{
            produtos.clear();
        }
        
        File arquivoCsv = new File("C:\\Users\\Allan\\Downloads\\produtos.csv");
        
        try{
            //estrutura de leitura do arquivo
            FileReader marcaLeitura = new FileReader(arquivoCsv);//byte
            
            //usar toda a capacidade do nosso barramento
            BufferedReader bufLeitura = new BufferedReader(marcaLeitura);
            
            //**************ler cada linha***********************
            //lendo a primeira linha(cabeçalho) - descartar
            bufLeitura.readLine();
            
            //primeiro produto
            String linha = bufLeitura.readLine();
            
            while(linha != null){
                //linhas seguintes, até o final do arquivo
                
                //78564213;Carne Churrasco;1.26;178 (split)
                String infos[] = linha.split(";");
                
                int cod = Integer.parseInt(infos[0]);
                String nome = infos[1];
                double preco = Double.parseDouble(infos[2]);
                int quant = Integer.parseInt(infos[3]);
                
                //adicao dos produtos para o vetor dinamico
                produtos.add(new Produto(cod, nome, preco, quant));
                
                //lendo a proxima linha do arquivo
                linha = bufLeitura.readLine();
                
            }
         
            //liberando o arquivo para outros processos
            bufLeitura.close();
            
        }catch(FileNotFoundException ex){
              System.err.println("Arquivo espec. não existe");      
        }catch(IOException e){
            System.err.println("Arquivo corrompido");
        }
    }
    
    public static Produto consultaProdutoCod(int cod){
        //se o arquivo produto ainda nao foi carregado, precisamos carrega-lo
        if(produtos == null){
            cargaArquivo();
        }
        
        //busca pelo produto com o codigo especificado
        for(Produto prodI : produtos){              //for(int i=0; i<produtos.size(); i++)
            if(prodI.getCodigo() == cod){                   //if(produtos.get(i).getCodigo() == cod)
                return prodI;
            }
        }
        //aqui nao existe produto com codigo especificado como p
        return null;
    }
    
    public static void atualizaArquivo(){
        
        File arquivo = new File("C:\\Users\\Allan\\Downloads\\produtos.csv");
        
        try {
            FileWriter escritor = new FileWriter(arquivo);
            
            BufferedWriter bufEscrita = new BufferedWriter(escritor);
            
            for(int i = 0; i < produtos.size(); i++){
                bufEscrita.write(produtos.get(i) + "\n");
            }
            bufEscrita.flush();
            bufEscrita.close();
        } catch (IOException ex) {
            System.err.println("dispositivo com falha");
        }
    }
}
