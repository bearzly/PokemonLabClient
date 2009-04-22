/*
 * TeamBuilder.java
 *
 * Created on Apr 4, 2009, 3:53:56 PM
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
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.Writer;
import java.util.ArrayList;
import javax.swing.JFileChooser;
import shoddybattleclient.shoddybattle.*;
import shoddybattleclient.shoddybattle.Pokemon.Gender;
import shoddybattleclient.utils.*;

/**
 *
 * @author ben
 */
public class TeamBuilder extends javax.swing.JFrame {

    private TeamBuilderForm[] m_forms = new TeamBuilderForm[6];
    private ArrayList<PokemonSpecies> m_species;
    private ArrayList<PokemonMove> m_moves;

    /** Creates new form TeamBuilder */
    public TeamBuilder() {
        initComponents();
        long t1 = System.currentTimeMillis();
        MoveListParser mlp = new MoveListParser();
        m_moves = mlp.parseDocument(TeamBuilder.class.getResource("resources/moves2.xml").toString());
        SpeciesListParser parser = new SpeciesListParser();
        m_species = parser.parseDocument(TeamBuilder.class.getResource("resources/species.xml").toString());
        String[] species = getSpeciesList();
        long t2 = System.currentTimeMillis();
        System.out.println("Loaded moves and species info in " + (t2-t1) + " milliseconds");

        //TODO: Catherine claims that she wants other numbers of teams
        for (int i = 0; i < 6; i++) {
            TeamBuilderForm tbf = new TeamBuilderForm(this, species, i);
            m_forms[i] = tbf;
            tabForms.addTab("", tbf);
            tbf.setPokemon(new Pokemon("Bulbasaur", "", false, Gender.GENDER_MALE, 100, "None", "None", "None",
                new String[] {null, null, null, null}, new int[] {3,3,3,3}, new int[] {31,31,31,31,31,31},
                new int[] {0,0,0,0,0,0}), true);
        }
        Dimension d = m_forms[0].getPreferredSize();
        setSize((int)d.getWidth() + 50, (int)d.getHeight() + 100);
    }

    public PokemonSpecies getSpecies(String species) {
        for (PokemonSpecies sp : m_species) {
            if (sp.getName().equals(species)) {
                return sp;
            }
        }
        return null;
    }

    public String[] getSpeciesList() {
        String[] ret = new String[m_species.size()];
        int i = 0;
        for (PokemonSpecies ps : m_species) {
            ret[i++] = ps.getName();
        }
        return ret;
    }

    public void updateTitle(int index, String title) {
        tabForms.setTitleAt(index, title);
    }

    public ArrayList<PokemonMove> getMoveList() {
        return m_moves;
    }

    private void saveTeam() {
        JFileChooser choose = new JFileChooser();
        if (choose.showSaveDialog(this) != JFileChooser.APPROVE_OPTION)
            return;

        String file = choose.getSelectedFile().getPath();

        int dot = file.lastIndexOf('.');
        int slash = file.lastIndexOf(File.separatorChar);
        if (slash > dot) {
            // no extension - so supply the default one
            file += ".sbt";
        }

        int length = m_forms.length;
        StringBuffer buf = new StringBuffer();
        buf.append("<?xml version=\"1.0\" encoding=\"UTF-8\" ?>\n<shoddybattle>\n\n");
        for (int i = 0; i < length; i++) {
            Pokemon p = m_forms[i].getPokemon();
            buf.append(p.toXML());
            buf.append("\n");
        }
        buf.append("</shoddybattle>");

        try {
            Writer output = new PrintWriter(new FileWriter(file));
            output.write(new String(buf));
            output.flush();
            output.close();
        } catch (IOException e) {
            System.out.println("Failed to write team to file");
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

        tabForms = new javax.swing.JTabbedPane();
        jMenuBar1 = new javax.swing.JMenuBar();
        jMenu1 = new javax.swing.JMenu();
        menuNew = new javax.swing.JMenuItem();
        menuLoad = new javax.swing.JMenuItem();
        menuSave = new javax.swing.JMenuItem();
        menuSaveAs = new javax.swing.JMenuItem();
        jSeparator1 = new javax.swing.JSeparator();
        menuExport = new javax.swing.JMenuItem();
        jMenu2 = new javax.swing.JMenu();
        menuRandomise = new javax.swing.JMenuItem();
        menuBox = new javax.swing.JMenuItem();
        jMenuItem6 = new javax.swing.JMenuItem();

        setDefaultCloseOperation(javax.swing.WindowConstants.DO_NOTHING_ON_CLOSE);
        setTitle("Shoddy Battle - Team Builder");
        setLocationByPlatform(true);
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosing(java.awt.event.WindowEvent evt) {
                formWindowClosing(evt);
            }
        });

        jMenu1.setText("File");

        menuNew.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_N, java.awt.event.InputEvent.CTRL_MASK));
        menuNew.setText("New Team");
        jMenu1.add(menuNew);

        menuLoad.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_O, java.awt.event.InputEvent.CTRL_MASK));
        menuLoad.setText("Load Team");
        menuLoad.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                menuLoadActionPerformed(evt);
            }
        });
        jMenu1.add(menuLoad);

        menuSave.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_S, java.awt.event.InputEvent.CTRL_MASK));
        menuSave.setText("Save");
        menuSave.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                menuSaveActionPerformed(evt);
            }
        });
        jMenu1.add(menuSave);

        menuSaveAs.setText("Save As...");
        menuSaveAs.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                menuSaveAsActionPerformed(evt);
            }
        });
        jMenu1.add(menuSaveAs);
        jMenu1.add(jSeparator1);

        menuExport.setText("Export to Text");
        jMenu1.add(menuExport);

        jMenuBar1.add(jMenu1);

        jMenu2.setText("Tools");

        menuRandomise.setText("Move to Front");
        jMenu2.add(menuRandomise);

        menuBox.setText("Randomise Team");
        jMenu2.add(menuBox);

        jMenuItem6.setText("Open Box");
        jMenu2.add(jMenuItem6);

        jMenuBar1.add(jMenu2);

        setJMenuBar(jMenuBar1);

        org.jdesktop.layout.GroupLayout layout = new org.jdesktop.layout.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(layout.createSequentialGroup()
                .addContainerGap()
                .add(tabForms, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 374, Short.MAX_VALUE)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(layout.createSequentialGroup()
                .addContainerGap()
                .add(tabForms, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 258, Short.MAX_VALUE)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void formWindowClosing(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowClosing
        this.dispose();
    }//GEN-LAST:event_formWindowClosing

    private void menuLoadActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_menuLoadActionPerformed
        JFileChooser choose = new JFileChooser();
        if (choose.showOpenDialog(this) != JFileChooser.APPROVE_OPTION)
            return;

        String file = choose.getSelectedFile().getPath();
 
        TeamFileParser tfp = new TeamFileParser();
        Pokemon[] team = tfp.parseTeam(file);
        for (int i = 0; i < m_forms.length; i++) {
            tabForms.removeTabAt(0);
        }
        m_forms = new TeamBuilderForm[team.length];
        for (int i = 0; i < team.length; i++) {
            m_forms[i] = new TeamBuilderForm(this, getSpeciesList(), i);
            tabForms.add("", m_forms[i]);
            m_forms[i].setPokemon(team[i], true);
        }
}//GEN-LAST:event_menuLoadActionPerformed

    private void menuSaveActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_menuSaveActionPerformed
        saveTeam();
}//GEN-LAST:event_menuSaveActionPerformed

    private void menuSaveAsActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_menuSaveAsActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_menuSaveAsActionPerformed

    /**
    * @param args the command line arguments
    */
    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new TeamBuilder().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JMenu jMenu1;
    private javax.swing.JMenu jMenu2;
    private javax.swing.JMenuBar jMenuBar1;
    private javax.swing.JMenuItem jMenuItem6;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JMenuItem menuBox;
    private javax.swing.JMenuItem menuExport;
    private javax.swing.JMenuItem menuLoad;
    private javax.swing.JMenuItem menuNew;
    private javax.swing.JMenuItem menuRandomise;
    private javax.swing.JMenuItem menuSave;
    private javax.swing.JMenuItem menuSaveAs;
    private javax.swing.JTabbedPane tabForms;
    // End of variables declaration//GEN-END:variables

}
