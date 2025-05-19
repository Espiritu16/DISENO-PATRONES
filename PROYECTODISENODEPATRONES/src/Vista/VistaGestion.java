import javax.swing.*;
import java.awt.*;
import Controlador.*;
import Modelo.*;

public class VistaGestion extends JFrame {
    private CRUDFactory factory = new EntidadDAOFactory();
    private CRUD direccionDAO = factory.crearDireccionCRUD();
    private CRUD usuarioDAO = factory.crearUsuarioCRUD();

    // Campos para Dirección
    private JTextField tfDireccion = new JTextField(20);
    private JTextField tfCiudad = new JTextField(15);
    private JTextField tfDistrito = new JTextField(15);
    private JTextField tfCodigoPostal = new JTextField(10);
    private JTextField tfPais = new JTextField(15);
    private JTextField tfReferencia = new JTextField(20);

    // Campos para Usuario
    private JTextField tfNombre = new JTextField(15);
    private JTextField tfApellido = new JTextField(15);
    private JTextField tfCorreo = new JTextField(20);
    private JTextField tfTelefono = new JTextField(15);
    private JTextField tfContrasena = new JTextField(15);
    private int idDireccionCreada = -1; // Guarda el id generado al crear dirección

    public VistaGestion() {
        super("Gestión de Usuarios y Direcciones");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(450, 450);
        setLayout(new BorderLayout());

        // Panel para Dirección
        JPanel panelDireccion = new JPanel(new GridLayout(7, 2, 5, 5));
        panelDireccion.setBorder(BorderFactory.createTitledBorder("Crear Dirección"));
        panelDireccion.add(new JLabel("Dirección:")); panelDireccion.add(tfDireccion);
        panelDireccion.add(new JLabel("Ciudad:")); panelDireccion.add(tfCiudad);
        panelDireccion.add(new JLabel("Distrito:")); panelDireccion.add(tfDistrito);
        panelDireccion.add(new JLabel("Código Postal:")); panelDireccion.add(tfCodigoPostal);
        panelDireccion.add(new JLabel("País:")); panelDireccion.add(tfPais);
        panelDireccion.add(new JLabel("Referencia:")); panelDireccion.add(tfReferencia);

        JButton btnCrearDireccion = new JButton("Crear Dirección");
        panelDireccion.add(new JLabel());  // espacio vacío
        panelDireccion.add(btnCrearDireccion);

        // Panel para Usuario
        JPanel panelUsuario = new JPanel(new GridLayout(8, 2, 5, 5));
        panelUsuario.setBorder(BorderFactory.createTitledBorder("Crear Usuario"));
        panelUsuario.add(new JLabel("Nombre:")); panelUsuario.add(tfNombre);
        panelUsuario.add(new JLabel("Apellido:")); panelUsuario.add(tfApellido);
        panelUsuario.add(new JLabel("Correo:")); panelUsuario.add(tfCorreo);
        panelUsuario.add(new JLabel("Teléfono:")); panelUsuario.add(tfTelefono);
        panelUsuario.add(new JLabel("Contraseña:")); panelUsuario.add(tfContrasena);

        JButton btnCrearUsuario = new JButton("Crear Usuario");
        panelUsuario.add(new JLabel("Dirección ID:")); 
        JLabel lblIdDireccion = new JLabel("Ninguna");
        panelUsuario.add(lblIdDireccion);
        panelUsuario.add(new JLabel());  // espacio vacío
        panelUsuario.add(btnCrearUsuario);

        // Agregar paneles a la ventana
        add(panelDireccion, BorderLayout.NORTH);
        add(panelUsuario, BorderLayout.SOUTH);

        // Acción botón Crear Dirección
        btnCrearDireccion.addActionListener(e -> {
            PrototypeDireccion direccion = new PrototypeDireccion();
            direccion.setDireccion(tfDireccion.getText());
            direccion.setCiudad(tfCiudad.getText());
            direccion.setDistrito(tfDistrito.getText());
            direccion.setCodigo_postal(tfCodigoPostal.getText());
            direccion.setPais(tfPais.getText().isEmpty() ? "Perú" : tfPais.getText());
            direccion.setReferencia(tfReferencia.getText());

            int resultado = direccionDAO.crear(direccion);
            if (resultado > 0) {
                idDireccionCreada = resultado;
                lblIdDireccion.setText(String.valueOf(idDireccionCreada));
                JOptionPane.showMessageDialog(this, "Dirección creada con ID: " + idDireccionCreada);
            } else {
                JOptionPane.showMessageDialog(this, "Error al crear dirección", "Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        // Acción botón Crear Usuario
        btnCrearUsuario.addActionListener(e -> {
            if (idDireccionCreada < 0) {
                JOptionPane.showMessageDialog(this, "Primero cree una dirección válida", "Error", JOptionPane.WARNING_MESSAGE);
                return;
            }
            PrototypeUsuario usuario = new PrototypeUsuario();
            usuario.setNombre(tfNombre.getText());
            usuario.setApellido(tfApellido.getText());
            usuario.setCorreo(tfCorreo.getText());
            usuario.setTelefono(tfTelefono.getText());
            usuario.setContrasena_hash(tfContrasena.getText()); // Mejor hashearla antes
            usuario.setIdDireccion(idDireccionCreada);

            int resultado = usuarioDAO.crear(usuario);
            if (resultado > 0) {
                JOptionPane.showMessageDialog(this, "Usuario creado con ID: " + resultado);
                ConexionSingleton.closeConnection();
            } else {
                JOptionPane.showMessageDialog(this, "Error al crear usuario", "Error", JOptionPane.ERROR_MESSAGE);
            }
        });
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new VistaGestion().setVisible(true);
        });
    }
}