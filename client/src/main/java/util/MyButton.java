package util;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;

import javax.swing.JButton;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class MyButton extends JButton {
  private Color color;
  private Color borderColor;
  private int radius = 0;

  public Color getColor() {
    return color;
  }

  public void setColor(Color color) {
    this.color = color;
    setBackground(color);
  }

  public Color getBorderColor() {
    return borderColor;
  }

  public void setBorderColor(Color borderColor) {
    this.borderColor = borderColor;
  }

  public int getRadius() {
    return radius;
  }

  public void setRadius(int radius) {
    this.radius = radius;
  }

  public MyButton(int radius, Color color, Color borderColor) {
    this.radius = radius;
    setColor(color);
    setBorderColor(borderColor);
    setContentAreaFilled(false);
    addMouseListener(new ML());
  }

  public MyButton(int radius) {
    this.radius = radius;
  }

  @Override
  protected void paintComponent(Graphics grphcs) {
    Graphics2D g2 = (Graphics2D) grphcs;
    g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
    g2.setColor(borderColor);
    g2.fillRoundRect(0, 0, getWidth(), getHeight(), radius, radius);
    g2.setColor(getBackground());
    g2.fillRoundRect(2, 2, getWidth() - 4, getHeight() - 4, radius, radius);
    super.paintComponent(grphcs);
  }

  public class ML extends MouseAdapter {
    @Override
    public void mouseExited(MouseEvent me) {
      setBackground(color);
    }

    @Override
    public void mouseEntered(MouseEvent me) {
      Color newColor = new Color(0f, 0f, 0f, 0.1f);
      setBackground(newColor);
    }
  }
}

// try {
// int x = 300;
// if (e.getSource() == btnClose) {
// if (flag == true) {
// new Thread(new Runnable() {
// @Override
// public void run() {
// try {
// for (int i = 300; i > 0; i -= 2) {
// Thread.sleep(1);
// pnlSlideBar.setSize(i, 2000);

// }
// layeredPane.add(pnlMainUI, Integer.valueOf(1));
// layeredPane.remove(pnlMainSlider);
// } catch (Exception e) {
// JOptionPane.showMessageDialog(null, e);

// }
// }

// }).start();
// flag = false;
// }
// } else if (e.getSource() == btnMenu) {
// if (flag == false) {
// layeredPane.remove(pnlMainUI);
// SlideBar();
// layeredPane.add(pnlMainSlider, Integer.valueOf(2));
// pnlSlideBar.setSize(x, 2000);

// new Thread(new Runnable() {

// @Override
// public void run() {
// try {
// for (int i = 0; i < 300; i += 2) {
// Thread.sleep(1);
// pnlSlideBar.setSize(i, 2000);

// }
// } catch (Exception e) {
// JOptionPane.showMessageDialog(null, e);

// }
// }

// }).start();
// flag = true;
// }
// } else if (e.getSource() == btnNhanVien) {
// new QLNhanVien(taiKhoan).setVisible(true);
// this.dispose();
// } else if (e.getSource() == btnHopDong) {
// new QuanLyHopDong(null).setVisible(true);
// this.dispose();
// } else if (e.getSource() == btnKhachHang) {
// new QuanLyKhachHang(taiKhoan).setVisible(true);
// this.dispose();
// } else if (e.getSource() == btnQuanLyXe) {
// if (flag == true) {
// new Thread(new Runnable() {
// @Override
// public void run() {
// try {
// for (int i = 300; i > 0; i -= 2) {
// Thread.sleep(1);
// pnlSlideBar.setSize(i, 2000);

// }
// layeredPane.add(pnlMainUI, Integer.valueOf(1));
// layeredPane.remove(pnlMainSlider);
// } catch (Exception e) {
// JOptionPane.showMessageDialog(null, e);

// }
// }

// }).start();
// flag = false;
// }
// } else if (e.getSource() == btnPhuTung) {
// new QuanLyPhuTungXeVaDichVu(taiKhoan).setVisible(true);
// this.dispose();
// }
// if (e.getSource() == btnThem) {
// pnlSuaXe.removeAll();
// PnlThemXe();
// pnlCenter.add(pnlDatXe, 1);
// } else if (e.getSource() == btnSua) {
// pnlDatXe.removeAll();
// PnlSuaXe();
// pnlCenter.add(pnlSuaXe, 1);

// }
// if (e.getSource() == btnChonAnh) {
// String defaultPath = "G:/phan-tan/showroom/client/assets/images/loai2";
// JFileChooser fileChooser = new JFileChooser(defaultPath);
// fileChooser.setDialogTitle("Chọn ảnh");
// fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
// fileChooser.setAcceptAllFileFilterUsed(false);
// FileNameExtensionFilter filter = new FileNameExtensionFilter("PNG, JPG,
// JPEG", "png", "jpg", "jpeg");
// fileChooser.addChoosableFileFilter(filter);
// int result = fileChooser.showOpenDialog(null);
// if (result == JFileChooser.APPROVE_OPTION) {
// File selectedFile = fileChooser.getSelectedFile();
// String path = selectedFile.getAbsolutePath();
// try {
// anh = new ImageIcon(ImageIO.read(new File(path)).getScaledInstance(330, 170,
// Image.SCALE_SMOOTH));
// btnHinhAnh.setIcon(anh);
// } catch (IOException e1) {
// e1.printStackTrace();
// }
// }
// }
// for (int i = 0; i < pnlDsXe.getComponentCount(); i++) {
// if (pnlDsXe.getComponent(i) instanceof JButton) {
// JButton btn = (JButton) pnlDsXe.getComponent(i);
// if (e.getSource() == btn) {
// for (Xe xe : listXe) {
// if (xe.getMaXe().equals(btn.getText())) {
// new ChiTietXe(xe).setVisible(true);
// }
// }
// }
// }
// }

// } catch (MalformedURLException | RemoteException | NotBoundException e1) {
// e1.printStackTrace();
// }
