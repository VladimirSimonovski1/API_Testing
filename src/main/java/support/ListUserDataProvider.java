package support;

import org.testng.annotations.DataProvider;

public class ListUserDataProvider {

    @DataProvider(name = "ListUserDataProvider")
    public static Object[][] listUsers() {
            return new Object[][] {
                    {1, "george.bluth@reqres.in", "George", "Bluth"},
                    {2, "janet.weaver@reqres.in", "Janet", "Weaver"},
                    {3, "emma.wong@reqres.in", "Emma", "Wong"}
        };
    }
}
