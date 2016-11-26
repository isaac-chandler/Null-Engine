package imageConverter;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.awt.image.BufferedImage;
import java.awt.image.ColorModel;
import java.awt.image.WritableRaster;
import java.io.File;
import java.io.IOException;

public class Main {

	private static JFileChooser chooser;

	private static final String[] extensions = new String[] {
			"jpg", "jpeg", "jpe", "jif", "jfif", "jfi",
			"bmp", "dib",
			"wbmp",
			"gif",
			"png"};

	public static void main(String[] args) {
		SwingUtilities.invokeLater(Main::createUI);
	}

	private static BufferedImage deepCopy(BufferedImage bi) {
		ColorModel cm = bi.getColorModel();
		boolean isAlphaPremultiplied = cm.isAlphaPremultiplied();
		WritableRaster raster = bi.copyData(null);
		return new BufferedImage(cm, raster, isAlphaPremultiplied, null);
	}

	private static JCheckBoxMenuItem gammaCorrectionItem;
	private static BufferedImage original;
	private static ImagePanel image;
	private static JFrame frame;

	private static void createUI() {
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (Exception e) {
			e.printStackTrace();
		}

		chooser = new JFileChooser("./Game Engine/res/res/textures/default");
		chooser.setMultiSelectionEnabled(false);

		frame = new JFrame("Image Editor");

		image = new ImagePanel();
		frame.add(image);

		JMenuBar menuBar = new JMenuBar();
		JMenu fileMenu = new JMenu("File");
		fileMenu.setMnemonic(KeyEvent.VK_F);
		menuBar.add(fileMenu);

		JMenuItem saveItem = new JMenuItem("Save");
		saveItem.setMnemonic(KeyEvent.VK_S);
		saveItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_S, ActionEvent.CTRL_MASK));
		saveItem.setEnabled(false);
		saveItem.addActionListener(e -> SwingUtilities.invokeLater(() -> {
			chooser.setFileFilter(new FileNameExtensionFilter("PNG Files", "png"));
			chooser.setMultiSelectionEnabled(false);
			if (chooser.showSaveDialog(null) == JFileChooser.APPROVE_OPTION) {
				File file = chooser.getSelectedFile();
				try {
					ImageIO.write(original, "PNG", file);
				} catch (IOException e1) {
					e1.printStackTrace();
					JOptionPane.showMessageDialog(null, "Failed to save image", "Image Failed", JOptionPane.ERROR_MESSAGE);
				}
			}
		}));

		fileMenu.add(saveItem);

		JMenuItem openItem = new JMenuItem("Open");
		openItem.setMnemonic(KeyEvent.VK_O);
		openItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_O, ActionEvent.CTRL_MASK));
		openItem.addActionListener(e -> SwingUtilities.invokeLater(() -> {
			chooser.setFileFilter(new FileNameExtensionFilter("Image Files", extensions));
			if (chooser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {
				File file = chooser.getSelectedFile();
				try {
					BufferedImage img = ImageIO.read(file);
					original = img;
					if (gammaCorrectionItem.getState())
						image.image = pow(original, 1 / 2.2);
					else
						image.image = deepCopy(original);
					image.scale = 1;
					image.offsetX = 0;
					image.offsetY = 0;
					saveItem.setEnabled(true);
					frame.repaint();
				} catch (IOException e1) {
					e1.printStackTrace();
					JOptionPane.showMessageDialog(null, "Failed to load image", "Image Failed", JOptionPane.ERROR_MESSAGE);
				}
			}
		}));

		fileMenu.add(openItem);

		JMenu gammaMenu = new JMenu("Gammma");
		gammaMenu.setMnemonic(KeyEvent.VK_G);
		menuBar.add(gammaMenu);

		gammaCorrectionItem = new JCheckBoxMenuItem("Gamma correction", true);
		gammaCorrectionItem.setMnemonic(KeyEvent.VK_G);
		gammaCorrectionItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_G, ActionEvent.CTRL_MASK));
		gammaCorrectionItem.addActionListener(e -> SwingUtilities.invokeLater(Main::gammaCorrection));

		gammaMenu.add(gammaCorrectionItem);

		gammaMenu.addSeparator();

		JMenuItem convertToLinearItem = new JMenuItem("Convert to linear");
		convertToLinearItem.setMnemonic(KeyEvent.VK_L);
		convertToLinearItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_L, ActionEvent.ALT_MASK));
		convertToLinearItem.addActionListener(e -> SwingUtilities.invokeLater(() -> {
			original = pow(original, 2.2);
			gammaCorrection();
		}));

		gammaMenu.add(convertToLinearItem);

		JMenuItem convertToNonlinearItem = new JMenuItem("Convert to Non-linear");
		convertToNonlinearItem.setMnemonic(KeyEvent.VK_N);
		convertToNonlinearItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_N, ActionEvent.ALT_MASK));
		convertToNonlinearItem.addActionListener(e -> SwingUtilities.invokeLater(() -> {
			original = pow(original, 1 / 2.2);
			gammaCorrection();
		}));

		gammaMenu.add(convertToNonlinearItem);

		frame.setJMenuBar(menuBar);
		frame.pack();
		frame.setLocationRelativeTo(null);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);
	}

	private static class ImagePanel extends JPanel {
		public int scale = 1;
		public int offsetX = 0, offsetY = 0;
		public BufferedImage image;

		private int lastX, lastY;

		public ImagePanel() {
			setBorder(BorderFactory.createLineBorder(Color.black));

			addMouseWheelListener(e -> {
				if (image != null) {
					scale -= e.getWheelRotation();
					if (scale < 1)
						scale = 1;

					if (offsetX >= image.getWidth() * scale)
						offsetX = image.getWidth() * scale - 1;
					if (offsetY >= image.getHeight() * scale)
						offsetY = image.getHeight() * scale - 1;

					repaint();
				}
			});

			addMouseListener(new MouseAdapter() {
				@Override
				public void mousePressed(MouseEvent e) {
					lastX = e.getX();
					lastY = e.getY();
				}
			});

			addMouseMotionListener(new MouseMotionAdapter() {
				@Override
				public void mouseDragged(MouseEvent e) {
					if (image != null) {
						offsetX -= e.getX() - lastX;
						offsetY -= e.getY() - lastY;
						lastX = e.getX();
						lastY = e.getY();

						if (offsetX < 0)
							offsetX = 0;
						else if (offsetX >= image.getWidth() * scale)
							offsetX = image.getWidth() * scale - 1;
						if (offsetY < 0)
							offsetY = 0;
						else if (offsetY >= image.getHeight() * scale)
							offsetY = image.getHeight() * scale - 1;

						repaint();
					}
				}
			});
		}

		@Override
		public Dimension getMinimumSize() {
			return new Dimension(1280, 720);
		}

		@Override
		public Dimension getPreferredSize() {
			return new Dimension(1280, 720);
		}

		@Override
		protected void paintComponent(Graphics g) {
			super.paintComponent(g);

			g.setColor(Color.black);
			g.fillRect(0, 0, getWidth(), getHeight());
			if (image != null)
				g.drawImage(image, -offsetX, -offsetY, image.getWidth() * scale, image.getHeight() * scale, null);
		}
	}

	private static BufferedImage pow(BufferedImage img, double power) {
		BufferedImage result = deepCopy(img);
		for (int y = 0; y < img.getHeight(); y++) {
			for (int x = 0; x < img.getWidth(); x++) {
				result.setRGB(x, y, pow(img.getRGB(x, y), power));
			}
		}
		return result;
	}

	private static int pow(int rgb, double power) {
		byte a = (byte) (rgb >> 24);
		double r = ((rgb >> 16) & 0xFF) / 255d;
		double g = ((rgb >> 8) & 0xFF) / 255d;
		double b = (rgb & 0xFF) / 255d;
		r = Math.pow(r, power);
		g = Math.pow(g, power);
		b = Math.pow(b, power);
		return (a << 24) | (((int) (r * 255)) << 16) | (((int) (g * 255)) << 8) | ((int) (b * 255));
	}

	private static void gammaCorrection() {
		if (gammaCorrectionItem.getState())
			image.image = pow(original, 1 / 2.2);
		else
			image.image = deepCopy(original);
		frame.repaint();
	}
}
