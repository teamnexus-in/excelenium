package in.teamnexus.excelenium;

import java.awt.Desktop;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;

/**
 * The entry point of the excelenium application
 */
@SpringBootApplication
public class ExceleniumApplication
{

    /**
     * The main method.
     *
     * @param args the arguments
     */
    public static void main(String[] args)
    {
        SpringApplication.run(ExceleniumApplication.class, args);
    }

    /**
     * This opens the browser and loads the url http://localhost:8080/ which is the application landing page 
     * after the Application Context has been loaded by Spring Boot

     */
    @EventListener({ ApplicationReadyEvent.class })
    private void applicationReady()
    {
        String url = "http://localhost:8080/";
        if (Desktop.isDesktopSupported())
        {
            Desktop desktop = Desktop.getDesktop();
            try
            {
                desktop.browse(new URI(url));
            }
            catch (IOException | URISyntaxException e)
            {
                e.printStackTrace();
            }
        }
        else
        {
            Runtime runtime = Runtime.getRuntime();
            String[] command;

            String operatingSystemName = System.getProperty("os.name").toLowerCase();
            if (operatingSystemName.indexOf("nix") >= 0 || operatingSystemName.indexOf("nux") >= 0)
            {
                String[] browsers = { "opera", "google-chrome", "epiphany", "firefox", "mozilla", "konqueror", "netscape", "links", "lynx" };
                StringBuffer stringBuffer = new StringBuffer();

                for (int i = 0; i < browsers.length; i++)
                {
                    if (i == 0)
                        stringBuffer.append(String.format("%s \"%s\"", browsers[i], url));
                    else
                        stringBuffer.append(String.format(" || %s \"%s\"", browsers[i], url));
                }
                command = new String[] { "sh", "-c", stringBuffer.toString() };
            }
            else if (operatingSystemName.indexOf("win") >= 0)
            {
                command = new String[] { "rundll32 url.dll,FileProtocolHandler " + url };

            }
            else if (operatingSystemName.indexOf("mac") >= 0)
            {
                command = new String[] { "open " + url };
            }
            else
            {
                System.out.println("an unknown operating system!!");
                return;
            }

            try
            {
                if (command.length > 1)
                {
                    runtime.exec(command); // linux
                }
                else
                {
                    runtime.exec(command[0]); // windows or mac
                }
            }
            catch (IOException e)
            {
                e.printStackTrace();
            }
        }
    }

}
