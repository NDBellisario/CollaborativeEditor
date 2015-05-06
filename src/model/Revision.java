package model;
import java.io.Serializable;
import java.util.*;

public class Revision implements Comparable<Revision>, Serializable {
    //private int revisionCount;
    private User revisor;
    private String revisionInfo;
    private long time;
    private Date ourDate;
    private Long exactT;

    public Revision(User userArg, Date dateArg, Long tArg) {//, int count) {
        //revisionCount = count;
        this.revisor = userArg;
        this.ourDate = dateArg;
        this.time = ourDate.getTime();
        revisionInfo = "Created By: " + userArg.getUserName() + " at " + ourDate + " " + time;
        exactT = tArg;
    }

    public String getText() {
        return revisionInfo;
    }
    public Long getET(){
    	return exactT;
    }

    public Long getTime() {
        return time;
    }
    public Date getDate(){
    	return ourDate;
    }

    public User getRevisorUser() {
        return revisor;
    }

    @Override
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