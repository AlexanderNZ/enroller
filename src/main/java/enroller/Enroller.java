package enroller;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.swing.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;

public class Enroller extends JPanel {

    private static final Logger LOGGER = LogManager.getLogger(Enroller.class);
    public static final int COLUMN_LENGTH = 30;
    public static final String ENCODE_TYPE = "UTF-8";
    public static final int LINE_GAP = 10;
    public static final int FRAME_WIDTH = 600;
    public static final int FRAME_HEIGHT = 300;


    //Fields Required

    //The below four fields will be sent straight to a google spreadsheet on submit button click
    JLabel devNameLabel = new JLabel("Full Name:");
    JTextField devName = new JTextField("", COLUMN_LENGTH);
    String devNameString = "";

    JLabel devEmailLabel = new JLabel("Email Address:");
    JTextField devEmail = new JTextField("", COLUMN_LENGTH);
    String devEmailString = "";

    JLabel devPhoneNumberLabel = new JLabel("Phone Number");
    JTextField devPhoneNumber = new JTextField("", COLUMN_LENGTH);
    String devPhoneNumberString = "";

    JLabel devPhoneTypeLabel = new JLabel("Phone OS:");
    JTextField devPhoneType = new JTextField("", COLUMN_LENGTH);
    String devPhoneTypeString = "";

    //This input will be masked while typing. It will be hashed and encoded before being sent to the google form
    JLabel devPasswordLabel = new JLabel("Password");
    JPasswordField devPassword = new JPasswordField("", COLUMN_LENGTH);
    String devPasswordString = "";

    //Buttons
    JButton submitButton = new JButton("Submit to Google Form");

    JButton clearButton = new JButton("Clear my details");

    JButton exitButton = new JButton("Exit");

    private final JFrame frame;


    public Enroller(JFrame frame) {
        this.frame = frame;

        Box verticalBox = Box.createVerticalBox();
        Box nameComponent = Box.createHorizontalBox();
        Box emailComponent = Box.createHorizontalBox();
        Box phoneNumberComponent = Box.createHorizontalBox();
        Box phoneTypeComponent = Box.createHorizontalBox();
        Box passwordComponent = Box.createHorizontalBox();

        nameComponent.add(devNameLabel);
        nameComponent.add(devName);
        nameComponent.add(Box.createVerticalStrut(LINE_GAP));

        verticalBox.add(nameComponent);

        emailComponent.add(devEmailLabel);
        emailComponent.add(devEmail);
        emailComponent.add(Box.createVerticalStrut(LINE_GAP));

        verticalBox.add(emailComponent);

        phoneNumberComponent.add(devPhoneNumberLabel);
        phoneNumberComponent.add(devPhoneNumber);
        phoneNumberComponent.add(Box.createVerticalStrut(LINE_GAP));

        verticalBox.add(phoneNumberComponent);

        phoneTypeComponent.add(devPhoneTypeLabel);
        phoneTypeComponent.add(devPhoneType);
        phoneTypeComponent.add(Box.createVerticalStrut(LINE_GAP));

        verticalBox.add(phoneTypeComponent);

        passwordComponent.add(devPasswordLabel);
        passwordComponent.add(devPassword);
        passwordComponent.add(Box.createVerticalStrut(LINE_GAP));

        verticalBox.add(passwordComponent);

        verticalBox.add(submitButton);
        verticalBox.add(clearButton);
        verticalBox.add(exitButton);

        exitButton.addActionListener(e -> {
            frame.dispose();
            LOGGER.info("Everything exited properly", e);
        });

        submitButton.addActionListener(e -> {
            devNameString = devName.getText();
            devEmailString = devEmail.getText();
            devPhoneNumberString = devPhoneNumber.getText();
            devPhoneTypeString = devPhoneType.getText();
            String hexPassword = DigestUtils.sha1Hex(String.valueOf(devPassword.getPassword()));

            String urlString = createURL(devNameString, devEmailString, devPhoneNumberString, devPhoneTypeString, hexPassword);

            try {
                sendGet(urlString);
            } catch (Exception error) {
                LOGGER.error("Submit Action Lister Exception", e);
                LOGGER.error(error);
            }
        });

        clearButton.addActionListener(e -> {
            devName.setText("");
            devEmail.setText("");
            devPhoneNumber.setText("");
            devPhoneType.setText("");
            devPassword.setText("");
        });

        add(verticalBox);

    }

    // HTTP GET request
    private void sendGet(String url) throws IOException {

        URL obj = new URL(url);
        HttpURLConnection urlConnection = (HttpURLConnection) obj.openConnection();

        // optional default is GET
        urlConnection.setRequestMethod("GET");

        //add request header
        urlConnection.setRequestProperty("User-Agent", "Mozilla/5.0");

        int responseCode = urlConnection.getResponseCode();
        LOGGER.info("\nSending 'GET' request to URL : " + url);
        LOGGER.info("Response Code : " + responseCode);

        BufferedReader in = new BufferedReader(
                new InputStreamReader(urlConnection.getInputStream()));
        String inputLine;
        StringBuilder response = new StringBuilder();

        while ((inputLine = in.readLine()) != null) {
            response.append(inputLine);
        }
        in.close();

        //print result
        LOGGER.info("Sanity check: URL String is " + response.toString());

    }

    public static void createAndShowGui() {
        JFrame frame = new JFrame();
        frame.add(new Enroller(frame));
        frame.setSize(FRAME_WIDTH, FRAME_HEIGHT);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocationByPlatform(true);
        frame.setVisible(true);

    }

    public String createURL(String name, String email, String phonenum, String phonetype, String password) {

        String createURLName = null;
        String createURLemail = null;
        String createURLphonenum = null;
        String createURLphonetype = null;
        String createURLpassword = null;

        try {
            createURLName = URLEncoder.encode(name, ENCODE_TYPE);
            createURLemail = URLEncoder.encode(email, ENCODE_TYPE);
            createURLphonenum = URLEncoder.encode(phonenum, ENCODE_TYPE);
            createURLphonetype = URLEncoder.encode(phonetype, ENCODE_TYPE);
            createURLpassword = URLEncoder.encode(password, ENCODE_TYPE);

        } catch (Exception error) {
            LOGGER.error("failed to encode user details into UTF-8");
            LOGGER.error(error);
        }
        
        Env env = new Env();

        StringBuilder sb = new StringBuilder();
        sb.append(env.FORMS_CONSTANT);
        sb.append(env.getFormId());
        sb.append(env.getFormResponse());

        sb.append(env.getFormColumn1());
        sb.append(env.getStrJoiner());
        sb.append(createURLName);
        sb.append(env.getStrJoiner2());

        sb.append(env.getFormColumn2());
        sb.append(env.getStrJoiner());
        sb.append(createURLemail);
        sb.append(env.getStrJoiner2());

        sb.append(env.getFormColumn3());
        sb.append(env.getStrJoiner());
        sb.append(createURLphonenum);
        sb.append(env.getStrJoiner2());

        sb.append(env.getFormColumn4());
        sb.append(env.getStrJoiner());
        sb.append(createURLphonetype);
        sb.append(env.getStrJoiner2());

        sb.append(env.getFormColumn5());
        sb.append(env.getStrJoiner());
        sb.append(createURLpassword);
        sb.append(env.getStrJoiner2());
        sb.append(env.getFormSubmit());

        return sb.toString();
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(Enroller::createAndShowGui);
    }
}
