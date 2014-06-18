package com.xabe.formData.ws.prueba;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.DefaultValue;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.glassfish.jersey.media.multipart.ContentDisposition;
import org.glassfish.jersey.media.multipart.FormDataBodyPart;
import org.glassfish.jersey.media.multipart.FormDataContentDisposition;
import org.glassfish.jersey.media.multipart.FormDataMultiPart;
import org.glassfish.jersey.media.multipart.FormDataParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.xabe.formData.model.Message;
import com.xabe.formData.model.prueba.Prueba;
import com.xabe.formData.model.prueba.PruebaExample;
import com.xabe.formData.persistence.PaginationContext;
import com.xabe.formData.service.prueba.PruebaService;
import com.xabe.formData.ws.BaseRest;

@Path("/prueba")
@Component
@Scope(value = "request")
public class PruebaRest extends BaseRest {

	@Autowired
	private PruebaService service;

	@GET
	@Path("/{Id}")
	@Produces(MediaType.APPLICATION_JSON)
	public List<Prueba> getId(@DefaultValue("1") @PathParam("Id") Integer Id) {
		PruebaExample example = new PruebaExample();
		example.createCriteria().andIdEqualTo(Id);
		return service.getAll(example);
	}

	@GET
	@Path("/pagination")
	@Produces(MediaType.APPLICATION_JSON)
	public List<Prueba> getCursosPagination(
			@DefaultValue("0") @QueryParam("frist") Integer first,
			@DefaultValue("10") @QueryParam("pageSize") Integer pageSize,
			@DefaultValue("id") @QueryParam("sortField") String sortField,
			@DefaultValue("ASC") @QueryParam("sortOrder") String sortOrder) {

		PruebaExample example = new PruebaExample();
		if (sortField != null) {
			example.setOrderByClause(sortField + " " + sortOrder);
		}
		PaginationContext paginationContext = new PaginationContext();
		paginationContext.setSkipResults(first);
		paginationContext.setMaxResults(pageSize);
		return service.getPaginated(example, paginationContext);
	}

	@GET
	@Path("/all")
	@Produces(MediaType.APPLICATION_JSON)
	public List<Prueba> getAll(
			@DefaultValue("id") @QueryParam("sortField") String sortField,
			@DefaultValue("ASC") @QueryParam("sortOrder") String sortOrder) {

		PruebaExample example = new PruebaExample();
		if (sortField != null) {
			example.setOrderByClause(sortField + " " + sortOrder);
		}
		return service.getAll(example);
	}

	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response post(Prueba entity) {
		Response response = null;
		try {
			service.add(entity);
			String location = String.format("%s%s", uriInfo.getAbsolutePath()
					.toString(), entity.getId());
			Message message = new Message();
			message.setMessage("Creado");
			response = Response.created(new URI(location)).entity(message)
					.build();
		} catch (URISyntaxException e) {
			logger.error("Error al añadir una prueba " + e.getMessage());
			response = Response.status(500).entity(e.getMessage()).build();
		}
		return response;
	}
	
	@POST
	@Path("/form")
	@Consumes(MediaType.MULTIPART_FORM_DATA)
	@Produces(MediaType.APPLICATION_JSON)
	public Response postMulti(	@FormDataParam("nombre") String nombre,
								@FormDataParam("file") InputStream file,
            					@FormDataParam("file") FormDataContentDisposition fileDisposition) {

		Response response = null;
		Prueba entity = null;
		try 
		{			
			entity = new Prueba();
			entity.setNombre(nombre);
			entity.setNombrefichero(fileDisposition.getFileName());
			writeToFile(file, System.getProperty("user.home")+File.separator+fileDisposition.getFileName());
			service.add(entity);			
			String location = String.format("%s%s", uriInfo.getAbsolutePath().toString(), entity.getId());
			Message message = new Message();
			message.setMessage("Creado");
			response = Response.created(new URI(location)).entity(message).build();
		} catch (URISyntaxException e) {
			logger.error("Error al añadir una prueba " + e.getMessage());
			response = Response.status(500).entity(e.getMessage()).build();
		}
		return response;
	
	}
	
	
	@POST
	@Path("/formData")
	@Consumes(MediaType.MULTIPART_FORM_DATA)
	@Produces(MediaType.APPLICATION_JSON)
	public Response postMulti(FormDataMultiPart multiPart) {
		Response response = null;
		Prueba entity = null;
		try {
			
			//Vamos a leer parametro model que es un json 
			List<FormDataBodyPart> model = multiPart.getFields("model"); 
			if(model.size() > 0)
			{

				ObjectMapper mapper = new ObjectMapper();
				entity = mapper.readValue(model.get(0).getValue(), Prueba.class);
				
				//Vamo a leer los ficheros
				Map<String, List<FormDataBodyPart>> files = multiPart.getFields(); 
				for (Entry<String, List<FormDataBodyPart>> entry : files.entrySet()) {
					if(entry.getKey().equalsIgnoreCase("file_0"))
					{
						ContentDisposition contentDisposition = entry.getValue().get(0).getContentDisposition();
						writeToFile(entry.getValue().get(0).getValueAs(InputStream.class), System.getProperty("user.home")+File.separator+contentDisposition.getFileName());
						entity.setNombrefichero(contentDisposition.getFileName());
					}				
				}		
				
				service.add(entity);
				String location = String.format("%s%s", uriInfo.getAbsolutePath().toString(), entity.getId());
				Message message = new Message();
				message.setMessage("Creado");
				response = Response.created(new URI(location)).entity(message).build();
			}
			else
			{
				Response.status(500).entity("Error no tiene todos los campos").build();
			}
		} catch (Exception e) {
			logger.error("Error al añadir una prueba " + e.getMessage());
			response = Response.status(500).entity(e.getMessage()).build();
		}
		return response;
	}
	
	private void writeToFile(InputStream uploadedInputStream,String uploadedFileLocation) {
		try 
		{
			File file = new File(uploadedFileLocation);
			  if (!file.exists()) {
			    file.createNewFile();
			 }
			OutputStream out = new FileOutputStream(file);
			int read = 0;
			byte[] bytes = new byte[1024];
			while ((read = uploadedInputStream.read(bytes)) != -1) {
				out.write(bytes, 0, read);
			}
			out.flush();
			out.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
 
	}

	@PUT
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response update(Prueba entity) {
		Response response = null;
		try {
			service.update(entity);
			Message message = new Message();
			message.setMessage("Modificado");
			response = Response.accepted().entity(message).build();
		} catch (Exception e) {
			logger.error("Error al actulizar una prueba " + e.getMessage());
			response = Response.status(500).entity(e.getMessage()).build();
		}
		return response;
	}

	@PUT
	@Path("/{Id}")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response deleteId(Prueba entity) {
		Response response = null;
		try {

			service.delete(entity);
			Message message = new Message();
			message.setMessage("Borrado");
			response = Response.accepted().entity(message).build();
		} catch (Exception e) {
			logger.error("Error al borrar una prueba " + e.getMessage());
			response = Response.status(500).entity(e.getMessage()).build();
		}
		return response;
	}

	@DELETE
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response delete(Prueba entity) {
		Response response = null;
		try {
			service.delete(entity);
			Message message = new Message();
			message.setMessage("Borrado");
			response = Response.accepted().entity(message).build();
		} catch (Exception e) {
			logger.error("Error al borrar una prueba " + e.getMessage());
			response = Response.status(500).entity(e.getMessage()).build();
		}
		return response;
	}

	@DELETE
	@Path("/{Id}")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response deleteId(@DefaultValue("1") @PathParam("Id") Integer Id) {
		Response response = null;
		try {
			Prueba entity = new Prueba();
			entity.setId(Id);
			service.delete(entity);
			Message message = new Message();
			message.setMessage("Borrado");
			response = Response.accepted().entity(message).build();
		} catch (Exception e) {
			logger.error("Error al borrar una prueba " + e.getMessage());
			response = Response.status(500).entity(e.getMessage()).build();
		}
		return response;
	}

}