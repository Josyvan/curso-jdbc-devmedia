package br.edu.devmedia.jdbc.util;

import java.awt.Component;

import javax.swing.JOptionPane;

public class MessagensUtil {
public static void addMsg(Component component,String msg){
	JOptionPane.showMessageDialog(component, msg);
}
}
