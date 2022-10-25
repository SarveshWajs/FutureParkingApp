package com.example.testing;

public class cardholder {
        private String cardid,months,years,CVV;

        public cardholder(String cardid, String months,String years, String CVV) {

            this.cardid = cardid;
            this.months = months;
            this.years = years;
            this.CVV = CVV;
        }



        public String getcardid() {
            return cardid;
        }

        public void setcardid(String cardid) {
            this.cardid = cardid;
        }

        public String getmonths() {
            return months;
        }

        public void setmonths(String months) {
            this.months = months;
        }

        public String getyears() {
            return years;
        }

        public void setyears(String years) {
            this.years = years;
        }

    public String getCVV() {
        return CVV;
    }

    public void setCVV(String CVV) {
        this.CVV = CVV;
    }
    }

