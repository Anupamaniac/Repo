package com.rosettastone.succor;

import javax.sql.DataSource;

import org.dbunit.database.DatabaseConfig;
import org.dbunit.database.DatabaseDataSourceConnection;
import org.dbunit.dataset.ReplacementDataSet;
import org.dbunit.dataset.xml.FlatXmlDataSet;
import org.dbunit.dataset.xml.FlatXmlDataSetBuilder;
import org.dbunit.operation.DatabaseOperation;
import org.springframework.context.ApplicationContext;

public final class TestUtil {

    private TestUtil() { }

    public static void initDb(String datasetLocation, DataSource dataSource, ApplicationContext applicationContext)
            throws Exception {
        DatabaseDataSourceConnection connection = new DatabaseDataSourceConnection(dataSource);
        FlatXmlDataSetBuilder builder = new FlatXmlDataSetBuilder();
        FlatXmlDataSet xmlDdataset = builder.build((applicationContext.getResource(datasetLocation)
                .getInputStream()));
        ReplacementDataSet dataSet = new ReplacementDataSet(xmlDdataset);
        dataSet.addReplacementObject("[NULL]", null);
        connection.getConfig().setProperty(DatabaseConfig.PROPERTY_ESCAPE_PATTERN, "`?`");
        DatabaseOperation.CLEAN_INSERT.execute(connection, dataSet);
        connection.close();
    }

    public static void clearDb(String datasetLocation, DataSource dataSource, ApplicationContext applicationContext)
            throws Exception {
        DatabaseDataSourceConnection connection = new DatabaseDataSourceConnection(dataSource);
        FlatXmlDataSetBuilder builder = new FlatXmlDataSetBuilder();
        FlatXmlDataSet xmlDdataset = builder.build((applicationContext.getResource(datasetLocation)
                .getInputStream()));
        ReplacementDataSet dataSet = new ReplacementDataSet(xmlDdataset);
        dataSet.addReplacementObject("[NULL]", null);
        connection.getConfig().setProperty(DatabaseConfig.PROPERTY_ESCAPE_PATTERN, "`?`");
        DatabaseOperation.DELETE_ALL.execute(connection, dataSet);
        connection.close();
    }

}

