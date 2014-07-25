package com.test;


import javafx.event.EventHandler;
import javafx.stage.WindowEvent;

/**
 * @author Rowell Belen
 */
public class JFXApp extends javafx.application.Application {

  private WebServer server = new WebServer();

  @Override
  public void start(javafx.stage.Stage primaryStage) {

    try {
      server.start();
    }
    catch (Exception e) {
      e.printStackTrace();
    }

    // path in current directory for WebEngine.load()
    //final String html = "htmlchess/example.html";
    //final java.net.URI uri = java.nio.file.Paths.get(html).toAbsolutePath().toUri();

    int port = server.getPort();
    String url = "http://localhost:" + port + "/jarstatic/angularjs/index.html";
    System.out.println(url);

    // create WebView with specified local content
    final javafx.scene.web.WebView root = new javafx.scene.web.WebView();
    root.getEngine().setJavaScriptEnabled(true);
    root.getEngine().load(url);
    //root.getEngine().load(uri.toString());
    //root.setZoom(javafx.stage.Screen.getPrimary().getDpi() / 96);


    primaryStage.setTitle("JFXApp");
    primaryStage.setScene(new javafx.scene.Scene(root, 1100, 820));
    primaryStage.show();

    primaryStage.setOnCloseRequest(new EventHandler<WindowEvent>() {
      @Override
      public void handle(WindowEvent event) {
        server.stop();
      }
    });
  }

  public static void main(String[] args) {
    launch(args);
  }

}
