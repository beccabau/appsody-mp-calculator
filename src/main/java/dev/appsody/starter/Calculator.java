package dev.appsody.starter;

import javax.ws.rs.ApplicationPath;
import javax.ws.rs.core.Application;
import java.util.List;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import jdk.nashorn.internal.objects.annotations.Getter;

import java.util.ArrayList;
import javax.ws.rs.PathParam;

@Path("/calculator")
public class Calculator extends Application {
    
    @GET
    @Path("/aboutme")
    @Produces(MediaType.TEXT_PLAIN)
    public String aboutme(){
        return "You can add (+), subtract (-), and multiply (*) with this simple calculator.";
    }

	@GET
    @Path("/{op}/{a}/{b}")
	@Produces(MediaType.TEXT_PLAIN)
    public Response calculate(@PathParam("op") String op, @PathParam("a") String a, @PathParam("b") String b)
    {
        int numA = Integer.parseInt(a);
        int numB = Integer.parseInt(b);

      switch(op)
      {
          case "+":
              return Response.ok(a + "+" + b + "=" + (Integer.toString((numA + numB)))).build();

          case "-":
              return Response.ok(a + "-" + b + "=" + (Integer.toString((numA - numB)))).build();

          case "*":
              return Response.ok(a + "*" + b + "=" + (Integer.toString((numA * numB)))).build();

          default:
          	return Response.ok("Invalid operation. Please Try again").build();
      }
    }
}




	
	
	