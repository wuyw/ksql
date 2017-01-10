package io.confluent.ksql.serde.avro;

import io.confluent.ksql.metastore.StructuredDataSource;
import io.confluent.ksql.serde.KQLTopicSerDe;

public class KQLAvroTopicSerDe extends KQLTopicSerDe {

  private final String schemaString;
  private final String schemaFilePath;

  public KQLAvroTopicSerDe(String schemaFilePath, String schemaString) {
    super(StructuredDataSource.DataSourceSerDe.AVRO);
    this.schemaString = schemaString;
    this.schemaFilePath = schemaFilePath;
  }

  public String getSchemaString() {
    return schemaString;
  }

  public String getSchemaFilePath() {
    return schemaFilePath;
  }
}