package model;
import java.util.*;

public class Revision implements Comparable<Revision> {
    //private int revisionCount;
    private User revisor;
    private String revision;
    private long time;
    private Date date;

    public Revision(User user, String text, Date date) {//, int count) {
        //revisionCount = count;
        this.revisor = user;
        revision = text;
        this.time = date.getTime();
        this.date = date;
    }

    public String getText() {
        return revision;
    }

    public Date getTime() {
        return date;
    }

    public User getRevisor() {
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