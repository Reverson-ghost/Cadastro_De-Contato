package Contato;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.HeadlessException;
import java.awt.List;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.Buffer;
import java.util.ArrayList;
import java.util.Scanner;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.Border;
import javax.swing.table.DefaultTableModel;

public class tela extends JFrame {
    public tela() {
        super("Cadastro de Contatos");
        setSize(500, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
        this.setLocationRelativeTo(null);
    }

    public static void main(String[] args) {
        tela tela = new tela();
        tela.setVisible(true);
        // adicionar componentes na tela
        JLabel labelNome = new JLabel("NOME ");
        JTextField textFieldNome = new JTextField(20);
        JLabel labelCpf = new JLabel("CPF ");
        JTextField textFieldCpf = new JTextField(20);
        JLabel labelEmail = new JLabel("EMAIL ");
        JTextField textFieldEmail = new JTextField(20);
        JTextArea areaSaida = new JTextArea(1, 20);
        // botoes
        JButton button = new JButton("Salvar");
        JButton buttonBuscar = new JButton("Buscar");
        JButton remover = new JButton("Remover");
        JButton alterar = new JButton("Alterar");
        JButton limpar = new JButton("X");
        // layouts dos paineis
        JPanel panel = new JPanel(); // painel principal onde sera exibido os componentes
        JPanel panelCamposDeEntrada = new JPanel(new GridLayout(3, 2, 5, 5));
        JPanel panelBotao = new JPanel(new FlowLayout(0, 10, 10));
        JPanel panelSaida = new JPanel();

        // ---------------------------------//
        // adicionar os components label e text field no panel
        panel.add(panelCamposDeEntrada);
        panelCamposDeEntrada.add(labelNome);
        panelCamposDeEntrada.add(textFieldNome);
        panelCamposDeEntrada.add(labelCpf);
        panelCamposDeEntrada.add(textFieldCpf);
        panelCamposDeEntrada.add(labelEmail);
        panelCamposDeEntrada.add(textFieldEmail);
        // ---------------------------------//
        panel.add(panelBotao);
        panelBotao.add(button);
        panelBotao.add(buttonBuscar);
        panelBotao.add(remover);
        panelBotao.add(alterar);
        panelBotao.add(limpar);
        panel.add(panelSaida);
        // ---------------------------------//
        String[] colunas = { "CPF", "NOME", "EMAIL" };
        Object[][] dados = {
        };
        DefaultTableModel model = new DefaultTableModel(dados, colunas);
        JTable tabela = new JTable(model);
        JScrollPane exibirTabela = new JScrollPane(tabela);
        panel.add(exibirTabela);
        exibirTabela.setBorder(BorderFactory.createBevelBorder(1, Color.black, Color.red, Color.blue, Color.green));
        exibirTabela.setPreferredSize(new Dimension(400, 380));
        panelSaida.add(areaSaida);
        // ---------------------------------//
        tela.add(panel);
        // adicionar bordas para os label
        Border bordasLabel = BorderFactory.createLineBorder(Color.black);
        labelNome.setBorder(bordasLabel);
        labelNome.setBorder(BorderFactory.createBevelBorder(1, Color.black, Color.red));
        labelCpf.setBorder(bordasLabel);
        labelCpf.setBorder(BorderFactory.createBevelBorder(1, Color.black, Color.red));
        labelEmail.setBorder(bordasLabel);
        labelEmail.setBorder(BorderFactory.createBevelBorder(1, Color.black, Color.red));
        // borda composta para as bordas do text field
        Border bordaComposta = BorderFactory.createCompoundBorder(BorderFactory.createLineBorder(Color.black),
                BorderFactory.createEmptyBorder(5, 5, 5, 5));
        textFieldNome.setBorder(bordaComposta);
        textFieldNome.setBorder(BorderFactory.createBevelBorder(1, Color.black, Color.red, Color.blue, Color.green));
        textFieldCpf.setBorder(bordaComposta);
        textFieldCpf.setBorder(BorderFactory.createBevelBorder(1, Color.black, Color.red, Color.blue, Color.green));
        textFieldEmail.setBorder(bordaComposta);
        textFieldEmail.setBorder(BorderFactory.createBevelBorder(1, Color.black, Color.red, Color.blue, Color.green));
        // alinhar textos do label
        labelNome.setHorizontalAlignment(SwingConstants.CENTER);
        labelCpf.setHorizontalAlignment(SwingConstants.CENTER);
        labelEmail.setHorizontalAlignment(SwingConstants.CENTER);
        // tamanho dos labels nome,email, cpf
        labelNome.setFont(new Font("Arial", Font.BOLD, 12));
        labelCpf.setFont(new Font("Arial", Font.BOLD, 12));
        labelEmail.setFont(new Font("Arial", Font.BOLD, 12));
        // area de saida
        areaSaida.setBorder(BorderFactory.createBevelBorder(1, Color.black, Color.red, Color.blue, Color.green));
        // adicionar açaõ ao botao salvar
        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String saida;
                // verificar os campos de textos
                if (textFieldCpf.getText().equals("") || textFieldNome.getText().equals("")
                        || textFieldEmail.getText().equals("")) {
                    JOptionPane.showMessageDialog(null, "Preencha Todos Os Campos");
                    areaSaida.setText("Não Foi Possivel Finalizar o Cadastro");
                } else {
                    try (
                            // escrita no arquivo txt
                            FileWriter arquivo = new FileWriter("listaDeContato.txt", true)) {
                        PrintWriter gravarArquivo = new PrintWriter(arquivo);
                        gravarArquivo.println(textFieldCpf.getText() + ";" + textFieldNome.getText() + ";"
                                + textFieldEmail.getText());
                        gravarArquivo.close();
                        areaSaida.setText("Cadastro Realizado Com Sucesso!");
                    } catch (IOException e1) {
                        e1.printStackTrace();
                    }
                    // impressao na tabela
                    model.addRow(new String[] { textFieldCpf.getText(), textFieldNome.getText(),
                            textFieldEmail.getText() });
                    // limpar os campos de textos
                    textFieldCpf.setText("");
                    textFieldNome.setText("");
                    textFieldEmail.setText("");

                }
            } 
        });
        // adicionar açaõ ao botao buscar
        buttonBuscar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // realizar busca apenas pelo cpf
                FileReader arquivo;
                try {
                    arquivo = new FileReader("listaDeContato.txt");
                    BufferedReader lerArquivo = new BufferedReader(arquivo);
                    String linha = lerArquivo.readLine();
                    while (linha != null) {
                        String[] dados = linha.split(";");
                        if (dados[0].equals(textFieldCpf.getText())) {
                            model.addRow(linha.split(";"));
                            areaSaida.setText("CPF Encontrado");
                        }
                        linha = lerArquivo.readLine();
                    }
                    if (textFieldCpf.getText().equals("")) {
                        JOptionPane.showMessageDialog(null, "Preencha o Campo CPF");
                        areaSaida.setText("Não Foi Possivel Realizar a Busca");
                    }
                    lerArquivo.close();
                } catch (IOException e1) {
                }
                textFieldCpf.setText("");
                textFieldNome.setText("");
                textFieldEmail.setText("");
            }

        });
        // adicionar açaõ ao botao remover
        remover.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String saida = "Removido Com Sucesso!";
                areaSaida.setText(saida);
                // verificar se o cpf esta no arquivo e remover a linha
                File inputFile = new File("listaDeContato.txt");
                File tempFile = new File("temp.txt");
                try {
                    BufferedReader reader = new BufferedReader(new FileReader(inputFile));
                    BufferedWriter writer = new BufferedWriter(new FileWriter(tempFile));

                    String cpfParaRemover = textFieldCpf.getText();
                    String currentLine;

                    while ((currentLine = reader.readLine()) != null) {
                        String[] dados = currentLine.split(";");
                        if (dados[0].equals(cpfParaRemover)) {
                            // Ignora a linha se ela contém o CPF a ser removido
                            continue;
                        }
                        writer.write(currentLine + System.getProperty("line.separator"));
                    }
                    reader.close();
                    writer.close();
                    inputFile.delete();
                    tempFile.renameTo(inputFile);

                    areaSaida.setText("CPF removido com sucesso.");
                } catch (IOException e1) {
                    JOptionPane.showMessageDialog(null, "Erro ao remover CPF.");
                    areaSaida.setText("Erro ao remover CPF.");
                }
            }

        });

        // adicionar açaõ ao botao alterar
        alterar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String saida = "Voce Alterou o Contato" + " ";
                areaSaida.setText(saida);
                // buscar arquivo

            }
                
        });  
        
        // adicionar açaõ ao botao limpar
        limpar.addActionListener( new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                textFieldCpf.setText("");
                textFieldNome.setText("");
                textFieldEmail.setText("");
                areaSaida.setText("");
                areaSaida.setText("Campos Limpos");
            }
        });
    }
} 
