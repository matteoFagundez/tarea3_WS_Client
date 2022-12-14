package servlets;

import java.io.IOException;

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



/**
 * Servlet implementation class AuxCargaDatos
 */
@WebServlet("/AuxCargaDatos")
public class AuxCargaDatos extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public AuxCargaDatos() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String nombre = request.getParameter("nomClase");
        String url = request.getParameter("url");

        String institucion = request.getParameter("institucion");
        try {
        	RequestDispatcher rd;
        	if(buscarclase(nombre).getNombre().equals("NULL1")) {
	        	System.out.println("hola "+institucion+" "+nombre+" "+url+" ");	 
	        	String[] act=listarActividades(institucion);
				String[] Prof=listarProfesoresInt(institucion);
				
	            request.setAttribute("Institucion", institucion);
	            request.setAttribute("Actdep", act);
	            request.setAttribute("Profes", Prof);
	            request.setAttribute("Nombre", nombre);
	            request.setAttribute("Url", url);
	            System.out.println("hola "+institucion+" "+nombre+" "+url+" ");	        	
	            rd = request.getRequestDispatcher("/conAltaClase.jsp");
	            rd.forward(request, response);
        	}
        	else {
        		request.setAttribute("Titulo", "Usted ya esta Reguistrado");
				request.setAttribute("Tipo", "error");
				request.setAttribute("Imagen", "imagenes/explosion.gif");
        		request.setAttribute("Exito", "OK");
        		rd = request.getRequestDispatcher("/altaClase.jsp");
	            rd.forward(request, response);
	            
        	}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        doGet(request, response);
	}

	public String[] listarActividades(String institucion) throws Exception {
		ControladorPublishService cps = new ControladorPublishServiceLocator();
		ControladorPublish port = cps.getControladorPublishPort();
		return port.listarActividades(institucion);
	}
	
	public String[] listarProfesoresInt(String institucion) throws Exception {
		ControladorPublishService cps = new ControladorPublishServiceLocator();
		ControladorPublish port = cps.getControladorPublishPort();
		return port.listarProfesoresInt(institucion);
	}
	
	public DtClase buscarclase(String nombre) throws Exception {
		ControladorPublishService cps = new ControladorPublishServiceLocator();
		ControladorPublish port = cps.getControladorPublishPort();
		return port.buscarclase(nombre);
	} 
}