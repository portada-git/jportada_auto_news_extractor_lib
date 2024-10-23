package org.elsquatrecaps.autonewsextractor.model;

import java.util.Date;

/**
 *
 * @author josepcanellas
 */
public class BaseFactCounter extends BaseFactData{
    private int amount;
    private String strAmount;
    
    public BaseFactCounter() {
    }

    public BaseFactCounter(Date date) {
        this.publicationDate = date;
    }

    
    public BaseFactCounter(String newsText, Date date) {
        this.newsText = newsText;
        this.publicationDate = date;
    }

    public BaseFactCounter(String newsText, Date publicationDate, int amount) {
        this.newsText = newsText;
        this.publicationDate = publicationDate;
        this.setAmount(amount);
    }
    
    /**
     * @return the amount
     */
    public String getAmount() {
        return strAmount;
    }

    /**
     * @return the amount
     */
    public int getAmountAsInt() {
        return amount;
    }

    /**
     * @param amount the amount to set
     */
    public void setAmount(String amount) {
        this.strAmount = amount;
        try {
            this.amount = Integer.parseInt(amount);
        } catch (NumberFormatException e) {
            this.amount = 0;
        }
    }

    public final void setAmount(int amount) {
        this.amount = amount;
        this.strAmount = String.valueOf(amount);
    }

    /**
     * @return the newsText
     */
    public String getNewsText() {
        return newsText;
    }

    /**
     * @param newsText the newsText to set
     */
    public void setNewsText(String newsText) {
        this.newsText = newsText;
    }

    /**
     * @return the publicationDate
     */
    public Date getPublicationDate() {
        return publicationDate;
    }

    /**
     * @param publicationDate the publicationDate to set
     */
    public void setPublicationDate(Date publicationDate) {
        this.publicationDate = publicationDate;
    }
    
}
