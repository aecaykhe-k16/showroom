package entities;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.List;

import org.hibernate.annotations.LazyToOne;
import org.hibernate.annotations.LazyToOneOption;
import org.hibernate.annotations.Proxy;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.PrimaryKeyJoinColumn;
import jakarta.persistence.Table;

@Entity
@Table(name = "nhanVien")
@Proxy(lazy = false)
public class NhanVien implements Serializable {

  @Id
  @Column(nullable = false)
  private String maNV;

  @Column(nullable = false, columnDefinition = "nvarchar(50)")
  private String tenNV;

  @Column(nullable = false, columnDefinition = "nvarchar(10)")
  private String sdt;

  @Column(nullable = false)
  private LocalDate ngaySinh;

  @Column(nullable = false)
  private boolean gioiTinh;

  @Column(nullable = false)
  private LocalDate ngayTuyenDung;

  @Column(nullable = false, columnDefinition = "nvarchar(50)")
  private String viTriCongViec;

  @Column(nullable = false)
  private String email;

  @Column(nullable = false, columnDefinition = "nvarchar(50)")
  private String diaChi;
  @Column(columnDefinition = "nvarchar(20)")
  private String trangThai;

  @OneToOne(mappedBy = "nhanVien", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
  @LazyToOne(LazyToOneOption.NO_PROXY)
  @PrimaryKeyJoinColumn
  private TaiKhoan taiKhoan;

  @OneToMany(mappedBy = "nhanVien")
  private List<HoaDonBaoHanh> dsHDBH;
  @OneToMany(mappedBy = "nhanVien")
  private List<HopDong> dsHD;

  @ManyToOne
  @JoinColumn(name = "quanLy")
  private NhanVien quanLy;

  public NhanVien() {
    super();
  }

  public NhanVien(String maNV, String tenNV, String sdt, LocalDate ngaySinh, boolean gioiTinh, LocalDate ngayTuyenDung,
      String viTriCongViec, String diaChi, String email, TaiKhoan taiKhoan) {
    super();
    this.maNV = maNV;
    this.tenNV = tenNV;
    this.sdt = sdt;
    this.ngaySinh = ngaySinh;
    this.gioiTinh = gioiTinh;
    this.ngayTuyenDung = ngayTuyenDung;
    this.viTriCongViec = viTriCongViec;
    this.diaChi = diaChi;
    this.email = email;
    this.taiKhoan = taiKhoan;

  }

  public NhanVien(String maNV, String tenNV, String sdt, LocalDate ngaySinh, boolean gioiTinh, LocalDate ngayTuyenDung,
      String viTriCongViec, String email, String diaChi, String trangThai) {
    this.maNV = maNV;
    this.tenNV = tenNV;
    this.sdt = sdt;
    this.ngaySinh = ngaySinh;
    this.gioiTinh = gioiTinh;
    this.ngayTuyenDung = ngayTuyenDung;
    this.viTriCongViec = viTriCongViec;
    this.email = email;
    this.diaChi = diaChi;
    this.trangThai = trangThai;
  }

  public String getMaNV() {
    return maNV;
  }

  public void setMaNV(String maNV) {
    this.maNV = maNV;
  }

  public String getTenNV() {
    return tenNV;
  }

  public void setTenNV(String tenNV) {
    this.tenNV = tenNV;
  }

  public String getSdt() {
    return sdt;
  }

  public void setSdt(String sdt) {
    this.sdt = sdt;
  }

  public LocalDate getNgaySinh() {
    return ngaySinh;
  }

  public void setNgaySinh(LocalDate ngaySinh) {
    this.ngaySinh = ngaySinh;
  }

  public boolean getGioiTinh() {
    return gioiTinh;
  }

  public void setGioiTinh(boolean gioiTinh) {
    this.gioiTinh = gioiTinh;
  }

  public LocalDate getNgayTuyenDung() {
    return ngayTuyenDung;
  }

  public void setNgayTuyenDung(LocalDate ngayTuyenDung) {
    this.ngayTuyenDung = ngayTuyenDung;
  }

  public String getViTriCongViec() {
    return viTriCongViec;
  }

  public void setViTriCongViec(String viTriCongViec) {
    this.viTriCongViec = viTriCongViec;
  }

  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  public TaiKhoan login() {
    return taiKhoan;
  }

  public void setTaiKhoan(TaiKhoan taiKhoan) {
    this.taiKhoan = taiKhoan;
  }

  public String getDiaChi() {
    return diaChi;
  }

  public void setDiaChi(String diaChi) {
    this.diaChi = diaChi;
  }

  public List<HoaDonBaoHanh> getDsHDBH() {
    return dsHDBH;
  }

  public void setDsHDBH(List<HoaDonBaoHanh> dsHDBH) {
    this.dsHDBH = dsHDBH;
  }

  public List<HopDong> getDsHD() {
    return dsHD;
  }

  public void setDsHD(List<HopDong> dsHD) {
    this.dsHD = dsHD;
  }

  @Override
  public String toString() {
    return "NhanVien [maNV=" + maNV + ", tenNV=" + tenNV + ", sdt=" + sdt + ", ngaySinh=" + ngaySinh + ", gioiTinh="
        + gioiTinh + ", ngayTuyenDung=" + ngayTuyenDung + ", viTriCongViec=" + viTriCongViec + ", email=" + email
        + ", diaChi=" + diaChi + "]";
  }

  public String getTrangThai() {
    return trangThai;
  }

  public void setTrangThai(String trangThai) {
    this.trangThai = trangThai;
  }

  public TaiKhoan getTaiKhoan() {
    return taiKhoan;
  }

  public NhanVien getQuanLy() {
    return quanLy;
  }

  public void setQuanLy(NhanVien quanLy) {
    this.quanLy = quanLy;
  }

}
