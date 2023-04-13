package ui;

import java.awt.BorderLayout;
import java.awt.Desktop;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.net.MalformedURLException;
import java.net.URL;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.time.LocalDate;
import java.util.List;

import javax.swing.AbstractAction;
import javax.swing.DefaultComboBoxModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.KeyStroke;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;

import org.kordamp.ikonli.fontawesome5.FontAwesomeSolid;
import org.kordamp.ikonli.swing.FontIcon;

import bus.IKhachHangBus;
import bus.ITaiKhoanBus;
import entities.KhachHang;
import entities.NhanVien;
import entities.TaiKhoan;
import util.Constants;
import util.Generator;
import util.MyButton;
import util.RoundJTextField;
import util.RoundedBorderWithColor;

public class QuanLyKhachHang extends JFrame implements ActionListener, MouseListener {
  private JLabel lblIconLogo, lblTitle;

  private JPanel pnlMenu, pnlTop, pnInfo, pnlBtn, pnlFilter, pnlCenter, pnlMainUI;
  private JLabel lblQuanLyKH, lblDSKH;
  private MyButton btnThemKH, btnSuaKH, btnXoaKH, btnTimkiemKH;
  private JTextField txtTimkiemKH;;

  private DefaultComboBoxModel<String> dfBoxModelGioiTinh;
  private JComboBox<String> comboBoxGioiTinh, cmbGioiTinh;
  private JTable tableKH;
  private DefaultTableModel tableModelKH;

  private JButton btnIconUser, btnIconLogout, btnRefresh, btnMenu, btnFilter;

  private JLabel lblTenKH, lblSdt, lblDiaChi, lblGioiTinh, lbllocgioitinh, lblEmail;

  private JTextField txtTenKH, txtSdt, txadiaChi, txtEmail;

  // slide bar
  private JPanel pnlSlideBar, pnlMainSlider;
  private JButton btnNhanVien, btnHopDong, btnKhachHang, btnQuanLyXe, btnPhuTung, btnTroGiup, btnClose;
  private JLabel lblMenu;
  private Cursor handCursor = new Cursor(Cursor.HAND_CURSOR);
  private JLayeredPane layeredPane;
  private boolean flag = false;

  // function
  private Generator generator = new Generator();
  IKhachHangBus khachHangBus = (IKhachHangBus) Naming.lookup("rmi://localhost:8080/khachHang");
  ITaiKhoanBus taiKhoanBus = (ITaiKhoanBus) Naming.lookup("rmi://localhost:8080/taiKhoan");
  private String maKH = "";
  private NhanVien nhanVien = new NhanVien();
  private TaiKhoan taiKhoan = new TaiKhoan();
  URL url = new URL(Constants.ICON_LOGO);
  Image imageIcon = new ImageIcon(url).getImage().getScaledInstance(200, 150, Image.SCALE_SMOOTH);

  public QuanLyKhachHang(TaiKhoan taiKhoan) throws MalformedURLException, RemoteException, NotBoundException {
    this.taiKhoan = taiKhoan;
    this.nhanVien = taiKhoan.getNhanVien();
    setTitle(Constants.APP_NAME);
    setExtendedState(MAXIMIZED_BOTH);
    setMinimumSize(new Dimension(1560, 830));
    this.setIconImage(imageIcon);
    setDefaultCloseOperation(EXIT_ON_CLOSE);
    setLocationRelativeTo(null);
    this.setBackground(Color.WHITE);
    setLayout(null);
    layeredPane = new JLayeredPane();
    layeredPane.setBounds(0, 0, 2000, 1500);
    add(layeredPane);
    MainUI();
    handCursor = new Cursor(Cursor.HAND_CURSOR);

    btnThemKH.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(KeyEvent.VK_F1, 0),
        "btnThemKH");
    btnThemKH.getActionMap().put("btnThemKH", new AbstractAction() {
      @Override
      public void actionPerformed(ActionEvent e) {
        btnThemKH.doClick();
      }
    });
    btnSuaKH.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(KeyEvent.VK_F2, 0),
        "btnSuaKH");
    btnSuaKH.getActionMap().put("btnSuaKH", new AbstractAction() {
      @Override
      public void actionPerformed(ActionEvent e) {
        btnSuaKH.doClick();
      }
    });
    btnXoaKH.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(KeyEvent.VK_F3, 0),
        "btnXoaKH");
    btnXoaKH.getActionMap().put("btnXoaKH", new AbstractAction() {
      @Override
      public void actionPerformed(ActionEvent e) {
        btnXoaKH.doClick();
      }
    });
    btnRefresh.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(KeyEvent.VK_F5, 0),
        "btnRefresh");
    btnRefresh.getActionMap().put("btnRefresh", new AbstractAction() {
      @Override
      public void actionPerformed(ActionEvent e) {
        btnRefresh.doClick();
      }
    });
    btnTimkiemKH.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(KeyEvent.VK_ENTER, 0),
        "btnTimkiemKH");
    btnTimkiemKH.getActionMap().put("btnTimkiemKH", new AbstractAction() {
      @Override
      public void actionPerformed(ActionEvent e) {
        btnTimkiemKH.doClick();
      }
    });
  }

  private void MainUI() {
    pnlMainUI = new JPanel();
    pnlMainUI.setLayout(null);
    pnlMainUI.setBounds(0, 0, 2000, 1400);
    pnlMainUI.setBackground(Color.decode("#E3FDFD"));
    pnlTop();
    pnlMenu();
    PnlCenter();
    PnlTable();
    layeredPane.add(pnlMainUI, Integer.valueOf(1));
    tableKH.addMouseListener(new MouseListener() {

      public void mouseReleased(MouseEvent e) {

      }

      public void mousePressed(MouseEvent e) {

      }

      public void mouseExited(MouseEvent e) {

      }

      public void mouseEntered(MouseEvent e) {

      }

      public void mouseClicked(MouseEvent e) {

        int row = tableKH.getSelectedRow();
        maKH = tableKH.getValueAt(row, 0).toString();
        txtTenKH.setText(tableKH.getValueAt(row, 1).toString());
        txtSdt.setText(tableKH.getValueAt(row, 2).toString());
        cmbGioiTinh.setSelectedItem(tableKH.getValueAt(row, 3).toString());
        txadiaChi.setText(tableKH.getValueAt(row, 4).toString());
        txtEmail.setText(tableKH.getValueAt(row, 5).toString());
      }
    });
  }

  void PnlTable() {
    JPanel pnBang = new JPanel();
    pnBang.setLayout(new BorderLayout());

    String[] header = { "Mã KH", "Họ Tên", "Số điện thoại", "Giới Tính ",
        "Địa chỉ", "Email" };
    tableModelKH = new DefaultTableModel(header, 0);
    tableKH = new JTable(tableModelKH);
    tableKH.setRowHeight(25);
    JScrollPane scrollPane = new JScrollPane(tableKH);
    tableKH.setFont(new Font(Constants.MAIN_FONT, Font.PLAIN, 15));
    tableKH.getTableHeader().setFont(new Font(Constants.MAIN_FONT, Font.BOLD, 15));
    pnBang.add(scrollPane);
    pnBang.setBounds(10, 430, 1520, 360);
    tableKH.setDefaultEditor(Object.class, null);
    DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
    centerRenderer.setHorizontalAlignment(JLabel.CENTER);
    tableKH.getColumnModel().getColumn(0).setCellRenderer(centerRenderer);
    tableKH.getColumnModel().getColumn(1).setCellRenderer(centerRenderer);
    tableKH.getColumnModel().getColumn(2).setCellRenderer(centerRenderer);
    tableKH.getColumnModel().getColumn(3).setCellRenderer(centerRenderer);
    tableKH.getColumnModel().getColumn(4).setCellRenderer(centerRenderer);
    tableKH.getColumnModel().getColumn(5).setCellRenderer(centerRenderer);

    pnlMainUI.add(pnBang);

    loadData();
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
    btnIconLogout = new JButton(Constants.DANG_XUAT);
    btnIconLogout.setFont(new Font(Constants.MAIN_FONT, Font.BOLD, 14));
    btnIconLogout.setBounds(0, 50, 300, 50);
    btnIconLogout.setBorder(null);
    btnIconLogout.setBackground(Color.decode(Constants.MAIN_COLOR));
    btnIconLogout.setFont(new Font(Constants.MAIN_FONT, Font.BOLD, 18));
    FontIcon logout = FontIcon.of(FontAwesomeSolid.SIGN_OUT_ALT, 30, Color.RED);
    btnIconLogout.setIcon(logout);

    pnInfo.add(btnIconUser);
    pnInfo.add(btnIconLogout);
    btnIconLogout.addActionListener(this);

    pnlTop.add(lblIconLogo);
    pnlTop.add(lblTitle);
    pnlTop.add(pnInfo);
    pnlMainUI.add(pnlTop);
  }

  void PnlCenter() {
    pnlCenter = new JPanel();
    pnlCenter.setBounds(20, 180, 1800, 250);
    pnlCenter.setLayout(null);
    pnlCenter.setBackground(Color.decode("#E3FDFD"));
    lblDSKH = new JLabel("Danh sách khách hàng");
    lblDSKH.setBounds(0, 200, 450, 70);
    lblDSKH.setFont(new Font(Constants.MAIN_FONT, Font.PLAIN, 20));
    lblDSKH.setForeground(Color.decode(Constants.MENU_TITLE_COLOR));
    pnlCenter.add(lblDSKH);

    pnlBtn = new JPanel();
    pnlBtn.setLayout(null);
    pnlBtn.setBounds(0, 0, 1800, 170);
    pnlBtn.setBackground(Color.decode("#E3FDFD"));

    lblTenKH = new JLabel("Họ tên khách hàng:");
    lblTenKH.setBounds(50, 10, 200, 30);
    lblTenKH.setFont(new Font(Constants.MAIN_FONT, Font.PLAIN, 17));
    pnlBtn.add(lblTenKH);

    txtTenKH = new RoundJTextField(15);
    txtTenKH.setBounds(200, 10, 400, 30);
    txtTenKH.setFont(new Font(Constants.MAIN_FONT, Font.PLAIN, 17));
    txtTenKH.setBorder(new RoundedBorderWithColor(new Color(238, 238, 238), 1, 15));
    pnlBtn.add(txtTenKH);
    // txtTenKH.setBorder(new RoundedBorderWithColor(new Color(238, 238, 238), 1,
    // 30));

    // lblSoDinhDanh = new JLabel("Số định danh:");
    // lblSoDinhDanh.setBounds(10, 125, 120, 70);
    // lblSoDinhDanh.setFont(fonttxt);

    // txtSoDinhDanh = new RoundJTextField(15);
    // txtSoDinhDanh.setBounds(130, 140, 200, 40);
    // txtSoDinhDanh.setFont(fonttxt);
    lblSdt = new JLabel("Số điện thoại:");
    lblSdt.setBounds(50, 50, 200, 30);
    lblSdt.setFont(new Font(Constants.MAIN_FONT, Font.PLAIN, 17));
    pnlBtn.add(lblSdt);
    txtSdt = new RoundJTextField(15);
    txtSdt.setBounds(200, 50, 400, 30);
    txtSdt.setBorder(new RoundedBorderWithColor(new Color(238, 238, 238), 1, 15));
    txtSdt.setFont(new Font(Constants.MAIN_FONT, Font.PLAIN, 17));
    pnlBtn.add(txtSdt);
    // txtmaKH.setBorder(new RoundedBorderWithColor(new Color(238, 238, 238), 1,
    // 30));

    lblGioiTinh = new JLabel("Giới Tính");
    lblGioiTinh.setBounds(50, 90, 200, 30);
    lblGioiTinh.setFont(new Font(Constants.MAIN_FONT, Font.PLAIN, 17));
    pnlBtn.add(lblGioiTinh);
    cmbGioiTinh = new JComboBox<String>();
    cmbGioiTinh.addItem("Nam");
    cmbGioiTinh.addItem("Nữ");
    cmbGioiTinh.setFont(new Font(Constants.MAIN_FONT, Font.PLAIN, 17));
    cmbGioiTinh.setBounds(200, 90, 400, 30);
    cmbGioiTinh.setBorder(new RoundedBorderWithColor(new Color(238, 238, 238), 1, 10));
    cmbGioiTinh.setBackground(Color.decode("#ffffff"));
    pnlBtn.add(cmbGioiTinh);

    lblDiaChi = new JLabel("Địa Chỉ ");
    lblDiaChi.setBounds(670, 10, 200, 30);
    lblDiaChi.setFont(new Font(Constants.MAIN_FONT, Font.PLAIN, 17));
    pnlBtn.add(lblDiaChi);

    txadiaChi = new RoundJTextField(15);
    txadiaChi.setBounds(750, 10, 400, 30);
    txadiaChi.setBorder(new RoundedBorderWithColor(new Color(238, 238, 238), 1, 15));
    txadiaChi.setFont(new Font(Constants.MAIN_FONT, Font.PLAIN, 17));
    pnlBtn.add(txadiaChi);

    lblEmail = new JLabel("Email:");
    lblEmail.setBounds(670, 50, 200, 30);
    lblEmail.setFont(new Font(Constants.MAIN_FONT, Font.PLAIN, 17));
    pnlBtn.add(lblEmail);
    txtEmail = new RoundJTextField(15);
    txtEmail.setBounds(750, 50, 400, 30);
    txtEmail.setBorder(new RoundedBorderWithColor(new Color(238, 238, 238), 1, 15));
    txtEmail.setFont(new Font(Constants.MAIN_FONT, Font.PLAIN, 17));
    pnlBtn.add(txtEmail);

    btnThemKH = new MyButton(20, Color.decode(Constants.BTN_CONFIRM_COLOR), Color.decode(Constants.BTN_CONFIRM_COLOR));
    btnThemKH.setBounds(1300, 15, 150, 35);
    btnThemKH.setText("Thêm(F1)");
    FontIcon add = FontIcon.of(FontAwesomeSolid.PLUS);
    add.setIconSize(25);
    btnThemKH.setIcon(add);
    btnThemKH.setFocusable(false);
    btnThemKH.setBorderPainted(false);

    btnThemKH.setFont(new Font(Constants.MAIN_FONT, Font.PLAIN, 20));
    btnThemKH.setCursor(handCursor);
    pnlBtn.add(btnThemKH);

    btnSuaKH = new MyButton(20, Color.decode(Constants.BTN_EDIT_COLOR), Color.decode(Constants.THIRD_COLOR));
    btnSuaKH.setText("Sửa(F2)");
    btnSuaKH.setBounds(1300, 65, 150, 35);
    FontIcon update = FontIcon.of(FontAwesomeSolid.SAVE);
    update.setIconSize(25);
    btnSuaKH.setIcon(update);
    btnSuaKH.setFocusable(false);
    btnSuaKH.setBorderPainted(false);
    btnSuaKH.setCursor(handCursor);

    btnSuaKH.setFont(new Font(Constants.MAIN_FONT, Font.PLAIN, 20));
    pnlBtn.add(btnSuaKH);

    btnXoaKH = new MyButton(20, Color.WHITE, Color.WHITE);
    btnXoaKH.setText("Xoá(F3)");
    btnXoaKH.setBounds(1300, 115, 150, 35);
    FontIcon delete = FontIcon.of(FontAwesomeSolid.TRASH_ALT, 20, Color.RED);
    btnXoaKH.setIcon(delete);
    btnXoaKH.setFocusable(false);
    btnXoaKH.setBorderPainted(false);
    btnXoaKH.setCursor(handCursor);

    btnXoaKH.setFont(new Font(Constants.MAIN_FONT, Font.PLAIN, 20));
    pnlBtn.add(btnXoaKH);

    pnlCenter.add(pnlBtn);

    pnlFilter = new JPanel();
    pnlFilter.setLayout(null);
    pnlFilter.setBounds(0, 180, 1800, 70);
    pnlFilter.setBackground(Color.decode("#E3FDFD"));
    lbllocgioitinh = new JLabel("Giới Tính ");
    lbllocgioitinh.setBounds(100, 3, 200, 20);
    lbllocgioitinh.setFont(new Font(Constants.MAIN_FONT, Font.PLAIN, 16));
    pnlFilter.add(lbllocgioitinh);

    dfBoxModelGioiTinh = new DefaultComboBoxModel<String>();
    dfBoxModelGioiTinh.addElement("");
    dfBoxModelGioiTinh.addElement("Nam");
    dfBoxModelGioiTinh.addElement("Nữ");
    comboBoxGioiTinh = new JComboBox<String>(dfBoxModelGioiTinh);
    comboBoxGioiTinh.setBounds(170, 3, 100, 25);
    comboBoxGioiTinh.setFont(new Font(Constants.MAIN_FONT, Font.PLAIN, 16));
    // comboBoxGioiTinh.setBackground(Color.decode(Constants.mainColor));
    pnlFilter.add(comboBoxGioiTinh);
    // lblBoLoc = new JLabel("Bộ lọc");
    // lblBoLoc.setForeground(Color.decode("#DC5E30"));
    // lblBoLoc.setBounds(10, 0, 200, 50);
    // lblBoLoc.setFont(new Font("Arial", Font.PLAIN, 30));
    // pnlFilter.add(lblBoLoc);
    pnlCenter.add(pnlFilter);

    btnFilter = new MyButton(15, Color.decode(Constants.BTN_FILTER_COLOR), Color.decode(Constants.BTN_FILTER_COLOR));
    btnFilter.setText("Lọc");
    FontIcon filter = FontIcon.of(FontAwesomeSolid.FILTER, 20, Color.BLACK);
    btnFilter.setIcon(filter);
    btnFilter.setBounds(300, 0, 120, 30);

    btnFilter.setFont(new Font(Constants.MAIN_FONT, Font.PLAIN, 16));
    btnFilter.setCursor(handCursor);
    btnFilter.setBorderPainted(false);
    btnFilter.setFocusPainted(false);
    pnlFilter.add(btnFilter);

    btnTimkiemKH = new MyButton(15, Color.decode(Constants.BTN_FILTER_COLOR), Color.decode(Constants.BTN_FILTER_COLOR));
    btnTimkiemKH.setBounds(1160, 0, 150, 30);
    btnTimkiemKH.setFocusable(false);
    btnTimkiemKH.setBorderPainted(false);
    FontIcon search = FontIcon.of(FontAwesomeSolid.SEARCH, 20, Color.BLACK);
    btnTimkiemKH.setIcon(search);
    btnTimkiemKH.setCursor(handCursor);

    btnTimkiemKH.setFont(new Font(Constants.MAIN_FONT, Font.PLAIN, 20));
    pnlFilter.add(btnTimkiemKH);
    txtTimkiemKH = new RoundJTextField(15);
    txtTimkiemKH.setBounds(900, 0, 250, 30);
    txtTimkiemKH.setFont(new Font(Constants.MAIN_FONT, Font.PLAIN, 17));
    pnlFilter.add(txtTimkiemKH);

    btnRefresh = new JButton();
    btnRefresh.setBounds(1350, 0, 30, 30);
    btnRefresh.setBorder(null);
    FontIcon refresh = FontIcon.of(FontAwesomeSolid.REDO, 20, Color.BLACK);
    btnRefresh.setIcon(refresh);
    btnRefresh.setToolTipText("Refresh");
    btnRefresh.setCursor(handCursor);
    btnRefresh.setBorderPainted(false);
    btnRefresh.setContentAreaFilled(false);
    btnRefresh.setFocusPainted(false);
    btnRefresh.setOpaque(false);
    pnlFilter.add(btnRefresh);

    pnlCenter.add(pnlFilter);
    pnlMainUI.add(pnlCenter);
    btnThemKH.addActionListener(this);
    btnSuaKH.addActionListener(this);
    btnXoaKH.addActionListener(this);
    btnTimkiemKH.addActionListener(this);
    btnRefresh.addActionListener(this);
    btnFilter.addActionListener(this);

  }

  private void pnlMenu() {
    pnlMenu = new JPanel();
    pnlMenu.setBounds(0, 100, 1800, 60);
    pnlMenu.setLayout(null);
    pnlMenu.setBackground(Color.decode("#71C9CE"));
    lblQuanLyKH = new JLabel("Quản lý khách hàng");
    lblQuanLyKH.setBounds(600, 0, 500, 60);
    lblQuanLyKH.setBackground(Color.WHITE);
    lblQuanLyKH.setFont(new Font(Constants.MAIN_FONT, Font.BOLD, 35));
    lblQuanLyKH.setForeground(Color.WHITE);
    btnMenu = new JButton();
    btnMenu.setCursor(handCursor);
    btnMenu.setFocusPainted(false);
    btnMenu.setBorderPainted(false);
    btnMenu.setBounds(0, 0, 60, 60);
    btnMenu.setBackground(Color.decode("#71C9CE"));
    btnMenu.setIcon(FontIcon.of(FontAwesomeSolid.BARS, 40));
    pnlMenu.add(btnMenu);
    pnlMenu.add(lblQuanLyKH);
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
    lblMenu.setBounds(100, 0, 200, 80);

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

      } else if (e.getSource() == btnQuanLyXe) {
        new QuanLyXe(taiKhoan).setVisible(true);
        this.dispose();
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

      if (e.getSource() == btnThemKH) {
        String tenKH = txtTenKH.getText();
        String diaChi = txadiaChi.getText();
        String gioiTinh = cmbGioiTinh.getSelectedItem().toString();
        String sdt = txtSdt.getText();
        String email = txtEmail.getText();

        try {
          for (int i = 0; i < tableKH.getRowCount(); i++) {
            if (tableKH.getValueAt(i, 2).toString().equals(sdt)) {
              JOptionPane.showMessageDialog(null, "Số điện thoại đã tồn tại", "Thông báo", JOptionPane.ERROR_MESSAGE);
              txtSdt.requestFocus();
              return;
            }
          }
          if (validator(sdt, diaChi, tenKH, email)) {
            String maKH = sdt.substring(6, 10) +
                LocalDate.now().toString().split("-")[0].substring(2, 4);
            KhachHang kh = new KhachHang(maKH, tenKH, sdt, gioiTinh.equals("Nam") ? true
                : false, diaChi, email, true);
            if (khachHangBus.themKhachHang(kh)) {
              JOptionPane.showMessageDialog(null, "Thêm khách hàng thành công");
              clearForm();
              loadData();
            } else {
              JOptionPane.showMessageDialog(null, "Thêm khách hàng thất bại");
            }
          }
        } catch (RemoteException e1) {
          e1.printStackTrace();
        }
      } else if (e.getSource() == btnSuaKH) {
        String tenKH = txtTenKH.getText();
        String sdt = txtSdt.getText();
        String diaChi = txadiaChi.getText();
        String gioiTinh = cmbGioiTinh.getSelectedItem().toString();

        String email = txtEmail.getText();

        KhachHang kh = new KhachHang(maKH, tenKH, sdt, gioiTinh.equals("Nam") ? true : false, diaChi, email, true);
        try {
          if (validator(sdt, diaChi, tenKH, email)) {
            if (khachHangBus.suaKhachHang(kh)) {
              JOptionPane.showMessageDialog(null, "Sửa khách hàng thành công");
              clearForm();
              loadData();
            } else {
              JOptionPane.showMessageDialog(null, "Sửa khách hàng thất bại");
            }
          }
        } catch (Exception e1) {
          e1.printStackTrace();
        }
      } else if (e.getSource() == btnXoaKH) {
        String tenKH = txtTenKH.getText();
        String sdt = txtSdt.getText();
        String diaChi = txadiaChi.getText();
        String gioiTinh = cmbGioiTinh.getSelectedItem().toString();

        String email = txtEmail.getText();

        KhachHang kh = new KhachHang(maKH, tenKH, sdt, gioiTinh.equals("Nam") ? true : false, diaChi, email, false);

        try {
          if (khachHangBus.suaKhachHang(kh)) {
            JOptionPane.showMessageDialog(null, "Xóa khách hàng thành công");
            clearForm();
            loadData();
          } else {
            JOptionPane.showMessageDialog(null, "Xóa khách hàng thất bại");
          }
        } catch (Exception e1) {
          e1.printStackTrace();
        }
      } else if (e.getSource() == btnTimkiemKH) {
        if (txtTimkiemKH.getText().equals("")) {
          JOptionPane.showMessageDialog(null, "Vui lòng nhập thông tin tìm kiếm");
        } else {
          String tenCanTim = txtTimkiemKH.getText();
          Boolean kq = false;
          for (int i = 0; i < tableKH.getRowCount(); i++) {
            Boolean result = false;
            String tenKH = tableKH.getValueAt(i, 1).toString();
            String[] arrName = tenKH.split(" ");
            for (int a = 0; a < arrName.length; a++) {
              if (arrName[a].equals(tenCanTim)) {
                result = true;
                kq = true;
              } else if (arrName[a].toLowerCase().equals(tenCanTim)) {
                result = true;
                kq = true;
              } else if (generator.boDauTrongTu(arrName[a]).equals(tenCanTim)) {
                result = true;
                kq = true;
              } else if (generator.boDauTrongTu(arrName[a]).toLowerCase().equals(tenCanTim)) {
                result = true;
                kq = true;
              }
            }
            if (tenKH.equals(tenCanTim)) {
              result = true;
              kq = true;
            }
            if (result == false) {
              tableModelKH.removeRow(i);
              i--;
            }
          }
          if (kq == false) {
            loadData();
            JOptionPane.showMessageDialog(null, "Không tìm thấy khách hàng ");
          } else if (kq == true) {
            JOptionPane.showMessageDialog(null, "Tìm thấy khách hàng ");
            txtTimkiemKH.setText("");
          }
        }
      } else if (e.getSource() == btnFilter) {
        String gioiTinhFilter = comboBoxGioiTinh.getSelectedItem().toString();
        loadData();
        if (gioiTinhFilter != "") {
          for (int i = 0; i < tableKH.getRowCount(); i++) {
            flag = false;
            if (tableKH.getValueAt(i, 3).toString().equals(gioiTinhFilter)) {
              flag = true;
            }
            if (flag == false) {
              tableModelKH.removeRow(i);
              i--;
            }
          }
        } else if (gioiTinhFilter != "") {
          for (int i = 0; i < tableKH.getRowCount(); i++) {
            if (tableModelKH.getValueAt(i, 3).equals(gioiTinhFilter)) {
            } else {
              tableModelKH.removeRow(i);
              i--;
            }
          }
        } else if (gioiTinhFilter == "") {
          loadData();
        }

      } else if (e.getSource() == btnRefresh) {
        clearForm();
        loadData();
      } else if (e.getSource() == btnIconLogout) {
        if (taiKhoanBus.logout(taiKhoan)) {
          JOptionPane.showMessageDialog(null, "Đăng xuất thành công");
          new DangNhap().setVisible(true);
          this.dispose();
        }
      }
    } catch (MalformedURLException | RemoteException | NotBoundException e1) {
      e1.printStackTrace();
    }
  }

  private void loadData() {
    tableModelKH.setRowCount(0);
    try {
      List<KhachHang> list = khachHangBus.getAllKhachHang();
      if (list != null) {
        for (KhachHang khachHang : list) {
          if (khachHang.isTrangThai())
            tableModelKH.addRow(new Object[] { khachHang.getMaKH(), khachHang.getTenKH(),
                khachHang.getSdt(), khachHang.isGioiTinh() ? "Nam" : "Nữ", khachHang.getDiaChi(),
                khachHang.getEmail() });
        }
      }
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  private boolean validator(String sdt, String diaChi, String ten, String email) throws RemoteException {
    for (int i = 0; i <= 10; i++) {
      if (khachHangBus.validator(sdt, diaChi, ten, email) == i) {
        switch (i) {
          case 1:
            JOptionPane.showMessageDialog(null, "Không được để trống họ tên", "Thông báo",
                JOptionPane.ERROR_MESSAGE);
            txtTenKH.requestFocus();
            return false;
          case 2:
            JOptionPane.showMessageDialog(null, "Không được để trống số điện thoại", "Thông báo",
                JOptionPane.ERROR_MESSAGE);
            txtSdt.requestFocus();
            return false;
          case 3:
            JOptionPane.showMessageDialog(null, "Không được để trống địa chỉ", "Thông báo",
                JOptionPane.ERROR_MESSAGE);
            txadiaChi.requestFocus();
            return false;
          case 4:
            JOptionPane.showMessageDialog(null, "Email không được để trống", "Thông báo",
                JOptionPane.ERROR_MESSAGE);
            txtEmail.requestFocus();
            return false;
          case 5:
            JOptionPane.showMessageDialog(null, "Tên không được chứa các ký tự đặc biệt", "Thông báo",
                JOptionPane.ERROR_MESSAGE);
            txtTenKH.requestFocus();
            return false;
          case 6:
            JOptionPane.showMessageDialog(null, "Số điện thoại không được chứa các ký tự đặc biệt và phải đủ 10 số",
                "Thông báo",
                JOptionPane.ERROR_MESSAGE);
            txtSdt.requestFocus();
            return false;
          case 7:
            JOptionPane.showMessageDialog(null, "Địa chỉ không được chứa các ký tự đặc biệt", "Thông báo",
                JOptionPane.ERROR_MESSAGE);
            txadiaChi.requestFocus();
            return false;
          case 8:
            JOptionPane.showMessageDialog(null, "Email không hợp lê", "Thông báo",
                JOptionPane.ERROR_MESSAGE);
            txtEmail.requestFocus();
            return false;
          case 0:
            return true;
        }
      }
    }
    return false;
  }

  public void clearForm() {
    txtTenKH.setText("");
    txtSdt.setText("");
    txtEmail.setText("");
    txadiaChi.setText("");
    cmbGioiTinh.setSelectedIndex(0);
    txtEmail.setText("");
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
}