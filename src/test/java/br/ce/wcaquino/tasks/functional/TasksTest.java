package br.ce.wcaquino.tasks.functional;

import static org.junit.Assert.assertEquals;

import java.util.concurrent.TimeUnit;

import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

public class TasksTest { // O Selenium é quem dá a habilidade de interagit com o Browser

	public WebDriver acessarAplicacao() {
		System.setProperty("webdriver.chrome.driver","C:\\Users\\Pedro\\.dev\\devops\\util\\chromedriver.exe");
		WebDriver driver = new ChromeDriver(); // driver do Chrome Iniciado
		driver.navigate().to("http://localhost:8001/tasks"); // navegando para a página do google
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS); // fique 10 segundos em timeout
		return driver;
	}

	@Test
	public void deveSalvarTarefaComSucesso() {
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
	public void naoDeveSalvarTarefaSemDescricao() {
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
	public void naoDeveSalvarTarefaSemData() {
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
	public void naoDeveSalvarTarefaComDataPassada() {
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
