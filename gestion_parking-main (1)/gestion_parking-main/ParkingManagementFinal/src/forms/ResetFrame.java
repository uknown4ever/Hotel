/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package forms;

/**
 *
 * @author PC
 */


/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


import dao.UtilisateurDAO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class ResetFrame extends JFrame {
    
    private JTextField txtEmail;
    private JTextField txtToken;
    private JPasswordField txtNouveauMotDePasse;
    private JPasswordField txtConfirmation;
    private JButton btnEnvoyerEmail;
    private JButton btnVerifierToken;
    private JButton btnReinitialiser;
    private JButton btnRetour;
    private JLabel lblMessage;
    
    private UtilisateurDAO utilisateurDAO;
    private String tokenActuel;
    
    public ResetFrame() {
        utilisateurDAO = new UtilisateurDAO();
        initComponents();
        this.setTitle("Récupération de mot de passe");
        this.setLocationRelativeTo(null);
    }
    
    private void initComponents() {
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(500, 550);
        setResizable(false);
        
        // Panneau principal avec le même fond que Auth
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBackground(new Color(255, 204, 204));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        
        // Titre
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        JLabel lblTitre = new JLabel("RÉCUPÉRATION DE MOT DE PASSE");
        lblTitre.setFont(new Font("Tahoma", Font.BOLD, 18));
        panel.add(lblTitre, gbc);
        
        // Section 1: Demande d'email
        gbc.gridwidth = 1;
        gbc.gridy = 1;
        gbc.gridx = 0;
        JLabel lblEmail = new JLabel("Email:");
        lblEmail.setFont(new Font("Tahoma", Font.PLAIN, 14));
        panel.add(lblEmail, gbc);
        
        gbc.gridx = 1;
        txtEmail = new JTextField(20);
        txtEmail.setFont(new Font("Tahoma", Font.PLAIN, 14));
        panel.add(txtEmail, gbc);
        
        gbc.gridy = 2;
        gbc.gridx = 0;
        gbc.gridwidth = 2;
        btnEnvoyerEmail = new JButton("📧 Envoyer le code");
        btnEnvoyerEmail.setBackground(Color.BLACK);
        btnEnvoyerEmail.setForeground(new Color(255, 204, 204));
        btnEnvoyerEmail.setFont(new Font("Tahoma", Font.BOLD, 14));
        btnEnvoyerEmail.setCursor(new Cursor(Cursor.HAND_CURSOR));
        panel.add(btnEnvoyerEmail, gbc);
        
        // Séparateur
        gbc.gridy = 3;
        panel.add(new JSeparator(), gbc);
        
        // Section 2: Saisie du token
        gbc.gridy = 4;
        gbc.gridwidth = 1;
        gbc.gridx = 0;
        JLabel lblToken = new JLabel("Code reçu:");
        lblToken.setFont(new Font("Tahoma", Font.PLAIN, 14));
        panel.add(lblToken, gbc);
        
        gbc.gridx = 1;
        txtToken = new JTextField(10);
        txtToken.setHorizontalAlignment(JTextField.CENTER);
        txtToken.setFont(new Font("Monospaced", Font.BOLD, 20));
        panel.add(txtToken, gbc);
        
        gbc.gridy = 5;
        gbc.gridx = 0;
        gbc.gridwidth = 2;
        btnVerifierToken = new JButton("🔑 Vérifier le code");
        btnVerifierToken.setBackground(Color.BLACK);
        btnVerifierToken.setForeground(new Color(255, 204, 204));
        btnVerifierToken.setFont(new Font("Tahoma", Font.BOLD, 14));
        btnVerifierToken.setCursor(new Cursor(Cursor.HAND_CURSOR));
        panel.add(btnVerifierToken, gbc);
        
        // Section 3: Nouveau mot de passe
        gbc.gridy = 6;
        gbc.gridwidth = 1;
        gbc.gridx = 0;
        JLabel lblNouveau = new JLabel("Nouveau mot de passe:");
        lblNouveau.setFont(new Font("Tahoma", Font.PLAIN, 14));
        panel.add(lblNouveau, gbc);
        
        gbc.gridx = 1;
        txtNouveauMotDePasse = new JPasswordField(15);
        txtNouveauMotDePasse.setFont(new Font("Tahoma", Font.PLAIN, 14));
        panel.add(txtNouveauMotDePasse, gbc);
        
        gbc.gridy = 7;
        gbc.gridx = 0;
        JLabel lblConfirmation = new JLabel("Confirmation:");
        lblConfirmation.setFont(new Font("Tahoma", Font.PLAIN, 14));
        panel.add(lblConfirmation, gbc);
        
        gbc.gridx = 1;
        txtConfirmation = new JPasswordField(15);
        txtConfirmation.setFont(new Font("Tahoma", Font.PLAIN, 14));
        panel.add(txtConfirmation, gbc);
        
        gbc.gridy = 8;
        gbc.gridx = 0;
        gbc.gridwidth = 2;
        btnReinitialiser = new JButton("✅ Réinitialiser");
        btnReinitialiser.setBackground(Color.BLACK);
        btnReinitialiser.setForeground(new Color(255, 204, 204));
        btnReinitialiser.setFont(new Font("Tahoma", Font.BOLD, 14));
        btnReinitialiser.setCursor(new Cursor(Cursor.HAND_CURSOR));
        panel.add(btnReinitialiser, gbc);
        
        // Message
        gbc.gridy = 9;
        lblMessage = new JLabel(" ");
        lblMessage.setForeground(Color.RED);
        lblMessage.setFont(new Font("Tahoma", Font.PLAIN, 12));
        panel.add(lblMessage, gbc);
        
        // Bouton retour
        gbc.gridy = 10;
        btnRetour = new JButton("⬅ Retour à la connexion");
        btnRetour.setBackground(Color.BLACK);
        btnRetour.setForeground(new Color(255, 204, 204));
        btnRetour.setFont(new Font("Tahoma", Font.BOLD, 14));
        btnRetour.setCursor(new Cursor(Cursor.HAND_CURSOR));
        panel.add(btnRetour, gbc);
        
        add(panel);
        
        
        // Désactiver les sections suivantes
        txtToken.setEnabled(false);
        btnVerifierToken.setEnabled(false);
        txtNouveauMotDePasse.setEnabled(false);
        txtConfirmation.setEnabled(false);
        btnReinitialiser.setEnabled(false);
                
        
        // Actions des boutons
        btnEnvoyerEmail.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                envoyerCode();
            }
        });
        
        btnVerifierToken.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                verifierCode();
            }
        });
        
        btnReinitialiser.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                reinitialiserMotDePasse();
            }
        });
        
        btnRetour.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                retourConnexion();
            }
        });
        
        // Permettre "Entrée" pour envoyer le code
        txtEmail.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                envoyerCode();
            }
        });
        
        txtToken.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                verifierCode();
            }
        });
    }
    
    private void envoyerCode() {
        String email = txtEmail.getText().trim();
        
        if (email.isEmpty()) {
            lblMessage.setText("❌ Veuillez entrer votre email");
            return;
        }
        
        // Vérification simple du format email
        if (!email.contains("@") || !email.contains(".")) {
            lblMessage.setText("❌ Format d'email invalide");
            return;
        }
        
        btnEnvoyerEmail.setEnabled(false);
        btnEnvoyerEmail.setText("⏳ Envoi en cours...");
        lblMessage.setText(" ");
        
        // Exécuter dans un thread séparé pour ne pas bloquer l'interface
        SwingWorker<String, Void> worker = new SwingWorker<String, Void>() {
            @Override
            protected String doInBackground() {
                return utilisateurDAO.genererTokenReinitialisation(email);
            }
            
            @Override
            protected void done() {
                try {
                    String token = get();
                    if (token != null) {
                        lblMessage.setForeground(new Color(0, 150, 0));
                        lblMessage.setText("✅ Code envoyé! Vérifiez votre boîte email");
                        txtToken.setEnabled(true);
                        btnVerifierToken.setEnabled(true);
                    } else {
                        lblMessage.setForeground(Color.RED);
                        lblMessage.setText("❌ Email non trouvé dans la base");
                    }
                } catch (Exception e) {
                    lblMessage.setForeground(Color.RED);
                    lblMessage.setText("❌ Erreur: " + e.getMessage());
                } finally {
                    btnEnvoyerEmail.setEnabled(true);
                    btnEnvoyerEmail.setText("📧 Envoyer le code");
                }
            }
        };
        worker.execute();
    }
    
    private void verifierCode() {
        String token = txtToken.getText().trim();
        
        if (token.isEmpty()) {
            lblMessage.setText("❌ Entrez le code reçu");
            return;
        }
        
        if (utilisateurDAO.verifierToken(token)) {
            tokenActuel = token;
            lblMessage.setForeground(new Color(0, 150, 0));
            lblMessage.setText("✅ Code valide!");
            txtNouveauMotDePasse.setEnabled(true);
            txtConfirmation.setEnabled(true);
            btnReinitialiser.setEnabled(true);
            
            // Désactiver les champs précédents
            txtEmail.setEnabled(false);
            btnEnvoyerEmail.setEnabled(false);
            txtToken.setEnabled(false);
            btnVerifierToken.setEnabled(false);
            
        } else {
            lblMessage.setForeground(Color.RED);
            lblMessage.setText("❌ Code invalide ou expiré");
        }
    }
    
    private void reinitialiserMotDePasse() {
        String nouveauMdp = new String(txtNouveauMotDePasse.getPassword());
        String confirmation = new String(txtConfirmation.getPassword());
        
        if (nouveauMdp.isEmpty() || confirmation.isEmpty()) {
            lblMessage.setText("❌ Remplissez tous les champs");
            return;
        }
        
        if (!nouveauMdp.equals(confirmation)) {
            lblMessage.setText("❌ Les mots de passe ne correspondent pas");
            return;
        }
        
        if (nouveauMdp.length() < 6) {
            lblMessage.setText("❌ Minimum 6 caractères");
            return;
        }
        
        boolean reussi = utilisateurDAO.reinitialiserMotDePasse(tokenActuel, nouveauMdp);
        
        if (reussi) {
            JOptionPane.showMessageDialog(this, 
                "✅ Mot de passe réinitialisé avec succès!\nConnectez-vous avec votre nouveau mot de passe.",
                "Succès",
                JOptionPane.INFORMATION_MESSAGE);
            retourConnexion();
        } else {
            lblMessage.setText("❌ Erreur lors de la réinitialisation");
        }
    }
    
    private void retourConnexion() {
        dispose();
        new Auth().setVisible(true);
    }
}