/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.humber.MarioLevel;

/**
 *
 * @author JPAmore
 * @mod SNugawila
 */
        import java.sql.PreparedStatement;
        import java.sql.Connection;
        import java.sql.DriverManager;
        import java.sql.ResultSet;
        import java.sql.SQLException;

public class DatabaseConnection
{
    public static void main(String[] args)
    {
        String url = "jdbc:mysql://localhost:3306/marioworld?allowMultiQueries=true";
        String user = "root";
        String password = "";

        Connection con = null;
        PreparedStatement pst = null;
        ResultSet rs = null;

        String query = "SELECT * FROM characters;" +
                "SELECT FirstName FROM characters;";

        try
        {
            con = DriverManager.getConnection(url, user, password);
            pst = con.prepareStatement(query);
            rs = pst.executeQuery();

            boolean isResult = pst.execute();

            do {
                rs = pst.getResultSet();

                while (rs.next())
                {
                    for(int i = 1; i <= rs.getMetaData().getColumnCount(); i++)
                    {
                        System.out.print(rs.getString(i) + "\t\t");
                    }

                    System.out.println();
                }

                isResult = pst.getMoreResults();
            } while (isResult);

        }
        catch (SQLException ex)
        {
            System.out.println("SQL Error " + ex.getMessage());
        }
        finally
        {
            try
            {
                if (rs != null)
                {
                    rs.close();
                }
                if (pst != null)
                {
                    pst.close();
                }
                if (con != null)
                {
                    con.close();
                }
            }
            catch (SQLException ex)
            {
                System.out.println("SQL Error " + ex.getMessage());
            }
        }
    }
}
