package de.dhbw.foodcoop.warehouse.domain.entities;

import java.sql.Time;
import java.time.LocalDateTime;
import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "deadline")
public class Deadline {
    @Id
    private String id;
    @Column
    private String weekday;
    @Column
    private Time time;
    @Column
    private LocalDateTime datum;

    public Deadline(String id, String weekday, Time time, LocalDateTime datum){
        this.id = id;
        this.weekday = weekday;
        this.time = time;
        this.datum = datum;
    }

    public Deadline(String weekday, Time time,LocalDateTime datum){
        this(UUID.randomUUID().toString(), weekday, time, datum);
    }

    public Deadline(){
        
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

    public void setWeekday(String weekday) {
        this.weekday = weekday;
    }

    public Time getTime() {
        return time;
    }

    public void setTime(Time time) {
        this.time = time;
    }

    public LocalDateTime getDatum() {
        return datum;
    }

    public void setDatum(LocalDateTime datum) {
        this.datum = datum;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((id == null) ? 0 : id.hashCode());
        result = prime * result + ((time == null) ? 0 : time.hashCode());
        result = prime * result + ((weekday == null) ? 0 : weekday.hashCode());
        result = prime * result + ((datum == null) ? 0 : datum.hashCode());
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
        Deadline other = (Deadline) obj;
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
        if (datum == null) {
            if (other.datum != null)
                return false;
        } else if (!datum.equals(other.datum))
            return false;
        return true;
    }

    @Override
    public String toString() {
        return "Deadline [id=" + id + ", time=" + time + ", weekday=" + weekday + ", datum=" + datum + "]";
    }
    
}
