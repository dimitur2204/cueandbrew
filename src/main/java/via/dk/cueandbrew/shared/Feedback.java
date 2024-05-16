package via.dk.cueandbrew.shared;

import java.io.Serializable;

public class Feedback implements Serializable
{
  private int feedback_id;
  private String author_firstname;
  private String author_lastname;
  private String content;
  private String type;
  private int checked_by_type;

  public Feedback(int feedback_id, String author_firstname,
      String author_lastname, String content, String type, int checked_by_type)
  {
    this.feedback_id = feedback_id;
    this.author_firstname = author_firstname;
    this.author_lastname = author_lastname;
    this.content = content;
    this.type = type;
    this.checked_by_type = checked_by_type;
  }

  public int getFeedback_id()
  {
    return feedback_id;
  }

  public void setFeedback_id(int feedback_id)
  {
    this.feedback_id = feedback_id;
  }

  public String getAuthor_firstname()
  {
    return author_firstname;
  }

  public void setAuthor_firstname(String author_firstname)
  {
    this.author_firstname = author_firstname;
  }

  public String getAuthor_lastname()
  {
    return author_lastname;
  }

  public void setAuthor_lastname(String author_lastname)
  {
    this.author_lastname = author_lastname;
  }

  public String getContent()
  {
    return content;
  }

  public void setContent(String content)
  {
    this.content = content;
  }

  public String getType()
  {
    return type;
  }

  public void setType(String type)
  {
    this.type = type;
  }

  public int getChecked_by_type()
  {
    return checked_by_type;
  }

  public void setChecked_by_type(int checked_by_type)
  {
    this.checked_by_type = checked_by_type;
  }
}
