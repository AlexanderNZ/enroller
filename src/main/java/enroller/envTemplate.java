package enroller;

/**
 * Created by alexcorkin on 13/10/2015.
 *
 * This is a sanitised version of the environment variables I use to connect to my google form.
 * To get the data you need, you'll need to open up your production form and inspect it with your browser's debug tools.
 *
 * First submit a test response using the form while inspecting the network status in your debug tools.
 *
 * Check the Form Response, the header status code should be 200
 * There will be a header called 'Form Data'. Here you'll find the entry IDs that you need to substitute in order to
 * send responses to your form.
 *
 * You also need your FORM_ID - grab this from the Request Headers section under 'referer'.
 *
 * For example:
 *
 * Referer:https://docs.google.com/forms/d/1XaOzUmYUgrGhekc7D8pTYl8mEs8nW5Tta6YugesbaA/viewform?fbzx=9876987698768976897
 * The FORM_ID is the string after the /d/ - in this case 1XaOzUmYUgrGhekc7D8pTYl8mEs8nW5Tta6YugesbaA
 *
 * To get access these fields from Enroller.java, copy this file and rename that class to env.java
 */

public class envTemplate {
    public static final String FORMS_CONSTANT = "https://docs.google.com/forms/d/";
    public static final String FORM_ID = ""; //Replace with your form ID
    public static final String FORM_RESPONSE = "/formResponse?";
    public static final String FORM_COLUMN_1 = "entry.******"; //Replace the form column stars with the entry IDs from your google form
    public static final String STRJOINER = "=";
    public static final String STRJOINER_2 = "&";
    public static final String FORM_COLUMN_2 = "entry.******";
    public static final String FORM_COLUMN_3 = "entry.******";
    public static final String FORM_COLUMN_4 = "entry.******";
    public static final String FORM_COLUMN_5 = "entry.******";
    public static final String FORM_SUBMIT = "submit=Submit";
}
