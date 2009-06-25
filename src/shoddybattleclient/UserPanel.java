/*
 * UserPanel.java
 *
 * Created on May 12, 2009, 1:48:11 PM
 *
 * This file is a part of Shoddy Battle.
 * Copyright (C) 2009  Catherine Fitzpatrick and Benjamin Gwin
 *
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Affero General Public License
 * as published by the Free Software Foundation; either version 3
 * of the License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Affero General Public License for more details.
 *
 * You should have received a copy of the GNU Affero General Public License
 * along with this program; if not, visit the Free Software Foundation, Inc.
 * online at http://gnu.org.
 */

package shoddybattleclient;

import java.awt.Dimension;
import java.awt.FileDialog;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.MediaTracker;
import javax.swing.BorderFactory;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import shoddybattleclient.network.ServerLink;
import shoddybattleclient.network.ServerLink.ChallengeMediator;
import shoddybattleclient.shoddybattle.Pokemon;
import shoddybattleclient.shoddybattle.Pokemon.Gender;
import shoddybattleclient.utils.CloseableTabbedPane.CloseableTab;
import shoddybattleclient.utils.TeamFileParser;

/**
 *
 * @author ben
 */
public class UserPanel extends javax.swing.JPanel implements CloseableTab {

    private static class SpritePanel extends JPanel {
        private Image m_image;
        public SpritePanel(String species, Gender g, boolean shiny, String repository) {
            setBorder(BorderFactory.createEtchedBorder());
            try {
                m_image = GameVisualisation.getSprite(species, true,
                      g != Gender.GENDER_FEMALE, shiny, repository);
                //m_image = m_image.getScaledInstance(32, 32, Image.SCALE_SMOOTH);
            } catch (Exception e) {
                m_image = null;
            }
            MediaTracker tracker = new MediaTracker(this);
            tracker.addImage(m_image, 0);
            try {
                tracker.waitForAll();
            } catch (Exception e) {

            }
            setToolTipText(species);
        }
        @Override
        public Dimension getPreferredSize() {
            return new Dimension(40, 40);
        }
        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            g.drawImage(m_image, 0, 0, this);
        }
    }

    private final ServerLink m_link;
    private String m_opponent;
    private int m_idx;
    private Pokemon[] m_team;
    private boolean m_incoming = false;
    private int m_n;
    private int m_generation;
    private boolean m_waiting = false;

    /** Creates new form UserPanel */
    public UserPanel(String name, ServerLink link, int index) {
        initComponents();
        lblName.setText(name);
        m_opponent = name;
        m_link = link;
        m_idx = index;
        panelSprites.setLayout(new GridLayout(2, 3));
        for (int i = 0; i < 6; i++) {
            panelSprites.add(new SpritePanel(null, null, false, null));
        }
        lblMessage.setText("<html>I am a polymath so don't challenge me unless you want to lose</html>");
    }

    public void setIncoming() {
        btnChallenge.setText("Accept");
        cmbRules.setEnabled(false);
        cmbN.setEnabled(false);
        cmbGen.setEnabled(false);
        m_incoming = true;
    }

    public void setOptions(int n, int generation) {
        if (m_waiting) return;
        m_n = n;
        m_generation = generation;
        cmbN.setSelectedIndex(n - 1);
        cmbGen.setSelectedIndex(generation);
    }

    public ChallengeMediator getMediator() {
        return new ChallengeMediator() {
            public Pokemon[] getTeam() {
                return m_team;
            }
            public void informResolved(boolean accepted) {
                close();
            }
            public String getOpponent() {
                return m_opponent;
            }
            public int getGeneration() {
                return m_generation;
            }
            public int getActivePartySize() {
                return m_n;
            }
        };
    }

    public void close() {
        m_link.getLobby().closeTab(m_idx);
    }

    public boolean informClosed() {
        if (m_waiting) {
            return JOptionPane.showConfirmDialog(this,
                    "Are you sure you wish to cancel your challenge?") == JOptionPane.YES_OPTION;
        } else {
            if (m_incoming) {
                m_link.resolveChallenge(m_opponent, false, null);
            }
            return true;
        }
    }

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        lblName = new javax.swing.JLabel();
        lblMessage = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jTabbedPane1 = new javax.swing.JTabbedPane();
        jPanel2 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        cmbRules = new javax.swing.JComboBox();
        jLabel2 = new javax.swing.JLabel();
        cmbN = new javax.swing.JComboBox();
        jLabel4 = new javax.swing.JLabel();
        cmbGen = new javax.swing.JComboBox();
        panelSprites = new javax.swing.JPanel();
        btnLoad = new javax.swing.JButton();
        btnChallenge = new javax.swing.JButton();
        jPanel3 = new javax.swing.JPanel();

        setOpaque(false);

        jPanel1.setOpaque(false);

        lblName.setFont(new java.awt.Font("Lucida Grande", 1, 16));
        lblName.setText("bearzly");

        lblMessage.setVerticalAlignment(javax.swing.SwingConstants.TOP);

        jLabel3.setFont(new java.awt.Font("Lucida Grande", 1, 13));
        jLabel3.setText("Rankings:");

        org.jdesktop.layout.GroupLayout jPanel1Layout = new org.jdesktop.layout.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .add(jPanel1Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                    .add(lblMessage, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 160, Short.MAX_VALUE)
                    .add(lblName)
                    .add(jLabel3))
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(jPanel1Layout.createSequentialGroup()
                .add(lblName)
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(lblMessage, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 135, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(jLabel3)
                .addContainerGap(117, Short.MAX_VALUE))
        );

        jPanel2.setOpaque(false);

        jLabel1.setFont(new java.awt.Font("Lucida Grande", 1, 13));
        jLabel1.setText("Rules:");

        cmbRules.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Standard", "Ubers", "Custom..." }));

        jLabel2.setFont(new java.awt.Font("Lucida Grande", 1, 13));
        jLabel2.setText("Pokemon per side:");

        cmbN.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "1", "2", "3", "4", "5", "6" }));
        cmbN.setSelectedIndex(1);

        jLabel4.setFont(new java.awt.Font("Lucida Grande", 1, 13));
        jLabel4.setText("Generation:");

        cmbGen.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "D/P", "Platinum", "Platinum Fake" }));

        panelSprites.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        panelSprites.setOpaque(false);

        org.jdesktop.layout.GroupLayout panelSpritesLayout = new org.jdesktop.layout.GroupLayout(panelSprites);
        panelSprites.setLayout(panelSpritesLayout);
        panelSpritesLayout.setHorizontalGroup(
            panelSpritesLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(0, 193, Short.MAX_VALUE)
        );
        panelSpritesLayout.setVerticalGroup(
            panelSpritesLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(0, 101, Short.MAX_VALUE)
        );

        btnLoad.setText("Load");
        btnLoad.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnLoadActionPerformed(evt);
            }
        });

        btnChallenge.setText("Challenge");
        btnChallenge.setEnabled(false);
        btnChallenge.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnChallengeActionPerformed(evt);
            }
        });

        org.jdesktop.layout.GroupLayout jPanel2Layout = new org.jdesktop.layout.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(org.jdesktop.layout.GroupLayout.TRAILING, jPanel2Layout.createSequentialGroup()
                .add(20, 20, 20)
                .add(jPanel2Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.TRAILING)
                    .add(org.jdesktop.layout.GroupLayout.LEADING, jPanel2Layout.createSequentialGroup()
                        .add(btnLoad, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 76, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                        .add(btnChallenge, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 115, Short.MAX_VALUE))
                    .add(org.jdesktop.layout.GroupLayout.LEADING, panelSprites, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .add(org.jdesktop.layout.GroupLayout.LEADING, jPanel2Layout.createSequentialGroup()
                        .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                        .add(jLabel4)
                        .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                        .add(cmbGen, 0, 113, Short.MAX_VALUE))
                    .add(org.jdesktop.layout.GroupLayout.LEADING, jPanel2Layout.createSequentialGroup()
                        .add(jLabel1)
                        .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                        .add(cmbRules, 0, 151, Short.MAX_VALUE))
                    .add(org.jdesktop.layout.GroupLayout.LEADING, jPanel2Layout.createSequentialGroup()
                        .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                        .add(jLabel2)
                        .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                        .add(cmbN, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 67, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)))
                .add(166, 166, 166))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(jPanel2Layout.createSequentialGroup()
                .add(jPanel2Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                    .add(jLabel1, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 23, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                    .add(cmbRules))
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(jPanel2Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                    .add(jLabel2, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 20, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                    .add(cmbN, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(jPanel2Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                    .add(jLabel4, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 21, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                    .add(cmbGen))
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(panelSprites, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(jPanel2Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                    .add(btnLoad)
                    .add(btnChallenge))
                .addContainerGap())
        );

        jTabbedPane1.addTab("Basic", jPanel2);

        jPanel3.setOpaque(false);

        org.jdesktop.layout.GroupLayout jPanel3Layout = new org.jdesktop.layout.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(0, 240, Short.MAX_VALUE)
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(0, 254, Short.MAX_VALUE)
        );

        jTabbedPane1.addTab("Advanced", jPanel3);

        org.jdesktop.layout.GroupLayout layout = new org.jdesktop.layout.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(org.jdesktop.layout.GroupLayout.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .add(jPanel1, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(jTabbedPane1, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 261, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(org.jdesktop.layout.GroupLayout.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .add(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.TRAILING)
                    .add(org.jdesktop.layout.GroupLayout.LEADING, jPanel1, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .add(org.jdesktop.layout.GroupLayout.LEADING, jTabbedPane1, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 300, Short.MAX_VALUE))
                .addContainerGap())
        );
    }// </editor-fold>//GEN-END:initComponents

    private void btnChallengeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnChallengeActionPerformed
        if (m_team == null) return;
        if (m_incoming) {
            m_link.resolveChallenge(m_opponent, true, m_team);
        } else {
            m_link.postChallenge(new ChallengeMediator() {
                public Pokemon[] getTeam() {
                    return m_team;
                }
                public void informResolved(boolean accepted) {
                    if (accepted) {
                        m_link.postChallengeTeam(m_opponent, m_team);
                    } else {
                        // todo: internationalisation
                        JOptionPane.showMessageDialog(UserPanel.this,
                                m_opponent + " rejected the challenge.");
                        m_waiting = false;
                        close();
                    }
                    m_waiting = false;
                }
                public String getOpponent() {
                    return m_opponent;
                }
                public int getGeneration() {
                    return cmbGen.getSelectedIndex();
                }
                public int getActivePartySize() {
                    return Integer.parseInt((String)cmbN.getSelectedItem());
                }
            });
            btnChallenge.setEnabled(false);
            btnLoad.setEnabled(false);
            m_waiting = true;
        }
    }//GEN-LAST:event_btnChallengeActionPerformed

    private void btnLoadActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnLoadActionPerformed
        FileDialog fd = new FileDialog(m_link.getLobby(), "Choose a team to load", FileDialog.LOAD);
        fd.setVisible(true);
        if (fd.getFile() == null) return;
        String file = fd.getDirectory() + fd.getFile();
        TeamFileParser tfp = new TeamFileParser();
        m_team = tfp.parseTeam(file);
        if (m_team != null) {
            panelSprites.removeAll();
            panelSprites.repaint();
            for (int i = 0; i < m_team.length; i++) {
                Pokemon p = m_team[i];
                //TODO: repository
                SpritePanel panel = new SpritePanel(p.species, p.gender, p.shiny, null);
                panelSprites.add(panel);
            }
            btnChallenge.setEnabled(true);
        }
    }//GEN-LAST:event_btnLoadActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnChallenge;
    private javax.swing.JButton btnLoad;
    private javax.swing.JComboBox cmbGen;
    private javax.swing.JComboBox cmbN;
    private javax.swing.JComboBox cmbRules;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JTabbedPane jTabbedPane1;
    private javax.swing.JLabel lblMessage;
    private javax.swing.JLabel lblName;
    private javax.swing.JPanel panelSprites;
    // End of variables declaration//GEN-END:variables

}
