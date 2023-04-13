package entities;

import java.io.Serializable;

import org.hibernate.annotations.Proxy;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "chiTietHoaDonBaoHanh")
@Proxy(lazy = false)
public class ChiTietHoaDonBaoHanh implements Serializable {

  @Id
  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "maHDBH")
  private HoaDonBaoHanh hoaDonBaoHanh;

  @Id
  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "maPhuTung")
  private PhuTung phuTung;

  private int soLuong;

  public ChiTietHoaDonBaoHanh() {
  }

  public ChiTietHoaDonBaoHanh(PhuTung phuTung, int soLuong) {
    this.phuTung = phuTung;
    this.soLuong = soLuong;
  }

  public HoaDonBaoHanh getHoaDonBaoHanh() {
    return hoaDonBaoHanh;
  }

  public void setHoaDonBaoHanh(HoaDonBaoHanh hoaDonBaoHanh) {
    this.hoaDonBaoHanh = hoaDonBaoHanh;
  }

  public PhuTung getPhuTung() {
    return phuTung;
  }

  public void setPhuTung(PhuTung phuTung) {
    this.phuTung = phuTung;
  }

  public int getSoLuong() {
    return soLuong;
  }

  public void setSoLuong(int soLuong) {
    this.soLuong = soLuong;
  }

  @Override
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result + ((hoaDonBaoHanh == null) ? 0 : hoaDonBaoHanh.hashCode());
    result = prime * result + ((phuTung == null) ? 0 : phuTung.hashCode());
    result = prime * result + soLuong;
    return result;
  }

  @Override
  public boolean equals(Object obj) {
    if (this == obj)
      return true;
    if (obj == null)
      return false;
    if (getClass() != obj.getClass())
      return false;
    ChiTietHoaDonBaoHanh other = (ChiTietHoaDonBaoHanh) obj;
    if (hoaDonBaoHanh == null) {
      if (other.hoaDonBaoHanh != null)
        return false;
    } else if (!hoaDonBaoHanh.equals(other.hoaDonBaoHanh))
      return false;
    if (phuTung == null) {
      if (other.phuTung != null)
        return false;
    } else if (!phuTung.equals(other.phuTung))
      return false;
    if (soLuong != other.soLuong)
      return false;
    return true;
  }

  @Override
  public String toString() {
    return "ChiTietHoaDonBaoHanh [hoaDonBaoHanh=" + hoaDonBaoHanh + ", phuTung=" + phuTung + ", soLuong=" + soLuong
        + "]";
  }

}
