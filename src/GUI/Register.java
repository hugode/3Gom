/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GUI;

import BL.City;
import BL.Users;
import DAL.Exceptions;
import DAL.UsersRepository;


import java.awt.Color;
import java.awt.HeadlessException;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.swing.JOptionPane;

/**
 *
 * @author Hugo
 */
public class Register extends javax.swing.JFrame {

    
    
 int cityId;   
 Users user;
 UsersRepository userRepo = new UsersRepository();
    
 
 
    public Register() {
        initComponents();
        this.setLocationRelativeTo(null);
        this.getRootPane().setDefaultButton(ruajButoni);
        
        
        emriTxt.setOpaque(false);
        emriTxt.setBackground(new Color(0, 0, 0, 0));
        
        usernameTxt.setOpaque(false);
        usernameTxt.setBackground(new Color(0, 0, 0, 0));
        
        passwordTxt.setOpaque(false);
        passwordTxt.setBackground(new Color(0, 0, 0, 0));
        
        repeatTxt.setOpaque(false);
        repeatTxt.setBackground(new Color(0, 0, 0, 0));
        
       
        
        ruajButoni.setBackground(new Color(50, 50, 50, 150));
        
        
         
    }


    //fshij tekstin nga hapsirat
    public void clearInputBoxes() {
     emriTxt.setText("");
     usernameTxt.setText("");
     passwordTxt.setText("");
     repeatTxt.setText("");
    }
    




/**     @-VALIDIMI
* Ketu fillojne funksionet per kontrollimin e shenimeve nga perdoruesi 
* psh: nese eshte username ne rregull, a ka emri numra etj
* jane te pershtatshme per validim
**/    
    //kontrollo nese eshte stringu valid
    private boolean isValid(String str) {
     str = str.replaceAll("\\s", "");
     return str.length() > 0 ? true : false;
    }

    //kontrollo per hapsira 
    private boolean hasSpaces(String s) {
     Pattern pattern = Pattern.compile("\\s");
     Matcher matcher = pattern.matcher(s);
     return matcher.find();
    }

    //kontrollo per cdo karakter tjeter perveq A-Z, 0-9 dhe . (pika)
    private boolean hasSpecialChars(String s) {     
     //nuk ka rendesi nese karakteret jane shkronja te medha ose te vogla (CASE_INSENSITIVE)
     Pattern p = Pattern.compile("[^a-z0-9. ]", Pattern.CASE_INSENSITIVE);
     Matcher m = p.matcher(s);
     return m.find();
    }

    //kontrollo nese emri eshte ne rregull
    private boolean badName(String s) {
        
     Pattern p = Pattern.compile("[^a-z \\s]", Pattern.CASE_INSENSITIVE);
     Matcher m = p.matcher(s);
     return m.find();
    }

    //kontrollo nese Username eshte valid, dmth nuk ka space ose karaktere speciale
    private void checkUser() {
      //nese ka hapsira paraqit error mesazh
      
     if (hasSpaces(usernameTxt.getText())) {
      JOptionPane.showMessageDialog(this, "Hapsirat nuk lejohen ne Username");
     }
     
     else {
      //nesee ka karaktere speciale paraqit error mesazh
      if (hasSpecialChars(usernameTxt.getText()))
       JOptionPane.showMessageDialog(this, "Karakteret speciale nuk lejohen ne Username");
      else {
        //nese username eshte ne rregull thirr metoden regUser() 
        //per vazhdimin e kontrollimit te te dhenave
        //ne menyre qe te regjistorhet perdoruesi
       try{
           regUser();
       }catch(Exception e){
          System.out.println(e);
       }
      }
     }
    }

/** Mbarimi i pjeses se funksioneve @-Validimi **/




/**     @-Kontrollimi
* Ketu fillojne funksionet per kontrollimin e databazes per shenimet e perdoruesit
* psh: nese ekziston perdoruesi me username qe eshte i shenuar
**/   

    //mer ID nga databaza per qytetin me emrin e dhene
    private int getCityById(String cityId) {      
     if (isValid(cityId)) {
      try {
        //kerko nga UserRepository ID te qytetit me stringun e dhene
        //nese arrin te marr ID ktheje ate ID
     
       return userRepo.getCityId(cityId);
      } catch (Exception e) {
        //nese nuk gjendet ID, paraqit error mesazhin
       JOptionPane.showMessageDialog(this, e.getMessage());
      }
     }
     return 0;
    }

    //Kontrollo nese ekziston perdoruesi me username te kerkuar per perdorim nga perdoruesi i ri
    private boolean userClean(String str) {
     try {
      if (userRepo.checkUser(str))
       return false;
     } catch (Exception ex) {
      Logger.getLogger(Register.class.getName()).log(Level.SEVERE, null, ex);
     }
     return true;
    }
    

    private void finalReg(String emri, String username, String password, int city) {
     try {
          
         
       City c = userRepo.getCity(1);

       
       user = new Users();
       user.setName(emri);
       user.setUsername(username);
       user.setPassword(password);
       user.setCityId(c);
       if (userClean(username)) {
           userRepo.create(user);
           JOptionPane.showMessageDialog(this, "Regjistrimi perfundoi me sukses");
            Login login = new Login();
            login.setVisible(true);
            this.dispose();
           
       } else {
        JOptionPane.showMessageDialog(this, "Perdoruesi ekziston");
       }
      

     } catch (Exceptions | HeadlessException e) {
      JOptionPane.showMessageDialog(this, e.getMessage());
     }


    }
    
    private void regUser() throws Exceptions {
        
        
     String emri = emriTxt.getText();
     String username = usernameTxt.getText();
     String password = passwordTxt.getText();
     String repeat = repeatTxt.getText();
     int city = userRepo.getCityId("Prishtine");
    

 
if(badName(emri))
{
    
  JOptionPane.showMessageDialog(this, "Emri ka karaktere speciale");  
 
}
else if(emri.startsWith(" "))
{
    JOptionPane.showMessageDialog(this, "Nuk lejohen hapsirat ne fillim te emrit");
}
else if(badName(username))
{
   JOptionPane.showMessageDialog(this, "Username ka karaktere speciale");
}
else if(emri.equals(""))
{
    JOptionPane.showMessageDialog(this, "Sheno Emrin");
}
else if(username.equals(""))
{
    JOptionPane.showMessageDialog(this, "Sheno Username");
}
else if(password.equals(""))
{
    JOptionPane.showMessageDialog(this, "Sheno Paswordin");
}
else if(repeat.equals(""))
{
     JOptionPane.showMessageDialog(this, "Perserit Paswordin");
}



else
{

    
    if(password.length()>5)
    {  
      
        if (password.equals(repeat))
        {
            if(emri.length()>3){
                if(username.length()>3)
                    finalReg(emri,username,password,city);
                else
                    JOptionPane.showMessageDialog(this, "Username shum i shkurter");
            }
            else
                JOptionPane.showMessageDialog(this, "Emri shum i shkurter");
            
        }
        else
        {
          JOptionPane.showMessageDialog(this, "Paswordi nuk eshte i njejte ne dy hapsirat !");  
        }
    }
    else
    {
        JOptionPane.showMessageDialog(this, "Paswordi shum i shkurte");
    }
    
}

     
    }      


    
    
 
private boolean rubrikat(){
     String emri = emriTxt.getText();
     String username = usernameTxt.getText();
     String password = passwordTxt.getText();
     String repeat = repeatTxt.getText();
     String qyteti = "Prishtine";
     
    if(emri.length()== 0 && username.length() == 0 && password.length()==0 && repeat.length()==0 && qyteti.length()==0)
        return false;    
return true;
}



    
    

    
    
    
   

 
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel2 = new javax.swing.JLabel();
        jTextField2 = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        jPanel3 = new javax.swing.JPanel();
        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        emriTxt = new javax.swing.JTextField();
        jSeparator1 = new javax.swing.JSeparator();
        jLabel4 = new javax.swing.JLabel();
        usernameTxt = new javax.swing.JTextField();
        jSeparator2 = new javax.swing.JSeparator();
        jLabel5 = new javax.swing.JLabel();
        passwordTxt = new javax.swing.JPasswordField();
        jSeparator3 = new javax.swing.JSeparator();
        jLabel6 = new javax.swing.JLabel();
        repeatTxt = new javax.swing.JPasswordField();
        jSeparator4 = new javax.swing.JSeparator();
        jButton1 = new javax.swing.JButton();
        jLabel14 = new javax.swing.JLabel();
        jLabel13 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        jLabel12 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        jSeparator6 = new javax.swing.JSeparator();
        ruajButoni = new javax.swing.JButton();
        jLabel16 = new javax.swing.JLabel();

        jLabel2.setText("jLabel2");

        jTextField2.setText("jTextField1");

        jLabel3.setText("Emri ");

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setMaximumSize(new java.awt.Dimension(720, 500));
        setMinimumSize(new java.awt.Dimension(720, 500));
        setPreferredSize(new java.awt.Dimension(720, 500));
        setResizable(false);
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel3.setBackground(new java.awt.Color(0, 0, 0));
        jPanel3.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());
        getContentPane().add(jPanel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, -1, 603));

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));
        jPanel1.setForeground(new java.awt.Color(255, 255, 255));
        jPanel1.setDoubleBuffered(false);
        jPanel1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel1.setFont(new java.awt.Font("Calibri", 1, 36)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(255, 255, 255));
        jLabel1.setText("REGJISTROHU");
        jPanel1.add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(440, 120, -1, -1));

        jLabel8.setIcon(new javax.swing.ImageIcon(getClass().getResource("/IMG/Add User Male_75px.png"))); // NOI18N
        jPanel1.add(jLabel8, new org.netbeans.lib.awtextra.AbsoluteConstraints(500, 30, -1, -1));

        jLabel11.setFont(new java.awt.Font("Calibri", 1, 14)); // NOI18N
        jLabel11.setForeground(new java.awt.Color(255, 255, 255));
        jLabel11.setText("EMRI");
        jPanel1.add(jLabel11, new org.netbeans.lib.awtextra.AbsoluteConstraints(450, 190, 70, 20));

        emriTxt.setFont(new java.awt.Font("Calibri", 0, 14)); // NOI18N
        emriTxt.setForeground(new java.awt.Color(255, 255, 255));
        emriTxt.setAutoscrolls(false);
        emriTxt.setBorder(null);
        emriTxt.setCaretColor(new java.awt.Color(255, 255, 255));
        emriTxt.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                emriTxtActionPerformed(evt);
            }
        });
        jPanel1.add(emriTxt, new org.netbeans.lib.awtextra.AbsoluteConstraints(450, 210, 170, 20));
        jPanel1.add(jSeparator1, new org.netbeans.lib.awtextra.AbsoluteConstraints(450, 230, 170, 10));

        jLabel4.setFont(new java.awt.Font("Calibri", 1, 14)); // NOI18N
        jLabel4.setForeground(new java.awt.Color(255, 255, 255));
        jLabel4.setText("PERDORUESI");
        jPanel1.add(jLabel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(450, 240, -1, -1));

        usernameTxt.setFont(new java.awt.Font("Calibri", 0, 14)); // NOI18N
        usernameTxt.setForeground(new java.awt.Color(255, 255, 255));
        usernameTxt.setAutoscrolls(false);
        usernameTxt.setBorder(null);
        usernameTxt.setCaretColor(new java.awt.Color(255, 255, 255));
        usernameTxt.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                usernameTxtActionPerformed(evt);
            }
        });
        jPanel1.add(usernameTxt, new org.netbeans.lib.awtextra.AbsoluteConstraints(450, 260, 170, 20));
        jPanel1.add(jSeparator2, new org.netbeans.lib.awtextra.AbsoluteConstraints(450, 280, 170, 10));

        jLabel5.setFont(new java.awt.Font("Calibri", 1, 14)); // NOI18N
        jLabel5.setForeground(new java.awt.Color(255, 255, 255));
        jLabel5.setText("FJALËKALIMI");
        jPanel1.add(jLabel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(450, 290, -1, -1));

        passwordTxt.setFont(new java.awt.Font("Calibri", 0, 14)); // NOI18N
        passwordTxt.setForeground(new java.awt.Color(255, 255, 255));
        passwordTxt.setAutoscrolls(false);
        passwordTxt.setBorder(null);
        passwordTxt.setCaretColor(new java.awt.Color(255, 255, 255));
        jPanel1.add(passwordTxt, new org.netbeans.lib.awtextra.AbsoluteConstraints(450, 310, 170, 20));
        jPanel1.add(jSeparator3, new org.netbeans.lib.awtextra.AbsoluteConstraints(450, 330, 170, 10));

        jLabel6.setFont(new java.awt.Font("Calibri", 1, 14)); // NOI18N
        jLabel6.setForeground(new java.awt.Color(255, 255, 255));
        jLabel6.setText("PËRSËRIT FJALËKALIMIN");
        jPanel1.add(jLabel6, new org.netbeans.lib.awtextra.AbsoluteConstraints(450, 340, -1, -1));

        repeatTxt.setFont(new java.awt.Font("Calibri", 0, 14)); // NOI18N
        repeatTxt.setForeground(new java.awt.Color(255, 255, 255));
        repeatTxt.setAutoscrolls(false);
        repeatTxt.setBorder(null);
        repeatTxt.setCaretColor(new java.awt.Color(255, 255, 255));
        repeatTxt.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                repeatTxtActionPerformed(evt);
            }
        });
        jPanel1.add(repeatTxt, new org.netbeans.lib.awtextra.AbsoluteConstraints(450, 360, 170, 20));
        jPanel1.add(jSeparator4, new org.netbeans.lib.awtextra.AbsoluteConstraints(450, 380, 170, 10));

        jButton1.setBackground(new java.awt.Color(32, 33, 35));
        jButton1.setFont(new java.awt.Font("Calibri", 1, 18)); // NOI18N
        jButton1.setForeground(new java.awt.Color(102, 153, 255));
        jButton1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/IMG/Left_40px.png"))); // NOI18N
        jButton1.setAutoscrolls(true);
        jButton1.setBorder(null);
        jButton1.setContentAreaFilled(false);
        jButton1.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });
        jPanel1.add(jButton1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 10, 70, 30));

        jLabel14.setIcon(new javax.swing.ImageIcon(getClass().getResource("/IMG/Share_80px.png"))); // NOI18N
        jPanel1.add(jLabel14, new org.netbeans.lib.awtextra.AbsoluteConstraints(210, 350, -1, -1));

        jLabel13.setIcon(new javax.swing.ImageIcon(getClass().getResource("/IMG/News_80px.png"))); // NOI18N
        jPanel1.add(jLabel13, new org.netbeans.lib.awtextra.AbsoluteConstraints(70, 350, -1, -1));

        jLabel9.setIcon(new javax.swing.ImageIcon(getClass().getResource("/IMG/Tregom.png"))); // NOI18N
        jPanel1.add(jLabel9, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 210, -1, -1));

        jLabel12.setIcon(new javax.swing.ImageIcon(getClass().getResource("/IMG/Todo List_80px.png"))); // NOI18N
        jPanel1.add(jLabel12, new org.netbeans.lib.awtextra.AbsoluteConstraints(200, 120, -1, -1));

        jLabel10.setIcon(new javax.swing.ImageIcon(getClass().getResource("/IMG/Partly Cloudy Day_80px.png"))); // NOI18N
        jPanel1.add(jLabel10, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 120, -1, -1));

        jSeparator6.setForeground(new java.awt.Color(255, 255, 255));
        jSeparator6.setOrientation(javax.swing.SwingConstants.VERTICAL);
        jPanel1.add(jSeparator6, new org.netbeans.lib.awtextra.AbsoluteConstraints(350, 0, 10, 600));

        ruajButoni.setBackground(new java.awt.Color(51, 102, 255));
        ruajButoni.setFont(new java.awt.Font("Calibri", 0, 14)); // NOI18N
        ruajButoni.setForeground(new java.awt.Color(255, 255, 255));
        ruajButoni.setText("Regjistrohu");
        ruajButoni.setBorder(javax.swing.BorderFactory.createTitledBorder(""));
        ruajButoni.setBorderPainted(false);
        ruajButoni.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        ruajButoni.setOpaque(false);
        ruajButoni.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ruajButoniActionPerformed(evt);
            }
        });
        jPanel1.add(ruajButoni, new org.netbeans.lib.awtextra.AbsoluteConstraints(470, 400, 120, 30));

        jLabel16.setIcon(new javax.swing.ImageIcon(getClass().getResource("/IMG/WDF_778052.jpg"))); // NOI18N
        jPanel1.add(jLabel16, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, -1, -1));

        getContentPane().add(jPanel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 720, 600));

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void emriTxtActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_emriTxtActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_emriTxtActionPerformed

    private void repeatTxtActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_repeatTxtActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_repeatTxtActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
         Login login = new Login();
         login.setVisible(true);
         this.dispose();
    }//GEN-LAST:event_jButton1ActionPerformed

    private void usernameTxtActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_usernameTxtActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_usernameTxtActionPerformed

    private void ruajButoniActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ruajButoniActionPerformed
        if(rubrikat()){
                
    String qyteti = "Prishtine";
     if (qyteti.length() > 3) {
      cityId = 1;

      if (cityId > 0) {
       checkUser();
      }
     } else {
      JOptionPane.showMessageDialog(this, "Emri i qytetit eshte shume i shkurter");
     }   
        }else{
            JOptionPane.showMessageDialog(this, "Ploteso te gjitha rubrikat");
        }
    }//GEN-LAST:event_ruajButoniActionPerformed



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
            java.util.logging.Logger.getLogger(Register.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Register.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Register.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Register.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Register().setVisible(true);
            }
        });
    }
    
    
    
    
    
    
    
    
    
    

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTextField emriTxt;
    private javax.swing.JButton jButton1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JSeparator jSeparator2;
    private javax.swing.JSeparator jSeparator3;
    private javax.swing.JSeparator jSeparator4;
    private javax.swing.JSeparator jSeparator6;
    private javax.swing.JTextField jTextField2;
    private javax.swing.JPasswordField passwordTxt;
    private javax.swing.JPasswordField repeatTxt;
    private javax.swing.JButton ruajButoni;
    private javax.swing.JTextField usernameTxt;
    // End of variables declaration//GEN-END:variables
}
