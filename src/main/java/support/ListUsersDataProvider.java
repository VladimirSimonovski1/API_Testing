package support;

import org.testng.annotations.DataProvider;

public class ListUsersDataProvider {

    @DataProvider(name = "ListUsersDataProvider")
    public static Object[][] listUsers() {
        return new Object[][] {
                {1, 4, "eve.holt@reqres.in", "Eve", "Holt"},
                {1, 6, "tracey.ramos@reqres.in", "Tracey", "Ramos"},
                {1, 2, "janet.weaver@reqres.in", "Janet", "Weaver"},
                {2, 10, "byron.fields@reqres.in", "Byron", "Fields"},
                {2, 8, "lindsay.ferguson@reqres.in", "Lindsay", "Ferguson"},
                {2, 12, "rachel.howell@reqres.in", "Rachel", "Howell"}
        };
    }
}
