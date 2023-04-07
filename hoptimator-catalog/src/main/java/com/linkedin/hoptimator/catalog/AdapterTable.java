package com.linkedin.hoptimator.catalog;

import org.apache.calcite.schema.impl.AbstractTable;
import org.apache.calcite.schema.TranslatableTable;
import org.apache.calcite.rel.RelNode;
import org.apache.calcite.rel.type.RelDataType;
import org.apache.calcite.rel.type.RelDataTypeFactory;
import org.apache.calcite.sql.SqlWriter;
import org.apache.calcite.sql.dialect.AnsiSqlDialect;
import org.apache.calcite.sql.pretty.SqlPrettyWriter;
import org.apache.calcite.plan.RelOptTable;
import org.apache.calcite.plan.RelOptCluster;

import org.apache.avro.Schema;

import java.util.Collection;
import java.util.Collections;
import java.util.Map;

/**
 * AdapterTables can have baggage, including Resources and arbitrary DDL/SQL.
 *
 * This mechanism is extremely powerful. In addition to enabling views, we can bring
 * along arbitrary infra required to materialize a view. For example, an Espresso
 * table can bring along a Brooklin CDC datatstream, or a Rest.li table can bring
 * along a Couchbase cache. Generally, such Resources won't physically exist until
 * they are needed by a pipeline, at which point Hoptimator will orchestrate their
 * deployment.
 */ 
public class AdapterTable extends AbstractTable implements ResourceProvider, ScriptImplementor, TranslatableTable {
  private String database;
  private String name;
  private RelDataType rowType;
  private final ResourceProvider resourceProvider;
  private final ScriptImplementor implementor;

  public AdapterTable(String database, String name, RelDataType rowType, ResourceProvider resourceProvider,
      ScriptImplementor implementor) {
    this.database = database;
    this.name = name;
    this.rowType = rowType;
    this.resourceProvider = resourceProvider;
    this.implementor = implementor;
  }

  /** Convenience constructor for AdapterTables that only need a connector config. */
  public AdapterTable(String database, String name, RelDataType rowType, Map<String, String> connectorConfig) {
    this(database, name, rowType, () -> Collections.emptyList(),
      new ScriptImplementor.ConnectorImplementor(database, name, rowType, connectorConfig));
  }

  /** Convenience constructor for using Avro schemas. */
  public AdapterTable(String database, String name, Schema avroSchema, Map<String, String> connectorConfig) {
    this(database, name, AvroConverter.rel(avroSchema), connectorConfig);
  } 

  public String database() {
    return database;
  }

  public String name() {
    return name;
  }

  public RelDataType rowType() {
    return rowType;
  }

  /** Not necessarily the Avro schema used to construct this object, since it is converted and reconverted. */
  public Schema avroSchema() {
    return AvroConverter.avro(rowType());
  }

  @Override
  public RelDataType getRowType(RelDataTypeFactory typeFactory) {
    return typeFactory.copyType(rowType());
  }

  @Override
  public Collection<Resource> resources() {
    return resourceProvider.resources();
  }

  @Override
  public void implement(SqlWriter writer) {
    implementor.implement(writer);
  }

  @Override
  public RelNode toRel(RelOptTable.ToRelContext context, RelOptTable relOptTable) {
    RelOptCluster cluster = context.getCluster();
    return new AdapterTableScan(cluster, cluster.traitSetOf(AdapterRel.CONVENTION), relOptTable);
  }

  /** Expresses the table as SQL/DDL in the defaul dialect. */
  @Override
  public String toString() {
    SqlWriter w = new SqlPrettyWriter(AnsiSqlDialect.DEFAULT);
    implement(w);
    return w.toSqlString().getSql();
  }
}
