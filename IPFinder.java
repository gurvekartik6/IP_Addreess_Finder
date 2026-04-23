import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.net.*;
import java.io.BufferedReader;
import java.io.InputStreamReader;

public class IPFinder extends JFrame {
    
    private JTextField urlField;
    private JTextArea resultArea;
    private JButton localIPButton, publicIPButton, websiteButton, clearButton;
    
    public IPFinder() {
        setTitle("IP Finder - Local & Public IP");
        setSize(550, 450);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        
        JPanel mainPanel = new JPanel(new BorderLayout(10, 10));
        
        JPanel topPanel = new JPanel(new GridLayout(4, 1, 5, 5));
        topPanel.setBorder(BorderFactory.createTitledBorder("IP Finder Options"));
        
        localIPButton = new JButton("Get Local IP Address");
        publicIPButton = new JButton("Get Public IP Address");
        
        JPanel urlPanel = new JPanel(new BorderLayout(5, 5));
        urlField = new JTextField();
        websiteButton = new JButton("Get Website IP");
        urlPanel.add(new JLabel("Enter Website: "), BorderLayout.WEST);
        urlPanel.add(urlField, BorderLayout.CENTER);
        urlPanel.add(websiteButton, BorderLayout.EAST);
        
        clearButton = new JButton("Clear Results");
        
        topPanel.add(localIPButton);
        topPanel.add(publicIPButton);
        topPanel.add(urlPanel);
        topPanel.add(clearButton);
        
        resultArea = new JTextArea();
        resultArea.setEditable(false);
        resultArea.setFont(new Font("Monospaced", Font.PLAIN, 13));
        resultArea.setBackground(Color.BLACK);
        resultArea.setForeground(Color.GREEN);
        JScrollPane scrollPane = new JScrollPane(resultArea);
        scrollPane.setBorder(BorderFactory.createTitledBorder("Results"));
        
        mainPanel.add(topPanel, BorderLayout.NORTH);
        mainPanel.add(scrollPane, BorderLayout.CENTER);
        mainPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        
        add(mainPanel);
        
        localIPButton.addActionListener(e -> getLocalIP());
        publicIPButton.addActionListener(e -> getPublicIP());
        websiteButton.addActionListener(e -> getWebsiteIP());
        clearButton.addActionListener(e -> resultArea.setText(""));
        
        setVisible(true);
    }
    
    private void getLocalIP() {
        try {
            InetAddress localhost = InetAddress.getLocalHost();
            resultArea.append("Local IP Address: " + localhost.getHostAddress() + "\n");
            resultArea.append("Host Name: " + localhost.getHostName() + "\n\n");
        } catch (UnknownHostException e) {
            resultArea.append("Error: Could not find local IP\n\n");
        }
    }
    
    private void getPublicIP() {
        resultArea.append("Fetching public IP...\n");
        new Thread(() -> {
            try {
                URL url = new URL("https://api.ipify.org");
                BufferedReader reader = new BufferedReader(new InputStreamReader(url.openStream()));
                String publicIP = reader.readLine();
                resultArea.append("Public IP Address: " + publicIP + "\n\n");
                reader.close();
            } catch (Exception e) {
                resultArea.append("Error: Could not find public IP\n\n");
            }
        }).start();
    }
    
    private void getWebsiteIP() {
        JOptionPane.showMessageDialog(this,"Coming Sooon");
    }
    
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new IPFinder());
    }
}