package entities;

import java.io.Serializable;
import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity
@Table(name = "dichVu")
public class DichVu implements Serializable {
  @Id
  @Column(name = "maDV", nullable = false, columnDefinition = "nvarchar(10)")
  private String maDichVu;

  @Column(name = "tenDV", nullable = false, columnDefinition = "nvarchar(50)")
  private String tenDichVu;

  @Column(nullable = false)
  private double donGia;

  @OneToMany(mappedBy = "dichVu")
  private List<ChiTietDichVu> dsChiTietDV;

  public DichVu() {
  }

  public DichVu(String maDichVu, String tenDichVu, double donGia) {
    this.maDichVu = maDichVu;
    this.tenDichVu = tenDichVu;
    this.donGia = donGia;
  }

  public String getMaDichVu() {
    return maDichVu;
  }

  public void setMaDichVu(String maDichVu) {
    this.maDichVu = maDichVu;
  }

  public String getTenDichVu() {
    return tenDichVu;
  }

  public void setTenDichVu(String tenDichVu) {
    this.tenDichVu = tenDichVu;
  }

  public double getDonGia() {
    return donGia;
  }

  public void setDonGia(double donGia) {
    this.donGia = donGia;
  }

  @Override
  public String toString() {
    return "DichVu [maDichVu=" + maDichVu + ", tenDichVu=" + tenDichVu + ", donGia=" + donGia + "]";
  }

}
