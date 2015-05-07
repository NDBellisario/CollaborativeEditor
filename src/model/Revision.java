package model;
import java.io.Serializable;
import java.util.*;
/**
 * @author Nicholas,Omri,Cameron,Taylor,Eric
 * Contains all details about a revision in the doc
 */
public class Revision implements Comparable<Revision>, Serializable {
    //private int revisionCount;
    private User revisor;
    private String revisionInfo;
    private long time;
    private Date ourDate;
    private Long exactT;
/**
 * Revision Constructor
 * @param userArg User who caused Revision
 * @param dateArg date of Revision
 * @param tArg Exact Time 
 */
    public Revision(User userArg, Date dateArg, Long tArg) {//, int count) {
        //revisionCount = count;
        this.revisor = userArg;
        this.ourDate = dateArg;
        this.time = ourDate.getTime();
        revisionInfo = "Created By: " + userArg.getUserName() + " at " + ourDate + " " + time;
        exactT = tArg;
    }

    /**
     * Get's revision
     * @return String of change
     */
    public String getText() {
        return revisionInfo;
    }
    /**
     * get exact time of revision
     * @return long of the millisec
     */
    public Long getET(){
    	return exactT;
    }

    /**
     * get the time
     * @return returns a millisec version of the date
     */
    public Long getTime() {
        return time;
    }
    /**
     * Get's the date
     * @return returns the Date in Date format
     */
    public Date getDate(){
    	return ourDate;
    }
/**
 * Gets the editor of the original revision
 * @return User instance of the Revisior
 */
    public User getRevisorUser() {
        return revisor;
    }

    @Override
    /**
     * Compares to Revisions to see which is new 
     * @return returns an integer depending on who is older 
     * -1 revision is older 
     * 1 revision is newer 
     * 0 revision is equal (Basically a clone)
     */
    public int compareTo(Revision o) {
        if (time < o.time) {
            return -1;
        } else if (time == o.time) {
            return 0;
        } else {
            return 1;
        }
    }

}