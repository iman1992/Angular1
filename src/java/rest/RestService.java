
package rest;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import entity.Car;
import exception.CarNotFoundException;
import facade.ControlFacade;
import interfaces.IControlFacade;
import java.util.List;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.Produces;
import javax.ws.rs.GET;
import static javax.ws.rs.HttpMethod.PUT;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.Response;


@Path("car")
public class RestService {

    @Context
    private UriInfo context;
    private final Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd").setPrettyPrinting().create();
    private final EntityManagerFactory emf = Persistence.createEntityManagerFactory("PU");
    
    private final IControlFacade facade = new ControlFacade(emf);

    public RestService() {
    }

    @GET
    @Path("/complete")
    @Produces("application/json")
    public Response getCars() {
        List<Car> cars = facade.getCars();
        return Response.ok(gson.toJson(cars)).build();
    }
    
    @GET
    @Path("{id}")
    @Produces("application/json")
    public Response getCar(@PathParam("id") int id) throws CarNotFoundException {
        Car c = facade.getCar(id);
        return Response.ok(gson.toJson(c)).build();
    }
    
    @POST
    @Consumes("application/json")
    public Response createCar(String car){
        Car c = gson.fromJson(car, Car.class);
        facade.addCar(c);
        return Response.status(Response.Status.CREATED).build();

    }
    
    @PUT
    @Consumes("application/json")
    public Response editCar(String car) {
        Car c = gson.fromJson(car, Car.class);
        facade.editCar(c);
        return Response.ok().build();
    }
    
    @DELETE
    @Path("{id}")
    public Response deleteCompany(@PathParam("id") int id) {
        facade.deleteCar(id);
        return Response.ok().build();
    }
    
    
}
