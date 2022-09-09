package stepDefinitions.CommonUtils;

import org.apache.commons.lang3.RandomStringUtils;

public class CommonUtils {

    public String getRandomFourLetterString() {
        return RandomStringUtils.randomAlphabetic(4).toLowerCase();
    }
}
