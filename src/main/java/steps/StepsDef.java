package main.java.steps;

import java.util.ArrayList;
import java.util.List;

import io.cucumber.java.en.And;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.testng.Assert;

import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import test.java.runner.Runner;

public class StepsDef extends Runner {

    WebElement element;
    int completed=0;
    int all=0;

    String url = "http://todomvc.com/examples/vue/";

    @Given("^user has launched the url$")
    public void user_has_launched_the_url() {
        js = (JavascriptExecutor) driver;
        actions = new Actions(driver);
        driver.get(url);

        System.out.println("Url has been opened.....");
    }

    @When("^user clicks on what needs to be done text box$")
    public void user_clicks_on_Learn_More_button() {
        element = driver.findElement(By.className("new-todo"));
        element.click();

        System.out.println("user clicks on what needs to be done");

    }


    @And("^user creates todo (.+) list$")
    public void userCreatesTodoTodoListNameList(String todoListName) {
        element.clear();
        element.sendKeys(todoListName, Keys.ENTER);
    }

    @Then("^verify the (.+) item is created successfully$")
    public void verifyTheTodoItemIsCreatedSuccessfully(String todoListName) {
        List<WebElement> elementlist = driver.findElements(By.xpath("//li[@class='todo']//label"));
        List<String> listoftext = new ArrayList<>();
        for (WebElement ele : elementlist) {
            listoftext.add(ele.getText().trim());
        }

    }

    @Then("^verify the item is created successfully$")
    public void verifyTheItemIsCreatedSuccessfully(DataTable dataTable) {
        List<String> listtodoitems = dataTable.asList(String.class);
        List<WebElement> elementlist = driver.findElements(By.xpath("//li[@class='todo']//label"));
        List<String> listoftext = new ArrayList<>();

        for (WebElement ele : elementlist) {
            listoftext.add(ele.getText().trim());
        }
        System.out.println("items have been added successfully");


        for (String ele : listtodoitems) {
            Assert.assertTrue(listoftext.contains(ele.trim()), ele + " is not found in the list");
        }
        System.out.println("items have been verified");
        all=listoftext.size();
    }

    @And("^user creates todo list$")
    public void userCreatesTodoList(DataTable dataTable) {
        List<String> listtodoitems = dataTable.asList(String.class);
        // create the todo items
        for (String item : listtodoitems) {
            element.clear();
            element.sendKeys(item, Keys.ENTER);
        }
        System.out.println("to do items has been created....");
    }

    @When("^user deletes two items form list$")
    public void userDeletesTwoItemsFormList() {

        // select the to do items
        List<WebElement> checkboxlist = driver.findElements(By.xpath("//ul[@class='todo-list']//li//div//input"));

        for (int i = 0; i < checkboxlist.size() - 1; i++) {
            actions.click(checkboxlist.get(i)).build().perform();
        }
        System.out.println("some items are deleted.....");

        // delete the selected to do items
        WebElement element = driver.findElement(By.xpath("//button[@class='clear-completed']"));
        actions.click(element).build().perform();
        System.out.println("user clicks on the all task left button.....");
    }

    @Then("^verify one item is still there in the list$")
    public void verifyOneItemIsStillThereInTheList() {

        List<WebElement> todoitem = driver.findElements(By.xpath("//li[contains(@class,'todo')]"));
        Assert.assertTrue(todoitem.size() > 0, " no to do item  found");
		System.out.println("No of all task "+todoitem.size());
    }

    @When("^user marks the one task as completed$")
    public void user_marks_one_taks_as_completed() {
        List<WebElement> checkboxlist1 = driver.findElements(By.xpath("//ul[@class='todo-list']//li//div//input"));
        actions.click(checkboxlist1.get(0)).build().perform();
        completed++;
    }

	@When("^user clicks on completed button and verify the list$")
	public void user_clicks_on_completed_button_and_verify_the_list() {
		driver.findElement(By.xpath("//a[text()='Completed']")).click();

		List<WebElement> todoitem = driver.findElements(By.xpath("//li[contains(@class,'todo')]"));
		Assert.assertTrue(todoitem.size() ==completed, " no to do item  found");
		System.out.println("No of completed task "+todoitem.size());
	}


	@When("^user clicks on all button and verify the list$")
	public void user_clicks_on_all_button_and_verify_the_list() {
		driver.findElement(By.xpath("//a[text()='All']")).click();

		List<WebElement> todoitem = driver.findElements(By.xpath("//li[contains(@class,'todo')]"));
		Assert.assertTrue(todoitem.size() ==all, " no to do item  found");
		System.out.println("No of all task "+todoitem.size());
	}

	@When("^user clicks on active button and verify the list$")
	public void user_clicks_on_active_button_and_verify_the_list() {
		driver.findElement(By.xpath("//a[text()='Active']")).click();

		List<WebElement> todoitem = driver.findElements(By.xpath("//li[contains(@class,'todo')]"));
		Assert.assertTrue(todoitem.size() ==(all-completed), " no to do item  found");
		System.out.println("No of Active task "+todoitem.size());
	}

}
