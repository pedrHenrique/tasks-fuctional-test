package br.ce.wcaquino.tasks.functional;

import static org.junit.Assert.assertEquals;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.concurrent.TimeUnit;

import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;

public class TasksTest { // O Selenium é quem dá a habilidade de interagit com o Browser

	public WebDriver acessarAplicacao() throws MalformedURLException { //Isse teste, seria com base para ser feito em outra máquina.
		//System.setProperty("webdriver.chrome.driver","C:\\Users\\Pedro\\.dev\\devops\\util\\chromedriver.exe");
		//WebDriver driver = new ChromeDriver(); // driver do Chrome Iniciado
		DesiredCapabilities cap = DesiredCapabilities.chrome();
		WebDriver driver = new RemoteWebDriver(new URL("http://192.168.0.10:4444/wd/hub"), cap); //Pede para o Hub hospedado neste endereço, iniciar o cap que é uma instância do chrmome
		driver.navigate().to("http://192.168.0.10:8001/tasks"); // navegando para a página do google
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS); // fique 10 segundos em timeout
		return driver;
	}

	@Test
	public void deveSalvarTarefaComSucesso() throws MalformedURLException {
		WebDriver driver = acessarAplicacao();
		try {
			driver.findElement(By.id("addTodo")).click(); // encontre o elemento com id x e click nele

			driver.findElement(By.id("task")).sendKeys("deveSalvarTarefaComSucesso"); // inseri valor nos campos

			driver.findElement(By.id("dueDate")).sendKeys("10/10/2030"); // Inseri valor nos campos

			driver.findElement(By.id("saveButton")).click();

			String mensagem = driver.findElement(By.id("message")).getText();

			assertEquals("Success!", mensagem);

		} finally {
			driver.quit();
		}

	}

	@Test
	public void naoDeveSalvarTarefaSemDescricao() throws MalformedURLException {
		WebDriver driver = acessarAplicacao();
		try {
			driver.findElement(By.id("addTodo")).click(); 

			driver.findElement(By.id("task")).sendKeys("naoDeveSalvarTarefaSemDescricao");
			
			driver.findElement(By.id("saveButton")).click();

			String mensagem = driver.findElement(By.id("message")).getText();

			assertEquals("Fill the due date", mensagem);

		} finally {
			driver.quit();
		}

		// Fill the task description
		// Due date must not be in past

	}

	@Test
	public void naoDeveSalvarTarefaSemData() throws MalformedURLException {
		WebDriver driver = acessarAplicacao();
		try {
			driver.findElement(By.id("addTodo")).click(); 

			driver.findElement(By.id("dueDate")).sendKeys("10/10/2030"); 

			driver.findElement(By.id("saveButton")).click();

			String mensagem = driver.findElement(By.id("message")).getText();

			assertEquals("Fill the task description", mensagem);

		} finally {
			driver.quit();
		}
	}

	@Test
	public void naoDeveSalvarTarefaComDataPassada() throws MalformedURLException {
		WebDriver driver = acessarAplicacao();
		try {
			driver.findElement(By.id("addTodo")).click(); 

			driver.findElement(By.id("task")).sendKeys("naoDeveSalvarTarefaComDataPassada"); 

			driver.findElement(By.id("dueDate")).sendKeys("10/10/1900"); 

			driver.findElement(By.id("saveButton")).click();

			String mensagem = driver.findElement(By.id("message")).getText();

			assertEquals("Due date must not be in past", mensagem);
		} finally {
			driver.quit();
		}
	}
}
