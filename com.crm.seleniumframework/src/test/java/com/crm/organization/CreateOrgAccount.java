package com.crm.organization;

import java.io.FileInputStream;
import java.util.Properties;
import java.util.Random;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

public class CreateOrgAccount {
public static void main(String[] args) throws Throwable {
	
	String key="webdriver.chrome.driver";
	String value="./src/main/resources/chromedriver.exe";
	System.setProperty(key, value);
	WebDriver driver=new ChromeDriver();
	driver.manage().window().maximize();
	
	FileInputStream fis=new FileInputStream("./src/test/resources/commondata1.properties");
	Properties pobj=new Properties();
	pobj.load(fis);
	String URL = pobj.getProperty("url");
	String username = pobj.getProperty("un");
	String password = pobj.getProperty("pwd");
	//System.out.println(username);
	//System.out.println(password);
	
	driver.get(URL);
	driver.findElement(By.xpath("//input[@name='user_name']")).sendKeys(username);
	driver.findElement(By.xpath("//input[@name='user_password']")).sendKeys(password);
	driver.findElement(By.id("submitButton")).submit();
	
	Random ran=new Random();
	FileInputStream fis1 = new FileInputStream("./src/test/resources/Worksheet.xlsx");
	Workbook workbook = WorkbookFactory.create(fis1);
	Sheet sheet=workbook.getSheet("sheet1");
	Row row = sheet.getRow(0);
	Cell cell = row.getCell(0);
	String cellsheet = cell.getStringCellValue()+ran;
	System.out.println("cellsheet="+cellsheet);
	
	driver.findElement(By.xpath("//td[@class=\"tabUnSelected\"]//a[text()='Organizations']")).click();
	driver.findElement(By.xpath("//td[@style='padding-right:0px;padding-left:10px;']//img[@title='Create Organization...']")).click();
	driver.findElement(By.xpath("//input[@name='accountname']")).sendKeys(cellsheet);
	driver.findElement(By.xpath("//input[@title='Save [Alt+S]']")).click();
	Thread.sleep(2000);
	String OrgName=driver.findElement(By.xpath("//span[@class='dvHeaderText']")).getText();
	System.out.println("OrgName= "+OrgName);
	if(OrgName.contains(cellsheet))
	{
		System.out.println("Created org_info is sucussfully");
	}else {
		System.out.println("Created org_info is unsucussfully");
	}
	
	/*driver.findElement(By.xpath("//input[@title='Delete [Alt+D]']")).click();
	Alert al = driver.switchTo().alert();
	al.accept();
    System.out.println("delete sucussfully");*/
    
	driver.findElement(By.xpath("//img[@src='themes/softed/images/user.PNG']")).click();
	driver.findElement(By.xpath("//a[.='Sign Out']")).click();
	Thread.sleep(3000);
	driver.quit();
}
}
