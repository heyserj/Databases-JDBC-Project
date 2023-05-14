package mysqlproject;

import java.sql.*; //importing the Java library designed for accessing and processing data stored in databases

/**
 *
 * Class that connects to the MySQL Sakila database and issues a query that identifies all of the 
 * actors that have appeared in more than 40 movies. This query returns the actors' first names,
 * last names, and actor_ID's. Note that this query corresponds to Problem 1 on Homework #5. Note
 * that this class, via the main() method, prints the result set corresponding to the query in a relation-
 * like format and then closes the system resources that were allocated as part of completing the
 * aforementioned tasks.
 * 
 * @author Jake Heyser
 * 4/26/23
 */
public class MySQLProject {

    /**
     * Driver method for this class. This method establishes a connection with the Sakila database, 
     * executes the query that we wish to run, obtains the corresponding result set, prints the result
     * set to the terminal in a relation-like format and then closes the system resources that were needed
     * in order to perform the aforementioned tasks.
     * 
     * @param args the command line arguments
     */
    public static void main(String[] args){
       //initializing local variables        
        Connection connection = null; //the Connection object associated with the database
        Statement statement = null; //the Statement object 
        ResultSet rs = null; //the ResultSet object for the query we will run
        String url = "jdbc:mysql://localhost:3306/sakila"; //the database URL parameter that we will use to establish a connection
        String user = "root"; //the username credential that we will use to establish a connection
        String password = "Fairfield2022!!!"; //the password credential that we will use to establish a connection
        String query = "select A.first_name, A.last_name, A.actor_id from actor as A, film_actor as F "
                + "where A.actor_id = F.actor_id group by A.actor_id having count(*) > 40"; //the query we wish to run
        
        //opening a connection to the Sakila database
        try{
            connection = DriverManager.getConnection(url, user, password); //getting the Connection object that we will use to make a statement
            statement = connection.createStatement(); //creating a Statement object that we will use to run our query
            rs = statement.executeQuery(query); //executing the query and obtain the corresponding ResultSet object
            
            System.out.printf("%-32s%-32s%-32s\n", "first_name", "last_name", "actor_id"); //printing the attribute names that will appear in the terminal
            
            //extracting the data from the result set of the query and printing it            
            while (rs.next()){ //while there is another tuple in the result set
                String first_name = rs.getString("first_name"); //the actor's first name in the current tuple
                String last_name = rs.getString("last_name"); //the actor's last name in the current tuple
                int actor_id = rs.getInt("actor_id"); //the actor's actor_id in the current tuple
                System.out.format("%-32s%-32s%-10d\n", first_name, last_name, actor_id); //printing the current tuple's values
            }
        }
        catch(SQLException e){ //there was an error connecting to the Sakila database
            System.out.println("Error connecting to the database"); //printing an appropriate error message
        }  
        
        finally{ //closing system resources; this is done automatically, but manually closing the resources speeds up the time at which they are re-allocated 
            try{
                if (rs != null){ //if system resources were allocated for the query's ResultSet
                    rs.close(); //closing the system resources associated with the ResultSet object
                }
                if (statement != null){ //if system resources were allocated for the Statement
                    statement.close(); //closing the system resources associated with the Statement object
                }
                if (connection != null){ //if system resources were allocated for the Connection we established with the database
                    connection.close(); //closing the system resources associated with the Connection object
                }
            }
            catch(SQLException e){ //there was an error closing system resources
                System.out.println("Error closing system resources"); //printing an appropriate error message
            }
        }
    }   
}
