/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Control;

import Model.Passageiro;
import Model.Voo;
import View.TelaCadastroPassageiro;
import View.TelaExibicao;
import View.TelaPrincipal;
import static View.TelaPrincipal.tabela;
import java.util.ArrayList;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;

/**
 *
 * @author Victor
 */
public class Controle {
    TelaPrincipal telaprincipal; //TEL PRINCIPAL
    TelaCadastroPassageiro telaCadastroPassageiro; //TELA DE CADASTRO DE PASSAGEIRO
    TelaExibicao telaExibicao; //TELA EXIBIR PASSAGEIROS
    
    DefaultTableModel dtmVoos; //MODELO PADRÃO DA TABELA DE VOOS
    DefaultTableModel dtmPassageiros;//MODELO PADRAO DA TABELA DE PASSAGEIROS
    
    ArrayList<Voo> voos; //ARRAY LIST CONTANDO OS VOOS CADASTRADOS
    
    public Controle() {
        voos = new ArrayList<>(); //INSTANCIANDO O ARRAYLIST
        configLookAndFeel(); //DEFINE O LOOK AND FEEL DO PROGRAMA COM BASE NO SISTEMA OPERACIONAL
        configMain();
        configCadastroPassageiro();
        configTelaExibicao();
        configTables();
       
    }
    
    private void SetLookAndFeel(String feel){ //CONFIGURAÇÃO DO LOOK AND FELL (LaF)
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if (feel.equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | javax.swing.UnsupportedLookAndFeelException | NullPointerException ex) {
            java.util.logging.Logger.getLogger(TelaPrincipal.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
    
    }

    public void cadastrarVoo() {
        try {
            for (Voo voo : voos) {
            if(voo.getId() == Integer.parseInt(TelaPrincipal.txt_id.getText())){
                JOptionPane.showMessageDialog(null, "Este ID já está cadastrado.");
                return;
            }
        }
        Voo voo = new Voo(TelaPrincipal.box_aeronave.getSelectedItem().toString(),Integer.parseInt(TelaPrincipal.txt_id.getText()),Double.parseDouble(TelaPrincipal.txt_valor.getText()));
        voos.add(voo);
        att_interface();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(tabela, "Preencha os campos para concluir a operação!");
        }
    }
    
    private void att_interface(){
        tabela.setRowSorter(new TableRowSorter(dtmVoos)); // permite re organizar a tabela
        if(dtmVoos.getRowCount() > 0){ //Limpa a tabela;(Verifica a quantidade de linhas na tabela)
              dtmVoos.setRowCount(0); // Define a contagem de linhas da Table para ZERO, se houver elementos na tabela eles serão descartados.
        }
        for (Voo instance : voos) {
            Object[] dados = {instance.getId(),instance.getAeronave(),instance.N_passageiros(), instance.getCapacidade()};
            dtmVoos.addRow(dados); 
        }
    }

    public void ReservarAssento() { //SETA TELA DE CADASTROD E PASSAGEIRO COMO VISIVEL 
        if(telaCadastroPassageiro.isVisible()){
            telaCadastroPassageiro.setVisible(false);
        }else{
            if(tabela.getSelectedRow() != -1){
               cleanFields();
               telaCadastroPassageiro.setVisible(true);
            }else{
               JOptionPane.showMessageDialog(tabela, "Selecione uma entrada na tabela.");
            }
        }
    }

    public void exibirDados() {
        try{
            telaExibicao.setVisible(true);
            Object id = tabela.getValueAt(tabela.getSelectedRow(), 0);
            int id_ = (int)id;
             for (Voo voo : voos) {
                if(voo.getId() == id_){
                    System.out.println(voo.toString());
                    telaExibicao.lbl_id.setText(id_+"");
                    telaExibicao.lbl_aeronave.setText(voo.getAeronave());
                    telaExibicao.lbl_capacidade.setText(voo.getCapacidade()+" MAXIMO.");
                    telaExibicao.lbl_valor.setText(voo.getValor()+" R$");
                    telaExibicao.lbl_passageiros.setText(voo.passageiros.size()+"");
                    if(dtmPassageiros.getRowCount() > 0){ 
                        dtmPassageiros.setRowCount(0); 
                    }
                    for (Passageiro instance : voo.passageiros) {//ADD OS DADOS NA TABELA NA INTERFACE
                        Object[] dados = {instance.getNome(),instance.getCpf(),instance.getTelefone(),instance.getEmail()};
                        dtmPassageiros.addRow(dados); 
                    }
                }
            }
        }catch(IndexOutOfBoundsException e){
            JOptionPane.showMessageDialog(tabela, "Selecione uma entrada na tabela.");
        }
    }

    public void receberDadosUser() {
         try{
            Object id = tabela.getValueAt(tabela.getSelectedRow(), 0); //RETORNA O OBJETO(object) NA CELULA ESCOLHIDA
            if(telaCadastroPassageiro.txt_cpf4.getText().isEmpty() || telaCadastroPassageiro.txt_nome4.getText().isEmpty() || telaCadastroPassageiro.txt_telefo4.getText().isEmpty() || telaCadastroPassageiro.txt_email4.getText().isEmpty()){
                JOptionPane.showMessageDialog(null, "Preencha os dados corretamente.");
                return;
            }
            Passageiro e = new Passageiro(telaCadastroPassageiro.txt_cpf4.getText(),
                                          telaCadastroPassageiro.txt_nome4.getText(),
                                          telaCadastroPassageiro.txt_telefo4.getText(),
                                          telaCadastroPassageiro.txt_email4.getText());
            
            for (Voo voo : voos) {
                if(voo.getId() == Integer.parseInt(id.toString())){
                    if(voo.addPassageiro(e)==false){
                        JOptionPane.showMessageDialog(tabela, "A aeronave já atingiu o limite máximo de ocupantes !");
                    }
                }
            }
            telaCadastroPassageiro.setVisible(false);
            att_interface(); //ATUALIZA A INTERFACEGRAFICA
        }catch(NumberFormatException | ArrayIndexOutOfBoundsException e){
            JOptionPane.showMessageDialog(tabela, "Selecione uma entrada na tabela.");
        }
    }

    public void sair() {
        System.exit(0); //FECHA O PROGRAMA RETORNANDO 0 PARA O SISTEMA OPERACIONAL
    }

    private void configMain() {
        telaprincipal = new TelaPrincipal(this);
        telaprincipal.setVisible(true);// DEFINE A TELA INCIIAL COMO VISIVEL
        telaprincipal.setResizable(false); //NÃO PERMITE O USUARIO REDIMENCIONAR TELA
        telaprincipal.setLocationRelativeTo(null);//DEFINE A POSIÇÃO COMO NULL, CENTRO DA TELA.
    }

    private void configCadastroPassageiro() {
        telaCadastroPassageiro = new TelaCadastroPassageiro(this);
        telaCadastroPassageiro.setLocationRelativeTo(null);//DEFINE A POSIÇÃO COMO NULL, CENTRO DA TELA.
        telaCadastroPassageiro.setResizable(false);//NÃO PERMITE O USUARIO REDIMENCIONAR TELA
    }

    private void configTelaExibicao() { //INSTANCIANDO A TELA DE EXIBIÇÃO
        telaExibicao = new TelaExibicao(this);
        telaExibicao.setLocationRelativeTo(null); //DEFINE A POSIÇÃO COMO NULL, CENTRO DA TELA.
        telaExibicao.setResizable(false); //NÃO PERMITE O USUARIO REDIMENCIONAR TELA
    }

    private void configTables() {
       dtmVoos = (DefaultTableModel) tabela.getModel(); //INSTANCIANDO OS MODELOSDE TABELA
       dtmPassageiros = (DefaultTableModel) TelaExibicao.tabela_passageiros.getModel(); 
    }

    private void configLookAndFeel() {
       if(System.getProperty("os.name").startsWith("Windows")){ //SE O SISTEMA FOR WINDOWS DEFINE O LaF COMO "WINDOWS"
            SetLookAndFeel("Windows"); //> WINDOWS
       }else if(System.getProperty("os.name").startsWith("Linux")){//SE O SSITEMA FOR LINUX DEFINE COMO "NINBUS"
            SetLookAndFeel("Nimbus");// LINUX
       }
    }

    private void cleanFields() { //SETA OS CAMPOS COMO '';
        telaCadastroPassageiro.txt_cpf4.setText("");
        telaCadastroPassageiro.txt_email4.setText("");
        telaCadastroPassageiro.txt_nome4.setText("");
        telaCadastroPassageiro.txt_telefo4.setText("");
    }

}
