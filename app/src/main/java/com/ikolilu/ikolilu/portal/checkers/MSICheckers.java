package com.ikolilu.ikolilu.portal.checkers;

/**
 * Created by Genuis on 19/06/2018.
 */

public class MSICheckers {
    public static String getNetwork(String msi){
        String prefix = msi.substring(0, 3);
        String output = null;
        switch (prefix){
            case "055":
                output = "MTN";
                break;
            case "054":
                output = "MTN";
                break;
            case "024":
                output = "MTN";
                break;
            case "020":
                output = "VODAFONE";
                break;
            case "050":
                output = "VODAFONE";
                break;
            case "056":
                output = "GLO";
                break;
            case "030":
                output = "VODAFONE";
                break;
            case "027":
                output = "TIGO";
                break;
            case "057":
                output = "TIGO";
                break;
            case "026":
                output = "AIRTEL";
                break;
            case "023":
                output = "GLO";
                break;
            default:
                output = "NONE";
                break;
        }
        return output;
    }
}
