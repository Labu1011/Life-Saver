package com.example.lifesaver;

import android.util.Log;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Objects;

public class DonorUtils {
    private static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("dd/MM/yyyy");
    private static boolean isEligible = false;

    public static boolean isDonorEligible(String lastDonationDateStr) {
        Date currentDate = new Date();
        Date lastDonationDate = null;

        if(lastDonationDateStr.equals("never")) {
            return true;
        }

        try {
            lastDonationDate = DATE_FORMAT.parse(lastDonationDateStr);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        if(lastDonationDate != null) {
            long currentTimeMillis = currentDate.getTime();
            long lastDonationTimeMillis = lastDonationDate.getTime();

            // Calculate the difference in milliseconds
            long timeDifferenceMillis = currentTimeMillis - lastDonationTimeMillis;

            // Convert the difference to days
            long daysDifference = timeDifferenceMillis / (1000 * 60 * 60 * 24);

            // Define eligibility threshold in days (for example, 120 days = 4 months)
            int eligibilityThresholdDays = 120;

            // Determine if the donor is eligible
            isEligible = (daysDifference >= eligibilityThresholdDays);
        } else {
            isEligible = false;
        }

        return isEligible;
    }
}
