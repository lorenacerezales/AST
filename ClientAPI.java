import java.util.Scanner;

import org.apache.axiom.om.*;
import org.apache.axis2.AxisFault;
import org.apache.axis2.addressing.EndpointReference;
import org.apache.axis2.client.Options;
import org.apache.axis2.client.ServiceClient;
import org.apache.axis2.rpc.client.RPCServiceClient;

public class ClientAPI {
	
	static OMFactory fac = OMAbstractFactory.getOMFactory();
	static OMNamespace omNs = fac.createOMNamespace("http://Noticia", "ns1");

	public static void main(String[] args) throws AxisFault {

		ServiceClient serviceClient = new ServiceClient();
			
			String metodo=new String();
			String valor=new String();
			String info=new String();
			
		
			
		
		
		// create option object
		Options opts = new Options();
		//setting target EPR
		opts.setTo(new EndpointReference("http://localhost:7158/axis2/services/Noticia"));
		//Setting action
		opts.setAction("urn:Cliente");
		//setting created option into service client
		serviceClient.setOptions(opts);
		
				
		Scanner scan=new Scanner(System.in);
		
            do {
            	
            	//Menu de opciones
            	
            	System.out.println("Seleccione la operación que desea realizar:");
            	System.out.println("  1) Establecer Titular.");
            	System.out.println("  2) Establecer Descripción.");
            	System.out.println("  3) Establecer URL.");
            	System.out.println("  4) Obtener Titular.");
            	System.out.println("  5) Obtener Descripción.");
            	System.out.println("  6) Obtener URL.");
            	
            	System.out.println("  7) SALIR.");
            	
            	
            	Integer operacion=Integer.parseInt(scan.nextLine());
            	
            	System.out.println("\n\n");
            	
            	OMElement method=null;
            	OMElement res=null;
            	
            	switch(operacion) {
            	
            	case 1: 
            		System.out.println("Introduzca el Titular de la noticia:");
            		String titular=scan.nextLine();
            		
            		serviceClient.sendRobust(createPayLoad("setTitular","titular",titular));
            		
            		System.out.println("\n -Se ha establecido el Titular.\n");
            		break;
       
            	case 2:
            		System.out.println("Introduzca la descripción que quiere añadir a la noticia:");
            		serviceClient.sendRobust(createPayLoad("setDescripcion","descripcion",scan.nextLine()));
            		
            		System.out.println("\n -Se ha añadido la descripcion.\n");
            		break;
            		
            	case 3:
            		System.out.println("Introduzca la URL de la noticiar:");
            		serviceClient.sendRobust(createPayLoad("setUrl","url",scan.nextLine()));
            		
            		System.out.println("\n -Se ha añadido la URL a la noticia.\n");
            		
            		break;
            		
            	case 4:
            		 method=fac.createOMElement("getTitular",omNs);
            		 try{
            			 res=serviceClient.sendReceive(method);
            		 }catch(AxisFault e) {
            			 System.out.println("Error al recibir la respuesta: "+e.toString());
            		 }
            		 
            		 System.out.println("~Titular:   "+res.getFirstElement().getText());
            		
            		break;
            		
            	case 5:
            		 method=fac.createOMElement("getDescripcion",omNs);
            		 try{
            			 res=serviceClient.sendReceive(method);
            		 }catch(AxisFault e) {
            			 System.out.println("Error al recibir la respuesta: "+e.toString());
            		 }
            		 System.out.println("~Descripción:   "+res.getFirstElement().getText());
            		
            		break;
            		
            	case 6:
            		 method=fac.createOMElement("getUrl",omNs);
            		 try{
            			 res=serviceClient.sendReceive(method);
            		 }catch(AxisFault e) {
            			 System.out.println("Error al recibir la respuesta: "+e.toString());
            		 }
            		 System.out.println("~URL:   "+res.getFirstElement().getText());
            		
            		break;
            		
            	case 7: 
            		System.out.println("Saliendo del servicio...");
            		System.exit(0);
            		
            		break;
            		
            	default: break;
            	
            	}
            	
            	
            	
				
				
			}while(true);
	}
	
	public static OMElement createPayLoad(String metodo,String valor,String info) {
		
		OMElement method = fac.createOMElement(metodo, omNs);
		OMElement value = fac.createOMElement(valor, omNs);
		value.setText(info);
		method.addChild(value);
		return method;
	} 

}
