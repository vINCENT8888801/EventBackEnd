package com.fyp.eventBackend;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;


import com.fyp.eventBackend.WiseAPIResponseClass.FaceLocate;

public class DrawImageUtil {
	
	public static BufferedImage drawBoundingBox(BufferedImage img,FaceLocate location) {
		Graphics2D g2d = img.createGraphics();
		g2d.setColor(Color.RED);
		g2d.setStroke(new BasicStroke(2));
		g2d.drawRect(location.getLeft(), location.getTop(), location.getWidth(), location.getHeight());
		g2d.dispose();
		return img;
	}
}
