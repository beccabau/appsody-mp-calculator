## Creating a new Appsody project using the VS Code Codewind plugin
Info: https://eclipse.org/codewind/     (also see: codewind.dev)
(Also see Kabanero getting started guide > https://github.com/gcharters/kabanero-dev-getting-started#creating-a-new-codewind-project)

1. Create a new MicroProfile project. Hover over the *Projects* entry underneath *Codewind* in *Visual Studio Code* and press the *+* icon to create a new project
2. Choose the `Appsody Eclipse Microprofile Template` and give the project a name, like `Appsody Calculator` (in this example)
3. Press `Enter` to create the project

The project has now been generated and will take a little time to build. To monitor the progress, expand `Codewind` -> `Projects` and right click the menu option `Show all logs:`

After a while, you should see a log message like below:
```
[Container] [�[1;34mINFO�[m] [AUDIT   ] CWWKF0011I: The defaultServer server is ready to run a smarter planet. The defaultServer server started in 69.467 seconds.
```
And the project status should change to `Running`.

To access the application endpoint in a browser, select the Open App icon next to the project's name ![](https://github.com/gcharters/kabanero-dev-getting-started/blob/master/images/open-app.png), or right-click on the project and select the Open App menu option. This opens up the application in the running container showing the Welcome to your Appsody Microservice page.

Let's take a look at the code. In the *VS Code EXPLORER* you should see a `CODEWIND-WORKSPACE` entry with your project name. If you don't find it, right-click on the project and choose Add Folder to Workspace. In the workspace view, expand the project and the sub-folders to show all the files created from the Appsody template (Note, the template is not intended to be a sample as most people would end up having to delete the code each time, it aims to provide the starter code, server configuration and build to which you can add your code).

The main Java files are StarterLivenessCheck, StarterReadinessCheck and StarterApplication. The first two provide the outlines for liveness and readiness checks that can be hooked up to Kubernetes liveness and readiness probes. These are implemented using MicroProflie Health. The third file is a JAX-RS application, which provides the Application Path for your REST API.

Let's add a REST service to your application. Navigate to the `src/main/java/dev/appsody/starter` directory, and create a file called `Calculator.java` - this will be our JAX-RS resource. Populate the file with the following code and save it:


```
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
```

Any changes you make to your code will automatically be built and re-deployed by Codewind, and viewed in your browser. Let's see this in action.

If you still have the logs `OUTPUT` tab open you will see that the code is compiled and the application restarted. You should see messages like:

```
[Container] [�[1;34mINFO�[m] Source compilation was successful.
[Container] [�[1;34mINFO�[m] [AUDIT   ] CWWKT0017I: Web application removed (default_host): http://04013dbc9c11:9080/
[Container] [�[1;34mINFO�[m] [AUDIT   ] CWWKZ0009I: The application starter-app has stopped successfully.
[Container] [�[1;34mINFO�[m] [WARNING ] CWMH0053W: The readiness health check reported a DOWN overall status because the following applications have not started yet: [starter-app]
[Container] [�[1;34mINFO�[m] [AUDIT   ] CWWKT0016I: Web application available (default_host): http://04013dbc9c11:9080/
```

Now we can do some interesting stuff with this new resource. You can point your browser at a couple of things (note, <port> is the port number you saw when you first opened the application):

http://127.0.0.1:<port>/starter/calculator/aboutme
You should see the following response:

```
You can add (+), subtract (-), and multiply (*) with this simple calculator.
```

You could also try a few of the functions:
`http://127.0.0.1:<port>/starter/calculator/{op}/{a}/{b}`, where you can input one of the available operations (`+`, `_`, `*`), and an integer a, and an integer b.
  
So for `http://127.0.0.1:<port>/starter/calculator/+/10/3` you should see:
```
10+3=13
```

And so on.



# appsody-mp-calculator

appsody template java calculator
+, _, *


other information - https://github.com/gcharters/kabanero-dev-getting-started#creating-a-new-codewind-project
jackson annotation - 
JAX-RS - Creating Resources using @ApplicationPath, @Path, @PathParam https://www.logicbig.com/tutorials/java-ee-tutorial/jax-rs/path-annotion-resource-mapping.html
@pathparam annotation example https://www.java2novice.com/restful-web-services/jax-rs-pathparam/
