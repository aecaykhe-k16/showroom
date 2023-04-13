package util;

import java.text.Normalizer;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Date;
import java.util.regex.Pattern;

import entities.LoaiXeEnum;
import entities.ThuongHieuEnum;

public class Generator {
  public String tuTaoMaNV(String loaiNV, int namSinh) {
    String nam = String.valueOf(namSinh);

    String year = nam.substring(2);

    String maNV = "";
    maNV += loaiNV;
    maNV += year;
    maNV += random3SoNguyen();

    return maNV;
  }

  public String tuTaoMaPhong(String loaiXe) {
    String maPhong = "";
    maPhong += loaiXe.startsWith("V") ? "V" : "T";
    maPhong += random3SoNguyen();
    return maPhong;
  }

  public String tuTaoMaHoaDon(String maHopDong) {
    Date date = new Date();
    SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
    String ngayLap = formatter.format(date);
    String[] day = ngayLap.split("/");
    String ngay = day[0];
    if (ngay.startsWith("0")) {
      ngay = ngay.substring(1);
    }
    String maHD = "";
    maHD += ngay;
    maHD += "HD";
    maHD += maHopDong;
    maHD += random3SoNguyen();// có thể thay đổi thành mã khách hàng
    return maHD;
  }

  public String tuTaoMaDichVu() {
    return "DV" + random3SoNguyen();
  }

  public String tuTaoMaCaTruc(LocalDate ngayLapPhanCong, int caLam) {
    // split ngayLapPhanCong
    String[] arrDate = ngayLapPhanCong.toString().split("-");
    String year = arrDate[0].substring(2);
    String month = arrDate[1];
    String day = arrDate[2];

    String thuTuCaLam = caLam == 0 ? "1" : caLam == 1 ? "2" : "3";

    String[] dayOfWeek = { "Monday", "Tuesday", "Wednesday", "Thursday",
        "Friday", "Saturday", "Sunday" };
    String thuTuNgay = "";

    for (int i = 0; i < dayOfWeek.length; i++) {
      if (ngayLapPhanCong.getDayOfWeek().name().equalsIgnoreCase(dayOfWeek[i])) {
        thuTuNgay = String.valueOf(i + 2);
      }
    }

    String maCaTruc = "";
    maCaTruc += day + month + year;
    maCaTruc += thuTuNgay;
    maCaTruc += thuTuCaLam;
    maCaTruc += random3SoNguyen();
    return maCaTruc;
  }

  public String tuTaoMaKH(String sdt, int namHienTai) {
    String nam = String.valueOf(namHienTai);

    String year = nam.substring(2);

    String maKH = "";
    maKH += sdt.substring(6, 10);
    maKH += year;
    maKH += random3SoNguyen();

    return maKH;
  }

  public String taoMaDichVu() {
    String maDV = "";
    maDV += "DV";
    maDV += random3SoNguyen();
    return maDV;
  }

  public String tuTaoMaDC() {
    return "DC" + random4SoNguyen();
  }

  public String tuTaoMaLoaiTV(String soDinhDanh) {
    Date date = new Date();
    SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
    String ngayDangKy = formatter.format(date);
    String[] arrDate = ngayDangKy.split("/");
    String month = arrDate[1];
    String year = arrDate[2].substring(2);

    String maLoaiTV = "";
    maLoaiTV += soDinhDanh.substring(8);
    maLoaiTV += month + year;
    return maLoaiTV;
  }

  // mã khách hàng lấy trực tiép số điện thoại cắt ra nên k cần hàm

  // có thể kết hợp với mã nhân viên + mã phòng + mã khách hàng
  public String tuTaoDatPhong() {
    return "DP" + random3SoNguyen();
  }

  public int random4SoNguyen() {
    return (int) (Math.random() * 9000 + 1000);
  }

  public int random3SoNguyen() {
    return (int) (Math.random() * 900 + 100);
  }

  public String parseLocaldateToDatetimepicker(String ngay) {
    String[] monthOfYear = { "January", "February", "March", "April", "May", "June", "July", "August", "September",
        "October", "November", "December" };
    String thuTuThang = "";

    String[] arrDate = ngay.split("/");
    String month = arrDate[1];
    String day = arrDate[0];
    String year = arrDate[2];
    if (day.startsWith("0")) {
      day = day.substring(1);
    }

    for (int i = 0; i < monthOfYear.length; i++) {
      if (month.equalsIgnoreCase(String.valueOf(i + 1))) {
        thuTuThang = monthOfYear[i];
      }
    }

    return thuTuThang + " " + day + ", " + year;
  }

  public String parseLocalDateToDate(String ngay) {
    String[] arrDate = ngay.split("-");
    String year = arrDate[0];
    String month = arrDate[1];
    String day = arrDate[2];
    if (day.startsWith("0")) {
      day = day.substring(1);
    }
    return day + "/" + month + "/" + year;
  }

  public String viToEn(String fullName) {
    String lastName = "";
    String[] arrName = fullName.split(" ");
    for (int i = 0; i < arrName.length;) {
      lastName += arrName[arrName.length - 1];
      break;
    }

    String nfdNormalizedString = Normalizer.normalize(lastName.toLowerCase(), Normalizer.Form.NFD);
    Pattern pattern = Pattern.compile("\\p{InCombiningDiacriticalMarks}+");
    return pattern.matcher(nfdNormalizedString).replaceAll("");
  }

  public String tachChuoi(String full) {
    String last = "";
    String[] arrName = full.split(" ");
    for (int i = 0; i < arrName.length;) {
      last += arrName[arrName.length - 1];
      break;
    }
    return last;
  }

  public String boDauCuaTu(String full) {
    String last = tachChuoi(full);
    String nfdNormalizedString = Normalizer.normalize(last.toLowerCase(),
        Normalizer.Form.NFD);
    Pattern pattern = Pattern.compile("\\p{InCombiningDiacriticalMarks}+");
    return pattern.matcher(nfdNormalizedString).replaceAll("");
  }

  public String boDau(String full) {
    String nfdNormalizedString = Normalizer.normalize(full.toLowerCase(), Normalizer.Form.NFD);
    Pattern pattern = Pattern.compile("\\p{InCombiningDiacriticalMarks}+");
    return pattern.matcher(nfdNormalizedString).replaceAll("");
  }

  public String convertGia(String gia) {
    String b = gia.replaceAll("\\,", "");
    String c = b.replaceAll("\\VND", "");
    return c;
  }

  public String taoEmail(String name, String maNV) {
    String email = "";
    email += viToEn(name);
    email += maNV.substring(2);
    email += "@gmail.com";

    return email;
  }

  public String boDauTrongTu(String tu) {
    String nfdNormalizedString = Normalizer.normalize(tu, Normalizer.Form.NFD);
    Pattern pattern = Pattern.compile("\\p{InCombiningDiacriticalMarks}+");
    return pattern.matcher(nfdNormalizedString).replaceAll("");
  }

  public String taoMaHopDong(String ngayLap, String loaiHD) {
    String maHD = "";
    maHD += "HD";
    maHD += loaiHD;
    maHD += ngayLap;
    maHD += random3SoNguyen();
    return maHD;
  }

  // convert Enum to String
  public String convertLoaiXeToString(LoaiXeEnum e) {
    String loaiXe = "";
    for (LoaiXeEnum lp : LoaiXeEnum.values()) {
      if (lp.equals(e)) {
        loaiXe = lp.getLoaiXe();
      }
    }
    return loaiXe;
  }

  public String convertTHToString(ThuongHieuEnum e) {
    String thuongHieu = "";
    for (ThuongHieuEnum tt : ThuongHieuEnum.values()) {
      if (tt.equals(e)) {
        thuongHieu = tt.getThuongHieu();
      }
    }
    return thuongHieu;
  }

  // convert String to enum
  public LoaiXeEnum convertStringToLoaiXe(String loaiXe) {
    LoaiXeEnum loaiXeEnum = null;
    for (LoaiXeEnum l : LoaiXeEnum.values()) {
      if (l.getLoaiXe().equalsIgnoreCase(loaiXe)) {
        loaiXeEnum = l;
      }
    }
    return loaiXeEnum;
  }

  public ThuongHieuEnum convertStringToThuongHieu(String thuongHieu) {
    ThuongHieuEnum th = null;
    for (ThuongHieuEnum t : ThuongHieuEnum.values()) {
      if (t.getThuongHieu().equalsIgnoreCase(thuongHieu)) {
        th = t;
      }
    }
    return th;
  }

  public String taoMaXe() {
    return "Xe" + random3SoNguyen();
  }

  public String convertGiaTien(String gia) {
    String a = gia.replaceAll("\\.0", "");
    return a;
  }

  public String tuTaoMaPT() {
    return "PT" + random3SoNguyen();
  }
}