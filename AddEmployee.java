package basic;
import java.io.FileInputStream;
import java.io.IOException;
import java.time.Duration;
import java.util.Properties;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
public class AddEmployee {
	public static void main(String[] args) throws IOException, InterruptedException {
		WebDriver driver=new ChromeDriver();
		driver.manage().window().maximize();
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
		FileInputStream fis=new FileInputStream("./data/LoginDetails.txt");
		Properties p=new Properties();
		p.load(fis);
		driver.get(p.getProperty("url"));
		driver.findElement(By.name("username")).sendKeys(p.getProperty("uname"));
		driver.findElement(By.name("password")).sendKeys(p.getProperty("pass"));
		driver.findElement(By.xpath("//button[@type='submit']")).click();
		driver.navigate().to("https://opensource-demo.orangehrmlive.com/web/index.php/dashboard/index");
		driver.findElement(By.xpath("//span[.='PIM']")).click();
		driver.navigate().to("https://opensource-demo.orangehrmlive.com/web/index.php/pim/viewEmployeeList");
		FileInputStream fis1=new FileInputStream("./data/commondata.xlsx");
		Workbook wb=WorkbookFactory.create(fis1);
		Sheet sheet=wb.getSheet("CreateCustomer");
		int rowcount=sheet.getLastRowNum();
		for(int i=1;i<=rowcount;i++) {
			int j=0;
			driver.findElement(By.xpath("//button[.=' Add ']")).click();
			driver.navigate().to("https://opensource-demo.orangehrmlive.com/web/index.php/pim/addEmployee");
			Thread.sleep(2000);
			driver.findElement(By.name("firstName")).sendKeys(sheet.getRow(i).getCell(j).getStringCellValue());
			Thread.sleep(2000);
			j++;
			driver.findElement(By.name("lastName")).sendKeys(sheet.getRow(i).getCell(j).getStringCellValue());
			Thread.sleep(2000);
			j++;
			String eid=sheet.getRow(i).getCell(j).getStringCellValue();
			driver.findElement(By.xpath("(//input[@class='oxd-input oxd-input--active'])[2]")).sendKeys(eid);
			Thread.sleep(2000);
			j++;
			driver.findElement(By.xpath("//button[.=' Save ']")).click();
			Thread.sleep(2000);
			if(driver.findElement(By.xpath("//p[.='Successfully Saved']")).isDisplayed())
				System.out.println("Employee added successfully");
		}
		driver.findElement(By.xpath("//*[@id=\"app\"]/div[1]/div[1]/header/div[1]/div[2]/ul/li")).click();
		driver.findElement(By.xpath("//a[.='Logout']")).click();
		driver.close();
	}
}
