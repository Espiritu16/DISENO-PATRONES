package Vista;
import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.*;
import Controlador.CRUD;
import Controlador.CRUDFactory;
import Controlador.EntidadDAOFactory;
import Modelo.*;
import Modelo.SistemaGestionResiduosFacade;
import Modelo.SistemaGestionResiduosFacadeImpl;

public class RegistroConTabsSeguro extends JFrame {
    private JTabbedPane tabbedPane;

    // Campos Tab 1: Usuario y Dirección
    private JTextField tfNombre, tfApellido, tfTelefono, tfCorreo, tfContrasena;
    private JTextField tfPais, tfCiudad, tfDistrito, tfDireccion, tfCodigoPostal, tfReferencia;

    // Campos Tab 2: Clasificación
    private JTextField tfNombreClas, tfDescripcionClas, tfColorCodigo, tfIcono;

    // Campos Tab 3: Residuo
    private JTextField tfNombreRes, tfDescripcionRes, tfUnidadMedida, tfPeligrosidad;

    // Botones de navegación
    private JButton btnAnterior, btnSiguiente;

    // Prototipos para guardar datos en memoria
    private PrototypeDireccion direccion = new PrototypeDireccion();
    private PrototypeUsuario usuario = new PrototypeUsuario();
    private PrototypeClasificacionResiduo clasificacion = new PrototypeClasificacionResiduo();
    private PrototypeResiduo residuo = new PrototypeResiduo();

    public RegistroConTabsSeguro() {
        setTitle("Registro con pestañas seguro");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(600, 600);
        setLayout(new BorderLayout());

        // 🎨 Menú superior interactivo
        JPanel menuSuperior = new JPanel(new GridLayout(1, 4));
        menuSuperior.setBackground(new Color(200, 230, 255)); // Azul claro

        JButton m1 = new JButton("M1");
        JButton m2 = new JButton("M2");
        JButton m3 = new JButton("M3");
        JButton registro = new JButton("Registro");

        // Establecer los colores de fondo y de texto
        m1.setBackground(new Color(0x2196F3)); // Azul claro para M1
        m1.setForeground(Color.BLACK); // Texto negro

        m2.setBackground(new Color(0x2196F3)); // Azul claro para M2
        m2.setForeground(Color.BLACK); // Texto negro

        m3.setBackground(new Color(0x2196F3)); // Azul claro para M3
        m3.setForeground(Color.BLACK); // Texto negro

        registro.setBackground(new Color(0xFFEB3B)); // Amarillo para el registro
        registro.setForeground(Color.BLACK); // Texto negro

        m1.addActionListener(e -> JOptionPane.showMessageDialog(this, "M1 aún no implementado"));
        m2.addActionListener(e -> JOptionPane.showMessageDialog(this, "M2 aún no implementado"));
        m3.addActionListener(e -> JOptionPane.showMessageDialog(this, "M3 aún no implementado"));
        registro.addActionListener(e -> {
            int opcion = JOptionPane.showConfirmDialog(this, "¿Desea volver al formulario inicial?", "Confirmar", JOptionPane.YES_NO_OPTION);
            if (opcion == JOptionPane.YES_OPTION) {
                limpiarFormulario();
                tabbedPane.setSelectedIndex(0);
            }
        });

        menuSuperior.add(m1);
        menuSuperior.add(m2);
        menuSuperior.add(m3);
        menuSuperior.add(registro);
        add(menuSuperior, BorderLayout.NORTH);

        // Pestañas
        tabbedPane = new JTabbedPane();
        tabbedPane.add("Usuario y Dirección", crearPanelUsuarioDireccion());
        tabbedPane.add("Clasificación", crearPanelClasificacion());
        tabbedPane.add("Residuo", crearPanelResiduo());

        add(tabbedPane, BorderLayout.CENTER);

        // Panel de botones de navegación
        JPanel panelBotones = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        btnAnterior = new JButton("Anterior");
        btnSiguiente = new JButton("Siguiente");

        // Estilo de los botones
        btnAnterior.setBackground(new Color(0xF44336)); // Rojo para Anterior
        btnAnterior.setForeground(Color.BLACK); // Texto negro

        btnSiguiente.setBackground(new Color(0x4CAF50)); // Verde para Siguiente
        btnSiguiente.setForeground(Color.BLACK); // Texto negro

        panelBotones.add(btnAnterior);
        panelBotones.add(btnSiguiente);
        add(panelBotones, BorderLayout.SOUTH);

        // Bloquear tabs 2 y 3 inicialmente
        tabbedPane.setEnabledAt(1, false);
        tabbedPane.setEnabledAt(2, false);

        // Impedir cambio por clic en tab deshabilitado
        tabbedPane.addChangeListener(new ChangeListener() {
            private int lastSelected = 0;

            @Override
            public void stateChanged(ChangeEvent e) {
                int selected = tabbedPane.getSelectedIndex();
                if (!tabbedPane.isEnabledAt(selected)) {
                    SwingUtilities.invokeLater(() -> tabbedPane.setSelectedIndex(lastSelected));
                } else {
                    lastSelected = selected;
                }
            }
        });

        btnAnterior.setEnabled(false); // No hay anterior al iniciar

        // Acción botón Anterior
        btnAnterior.addActionListener(e -> {
            int current = tabbedPane.getSelectedIndex();
            if (current > 0) {
                tabbedPane.setSelectedIndex(current - 1);
                btnSiguiente.setText("Siguiente");
                btnAnterior.setEnabled(current - 1 != 0);
            }
        });

        // Acción botón Siguiente / Finalizar
        btnSiguiente.addActionListener(e -> {
            int current = tabbedPane.getSelectedIndex();

            // Validar y guardar datos en prototipos
            boolean valid = guardarDatosParciales(current);
            if (!valid) {
                JOptionPane.showMessageDialog(this, "Por favor completa todos los campos requeridos.");
                return;
            }

            if (current < tabbedPane.getTabCount() - 1) {
                tabbedPane.setEnabledAt(current + 1, true);
                tabbedPane.setSelectedIndex(current + 1);
                btnAnterior.setEnabled(true);
                if (current + 1 == tabbedPane.getTabCount() - 1) {
                    btnSiguiente.setText("Finalizar");
                } else {
                    btnSiguiente.setText("Siguiente");
                }
            } else {
                // Última pestaña: guardar todo en BD
                boolean exito = guardarTodoEnBD();
                if (exito) {
                    JOptionPane.showMessageDialog(this, "Datos guardados exitosamente.");
                    limpiarFormulario();
                    tabbedPane.setSelectedIndex(0);
                    btnSiguiente.setText("Siguiente");
                    btnAnterior.setEnabled(false);
                    tabbedPane.setEnabledAt(1, false);
                    tabbedPane.setEnabledAt(2, false);
                } else {
                    JOptionPane.showMessageDialog(this, "Error al guardar los datos.");
                }
            }
        });

        setLocationRelativeTo(null);
        setVisible(true);
    }

    private JPanel crearPanelUsuarioDireccion() {
        JPanel panel = new JPanel(new GridLayout(11, 2, 5, 5));

        panel.add(new JLabel("Nombres:"));
        tfNombre = new JTextField();
        panel.add(tfNombre);

        panel.add(new JLabel("Apellidos:"));
        tfApellido = new JTextField();
        panel.add(tfApellido);

        panel.add(new JLabel("Teléfono:"));
        tfTelefono = new JTextField();
        panel.add(tfTelefono);

        panel.add(new JLabel("Correo:"));
        tfCorreo = new JTextField();
        panel.add(tfCorreo);

        panel.add(new JLabel("Contraseña:"));
        tfContrasena = new JTextField();
        panel.add(tfContrasena);

        panel.add(new JLabel("País:"));
        tfPais = new JTextField("Perú");
        panel.add(tfPais);

        panel.add(new JLabel("Ciudad:"));
        tfCiudad = new JTextField();
        panel.add(tfCiudad);

        panel.add(new JLabel("Distrito:"));
        tfDistrito = new JTextField();
        panel.add(tfDistrito);

        panel.add(new JLabel("Dirección:"));
        tfDireccion = new JTextField();
        panel.add(tfDireccion);

        panel.add(new JLabel("Código Postal:"));
        tfCodigoPostal = new JTextField();
        panel.add(tfCodigoPostal);

        panel.add(new JLabel("Referencia:"));
        tfReferencia = new JTextField();
        panel.add(tfReferencia);

        return panel;
    }

    private JPanel crearPanelClasificacion() {
        JPanel panel = new JPanel(new GridLayout(4, 2, 5, 5));

        panel.add(new JLabel("Nombre Clasificación:"));
        tfNombreClas = new JTextField();
        panel.add(tfNombreClas);

        panel.add(new JLabel("Descripción:"));
        tfDescripcionClas = new JTextField();
        panel.add(tfDescripcionClas);

        panel.add(new JLabel("Código de Color:"));
        tfColorCodigo = new JTextField();
        panel.add(tfColorCodigo);

        panel.add(new JLabel("Icono:"));
        tfIcono = new JTextField();
        panel.add(tfIcono);

        return panel;
    }

    private JPanel crearPanelResiduo() {
        JPanel panel = new JPanel(new GridLayout(4, 2, 5, 5));

        panel.add(new JLabel("Nombre Residuo:"));
        tfNombreRes = new JTextField();
        panel.add(tfNombreRes);

        panel.add(new JLabel("Descripción:"));
        tfDescripcionRes = new JTextField();
        panel.add(tfDescripcionRes);

        panel.add(new JLabel("Unidad de Medida:"));
        tfUnidadMedida = new JTextField("Kilogramos");
        panel.add(tfUnidadMedida);

        panel.add(new JLabel("Peligrosidad:"));
        tfPeligrosidad = new JTextField();
        panel.add(tfPeligrosidad);

        return panel;
    }

    private boolean guardarDatosParciales(int tabIndex) {
        switch (tabIndex) {
            case 0:
                if (tfNombre.getText().isEmpty() || tfApellido.getText().isEmpty() || tfCorreo.getText().isEmpty()
                        || tfContrasena.getText().isEmpty() || tfDireccion.getText().isEmpty() || tfCiudad.getText().isEmpty()) {
                    return false;
                }
                usuario.setNombre(tfNombre.getText());
                usuario.setApellido(tfApellido.getText());
                usuario.setCorreo(tfCorreo.getText());
                usuario.setTelefono(tfTelefono.getText());
                usuario.setContrasena_hash(tfContrasena.getText());

                direccion.setDireccion(tfDireccion.getText());
                direccion.setCiudad(tfCiudad.getText());
                direccion.setDistrito(tfDistrito.getText());
                direccion.setCodigo_postal(tfCodigoPostal.getText());
                direccion.setPais(tfPais.getText());
                direccion.setReferencia(tfReferencia.getText());
                return true;

            case 1:
                if (tfNombreClas.getText().isEmpty() || tfDescripcionClas.getText().isEmpty()) {
                    return false;
                }
                clasificacion.setNombre(tfNombreClas.getText());
                clasificacion.setDescripcion(tfDescripcionClas.getText());
                clasificacion.setColor_codigo(tfColorCodigo.getText());
                clasificacion.setIcono(tfIcono.getText());
                return true;

            case 2:
                if (tfNombreRes.getText().isEmpty() || tfDescripcionRes.getText().isEmpty() || tfUnidadMedida.getText().isEmpty()) {
                    return false;
                }
                residuo.setNombre(tfNombreRes.getText());
                residuo.setDescripcion(tfDescripcionRes.getText());
                residuo.setUnidad_medida(tfUnidadMedida.getText());
                residuo.setPeligrosidad(tfPeligrosidad.getText());
                return true;

            default:
                return false;
        }
    }

    private boolean guardarTodoEnBD() {
        SistemaGestionResiduosFacade fachada = new SistemaGestionResiduosFacadeImpl();
        return fachada.registrarFlujoCompleto(usuario, direccion, clasificacion, residuo);
    }

    private void limpiarFormulario() {
        // Limpia campos de Usuario y Dirección
        tfNombre.setText("");
        tfApellido.setText("");
        tfTelefono.setText("");
        tfCorreo.setText("");
        tfContrasena.setText("");
        tfPais.setText("Perú");
        tfCiudad.setText("");
        tfDistrito.setText("");
        tfDireccion.setText("");
        tfCodigoPostal.setText("");
        tfReferencia.setText("");

        // Limpia campos de Clasificación
        tfNombreClas.setText("");
        tfDescripcionClas.setText("");
        tfColorCodigo.setText("");
        tfIcono.setText("");

        // Limpia campos de Residuo
        tfNombreRes.setText("");
        tfDescripcionRes.setText("");
        tfUnidadMedida.setText("Kilogramos");
        tfPeligrosidad.setText("");

        // Resetea los objetos prototipo
        direccion = new PrototypeDireccion();
        usuario = new PrototypeUsuario();
        clasificacion = new PrototypeClasificacionResiduo();
        residuo = new PrototypeResiduo();
    }

    public static void main(String[] args) {
         // Aplicamos Look and Feel Nimbus para asegurar la correcta visualización de los colores
        try {
            UIManager.setLookAndFeel("javax.swing.plaf.nimbus.NimbusLookAndFeel");
        } catch (Exception e) {
            e.printStackTrace();
        }
        SwingUtilities.invokeLater(() -> new RegistroConTabsSeguro());
    }
}