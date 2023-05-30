
import org.testng.Assert;
import io.restassured.RestAssured;
import io.restassured.path.xml.XmlPath;
import static io.restassured.RestAssured.given;

public class SoapReference {

	public static void main(String[] args) {
		
		//Step 1: Declare the Base URI
		
		String BaseURI = "https://www.dataaccess.com";
		String soapReqBody = "<?xml version=\"1.0\" encoding=\"utf-8\"?>\r\n"
				+ "<soap12:Envelope xmlns:soap12=\"http://www.w3.org/2003/05/soap-envelope\">\r\n"
				+ "  <soap12:Body>\r\n"
				+ "    <NumberToDollars xmlns=\"http://www.dataaccess.com/webservicesserver/\">\r\n"
				+ "      <dNum>100</dNum>\r\n"
				+ "    </NumberToDollars>\r\n"
				+ "  </soap12:Body>\r\n"
				+ "</soap12:Envelope>\r\n"
				+ "";
		
		//Extract
		XmlPath xp = new XmlPath (soapReqBody);
		String reqParameter = xp.getString("dNum");
		
		RestAssured.baseURI = BaseURI;
		
		String soapResBody = given().header("Content-Type", "text/xml; charset=utf-8").body(soapReqBody)
				.when().post("/webservicesserver/NumberConversion.wso").then().extract().response().getBody().asString();
		
		XmlPath xmPath = new XmlPath (soapResBody);
		String resParameter = xmPath.getString("NumberToDollarsResult");
		
		Assert.assertEquals(resParameter, "one hundred dollars");
		System.out.println(resParameter);
		System.out.println(reqParameter);
		System.out.println(soapReqBody);
		
		

	}

}