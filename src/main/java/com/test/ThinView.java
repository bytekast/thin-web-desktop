package com.test;

/**
 * @author Rowell Belen
 */
public class ThinView extends javafx.application.Application {


  @Override
  public void start(javafx.stage.Stage primaryStage) {

    // path in current directory for WebEngine.load()
    //final String html = "htmlchess/example.html";
    //final java.net.URI uri = java.nio.file.Paths.get(html).toAbsolutePath().toUri();

    // create WebView with specified local content
    final javafx.scene.web.WebView root = new javafx.scene.web.WebView();
    root.getEngine().load("http://google.com");
    //root.getEngine().load(uri.toString());
    //root.setZoom(javafx.stage.Screen.getPrimary().getDpi() / 96);

    primaryStage.setTitle("HTML Chess");
    primaryStage.setScene(new javafx.scene.Scene(root, 1100, 820));
    primaryStage.show();
  }

  public static void main(String[] args) {
    launch(args);
  }

}
