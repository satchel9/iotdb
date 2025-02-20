/*
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */

package org.apache.iotdb.commons.auth.entity;

import org.apache.iotdb.commons.utils.TestOnly;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public enum PriPrivilegeType {
  CREATE_DATABASE(true, false, PrivilegeType.MANAGE_DATABASE),
  INSERT_TIMESERIES(true, true, PrivilegeType.WRITE_DATA),
  UPDATE_TIMESERIES(true, true, PrivilegeType.WRITE_DATA),
  READ_TIMESERIES(true, true, PrivilegeType.READ_DATA),
  CREATE_TIMESERIES(true, true, PrivilegeType.WRITE_SCHEMA),
  DELETE_TIMESERIES(true, true, PrivilegeType.WRITE_SCHEMA),
  CREATE_USER(false, PrivilegeType.MANAGE_USER),
  DELETE_USER(false, PrivilegeType.MANAGE_USER),
  MODIFY_PASSWORD(false),
  LIST_USER(false),
  GRANT_USER_PRIVILEGE(false),
  REVOKE_USER_PRIVILEGE(false),
  GRANT_USER_ROLE(false, PrivilegeType.MANAGE_ROLE),
  REVOKE_USER_ROLE(false, PrivilegeType.MANAGE_ROLE),
  CREATE_ROLE(false, PrivilegeType.MANAGE_ROLE),
  DELETE_ROLE(false, PrivilegeType.MANAGE_ROLE),
  LIST_ROLE(false),
  GRANT_ROLE_PRIVILEGE(false),
  REVOKE_ROLE_PRIVILEGE(false),
  CREATE_FUNCTION(false, PrivilegeType.USE_UDF),
  DROP_FUNCTION(false, PrivilegeType.USE_UDF),
  CREATE_TRIGGER(true, false, PrivilegeType.USE_TRIGGER),
  DROP_TRIGGER(true, false, PrivilegeType.USE_TRIGGER),
  START_TRIGGER(true, false, PrivilegeType.USE_TRIGGER),
  STOP_TRIGGER(true, false, PrivilegeType.USE_TRIGGER),
  CREATE_CONTINUOUS_QUERY(false, PrivilegeType.USE_CQ),
  DROP_CONTINUOUS_QUERY(false, PrivilegeType.USE_CQ),
  ALL(
      true,
      PrivilegeType.USE_PIPE,
      PrivilegeType.USE_UDF,
      PrivilegeType.USE_CQ,
      PrivilegeType.USE_TRIGGER,
      PrivilegeType.MANAGE_USER,
      PrivilegeType.MANAGE_ROLE,
      PrivilegeType.MANAGE_DATABASE,
      PrivilegeType.EXTEND_TEMPLATE,
      PrivilegeType.WRITE_SCHEMA,
      PrivilegeType.WRITE_DATA,
      PrivilegeType.READ_DATA,
      PrivilegeType.READ_SCHEMA,
      PrivilegeType.MAINTAIN),
  DELETE_DATABASE(true, false, PrivilegeType.MANAGE_DATABASE),
  ALTER_TIMESERIES(true, true, PrivilegeType.WRITE_SCHEMA),
  UPDATE_TEMPLATE(false),
  READ_TEMPLATE(false),
  APPLY_TEMPLATE(true, PrivilegeType.WRITE_SCHEMA),
  READ_TEMPLATE_APPLICATION(false),
  SHOW_CONTINUOUS_QUERIES(false),
  CREATE_PIPEPLUGIN(false, PrivilegeType.USE_PIPE),
  DROP_PIPEPLUGIN(false, PrivilegeType.USE_PIPE),
  SHOW_PIPEPLUGINS(false),
  CREATE_PIPE(false, PrivilegeType.USE_PIPE),
  START_PIPE(false, PrivilegeType.USE_PIPE),
  STOP_PIPE(false, PrivilegeType.USE_PIPE),
  DROP_PIPE(false, PrivilegeType.USE_PIPE),
  SHOW_PIPES(false),
  CREATE_VIEW(true, PrivilegeType.WRITE_SCHEMA),
  ALTER_VIEW(true, PrivilegeType.WRITE_SCHEMA),
  RENAME_VIEW(true, PrivilegeType.WRITE_SCHEMA),
  DELETE_VIEW(true, PrivilegeType.WRITE_SCHEMA),
  ;

  boolean accept = false;
  private final boolean isPathRelevant;
  private final boolean preIsPathRelevant;
  private final List<PrivilegeType> refPri = new ArrayList<>();

  PriPrivilegeType(boolean accept) {
    this.accept = accept;
    this.isPathRelevant = false;
    this.preIsPathRelevant = false;
  }

  PriPrivilegeType(boolean isPathRelevant, PrivilegeType... privilegeTypes) {
    this.accept = true;
    this.isPathRelevant = isPathRelevant;
    this.preIsPathRelevant = false;
    this.refPri.addAll(Arrays.asList(privilegeTypes));
  }

  PriPrivilegeType(
      boolean preIsPathRelevant, boolean isPathRelevant, PrivilegeType... privilegeTypes) {
    this.accept = true;
    this.preIsPathRelevant = preIsPathRelevant;
    this.isPathRelevant = isPathRelevant;
    this.refPri.addAll(Arrays.asList(privilegeTypes));
  }

  public boolean isAccept() {
    return this.accept;
  }

  public boolean isPathRelevant() {
    return this.isPathRelevant;
  }

  @TestOnly
  public boolean isPreIsPathRelevant() {
    return this.preIsPathRelevant;
  }

  public Set<PrivilegeType> getSubPri() {
    Set<PrivilegeType> result = new HashSet<>();
    for (PrivilegeType peivType : refPri) {
      result.add(peivType);
    }
    return result;
  }

  public Set<Integer> getSubPriOrd() {
    Set<Integer> result = new HashSet<>();
    for (PrivilegeType peivType : refPri) {
      result.add(peivType.ordinal());
    }
    return result;
  }

  public Set<Integer> getSubSysPriOrd() {
    Set<Integer> result = new HashSet<>();
    for (PrivilegeType peivType : refPri) {
      if (!peivType.isPathRelevant()) {
        result.add(peivType.ordinal());
      }
    }
    return result;
  }
}
