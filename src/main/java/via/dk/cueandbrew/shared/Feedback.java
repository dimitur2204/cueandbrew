package via.dk.cueandbrew.shared;

import java.io.Serializable;

public class Feedback implements Serializable
{
  private final int feedback_id;
  private final String author_firstname;
  private final String author_lastname;
  private final String content;
  private final String type;
  private int checked_by_id;

  public Feedback(int feedback_id, String author_firstname,
      String author_lastname, String content, String type, int checked_by_id)
  {
    this.feedback_id = feedback_id;
    this.author_firstname = author_firstname;
    this.author_lastname = author_lastname;
    this.content = content;
    this.type = type;
    this.checked_by_id = checked_by_id;
  }

  public int getFeedback_id()
  {
    return feedback_id;
  }

  public String getAuthor_firstname()
  {
    return author_firstname;
  }

  public String getAuthor_lastname()
  {
    return author_lastname;
  }

  public String getContent()
  {
    return content;
  }

  public String getType()
  {
    return type;
  }

  public int getChecked_by_id()
  {
    return checked_by_id;
  }

  public void setChecked_by_id(int checked_by_id)
  {
    this.checked_by_id = checked_by_id;
  }

  @Override public String toString()
  {
    return "Feedback{" + "feedback_id=" + feedback_id + ", author_firstname='"
        + author_firstname + '\'' + ", author_lastname='" + author_lastname
        + '\'' + ", content='" + content + '\'' + ", type='" + type + '\''
        + ", checked_by_id=" + checked_by_id + '}';
  }
}
