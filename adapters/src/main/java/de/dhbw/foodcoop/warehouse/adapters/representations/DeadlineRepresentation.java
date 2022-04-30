package de.dhbw.foodcoop.warehouse.adapters.representations;

import java.sql.Time;

public class DeadlineRepresentation {
    private String id;
    private String weekday;
    private Time time;

    public DeadlineRepresentation(String id, String weekday, Time time){
        this.id = id;
        this.weekday = weekday;
        this.time = time;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getWeekday() {
        return weekday;
    }

    public Time getTime() {
        return time;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((id == null) ? 0 : id.hashCode());
        result = prime * result + ((time == null) ? 0 : time.hashCode());
        result = prime * result + ((weekday == null) ? 0 : weekday.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        DeadlineRepresentation other = (DeadlineRepresentation) obj;
        if (id == null) {
            if (other.id != null)
                return false;
        } else if (!id.equals(other.id))
            return false;
        if (time == null) {
            if (other.time != null)
                return false;
        } else if (!time.equals(other.time))
            return false;
        if (weekday == null) {
            if (other.weekday != null)
                return false;
        } else if (!weekday.equals(other.weekday))
            return false;
        return true;
    }

    @Override
    public String toString() {
        return "DeadlineRepresentation [id=" + id + ", time=" + time + ", weekday=" + weekday + "]";
    }
    
}
