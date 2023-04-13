package ui;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Font;
import java.awt.event.KeyEvent;
import java.net.URL;
import java.awt.Frame;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.WindowEvent;
import java.awt.event.ActionListener;
import java.net.MalformedURLException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;

import javax.swing.AbstractAction;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;
import javax.swing.JPanel;
import javax.swing.KeyStroke;

import entities.TaiKhoan;
import entities.Xe;
import util.Constants;
import util.Generator;
import util.MyButton;

public class ChiTietXe extends JFrame implements ActionListener {

  JLayeredPane layeredPane;
  boolean flag = false;
  private Cursor handCursor;

  private JLabel lblTenXe, lblTitleThuongHieu, lblTitleLoaiXe, lblTitleMauSac, lblTitleGiaTien, lblTitleSoGhe,
      lblThuongHieu, lblLoaiXe, lblMauSac, lblGiaTien, lblSoGhe, lblHinhAnh;

  private JPanel pnlHDXe;
  private JButton btnDatXe, btnHuyDatXe;

  private Xe xe = new Xe();
  private Generator generator = new Generator();
  private TaiKhoan taiKhoan = new TaiKhoan();
  URL url = new URL(Constants.ICON_LOGO);
  Image imageIcon = new ImageIcon(url).getImage().getScaledInstance(200, 150, Image.SCALE_SMOOTH);

  public ChiTietXe(TaiKhoan taiKhoan, Xe x) throws MalformedURLException {
    this.xe = x;
    this.taiKhoan = taiKhoan;
    setTitle(Constants.APP_NAME);
    this.setIconImage(imageIcon);
    setSize(920, 550);
    setResizable(false);
    setLocationRelativeTo(null);
    this.setBackground(Color.WHITE);
    setLayout(null);
    layeredPane = new JLayeredPane();
    layeredPane.setBounds(0, 0, 2000, 1500);
    add(layeredPane);
    handCursor = new Cursor(Cursor.HAND_CURSOR);

    XemChiTietXe();
    btnHuyDatXe.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0),
        "btnHuyDatXe");
    btnHuyDatXe.getActionMap().put("btnHuyDatXe", new AbstractAction() {
      @Override
      public void actionPerformed(ActionEvent e) {
        btnHuyDatXe.doClick();
      }
    });
  }

  void XemChiTietXe() {
    pnlHDXe = new JPanel();
    pnlHDXe.setLayout(null);
    pnlHDXe.setBounds(0, 0, 920, 600);
    pnlHDXe.setBackground(Color.WHITE);
    lblTenXe = new JLabel(xe.getTenXe());
    lblTenXe.setBounds(200, 40, 500, 50);
    lblTenXe.setHorizontalAlignment(JLabel.CENTER);
    lblTenXe.setFont(new Font(Constants.MAIN_FONT, Font.BOLD, 50));
    lblTenXe.setForeground(Color.decode(Constants.SECOND_TEXT_COLOR));
    pnlHDXe.add(lblTenXe);

    lblHinhAnh = new JLabel();
    lblHinhAnh.setBounds(50, 120, 480, 400);
    lblHinhAnh.setIcon(
        new ImageIcon(
            new ImageIcon(xe.getHinhAnh()).getImage().getScaledInstance(480, 270,
                Image.SCALE_SMOOTH)));
    pnlHDXe.add(lblHinhAnh);

    lblTitleThuongHieu = new JLabel("Thương hiệu:");
    lblTitleThuongHieu.setBounds(570, 120, 200, 30);
    lblTitleThuongHieu.setFont(new Font(Constants.MAIN_FONT, Font.BOLD, 20));
    lblTitleThuongHieu.setForeground(Color.decode(Constants.SECOND_TEXT_COLOR));
    pnlHDXe.add(lblTitleThuongHieu);
    lblThuongHieu = new JLabel(generator.convertTHToString(xe.getThuongHieu()));
    lblThuongHieu.setBounds(690, 120, 200, 30);
    lblThuongHieu.setHorizontalAlignment(JLabel.CENTER);
    lblThuongHieu.setFont(new Font(Constants.MAIN_FONT, Font.PLAIN, 20));
    lblThuongHieu.setForeground(Color.decode(Constants.SECOND_TEXT_COLOR));
    pnlHDXe.add(lblThuongHieu);

    lblTitleLoaiXe = new JLabel("Loại xe:");
    lblTitleLoaiXe.setBounds(570, 170, 200, 30);
    lblTitleLoaiXe.setFont(new Font(Constants.MAIN_FONT, Font.BOLD, 20));
    lblTitleLoaiXe.setForeground(Color.decode(Constants.SECOND_TEXT_COLOR));
    pnlHDXe.add(lblTitleLoaiXe);
    lblLoaiXe = new JLabel(generator.convertLoaiXeToString(xe.getLoaiXe()));
    lblLoaiXe.setBounds(690, 170, 200, 30);
    lblLoaiXe.setHorizontalAlignment(JLabel.CENTER);
    lblLoaiXe.setFont(new Font(Constants.MAIN_FONT, Font.PLAIN, 20));
    lblLoaiXe.setForeground(Color.decode(Constants.SECOND_TEXT_COLOR));
    pnlHDXe.add(lblLoaiXe);

    lblTitleMauSac = new JLabel("Màu sắc:");
    lblTitleMauSac.setBounds(570, 220, 200, 30);
    lblTitleMauSac.setFont(new Font(Constants.MAIN_FONT, Font.BOLD, 20));
    lblTitleMauSac.setForeground(Color.decode(Constants.SECOND_TEXT_COLOR));
    pnlHDXe.add(lblTitleMauSac);
    lblMauSac = new JLabel(xe.getMauSac());
    lblMauSac.setBounds(690, 220, 200, 30);
    lblMauSac.setHorizontalAlignment(JLabel.CENTER);
    lblMauSac.setFont(new Font(Constants.MAIN_FONT, Font.PLAIN, 20));
    lblMauSac.setForeground(Color.decode(Constants.SECOND_TEXT_COLOR));
    pnlHDXe.add(lblMauSac);

    lblTitleGiaTien = new JLabel("Giá tiền:");
    lblTitleGiaTien.setBounds(570, 270, 200, 30);
    lblTitleGiaTien.setFont(new Font(Constants.MAIN_FONT, Font.BOLD, 20));
    lblTitleGiaTien.setForeground(Color.decode(Constants.SECOND_TEXT_COLOR));
    pnlHDXe.add(lblTitleGiaTien);
    lblGiaTien = new JLabel(xe.getGiaTien() + " VNĐ");
    lblGiaTien.setBounds(690, 270, 200, 30);
    lblGiaTien.setHorizontalAlignment(JLabel.CENTER);
    lblGiaTien.setFont(new Font(Constants.MAIN_FONT, Font.PLAIN, 20));
    lblGiaTien.setForeground(Color.decode(Constants.SECOND_TEXT_COLOR));
    pnlHDXe.add(lblGiaTien);

    lblTitleSoGhe = new JLabel("Số ghế:");
    lblTitleSoGhe.setBounds(570, 320, 200, 30);
    lblTitleSoGhe.setFont(new Font(Constants.MAIN_FONT, Font.BOLD, 20));
    lblTitleSoGhe.setForeground(Color.decode(Constants.SECOND_TEXT_COLOR));
    pnlHDXe.add(lblTitleSoGhe);
    lblSoGhe = new JLabel(xe.getSoGhe() + "");
    lblSoGhe.setBounds(690, 320, 200, 30);
    lblSoGhe.setHorizontalAlignment(JLabel.CENTER);
    lblSoGhe.setFont(new Font(Constants.MAIN_FONT, Font.PLAIN, 20));
    lblSoGhe.setForeground(Color.decode(Constants.SECOND_TEXT_COLOR));
    pnlHDXe.add(lblSoGhe);

    btnDatXe = new MyButton(15, Color.GREEN, Color.GREEN);
    btnDatXe.setText("Đặt xe");
    btnDatXe.setBounds(570, 400, 100, 50);
    btnDatXe.setCursor(handCursor);
    btnDatXe.setFont(new Font(Constants.MAIN_FONT, Font.BOLD, 20));

    btnDatXe.setFocusPainted(false);
    btnDatXe.setBorderPainted(false);
    btnDatXe.addActionListener(this);
    pnlHDXe.add(btnDatXe);

    btnHuyDatXe = new MyButton(15, Color.decode("#DC5F00"), Color.decode("#DC5F00"));
    btnHuyDatXe.setText("Hủy đặt xe");
    btnHuyDatXe.setBounds(700, 400, 150, 50);
    btnHuyDatXe.setCursor(handCursor);
    btnHuyDatXe.setFont(new Font(Constants.MAIN_FONT, Font.BOLD, 20));
    btnHuyDatXe.setForeground(Color.decode(Constants.MAIN_TEXT_COLOR));
    btnHuyDatXe.setBackground(Color.decode("#DC5F00"));
    btnHuyDatXe.setFocusPainted(false);
    btnHuyDatXe.setBorderPainted(false);
    btnHuyDatXe.addActionListener(this);
    pnlHDXe.add(btnHuyDatXe);

    layeredPane.add(pnlHDXe, 2);
  }

  @Override
  public void actionPerformed(ActionEvent e) {
    try {
      if (e.getSource() == btnDatXe) {
        for (Frame frame : Frame.getFrames()) {
          frame.dispose();
        }
        new QuanLyHopDong(taiKhoan, xe).setVisible(true);
        dispatchEvent(new WindowEvent(this, WindowEvent.WINDOW_CLOSING));
      } else if (e.getSource() == btnHuyDatXe) {

        this.dispose();
      }
    } catch (MalformedURLException | RemoteException | NotBoundException e1) {
      e1.printStackTrace();
    }
  }
}
