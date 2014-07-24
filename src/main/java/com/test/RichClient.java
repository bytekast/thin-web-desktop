package com.test;

import org.eclipse.swt.SWT;
import org.eclipse.swt.browser.Browser;
import org.eclipse.swt.events.ShellAdapter;
import org.eclipse.swt.events.ShellEvent;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;

public class RichClient {
  private static final int APP_HEIGHT = 600;
  private static final int APP_WIDTH = 800;
  private static final String APP_TITLE = "Standalone Java App";

  private Shell shell;
  private Display display;
  private Browser browser;
  private GridData browserLayout;

  public RichClient(final String url){
    initializeApplication(url);
  }

  public void initializeApplication(final String url) {
    display = new Display();

    shell = new Shell(display);
    shell.setText(APP_TITLE);
    shell.setSize(APP_WIDTH, APP_HEIGHT);
    shell.setLayout(new GridLayout());

    browser = new Browser(shell, SWT.NONE);
    browserLayout = new GridData(GridData.FILL_BOTH);
    browserLayout.grabExcessHorizontalSpace = true;
    browserLayout.grabExcessVerticalSpace = true;
    browser.setLayoutData(browserLayout);

    // Detect [X] or ALT-F4 or something that kills this window...
    shell.addShellListener( new ShellAdapter() {
      public void shellClosed( ShellEvent e ) {
        onClose();
      }
    } );

    browser.setUrl(url);

    System.out.println("browser initialized");

    shell.open();
    while (!shell.isDisposed()) {
      if (!display.readAndDispatch()) {
        display.sleep();
      }
    }
    display.dispose();
  }

  public void onClose() {
    shell.dispose();
  }

  public void close(){
    // Run in Display thread
    display.asyncExec(new Runnable() {
      @Override
      public void run() {
        shell.close();
      }
    });
  }
}
