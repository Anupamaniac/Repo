package com.rosettastone.succor.bandit;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.testng.Assert;
import org.testng.annotations.Test;

import com.rosettastone.succor.exception.InvalidJsonException;

@Test
public class EventParserTest {

    private static final Log LOG = LogFactory.getLog(EventParserTest.class);

    @Test(enabled = false)
    public void testOnRealMessages() throws Exception {
        File directory = new File("target/test-classes/bandit");

        if (!directory.exists() || !directory.isDirectory()) {
            throw new Exception("directory not found or not a directory");
        }
        EventParser parser = new EventParser();
        File[] files = directory.listFiles();
        int failedCounter = 0;
        for (File file : files) {
            String fileName = file.getName();
            String json = loadJson("/bandit/" + fileName);
            try {
                parser.parse(json);
            } catch (InvalidJsonException e) {
                LOG.error("Can not parse file " + file.getName());
                failedCounter++;
            }
        }
        Assert.assertEquals(failedCounter, 0, String.format("Can not parse %d files from %d",
                failedCounter, files.length));
    }

    public void testSingleJson() throws IOException {
        String json = loadJson("/level_completion_1_INST_english_inst.json");
        new EventParser().parse(json);
    }

    private String loadJson(String resourceName) throws IOException {
        BufferedReader reader = null;

        try {
            InputStream is = this.getClass().getResourceAsStream(resourceName);
            reader = new BufferedReader(new InputStreamReader(is));
            StringBuilder buffer = new StringBuilder();
            while (reader.ready()) {
                buffer.append(reader.readLine());
            }
            return buffer.toString();
        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e) {
                    LOG.warn("Can not close input stream", e);
                }
            }
        }
    }
}
