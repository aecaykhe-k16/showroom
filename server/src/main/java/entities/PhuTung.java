package entities;

import java.io.Serializable;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "phuTung")
public class PhuTung implements Serializable {

  @Id
  @Column(nullable = false)
  private String maPhuTung;

  @Column(nullable = false, columnDefinition = "nvarchar(50)")
  private String tenPhuTung;

  @Column(nullable = false)
  private double giaPhuTung;

  @Column(nullable = false)
  private int soLuong;

  public PhuTung() {
  }

  public PhuTung(String maPhuTung, String tenPhuTung, double giaPhuTung, int soLuong) {
    this.maPhuTung = maPhuTung;
    this.tenPhuTung = tenPhuTung;
    this.giaPhuTung = giaPhuTung;
    this.soLuong = soLuong;
  }

  public String getMaPhuTung() {
    return maPhuTung;
  }

  public void setMaPhuTung(String maPhuTung) {
    this.maPhuTung = maPhuTung;
  }

  public String getTenPhuTung() {
    return tenPhuTung;
  }

  public void setTenPhuTung(String tenPhuTung) {
    this.tenPhuTung = tenPhuTung;
  }

  public double getGiaPhuTung() {
    return giaPhuTung;
  }

  public void setGiaPhuTung(double giaPhuTung) {
    this.giaPhuTung = giaPhuTung;
  }

  public int getSoLuong() {
    return soLuong;
  }

  public void setSoLuong(int soLuong) {
    this.soLuong = soLuong;
  }

  @Override
  public String toString() {
    return "PhuTung [maPhuTung=" + maPhuTung + ", tenPhuTung=" + tenPhuTung + ", giaPhuTung=" + giaPhuTung
        + ", soLuong=" + soLuong + "]";
  }

}
