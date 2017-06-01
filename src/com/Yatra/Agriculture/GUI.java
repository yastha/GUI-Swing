/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.Yatra.Agriculture;

import com.fazecast.jSerialComm.SerialPort;
import java.awt.Color;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.logging.Level;
import java.util.logging.Logger;
/**
 *
 * @author Astha
 * 
 */
public class GUI extends javax.swing.JFrame {
    String NODE1;
    String NODE2;
    String NODE3;
    
    String temp1;
    String humidity1;
    String moisture1;
    String temp2;
    String humidity2;
    String moisture2;
    String temp3;
    String humidity3;
    String moisture3;
    String content="";
    String[] contentArray;
    public SerialPort [] serialPortArray;
    public SerialPort serialPort;
    Calendar cal;
    File file;
    private FileWriter writer1;
    private FileWriter writer2;
    private FileWriter writer3;
    private InputStream is;
    
    PrintWriter print;
    private OutputStream id;
//print = new PrintWriter(SerialPort.getOutputStream());
    /**
     * Creates new form GUI
     */
    public GUI() {
        initComponents();
        
         try {
            file = new File("Node1.csv");
            writer1 = new FileWriter(file,true);
            
            file = new File("Node2.csv");
            writer2 = new FileWriter(file,true);
            
            file = new File("Node3.csv");
            writer3 = new FileWriter(file,true);
            
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
         
        this.serialPortArray= SerialPort.getCommPorts();
        this.serialPort = serialPortArray[0];
        serialPort.openPort();
        System.out.println(serialPort.getDescriptivePortName()+" connected.");
        id = serialPort.getOutputStream();
        print = new PrintWriter(id);
    }
    
     public void setComPortFieldText()
    {
        if(serialPort.isOpen()){
            comPortField.setForeground(Color.green);
            comPortField.setText(serialPort.getDescriptivePortName()+" Connected.");
        }
        else{
            comPortField.setForeground(Color.red);
            comPortField.setText("No serial port connected.");
        }
    }
public void getSerialString() 
    {
        is = serialPort.getInputStream();
        int i;
        
        try {
           
           for(i=0;i<33;i++){
               content += String.valueOf((char)is.read());
           }
           content = content.substring(0,content.lastIndexOf(","));
           System.out.println(content);
           contentArray = content.trim().split(",");
           if(contentArray[0].equalsIgnoreCase("1"))
           {
               getNode1();
                       }
            if(contentArray[0].equalsIgnoreCase("2"))
           {
               getNode2();
           }
           if(contentArray[0].equalsIgnoreCase("3"))
           {
               getNode3();
           }
          
           
           is.close();
        } catch (IOException | NullPointerException ex) {
            System.out.println(ex.getMessage());
        }
        content = "";
    }


 
public void getNode1()
    {
        NODE1 =contentArray[0];
     Node1.setText(NODE1);
     
    System.out.println("NODE1");
       temp1 = contentArray[1];
       System.out.println("Temperature:"+temp1);
       TempField1.setText(temp1);
       tempSlider1.setValue(Integer.parseInt(temp1));
       
       humidity1=contentArray[2];
        System.out.println("Humidity:"+humidity1);
        HumidityField1.setText(humidity1);
        humiditySlider1.setValue(Integer.parseInt(humidity1));
    
    moisture1 = contentArray[3];
        if (Integer.parseInt(moisture1) >= 10 && Integer.parseInt(moisture1) < 40){
            new Moisture1().setVisible(true);
        }
        System.out.println("Moisture:"+moisture1);
        MoistureField1.setText(moisture1);
        moistureSlider1.setValue(Integer.parseInt(moisture1));
    
        writeToFile1();
        
    }
    public void getNode2(){
      
        NODE2 =contentArray[0];
         Node2.setText(NODE2);
        System.out.println("NODE2"); 
      temp2 = contentArray[1];
       System.out.println("Temperature:"+temp2);
       TempField2.setText(temp2);
       tempSlider2.setValue(Integer.parseInt(temp2)); 
       
        humidity2=contentArray[2];
        System.out.println("Humidity:"+humidity2);
        HumidityField2.setText(humidity2);
        humiditySlider2.setValue(Integer.parseInt(humidity2));
        
        moisture2 = contentArray[3];
        System.out.println("Moisture:"+moisture2);
        MoistureField2.setText(moisture2);
        moistureSlider2.setValue(Integer.parseInt(moisture2));
        
        writeToFile2();
    }
    
    public void getNode3()
    {
       
        NODE3 =contentArray[0];
        Node3.setText(NODE3);
        System.out.println("NODE3");
        temp3 = contentArray[1];
       System.out.println("Temperature:"+temp3);
       TempField3.setText(temp3);
       tempSlider3.setValue(Integer.parseInt(temp3)); 
    
        humidity3=contentArray[2];
        System.out.println("Humidity:"+humidity3);
        HumidityField3.setText(humidity3);
        humiditySlider3.setValue(Integer.parseInt(humidity3));
    
    
        moisture3 = contentArray[3];
        System.out.println("Moisture:"+moisture3);
        MoistureField3.setText(moisture3);
        moistureSlider3.setValue(Integer.parseInt(moisture3));
        
         writeToFile3();
    }
    public void writeToFile1()
    {
        try {
            cal = new GregorianCalendar();
            writer1.append(cal.getTime()+","+temp1+","+humidity1+","+moisture1+"\n");
            writer1.flush();
        } catch (IOException ex) {
            Logger.getLogger(GUI.class.getName()).log(Level.SEVERE, null, ex);
        }
         //serialPort.closePort();
    }
    
    public void writeToFile2(){
        try {
            cal = new GregorianCalendar();
            writer2.append(cal.getTime()+","+temp2+","+humidity2+","+moisture2+"\n");
            writer2.flush();
        } catch (IOException ex) {
            Logger.getLogger(GUI.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
     
     public void writeToFile3(){
        try {
            cal = new GregorianCalendar();
            writer3.append(cal.getTime()+","+temp3+","+humidity3+","+moisture3+"\n");
            writer3.flush();
        } catch (IOException ex) {
            Logger.getLogger(GUI.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        Panel = new javax.swing.JPanel();
        tempSlider3 = new javax.swing.JSlider();
        humiditySlider3 = new javax.swing.JSlider();
        TempField3 = new javax.swing.JTextField();
        MoistureField3 = new javax.swing.JTextField();
        HumidityField3 = new javax.swing.JTextField();
        jLabel11 = new javax.swing.JLabel();
        jLabel12 = new javax.swing.JLabel();
        jLabel13 = new javax.swing.JLabel();
        moistureSlider3 = new javax.swing.JSlider();
        jLabel20 = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();
        jPanel3 = new javax.swing.JPanel();
        tempSlider1 = new javax.swing.JSlider();
        humiditySlider1 = new javax.swing.JSlider();
        moistureSlider1 = new javax.swing.JSlider();
        HumidityField1 = new javax.swing.JTextField();
        MoistureField1 = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        TempField1 = new javax.swing.JTextField();
        jLabel19 = new javax.swing.JLabel();
        jPanel4 = new javax.swing.JPanel();
        tempSlider2 = new javax.swing.JSlider();
        humiditySlider2 = new javax.swing.JSlider();
        moistureSlider2 = new javax.swing.JSlider();
        HumidityField2 = new javax.swing.JTextField();
        MoistureField2 = new javax.swing.JTextField();
        jLabel8 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        TempField2 = new javax.swing.JTextField();
        jLabel18 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jSeparator1 = new javax.swing.JSeparator();
        Database = new javax.swing.JButton();
        jSeparator2 = new javax.swing.JSeparator();
        jSeparator3 = new javax.swing.JSeparator();
        jLabel4 = new javax.swing.JLabel();
        jPanel1 = new javax.swing.JPanel();
        MotorON = new javax.swing.JButton();
        MotorOFF = new javax.swing.JButton();
        jLabel5 = new javax.swing.JLabel();
        jLabel14 = new javax.swing.JLabel();
        jLabel15 = new javax.swing.JLabel();
        jLabel16 = new javax.swing.JLabel();
        comPortField = new javax.swing.JTextField();
        Node2 = new javax.swing.JTextField();
        Node3 = new javax.swing.JTextField();
        Node1 = new javax.swing.JTextField();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        Panel.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        Panel.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        tempSlider3.setOrientation(javax.swing.JSlider.VERTICAL);
        Panel.add(tempSlider3, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 30, 10, -1));

        humiditySlider3.setOrientation(javax.swing.JSlider.VERTICAL);
        Panel.add(humiditySlider3, new org.netbeans.lib.awtextra.AbsoluteConstraints(131, 30, -1, -1));
        Panel.add(TempField3, new org.netbeans.lib.awtextra.AbsoluteConstraints(24, 259, 53, -1));
        Panel.add(MoistureField3, new org.netbeans.lib.awtextra.AbsoluteConstraints(197, 259, 50, -1));
        Panel.add(HumidityField3, new org.netbeans.lib.awtextra.AbsoluteConstraints(113, 259, 49, -1));

        jLabel11.setText("Temperature");
        Panel.add(jLabel11, new org.netbeans.lib.awtextra.AbsoluteConstraints(24, 236, -1, -1));

        jLabel12.setText("Humidity");
        Panel.add(jLabel12, new org.netbeans.lib.awtextra.AbsoluteConstraints(113, 236, -1, -1));

        jLabel13.setText("Moisture");
        Panel.add(jLabel13, new org.netbeans.lib.awtextra.AbsoluteConstraints(197, 236, -1, -1));

        moistureSlider3.setOrientation(javax.swing.JSlider.VERTICAL);
        Panel.add(moistureSlider3, new org.netbeans.lib.awtextra.AbsoluteConstraints(215, 30, -1, -1));

        jLabel20.setIcon(new javax.swing.ImageIcon("C:\\Users\\Family\\Desktop\\oth.PNG")); // NOI18N
        Panel.add(jLabel20, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 280, 300));

        getContentPane().add(Panel, new org.netbeans.lib.awtextra.AbsoluteConstraints(810, 210, 280, 300));

        jLabel1.setFont(new java.awt.Font("Times New Roman", 1, 48)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(0, 255, 51));
        jLabel1.setText("                               Agricultural Monitoring System");
        getContentPane().add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 0, 1190, 70));

        jPanel3.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 0, 0), 1, true));
        jPanel3.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        tempSlider1.setForeground(new java.awt.Color(255, 0, 0));
        tempSlider1.setOrientation(javax.swing.JSlider.VERTICAL);
        jPanel3.add(tempSlider1, new org.netbeans.lib.awtextra.AbsoluteConstraints(32, 30, 20, -1));

        humiditySlider1.setOrientation(javax.swing.JSlider.VERTICAL);
        jPanel3.add(humiditySlider1, new org.netbeans.lib.awtextra.AbsoluteConstraints(121, 30, 20, -1));

        moistureSlider1.setOrientation(javax.swing.JSlider.VERTICAL);
        jPanel3.add(moistureSlider1, new org.netbeans.lib.awtextra.AbsoluteConstraints(214, 30, 19, -1));
        jPanel3.add(HumidityField1, new org.netbeans.lib.awtextra.AbsoluteConstraints(100, 268, 50, -1));
        jPanel3.add(MoistureField1, new org.netbeans.lib.awtextra.AbsoluteConstraints(201, 268, 50, -1));

        jLabel3.setText("Tempurature");
        jPanel3.add(jLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(21, 236, -1, 21));

        jLabel6.setText("Humidity");
        jPanel3.add(jLabel6, new org.netbeans.lib.awtextra.AbsoluteConstraints(109, 236, -1, 21));

        jLabel7.setText("Moisture");
        jPanel3.add(jLabel7, new org.netbeans.lib.awtextra.AbsoluteConstraints(201, 239, -1, -1));
        jPanel3.add(TempField1, new org.netbeans.lib.awtextra.AbsoluteConstraints(21, 268, 52, -1));

        jLabel19.setIcon(new javax.swing.ImageIcon("C:\\Users\\Family\\Desktop\\oth.PNG")); // NOI18N
        jPanel3.add(jLabel19, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 290, 300));

        getContentPane().add(jPanel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 210, 290, 300));

        jPanel4.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        jPanel4.setMaximumSize(new java.awt.Dimension(1290, 750));
        jPanel4.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        tempSlider2.setOrientation(javax.swing.JSlider.VERTICAL);
        jPanel4.add(tempSlider2, new org.netbeans.lib.awtextra.AbsoluteConstraints(32, 30, 10, -1));

        humiditySlider2.setOrientation(javax.swing.JSlider.VERTICAL);
        jPanel4.add(humiditySlider2, new org.netbeans.lib.awtextra.AbsoluteConstraints(131, 30, -1, -1));

        moistureSlider2.setOrientation(javax.swing.JSlider.VERTICAL);
        jPanel4.add(moistureSlider2, new org.netbeans.lib.awtextra.AbsoluteConstraints(221, 30, 19, -1));
        jPanel4.add(HumidityField2, new org.netbeans.lib.awtextra.AbsoluteConstraints(111, 261, 54, -1));
        jPanel4.add(MoistureField2, new org.netbeans.lib.awtextra.AbsoluteConstraints(211, 261, 45, -1));

        jLabel8.setText("Tempurature");
        jPanel4.add(jLabel8, new org.netbeans.lib.awtextra.AbsoluteConstraints(11, 236, -1, -1));

        jLabel9.setText("Humidity");
        jPanel4.add(jLabel9, new org.netbeans.lib.awtextra.AbsoluteConstraints(124, 236, -1, -1));

        jLabel10.setText("Moisture");
        jPanel4.add(jLabel10, new org.netbeans.lib.awtextra.AbsoluteConstraints(211, 236, -1, -1));
        jPanel4.add(TempField2, new org.netbeans.lib.awtextra.AbsoluteConstraints(11, 261, 54, -1));

        jLabel18.setIcon(new javax.swing.ImageIcon("C:\\Users\\Family\\Desktop\\oth.PNG")); // NOI18N
        jPanel4.add(jLabel18, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 300, 300));

        getContentPane().add(jPanel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(400, 210, 300, 300));

        jLabel2.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N
        jLabel2.setText("COMPORT:");
        getContentPane().add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 90, 100, 40));
        getContentPane().add(jSeparator1, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 60, 1530, 40));

        Database.setText("View in database");
        Database.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                DatabaseActionPerformed(evt);
            }
        });
        getContentPane().add(Database, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 550, 150, 40));
        getContentPane().add(jSeparator2, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 750, -1, -1));
        getContentPane().add(jSeparator3, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 662, 1680, 10));

        jLabel4.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N
        jLabel4.setText("@Agriculutural Monitoring System");
        getContentPane().add(jLabel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 680, 230, 20));

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));
        jPanel1.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        jPanel1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        MotorON.setText("ON");
        MotorON.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MotorONActionPerformed(evt);
            }
        });
        jPanel1.add(MotorON, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 60, -1, -1));

        MotorOFF.setText("OFF");
        MotorOFF.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MotorOFFActionPerformed(evt);
            }
        });
        jPanel1.add(MotorOFF, new org.netbeans.lib.awtextra.AbsoluteConstraints(140, 60, -1, -1));

        jLabel5.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel5.setText("     DC MOTOR");
        jPanel1.add(jLabel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(70, 20, 110, 20));

        getContentPane().add(jPanel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(1030, 520, 250, 90));

        jLabel14.setFont(new java.awt.Font("Times New Roman", 1, 12)); // NOI18N
        jLabel14.setText("NODE ");
        getContentPane().add(jLabel14, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 180, 60, 20));

        jLabel15.setFont(new java.awt.Font("Times New Roman", 1, 12)); // NOI18N
        jLabel15.setText(" NODE ");
        getContentPane().add(jLabel15, new org.netbeans.lib.awtextra.AbsoluteConstraints(480, 170, 60, 30));

        jLabel16.setFont(new java.awt.Font("Times New Roman", 1, 12)); // NOI18N
        jLabel16.setText("NODE ");
        getContentPane().add(jLabel16, new org.netbeans.lib.awtextra.AbsoluteConstraints(880, 180, 70, 20));
        getContentPane().add(comPortField, new org.netbeans.lib.awtextra.AbsoluteConstraints(90, 90, 250, 50));
        getContentPane().add(Node2, new org.netbeans.lib.awtextra.AbsoluteConstraints(540, 170, 110, 30));
        getContentPane().add(Node3, new org.netbeans.lib.awtextra.AbsoluteConstraints(930, 170, 100, 30));
        getContentPane().add(Node1, new org.netbeans.lib.awtextra.AbsoluteConstraints(130, 170, 110, 30));

        pack();
    }// </editor-fold>//GEN-END:initComponents
//   public void Display(){
//       id= serialPort.getOutputStream();
//       print = new PrintWriter(id);
//   }
    private void MotorONActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MotorONActionPerformed
        // TODO add your handling code here:
        //Display();
        try {
              print.write('1');
            } catch (Exception ex) {
                System.out.println(ex.getMessage());
            }
            print.flush();
            
    }//GEN-LAST:event_MotorONActionPerformed

    private void MotorOFFActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MotorOFFActionPerformed
        // TODO add your handling code here:
         //Display();
        try {
              print.write('2');
            } catch (Exception ex) {
                System.out.println(ex.getMessage());
            }
            print.flush();
    }//GEN-LAST:event_MotorOFFActionPerformed

    private void DatabaseActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_DatabaseActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_DatabaseActionPerformed

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
            java.util.logging.Logger.getLogger(GUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(GUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(GUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(GUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new GUI().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton Database;
    private javax.swing.JTextField HumidityField1;
    private javax.swing.JTextField HumidityField2;
    private javax.swing.JTextField HumidityField3;
    private javax.swing.JTextField MoistureField1;
    private javax.swing.JTextField MoistureField2;
    private javax.swing.JTextField MoistureField3;
    private javax.swing.JButton MotorOFF;
    private javax.swing.JButton MotorON;
    private javax.swing.JTextField Node1;
    private javax.swing.JTextField Node2;
    private javax.swing.JTextField Node3;
    private javax.swing.JPanel Panel;
    private javax.swing.JTextField TempField1;
    private javax.swing.JTextField TempField2;
    private javax.swing.JTextField TempField3;
    private javax.swing.JTextField comPortField;
    private javax.swing.JSlider humiditySlider1;
    private javax.swing.JSlider humiditySlider2;
    private javax.swing.JSlider humiditySlider3;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel19;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel20;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JSeparator jSeparator2;
    private javax.swing.JSeparator jSeparator3;
    private javax.swing.JSlider moistureSlider1;
    private javax.swing.JSlider moistureSlider2;
    private javax.swing.JSlider moistureSlider3;
    private javax.swing.JSlider tempSlider1;
    private javax.swing.JSlider tempSlider2;
    private javax.swing.JSlider tempSlider3;
    // End of variables declaration//GEN-END:variables
}
