package entities;

public enum ThuongHieuEnum {
  LAMBORGHINI("Lamborghini"),
  FERRARI("Ferrari"),
  PORSCHE("Porsche"),
  AUDI("Audi"),
  BMW("BMW"),
  MERCEDES("Mercedes"),
  TOYOTA("Toyota"),
  HONDA("Honda"),
  KIA("Kia"),
  HYUNDAI("Hyundai"),
  MAZDA("Mazda"),
  SUZUKI("Suzuki"),
  NISSAN("Nissan");

  private String thuongHieu;

  private ThuongHieuEnum(String thuongHieu) {
    this.thuongHieu = thuongHieu;
  }

  public String getThuongHieu() {
    return thuongHieu;
  }
}
