package PageObjects;

import java.time.Duration;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

public class ToDoPage {
	WebDriver driver;
	WebDriverWait wait;

	public ToDoPage(WebDriver driver) {
		this.driver = driver;
		this.wait = new WebDriverWait(driver, Duration.ofSeconds(5));
		PageFactory.initElements(driver, this);
	}

	@FindBy(xpath = "//h1[contains(text(),'todos')]")
	WebElement lblTodo;

	@FindBy(xpath = "//input[@id='todo-input']")
	WebElement txtTodo;

	@FindBy(xpath = "//input[@type='checkbox']")
	List<WebElement> chkList;

	@FindBy(xpath = "//input[@type='checkbox']/following-sibling::label")
	List<WebElement> lblList;

	@FindBy(xpath = "//button[@data-testid='todo-item-button']")
	List<WebElement> btnRemove;

	@FindBy(xpath = "//span[@class='todo-count']")
	WebElement lblCount;

	@FindBy(xpath = "//a[contains(text(),'Active')]")
	WebElement lblActive;

	@FindBy(xpath = "//a[contains(text(),'Completed')]")
	WebElement lblCompleted;
	
	@FindBy(xpath = "//a[contains(text(),'All')]")
	WebElement lblAll;

	@FindBy(xpath = "//button[contains(text(),'Clear completed')]")
	WebElement lblClearCompleted;

	public void checkWebsite() {
		Assert.assertEquals(lblTodo.getText(), "todos", "Incorrect Page");

	}

	public void addTodo(List<String> todo) {
		for (String s : todo) {
			
			txtTodo.sendKeys(s, Keys.ENTER);

		}
	}

	public void countList(int countList) {

		lblCount = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//span[@class='todo-count']")));
		String[] s = lblCount.getText().split(" ");
		
		String count = Integer.toString(countList);
		if (s[0].equals(count)) {
			
		}
		Assert.assertEquals(s[0], count, "Count is mismatching");
	}

	public void clickCompleted(List<String> completed) throws InterruptedException {
		int size = lblList.size() - 1;
		countList(size);
		int count = 0;

		for (int i = 1; i < chkList.size(); i++) {

			String lblText = lblList.get(i).getText();
			
			if (completed.contains(lblText)) {
				if (!chkList.get(i).isSelected()) {
					chkList.get(i).click();
					count++;
				}

			}

		}
		int remaining = size - count;
		countList(remaining);
		

	}

	public void removeTodo(List<String> removed) throws InterruptedException {

		int listCount = lblList.size();
		JavascriptExecutor js = (JavascriptExecutor) driver;
		int removeCount = 0;

		for (int i = 1; i < chkList.size(); i++) {

			String lblText = lblList.get(i).getText();

			if (removed.contains(lblText)) {
				WebElement removeBtn = btnRemove.get(i - 1);
				Actions action = new Actions(driver);
				action.moveToElement(lblList.get(i)).build().perform();

				wait.until(ExpectedConditions.visibilityOf(removeBtn));
				removeBtn.click();
				removeCount++;

			}
		}
		Thread.sleep(4000);
	}

	public void getActive() throws InterruptedException {
		lblActive.click();

	}

	public void getCompleted() throws InterruptedException {
		lblCompleted.click();

	}

	public void clearCompleted() throws InterruptedException {
		int countComplete = 0;
		int countAllList = chkList.size();
		for (int i = 1; i < countAllList; i++) {

			if (chkList.get(i).isSelected()) {
				countComplete = countComplete + 1;
			}

		}

		if (countComplete > 0) {
			lblClearCompleted.click();
		}

		if (chkList.size() > 0) {
			int countLeft = countAllList - countComplete;
			countList(countLeft);
		}

	}
	public void chooseAll(List<String> list) {
		
	for (String s : list) {
				txtTodo.sendKeys(s);
				System.out.println(txtTodo.getText());
				txtTodo.sendKeys(s, Keys.ENTER);
	
			}
		lblAll.click();
		//addTodo(list);
		//System.out.println(chkList.size());
		WebElement toggleAll=driver.findElement(By.xpath("//input[@id='toggle-all']"));
		toggleAll.click();
	}
}
