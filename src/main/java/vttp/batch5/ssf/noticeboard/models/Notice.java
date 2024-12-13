package vttp.batch5.ssf.noticeboard.models;

import java.util.Date;
import java.util.List;

import org.springframework.format.annotation.DateTimeFormat;

import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public class Notice {
    
    @NotNull(message="Can't leave field empty!")
    @NotEmpty(message="Can't leave field empty!")
    @Size(min=3, max=128, message="Length of title has to be between 3 and 128 characters inclusive!")
    private String title;
    @NotNull(message="Can't leave field empty!")
    @NotEmpty(message="Can't leave field empty!")
    private String poster;
    @NotNull(message="Can't leave field empty!")
    @DateTimeFormat(pattern="yyyy-MM-dd")
    @Future(message="Select a date after today :)")
    private Date postDate;
    @NotNull(message="Can't leave field empty!")
    @NotEmpty(message="Can't leave field empty!")
    private List<String> categories;
    @NotNull(message="Can't leave field empty!")
    @NotEmpty(message="Can't leave field empty!")
    private String text;
   
    public Notice(String title, String poster, Date postDate, List<String> categories, String text) {
        this.title = title;
        this.poster = poster;
        this.postDate = postDate;
        this.categories = categories;
        this.text = text;
    }

    public Notice(){
    }

    public String getTitle() {return title;}
    public void setTitle(String title) {this.title = title;}
    public String getPoster() {return poster;}
    public void setPoster(String poster) {this.poster = poster;}
    public Date getPostDate() {return postDate;}
    public void setPostDate(Date postDate) {this.postDate = postDate;}
    public List<String> getCategories() {return categories;}
    public void setCategories(List<String> categories) {this.categories = categories;}
    public String getText() {return text;}
    public void setText(String text) {this.text = text;}

    @Override
    public String toString() {
        return "Notice [title=" + title + ", poster=" + poster + ", postDate=" + postDate + ", categories=" + categories
                + ", text=" + text + "]";
    }
}
