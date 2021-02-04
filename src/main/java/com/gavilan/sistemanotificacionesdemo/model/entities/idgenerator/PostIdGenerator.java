package com.gavilan.sistemanotificacionesdemo.model.entities.idgenerator;

import org.hibernate.HibernateException;
import org.hibernate.engine.spi.SharedSessionContractImplementor;
import org.hibernate.id.IdentifierGenerator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class PostIdGenerator implements IdentifierGenerator {

    private final Logger log = LoggerFactory.getLogger(PostIdGenerator.class);

    @Override
    public Serializable generate(SharedSessionContractImplementor sharedSessionContractImplementor, Object o)
            throws HibernateException {

        String prefix = "POST-";
        Connection connection = sharedSessionContractImplementor.connection();

        String query;
        try {
            Statement statement = connection.createStatement();

            query = "SELECT MAX(post_id) as Id FROM posts";
            ResultSet resultSet = statement.executeQuery(query);

            if (resultSet.next()) {
                String prevId = resultSet.getString(1);

                if (prevId == null) {
                    return prefix + 101;
                }

                String[] splitPrevId = prevId.split("-", 2);

                int prevIdNumber = Integer.parseInt(splitPrevId[1]);
                int nextIdNumber = prevIdNumber + 1;

                String generatedId = prefix + nextIdNumber;
                log.info("Generated ID --> ".concat(generatedId));
                return generatedId;
            }

            connection.close();
        } catch (SQLException e) {
            log.error(e.getMessage());
        }

        return null;
    }
}
