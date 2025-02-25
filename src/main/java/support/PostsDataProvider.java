package support;

import org.testng.annotations.DataProvider;

public class PostsDataProvider {

    @DataProvider(name="DataProviderPosts")
    public static Object[][] createPostProvider() {
        return new Object[][] {
                {"Roman Reigns", "superman punch", 104},
                {"Seth Rollins", "curb stomp", 5245},
                {"Dean Ambrose", "dirty deeds", 43},
        };
    }
}
