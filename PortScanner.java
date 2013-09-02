
package port.scanner;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.io.IOException;
import java.net.*;
import java.nio.channels.SocketChannel;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.*;


public class PortScanner extends JFrame implements FocusListener,ActionListener,Runnable {
   
    Thread t = new Thread(this);
    private JButton btnStart,btnStop,btnPause,btnResume;
    private JTextField entrIP,entrPort,entrEndPort;
    private JTextArea showResult;
    private JLabel l1;
    InetAddress rslt = null;
    private String IP;
    private int strtPort,endPort;
    ImageIcon icon;
    Socket s1=null;
    
    PortScanner(){
       
        icon =new ImageIcon("ofs.png");
        setTitle("OFS port scanner");
        setSize(630,600);
        setIconImage(icon.getImage());
        setLayout(null);
        setResizable(false);
        
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setDefaultLookAndFeelDecorated(true);
        l1=new JLabel("Port Scanner by DevilGarg ", SwingConstants.CENTER);
        l1.setFont(new Font("Serif", Font.BOLD, 20));
        l1.setBounds(150,25,300,25);
        add(l1);
        entrIP=new JTextField("Enter IP Here");
        entrIP.setBounds(30,70,200,30);
        entrIP.addFocusListener(this);
        add(entrIP);
        
        entrPort=new JTextField("Start Port");
        entrPort.setBounds(260,70,70,30);
        entrPort.addFocusListener(this);
        add(entrPort);
        
        entrEndPort=new JTextField("End Port");
        entrEndPort.setBounds(360,70,70,30);
        entrEndPort.addFocusListener(this);
        add(entrEndPort);
        
        btnStart=new JButton("Start");
        btnStart.setBounds(200,120,70,30);
        btnStart.addActionListener(this);
        add(btnStart);
        
        btnStop=new JButton("Stop");
        btnStop.setBounds(290,120,70,30);
        btnStop.addActionListener(this);
        add(btnStop);
        
        btnPause=new JButton("Pause");
        btnPause.setBounds(380,120,70,30);
        btnPause.addActionListener(this);
        add(btnPause);
        
        btnResume=new JButton("Resume");
        btnResume.setBounds(470,120,85,30);
        btnResume.addActionListener(this);
        add(btnResume);
        
        showResult=new JTextArea();
        showResult.setBounds(30,200,560,350);
        add(showResult);
        setVisible(true);
     
        
    }
    
    public static void main(String[] args) {
        new PortScanner();
      
    }
    
    @Override
    public void focusGained(FocusEvent e) {
        
        try{
            
            if(e.getSource().equals(entrIP)){
                entrIP.selectAll();
            }
            
            if(e.getSource().equals(entrPort)){
                entrPort.selectAll();
            }
            
            if(e.getSource().equals(entrEndPort)){
                entrEndPort.selectAll();
            }               
         }
        catch(Exception ex){
            System.out.println(ex.getMessage());
            
        }       
    }

    @Override
    public void focusLost(FocusEvent e) {
        
    }
    
    @Override
    public void actionPerformed(ActionEvent e) {
        
        if(e.getSource().equals(btnStart) ){
          
            IP=entrIP.getText().toString();
            strtPort=Integer.parseInt(entrPort.getText());
            endPort=Integer.parseInt(entrEndPort.getText());
         
            try {
                rslt=InetAddress.getByName(IP);
                t.start();
            } catch (IOException ex) {
                Logger.getLogger(PortScanner.class.getName()).log(Level.SEVERE, null, ex);
            } 
         
     } 
     
        if(e.getSource().equals(btnStop)){
           t.stop();
        }
        
        if(e.getSource().equals(btnPause)){
            t.suspend();
        }
        
        if(e.getSource().equals(btnResume)){
                t.resume();
        }
    }
    
    @Override
    public void run(){
        
        while(strtPort<=endPort){
           
           try{
                
               s1=new Socket(rslt,strtPort);
               SocketChannel channel =SocketChannel.open(s1.getRemoteSocketAddress());
               if(channel.isConnected())
               showResult.append(rslt+" is listening at port "+strtPort+ "\n");
               s1.close();
           }
           catch(IOException ex) {
               showResult.append("Server is not listening at port "+strtPort+ "\n");
           }
                      
          strtPort++;
         }
       
        showResult.append("Scan has been Completed");
    }
      
}