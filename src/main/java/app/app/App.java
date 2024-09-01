package app;

import io.jooby.Context;
import io.jooby.Jooby;
import io.jooby.StatusCode;
import io.jooby.netty.NettyServer;

public class App extends Jooby {

  {
    install(new NettyServer());
    before(ctx -> ctx.setResponseCode(StatusCode.OK_CODE));
    use(next -> ctx -> {
      long start = System.currentTimeMillis();

      Object response = next.apply(ctx);

      long end = System.currentTimeMillis();
      long took = end - start;

      System.out.println("Took: " + took + "ms");
      //System.out.println(response.toString());
      return response;
    });
    get("/", ctx -> {Thread.sleep(1000);return "Filter";});
    get("/use/{id}", ctx -> "Hello "+ ctx.path("id").value()+" to the portal");



    get("/test", ctx -> {
      return "filter";
    });
  }

  public static void main(final String[] args) {
    runApp(args, App::new);
  }

}
