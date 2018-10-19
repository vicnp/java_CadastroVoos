/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model;

import java.util.ArrayList;

/**
 *
 * @author Victor
 */
public class Voo {
    String aeronave;
    public ArrayList<Passageiro> passageiros;
    int id;
    Double valor;
    int capacidade;
    
    public Voo(String aeronave, int id,Double valor) {
        this.aeronave = aeronave;
        this.passageiros = new ArrayList<>();
        this.id = id;
        this.valor = valor;
        
        if(aeronave.equals("Boing 777")){
            this.capacidade = 10;
        }else if(aeronave.equals("Boing 767")){
            this.capacidade = 5;
        }else if(aeronave.equals("Boing 737")){
            this.capacidade = 6;
        }
    }

    public int getCapacidade() {
        return capacidade;
    }
    
    public int N_passageiros(){
        return this.passageiros.size();
    }
    
    public String getAeronave() {
        return aeronave;
    }

    public void setAeronave(String aeronave) {
        this.aeronave = aeronave;
    }
    
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Double getValor() {
        return valor;
    }

    public void setValor(Double valor) {
        this.valor = valor;
    }
    
    public boolean addPassageiro(Passageiro e){
        if(aeronave.equals("Boing 777")&&this.passageiros.size()<=9){
            this.passageiros.add(e);
            return true;
        }else if(aeronave.equals("Boing 767")&&this.passageiros.size()<=4){
             this.passageiros.add(e);
             return true;
        }else if(aeronave.equals("Boing 737")&&this.passageiros.size()<=5){
            this.passageiros.add(e);
            return true;
        }
        return false;
    }

    @Override
    public String toString() {
        return "Voo{" + "aeronave=" + aeronave + ", passageiros=" + passageiros + ", id=" + id + ", valor=" + valor + ", capacidade=" + capacidade + '}';
    }
    
    
}
