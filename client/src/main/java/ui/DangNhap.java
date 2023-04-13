package ui;

import static util.Constants.MAIN_FONT;
import static util.Constants.TITLE;
import static util.Constants.TITLE_COLOR;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Image;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.net.MalformedURLException;
import java.net.URL;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;

import javax.swing.AbstractAction;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.KeyStroke;

import org.kordamp.ikonli.fontawesome5.FontAwesomeRegular;
import org.kordamp.ikonli.swing.FontIcon;

import bus.ITaiKhoanBus;
import util.Constants;
import util.JPictureBox;
import util.MyButton;
import util.RoundJTextField;
import util.RoundedBorderWithColor;

public class DangNhap extends JFrame implements ActionListener {
  private JLabel lbTaiKhoan;
  private JLabel lblMatKhau;
  private JPasswordField txtMatKhau;
  private JTextField txtTaiKhoan;
  private JButton btnDangNhap, btnHienMatKhau;
  private JLabel lbTieuDe;
  private Cursor handleCursor;
  private JTextField txtMK;
  private JPanel pnlChinh;
  private boolean flag = false;
  ITaiKhoanBus taiKhoanBus = (ITaiKhoanBus) Naming.lookup("rmi://localhost:8080/taiKhoan");
  URL url = new URL(Constants.ICON_LOGO);
  Image imageIcon = new ImageIcon(url).getImage().getScaledInstance(200, 150, Image.SCALE_SMOOTH);

  public DangNhap() throws MalformedURLException, RemoteException, NotBoundException {

    gui();
  }

  private void gui() {
    this.setTitle("Đăng nhập");
    this.setDefaultCloseOperation(EXIT_ON_CLOSE);
    this.setMinimumSize(new Dimension(900, 550));
    this.setLocationRelativeTo(null);
    this.setResizable(false);

    this.setIconImage(imageIcon);
    this.setLayout(null);
    handleCursor = new Cursor(Cursor.HAND_CURSOR);
    pnlChinh = new JPanel();
    pnlChinh.setLayout(null);
    pnlChinh.setBounds(0, 0, 900, 600);
    pnlChinh.setBackground(Color.decode("#CBF1F5"));
    JPictureBox pnlAnh = new JPictureBox();
    pnlAnh.setIcon(new ImageIcon(imageIcon));
    pnlAnh.setBounds(30, 30, 200, 150);
    pnlChinh.add(pnlAnh);

    pnlChinh.add(lbTieuDe = new JLabel(TITLE));
    Font f1 = new Font(MAIN_FONT, Font.BOLD, 70);
    lbTieuDe.setFont(f1);
    lbTieuDe.setForeground(Color.decode(TITLE_COLOR));
    lbTieuDe.setBounds(350, 20, 600, 150);

    pnlChinh.add(lbTaiKhoan = new JLabel("Tài khoản"));
    Font f2 = new Font(MAIN_FONT, 0, 25);
    lbTaiKhoan.setFont(f2);
    lbTaiKhoan.setBounds(70, 210, 200, 30);

    txtTaiKhoan = new RoundJTextField(15);
    txtTaiKhoan.setBounds(220, 200, 550, 50);
    txtTaiKhoan.setFont(f2);
    txtTaiKhoan.requestFocus();
    txtTaiKhoan.setBorder(new RoundedBorderWithColor(Color.decode("#CBF1F5"), 2, 15));
    pnlChinh.add(txtTaiKhoan);

    pnlChinh.add(lblMatKhau = new JLabel("Mật khẩu"));
    lblMatKhau.setFont(f2);
    lblMatKhau.setBounds(70, 310, 200, 30);

    pnlChinh.add(txtMatKhau = new JPasswordField());
    txtMatKhau.setBounds(220, 300, 550, 50);
    txtMatKhau.setFont(f2);
    txtMatKhau.setBorder(new RoundedBorderWithColor(Color.decode("#CBF1F5"), 2, 15));

    txtMK = new RoundJTextField(15);
    txtMK.setBounds(220, 300, 550, 50);
    txtMK.setFont(f2);
    txtMK.setBorder(new RoundedBorderWithColor(Color.decode("#CBF1F5"), 2, 15));

    btnDangNhap = new MyButton(20, Color.decode("#71C9CE"), Color.decode("#71C9CE"));
    btnDangNhap.setText("Đăng nhập");
    btnDangNhap.setFocusPainted(false);
    btnDangNhap.setBorderPainted(false);
    btnDangNhap.setCursor(handleCursor);
    btnDangNhap.setBounds(340, 400, 250, 40);
    btnDangNhap.setFont(f2);
    pnlChinh.add(btnDangNhap);
    btnDangNhap.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(KeyEvent.VK_ENTER, 0),
        "btnDangNhap");
    btnDangNhap.getActionMap().put("btnDangNhap", new AbstractAction() {
      @Override
      public void actionPerformed(ActionEvent e) {
        btnDangNhap.doClick();
      }
    });

    JLabel mark = new JLabel("© 2022 - 2023 - Bản quyền thuộc về nhóm 28");
    mark.setFont(new Font(MAIN_FONT, 0, 15));
    mark.setBounds(320, 460, 500, 30);
    pnlChinh.add(mark);

    JLabel start = new JLabel("* Tài khoản: admin, password: 1111");
    start.setFont(new Font(MAIN_FONT, Font.ITALIC, 15));
    start.setForeground(Color.RED);
    start.setBounds(350, 490, 500, 20);
    pnlChinh.add(start);

    pnlChinh.add(btnHienMatKhau = new JButton(""));
    btnHienMatKhau.setBounds(790, 300, 50, 50);
    btnHienMatKhau.setBackground(null);
    btnHienMatKhau.setBorderPainted(false);
    btnHienMatKhau.setContentAreaFilled(false);
    btnHienMatKhau.setFocusPainted(false);
    btnHienMatKhau.setOpaque(false);
    btnHienMatKhau.setToolTipText("Hiển thị mật khẩu");
    btnHienMatKhau.setCursor(handleCursor);
    FontIcon showPassword = FontIcon.of(FontAwesomeRegular.EYE, 25);
    btnHienMatKhau.setIcon(showPassword);

    this.add(pnlChinh);

    btnDangNhap.addActionListener(this);
    btnHienMatKhau.addActionListener(this);

  }

  public void actionPerformed(ActionEvent arg0) {
    Object o = arg0.getSource();
    try {
      if (o.equals(btnDangNhap)) {
        String userName = txtTaiKhoan.getText();
        String password = String.valueOf(txtMatKhau.getPassword());
        if (taiKhoanBus.login(userName, password) != null) {
          JOptionPane.showMessageDialog(null, "Đăng nhập thành công", "Thông báo", JOptionPane.INFORMATION_MESSAGE);
          this.dispose();
          new QuanLyXe(taiKhoanBus.login(userName, password)).setVisible(true);
        } else {
          JOptionPane.showMessageDialog(null, "Sai tên đăng nhập hoặc mật khẩu");
        }

      } else if (o.equals(btnHienMatKhau)) {
        if (flag == false) {
          String password = String.valueOf(txtMatKhau.getPassword());
          FontIcon hiddenPass = FontIcon.of(FontAwesomeRegular.EYE_SLASH, 25);
          btnHienMatKhau.setIcon(hiddenPass);
          pnlChinh.remove(txtMatKhau);
          pnlChinh.add(txtMK);
          txtMK.setText(password);
          btnHienMatKhau.setToolTipText("Ẩn mật khẩu");
          flag = true;
        } else {
          String password = String.valueOf(txtMK.getText());

          FontIcon showPassword = FontIcon.of(FontAwesomeRegular.EYE, 25);
          btnHienMatKhau.setIcon(showPassword);
          pnlChinh.remove(txtMK);
          pnlChinh.add(txtMatKhau);
          btnHienMatKhau.setToolTipText("Hiển thị mật khẩu");
          txtMatKhau.setText(password);
          flag = false;
        }
      }
    } catch (MalformedURLException | RemoteException | NotBoundException e) {
      e.printStackTrace();
    }

  }
}