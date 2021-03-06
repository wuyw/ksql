/**
 * Copyright 2017 Confluent Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 **/

package io.confluent.ksql.cli;


import io.confluent.ksql.util.PersistentQueryMetadata;
import io.confluent.ksql.util.QueryMetadata;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

import io.confluent.ksql.KsqlEngine;

public class StandaloneExecutor {

  private static final Logger log = LoggerFactory.getLogger(StandaloneExecutor.class);

  private final KsqlEngine ksqlEngine;

  public StandaloneExecutor(final KsqlEngine ksqlEngine) {
    this.ksqlEngine = ksqlEngine;
  }

  public void executeStatements(String queries) throws Exception {
    final List<QueryMetadata> queryMetadataList = ksqlEngine.createQueries(queries);
    for (QueryMetadata queryMetadata: queryMetadataList) {
      if (queryMetadata instanceof PersistentQueryMetadata) {
        PersistentQueryMetadata persistentQueryMetadata = (PersistentQueryMetadata) queryMetadata;
        persistentQueryMetadata.start();
      } else {
        final String message = String.format("Ignoring statements: %s" +
                "\nOnly CREATE statements can run KSQL embedded mode.",
            queryMetadata.getStatementString());
        System.err.println(message);
        log.warn(message);
      }
    }
  }
}
