package GUI;

import java.awt.Color;
import BL.City;
import BL.Daily;
import BL.Reminders;
import BL.Today;
import BL.Users;
import DAL.Exceptions;
import DAL.UsersRepository;
import DAL.WeatherRepository;
import DAL.RemindersRepository;
import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 *
 * @author Ardian
 */
public class Main extends javax.swing.JFrame {

    
    public Main() {
        initComponents();jPanel8.setOpaque(false);jPanel7.setOpaque(false);jPanel6.setOpaque(false);jPanel5.setOpaque(false);jPanel9.setOpaque(false);jPanel10.setOpaque(false);jPanel8.setBackground(new Color(0,0,0,150));jPanel8.setForeground(new Color(0,0,0,150));jPanel7.setBackground(new Color(0,0,0,150));jPanel7.setForeground(new Color(0,0,0,150));jPanel5.setBackground(new Color(0,0,0,150));jPanel5.setForeground(new Color(0,0,0,150));jPanel9.setBackground(new Color(0,0,0,150));jPanel9.setForeground(new Color(0,0,0,150));jPanel6.setBackground(new Color(0,0,0,150));jPanel6.setForeground(new Color(0,0,0,150));jPanel5.setBackground(new Color(0,0,0,150));jPanel5.setForeground(new Color(0,0,0,150));jPanel10.setBackground(new Color(0,0,0,150));jPanel10.setForeground(new Color(0,0,0,150));jButton10.setOpaque(false);jButton10.setBackground(new Color(0,0,0,150));jScrollPane1.setOpaque(false);jScrollPane1.getViewport().setOpaque(false);jScrollPane1.setBorder(null);jScrollPane1.setViewportBorder(null);jTextArea1.setBorder(null);jTextArea1.setBackground(new Color(0,0,0,0));jScrollPane8.setOpaque(false);jScrollPane8.getViewport().setOpaque(false);jScrollPane8.setBorder(null);jScrollPane8.setViewportBorder(null);jTextArea8.setBorder(null);jTextArea8.setBackground(new Color(0,0,0,0));jTextArea8.setWrapStyleWord(true);jTextArea8.setLineWrap(true);jTextArea8.setOpaque(false);jTextArea8.setEditable(false);jTextArea8.setFocusable(false);jScrollPane9.setOpaque(false);jScrollPane9.getViewport().setOpaque(false);jScrollPane9.setBorder(null);jScrollPane9.setViewportBorder(null);jTextArea9.setBorder(null);jTextArea9.setBackground(new Color(0,0,0,0));jTextArea9.setWrapStyleWord(true);jTextArea9.setLineWrap(true);jTextArea9.setOpaque(false);jTextArea9.setEditable(false);jTextArea9.setFocusable(false);jScrollPane10.setOpaque(false);jScrollPane10.getViewport().setOpaque(false);jScrollPane10.setBorder(null);jScrollPane10.setViewportBorder(null);jTextArea10.setBorder(null);jTextArea10.setBackground(new Color(0,0,0,0));jTextArea10.setWrapStyleWord(true);jTextArea10.setLineWrap(true);jTextArea10.setOpaque(false);jTextArea10.setEditable(false);jTextArea10.setFocusable(false);jScrollPane7.setOpaque(false);jScrollPane7.getViewport().setOpaque(false);jScrollPane7.setBorder(null);jScrollPane7.setViewportBorder(null);jTextArea7.setBorder(null);jTextArea7.setBackground(new Color(0,0,0,0));jTextArea7.setWrapStyleWord(true);jTextArea7.setLineWrap(true);jTextArea7.setOpaque(false);jTextArea7.setEditable(false);jTextArea7.setFocusable(false);jScrollPane6.setOpaque(false);jScrollPane6.getViewport().setOpaque(false);jScrollPane6.setBorder(null);jScrollPane6.setViewportBorder(null);jTextArea6.setBorder(null);jTextArea6.setBackground(new Color(0,0,0,0));jTextArea6.setWrapStyleWord(true);jTextArea6.setLineWrap(true);jTextArea6.setOpaque(false);jTextArea6.setEditable(false);jTextArea6.setFocusable(false);jScrollPane5.setOpaque(false);jScrollPane5.getViewport().setOpaque(false);jScrollPane5.setBorder(null);jScrollPane5.setViewportBorder(null);jTextArea5.setBorder(null);jTextArea5.setBackground(new Color(0,0,0,0));jTextArea5.setWrapStyleWord(true);jTextArea5.setLineWrap(true);jTextArea5.setOpaque(false);jTextArea5.setEditable(false);jTextArea5.setFocusable(false);jScrollPane4.setOpaque(false);jScrollPane4.getViewport().setOpaque(false);jScrollPane4.setBorder(null);jScrollPane4.setViewportBorder(null);jTextArea4.setBorder(null);jTextArea4.setBackground(new Color(0,0,0,0));jTextArea4.setWrapStyleWord(true);jTextArea4.setLineWrap(true);jTextArea4.setOpaque(false);jTextArea4.setEditable(false);jTextArea4.setFocusable(false);jScrollPane3.setOpaque(false);jScrollPane3.getViewport().setOpaque(false);jScrollPane3.setBorder(null);jScrollPane3.setViewportBorder(null);jTextArea3.setBorder(null);jTextArea3.setBackground(new Color(0,0,0,0));jTextArea3.setWrapStyleWord(true);jTextArea3.setLineWrap(true);jTextArea3.setOpaque(false);jTextArea3.setEditable(false);jTextArea3.setFocusable(false);jScrollPane1.setOpaque(false);jScrollPane1.getViewport().setOpaque(false);jScrollPane1.setBorder(null);jScrollPane1.setViewportBorder(null);jTextArea1.setBorder(null);jTextArea1.setBackground(new Color(0,0,0,0));jTextArea1.setWrapStyleWord(true);jTextArea1.setLineWrap(true);jTextArea1.setOpaque(false);jTextArea1.setEditable(false);jTextArea1.setFocusable(false);jTextField2.setOpaque(false);jTextField2.setBackground(new Color(0,0,0,0));jTextField2.setEditable(false);jTextField2.setFocusable(false);jScrollPane2.setOpaque(false);jScrollPane2.getViewport().setOpaque(false);jScrollPane2.setBorder(null);jScrollPane2.setViewportBorder(null);jTextArea2.setBorder(null);jTextArea2.setBackground(new Color(0,0,0,0));jTextArea2.setLineWrap(true);jTextArea2.setWrapStyleWord(true);jTextArea2.setLineWrap(true);jTextArea2.setOpaque(false);jTextArea2.setEditable(false);jTextArea2.setFocusable(false);
        checkUser();
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel12 = new javax.swing.JPanel();
        jPanel2 = new javax.swing.JPanel();
        jLabel5 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        jLabel13 = new javax.swing.JLabel();
        jLabel14 = new javax.swing.JLabel();
        jLabel17 = new javax.swing.JLabel();
        jSeparator1 = new javax.swing.JSeparator();
        jLabel19 = new javax.swing.JLabel();
        jLabel20 = new javax.swing.JLabel();
        jLabel25 = new javax.swing.JLabel();
        jLabel16 = new javax.swing.JLabel();
        jLabel27 = new javax.swing.JLabel();
        jLabel26 = new javax.swing.JLabel();
        jLabel29 = new javax.swing.JLabel();
        jLabel34 = new javax.swing.JLabel();
        jLabel35 = new javax.swing.JLabel();
        jLabel31 = new javax.swing.JLabel();
        jLabel32 = new javax.swing.JLabel();
        jLabel37 = new javax.swing.JLabel();
        jLabel38 = new javax.swing.JLabel();
        jLabel40 = new javax.swing.JLabel();
        jLabel41 = new javax.swing.JLabel();
        jLabel43 = new javax.swing.JLabel();
        jLabel44 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        jButton1 = new javax.swing.JButton();
        jSeparator10 = new javax.swing.JSeparator();
        jPanel1 = new javax.swing.JPanel();
        jPanel6 = new javax.swing.JPanel();
        jScrollPane8 = new javax.swing.JScrollPane();
        jTextArea8 = new javax.swing.JTextArea();
        jScrollPane9 = new javax.swing.JScrollPane();
        jTextArea9 = new javax.swing.JTextArea();
        jButton9 = new javax.swing.JButton();
        jPanel7 = new javax.swing.JPanel();
        jScrollPane4 = new javax.swing.JScrollPane();
        jTextArea4 = new javax.swing.JTextArea();
        jScrollPane5 = new javax.swing.JScrollPane();
        jTextArea5 = new javax.swing.JTextArea();
        jButton6 = new javax.swing.JButton();
        jPanel8 = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        jTextArea2 = new javax.swing.JTextArea();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTextArea1 = new javax.swing.JTextArea();
        jButton7 = new javax.swing.JButton();
        jPanel9 = new javax.swing.JPanel();
        jScrollPane6 = new javax.swing.JScrollPane();
        jTextArea6 = new javax.swing.JTextArea();
        jScrollPane7 = new javax.swing.JScrollPane();
        jTextArea7 = new javax.swing.JTextArea();
        jButton8 = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jPanel3 = new javax.swing.JPanel();
        jPanel5 = new javax.swing.JPanel();
        jLabel12 = new javax.swing.JLabel();
        jLabel21 = new javax.swing.JLabel();
        jScrollPane3 = new javax.swing.JScrollPane();
        jTextArea3 = new javax.swing.JTextArea();
        jButton2 = new javax.swing.JButton();
        jButton3 = new javax.swing.JButton();
        jButton4 = new javax.swing.JButton();
        jButton5 = new javax.swing.JButton();
        jSeparator3 = new javax.swing.JSeparator();
        jPanel4 = new javax.swing.JPanel();
        jPanel10 = new javax.swing.JPanel();
        jScrollPane10 = new javax.swing.JScrollPane();
        jTextArea10 = new javax.swing.JTextArea();
        jTextField2 = new javax.swing.JTextField();
        jButton10 = new javax.swing.JButton();
        jSeparator2 = new javax.swing.JSeparator();
        jLabel138 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel12.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel2.setBackground(new java.awt.Color(66, 142, 146));
        jPanel2.setOpaque(false);
        jPanel2.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel5.setForeground(new java.awt.Color(255, 255, 255));
        jLabel5.setIcon(new javax.swing.ImageIcon(getClass().getResource("/IMG/Rain_49px.png"))); // NOI18N
        jPanel2.add(jLabel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(350, 60, -1, -1));

        jLabel4.setFont(new java.awt.Font("Calibri", 0, 24)); // NOI18N
        jLabel4.setForeground(new java.awt.Color(255, 255, 255));
        jLabel4.setText("E DIELL");
        jPanel2.add(jLabel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(230, 30, -1, -1));

        jLabel7.setIcon(new javax.swing.ImageIcon(getClass().getResource("/IMG/Partly Cloudy Rain_49px.png"))); // NOI18N
        jPanel2.add(jLabel7, new org.netbeans.lib.awtextra.AbsoluteConstraints(470, 60, -1, -1));

        jLabel8.setFont(new java.awt.Font("Calibri", 0, 24)); // NOI18N
        jLabel8.setForeground(new java.awt.Color(255, 255, 255));
        jLabel8.setText("E MARTE");
        jPanel2.add(jLabel8, new org.netbeans.lib.awtextra.AbsoluteConstraints(460, 30, -1, -1));

        jLabel10.setIcon(new javax.swing.ImageIcon(getClass().getResource("/IMG/Fog Day_49px.png"))); // NOI18N
        jPanel2.add(jLabel10, new org.netbeans.lib.awtextra.AbsoluteConstraints(630, 60, 50, -1));

        jLabel11.setFont(new java.awt.Font("Calibri", 0, 24)); // NOI18N
        jLabel11.setForeground(new java.awt.Color(255, 255, 255));
        jLabel11.setText("E MERKURE");
        jPanel2.add(jLabel11, new org.netbeans.lib.awtextra.AbsoluteConstraints(600, 30, -1, -1));

        jLabel13.setIcon(new javax.swing.ImageIcon(getClass().getResource("/IMG/Winter_49px.png"))); // NOI18N
        jPanel2.add(jLabel13, new org.netbeans.lib.awtextra.AbsoluteConstraints(780, 60, -1, 50));

        jLabel14.setFont(new java.awt.Font("Calibri", 0, 24)); // NOI18N
        jLabel14.setForeground(new java.awt.Color(255, 255, 255));
        jLabel14.setText("E EJTE");
        jPanel2.add(jLabel14, new org.netbeans.lib.awtextra.AbsoluteConstraints(780, 30, -1, -1));

        jLabel17.setFont(new java.awt.Font("Calibri", 1, 14)); // NOI18N
        jLabel17.setForeground(new java.awt.Color(255, 255, 255));
        jLabel17.setText("E Shtune , 06 Maj 2017");
        jPanel2.add(jLabel17, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 130, -1, -1));

        jSeparator1.setOrientation(javax.swing.SwingConstants.VERTICAL);
        jPanel2.add(jSeparator1, new org.netbeans.lib.awtextra.AbsoluteConstraints(180, 20, -1, 130));

        jLabel19.setIcon(new javax.swing.ImageIcon(getClass().getResource("/IMG/Cloud_49px.png"))); // NOI18N
        jPanel2.add(jLabel19, new org.netbeans.lib.awtextra.AbsoluteConstraints(920, 60, -1, -1));

        jLabel20.setFont(new java.awt.Font("Calibri", 0, 24)); // NOI18N
        jLabel20.setForeground(new java.awt.Color(255, 255, 255));
        jLabel20.setText("E PREMTE");
        jPanel2.add(jLabel20, new org.netbeans.lib.awtextra.AbsoluteConstraints(890, 30, -1, -1));

        jLabel25.setFont(new java.awt.Font("Calibri", 0, 52)); // NOI18N
        jLabel25.setForeground(new java.awt.Color(255, 255, 255));
        jLabel25.setText("16 °C");
        jPanel2.add(jLabel25, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 20, -1, -1));

        jLabel16.setFont(new java.awt.Font("Calibri", 0, 14)); // NOI18N
        jLabel16.setForeground(new java.awt.Color(255, 255, 255));
        jLabel16.setText("Min. 10 °C");
        jPanel2.add(jLabel16, new org.netbeans.lib.awtextra.AbsoluteConstraints(630, 110, -1, -1));

        jLabel27.setFont(new java.awt.Font("Calibri", 0, 14)); // NOI18N
        jLabel27.setForeground(new java.awt.Color(255, 255, 255));
        jLabel27.setText("Max. 16 °C");
        jPanel2.add(jLabel27, new org.netbeans.lib.awtextra.AbsoluteConstraints(630, 130, -1, -1));

        jLabel26.setFont(new java.awt.Font("Calibri", 0, 24)); // NOI18N
        jLabel26.setForeground(new java.awt.Color(255, 255, 255));
        jLabel26.setText("E HENE");
        jPanel2.add(jLabel26, new org.netbeans.lib.awtextra.AbsoluteConstraints(340, 30, -1, -1));

        jLabel29.setIcon(new javax.swing.ImageIcon(getClass().getResource("/IMG/Sun_49px.png"))); // NOI18N
        jPanel2.add(jLabel29, new org.netbeans.lib.awtextra.AbsoluteConstraints(230, 60, -1, -1));

        jLabel34.setFont(new java.awt.Font("Calibri", 0, 14)); // NOI18N
        jLabel34.setForeground(new java.awt.Color(255, 255, 255));
        jLabel34.setText("Min. 10 °C");
        jPanel2.add(jLabel34, new org.netbeans.lib.awtextra.AbsoluteConstraints(920, 110, -1, 20));

        jLabel35.setFont(new java.awt.Font("Calibri", 0, 14)); // NOI18N
        jLabel35.setForeground(new java.awt.Color(255, 255, 255));
        jLabel35.setText("Max. 16 °C");
        jPanel2.add(jLabel35, new org.netbeans.lib.awtextra.AbsoluteConstraints(920, 130, -1, 20));

        jLabel31.setFont(new java.awt.Font("Calibri", 0, 14)); // NOI18N
        jLabel31.setForeground(new java.awt.Color(255, 255, 255));
        jLabel31.setText("Min. 10 °C");
        jPanel2.add(jLabel31, new org.netbeans.lib.awtextra.AbsoluteConstraints(780, 110, -1, 20));

        jLabel32.setFont(new java.awt.Font("Calibri", 0, 14)); // NOI18N
        jLabel32.setForeground(new java.awt.Color(255, 255, 255));
        jLabel32.setText("Max. 16 °C");
        jPanel2.add(jLabel32, new org.netbeans.lib.awtextra.AbsoluteConstraints(780, 130, -1, -1));

        jLabel37.setFont(new java.awt.Font("Calibri", 0, 14)); // NOI18N
        jLabel37.setForeground(new java.awt.Color(255, 255, 255));
        jLabel37.setText("Min. 10 °C");
        jPanel2.add(jLabel37, new org.netbeans.lib.awtextra.AbsoluteConstraints(470, 110, -1, 20));

        jLabel38.setFont(new java.awt.Font("Calibri", 0, 14)); // NOI18N
        jLabel38.setForeground(new java.awt.Color(255, 255, 255));
        jLabel38.setText("Max. 16 °C");
        jPanel2.add(jLabel38, new org.netbeans.lib.awtextra.AbsoluteConstraints(470, 130, -1, -1));

        jLabel40.setFont(new java.awt.Font("Calibri", 0, 14)); // NOI18N
        jLabel40.setForeground(new java.awt.Color(255, 255, 255));
        jLabel40.setText("Min. 10 °C");
        jPanel2.add(jLabel40, new org.netbeans.lib.awtextra.AbsoluteConstraints(340, 110, -1, 20));

        jLabel41.setFont(new java.awt.Font("Calibri", 0, 14)); // NOI18N
        jLabel41.setForeground(new java.awt.Color(255, 255, 255));
        jLabel41.setText("Max. 16 °C");
        jPanel2.add(jLabel41, new org.netbeans.lib.awtextra.AbsoluteConstraints(340, 130, -1, -1));

        jLabel43.setFont(new java.awt.Font("Calibri", 0, 14)); // NOI18N
        jLabel43.setForeground(new java.awt.Color(255, 255, 255));
        jLabel43.setText("Min. 10 °C");
        jPanel2.add(jLabel43, new org.netbeans.lib.awtextra.AbsoluteConstraints(230, 110, -1, 20));

        jLabel44.setFont(new java.awt.Font("Calibri", 0, 14)); // NOI18N
        jLabel44.setForeground(new java.awt.Color(255, 255, 255));
        jLabel44.setText("Max. 16 °C");
        jPanel2.add(jLabel44, new org.netbeans.lib.awtextra.AbsoluteConstraints(230, 130, -1, -1));

        jLabel9.setIcon(new javax.swing.ImageIcon(getClass().getResource("/IMG/Partly Cloudy Day_49px.png"))); // NOI18N
        jPanel2.add(jLabel9, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 70, -1, -1));

        jButton1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/IMG/Refresh_25px.png"))); // NOI18N
        jButton1.setBorder(null);
        jButton1.setContentAreaFilled(false);
        jPanel2.add(jButton1, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 10, -1, -1));

        jSeparator10.setForeground(new java.awt.Color(255, 255, 255));
        jPanel2.add(jSeparator10, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 0, 1020, -1));

        jPanel12.add(jPanel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 540, 1040, 170));

        jPanel1.setBackground(new java.awt.Color(66, 142, 146));
        jPanel1.setForeground(new java.awt.Color(255, 204, 204));
        jPanel1.setOpaque(false);
        jPanel1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel6.setBorder(javax.swing.BorderFactory.createTitledBorder(""));
        jPanel6.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jScrollPane8.setHorizontalScrollBarPolicy(javax.swing.ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        jScrollPane8.setVerticalScrollBarPolicy(javax.swing.ScrollPaneConstants.VERTICAL_SCROLLBAR_NEVER);

        jTextArea8.setBackground(new java.awt.Color(0, 0, 0));
        jTextArea8.setColumns(20);
        jTextArea8.setFont(new java.awt.Font("Calibri", 1, 14)); // NOI18N
        jTextArea8.setForeground(new java.awt.Color(255, 255, 255));
        jTextArea8.setLineWrap(true);
        jTextArea8.setRows(5);
        jTextArea8.setText("Arrestoi i dyshuari i gjashtë për sulmin në Mançester");
        jTextArea8.setWrapStyleWord(true);
        jTextArea8.setOpaque(false);
        jScrollPane8.setViewportView(jTextArea8);

        jPanel6.add(jScrollPane8, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 10, 190, 60));

        jScrollPane9.setHorizontalScrollBarPolicy(javax.swing.ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        jScrollPane9.setVerticalScrollBarPolicy(javax.swing.ScrollPaneConstants.VERTICAL_SCROLLBAR_NEVER);

        jTextArea9.setBackground(new java.awt.Color(0, 0, 0));
        jTextArea9.setColumns(20);
        jTextArea9.setFont(new java.awt.Font("Calibri", 0, 11)); // NOI18N
        jTextArea9.setForeground(new java.awt.Color(255, 255, 255));
        jTextArea9.setLineWrap(true);
        jTextArea9.setRows(5);
        jTextArea9.setText("Ky 83-vjeçar nga Obiliqi, që është tifoz i flaktë edhe i skuadrës “KEK”, rrëfen këtë “pasion” të pazakontë të tij.\n\nPër më shumë, shikojeni storjet e përgatitur nga Klan Kosova.");
        jScrollPane9.setViewportView(jTextArea9);

        jPanel6.add(jScrollPane9, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 80, 170, 180));

        jButton9.setFont(new java.awt.Font("Calibri", 0, 11)); // NOI18N
        jButton9.setForeground(new java.awt.Color(255, 255, 255));
        jButton9.setText("Shikoni me shume..");
        jButton9.setBorder(null);
        jButton9.setContentAreaFilled(false);
        jPanel6.add(jButton9, new org.netbeans.lib.awtextra.AbsoluteConstraints(110, 310, -1, -1));

        jPanel1.add(jPanel6, new org.netbeans.lib.awtextra.AbsoluteConstraints(800, 40, 210, 330));

        jPanel7.setBackground(new java.awt.Color(255, 255, 255));
        jPanel7.setBorder(javax.swing.BorderFactory.createTitledBorder(""));
        jPanel7.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jScrollPane4.setHorizontalScrollBarPolicy(javax.swing.ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        jScrollPane4.setVerticalScrollBarPolicy(javax.swing.ScrollPaneConstants.VERTICAL_SCROLLBAR_NEVER);
        jScrollPane4.setOpaque(false);

        jTextArea4.setBackground(new java.awt.Color(0, 0, 0));
        jTextArea4.setColumns(20);
        jTextArea4.setFont(new java.awt.Font("Calibri", 1, 14)); // NOI18N
        jTextArea4.setForeground(new java.awt.Color(255, 255, 255));
        jTextArea4.setLineWrap(true);
        jTextArea4.setRows(5);
        jTextArea4.setText("Arrestoi i dyshuari i gjashtë për sulmin në Mançester");
        jTextArea4.setWrapStyleWord(true);
        jTextArea4.setOpaque(false);
        jScrollPane4.setViewportView(jTextArea4);

        jPanel7.add(jScrollPane4, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 10, 190, 60));

        jScrollPane5.setHorizontalScrollBarPolicy(javax.swing.ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        jScrollPane5.setVerticalScrollBarPolicy(javax.swing.ScrollPaneConstants.VERTICAL_SCROLLBAR_NEVER);

        jTextArea5.setBackground(new java.awt.Color(0, 0, 0));
        jTextArea5.setColumns(20);
        jTextArea5.setFont(new java.awt.Font("Calibri", 0, 11)); // NOI18N
        jTextArea5.setForeground(new java.awt.Color(255, 255, 255));
        jTextArea5.setLineWrap(true);
        jTextArea5.setRows(5);
        jTextArea5.setText("Ky 83-vjeçar nga Obiliqi, që është tifoz i flaktë edhe i skuadrës “KEK”, rrëfen këtë “pasion” të pazakontë të tij.\n\nPër më shumë, shikojeni storjet e përgatitur nga Klan Kosova.");
        jScrollPane5.setViewportView(jTextArea5);

        jPanel7.add(jScrollPane5, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 80, 170, 190));

        jButton6.setFont(new java.awt.Font("Calibri", 0, 11)); // NOI18N
        jButton6.setForeground(new java.awt.Color(255, 255, 255));
        jButton6.setText("Shikoni me shume..");
        jButton6.setBorder(null);
        jButton6.setContentAreaFilled(false);
        jPanel7.add(jButton6, new org.netbeans.lib.awtextra.AbsoluteConstraints(110, 310, -1, -1));

        jPanel1.add(jPanel7, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 40, 210, 330));

        jPanel8.setBackground(new java.awt.Color(255, 255, 255));
        jPanel8.setBorder(javax.swing.BorderFactory.createTitledBorder(""));
        jPanel8.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jScrollPane2.setHorizontalScrollBarPolicy(javax.swing.ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        jScrollPane2.setVerticalScrollBarPolicy(javax.swing.ScrollPaneConstants.VERTICAL_SCROLLBAR_NEVER);

        jTextArea2.setBackground(new java.awt.Color(0, 0, 0));
        jTextArea2.setColumns(20);
        jTextArea2.setFont(new java.awt.Font("Calibri", 1, 14)); // NOI18N
        jTextArea2.setForeground(new java.awt.Color(255, 255, 255));
        jTextArea2.setLineWrap(true);
        jTextArea2.setRows(5);
        jTextArea2.setText("Arrestoi i dyshuari i gjashtë për sulmin në Mançester");
        jTextArea2.setWrapStyleWord(true);
        jTextArea2.setAutoscrolls(false);
        jScrollPane2.setViewportView(jTextArea2);

        jPanel8.add(jScrollPane2, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 10, 190, 60));

        jScrollPane1.setHorizontalScrollBarPolicy(javax.swing.ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        jScrollPane1.setVerticalScrollBarPolicy(javax.swing.ScrollPaneConstants.VERTICAL_SCROLLBAR_NEVER);

        jTextArea1.setBackground(new java.awt.Color(0, 0, 0));
        jTextArea1.setColumns(20);
        jTextArea1.setFont(new java.awt.Font("Calibri", 0, 11)); // NOI18N
        jTextArea1.setForeground(new java.awt.Color(255, 255, 255));
        jTextArea1.setLineWrap(true);
        jTextArea1.setRows(5);
        jTextArea1.setText("Ky 83-vjeçar nga Obiliqi, që është\n tifoz i flaktë edhe i skuadrës “KEK”, rrëfen këtë “pasion” të pazakontë të tij.\n\nPër më shumë, shikojeni storjet e përgatitur nga Klan Kosova.");
        jScrollPane1.setViewportView(jTextArea1);

        jPanel8.add(jScrollPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 80, 180, 180));

        jButton7.setFont(new java.awt.Font("Calibri", 0, 11)); // NOI18N
        jButton7.setForeground(new java.awt.Color(255, 255, 255));
        jButton7.setText("Shikoni me shume..");
        jButton7.setBorder(null);
        jButton7.setContentAreaFilled(false);
        jPanel8.add(jButton7, new org.netbeans.lib.awtextra.AbsoluteConstraints(110, 310, -1, -1));

        jPanel1.add(jPanel8, new org.netbeans.lib.awtextra.AbsoluteConstraints(300, 40, 210, 330));

        jPanel9.setBorder(javax.swing.BorderFactory.createTitledBorder(""));
        jPanel9.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jScrollPane6.setHorizontalScrollBarPolicy(javax.swing.ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        jScrollPane6.setToolTipText("");
        jScrollPane6.setVerticalScrollBarPolicy(javax.swing.ScrollPaneConstants.VERTICAL_SCROLLBAR_NEVER);

        jTextArea6.setBackground(new java.awt.Color(0, 0, 0));
        jTextArea6.setColumns(20);
        jTextArea6.setFont(new java.awt.Font("Calibri", 1, 14)); // NOI18N
        jTextArea6.setForeground(new java.awt.Color(255, 255, 255));
        jTextArea6.setLineWrap(true);
        jTextArea6.setRows(5);
        jTextArea6.setText("Arrestoi i dyshuari i gjashtë për sulmin në Mançester");
        jTextArea6.setWrapStyleWord(true);
        jTextArea6.setOpaque(false);
        jScrollPane6.setViewportView(jTextArea6);

        jPanel9.add(jScrollPane6, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 10, 190, 60));

        jScrollPane7.setHorizontalScrollBarPolicy(javax.swing.ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        jScrollPane7.setVerticalScrollBarPolicy(javax.swing.ScrollPaneConstants.VERTICAL_SCROLLBAR_NEVER);

        jTextArea7.setBackground(new java.awt.Color(0, 0, 0));
        jTextArea7.setColumns(20);
        jTextArea7.setFont(new java.awt.Font("Calibri", 0, 11)); // NOI18N
        jTextArea7.setForeground(new java.awt.Color(255, 255, 255));
        jTextArea7.setLineWrap(true);
        jTextArea7.setRows(5);
        jTextArea7.setText("Ky 83-vjeçar nga Obiliqi, që është tifoz i flaktë edhe i skuadrës “KEK”, rrëfen këtë “pasion” të pazakontë të tij.\n\nPër më shumë, shikojeni storjet e përgatitur nga Klan Kosova.");
        jScrollPane7.setViewportView(jTextArea7);

        jPanel9.add(jScrollPane7, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 80, 170, 180));

        jButton8.setFont(new java.awt.Font("Calibri", 0, 11)); // NOI18N
        jButton8.setForeground(new java.awt.Color(255, 255, 255));
        jButton8.setText("Shikoni me shume..");
        jButton8.setBorder(null);
        jButton8.setContentAreaFilled(false);
        jPanel9.add(jButton8, new org.netbeans.lib.awtextra.AbsoluteConstraints(110, 310, -1, -1));

        jPanel1.add(jPanel9, new org.netbeans.lib.awtextra.AbsoluteConstraints(550, 40, 210, 330));

        jLabel1.setFont(new java.awt.Font("Calibri", 1, 20)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(255, 255, 255));
        jLabel1.setText("LajmiNet");
        jPanel1.add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(860, 10, -1, -1));

        jLabel2.setFont(new java.awt.Font("Calibri", 1, 20)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(255, 255, 255));
        jLabel2.setText("Telegrafi");
        jPanel1.add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(90, 10, -1, -1));

        jLabel3.setFont(new java.awt.Font("Calibri", 1, 20)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(255, 255, 255));
        jLabel3.setText("Gazeta Express");
        jPanel1.add(jLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(340, 10, -1, -1));

        jLabel6.setFont(new java.awt.Font("Calibri", 1, 20)); // NOI18N
        jLabel6.setForeground(new java.awt.Color(255, 255, 255));
        jLabel6.setText("Kosova Press");
        jPanel1.add(jLabel6, new org.netbeans.lib.awtextra.AbsoluteConstraints(600, 10, -1, -1));

        jPanel12.add(jPanel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 1040, 400));

        jPanel3.setBackground(new java.awt.Color(66, 142, 146));
        jPanel3.setOpaque(false);
        jPanel3.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel5.setBorder(javax.swing.BorderFactory.createTitledBorder(""));
        jPanel5.setOpaque(false);
        jPanel5.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel12.setFont(new java.awt.Font("Calibri", 0, 12)); // NOI18N
        jLabel12.setForeground(new java.awt.Color(255, 255, 255));
        jLabel12.setText("Trajnime ne UBT Campus");
        jPanel5.add(jLabel12, new org.netbeans.lib.awtextra.AbsoluteConstraints(24, 13, -1, -1));

        jLabel21.setFont(new java.awt.Font("Calibri", 0, 11)); // NOI18N
        jLabel21.setForeground(new java.awt.Color(255, 255, 255));
        jLabel21.setText("26.05.2017");
        jPanel5.add(jLabel21, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 90, -1, -1));

        jScrollPane3.setHorizontalScrollBarPolicy(javax.swing.ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        jScrollPane3.setVerticalScrollBarPolicy(javax.swing.ScrollPaneConstants.VERTICAL_SCROLLBAR_NEVER);

        jTextArea3.setBackground(new java.awt.Color(0, 0, 0));
        jTextArea3.setColumns(20);
        jTextArea3.setFont(new java.awt.Font("Calibri", 0, 11)); // NOI18N
        jTextArea3.setForeground(new java.awt.Color(255, 255, 255));
        jTextArea3.setLineWrap(true);
        jTextArea3.setRows(5);
        jTextArea3.setText("Trajnimi do te behet ne Lipjane. Ne ora    13:00.");
        jScrollPane3.setViewportView(jTextArea3);

        jPanel5.add(jScrollPane3, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 30, 250, 50));

        jPanel3.add(jPanel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 40, 300, 110));

        jButton2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/IMG/More_30px.png"))); // NOI18N
        jButton2.setContentAreaFilled(false);
        jPanel3.add(jButton2, new org.netbeans.lib.awtextra.AbsoluteConstraints(260, 600, -1, -1));

        jButton3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/IMG/Plus_50px.png"))); // NOI18N
        jButton3.setContentAreaFilled(false);
        jPanel3.add(jButton3, new org.netbeans.lib.awtextra.AbsoluteConstraints(90, 630, -1, -1));

        jButton4.setIcon(new javax.swing.ImageIcon(getClass().getResource("/IMG/Settings_28px.png"))); // NOI18N
        jButton4.setContentAreaFilled(false);
        jPanel3.add(jButton4, new org.netbeans.lib.awtextra.AbsoluteConstraints(240, 0, -1, -1));

        jButton5.setIcon(new javax.swing.ImageIcon(getClass().getResource("/IMG/Activity Feed_50px.png"))); // NOI18N
        jButton5.setContentAreaFilled(false);
        jPanel3.add(jButton5, new org.netbeans.lib.awtextra.AbsoluteConstraints(180, 630, -1, -1));

        jPanel12.add(jPanel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(1050, 0, 320, 710));

        jSeparator3.setOrientation(javax.swing.SwingConstants.VERTICAL);
        jPanel12.add(jSeparator3, new org.netbeans.lib.awtextra.AbsoluteConstraints(1042, 40, 10, 670));

        jPanel4.setBackground(new java.awt.Color(66, 142, 146));
        jPanel4.setOpaque(false);
        jPanel4.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel10.setBackground(new java.awt.Color(255, 255, 255));
        jPanel10.setBorder(javax.swing.BorderFactory.createTitledBorder(""));
        jPanel10.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jScrollPane10.setHorizontalScrollBarPolicy(javax.swing.ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        jScrollPane10.setVerticalScrollBarPolicy(javax.swing.ScrollPaneConstants.VERTICAL_SCROLLBAR_NEVER);

        jTextArea10.setBackground(new java.awt.Color(0, 0, 0));
        jTextArea10.setColumns(20);
        jTextArea10.setFont(new java.awt.Font("Calibri", 0, 11)); // NOI18N
        jTextArea10.setForeground(new java.awt.Color(255, 255, 255));
        jTextArea10.setLineWrap(true);
        jTextArea10.setRows(5);
        jTextArea10.setText("Takime ne ora 13:00 ne UBT\nCAMPUS");
        jScrollPane10.setViewportView(jTextArea10);

        jPanel10.add(jScrollPane10, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 30, 170, 40));

        jTextField2.setFont(new java.awt.Font("Calibri", 1, 12)); // NOI18N
        jTextField2.setForeground(new java.awt.Color(255, 255, 255));
        jTextField2.setText("Laura Berisha");
        jTextField2.setBorder(null);
        jPanel10.add(jTextField2, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 10, -1, -1));

        jPanel4.add(jPanel10, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 10, 210, 100));

        jButton10.setFont(new java.awt.Font("Calibri", 0, 11)); // NOI18N
        jButton10.setForeground(new java.awt.Color(255, 255, 255));
        jButton10.setText("Shiko te gjitha...");
        jButton10.setBorder(null);
        jButton10.setContentAreaFilled(false);
        jButton10.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        jPanel4.add(jButton10, new org.netbeans.lib.awtextra.AbsoluteConstraints(915, 0, 120, 20));

        jPanel12.add(jPanel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 410, 1040, 120));
        jPanel12.add(jSeparator2, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 400, 1010, -1));

        jLabel138.setIcon(new javax.swing.ImageIcon(getClass().getResource("/IMG/Finale.jpg"))); // NOI18N
        jPanel12.add(jLabel138, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, -1, -1));

        getContentPane().add(jPanel12, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 1370, 710));

        pack();
    }// </editor-fold>//GEN-END:initComponents

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
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(Main.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Main.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Main.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Main.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Main().setVisible(true);
            }
        });
    }

    
    
    
    
    
    
    
    
    
    /**
     * @param useri Inicializimi i perdoruesit
     * @param city Inicializimi i City [qytetit], te dhenat <city> i mer nga <useri>
     * @param dailyList Inicializimi i listes me mot ditor [perdoret vetem ne rastet kur ka te ruajtur mot ditor lokalisht]
     * @param today Inicializimi i Today [moti aktual]
     * @param daily Inicializimi i Daily [moti ditor]
     * @param userRepo Inicializimi i grupit te metodave qe kontrollon informatat e perdoruesit <useri> [username,city_id]
     * @param weatherRepo Inicializimi i grupit te metodave qe kontrollon lidhjen me databaze ose server [yahoo.com]
     * @param URL_HOST Pjesa e pare e URL per lidhje me YahooWeather [per tu kompletuar si URL shtohet pjesa e dyte <line:169> dhe pjesa e trete <line:56>]
     * @param URL_FOOTER Pjesa e trete e URL per lidhje me YahooWeather, definon tipin e te dhenave [JSONObject] njesine matese [Celsious]
     * @param URL Bashkimi i URL_HOST + URL_CityID + URL_FOOTER [URL e plote per qasje ne YahooWeather <line:169>]
     */
    Users useri ;
    City city;
    List<Daily> dailyList;
    Today today = new Today();
    Daily daily = new Daily();
    UsersRepository userRepo = new UsersRepository();
    WeatherRepository weatherRepo = new WeatherRepository();
    final String URL_HOST = "https://query.yahooapis.com/v1/public/yql?q=select%20*%20from%20weather.forecast%20where%20woeid%20in%20(select%20woeid%20from%20geo.places(1)%20where%20woeid%20%3D%22";             
    final String URL_FOOTER = "%22)%20and%20u%3D%22c%22&format=json&env=store%3A%2F%2Fdatatables.org%2Falltableswithkeys";
    String URL;

 
    
    /*
    *METODAT:
    * 1)    Validimi i perdoruesit
    * 2)    Perkthim i emrit te dites
    * 3)    Pastrim i databazes
    * 4)    Marrja e te dhenave lokalisht
    * 5)    Marrja e te dhenave Online
    * 6)    Ruajtja e motit lokalisht
    * 7)    Shfaqja e te dhenave tek perdoruesi
    * 8)    Shfaqja e ikones
    * 9)    Ndryshimi i prapavise
    */
    
    /**1)    <Validimi i perdoruesit>   */
    //Kontrollo nese perdoruesi egziston 
    private void checkUser(){           
      try{
                    
          useri = userRepo.getUser("doni","123456"); 
          city = useri.getCityId();
          //Nese perdoruesi ka te caktuar qytetin vazhdo me pjesen e motit 
          if(city.getId()>0)
            getWeatherLocaly();
          else
              throw new Exception("Perdoruesi nuk e ka te caktuar qytetin");
            

           //RemindersRepository remindersRep = new RemindersRepository();
           //remindersRep.getReminders(useri);
      }catch(Exception e){       
          try {
              throw new Exception(e);
          } catch (Exception ex) {
              Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
          }
      }
    }
    
    



    
    
    /**3)    <Pastrim i databazes>    */
    //Fshij te gjitha te dhenat e motit te ruajtura lokalisht per qytetin e perdoruesit
    private void clearCache(){
        try {
        //Fshij te dhenat per javen e ardhshme ne databazen lokale (nese ka), dhe shto te rejat
        weatherRepo.clearDailyWeather(city);
        
        //Fshij te dhenat per diten e sotme ne databazen lokale (nese ka), dhe shto te rejat
        weatherRepo.clearTodayWeather(city);
        
        } catch (Exceptions ex) {
             System.out.println("Clear Cache: "+ex);
        }
    }
    
    
    /**4)    <Marrja e te dhenave lokalisht>    */
    //Mer te dhenat e motit lokalisht
    private void getWeatherLocaly(){ 
        Date date = new Date();
        try {
        today = weatherRepo.getWeather(city,date);
        dailyList = weatherRepo.getDailyWeather(city,date);
        getWeatherOnline();
        } catch (Exceptions ex) {
         System.out.println("Get Weather Localy Error: "+ex);
        }
    }
    
    
    /**5)    <Marrja e te dhenave Online>   */ 
    //Mer te dhenat e motit nga YahooWeather dhe ruaj ato lokalisht
    private void getWeatherOnline(){ 
        //Bashko hostin e URL me ID te qytetit dhe ne fund shto edhe konfigurimet [moti te kthehet si JSON Object]
        URL = URL_HOST + city.getZip() + URL_FOOTER; 
        try{
            JSONObject yahooWeather = weatherRepo.getYahooWeather(URL);
            parseJsonFeed(yahooWeather);
        }catch(IOException | JSONException e){
            System.out.println("Get Weather Online Error: "+e);
        }
    }
    //Kthe nga JSON Objekt ne te dhena te gatshme per paraqitje dhe ruajtje
    private void parseJsonFeed(JSONObject response) {
         //Pastro databazen lokale nga te dhenat e motit
         clearCache();
        
        
         /***Moti i sotem
          * @param df Formati i dates e cila do kthehet nga String ne Date 
          * @param currentWind Shpejtesia e eres per momentin
          * @param currentDay Emri i dites se sotme 
          * @param cDate String qe mbane daten e sotme per tu konvertuar ne Date() currentDate
          * @param currentDate Data e sotme
          * @param currentCondition Kushtet e motit per momentin [(0-47) kushtet aktuale, (3200) not available]
          * 
          */  
         DateFormat df = new SimpleDateFormat("dd MMMM yyyy");
         String currentWind, currentDay , cDate;
         Date currentDate;
         short currentCondition, currentTemp;
         
         /***Moti ditor
          * @param dailyDate Data e dites se dhene
          * @param dailyDay Emri i dites se dhene
          * @param dailyCondition Kushtet e motit per diten e dhen
          * @param dailyMax Temperatura maksimale per diten e dhene
          * @param dailyMin Temperatura minimale per diten e dhene
          */
         Date dailyDate;
         String dailyDay;
         short dailyCondition, dailyMax, dailyMin;
        
         
         /**
         * @alias Moti i sotem 
         * 
         * Perditeso motin momental
         * @param currentTemp       =   temperatura momentale
         * @param currentWind       =   shpejtesia e eres per momentin
         * @param currentDay        =   emri i dites se sotme
         * @param currentCondition  =   kushtet atmosferike
         */ 
        try {
            /***
             * Moti i sotem
             */
           currentTemp = (short) response
                    .getJSONObject("query")
                    .getJSONObject("results")
                    .getJSONObject("channel")
                    .getJSONObject("item")
                    .getJSONObject("condition")
                    .getInt("temp");
            currentWind = response
                    .getJSONObject("query")
                    .getJSONObject("results")
                    .getJSONObject("channel")
                    .getJSONObject("wind")
                    .getString("speed");                    
            currentDay = response
                    .getJSONObject("query")
                    .getJSONObject("results")
                    .getJSONObject("channel")
                    .getString("lastBuildDate");
            currentDay = userRepo.setDay(currentDay.substring(0, 3));
            cDate = response
                    .getJSONObject("query")
                    .getJSONObject("results")
                    .getJSONObject("channel")
                    .getString("lastBuildDate");
            cDate = cDate.substring(5,16); 
            currentDate = df.parse(cDate);
            currentCondition = (short) response
                    .getJSONObject("query")
                    .getJSONObject("results")
                    .getJSONObject("channel")
                    .getJSONObject("item")
                    .getJSONObject("condition")
                    .getInt("code"); 
            
            
            //Deklaro vargun dailyWeather i cili permbane motin per 5 ditet ne vijim
            JSONArray dailyWeather = response
                    .getJSONObject("query")
                    .getJSONObject("results")
                    .getJSONObject("channel")
                    .getJSONObject("item")
                    .getJSONArray("forecast");
            
            //mer 5 elementet e para te vargut [5 ditet e motit] 
            for (int i = 0; i < 6; i++) {
                //Mer te dhenat e motit nga vargu specifikisht per diten e dhene [i]
                // nese i=0, mer te dhenat e motit per diten e pare (sot)
                // nese i=1, mer te dhenat e motit per diten e dyte (neser)
                JSONObject getDailyWeatherByDay = (JSONObject) dailyWeather.get(i);
                
                //Mer daten nga JSON Objekti si String, dhe ktheje ne date
                dailyDate = df.parse(getDailyWeatherByDay.getString("date"));
                
                //Perkthe emrin e dites [Mon - E Hene]
                dailyDay = userRepo.setDay(getDailyWeatherByDay.getString("day"));
                dailyCondition = (short) getDailyWeatherByDay.getInt("code");
                dailyMax = (short) getDailyWeatherByDay.getInt("high");
                dailyMin = (short) getDailyWeatherByDay.getInt("low");
                //Ruaj te dhenat e motit lokalisht  
                weatherRepo.updateDailyWeatherInLocalhost(dailyDate, dailyDay, dailyCondition, dailyMax, dailyMin,city);
                //Paraqit te dhenat e motit ditor tek perdoruesi ne baze te renditjes [(i=0) - pozita 1]
                displayDailyWeather(dailyDate,dailyDay,dailyCondition,dailyMax,dailyMin,i);
            }
            
            //Ruaj te dhenat e motit lokalisht
            weatherRepo.updateTodayWeatherInLocalhost(currentDay,
                                            currentDate,
                                            (short) currentCondition,
                                            (short) currentTemp,city);
            
            //Paraqit te dhenat e motit te sotem tek perdoruesi
            displayTodayWeather(currentTemp,currentWind,currentDay,currentCondition);
            
        }catch(JSONException | ParseException e){
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, e);
        }
    }
    
    


    
    
    /**7)    <Shfaqja e te dhenave tek perdoruesi>    */
    private void displayTodayWeather(int currentTemp,String currentWind,String currentDay,int currentCondition){
        //Paraqit ikonen, dergo kushtet e motit dhe diten e sotme [0 - dita e sotme]
        setIcon(String.valueOf(currentCondition),0);
        
        jLabel25.setText(currentTemp+" °C");
        jLabel17.setText(currentDay);
    }
    private void displayDailyWeather(Date dailyDate,String dailyDay,short dailyCondition,short dailyMax,short dailyMin,int i) {
        //Paraqit ikonen, dergo kushtet e motit dhe diten [(i=0) sot, (i=1) neser]
        setIcon(String.valueOf(dailyCondition),i);
        
            switch (i){
                case 0:
                    jLabel4.setText(dailyDay);
                    jLabel43.setText("Min. "+dailyMin+" °C");
                    jLabel44.setText("Max. "+dailyMax+" °C");
                    //setIcon(i, code);*/
                    break;
                case 1:
                    jLabel26.setText(dailyDay);
                    jLabel40.setText("Min. "+dailyMin+" °C");
                    jLabel41.setText("Max. "+dailyMax+" °C");
                    /*setIcon(i, code);*/
                    break;
                case 2:
                    jLabel8.setText(dailyDay);
                    jLabel37.setText("Min. "+dailyMin+" °C");
                    jLabel38.setText("Max. "+dailyMax+" °C");
                    /*setIcon(i, code);*/
                    break;
                case 3:
                    jLabel11.setText(dailyDay);
                    jLabel16.setText("Min. "+dailyMin+" °C");
                    jLabel27.setText("Max. "+dailyMax+" °C");
                    /*setIcon(i, code);*/
                    break;
                case 4:
                    jLabel14.setText(dailyDay);
                    jLabel31.setText("Min. "+dailyMin+" °C");
                    jLabel32.setText("Max. "+dailyMax+" °C");
                    /*setIcon(i, code);/*/
                    break;
                case 5:
                    jLabel20.setText(dailyDay);
                    jLabel34.setText("Min. "+dailyMin+" °C");
                    jLabel35.setText("Max. "+dailyMax+" °C");
                    /*setIcon(i, code);/*/
                
                default:
                    break;
        }
    }
    
    /**8)   <Shfaqja e ikonave>   */
    //Gjej ikonen bazuar ne kushtet e motit
    private void setIcon(String code, int i){
        String iconSource;
        short backgroundCode;
                if (code.matches("0|1|2")) {
                    iconSource = "mot_me_ere.ico";
                    backgroundCode = 0;
                } else if (code.matches("3|4|37|38|39")) {
                    iconSource = "vetetima.ico";
                    backgroundCode = 1;
                } else if (code.matches("5|6|7|25")) {
                    iconSource = "bore_me_shi.ico";
                    backgroundCode = 2;
                } else if (code.matches("9|10")) {
                    iconSource = "ngrice.ico";
                    backgroundCode = 3;
                } else if (code.matches("47|45|10|11|12|37|38|39")) {
                    iconSource = "rrebesh.ico";
                    backgroundCode = 4;
                } else if (code.matches("13|14|15|16|17|18|46|41|42|43")) {
                    iconSource = "bore.ico";
                    backgroundCode = 5;
                } else if (code.matches("19|20|21|22|23|24")) {
                    iconSource = "mjegull.ico";
                    backgroundCode = 6;
                } else if (code.matches("26|27|28|29|30|44")) {
                    iconSource = "mot_me_re.ico";
                    backgroundCode = 7;
                } else if (code.matches("31|32|33|34|36")) {
                    iconSource = "mot_me_diell.ico";
                    backgroundCode = 8;
                } else if (code.matches("35")) {
                    iconSource = "riga_lokale_shiu.ico";
                    backgroundCode = 9;
                } else {
                    iconSource = "erro_404.ico";
                    backgroundCode = 10;
                }
                if(i==0) setBackground(backgroundCode);
                displayIcon(iconSource, i);
    }
    //Paraqit ikonen e dhene [iconSource] tek pozicioni i caktuar 
    //  [(i=0) Moti aktuar, (i=1,2,3) Moti ditor]
    private void displayIcon(String iconSource, int i){
        switch(i){
            case 0:
                //icon0.setIcon(iconSource)
                break;
            case 1:
                //icon1.setIcon(iconSource)
                break; 
            case 2:
                //icon2.setIcon(iconSource)
                break;
            case 3:
                //icon3.setIcon(iconSource)
                break;
            case 4:
                //icon4.setIcon(iconSource)
                break;
            case 5:
                //icon5.setIcon(iconSource)
                break;
            default:
                break; 
        }
    }
    
    
    /**9)   <Ndryshimi i prapavise>   */
    //Ndrysho prapavine bazuar ne kushtet e motit [(conditionCode=1)]
    private void setBackground(short conditionCode){
        String imageSource; 
        
        switch(conditionCode){ 
            case 0:
                imageSource = "mot_me_ere.jpg";
                break;
            case 1:
                imageSource = "vetetima.jpg";
                break;
            case 2:
                imageSource = "bore_me_shi.jpg";
                break;
            case 3:
                imageSource = "ngrice.jpg";
                break;
            case 4:
                imageSource = "rrebesh.jpg";
                break;
            case 5:
                imageSource = "bore.jpg";
                break;
            case 6:
                imageSource = "mjegull.jpg";
                break;
            case 7:
                imageSource = "mot_me_re.jpg";
                break;
            case 8:
                imageSource = "mot_me_diell.jpg";
                break;
            case 9:
                imageSource = "riga_lokale_shiu.jpg";
                break;
            default:
                imageSource = "error_404.jpg";
                break;
        }
        //backgroundImgae.setImage(imageSource);
    }
    
    
    
    
    /**10) <Set Rem >*/
    public void setRem(Reminders r, int isset){
        if(isset==1)
            System.out.println(r.getRemindersTitle());
    }
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    /** <---------- NETBEANS AUTO GENERATED ---------->*/
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton10;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JButton jButton4;
    private javax.swing.JButton jButton5;
    private javax.swing.JButton jButton6;
    private javax.swing.JButton jButton7;
    private javax.swing.JButton jButton8;
    private javax.swing.JButton jButton9;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel138;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel19;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel20;
    private javax.swing.JLabel jLabel21;
    private javax.swing.JLabel jLabel25;
    private javax.swing.JLabel jLabel26;
    private javax.swing.JLabel jLabel27;
    private javax.swing.JLabel jLabel29;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel31;
    private javax.swing.JLabel jLabel32;
    private javax.swing.JLabel jLabel34;
    private javax.swing.JLabel jLabel35;
    private javax.swing.JLabel jLabel37;
    private javax.swing.JLabel jLabel38;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel40;
    private javax.swing.JLabel jLabel41;
    private javax.swing.JLabel jLabel43;
    private javax.swing.JLabel jLabel44;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel10;
    private javax.swing.JPanel jPanel12;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JPanel jPanel8;
    private javax.swing.JPanel jPanel9;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane10;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JScrollPane jScrollPane5;
    private javax.swing.JScrollPane jScrollPane6;
    private javax.swing.JScrollPane jScrollPane7;
    private javax.swing.JScrollPane jScrollPane8;
    private javax.swing.JScrollPane jScrollPane9;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JSeparator jSeparator10;
    private javax.swing.JSeparator jSeparator2;
    private javax.swing.JSeparator jSeparator3;
    private javax.swing.JTextArea jTextArea1;
    private javax.swing.JTextArea jTextArea10;
    private javax.swing.JTextArea jTextArea2;
    private javax.swing.JTextArea jTextArea3;
    private javax.swing.JTextArea jTextArea4;
    private javax.swing.JTextArea jTextArea5;
    private javax.swing.JTextArea jTextArea6;
    private javax.swing.JTextArea jTextArea7;
    private javax.swing.JTextArea jTextArea8;
    private javax.swing.JTextArea jTextArea9;
    private javax.swing.JTextField jTextField2;
    // End of variables declaration//GEN-END:variables
}
