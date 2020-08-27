package br.ce.wcaquino.tasks.prod;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.concurrent.TimeUnit;

import org.junit.Assert;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;

public class HealthCheckIT {

	@Test
	public void healthCheck() throws MalformedURLException {
		DesiredCapabilities cap = DesiredCapabilities.chrome();
		WebDriver driver = new RemoteWebDriver(new URL("http://192.168.0.10:4444/wd/hub"), cap); //Pede para o Hub hospedado neste endereço, iniciar o cap que é uma instância do chrmome
		driver.navigate().to("http://192.168.0.10:9999/tasks"); // navegando para a página do google
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS); // fique 10 segundos em timeout
		
		try { //Método de segurança seguindo o Aquino
			String version = driver.findElement(By.id("version")).getText();
			Assert.assertTrue(version.startsWith("build"));
		} finally {
			driver.quit();
		}

		
	}
}
