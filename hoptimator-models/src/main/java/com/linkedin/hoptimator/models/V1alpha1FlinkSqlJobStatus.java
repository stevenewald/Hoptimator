/*
 * Kubernetes
 * No description provided (generated by Openapi Generator https://github.com/openapitools/openapi-generator)
 *
 * The version of the OpenAPI document: v1.21.1
 * 
 *
 * NOTE: This class is auto generated by OpenAPI Generator (https://openapi-generator.tech).
 * https://openapi-generator.tech
 * Do not edit the class manually.
 */


package com.linkedin.hoptimator.models;

import java.util.Objects;
import java.util.Arrays;
import com.google.gson.TypeAdapter;
import com.google.gson.annotations.JsonAdapter;
import com.google.gson.annotations.SerializedName;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.io.IOException;

/**
 * Current state of the Flink SQL job.
 */
@ApiModel(description = "Current state of the Flink SQL job.")
@javax.annotation.Generated(value = "org.openapitools.codegen.languages.JavaClientCodegen", date = "2023-04-17T18:24:49.721Z[Etc/UTC]")
public class V1alpha1FlinkSqlJobStatus {
  public static final String SERIALIZED_NAME_MESSAGE = "message";
  @SerializedName(SERIALIZED_NAME_MESSAGE)
  private String message;

  public static final String SERIALIZED_NAME_READY = "ready";
  @SerializedName(SERIALIZED_NAME_READY)
  private Boolean ready;


  public V1alpha1FlinkSqlJobStatus message(String message) {
    
    this.message = message;
    return this;
  }

   /**
   * Error or success message, for information only.
   * @return message
  **/
  @javax.annotation.Nullable
  @ApiModelProperty(value = "Error or success message, for information only.")

  public String getMessage() {
    return message;
  }


  public void setMessage(String message) {
    this.message = message;
  }


  public V1alpha1FlinkSqlJobStatus ready(Boolean ready) {
    
    this.ready = ready;
    return this;
  }

   /**
   * Whether the requested job has been created.
   * @return ready
  **/
  @javax.annotation.Nullable
  @ApiModelProperty(value = "Whether the requested job has been created.")

  public Boolean getReady() {
    return ready;
  }


  public void setReady(Boolean ready) {
    this.ready = ready;
  }


  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    V1alpha1FlinkSqlJobStatus v1alpha1FlinkSqlJobStatus = (V1alpha1FlinkSqlJobStatus) o;
    return Objects.equals(this.message, v1alpha1FlinkSqlJobStatus.message) &&
        Objects.equals(this.ready, v1alpha1FlinkSqlJobStatus.ready);
  }

  @Override
  public int hashCode() {
    return Objects.hash(message, ready);
  }


  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class V1alpha1FlinkSqlJobStatus {\n");
    sb.append("    message: ").append(toIndentedString(message)).append("\n");
    sb.append("    ready: ").append(toIndentedString(ready)).append("\n");
    sb.append("}");
    return sb.toString();
  }

  /**
   * Convert the given object to string with each line indented by 4 spaces
   * (except the first line).
   */
  private String toIndentedString(Object o) {
    if (o == null) {
      return "null";
    }
    return o.toString().replace("\n", "\n    ");
  }

}

