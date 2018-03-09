package com.spring.location;

import com.spring.storage.Storage;
import java.math.BigDecimal;
import java.time.Instant;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import lombok.AccessLevel;
import lombok.Data;
import lombok.Setter;

@Data
@SuppressWarnings("serial")
@Entity
@Table(name = "location")
public class Location implements java.io.Serializable {

  @Id
  @GeneratedValue
  @Column(name = "location_id", unique = true, nullable = false)
  @Setter(AccessLevel.NONE)
  private Long id;

  private String city;

  private String street;

  private String country;

  @Column(scale = 4)
  private BigDecimal latitude;

  @Column(scale = 4)
  private BigDecimal longitude;

  @OneToOne(cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
  @JoinColumn(name = "storage_id")
  private Storage storage;

  private Instant created = Instant.now();

  @Override
  public String toString() {
    return "Location{" +
        "id=" + id +
        ", city='" + city + '\'' +
        ", street='" + street + '\'' +
        ", country='" + country + '\'' +
        ", latitude=" + latitude +
        ", longitude=" + longitude +
        ", created=" + created +
        '}';
  }
}
