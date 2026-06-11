package com.jarsolutions.fitness.common.infrastructure.persistence;

import java.util.Objects;

public abstract class BaseJpaEntity<ID> {
  public abstract ID getId();

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    BaseJpaEntity<?> that = (BaseJpaEntity<?>) o;
    return getId() != null && Objects.equals(getId(), that.getId());
  }

  @Override
  public int hashCode() {
    return getClass().hashCode();
  }
}
