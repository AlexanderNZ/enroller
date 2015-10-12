package enroller;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.util.Properties;

/**
 * Created by alexcorkin on 13/10/2015.
 *
 * */

public class Env {

    public static final String FORMS_CONSTANT = "https://docs.google.com/forms/d/";
    private static final Logger LOGGER = LogManager.getLogger(Env.class);

    private final Properties properties = new Properties();

    Env() {
        try {
            properties.load(Env.class.getResourceAsStream("/env.properties"));
        } catch (IOException e) {
            LOGGER.error("Error loading properties file", e);
        }
    }

    public String getFormId() {
        return properties.getProperty("form.ID");
    }

    public String getFormResponse() {
        return properties.getProperty("form.Response");
    }

    public String getStrJoiner() {
        return properties.getProperty("str.Joiner");
    }

    public String getStrJoiner2() {
        return properties.getProperty("str.Joiner2");
    }

    public String getFormColumn1() {
        return properties.getProperty("form.Column1");
    }

    public String getFormColumn2() {
        return properties.getProperty("form.Column2");
    }

    public String getFormColumn3() {
        return properties.getProperty("form.Column3");
    }

    public String getFormColumn4() {
        return properties.getProperty("form.Column4");
    }

    public String getFormColumn5() {
        return properties.getProperty("form.Column5");
    }

    public String getFormSubmit() {
        return properties.getProperty("form.Submit");
    }
}
