package via.dk.cueandbrew.shared;

import java.io.Serializable;

/**
 * A class that is responsible for the Feedback
 * @author Marius Marcoci
 */
public class Feedback implements Serializable
{
  private final int feedback_id;
  private final String author_firstname;
  private final String author_lastname;
  private final String content;
  private final String type;
  private int checked_by_id;

    /**
     * A constructor that initializes the Feedback with the specified values
     * @param feedback_id The id of the feedback
     * @param author_firstname The first name of the author
     * @param author_lastname The last name of the author
     * @param content The content of the feedback
     * @param type The type of the feedback
     * @param checked_by_type The type of the feedback
     */
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

  /**
   * A method that returns the feedback id
   * @return The id of the feedback
   */
  public int getFeedback_id()
  {
    return feedback_id;
  }

    /**
     * A method that returns the first name of the author
     * @return The first name of the author
     */
  public String getAuthor_firstname()
  {
    return author_firstname;
  }

    /**
     * A method that returns the last name of the author
     * @return The last name of the author
     */
  public String getAuthor_lastname()
  {
    return author_lastname;
  }

    /**
     * A method that returns the content of the feedback
     * @return The content of the feedback
     */
  public String getContent()
  {
    return content;
  }

    /**
     * A method that returns the type of the feedback
     * @return The type of the feedback
     */
  public String getType()
  {
    return type;
  }

    /**
     * A method that returns the type of the feedback
     * @return The type of the feedback
     */
  public int getChecked_by_id()
  {
    return checked_by_id;
  }
    /**
     * A method that sets the type of the feedback
     * @param checked_by_type The type of the feedback
     */

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
