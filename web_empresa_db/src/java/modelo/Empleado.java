/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package modelo;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.sql.PreparedStatement;
import java.util.Set;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author salaz
 */
public class Empleado extends Persona {
    private String codigo;
    private int id_puesto;
    private conexion cn;
   
    public Empleado() {
    }

    public Empleado(String codigo, int id_puesto, int id, String nombres, String apellidos, String direccion, String telefono, String fecha_nacimiento) {
        super(id, nombres, apellidos, direccion, telefono, fecha_nacimiento);
        this.codigo = codigo;
        this.id_puesto = id_puesto;
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public int getId_puesto() {
        return id_puesto;
    }

    public void setId_puesto(int id_puesto) {
        this.id_puesto = id_puesto;
    }

    public HashMap drop_sangre(){
    HashMap<String,String> drop = new HashMap ();
    try{
        cn = new conexion();
        String query = "SELECT id_puesto as id, puesto FROM bd_empresa.puestos;";
        cn.abirir_Conexion();
        ResultSet consulta = cn.conexion.createStatement().executeQuery(query);
        while (consulta.next()){
           drop.put(consulta.getString("id"), consulta.getString("puesto"));
        }
        cn.cerrar_Conexion();
    }catch(Exception ex){
        System.out.println(ex.getMessage());
    }
    return drop;
    }
    
    
   public  DefaultTableModel leer() {
       DefaultTableModel tabla = new DefaultTableModel();
       try{
            cn = new conexion();
          cn.abirir_Conexion();
          String query= "SELECT e.id_empleado as id,e.codigo,e.nombres,e.apellidos,e.direccion,e.telefono,e.fecha_nacimiento,p.puesto,p.id_puesto FROM  empleados as e inner join puestos as p on  e.id_puesto=p.id_puesto;";
          ResultSet consulta = cn.conexion.createStatement().executeQuery(query);
          String encabezado []= {"id","codigo","nombres","apellidos","direccion","telefono","nacimiento","puesto"};
          tabla.setColumnIdentifiers(encabezado);
          String datos[] = new String [9];
          while (consulta.next()){
              datos[0] = consulta.getString("id");
              datos[1] = consulta.getString("codigo");
              datos[2] = consulta.getString("nombres");
              datos[3] = consulta.getString("apellidos");
              datos[4] = consulta.getString("direccion");
              datos[5] = consulta.getString("telefono");
              datos[6] = consulta.getString("fecha_nacimiento");
              datos[7] = consulta.getString("puesto");
              datos[8] = consulta.getString("id_puesto");
          tabla.addRow(datos);
         
          }
          cn.cerrar_Conexion();
       }catch(Exception ex){  
           System.out.println(ex.getMessage());
       }
       return tabla;
   }
    public int agregar (){
        int retorno = 0;
    try {
        PreparedStatement parametro;
        cn = new conexion ();
        String query="INSERT INTO empleados(codigo,nombres,apellidos,direccion,telefono,fecha_nacimiento,id_puesto)VALUES(?,?,?,?,?,?,?);";
        cn.abirir_Conexion();
        parametro = (PreparedStatement)cn.conexion.prepareStatement(query);
        parametro.setString(1, getCodigo());
        parametro.setString(2, getNombres());
        parametro.setString(3, getApellidos());
        parametro.setString(4, getDireccion());
        parametro.setString(5, getTelefono());
        parametro.setString(6, getFecha_nacimiento());
        parametro.setInt(7, getId_puesto());

        retorno = parametro.executeUpdate();
        cn.cerrar_Conexion();
        
    }catch(Exception ex){
    System.out.println(ex.getMessage());
    retorno = 0;
}
            return retorno;    
}
}


      

