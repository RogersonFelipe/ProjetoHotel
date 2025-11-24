/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JDialog.java to edit this template
 */
package forms;

import controller.DaoHospedes;
import controller.DaoQuartos;
import controller.DaoReservas;
import enums.Status;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.List;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JOptionPane;
import model.Hospedes;
import model.Quartos;
import model.Reservas;

/**
 *
 * @author Administrador
 */
public class DialogReservas extends javax.swing.JDialog {
    
    private static final java.util.logging.Logger logger = java.util.logging.Logger.getLogger(DialogReservas.class.getName());
    
    private DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd/MM/yyyy");
    private DaoReservas dao = new DaoReservas();
    
    //Carregar os dados da Table
    private void loadTable(){
        Table.setModel(new MyTableModel(Reservas.class, dao.read(), Table));
    }
    
    //Carregar Table com o Filtro de nome dos hospedes
    private void loadTable(String filtro){
        Table.setModel(new MyTableModel(Reservas.class, dao.reaByNomeHospedes(filtro), Table));
    }
    
    //Carregar Quartos
    
    private void loadCQuarto() {
       loadCQuarto(null);
    }

    private void loadCQuarto(Quartos quartoAtual) {
        DaoQuartos dao = new DaoQuartos();
        List<Quartos> lista = dao.readLivres();
        if ((lista == null || lista.isEmpty()) && quartoAtual == null) {
            JOptionPane.showMessageDialog(this,
                    "Não há quartos livres disponíveis no momento.",
                    "Aviso",
                    JOptionPane.INFORMATION_MESSAGE);
        }
        if (quartoAtual != null && !lista.contains(quartoAtual)) {
            lista.add(quartoAtual);
        }
        DefaultComboBoxModel cbm = new DefaultComboBoxModel(lista.toArray());
        CNumeroQuarto.setModel(cbm);

        if (quartoAtual != null) {
            CNumeroQuarto.setSelectedItem(quartoAtual);
        }
    }
    
    //Carregar Hospedes
    private void loadCHospedes(){
        DaoHospedes dao = new DaoHospedes();
        DefaultComboBoxModel cbm = new DefaultComboBoxModel(dao.read().toArray());
        CHospedes.setModel(cbm);
    }
    
    //Zerar Componentes
    private void limparComponentes(){
        TCodigo.setText("");
        FTEntrada.setText("");
        FTSaida.setText("");
        TNomeReserva.setText("");
        TFiltro.setText("");
        TNomeReserva.requestFocus();
        if (CHospedes.getItemCount() > 0) {
            CHospedes.setSelectedIndex(0);
        }

        if (CNumeroQuarto.getItemCount() > 0) {
            CNumeroQuarto.setSelectedIndex(0);
        }
    }
    
    //Atualizar Valor Total
    private void atualizarTotal() {
        try {
            Quartos q = (Quartos) CNumeroQuarto.getSelectedItem();
            if (q == null || q.getPreco_diaria()== null) {
                TTotal.setText("0.00");
                return;
            }

            String entradaStr = FTEntrada.getText().trim();
            String saidaStr = FTSaida.getText().trim();

            if (entradaStr.isEmpty() || saidaStr.isEmpty()) {
                TTotal.setText("0.00");
                return;
            }

            LocalDate checkin = LocalDate.parse(entradaStr, dtf);
            LocalDate checkout = LocalDate.parse(saidaStr, dtf);

            long dias = ChronoUnit.DAYS.between(checkin, checkout);
            if (dias < 1) {
                dias = 1;
            }

            BigDecimal total = q.getPreco_diaria().multiply(BigDecimal.valueOf(dias));
            TTotal.setText(total.setScale(2, RoundingMode.HALF_UP).toString());
        } catch (Exception e) {
            TTotal.setText("0.00");
        }
    }

    
    //Montar Objeto
    private Reservas createObject(){
        Reservas r = new Reservas();
        Quartos q = (Quartos) CNumeroQuarto.getSelectedItem();
        if(TCodigo.getText().isEmpty()){
            r.setId(0);
        }else{
            r.setId(Integer.parseInt((TCodigo.getText())));
        }
        r.setQuartos((Quartos) CNumeroQuarto.getSelectedItem());
        r.setHospedes((Hospedes) CHospedes.getSelectedItem());
        r.setNome_reserva(TNomeReserva.getText());
        LocalDate checkin = null;
        LocalDate checkout = null;
        try {
            if (!FTEntrada.getText().trim().isEmpty()) {
                checkin = LocalDate.parse(FTEntrada.getText(), dtf);
                r.setData_checkin(checkin);
            }
            if (!FTSaida.getText().trim().isEmpty()) {
                checkout = LocalDate.parse(FTSaida.getText(), dtf);
                r.setData_checkout(checkout);
            }
        } catch (Exception e) {
            System.out.println("Erro ao converter datas: " + e.getMessage());
        }
        java.math.BigDecimal total = java.math.BigDecimal.ZERO;
        if (checkin != null && checkout != null && q != null && q.getPreco_diaria() != null) {
            long dias = java.time.temporal.ChronoUnit.DAYS.between(checkin, checkout);
            if (dias < 1) {
                dias = 1;
            }

            total = q.getPreco_diaria().multiply(java.math.BigDecimal.valueOf(dias));
        }
        r.setTotal(total);
        TTotal.setText(total.setScale(2, java.math.RoundingMode.HALF_UP).toString());
        return r;
    }
    
    //Popular Componentes
    private void loadComponentes(Reservas r){
        TCodigo.setText(r.getId()+"");
        CHospedes.setSelectedItem(r.getHospedes());
        loadCQuarto(r.getQuartos());
        TNomeReserva.setText(r.getNome_reserva());
        if (r.getData_checkin() != null) {
            FTEntrada.setText(r.getData_checkin().format(dtf));
        } else {
            FTEntrada.setText("");
        }
        if (r.getData_checkout() != null) {
            FTSaida.setText(r.getData_checkout().format(dtf));
        } else {
            FTSaida.setText("");
        }
        if (r.getTotal() != null) {
            TTotal.setText(r.getTotal().setScale(2, java.math.RoundingMode.HALF_UP).toString());
        } else {
            TTotal.setText("0.00");
        }

    }
    

    /**
     * Creates new form DialogReservas
     */
    public DialogReservas(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();
        FTEntrada.addPropertyChangeListener("value", evt -> atualizarTotal());
        FTSaida.addPropertyChangeListener("value", evt -> atualizarTotal());
        CNumeroQuarto.addActionListener(evt -> atualizarTotal());
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        TCodigo = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        CNumeroQuarto = new javax.swing.JComboBox<>();
        jLabel3 = new javax.swing.JLabel();
        CHospedes = new javax.swing.JComboBox<>();
        jLabel4 = new javax.swing.JLabel();
        FTEntrada = new javax.swing.JFormattedTextField();
        jLabel5 = new javax.swing.JLabel();
        FTSaida = new javax.swing.JFormattedTextField();
        jLabel6 = new javax.swing.JLabel();
        TTotal = new javax.swing.JTextField();
        jLabel8 = new javax.swing.JLabel();
        TNomeReserva = new javax.swing.JTextField();
        jPanel2 = new javax.swing.JPanel();
        BtnSalvar = new javax.swing.JButton();
        BtnNovo = new javax.swing.JButton();
        BtnRemover = new javax.swing.JButton();
        jPanel3 = new javax.swing.JPanel();
        jLabel7 = new javax.swing.JLabel();
        TFiltro = new javax.swing.JTextField();
        BtnFiltro = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        Table = new javax.swing.JTable();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowOpened(java.awt.event.WindowEvent evt) {
                formWindowOpened(evt);
            }
        });

        jLabel1.setText("Codigo");

        TCodigo.setEditable(false);

        jLabel2.setText("Numero do Quarto");

        CNumeroQuarto.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        jLabel3.setText("Hospedes");

        CHospedes.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        jLabel4.setText("Data de Entrada");

        try {
            FTEntrada.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.MaskFormatter("##/##/####")));
        } catch (java.text.ParseException ex) {
            ex.printStackTrace();
        }

        jLabel5.setText("Data de Saida");

        try {
            FTSaida.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.MaskFormatter("##/##/####")));
        } catch (java.text.ParseException ex) {
            ex.printStackTrace();
        }

        jLabel6.setText("Valor Total");

        TTotal.setEditable(false);

        jLabel8.setText("Nome da Reserva");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel3)
                            .addComponent(CHospedes, javax.swing.GroupLayout.PREFERRED_SIZE, 172, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel4)
                            .addComponent(jLabel5)
                            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                .addComponent(FTSaida, javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(FTEntrada, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 135, Short.MAX_VALUE)))
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jLabel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(CNumeroQuarto, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                .addComponent(TCodigo, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                                .addComponent(jLabel1, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel6)
                            .addComponent(TTotal, javax.swing.GroupLayout.PREFERRED_SIZE, 93, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(20, 20, 20))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel8)
                            .addComponent(TNomeReserva, javax.swing.GroupLayout.PREFERRED_SIZE, 301, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(0, 323, Short.MAX_VALUE))))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(TCodigo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel6)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(TTotal, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(18, 18, 18)
                .addComponent(jLabel2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(CNumeroQuarto, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jLabel3)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(CHospedes, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jLabel4)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(FTEntrada, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jLabel5)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(FTSaida, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jLabel8)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(TNomeReserva, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(19, Short.MAX_VALUE))
        );

        BtnSalvar.setText("Salvar");
        BtnSalvar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnSalvarActionPerformed(evt);
            }
        });

        BtnNovo.setText("Novo");
        BtnNovo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnNovoActionPerformed(evt);
            }
        });

        BtnRemover.setText("Remover");
        BtnRemover.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnRemoverActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addContainerGap(10, Short.MAX_VALUE)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addComponent(BtnSalvar, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(BtnRemover, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(BtnNovo, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(15, 15, 15))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(16, 16, 16)
                .addComponent(BtnSalvar)
                .addGap(18, 18, 18)
                .addComponent(BtnNovo)
                .addGap(18, 18, 18)
                .addComponent(BtnRemover)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jLabel7.setText("Filtrar por Hospede");

        BtnFiltro.setText("Filtrar");
        BtnFiltro.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnFiltroActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addComponent(jLabel7)
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addComponent(TFiltro)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(BtnFiltro)
                        .addGap(25, 25, 25))))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(BtnFiltro))
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addComponent(jLabel7)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(TFiltro, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );

        Table.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        Table.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                TableMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(Table);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addComponent(jScrollPane1))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(18, 18, 18)
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void BtnNovoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnNovoActionPerformed
        this.limparComponentes();
    }//GEN-LAST:event_BtnNovoActionPerformed

    private void formWindowOpened(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowOpened
        this.loadTable();
        this.loadCHospedes();
        this.loadCQuarto();
    }//GEN-LAST:event_formWindowOpened

    private void BtnSalvarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnSalvarActionPerformed
        Reservas r = this.createObject();
        try {
            if (TCodigo.getText().isEmpty()) {
                dao.create(r);

                Quartos q = r.getQuartos();
                q.setStatus(Status.R);
                DaoQuartos daoQ = new DaoQuartos();
                daoQ.update(q);

                JOptionPane.showMessageDialog(this, "Reserva criada com sucesso! Quarto marcado como Alugado.");
            } else {
                dao.update(r);
                JOptionPane.showMessageDialog(this, "Reserva atualizada com sucesso!");
            }

            this.limparComponentes();
            this.loadCQuarto();
            this.loadTable();
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Erro ao gravar reserva: " + ex.getMessage());
        }
    }//GEN-LAST:event_BtnSalvarActionPerformed

    private void BtnRemoverActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnRemoverActionPerformed
        if (TCodigo.getText().isEmpty()) {
            JOptionPane.showMessageDialog(null, "Selecione um dos registros.");
            return;
        }
        if (JOptionPane.showConfirmDialog(null, "Deseja remover?") != 0) {
            return;
        }
        try {
            int codigo = Integer.parseInt(TCodigo.getText());
            Reservas r = dao.read(Reservas.class, codigo);
            Quartos q = r.getQuartos();
            if (q != null) {
                q.setStatus(Status.L);
                DaoQuartos daoQ = new DaoQuartos();
                daoQ.update(q);
            }
            dao.delete(r);
            JOptionPane.showMessageDialog(null, "Reserva removida com sucesso! Quarto liberado.");
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, "Erro ao remover: " + ex.getMessage());
        }
        this.limparComponentes();
        this.loadCQuarto();
        this.loadTable();
    }//GEN-LAST:event_BtnRemoverActionPerformed

    private void BtnFiltroActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnFiltroActionPerformed
        this.loadTable(TFiltro.getText());
    }//GEN-LAST:event_BtnFiltroActionPerformed

    private void TableMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_TableMouseClicked
        if(evt.getClickCount() == 2){
            int selecionado = (Integer)Table.getValueAt(Table.getSelectedRow(), 0);
            Reservas r = dao.read(Reservas.class, selecionado);
            this.loadComponentes(r);
        }
    }//GEN-LAST:event_TableMouseClicked

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ReflectiveOperationException | javax.swing.UnsupportedLookAndFeelException ex) {
            logger.log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the dialog */
        java.awt.EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                DialogReservas dialog = new DialogReservas(new javax.swing.JFrame(), true);
                dialog.addWindowListener(new java.awt.event.WindowAdapter() {
                    @Override
                    public void windowClosing(java.awt.event.WindowEvent e) {
                        System.exit(0);
                    }
                });
                dialog.setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton BtnFiltro;
    private javax.swing.JButton BtnNovo;
    private javax.swing.JButton BtnRemover;
    private javax.swing.JButton BtnSalvar;
    private javax.swing.JComboBox<String> CHospedes;
    private javax.swing.JComboBox<String> CNumeroQuarto;
    private javax.swing.JFormattedTextField FTEntrada;
    private javax.swing.JFormattedTextField FTSaida;
    private javax.swing.JTextField TCodigo;
    private javax.swing.JTextField TFiltro;
    private javax.swing.JTextField TNomeReserva;
    private javax.swing.JTextField TTotal;
    private javax.swing.JTable Table;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JScrollPane jScrollPane1;
    // End of variables declaration//GEN-END:variables
}
