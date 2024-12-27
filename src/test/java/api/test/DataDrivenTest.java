package api.test;

import api.endpoints.UserEndPoints;
import api.payload.User;
import api.utilities.DataProviders;
import io.restassured.response.Response;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class DataDrivenTest {
    public Logger logger;
    @BeforeClass
    public void setup()
    {
        logger = LogManager.getLogger(this.getClass());
    }

    @Test(priority=1,dataProvider = "Data",dataProviderClass = DataProviders.class )
    public void testPostUser(String userID, String userName,String fname,String lname,String useremail,String pwd,String ph)
    {
        logger.info("*************** CreatingUser *********");
        User user = new User();
        user.setId(Integer.parseInt(userID));
        user.setUsername(userName);
        user.setFirstName(fname);
        user.setLastName(lname);
        user.setEmail(useremail);
        user.setPassword(pwd);
        user.setPhone(ph);

        Response response = (Response) UserEndPoints.createUser(user);
        //response.then().log().all();
        Assert.assertEquals(response.getStatusCode(),200);
        logger.info("*************** validated user created *********");
    }

    @Test(priority=2, dataProvider="UserNames", dataProviderClass=DataProviders.class)
    public void testDeleteUserByName(String userName)
    {
        logger.info("*************** Deleting user *********");
        Response response=UserEndPoints.deleteUser(userName);
        Assert.assertEquals(response.getStatusCode(),200);
        logger.info("*************** Deleted user *********");

    }
}
