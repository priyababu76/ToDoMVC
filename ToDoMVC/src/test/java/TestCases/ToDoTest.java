package TestCases;

import java.time.Duration;
import java.util.ArrayList;
import java.util.Arrays;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import PageObjects.ToDoPage;

public class ToDoTest {
	
	WebDriver driver;
	ToDoPage tp;
	@BeforeClass
	public void setUp() {
		
		driver=new ChromeDriver();
		driver.get("https://todomvc.com/examples/react/dist/");
		driver.manage().window().maximize();
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
		
	}
	@Test(priority=1)
	public void verifyPage() {
		tp=new ToDoPage(driver);
		tp.checkWebsite();
	}
	
	@DataProvider(name="todoList")
	public Object[][] providetodoList(){
		
		ArrayList<String> todo1=new ArrayList<>(Arrays.asList("Yoga","Walking","Cleaning"));
		return new Object[][]{ {todo1}};
	}
	
	@Test(priority=2,dataProvider="todoList")
	public void enterTodo(ArrayList<String> a) {
		
		tp.addTodo(a);
		
	}
	
	@DataProvider(name="CompletedList")
	public Object[][] provideCompletedList(){
		ArrayList<String> completed=new ArrayList<>(Arrays.asList("Yoga","Walking"));
		return new Object[][] {{completed}};
		
	}
	
	@Test(priority=3,dataProvider="CompletedList")
	public void clickCheckBoxes(ArrayList<String> completed) throws InterruptedException
	{
		tp.clickCompleted(completed);
	}
	
	@DataProvider(name="listRemove")
	public Object[][] providerRemove(){
		ArrayList<String> remove=new ArrayList<>(Arrays.asList("Cleaning"));
		return new Object[][] {{remove}};
	}
	
	@Test(priority=4,dataProvider="listRemove")
	public void removeTodo(ArrayList<String> removed) throws InterruptedException {
		tp.removeTodo(removed);
	}
	@Test(priority=5)
	
	public void clickActive() throws InterruptedException {
		tp.getActive();
	}
	
	@Test(priority=6)
	public void clickCompleted() throws InterruptedException {
		tp.getCompleted();
	}
	@Test(priority=7)
	public void clickClearCompleted() throws InterruptedException {
		tp.clearCompleted();
	}
	@Test(priority=8,dataProvider="todoList")
	public void chooseAll(ArrayList<String> a) {
		tp.chooseAll(a);
	}
	 @AfterClass
		public void tearDown() {
			driver.quit();
			
		}

}
