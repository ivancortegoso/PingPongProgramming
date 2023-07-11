package com.solera.bankbackend.selenium;

import com.solera.bankbackend.domain.model.User;
import com.solera.bankbackend.service.UserService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

@SpringBootTest
public class SingUp {
    @Autowired
    UserService userService;
    @Test
    void  registerFormNewValidUser() {
        System.setProperty("webdriver.chrome.driver", "C:\\Users\\Ivan.CortegosoBascon\\Downloads\\chromedriver_win32\\chromedriver.exe");
        ChromeOptions options = new ChromeOptions();
        options.addArguments("headless");
        WebDriver driver=new ChromeDriver(options);
        driver.get("http://localhost:3000/signup");
        List<WebElement> webElementsForm = driver.findElements(By.tagName("input"));
        fillForm(webElementsForm, "Carlas", "Vergas", "04/03/2000", "Guadalajara", "NIEColombianoxd", "665009221", "carlas@solera.com", "carlasvergas", "password");
        User user = userService.findByEmail("carlas@solera.com");
        Assertions.assertNotNull(user);
    }
    public static void fillForm(List<WebElement> form, String firstName, String lastName, String birthDate, String address, String documentId, String phoneNumber, String email, String username, String password) {
        form.get(0).sendKeys(firstName);
        form.get(1).sendKeys(lastName);
        form.get(2).sendKeys(birthDate);
        form.get(3).sendKeys(address);
        form.get(4).sendKeys(documentId);
        form.get(5).sendKeys(phoneNumber);
        form.get(6).sendKeys(email);
        form.get(7).sendKeys(username);
        form.get(8).sendKeys(password);
        form.get(9).click();
    }
}
