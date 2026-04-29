package base;

import config.ConfigReader;
import io.restassured.RestAssured;
import org.testng.annotations.BeforeSuite;

public class BaseTest {
    @BeforeSuite
    public void setup()
    {
        //RestAssured.baseURI = ConfigReader.get("base.url");

    }
}
