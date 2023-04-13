package ui;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Desktop;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.HeadlessException;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.DefaultComboBoxModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.TitledBorder;
import javax.swing.filechooser.FileNameExtensionFilter;

import org.kordamp.ikonli.fontawesome5.FontAwesomeSolid;
import org.kordamp.ikonli.swing.FontIcon;

import bus.IXeBus;
import entities.NhanVien;
import entities.TaiKhoan;
import entities.Xe;
import util.Constants;
import util.Generator;
import util.MyButton;
import util.RoundJTextField;

public class QuanLyXe extends JFrame implements ActionListener, MouseListener {

  private JPanel pnlMenu, pnlTop, pnInfo, pnlBtn, pnlFilter, pnlMainUI, pnlDsXe,
      pnlPagination;
  private JButton btnMenu, btnThem, btnXoa, btnSua, btnXe, btnNext, btnPrevious, btnIconUser, btnIconLogout;
  private DefaultComboBoxModel<String> dfHangXe, dfGiaXe;
  private JComboBox<String> cmbHangXe, cmbGiaXe;
  private JLabel lblIconLogo, lblTitle, lblTenXe;
  private JLayeredPane pnlCenter;

  private ImageIcon anh;
  private int index, kiemTra = 12;

  // slide bar
  private JPanel pnlSlideBar, pnlMainSlider;
  private JButton btnNhanVien, btnHopDong, btnKhachHang, btnQuanLyXe, btnPhuTung, btnTroGiup, btnClose, btnHinhAnh,
      btnChonAnh;
  private JLabel lblQuanLyXe,
      lblMenu, lblLine;
  private JLayeredPane layeredPane;
  private boolean flag = false;
  private String duongDan = "";

  // Đặt xe
  private DefaultComboBoxModel<String> dfThuongHieu, dfLoaiXe;
  private JComboBox<String> cmbThuongHieu, cmbLoaiXe;
  private JPanel pnlDatXe;
  private JLabel lblTenXeDat, lblThuongHieu, lblLoaiXe, lblMauSac, lblGiaTien, lblSoGhe, lblHinhAnh;
  private JTextField txtTenXeDat, txtMauSac, txtGiaTien, txtSoGhe;

  private JButton btnDatXe, btnHuyDatXe;

  // Sửa xe
  private JPanel pnlSuaXe;
  private JLabel lblMaXeSua, lblTenXeSua, lblThuongHieuSua, lblLoaiXeSua, lblMauSacSua, lblGiaTienSua, lblSoGheSua,
      lblHinhAnhSua;
  private JTextField txtMaXeSua, txtTenXeSua, txtMauSacSua, txtGiaTienSua, txtSoGheSua;

  private JButton btnSuaXe, btnHuySuaXe;
  private Cursor handCursor = new Cursor(Cursor.HAND_CURSOR);

  private Font font = new Font(Constants.MAIN_FONT, Font.PLAIN, 20);

  private IXeBus xeBus = (IXeBus) Naming.lookup("rmi://localhost:8080/xe");
  private Generator gen = new Generator();
  List<Xe> xe = xeBus.getAllXe();
  private NhanVien nhanVien = new NhanVien();
  private TaiKhoan taiKhoan = new TaiKhoan();
  URL url = new URL(Constants.ICON_LOGO);
  Image imageIcon = new ImageIcon(url).getImage().getScaledInstance(200, 150, Image.SCALE_SMOOTH);

  public QuanLyXe(TaiKhoan taiKhoan) throws MalformedURLException, RemoteException, NotBoundException {
    this.nhanVien = taiKhoan.getNhanVien();
    this.taiKhoan = taiKhoan;
    setTitle(Constants.APP_NAME);
    setExtendedState(MAXIMIZED_BOTH);
    setMinimumSize(new Dimension(1500, 800));
    this.setIconImage(imageIcon);
    setDefaultCloseOperation(EXIT_ON_CLOSE);
    setLocationRelativeTo(null);
    this.setBackground(Color.WHITE);
    setLayout(null);
    layeredPane = new JLayeredPane();
    layeredPane.setBounds(0, 0, 2000, 1500);
    add(layeredPane);
    MainUI();

  }

  private void MainUI() throws RemoteException {
    pnlMainUI = new JPanel();
    pnlMainUI.setLayout(null);
    pnlMainUI.setBounds(0, 0, 2000, 1400);
    pnlTop();
    pnlMenu();
    PnlCenter();
    layeredPane.add(pnlMainUI, Integer.valueOf(1));

  }

  void PnlCenter() throws RemoteException {
    pnlCenter = new JLayeredPane();
    pnlCenter.setBounds(0, 150, 1800, 1000);
    pnlCenter.setLayout(null);
    pnlCenter.setBackground(Color.WHITE);
    pnlFilter = new JPanel();
    pnlFilter.setBounds(0, 0, 890, 80);
    pnlFilter.setLayout(null);
    pnlFilter.setBackground(Color.WHITE);

    dfHangXe = new DefaultComboBoxModel<String>();
    dfHangXe.addElement("Thương hiệu");
    dfHangXe.addElement("Lamborghini");
    dfHangXe.addElement("Ferrari");
    dfHangXe.addElement("Porsche");
    dfHangXe.addElement("Audi");
    dfHangXe.addElement("BMW");
    dfHangXe.addElement("Mercedes");
    dfHangXe.addElement("Toyota");
    dfHangXe.addElement("Honda");
    dfHangXe.addElement("Kia");
    dfHangXe.addElement("Hyundai");
    dfHangXe.addElement("Mazda");
    dfHangXe.addElement("Suzuki");
    dfHangXe.addElement("Nissan");
    cmbHangXe = new JComboBox<String>(dfHangXe);
    cmbHangXe.setBounds(50, 30, 200, 30);
    cmbHangXe.setFont(font);
    cmbHangXe.addActionListener(this);
    pnlFilter.add(cmbHangXe);

    dfGiaXe = new DefaultComboBoxModel<String>();
    dfGiaXe.addElement("Giá xe");
    dfGiaXe.addElement("Giá tăng dần");
    dfGiaXe.addElement("Giá giảm dần");
    cmbGiaXe = new JComboBox<String>(dfGiaXe);
    cmbGiaXe.setBounds(300, 30, 200, 30);
    cmbGiaXe.setFont(font);
    cmbGiaXe.addActionListener(this);
    pnlFilter.add(cmbGiaXe);
    PnlBtn();

    PnlDsXe(index);
    PnlThemXe();
    pnlCenter.add(pnlFilter);
    pnlMainUI.add(pnlCenter);
    pnlPagination = new JPanel();
    pnlPagination.setBounds(0, 600, 890, 40);
    pnlPagination.setLayout(null);
    pnlPagination.setBackground(Color.WHITE);
    btnPrevious = new MyButton(15, Color.decode("#cccccc"), Color.decode("#cccccc"));
    btnPrevious.setBorderPainted(false);
    btnPrevious.setFocusPainted(false);
    FontIcon iconPrevious = FontIcon.of(FontAwesomeSolid.CHEVRON_LEFT, 20);
    btnPrevious.setIcon(iconPrevious);
    btnPrevious.setBounds(30, 10, 100, 30);
    btnPrevious.setFont(new Font(Constants.MAIN_FONT, Font.PLAIN, 15));

    pnlPagination.add(btnPrevious);
    btnNext = new MyButton(15, Color.decode("#cccccc"), Color.decode("#cccccc"));
    FontIcon iconNext = FontIcon.of(FontAwesomeSolid.CHEVRON_RIGHT, 20);
    btnNext.setIcon(iconNext);
    btnNext.setBorderPainted(false);
    btnNext.setFocusPainted(false);
    btnNext.setBounds(760, 10, 100, 30);
    btnNext.setFont(new Font(Constants.MAIN_FONT, Font.PLAIN, 15));

    pnlPagination.add(btnNext);

    pnlCenter.add(pnlPagination);
    btnPrevious.addActionListener(this);
    btnNext.addActionListener(this);
  }

  void PnlSuaXe() {

    pnlSuaXe = new JPanel();
    pnlSuaXe.setBounds(900, 30, 600, 550);
    pnlSuaXe.setLayout(null);
    TitledBorder title = new TitledBorder(BorderFactory.createLineBorder(Color.black), "Sửa xe");
    pnlSuaXe.setBorder(title);

    lblMaXeSua = new JLabel("Mã xe");
    lblMaXeSua.setBounds(50, 20, 200, 40);
    lblMaXeSua.setFont(font);
    pnlSuaXe.add(lblMaXeSua);

    txtMaXeSua = new RoundJTextField(15);
    txtMaXeSua.setBounds(250, 20, 330, 30);
    txtMaXeSua.setFont(font);
    pnlSuaXe.add(txtMaXeSua);

    lblTenXeSua = new JLabel("Tên xe");
    lblTenXeSua.setBounds(50, 70, 200, 30);
    lblTenXeSua.setFont(font);
    pnlSuaXe.add(lblTenXeSua);

    txtTenXeSua = new RoundJTextField(15);
    txtTenXeSua.setBounds(250, 70, 330, 30);
    txtTenXeSua.setFont(font);
    pnlSuaXe.add(txtTenXeSua);
    txtTenXeSua.addActionListener(this);

    lblThuongHieuSua = new JLabel("Thương hiệu");
    lblThuongHieuSua.setBounds(50, 120, 200, 30);
    lblThuongHieuSua.setFont(font);
    pnlSuaXe.add(lblThuongHieuSua);

    dfThuongHieu = new DefaultComboBoxModel<String>();
    dfThuongHieu.addElement("Lamborghini");
    dfThuongHieu.addElement("Ferrari");
    dfThuongHieu.addElement("Porsche");
    dfThuongHieu.addElement("Audi");
    dfThuongHieu.addElement("BMW");
    dfThuongHieu.addElement("Mercedes");
    dfThuongHieu.addElement("Toyota");
    dfThuongHieu.addElement("Honda");
    dfThuongHieu.addElement("Kia");
    dfThuongHieu.addElement("Hyundai");
    dfThuongHieu.addElement("Mazda");
    dfThuongHieu.addElement("Suzuki");
    dfThuongHieu.addElement("Nissan");
    cmbThuongHieu = new JComboBox<String>(dfThuongHieu);
    cmbThuongHieu.setBounds(250, 120, 330, 30);
    cmbThuongHieu.setFont(font);
    cmbThuongHieu.setBackground(Color.WHITE);
    pnlSuaXe.add(cmbThuongHieu);

    lblLoaiXeSua = new JLabel("Loại xe");
    lblLoaiXeSua.setBounds(50, 170, 200, 30);
    lblLoaiXeSua.setFont(font);
    pnlSuaXe.add(lblLoaiXeSua);

    dfLoaiXe = new DefaultComboBoxModel<String>();
    dfLoaiXe.addElement("Xe sedan");
    dfLoaiXe.addElement("Xe mui trần");
    dfLoaiXe.addElement("Xe thể thao");
    dfLoaiXe.addElement("Xe bán tải");
    cmbLoaiXe = new JComboBox<String>(dfLoaiXe);
    cmbLoaiXe.setBounds(250, 170, 330, 30);
    cmbLoaiXe.setFont(font);
    cmbLoaiXe.setBackground(Color.WHITE);
    pnlSuaXe.add(cmbLoaiXe);

    lblSoGheSua = new JLabel("Số chỗ ngồi");
    lblSoGheSua.setBounds(50, 220, 200, 30);
    lblSoGheSua.setFont(font);
    pnlSuaXe.add(lblSoGheSua);

    txtSoGheSua = new RoundJTextField(15);
    txtSoGheSua.setBounds(250, 220, 330, 30);
    txtSoGheSua.setFont(font);
    pnlSuaXe.add(txtSoGheSua);

    lblGiaTienSua = new JLabel("Giá xe");
    lblGiaTienSua.setBounds(50, 270, 200, 30);
    lblGiaTienSua.setFont(font);
    pnlSuaXe.add(lblGiaTienSua);

    txtGiaTienSua = new RoundJTextField(15);
    txtGiaTienSua.setBounds(250, 270, 330, 30);
    txtGiaTienSua.setFont(font);
    pnlSuaXe.add(txtGiaTienSua);

    lblMauSacSua = new JLabel("Màu xe");
    lblMauSacSua.setBounds(50, 320, 200, 30);
    lblMauSacSua.setFont(font);
    pnlSuaXe.add(lblMauSacSua);

    txtMauSacSua = new RoundJTextField(15);
    txtMauSacSua.setBounds(250, 320, 330, 30);
    txtMauSacSua.setFont(font);
    pnlSuaXe.add(txtMauSacSua);

    lblHinhAnhSua = new JLabel("Hình ảnh");
    lblHinhAnhSua.setBounds(50, 370, 200, 30);
    lblHinhAnhSua.setFont(font);
    pnlSuaXe.add(lblHinhAnhSua);

    btnChonAnh = new MyButton(0, Color.decode("#eeeeee"), Color.decode("#eeeeee"));
    btnChonAnh.setBounds(220, 370, 30, 30);
    FontIcon iconFolder = FontIcon.of(FontAwesomeSolid.FOLDER_OPEN, 25, Color.BLACK);
    btnChonAnh.setIcon(iconFolder);
    btnChonAnh.setFont(font);
    btnChonAnh.setBorderPainted(false);
    btnChonAnh.setContentAreaFilled(false);
    btnChonAnh.setFocusPainted(false);
    btnChonAnh.setOpaque(false);
    btnChonAnh.setCursor(handCursor);
    pnlSuaXe.add(btnChonAnh);

    btnHinhAnh = new JButton();
    btnHinhAnh.setBounds(250, 370, 330, 120);
    btnHinhAnh.setBorderPainted(false);
    btnHinhAnh.setFocusPainted(false);
    btnHinhAnh.setContentAreaFilled(false);
    btnHinhAnh.setOpaque(false);
    btnHinhAnh.setFont(font);
    pnlSuaXe.add(btnHinhAnh);

    btnSuaXe = new MyButton(15, Color.ORANGE, Color.ORANGE);
    btnSuaXe.setText("Cập nhật");
    btnSuaXe.setBorderPainted(false);
    btnSuaXe.setFocusPainted(false);
    btnSuaXe.setBounds(100, 510, 200, 30);
    btnSuaXe.setFont(font);
    pnlSuaXe.add(btnSuaXe);

    btnHuySuaXe = new MyButton(15, Color.decode("#FF5858"), Color.decode("#FF5858"));
    btnHuySuaXe.setText("Hủy");
    btnHuySuaXe.setBorderPainted(false);
    btnHuySuaXe.setFocusPainted(false);
    btnHuySuaXe.setBounds(330, 510, 200, 30);
    btnHuySuaXe.setFont(font);
    pnlSuaXe.add(btnHuySuaXe);

    btnSuaXe.addActionListener(this);
    btnHuySuaXe.addActionListener(this);
    btnChonAnh.addActionListener(this);

    pnlCenter.add(pnlSuaXe);

  }

  void PnlThemXe() {
    pnlDatXe = new JPanel();
    pnlDatXe.setBounds(900, 30, 600, 550);
    pnlDatXe.setLayout(null);
    TitledBorder title = new TitledBorder(BorderFactory.createLineBorder(Color.black), "Thêm xe");

    pnlDatXe.setBorder(title);

    lblTenXeDat = new JLabel("Tên xe");
    lblTenXeDat.setBounds(50, 20, 200, 30);
    lblTenXeDat.setFont(font);
    pnlDatXe.add(lblTenXeDat);

    txtTenXeDat = new RoundJTextField(15);
    txtTenXeDat.setBounds(250, 20, 330, 30);
    txtTenXeDat.setFont(font);
    pnlDatXe.add(txtTenXeDat);

    lblThuongHieu = new JLabel("Thương hiệu");
    lblThuongHieu.setBounds(50, 70, 200, 30);
    lblThuongHieu.setFont(font);
    pnlDatXe.add(lblThuongHieu);

    dfThuongHieu = new DefaultComboBoxModel<String>();
    dfThuongHieu.addElement("Lamborghini");
    dfThuongHieu.addElement("Ferrari");
    dfThuongHieu.addElement("Porsche");
    dfThuongHieu.addElement("Audi");
    dfThuongHieu.addElement("BMW");
    dfThuongHieu.addElement("Mercedes");
    dfThuongHieu.addElement("Toyota");
    dfThuongHieu.addElement("Honda");
    dfThuongHieu.addElement("Kia");
    dfThuongHieu.addElement("Hyundai");
    dfThuongHieu.addElement("Mazda");
    dfThuongHieu.addElement("Suzuki");
    dfThuongHieu.addElement("Nissan");
    cmbThuongHieu = new JComboBox<String>(dfThuongHieu);
    cmbThuongHieu.setBounds(250, 70, 330, 30);
    cmbThuongHieu.setFont(font);
    cmbThuongHieu.setBackground(Color.WHITE);
    pnlDatXe.add(cmbThuongHieu);

    lblLoaiXe = new JLabel("Loại xe");
    lblLoaiXe.setBounds(50, 120, 200, 30);
    lblLoaiXe.setFont(font);
    pnlDatXe.add(lblLoaiXe);

    dfLoaiXe = new DefaultComboBoxModel<String>();
    dfLoaiXe.addElement("Xe sedan");
    dfLoaiXe.addElement("Xe mui trần");
    dfLoaiXe.addElement("Xe thể thao");
    dfLoaiXe.addElement("Xe bán tải");
    cmbLoaiXe = new JComboBox<String>(dfLoaiXe);
    cmbLoaiXe.setBounds(250, 120, 330, 30);
    cmbLoaiXe.setFont(font);
    cmbLoaiXe.setBackground(Color.WHITE);
    pnlDatXe.add(cmbLoaiXe);

    lblSoGhe = new JLabel("Số chỗ ngồi");
    lblSoGhe.setBounds(50, 170, 200, 30);
    lblSoGhe.setFont(font);
    pnlDatXe.add(lblSoGhe);

    txtSoGhe = new RoundJTextField(15);
    txtSoGhe.setBounds(250, 170, 330, 30);
    txtSoGhe.setFont(font);
    pnlDatXe.add(txtSoGhe);

    lblGiaTien = new JLabel("Giá xe");
    lblGiaTien.setBounds(50, 220, 200, 30);
    lblGiaTien.setFont(font);
    pnlDatXe.add(lblGiaTien);

    txtGiaTien = new RoundJTextField(15);
    txtGiaTien.setBounds(250, 220, 330, 30);
    txtGiaTien.setFont(font);
    pnlDatXe.add(txtGiaTien);

    lblMauSac = new JLabel("Màu xe");
    lblMauSac.setBounds(50, 270, 200, 30);
    lblMauSac.setFont(font);
    pnlDatXe.add(lblMauSac);

    txtMauSac = new RoundJTextField(15);
    txtMauSac.setBounds(250, 270, 330, 30);
    txtMauSac.setFont(font);
    pnlDatXe.add(txtMauSac);

    lblHinhAnh = new JLabel("Hình ảnh");
    lblHinhAnh.setBounds(50, 320, 200, 30);
    lblHinhAnh.setFont(font);
    pnlDatXe.add(lblHinhAnh);

    btnChonAnh = new MyButton(0, Color.decode("#eeeeee"), Color.decode("#eeeeee"));
    btnChonAnh.setBounds(220, 320, 30, 30);
    FontIcon iconFolder = FontIcon.of(FontAwesomeSolid.FOLDER_OPEN, 25, Color.BLACK);
    btnChonAnh.setIcon(iconFolder);
    btnChonAnh.setFont(font);
    btnChonAnh.setFont(font);
    btnChonAnh.setBorderPainted(false);
    btnChonAnh.setContentAreaFilled(false);
    btnChonAnh.setFocusPainted(false);
    btnChonAnh.setOpaque(false);
    btnChonAnh.setCursor(handCursor);
    pnlDatXe.add(btnChonAnh);

    btnHinhAnh = new JButton();
    btnHinhAnh.setBounds(250, 320, 330, 170);
    btnHinhAnh.setBorderPainted(false);
    btnHinhAnh.setFocusPainted(false);
    btnHinhAnh.setContentAreaFilled(false);
    btnHinhAnh.setOpaque(false);
    btnHinhAnh.setFont(font);
    pnlDatXe.add(btnHinhAnh);

    btnDatXe = new MyButton(15, Color.GREEN, Color.GREEN);
    btnDatXe.setText("Thêm xe");
    btnDatXe.setBorderPainted(false);
    btnDatXe.setFocusPainted(false);
    btnDatXe.setBounds(100, 510, 200, 30);
    btnDatXe.setFont(font);
    pnlDatXe.add(btnDatXe);

    btnHuyDatXe = new MyButton(15, Color.decode("#FF5858"), Color.decode("#FF5858"));
    btnHuyDatXe.setText("Hủy");
    btnHuyDatXe.setBorderPainted(false);
    btnHuyDatXe.setFocusPainted(false);
    btnHuyDatXe.setBounds(330, 510, 200, 30);
    btnHuyDatXe.setFont(font);
    pnlDatXe.add(btnHuyDatXe);
    btnHuyDatXe.addActionListener(this);
    btnChonAnh.addActionListener(this);
    btnDatXe.addActionListener(this);
    btnHinhAnh.addActionListener(this);
    pnlCenter.add(pnlDatXe);

  }

  void PnlDsXe(int index) throws RemoteException {
    this.repaint();
    List<Xe> listXe = xe;
    int a;
    int b = listXe.size();
    int c = 0;
    pnlDsXe = new JPanel();
    pnlDsXe.setBounds(0, 60, 890, 550);
    pnlDsXe.setBackground(Color.WHITE);
    pnlDsXe.setLayout(null);
    try {
      for (a = index; a < b; index++) {
        anh = new ImageIcon(
            ImageIO.read(new File(listXe.get(index).getHinhAnh())).getScaledInstance(160, 90, Image.SCALE_SMOOTH));
        btnXe = new JButton();
        btnXe.setText(listXe.get(index).getMaXe());
        btnXe.setForeground(Color.WHITE);
        btnXe.setBorderPainted(false);
        btnXe.setFocusPainted(false);
        btnXe.setBackground(Color.WHITE);
        btnXe.setLayout(null);
        btnXe.setCursor(handCursor);
        btnXe.setBounds(10 + (c % 4) * 220, 10 + (c / 4) * 180, 200, 175);
        lblHinhAnh = new JLabel(anh);
        lblHinhAnh.setBounds(0, 0, 200, 175);
        lblHinhAnh.setVisible(true);
        btnXe.add(lblHinhAnh);
        btnXe.setBounds(10 + (c % 4) * 220, 10 + (c / 4) * 180, 200, 175);
        lblTenXe = new JLabel(listXe.get(index).getTenXe());
        lblTenXe.setBounds(50, 80, 200, 160);
        lblTenXe.setFont(font);
        lblTenXe.setVisible(true);
        btnXe.addActionListener(this);
        btnXe.add(lblTenXe);
        btnXe.setVisible(true);
        pnlDsXe.add(btnXe);
        c++;
        a++;
      }

    } catch (Exception e) {
      e.printStackTrace();
    }
    pnlCenter.add(pnlDsXe);

  }

  void PnlBtn() {
    pnlBtn = new JPanel();
    pnlBtn.setBounds(900, 590, 600, 50);
    pnlBtn.setLayout(null);

    btnThem = new MyButton(15, Color.GREEN, Color.GREEN);
    btnThem.setText("Thêm");
    btnThem.setBorderPainted(false);
    btnThem.setFocusPainted(false);
    btnThem.setBounds(25, 0, 150, 40);
    btnThem.setFont(font);
    btnThem.setBackground(Color.GREEN);
    FontIcon iconThem = FontIcon.of(FontAwesomeSolid.PLUS);
    iconThem.setIconSize(20);
    btnThem.setIcon(iconThem);
    btnThem.setCursor(handCursor);
    pnlBtn.add(btnThem);

    btnXoa = new MyButton(15, Color.RED, Color.RED);
    btnXoa.setText("Xóa");
    btnXoa.setBorderPainted(false);
    btnXoa.setFocusPainted(false);
    btnXoa.setBounds(225, 0, 150, 40);
    btnXoa.setFont(font);
    FontIcon iconXoa = FontIcon.of(FontAwesomeSolid.TRASH_ALT);
    iconXoa.setIconSize(20);
    btnXoa.setIcon(iconXoa);
    btnXoa.setCursor(handCursor);
    pnlBtn.add(btnXoa);

    btnSua = new MyButton(15, Color.ORANGE, Color.ORANGE);
    btnSua.setText("Sửa");
    btnSua.setBorderPainted(false);
    btnSua.setFocusPainted(false);
    btnSua.setBounds(425, 0, 150, 40);
    btnSua.setFont(font);
    FontIcon iconSua = FontIcon.of(FontAwesomeSolid.EDIT);
    iconSua.setIconSize(20);
    btnSua.setIcon(iconSua);
    btnSua.setCursor(handCursor);
    pnlBtn.add(btnSua);
    pnlCenter.add(pnlBtn);

    btnThem.addActionListener(this);
    btnXoa.addActionListener(this);
    btnSua.addActionListener(this);
  }

  private void pnlTop() {
    pnlTop = new JPanel();
    pnlTop.setBounds(0, 0, 2000, 100);

    pnlTop.setBackground(Color.decode(Constants.MAIN_COLOR));
    pnlTop.setLayout(null);

    lblIconLogo = new JLabel();
    lblIconLogo.setBounds(50, -15, 300, 150);
    lblIconLogo.setIcon(new ImageIcon(imageIcon));

    lblTitle = new JLabel(Constants.TITLE);
    lblTitle.setBounds(570, 0, 750, 100);
    lblTitle.setFont(new Font(Constants.MAIN_FONT, Font.BOLD, 70));
    lblTitle.setForeground(Color.decode("#112D4E"));

    pnInfo = new JPanel();
    pnInfo.setBounds(1200, 0, 350, 100);
    pnInfo.setBackground(Color.decode(Constants.MAIN_COLOR));
    pnInfo.setLayout(null);
    btnIconUser = new JButton();
    btnIconUser.setText(nhanVien.getTenNV());

    btnIconUser.setFont(new Font(Constants.MAIN_FONT, Font.BOLD, 14));
    btnIconUser.setBounds(20, 0, 300, 50);
    btnIconUser.setBorder(null);
    btnIconUser.setFocusPainted(false);
    btnIconUser.setBorderPainted(false);
    btnIconUser.setBackground(Color.decode(Constants.MAIN_COLOR));
    btnIconUser.setFont(new Font(Constants.MAIN_FONT, Font.BOLD, 18));
    FontIcon user = FontIcon.of(FontAwesomeSolid.USER_CIRCLE, 30, Color.decode("#112D4E"));
    btnIconUser.setIcon(user);
    btnIconLogout = new JButton("Đăng xuất");
    btnIconLogout.setFont(new Font(Constants.MAIN_FONT, Font.BOLD, 14));
    btnIconLogout.setBounds(0, 50, 300, 50);
    btnIconLogout.setBorder(null);
    btnIconLogout.setBackground(Color.decode(Constants.MAIN_COLOR));
    btnIconLogout.setFont(new Font(Constants.MAIN_FONT, Font.BOLD, 18));
    FontIcon logout = FontIcon.of(FontAwesomeSolid.SIGN_OUT_ALT, 30, Color.RED);
    btnIconLogout.setIcon(logout);

    pnInfo.add(btnIconUser);
    pnInfo.add(btnIconLogout);

    pnlTop.add(lblIconLogo);
    pnlTop.add(lblTitle);
    pnlTop.add(pnInfo);
    pnlMainUI.add(pnlTop);
  }

  private void pnlMenu() {
    pnlMenu = new JPanel();
    pnlMenu.setBounds(0, 100, 1800, 60);
    pnlMenu.setLayout(null);
    pnlMenu.setBackground(Color.decode("#71C9CE"));
    lblQuanLyXe = new JLabel("Quản lý xe");
    lblQuanLyXe.setBounds(650, 0, 400, 60);
    lblQuanLyXe.setBackground(Color.red);
    lblQuanLyXe.setFont(new Font(Constants.MAIN_FONT, Font.BOLD, 40));
    lblQuanLyXe.setForeground(Color.WHITE);
    btnMenu = new JButton();
    btnMenu.setCursor(handCursor);
    btnMenu.setBorder(null);
    btnMenu.setFocusPainted(false);
    btnMenu.setBounds(0, 0, 60, 60);
    btnMenu.setBackground(Color.decode("#71C9CE"));
    btnMenu.setIcon(FontIcon.of(FontAwesomeSolid.BARS, 40));
    pnlMenu.add(btnMenu);
    pnlMenu.add(lblQuanLyXe);
    pnlMainUI.add(pnlMenu);
    btnMenu.addActionListener(this);

  }

  private void SlideBar() {
    pnlMainSlider = new JPanel();
    pnlMainSlider.setBounds(0, 0, 300, 1400);
    pnlMainSlider.setLayout(null);

    pnlSlideBar = new JPanel();
    pnlSlideBar.setLayout(null);
    pnlSlideBar.setBounds(0, 0, 300, 2000);
    pnlSlideBar.setBackground(Color.decode("#CBF1F5"));

    Font f1 = new Font("Tahoma", Font.BOLD, 50);
    pnlSlideBar.add(lblMenu = new JLabel("Menu"));
    lblMenu.setFont(f1);
    lblMenu.setBounds(80, 0, 200, 80);

    lblLine = new JLabel();
    lblLine.setBounds(0, 85, 500, 4);

    pnlSlideBar.add(lblLine);

    Font f2 = new Font("Tahoma", Font.PLAIN, 25);
    btnNhanVien = new MyButton(0, Color.decode("#CBF1F5"), Color.decode("#CBF1F5"));
    btnNhanVien.setText("   Nhân viên");
    btnNhanVien.setHorizontalAlignment(SwingConstants.CENTER);
    btnNhanVien.setFont(f2);
    btnNhanVien.setBounds(40, 100, 220, 60);
    btnNhanVien.setBackground(Color.decode("#CBF1F5"));
    btnNhanVien.setBorder(null);
    FontIcon iconNhanVien = FontIcon.of(FontAwesomeSolid.USERS, 45);
    btnNhanVien.setIcon(iconNhanVien);
    btnNhanVien.setCursor(handCursor);
    pnlSlideBar.add(btnNhanVien);

    pnlSlideBar.add(btnHopDong = new MyButton(0, Color.decode("#CBF1F5"), Color.decode("#CBF1F5")));
    btnHopDong.setText("  Hợp đồng");
    btnHopDong.setHorizontalAlignment(SwingConstants.CENTER);
    btnHopDong.setFont(f2);
    btnHopDong.setBounds(40, 180, 220, 60);
    btnHopDong.setBorder(null);
    btnHopDong.setCursor(handCursor);
    FontIcon iconHopDong = FontIcon.of(FontAwesomeSolid.FILE_CONTRACT, 45);
    btnHopDong.setIcon(iconHopDong);

    pnlSlideBar.add(btnKhachHang = new MyButton(0, Color.decode("#CBF1F5"), Color.decode("#CBF1F5")));
    btnKhachHang.setText("  Khách hàng");
    btnKhachHang.setHorizontalAlignment(SwingConstants.CENTER);
    btnKhachHang.setFont(f2);
    btnKhachHang.setBounds(40, 260, 220, 60);
    btnKhachHang.setBorder(null);
    btnKhachHang.setCursor(handCursor);
    FontIcon iconKhachHang = FontIcon.of(FontAwesomeSolid.USER_TIE, 45);
    btnKhachHang.setIcon(iconKhachHang);

    pnlSlideBar.add(btnQuanLyXe = new MyButton(0, Color.decode("#CBF1F5"), Color.decode("#CBF1F5")));
    btnQuanLyXe.setText("  Quản lý xe");
    btnQuanLyXe.setHorizontalAlignment(SwingConstants.CENTER);
    btnQuanLyXe.setFont(f2);
    btnQuanLyXe.setBounds(40, 340, 220, 60);
    btnQuanLyXe.setBorder(null);
    btnQuanLyXe.setBackground(Color.decode("#CBF1F5"));
    btnQuanLyXe.setCursor(handCursor);
    FontIcon iconXe = FontIcon.of(FontAwesomeSolid.CAR, 45);
    btnQuanLyXe.setIcon(iconXe);

    pnlSlideBar.add(btnPhuTung = new MyButton(0, Color.decode("#CBF1F5"), Color.decode("#CBF1F5")));
    btnPhuTung.setText("  Phụ tùng");
    btnPhuTung.setHorizontalAlignment(SwingConstants.CENTER);
    btnPhuTung.setFont(f2);
    btnPhuTung.setBounds(40, 420, 220, 60);
    btnPhuTung.setBorder(null);
    btnPhuTung.setBackground(Color.decode("#CBF1F5"));
    btnPhuTung.setCursor(handCursor);
    FontIcon iconPhuTung = FontIcon.of(FontAwesomeSolid.COGS, 45);
    btnPhuTung.setIcon(iconPhuTung);

    pnlSlideBar.add(btnTroGiup = new MyButton(0, Color.decode("#CBF1F5"), Color.decode("#CBF1F5")));
    btnTroGiup.setText("  Trợ giúp");
    btnTroGiup.setHorizontalAlignment(SwingConstants.CENTER);
    btnTroGiup.setFont(f2);
    btnTroGiup.setBounds(40, 500, 220, 60);
    btnTroGiup.setBorder(null);
    btnTroGiup.setBackground(Color.decode("#CBF1F5"));
    btnTroGiup.setCursor(handCursor);
    FontIcon iconTroGiup = FontIcon.of(FontAwesomeSolid.QUESTION_CIRCLE, 45);
    btnTroGiup.setIcon(iconTroGiup);

    pnlSlideBar.add(btnClose = new JButton(""));
    btnClose.setFont(f2);
    btnClose.setBounds(240, 20, 50, 50);
    FontIcon iconPre = FontIcon.of(FontAwesomeSolid.ANGLE_LEFT, 40);
    btnClose.setIcon(iconPre);
    btnClose.setBorder(null);
    btnClose.setBackground(Color.decode("#CBF1F5"));
    btnClose.setCursor(handCursor);

    pnlMainSlider.add(pnlSlideBar);
    this.add(pnlMainSlider);
    btnClose.addActionListener(this);
    btnNhanVien.addActionListener(this);
    btnHopDong.addActionListener(this);
    btnKhachHang.addActionListener(this);
    btnQuanLyXe.addActionListener(this);
    btnPhuTung.addActionListener(this);
    btnTroGiup.addActionListener(this);
  }

  @Override
  public void actionPerformed(ActionEvent e) {

    try {
      int x = 300;
      if (e.getSource() == btnClose) {
        if (flag == true) {
          new Thread(new Runnable() {
            @Override
            public void run() {
              try {
                for (int i = 300; i > 0; i -= 2) {
                  Thread.sleep(1);
                  pnlSlideBar.setSize(i, 2000);

                }
                layeredPane.add(pnlMainUI, Integer.valueOf(1));
                layeredPane.remove(pnlMainSlider);
              } catch (Exception e) {
                JOptionPane.showMessageDialog(null, e);

              }
            }

          }).start();
          flag = false;
        }
      } else if (e.getSource() == btnMenu) {
        if (flag == false) {
          layeredPane.remove(pnlMainUI);
          SlideBar();
          layeredPane.add(pnlMainSlider, Integer.valueOf(2));
          pnlSlideBar.setSize(x, 2000);

          new Thread(new Runnable() {

            @Override
            public void run() {
              try {
                for (int i = 0; i < 300; i += 2) {
                  Thread.sleep(1);
                  pnlSlideBar.setSize(i, 2000);

                }
              } catch (Exception e) {
                JOptionPane.showMessageDialog(null, e);

              }
            }

          }).start();
          flag = true;
        }
      } else if (e.getSource() == btnNhanVien) {
        new QLNhanVien(taiKhoan).setVisible(true);
        this.dispose();
      } else if (e.getSource() == btnHopDong) {
        new QuanLyHopDong(taiKhoan, null).setVisible(true);
        this.dispose();
      } else if (e.getSource() == btnKhachHang) {
        new QuanLyKhachHang(taiKhoan).setVisible(true);
        this.dispose();
      } else if (e.getSource() == btnQuanLyXe) {
        if (flag == true) {
          new Thread(new Runnable() {
            @Override
            public void run() {
              try {
                for (int i = 300; i > 0; i -= 2) {
                  Thread.sleep(1);
                  pnlSlideBar.setSize(i, 2000);
                }
                layeredPane.add(pnlMainUI, Integer.valueOf(1));
                layeredPane.remove(pnlMainSlider);
              } catch (Exception e) {
                JOptionPane.showMessageDialog(null, e);
              }
            }

          }).start();
          flag = false;
        }
      } else if (e.getSource() == btnPhuTung) {
        new QuanLyPhuTungXeVaDichVu(taiKhoan).setVisible(true);
        this.dispose();
      } else if (e.getSource() == btnTroGiup) {
        try {
          Desktop.getDesktop().browse(new URL("https://phantan-nhom28.netlify.app/").toURI());
        } catch (Exception e2) {
          e2.printStackTrace();
        }
      }
      if (e.getSource() == btnThem) {
        pnlSuaXe.removeAll();
        PnlThemXe();
        pnlCenter.add(pnlDatXe, 1);
      } else if (e.getSource() == btnSua) {
        pnlDatXe.removeAll();
        PnlSuaXe();
        pnlCenter.add(pnlSuaXe, 1);
      }
      if (e.getSource() == btnThem) {
        pnlSuaXe.removeAll();
        PnlThemXe();
        pnlCenter.add(pnlDatXe, 1);
      } else if (e.getSource() == btnSua) {
        pnlDatXe.removeAll();
        PnlSuaXe();
        pnlCenter.add(pnlSuaXe, 1);
      }

      for (int i = 0; i < pnlDsXe.getComponentCount(); i++) {
        if (pnlDsXe.getComponent(i) instanceof JButton) {
          JButton btn = (JButton) pnlDsXe.getComponent(i);
          if (e.getSource() == btn) {
            for (Xe v : xe) {
              if (v.getMaXe().equals(btn.getText())) {
                new ChiTietXe(taiKhoan, v).setVisible(true);
              }
            }
          }
        }
      }

      if (e.getSource() == btnChonAnh) {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("Chọn ảnh");
        // set icon cho file chooser

        fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
        fileChooser.setAcceptAllFileFilterUsed(false);
        FileNameExtensionFilter filter = new FileNameExtensionFilter("PNG, JPG, JPEG", "png", "jpg", "jpeg");
        fileChooser.addChoosableFileFilter(filter);
        int result = fileChooser.showOpenDialog(null);
        if (result == JFileChooser.APPROVE_OPTION) {
          File selectedFile = fileChooser.getSelectedFile();
          String path = selectedFile.getAbsolutePath();
          duongDan = path;
          try {
            anh = new ImageIcon(ImageIO.read(new File(path)).getScaledInstance(320, 180, Image.SCALE_SMOOTH));
            btnHinhAnh.setIcon(anh);
          } catch (IOException e1) {
            e1.printStackTrace();
          }
        }

      }

      else if (e.getSource() == btnDatXe) {

        String tenXe = txtTenXeDat.getText();
        String thuongHieu = cmbThuongHieu.getSelectedItem().toString();
        String loaiXe = cmbLoaiXe.getSelectedItem().toString();

        String mau = txtMauSac.getText();
        try {
          if (validator(tenXe, mau, txtGiaTien.getText(), txtSoGhe.getText(), duongDan)) {
            int soGhe = Integer.parseInt(txtSoGhe.getText());
            Long gia = Long.parseLong(txtGiaTien.getText());
            String maXe = gen.taoMaXe();
            Xe xe = new Xe(maXe, tenXe, mau, gia, soGhe, duongDan, gen.convertStringToLoaiXe(loaiXe),
                gen.convertStringToThuongHieu(thuongHieu));
            if (xeBus.themXe(xe)) {
              JOptionPane.showMessageDialog(null, "Thêm thành công");
              loadData();
              revalidate();
              refreshPnlThem();
            } else {
              JOptionPane.showMessageDialog(null, "Thêm thất bại");
            }
          }
        } catch (HeadlessException | NumberFormatException | RemoteException e1) {
          JOptionPane.showMessageDialog(null, "Có lỗi xảy ra vui lòng báo với quản trị viên");
          e1.printStackTrace();
        }
      } else if (e.getSource() == btnHuyDatXe) {
        refreshPnlThem();
      } else if (e.getSource() == btnXoa) {
        String ten = JOptionPane.showInputDialog(rootPane, "Nhập tên xe cần xóa", "Xóa xe", 1);
        try {
          if (xeBus.xoaXe(ten)) {
            JOptionPane.showMessageDialog(null, "Xóa xe thành công");
            loadData();
            revalidate();
          } else {
            JOptionPane.showMessageDialog(null, "Xóa xe thất bại");
          }
        } catch (RemoteException e1) {
          JOptionPane.showMessageDialog(null, "Có lỗi xảy ra vui lòng báo với quản trị viên");
          e1.printStackTrace();
        }
      } else if (e.getSource() == cmbHangXe) {
        String hangXe = cmbHangXe.getSelectedItem().toString();
        List<Xe> list = xeBus.getAllXe();
        xe = new ArrayList<>();
        for (Xe a : list) {
          String thuongHieu = gen.convertTHToString(a.getThuongHieu());
          if (thuongHieu.equals(hangXe)) {
            xe.add(a);
          }
        }
        pnlCenter.remove(pnlDsXe);
        PnlDsXe(index);
        if (hangXe.equals("Thương hiệu")) {
          loadData();
          pnlCenter.remove(pnlDsXe);
          PnlDsXe(index);
        }

      } else if (e.getSource() == cmbGiaXe) {
        String sapXep = cmbGiaXe.getSelectedItem().toString();
        xe = xeBus.getAllXe();
        if (sapXep.equals("Giá tăng dần")) {
          Collections.sort(xe, new Comparator<Xe>() {
            @Override
            public int compare(Xe o1, Xe o2) {
              return (int) (o1.getGiaTien() - o2.getGiaTien());
            }
          });
          pnlCenter.remove(pnlDsXe);
          PnlDsXe(index);
        } else if (sapXep.equals("Giá giảm dần")) {
          Collections.sort(xe, new Comparator<Xe>() {
            @Override
            public int compare(Xe o1, Xe o2) {
              return (int) (o2.getGiaTien() - o1.getGiaTien());
            }
          });
          pnlCenter.remove(pnlDsXe);
          PnlDsXe(index);
        } else if (sapXep.equals("Giá xe")) {
          pnlCenter.remove(pnlDsXe);
          PnlDsXe(index);
        }
      } else if (e.getSource() == btnNext) {
        if (kiemTra < xe.size()) {
          index += 12;
          pnlCenter.remove(pnlDsXe);
          PnlDsXe(index);
          revalidate();
          kiemTra += 12;
        }
      } else if (e.getSource() == btnPrevious) {
        if (kiemTra > 12) {
          index -= 12;
          pnlCenter.remove(pnlDsXe);
          PnlDsXe(index);
          revalidate();
          kiemTra -= 12;
        }
      } else if (e.getSource() == txtTenXeSua) {
        String tenXe = txtTenXeSua.getText();
        try {
          if (xeBus.timXe(tenXe) != null) {
            Xe xe = xeBus.timXe(tenXe);
            txtMaXeSua.setText(xe.getMaXe());
            txtMaXeSua.setEditable(false);
            txtSoGheSua.setText(String.valueOf(xe.getSoGhe()));
            txtMauSacSua.setText(xe.getMauSac());
            txtGiaTienSua.setText(String.valueOf(xe.getGiaTien()));
            try {
              duongDan = xe.getHinhAnh();
              anh = new ImageIcon(
                  ImageIO.read(new File(xe.getHinhAnh())).getScaledInstance(320, 180, Image.SCALE_SMOOTH));
              btnHinhAnh.setIcon(anh);
            } catch (IOException e1) {
              e1.printStackTrace();
            }
            cmbThuongHieu.setSelectedItem(gen.convertTHToString(xe.getThuongHieu()));
            cmbLoaiXe.setSelectedItem(gen.convertLoaiXeToString(xe.getLoaiXe()));
          } else if (xeBus.timXe(tenXe) == null) {
            JOptionPane.showMessageDialog(null, "Không tìm thấy xe cần sửa");
          }
        } catch (RemoteException e1) {
          JOptionPane.showMessageDialog(null, "Có lỗi xảy ra vui lòng báo với quản trị viên");
          e1.printStackTrace();
        }
      } else if (e.getSource() == btnSuaXe) {
        String maXe = txtMaXeSua.getText();
        String tenXeSua = txtTenXeSua.getText();
        String thuongHieuSua = cmbThuongHieu.getSelectedItem().toString();
        String loaiXeSua = cmbLoaiXe.getSelectedItem().toString();
        String mauSua = txtMauSacSua.getText();
        String giaSua = txtGiaTienSua.getText();
        String soGheSua = txtSoGheSua.getText();
        try {
          if (validator(tenXeSua, mauSua, giaSua, soGheSua, duongDan)) {
            int soGheInt = Integer.parseInt(soGheSua);
            Long giaDouble = Long.parseLong(giaSua);

            Xe xe = new Xe(maXe, tenXeSua, mauSua, giaDouble, soGheInt, duongDan, gen.convertStringToLoaiXe(loaiXeSua),
                gen.convertStringToThuongHieu(thuongHieuSua));
            if (xeBus.suaXe(xe)) {
              JOptionPane.showMessageDialog(null, "Sửa thành công");
              loadData();
              revalidate();
              refreshPnlSua();
            } else {
              JOptionPane.showMessageDialog(null, "Sửa thất bại");
            }
          }
        } catch (HeadlessException | NumberFormatException | RemoteException e1) {
          JOptionPane.showMessageDialog(null, "Có lỗi xảy ra vui lòng báo với quản trị viên");
          e1.printStackTrace();
        }
      } else if (e.getSource() == btnHuySuaXe) {
        refreshPnlSua();
      } else if (e.getSource() == btnHuyDatXe) {
        refreshPnlThem();
      }

    } catch (MalformedURLException | RemoteException | NotBoundException e1) {
      e1.printStackTrace();
    }

  }

  @Override
  public void mouseClicked(MouseEvent e) {

  }

  @Override
  public void mousePressed(MouseEvent e) {

  }

  @Override
  public void mouseReleased(MouseEvent e) {

  }

  @Override
  public void mouseEntered(MouseEvent e) {
  }

  @Override
  public void mouseExited(MouseEvent e) {

  }

  private void loadData() throws RemoteException {
    xe = xeBus.getAllXe();
    pnlCenter.remove(pnlDsXe);
    PnlDsXe(index);
  }

  private Boolean validator(String tenXe, String mauSac, String giaXe, String soGhe, String hinhAnh)
      throws HeadlessException, RemoteException {
    for (int i = 0; i < 10; i++) {
      if (xeBus.validator(tenXe, mauSac, giaXe, soGhe, hinhAnh) == i) {
        switch (i) {
          case 1:
            JOptionPane.showMessageDialog(null, "Tên xe không được để trống");
            return false;
          case 2:
            JOptionPane.showMessageDialog(null, "Màu sắc không được để trống");
            return false;
          case 3:
            JOptionPane.showMessageDialog(null, "Giá không được để trống");
            return false;
          case 4:
            JOptionPane.showMessageDialog(null, "Số chỗ ngồi không được để trống");
            return false;
          case 5:
            JOptionPane.showMessageDialog(null, "Hình ảnh không được để trống");
            return false;
          case 6:
            JOptionPane.showMessageDialog(null, "Giá phải là số");
            return false;
          case 7:
            JOptionPane.showMessageDialog(null, "Số chỗ ngồi phải là số");
            return false;
          case 0:
            return true;
        }
      }
    }
    return false;
  }

  private void refreshPnlThem() {
    txtTenXeDat.setText("");
    txtMauSac.setText("");
    txtGiaTien.setText("");
    txtSoGhe.setText("");
    cmbThuongHieu.setSelectedIndex(0);
    cmbLoaiXe.setSelectedIndex(0);
    btnHinhAnh.setIcon(new ImageIcon(""));
  }

  private void refreshPnlSua() {
    txtMaXeSua.setText("");
    txtTenXeSua.setText("");
    txtMauSacSua.setText("");
    txtGiaTienSua.setText("");
    txtSoGheSua.setText("");
    cmbThuongHieu.setSelectedIndex(0);
    cmbLoaiXe.setSelectedIndex(0);
    btnHinhAnh.setIcon(new ImageIcon(""));
  }
}