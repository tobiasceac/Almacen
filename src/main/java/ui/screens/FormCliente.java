/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package ui.screens;

import data.model.Cliente;
import excepciones.ClienteAlreadyExistsException;
import excepciones.ClienteNotFoundException;
import excepciones.DataAccessException;
import java.awt.Color;
import java.sql.SQLException;
import java.util.ArrayList;
import javax.swing.JOptionPane;
import net.sf.jasperreports.engine.JRException;
import ui.viewmodel.ClienteViewModel;

/**
 * Formulario principal para la gestión CRUD de clientes.
 * 
 * <p>Esta ventana permite realizar las operaciones de Alta, Baja, Modificación
 * y Consulta de clientes. Utiliza un sistema basado en modos que determina
 * qué campos están habilitados y qué acción se ejecutará al pulsar Aceptar.</p>
 * 
 * <p>El formulario incluye validación en tiempo real de todos los campos,
 * mostrando los datos inválidos en rojo y los válidos en negro. También
 * genera automáticamente la letra del DNI español según el algoritmo oficial.</p>
 * 
 * @author jfeyj
 * @see ClienteViewModel
 * @see data.model.Cliente
 */
public class FormCliente extends javax.swing.JFrame {
    
    private static final java.util.logging.Logger logger = java.util.logging.Logger.getLogger(FormCliente.class.getName());
    
    /**
     * Enum que representa los diferentes modos de operación del formulario.
     * 
     * <ul>
     *   <li>ALTA: Modo de inserción de nuevos clientes</li>
     *   <li>BAJA: Modo de eliminación de clientes existentes</li>
     *   <li>MODIFICACIONES: Modo de actualización de datos de clientes</li>
     *   <li>CONSULTAPORCODIGO: Modo de búsqueda y visualización de clientes</li>
     * </ul>
     */
    private enum Modo {ALTA, BAJA, MODIFICACIONES, CONSULTAPORCODIGO};
    private Modo modo;
    private ClienteViewModel vm = new ClienteViewModel();
    
    /**
     * Creates new form Formulario
     */
    public FormCliente() {
        initComponents();
        desactivateAll();
        setDefaultCloseOperation(javax.swing.WindowConstants.DO_NOTHING_ON_CLOSE); // Desaciva el boton de cerrar
        setLocationRelativeTo(null); // Centra la ventana al inizializar
        setResizable(false); // Desactiva el redimensionamiento
        // Hacer que el campo de letra del DNI no sea editable
        niflText.setEditable(false);
        // Establecer el color de fondo para indicar que no es editable
        niflText.setBackground(new Color(240, 240, 240));
        niflText.setFocusable(false);
        niflText.setEnabled(false);
    }
    
    /**
     * Deshabilita todos los campos y botones del formulario.
     * 
     * <p>Este método se utiliza para resetear el formulario a su estado inicial,
     * donde ningún campo está disponible para edición hasta que el usuario
     * seleccione una operación desde el menú.</p>
     */
    public void desactivateAll(){
        codigoText.setEnabled(false);
        nifnText.setEnabled(false);
        nombreText.setEnabled(false);
        apellidosText.setEnabled(false);
        domicilioText.setEnabled(false); 
        cpText.setEnabled(false);
        localidadText.setEnabled(false);
        telefonoText.setEnabled(false);
        movilText.setEnabled(false);
        faxText.setEnabled(false);
        emailText.setEnabled(false);
        totalText.setEnabled(false);
        aceptarButton.setEnabled(false);
        cancelButton.setEnabled(false);
        salirButton.setEnabled(false);
    }
    
    /**
     * Habilita todos los campos y botones del formulario.
     * 
     * <p>Este método activa todos los controles para permitir la entrada completa
     * de datos, típicamente usado en el modo ALTA después de ingresar un código válido.</p>
     */
    public void activateAll(){
        codigoText.setEnabled(true);
        nifnText.setEnabled(true);
        nombreText.setEnabled(true);
        apellidosText.setEnabled(true);
        domicilioText.setEnabled(true); 
        cpText.setEnabled(true);
        localidadText.setEnabled(true);
        telefonoText.setEnabled(true);
        movilText.setEnabled(true);
        faxText.setEnabled(true);
        emailText.setEnabled(true);
        aceptarButton.setEnabled(true);
        cancelButton.setEnabled(true);
        salirButton.setEnabled(true);
    }
    
    // Habilita los campos necesarios para las bases de datos
    /**
     * Habilita solo los campos necesarios para iniciar una operación CRUD.
     * 
     * <p>Este método activa únicamente el campo de código y los botones de acción
     * (Aceptar, Cancelar, Salir), dejando deshabilitados los demás campos hasta
     * que se ingrese un código válido o se complete una validación específica del modo.</p>
     */
    public void modoForm(){
        codigoText.setEnabled(true);
        aceptarButton.setEnabled(true);
        cancelButton.setEnabled(true);
        salirButton.setEnabled(true);
    }
    
    /**
     * Hace los campos de solo lectura (para consultas).
     * 
     * <p>Este método deshabilita la edición de todos los campos de datos,
     * permitiendo solo su visualización. Se utiliza principalmente en el
     * modo CONSULTAPORCODIGO para mostrar información sin permitir modificaciones.</p>
     */
    public void deshabilitarEdicion(){
        nifnText.setEditable(false);
        nombreText.setEditable(false);
        apellidosText.setEditable(false);
        domicilioText.setEditable(false); 
        cpText.setEditable(false);
        localidadText.setEditable(false);
        telefonoText.setEditable(false);
        movilText.setEditable(false);
        faxText.setEditable(false);
        emailText.setEditable(false);
        totalText.setEditable(false);
    }
    
    /**
     * Permite editar los campos (para altas y modificaciones).
     * 
     * <p>Este método habilita la edición de todos los campos de datos (excepto
     * el total de ventas), permitiendo al usuario ingresar o modificar información.
     * Se utiliza en los modos ALTA y MODIFICACIONES.</p>
     */
     public void habilitarEdicion(){
        nifnText.setEditable(true);
        nombreText.setEditable(true);
        apellidosText.setEditable(true);
        domicilioText.setEditable(true); 
        cpText.setEditable(true);
        localidadText.setEditable(true);
        telefonoText.setEditable(true);
        movilText.setEditable(true);
        faxText.setEditable(true);
        emailText.setEditable(true);
    }
    
    public void focoCodigo(){
        codigoText.setEnabled(true);
        codigoText.requestFocus();
    }
     
    public void preparacionModos(){
        reset();
        desactivateAll();
        habilitarEdicion();
        modoForm();

    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jOptionPane1 = new javax.swing.JOptionPane();
        codigoText = new javax.swing.JTextField();
        nifnText = new javax.swing.JTextField();
        nombreText = new javax.swing.JTextField();
        apellidosText = new javax.swing.JTextField();
        domicilioText = new javax.swing.JTextField();
        cpText = new javax.swing.JTextField();
        localidadText = new javax.swing.JTextField();
        telefonoText = new javax.swing.JTextField();
        movilText = new javax.swing.JTextField();
        faxText = new javax.swing.JTextField();
        emailText = new javax.swing.JTextField();
        totalText = new javax.swing.JTextField();
        niflText = new javax.swing.JTextField();
        aceptarButton = new javax.swing.JButton();
        cancelButton = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        jLabel12 = new javax.swing.JLabel();
        salirButton = new javax.swing.JButton();
        jMenuBar1 = new javax.swing.JMenuBar();
        jMenu2 = new javax.swing.JMenu();
        altasMenu = new javax.swing.JMenuItem();
        bajasMenu = new javax.swing.JMenuItem();
        modificacionesMenu = new javax.swing.JMenuItem();
        jSeparator2 = new javax.swing.JPopupMenu.Separator();
        volverMenu = new javax.swing.JMenuItem();
        jMenu3 = new javax.swing.JMenu();
        porCodigoMenu = new javax.swing.JMenuItem();
        porCodigoMenuV2 = new javax.swing.JMenu();
        PorCodigoJasper = new javax.swing.JMenuItem();
        entreCodigosMenu = new javax.swing.JMenuItem();
        graficoMenu = new javax.swing.JMenuItem();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Prueba de entrada de datos");

        codigoText.addCaretListener(new javax.swing.event.CaretListener() {
            public void caretUpdate(javax.swing.event.CaretEvent evt) {
                codigoTextCaretUpdate(evt);
            }
        });
        codigoText.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                codigoTextFocusLost(evt);
            }
        });
        codigoText.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                codigoTextActionPerformed(evt);
            }
        });

        nifnText.addCaretListener(new javax.swing.event.CaretListener() {
            public void caretUpdate(javax.swing.event.CaretEvent evt) {
                nifnTextCaretUpdate(evt);
            }
        });
        nifnText.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                nifnTextActionPerformed(evt);
            }
        });

        nombreText.addCaretListener(new javax.swing.event.CaretListener() {
            public void caretUpdate(javax.swing.event.CaretEvent evt) {
                nombreTextCaretUpdate(evt);
            }
        });
        nombreText.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                nombreTextActionPerformed(evt);
            }
        });

        apellidosText.addCaretListener(new javax.swing.event.CaretListener() {
            public void caretUpdate(javax.swing.event.CaretEvent evt) {
                apellidosTextCaretUpdate(evt);
            }
        });
        apellidosText.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                apellidosTextActionPerformed(evt);
            }
        });

        domicilioText.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                domicilioTextActionPerformed(evt);
            }
        });

        cpText.addCaretListener(new javax.swing.event.CaretListener() {
            public void caretUpdate(javax.swing.event.CaretEvent evt) {
                cpTextCaretUpdate(evt);
            }
        });
        cpText.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cpTextActionPerformed(evt);
            }
        });

        localidadText.addCaretListener(new javax.swing.event.CaretListener() {
            public void caretUpdate(javax.swing.event.CaretEvent evt) {
                localidadTextCaretUpdate(evt);
            }
        });
        localidadText.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                localidadTextActionPerformed(evt);
            }
        });

        telefonoText.addCaretListener(new javax.swing.event.CaretListener() {
            public void caretUpdate(javax.swing.event.CaretEvent evt) {
                telefonoTextCaretUpdate(evt);
            }
        });
        telefonoText.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                telefonoTextActionPerformed(evt);
            }
        });

        movilText.addCaretListener(new javax.swing.event.CaretListener() {
            public void caretUpdate(javax.swing.event.CaretEvent evt) {
                movilTextCaretUpdate(evt);
            }
        });
        movilText.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                movilTextActionPerformed(evt);
            }
        });

        faxText.addCaretListener(new javax.swing.event.CaretListener() {
            public void caretUpdate(javax.swing.event.CaretEvent evt) {
                faxTextCaretUpdate(evt);
            }
        });
        faxText.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                faxTextActionPerformed(evt);
            }
        });

        emailText.addCaretListener(new javax.swing.event.CaretListener() {
            public void caretUpdate(javax.swing.event.CaretEvent evt) {
                emailTextCaretUpdate(evt);
            }
        });
        emailText.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                emailTextActionPerformed(evt);
            }
        });

        totalText.addCaretListener(new javax.swing.event.CaretListener() {
            public void caretUpdate(javax.swing.event.CaretEvent evt) {
                totalTextCaretUpdate(evt);
            }
        });

        aceptarButton.setText("Aceptar");
        aceptarButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                aceptarButtonActionPerformed(evt);
            }
        });

        cancelButton.setText("Cancelar");
        cancelButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cancelButtonActionPerformed(evt);
            }
        });

        jLabel1.setText("Código");

        jLabel2.setText("N.I.F.");

        jLabel3.setText("Nombre");

        jLabel4.setText("Apellidos");

        jLabel5.setText("Domicilio");

        jLabel6.setText("C.P.");

        jLabel7.setText("Localidad");

        jLabel8.setText("Teléfono");

        jLabel9.setText("Móvil");

        jLabel10.setText("Fax");

        jLabel11.setText("Email");

        jLabel12.setText("Total");

        salirButton.setText("Salir");
        salirButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                salirButtonActionPerformed(evt);
            }
        });

        jMenu2.setText("Mantenimiento");

        altasMenu.setText("Altas");
        altasMenu.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                altasMenuActionPerformed(evt);
            }
        });
        jMenu2.add(altasMenu);

        bajasMenu.setText("Bajas");
        bajasMenu.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bajasMenuActionPerformed(evt);
            }
        });
        jMenu2.add(bajasMenu);

        modificacionesMenu.setText("Modificaciones");
        modificacionesMenu.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                modificacionesMenuFocusLost(evt);
            }
        });
        modificacionesMenu.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                modificacionesMenuActionPerformed(evt);
            }
        });
        jMenu2.add(modificacionesMenu);
        jMenu2.add(jSeparator2);

        volverMenu.setText("Volver");
        volverMenu.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                volverMenuActionPerformed(evt);
            }
        });
        jMenu2.add(volverMenu);

        jMenuBar1.add(jMenu2);

        jMenu3.setText("Consultas");

        porCodigoMenu.setText("Por código");
        porCodigoMenu.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                porCodigoMenuActionPerformed(evt);
            }
        });
        jMenu3.add(porCodigoMenu);

        porCodigoMenuV2.setText("Listados");

        PorCodigoJasper.setText("Por código");
        PorCodigoJasper.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                PorCodigoJasperActionPerformed(evt);
            }
        });
        porCodigoMenuV2.add(PorCodigoJasper);

        entreCodigosMenu.setText("Entre códigos");
        entreCodigosMenu.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                entreCodigosMenuActionPerformed(evt);
            }
        });
        porCodigoMenuV2.add(entreCodigosMenu);

        graficoMenu.setText("Gráficos");
        graficoMenu.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                graficoMenuActionPerformed(evt);
            }
        });
        porCodigoMenuV2.add(graficoMenu);

        jMenu3.add(porCodigoMenuV2);

        jMenuBar1.add(jMenu3);

        setJMenuBar(jMenuBar1);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(codigoText, javax.swing.GroupLayout.PREFERRED_SIZE, 101, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel2)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(nifnText, javax.swing.GroupLayout.PREFERRED_SIZE, 110, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(niflText, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel3)
                            .addComponent(nombreText)))
                    .addComponent(jLabel5)
                    .addComponent(jLabel4)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(cpText, javax.swing.GroupLayout.PREFERRED_SIZE, 63, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(telefonoText, javax.swing.GroupLayout.PREFERRED_SIZE, 103, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel6)
                            .addComponent(jLabel8))
                        .addGap(27, 27, 27)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel7)
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(movilText, javax.swing.GroupLayout.PREFERRED_SIZE, 103, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel9))
                                .addGap(23, 23, 23)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel10)
                                    .addComponent(faxText, javax.swing.GroupLayout.PREFERRED_SIZE, 103, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addComponent(localidadText)))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addComponent(aceptarButton)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(cancelButton)
                        .addGap(12, 12, 12)
                        .addComponent(salirButton))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel11)
                            .addComponent(emailText, javax.swing.GroupLayout.PREFERRED_SIZE, 340, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(totalText, javax.swing.GroupLayout.PREFERRED_SIZE, 82, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel12)))
                    .addComponent(apellidosText)
                    .addComponent(domicilioText))
                .addContainerGap(18, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(15, 15, 15)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(layout.createSequentialGroup()
                            .addComponent(jLabel2)
                            .addGap(6, 6, 6)
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(nifnText, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(niflText, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGroup(layout.createSequentialGroup()
                            .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 17, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGap(6, 6, 6)
                            .addComponent(codigoText, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel3)
                        .addGap(6, 6, 6)
                        .addComponent(nombreText, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(10, 10, 10)
                .addComponent(jLabel4)
                .addGap(6, 6, 6)
                .addComponent(apellidosText, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(10, 10, 10)
                .addComponent(jLabel5)
                .addGap(6, 6, 6)
                .addComponent(domicilioText, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(10, 10, 10)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel6)
                    .addComponent(jLabel7))
                .addGap(6, 6, 6)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(cpText, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(localidadText, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(10, 10, 10)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel8)
                    .addComponent(jLabel9)
                    .addComponent(jLabel10))
                .addGap(6, 6, 6)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(telefonoText, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(movilText, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(faxText, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(10, 10, 10)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel11)
                    .addComponent(jLabel12))
                .addGap(8, 8, 8)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(emailText, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(totalText, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(10, 10, 10)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(aceptarButton)
                    .addComponent(cancelButton)
                    .addComponent(salirButton))
                .addContainerGap(15, Short.MAX_VALUE))
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void codigoTextActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_codigoTextActionPerformed

    }//GEN-LAST:event_codigoTextActionPerformed

    private void nifnTextActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_nifnTextActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_nifnTextActionPerformed

    private void nombreTextActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_nombreTextActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_nombreTextActionPerformed

    private void apellidosTextActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_apellidosTextActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_apellidosTextActionPerformed

    private void domicilioTextActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_domicilioTextActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_domicilioTextActionPerformed

    private void cpTextActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cpTextActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_cpTextActionPerformed

    private void localidadTextActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_localidadTextActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_localidadTextActionPerformed

    private void telefonoTextActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_telefonoTextActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_telefonoTextActionPerformed

    private void movilTextActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_movilTextActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_movilTextActionPerformed

    private void faxTextActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_faxTextActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_faxTextActionPerformed

    private void emailTextActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_emailTextActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_emailTextActionPerformed

    private void cancelButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cancelButtonActionPerformed
        // TODO add your handling code here:
        reset();
        desactivateAll();
        focoCodigo();
    }//GEN-LAST:event_cancelButtonActionPerformed

    private void aceptarButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_aceptarButtonActionPerformed
        // Procesa la acción según el modo actual (ALTA/BAJA/MODIFICACIONES/CONSULTA)
        String text = "Errores en: \n";
        ArrayList<String> errores = new ArrayList<>();

        switch(modo){
            case BAJA, CONSULTAPORCODIGO:
                // En modos BAJA y CONSULTA solo se valida el código
                if (!(codigoCheck(codigoText.getText()))){
                errores.add("Código");
                }
                break;
            default: 
                // En modos ALTA y MODIFICACIONES se validan todos los campos
               if (!(codigoCheck(codigoText.getText()))){
                errores.add("Código");
                }
                if (!(numCheck(nifnText.getText()))){
                    errores.add("DNI");
                }
                if (!(nameCheck(nombreText.getText()))) {
                    errores.add("Nombre");
                }
                if (!(nameCheck(apellidosText.getText()))) {
                    errores.add("Apellidos");
                }
                if (domicilioText.getText().isEmpty()){
                    errores.add("Domicilio");
                }
                if (!(cpCheck(cpText.getText()))) {
                    errores.add("C.P.");
                }
                if (!(phoneCheck(telefonoText.getText()))) {
                    errores.add("Teléfono");
                }
                if (!(phoneCheck(movilText.getText()))) {
                    errores.add("Móvil");
                }
                if (!(phoneCheck(faxText.getText()))) {
                    errores.add("Fax");
                }
                if (!(emailCheck(emailText.getText()))) {
                    errores.add("email");
                }
        } 
        
            
        // Construcción del mensaje de error
        if (errores.size()==1){
            text = "Error en: ";
        }
        
        for (int i = 0; i < errores.size(); i++){
            if (i == errores.size()-1){
                text += errores.get(i);
            } else {
                text += errores.get(i) + ", ";
            }
        }
        
        
        if (!errores.isEmpty()) {
            jOptionPane1.showMessageDialog(null, text, "Ventana Error", jOptionPane1.YES_OPTION);
        } else {
            try{
                switch(modo) {
                case ALTA:
                    // Operación de inserción de nuevo cliente
                    totalText.setEnabled(false);

                       vm.altaCLiente(
                                codigoText.getText(), 
                                nifnText.getText() + calcularLetraDNI(nifnText.getText()), 
                                apellidosText.getText(), 
                                nombreText.getText(), 
                                domicilioText.getText(), 
                                cpText.getText(), 
                                localidadText.getText(), 
                                telefonoText.getText(), 
                                movilText.getText(), 
                                faxText.getText(), 
                                emailText.getText(), 
                                0
                        );

                    reset();
                    desactivateAll();
                    break; 


                case BAJA:
                        // Operación de eliminación de cliente
                        vm.bajaCliente(codigoText.getText());

                        JOptionPane.showMessageDialog(
                            null,                         
                            "Cliente eliminado con exito", 
                            "Información",                
                            JOptionPane.INFORMATION_MESSAGE 
                        );

                    reset();
                    break; 

                case MODIFICACIONES: 
                        // Operación de actualización de datos del cliente (sin modificar total de ventas)
                        vm.modificarCliente(
                                codigoText.getText(), 
                                nifnText.getText() + calcularLetraDNI(nifnText.getText()), 
                                apellidosText.getText(), 
                                nombreText.getText(), 
                                domicilioText.getText(), 
                                cpText.getText(), 
                                localidadText.getText(), 
                                telefonoText.getText(), 
                                movilText.getText(), 
                                faxText.getText(), 
                                emailText.getText()
                        );
                        JOptionPane.showMessageDialog(
                            null,                         
                            "Cliente modificado con exito", 
                            "Información",                
                            JOptionPane.INFORMATION_MESSAGE 
                        );

                    reset();
                    desactivateAll();
                    break; 

                case CONSULTAPORCODIGO:
                        // Operación de búsqueda y visualización de cliente
                        Cliente cliente = vm.consultaPorCodigo(codigoText.getText());
                        nifnText.setText(cliente.getNif().substring(0, cliente.getNif().length() - 1));
                        apellidosText.setText(cliente.getApellidos());
                        nombreText.setText(cliente.getNombre()); 
                        cpText.setText(cliente.getCodigoPostal()); 
                        localidadText.setText(cliente.getLocalidad());
                        domicilioText.setText(cliente.getDomicilio());
                        telefonoText.setText(cliente.getTelefono());
                        movilText.setText(cliente.getMovil());
                        faxText.setText(cliente.getFax());
                        emailText.setText(cliente.getEmail());
                        totalText.setText(String.valueOf(cliente.getTotal()));

                    break;
                }
            } catch (ClienteAlreadyExistsException ex) {
                // Manejo de cliente duplicado en operación ALTA
                codigoText.setText("");                    
                JOptionPane.showMessageDialog(
                    null,                       
                    "Cliente ya existe",     
                    "Error",                    
                    JOptionPane.ERROR_MESSAGE );
            }catch (ClienteNotFoundException ex) {
                // Manejo de cliente no encontrado en operaciones BAJA, MODIFICACIONES o CONSULTA
                codigoText.setText("");                    
                JOptionPane.showMessageDialog(
                    null,                       
                    "Cliente no encontrado",     
                    "Error",                    
                    JOptionPane.ERROR_MESSAGE );
            } catch(DataAccessException ex) {
                // Manejo de errores generales de acceso a datos
                codigoText.setText("");                    
                JOptionPane.showMessageDialog(
                        null, 
                        "Ha ocurrido un error", 
                        "Error", 
                        JOptionPane.ERROR_MESSAGE
                );
                System.getLogger(FormCliente.class.getName()).log(System.Logger.Level.ERROR, "DB error", ex);
            }
        }
        focoCodigo();
    }//GEN-LAST:event_aceptarButtonActionPerformed

    // Valida en tiempo real y cambia el color del campo (negro=válido, rojo=inválido)
    private void codigoTextCaretUpdate(javax.swing.event.CaretEvent evt) {//GEN-FIRST:event_codigoTextCaretUpdate
        String text = codigoText.getText();
        if (codigoCheck(text)){
            codigoText.setForeground(Color.BLACK);
            if(modo != Modo.BAJA && modo != Modo.CONSULTAPORCODIGO){
                activateAll();
            }
        } else {
            codigoText.setForeground(Color.RED);
                desactivateAll();
                codigoText.setEnabled(true);
                
        }
    }//GEN-LAST:event_codigoTextCaretUpdate

    // Valida en tiempo real y cambia el color del campo (negro=válido, rojo=inválido)
    private void nifnTextCaretUpdate(javax.swing.event.CaretEvent evt) {//GEN-FIRST:event_nifnTextCaretUpdate
        String text = nifnText.getText();
        if (numCheck(text)){
            String letra = calcularLetraDNI(text);
            niflText.setText(letra);
            nifnText.setForeground(Color.BLACK);
        } else {
            nifnText.setForeground(Color.RED);
            niflText.setText("");            
        }
        
        
    }//GEN-LAST:event_nifnTextCaretUpdate

    // Valida en tiempo real y cambia el color del campo (negro=válido, rojo=inválido)
    private void nombreTextCaretUpdate(javax.swing.event.CaretEvent evt) {//GEN-FIRST:event_nombreTextCaretUpdate
        String text = nombreText.getText();
        if (nameCheck(text)){
            nombreText.setForeground(Color.BLACK);
        } else {
            nombreText.setForeground(Color.RED);
        }
    }//GEN-LAST:event_nombreTextCaretUpdate

    // Valida en tiempo real y cambia el color del campo (negro=válido, rojo=inválido)
    private void apellidosTextCaretUpdate(javax.swing.event.CaretEvent evt) {//GEN-FIRST:event_apellidosTextCaretUpdate
        String text = apellidosText.getText();
        if (apellidoCheck(text)){
            apellidosText.setForeground(Color.BLACK);
        } else {
            apellidosText.setForeground(Color.RED);
        }
    }//GEN-LAST:event_apellidosTextCaretUpdate

    // Valida en tiempo real y cambia el color del campo (negro=válido, rojo=inválido)
    private void cpTextCaretUpdate(javax.swing.event.CaretEvent evt) {//GEN-FIRST:event_cpTextCaretUpdate
        String text = cpText.getText();
        if (cpCheck(text)){
            cpText.setForeground(Color.BLACK);
        } else {
            cpText.setForeground(Color.RED);
        }        // TODO add your handling code here:
    }//GEN-LAST:event_cpTextCaretUpdate

    // Valida en tiempo real y cambia el color del campo (negro=válido, rojo=inválido)
    private void telefonoTextCaretUpdate(javax.swing.event.CaretEvent evt) {//GEN-FIRST:event_telefonoTextCaretUpdate
        String text = telefonoText.getText();
        if (phoneCheck(text)){
            telefonoText.setForeground(Color.BLACK);
        } else {
            telefonoText.setForeground(Color.RED);
        }
    }//GEN-LAST:event_telefonoTextCaretUpdate

    // Valida en tiempo real y cambia el color del campo (negro=válido, rojo=inválido)
    private void movilTextCaretUpdate(javax.swing.event.CaretEvent evt) {//GEN-FIRST:event_movilTextCaretUpdate
        String text = movilText.getText();
        if (phoneCheck(text)){
            movilText.setForeground(Color.BLACK);
        } else {
            movilText.setForeground(Color.RED);
        } 
    }//GEN-LAST:event_movilTextCaretUpdate

    // Valida en tiempo real y cambia el color del campo (negro=válido, rojo=inválido)
    private void faxTextCaretUpdate(javax.swing.event.CaretEvent evt) {//GEN-FIRST:event_faxTextCaretUpdate
        String text = faxText.getText();
        if (phoneCheck(text)){
            faxText.setForeground(Color.BLACK);
        } else {
            faxText.setForeground(Color.RED);
        }
    }//GEN-LAST:event_faxTextCaretUpdate

    // Valida en tiempo real y cambia el color del campo (negro=válido, rojo=inválido)
    private void emailTextCaretUpdate(javax.swing.event.CaretEvent evt) {//GEN-FIRST:event_emailTextCaretUpdate
        String text = emailText.getText();
        if (emailCheck(text)){
            emailText.setForeground(Color.BLACK);
        } else {
            emailText.setForeground(Color.RED);
        }
    }//GEN-LAST:event_emailTextCaretUpdate

    // Valida en tiempo real y cambia el color del campo (negro=válido, rojo=inválido)
    private void localidadTextCaretUpdate(javax.swing.event.CaretEvent evt) {//GEN-FIRST:event_localidadTextCaretUpdate
        String text = localidadText.getText();
        if (nameCheck(text)){
            localidadText.setForeground(Color.BLACK);
        } else {
            localidadText.setForeground(Color.RED);
        }
    }//GEN-LAST:event_localidadTextCaretUpdate

    private void salirButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_salirButtonActionPerformed
        reset();
        desactivateAll();
    }//GEN-LAST:event_salirButtonActionPerformed

    private void volverMenuActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_volverMenuActionPerformed
        Menu menu = new Menu();
        menu.setVisible(true);
        this.dispose();
    }//GEN-LAST:event_volverMenuActionPerformed

    // Valida en tiempo real y cambia el color del campo (negro=válido, rojo=inválido)
    private void totalTextCaretUpdate(javax.swing.event.CaretEvent evt) {//GEN-FIRST:event_totalTextCaretUpdate
        String text = totalText.getText();
        if (esFloat(text)){
            totalText.setForeground(Color.BLACK);
        } else {
            totalText.setForeground(Color.RED);
        }
    }//GEN-LAST:event_totalTextCaretUpdate

    private void altasMenuActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_altasMenuActionPerformed
        modo = Modo.ALTA;
        preparacionModos();

    }//GEN-LAST:event_altasMenuActionPerformed

    private void bajasMenuActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bajasMenuActionPerformed
        preparacionModos();
        modo = Modo.BAJA;
    }//GEN-LAST:event_bajasMenuActionPerformed

    private void modificacionesMenuActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_modificacionesMenuActionPerformed
        preparacionModos();
        modo = Modo.MODIFICACIONES;
    }//GEN-LAST:event_modificacionesMenuActionPerformed

    private void porCodigoMenuActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_porCodigoMenuActionPerformed
        preparacionModos();
        modo = Modo.CONSULTAPORCODIGO;  
    }//GEN-LAST:event_porCodigoMenuActionPerformed

    private void entreCodigosMenuActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_entreCodigosMenuActionPerformed
        desactivateAll();
        // Uso de lambda para implementar AccionJasperEntreCodigos y generar reporte filtrado
        EntreCodigos ec = new EntreCodigos( 
          (cod1, cod2) -> {
            try {
                vm.jasperClienteEntreCodigos(cod1, cod2);
            } catch (JRException ex) {
                System.getLogger(FormCliente.class.getName()).log(System.Logger.Level.ERROR, (String) null, ex);
            } catch (SQLException ex) {
                System.getLogger(FormCliente.class.getName()).log(System.Logger.Level.ERROR, (String) null, ex);
            } catch (DataAccessException ex) {
                System.getLogger(FormCliente.class.getName()).log(System.Logger.Level.ERROR, (String) null, ex);
            }
        }
        );
        ec.setVisible(true);
        this.dispose();
    }//GEN-LAST:event_entreCodigosMenuActionPerformed

    private void graficoMenuActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_graficoMenuActionPerformed
        desactivateAll();
        // Generación de reporte con gráfico estadístico de clientes
        try {
            vm.jasperClienteGrafico();
            JOptionPane.showMessageDialog(
                        null,                       
                        "¡PDF descargado correctamente!",     
                        "Realizado",                    
                        JOptionPane.INFORMATION_MESSAGE 
                    );
        } catch (JRException | SQLException ex) {
            System.getLogger(FormCliente.class.getName()).log(System.Logger.Level.ERROR, (String) null, ex);
        } catch (DataAccessException ex) {
            System.getLogger(FormCliente.class.getName()).log(System.Logger.Level.ERROR, (String) null, ex);
        }
    }//GEN-LAST:event_graficoMenuActionPerformed

    private void PorCodigoJasperActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_PorCodigoJasperActionPerformed
        desactivateAll();
        // Generación de reporte completo de clientes ordenados por código
        try {
            vm.jasperClientePorCodigo();
            JOptionPane.showMessageDialog(
                        null,                       
                        "¡PDF descargado correctamente!",     
                        "Realizado",                    
                        JOptionPane.INFORMATION_MESSAGE 
                    );
        } catch (JRException | SQLException ex) {
            System.getLogger(FormCliente.class.getName()).log(System.Logger.Level.ERROR, (String) null, ex);
        } catch (DataAccessException ex) {
            System.getLogger(FormCliente.class.getName()).log(System.Logger.Level.ERROR, (String) null, ex);
        }
    }//GEN-LAST:event_PorCodigoJasperActionPerformed

    private void codigoTextFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_codigoTextFocusLost
            // En modo MODIFICACIONES, autocompleta los campos buscando el cliente por código al perder el foco
            if(modo == Modo.MODIFICACIONES && codigoCheck(codigoText.getText())){
                try {
                    
                    Cliente cliente = vm.consultaPorCodigo(codigoText.getText());
                    
                    nifnText.setText(cliente.getNif().substring(0, cliente.getNif().length() - 1));
                    apellidosText.setText(cliente.getApellidos());
                    nombreText.setText(cliente.getNombre()); 
                    cpText.setText(cliente.getCodigoPostal()); 
                    localidadText.setText(cliente.getLocalidad());
                    domicilioText.setText(cliente.getDomicilio());
                    telefonoText.setText(cliente.getTelefono());
                    movilText.setText(cliente.getMovil());
                    faxText.setText(cliente.getFax());
                    emailText.setText(cliente.getEmail());
                    totalText.setText(String.valueOf(cliente.getTotal()));

                }catch (ClienteNotFoundException ex) {
                    codigoText.setText("");   
                    desactivateAll();
                    modoForm();
                    JOptionPane.showMessageDialog(
                        null,                       
                        "Cliente no encontrado",     
                        "Error",                    
                        JOptionPane.ERROR_MESSAGE );
                } catch(DataAccessException ex) {
                    codigoText.setText("");   
                    desactivateAll();
                    modoForm();                    
                    JOptionPane.showMessageDialog(
                            null, 
                            "Ha ocurrido un error", 
                            "Error", 
                            JOptionPane.ERROR_MESSAGE
                    );
                System.getLogger(FormCliente.class.getName()).log(System.Logger.Level.ERROR, "DB error", ex);
                }
            }  
    }//GEN-LAST:event_codigoTextFocusLost

    private void modificacionesMenuFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_modificacionesMenuFocusLost
            
    }//GEN-LAST:event_modificacionesMenuFocusLost

  
    
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
        } catch (ReflectiveOperationException | javax.swing.UnsupportedLookAndFeelException ex) {
            logger.log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(() -> new FormCliente().setVisible(true));
    }
    
    /**
     * Valida nombres: 1-15 caracteres alfabéticos con espacios y acentos.
     * 
     * <p>Acepta letras mayúsculas, minúsculas, espacios, vocales acentuadas
     * y la letra ñ. No acepta números ni caracteres especiales.</p>
     * 
     * @param text El texto a validar
     * @return true si cumple el patrón [a-zA-ZáéíóúÁÉÍÓÚñÑ\\s]{1,15}, false en caso contrario
     */
    //método para comprobar Nombre y Apellido
    public static boolean nameCheck(String text){
        return text.matches("[a-zA-ZáéíóúÁÉÍÓÚñÑ\\s]{1,15}"); 
    }
    
    /**
     * Valida apellidos: 1-35 caracteres alfabéticos con espacios y acentos.
     * 
     * <p>Similar a nameCheck pero con mayor longitud permitida (35 caracteres)
     * para acomodar apellidos compuestos. Acepta letras, espacios, acentos y ñ.</p>
     * 
     * @param text El texto a validar
     * @return true si cumple el patrón [a-zA-ZáéíóúÁÉÍÓÚñÑ\\s]{1,35}, false en caso contrario
     */
    //método para comprobar Apellido
    public static boolean apellidoCheck(String text){
        return text.matches("[a-zA-ZáéíóúÁÉÍÓÚñÑ\\s]{1,35}"); 
    }
    
    /**
     * Valida teléfonos: exactamente 9 dígitos o campo vacío (opcional).
     * 
     * <p>Este método valida números de teléfono, móvil y fax. Los campos
     * son opcionales, por lo que también acepta cadenas nulas o vacías.</p>
     * 
     * @param text El texto a validar
     * @return true si el texto es vacío o cumple el patrón [0-9]{9}, false en caso contrario
     */
    //método para comprobar Teléfono, Móvil, Fax
    public static boolean phoneCheck(String text){
        
        if (text == null || text.isBlank()){
            return true;
        }        
        
        return text.matches("[0-9]{9}");
    }
    
    /**
     * Valida emails: formato estándar, máximo 20 caracteres, opcional.
     * 
     * <p>Valida direcciones de correo electrónico con el formato usuario@dominio.extension.
     * El campo es opcional, por lo que acepta cadenas nulas o vacías. La longitud total
     * no puede superar los 20 caracteres.</p>
     * 
     * @param text El texto a validar
     * @return true si es vacío o cumple el formato email con máximo 20 caracteres, false en caso contrario
     */
    //método para comprobar Email
    public static boolean emailCheck(String text){
        
        if (text == null || text.isBlank()){
            return true;
        }
        
        return text.matches("^(?=.{1,20}$)[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$");
    }
    
    /**
     * Valida código de cliente: exactamente 6 dígitos.
     * 
     * <p>El código de cliente es un identificador único de 6 dígitos numéricos.
     * Es un campo obligatorio y debe ser exactamente 6 caracteres.</p>
     * 
     * @param text El texto a validar
     * @return true si cumple el patrón [0-9]{6}, false en caso contrario
     */
    //metodo para Código
    public static boolean codigoCheck(String text){
        return text.matches("[0-9]{6}");
    }
    
    /**
     * Valida código postal: exactamente 5 dígitos.
     * 
     * <p>Valida códigos postales españoles, que constan de exactamente
     * 5 dígitos numéricos.</p>
     * 
     * @param text El texto a validar
     * @return true si cumple el patrón [0-9]{5}, false en caso contrario
     */
    //metodo para comprobar Codigo Postal
    public static boolean cpCheck(String text){
        return text.matches("[0-9]{5}");
    }
    
    /**
     * Valida parte numérica del DNI: exactamente 8 dígitos.
     * 
     * <p>Valida los 8 dígitos numéricos del DNI español. La letra del DNI
     * se calcula automáticamente mediante el método {@link #calcularLetraDNI(String)}.</p>
     * 
     * @param text El texto a validar
     * @return true si cumple el patrón [0-9]{8}, false en caso contrario
     * @see #calcularLetraDNI(String)
     */
    //metodo para comprobar DNI
    public static boolean numCheck(String text){
        return text.matches("[0-9]{8}");
    }
    
    /**
     * Valida domicilio: alfanumérico con caracteres especiales, 1-40 caracteres.
     * 
     * <p>Acepta letras, números, espacios y caracteres especiales comunes en direcciones
     * como º, ª y /. La longitud debe estar entre 1 y 40 caracteres.</p>
     * 
     * @param text El texto a validar
     * @return true si cumple el patrón [A-Za-z\\s0-9ºª/]{1,40}, false en caso contrario
     */
    //metodo para comprobar Domicilio
    public static boolean domicilioCheck(String text){
        return text.matches("[A-Za-z\\s0-9ºª/]{1,40}");
    }
    
    /**
     * Valida nombres de localidad: 1-20 caracteres alfabéticos con espacios y acentos.
     * 
     * <p>Similar a nameCheck pero permite hasta 20 caracteres para acomodar nombres
     * de localidades más largos. Acepta letras, espacios, vocales acentuadas y la letra ñ.
     * No acepta números ni caracteres especiales.</p>
     * 
     * @param text El texto a validar
     * @return true si cumple el patrón [a-zA-ZáéíóúÁÉÍÓÚñÑ\\s]{1,20}, false en caso contrario
     */
    public static boolean localidadCheck(String text){
        return text.matches("[a-zA-ZáéíóúÁÉÍÓÚñÑ\\s]{1,20}");
    }
    
    /**
     * Verifica si el texto es convertible a Float.
     * 
     * <p>Utilizado para validar el campo de total de ventas. Retorna true
     * si el texto puede ser parseado como un número de punto flotante,
     * false en caso contrario (incluyendo texto nulo).</p>
     * 
     * @param text El texto a validar
     * @return true si el texto es convertible a Float, false en caso contrario
     */
    public boolean esFloat(String text) {
    if (text == null) return false;

    try {
        Float.valueOf(text);
        return true;
    } catch (NumberFormatException e) {
        return false;
    }
}
    /**
     * Calcula la letra del DNI español según el algoritmo oficial (módulo 23).
     * 
     * <p>Este método implementa el algoritmo oficial español para calcular la letra
     * de control del DNI. Se divide el número del DNI entre 23 y según el resto
     * (0-22) se asigna una letra específica del array oficial.</p>
     * 
     * <p>El array de letras sigue el orden establecido por la legislación española:
     * T, R, W, A, G, M, Y, F, P, D, X, B, N, J, Z, S, Q, V, H, L, C, K, E</p>
     * 
     * @param num Los 8 dígitos del DNI como String
     * @return La letra correspondiente al DNI o cadena vacía si el número es inválido
     */
    
    
    private String calcularLetraDNI(String num) {
        if (num == null || num.isEmpty() || ! numCheck(num)) {
            return "";
        }
        
        // Array de letras del algoritmo oficial del DNI español
        String[] letras = {"T", "R", "W", "A", "G", "M", "Y", "F", "P", "D", "X","B", 
                           "N", "J", "Z", "S", "Q", "V", "H", "L", "C", "K", "E"};
        
        try {
            int numero = Integer.parseInt(num);
            int resto = numero % 23;
            return letras[resto];
        } catch (NumberFormatException e) {
            return "";
        }
    }
    
 
        
   
    /**
     * Limpia todos los campos del formulario y devuelve el foco al campo código.
     * 
     * <p>Este método resetea el formulario a su estado inicial, vaciando todos
     * los campos de texto y colocando el cursor en el campo código para facilitar
     * la entrada de un nuevo cliente o la búsqueda de otro.</p>
     */
    
    public void reset(){
        codigoText.grabFocus();
        codigoText.setText("");
        nifnText.setText("");
        niflText.setText("");
        nombreText.setText("");
        apellidosText.setText("");
        domicilioText.setText("");
        cpText.setText("");
        localidadText.setText("");
        telefonoText.setText("");
        movilText.setText("");
        faxText.setText("");
        emailText.setText("");
        totalText.setText("");
        
    }
    public static void showOtherWindow(){
        
    }
    
    
    
  
    

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JMenuItem PorCodigoJasper;
    private javax.swing.JButton aceptarButton;
    private javax.swing.JMenuItem altasMenu;
    private javax.swing.JTextField apellidosText;
    private javax.swing.JMenuItem bajasMenu;
    private javax.swing.JButton cancelButton;
    private javax.swing.JTextField codigoText;
    private javax.swing.JTextField cpText;
    private javax.swing.JTextField domicilioText;
    private javax.swing.JTextField emailText;
    private javax.swing.JMenuItem entreCodigosMenu;
    private javax.swing.JTextField faxText;
    private javax.swing.JMenuItem graficoMenu;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JMenu jMenu2;
    private javax.swing.JMenu jMenu3;
    private javax.swing.JMenuBar jMenuBar1;
    private javax.swing.JOptionPane jOptionPane1;
    private javax.swing.JPopupMenu.Separator jSeparator2;
    private javax.swing.JTextField localidadText;
    private javax.swing.JMenuItem modificacionesMenu;
    private javax.swing.JTextField movilText;
    private javax.swing.JTextField niflText;
    private javax.swing.JTextField nifnText;
    private javax.swing.JTextField nombreText;
    private javax.swing.JMenuItem porCodigoMenu;
    private javax.swing.JMenu porCodigoMenuV2;
    private javax.swing.JButton salirButton;
    private javax.swing.JTextField telefonoText;
    private javax.swing.JTextField totalText;
    private javax.swing.JMenuItem volverMenu;
    // End of variables declaration//GEN-END:variables
}
