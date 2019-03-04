package realestate.util;

import java.io.*;

public class HtmlReader {
    public String readHtmlFile(String HtmlFilePath) throws IOException {
        BufferedReader reader = new BufferedReader(
                new InputStreamReader(
                        new FileInputStream(
                                new File(HtmlFilePath)
                        )
                )
        );

        StringBuilder htmlFileContent = new StringBuilder();
        String line;
        while ((line = reader.readLine()) != null) {
            htmlFileContent.append(line).append(System.lineSeparator());
        }

        return htmlFileContent.toString().trim();
    }
}
