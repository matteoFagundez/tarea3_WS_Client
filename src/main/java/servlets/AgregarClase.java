package servlets;


import java.io.IOException;
import java.util.Calendar;
import java.util.GregorianCalendar;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import publicadores.ControladorPublish;
import publicadores.ControladorPublishService;
import publicadores.ControladorPublishServiceLocator;
import publicadores.DtClase;

import java.io.File;



@WebServlet("/AgregarClase")
public class AgregarClase extends HttpServlet {
    private static final long serialVersionUID = 1L;

    public AgregarClase() {
        super();
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.getWriter().append("Served at: ").append(request.getContextPath());
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String nombre = request.getParameter("nomClase");
        String url = request.getParameter("url");

        String institucion = request.getParameter("institucion");
        String profe = request.getParameter("Profesor");
        String actividad = (String)request.getParameter("Actividad");
        // open image
        File imagen = new File(request.getParameter("foto"));
        String Url ="imagenes/"+request.getParameter("foto");
        //BufferedImage bufferedImage = ImageIO.read(imagen);
        byte[] buffer = new byte[(int) imagen.length()];
         //get DataBufferBytes from Raster
    
  

        try {
        	String fechaString = request.getParameter("fecha"); //Toma la fecha como String del formulario
        	Calendar fecha =  formatoCalendar(fechaString); // La convierte en Calendar
        	 
        	
        	String fechaSisString =  fechaActual(); 
        	Calendar fechaSis = formatoCalendar(fechaSisString); // La convierte en Calendar
           
        	// Date fech =new Date();
            String horaString = request.getParameter("fecha"); //Toma la fecha como String del formulario
        	Calendar hora =  formatoCalendar(horaString); // La convierte en Calendar
            
            
            System.out.println("hola "+institucion+" "+profe+" "+actividad+" "+nombre+" "+url+" "+fechaSis+" "+fecha+" "+hora);
            publicadores.DtClase dtClase= new DtClase(nombre, fecha, hora, url,fechaSis,Url, buffer, null, null, null);
            altaClase(dtClase,institucion,actividad,profe);
            request.setAttribute("mensaje ", "Se ha ingresado correctamente la clase " + nombre + " en el sistema.");
            System.out.println("Paso el test\n");
            RequestDispatcher rd;
            request.setAttribute("Exito", "OK");
        	rd = request.getRequestDispatcher("/altaClase.jsp");
            rd.forward(request, response);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
    
    public void altaClase(DtClase dtClase,String institucion,String actividad,String profe) throws Exception {
		ControladorPublishService cps = new ControladorPublishServiceLocator();
		ControladorPublish port = cps.getControladorPublishPort();
		port.altaClase( dtClase, institucion, actividad, profe);
	}
    
  //CONVIERTE STRING EN FORMATO CALENDAR
    public static Calendar formatoCalendar(String DateHum){
      String[] fecVct = DateHum.split("-"); //cambiar a signo dependiendo la antrada
      int dia = Integer.parseInt(fecVct[0]);
      int mes = Integer.parseInt(fecVct[1])-1;
      int year = Integer.parseInt(fecVct[2]);
      Calendar cal;
      cal = Calendar.getInstance();
      cal.clear();
      cal.set(year,mes,dia);

      return cal;
    }
    
  //FORMAR FECHA DEL SISTEMA GREGORIANCALENDAR
    public static String fechaActual(){
    	//Instanciamos el objeto Calendar
        Calendar fecha = new GregorianCalendar();
        //Obtenemos el valor del año, mes, día,
        //usando el método get y el parámetro correspondiente                                                     
        int año = fecha.get(Calendar.YEAR);
        int mes = fecha.get(Calendar.MONTH);
        int dia = fecha.get(Calendar.DAY_OF_MONTH);
        String fechaActual = "" + dia + "-" + (mes+1) + "-" + año;
        System.out.println(fechaActual);
        return fechaActual;
    }
    
}