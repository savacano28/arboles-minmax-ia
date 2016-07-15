/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Interfaz;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;

/**
 *
 * @author CORE i5
 */
public class Accion implements ActionListener{

    private JButton boton;
    
    public Accion(JButton boton){
        super();
        this.boton = boton;
    }
    
    @Override
    public void actionPerformed(ActionEvent e) {
        
    }
    
}
