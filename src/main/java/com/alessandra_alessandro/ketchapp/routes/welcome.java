package com.alessandra_alessandro.ketchapp.routes;

import com.alessandra_alessandro.ketchapp.models.UserRecord;
import com.alessandra_alessandro.ketchapp.utils.DatabaseConnection;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

@RestController
public class welcome {

    @Operation(summary = "Get all user records", description = "Fetches all user records from the database.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Successfully retrieved user records"),
        @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @GetMapping("/welcome")
    public List<UserRecord> welcome() {
        List<UserRecord> records = new ArrayList<>();

        try (Connection conn = DatabaseConnection.getConnection()) {
            String query = "SELECT * FROM test";
            Statement stmt = conn.createStatement();
            ResultSet res = stmt.executeQuery(query);

            while (res.next()) {
                int id = res.getInt("id");
                String name = res.getString("name");
                UserRecord record = new UserRecord(id, name);
                records.add(record);
            }

            res.close();
            stmt.close();
        } catch (SQLException e) {
            System.out.println("Connection error: " + e.getMessage());
        }

        return records;
    }
}
