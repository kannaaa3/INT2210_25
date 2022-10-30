import edu.princeton.cs.algs4.Picture;
import edu.princeton.cs.algs4.Stack;

public class SeamCarver {
    private static final double BORDER = 1000;

    private int[][]    pxl;
    private double[][] energyPic;

    private int width;
    private int height;

    // create a seam carver object based on the given picture
    public SeamCarver(Picture picture) {
        if (picture == null) throw new IllegalArgumentException();

        width = picture.width();
        height = picture.height();

        pxl = new int[width][height];
        for (int x = 0; x < width; x++)
            for (int y = 0; y < height; y++)
                pxl[x][y] = picture.getRGB(x, y);

        calcEnergy();
    }

    // current picture
    public Picture picture() {
        Picture pic = new Picture(width, height);
        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                pic.setRGB(x, y, pxl[x][y]);
            }
        }
        return pic;
    }

    // width of current picture
    public int width() {
        return width;
    }

    // height of current picture
    public int height() {
        return height;
    }

    private void calcEnergy() {
        energyPic = new double[width][height];
        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                if (x == 0 || x == width - 1 || y == 0 || y == height - 1) {
                    energyPic[x][y] = BORDER;
                    continue;
                }
                double dX = delta(pxl[x - 1][y], pxl[x + 1][y]);
                double dY = delta(pxl[x][y - 1], pxl[x][y + 1]);
                energyPic[x][y] = Math.sqrt(dX + dY);
            }
        }
    }

    // energy of pixel at column x and row y
    public double energy(int x, int y) {
        if (x < 0 || x >= width || y < 0 || y >= height) throw new IllegalArgumentException();
        return energyPic[x][y];
    }

    private double delta(int fi, int se) {
        double r = getColor(fi, 16) - getColor(se, 16);
        double g = getColor(fi, 8) - getColor(se, 8);
        double b = getColor(fi, 0) - getColor(se, 0);

        return r * r + g * g + b * b;
    }

    private int getColor(int rgb, int color) {
        return (rgb >> color) & 0xFF;
    }


    // sequence of indices for horizontal seam
    public int[] findHorizontalSeam() {
        return findSeam(width, height, energyPic);
    }

    // sequence of indices for vertical seam
    public int[] findVerticalSeam() {
        return findSeam(height, width, flip2D(height, width, energyPic));
    }

    private int[] findSeam(int W, int H, double[][] energyAr) {
        double[][] dp     = new double[W][H];
        int[][]    trace  = new int[W][H];
        int        lastId = 0;

//        for (double[] i : energyAr) {
//            for (double j : i) System.out.print(j + "-");
//            System.out.println();
//        }

        for (int j = 0; j < H; j++)
            dp[0][j] = BORDER;

        for (int i = 1; i < W; i++) {
            for (int j = 0; j < H; j++) {
                trace[i][j] = j;
                for (int prev = Math.max(0, j - 1); prev <= Math.min(j + 1, H - 1); prev++) {
                    if (dp[i - 1][prev] < dp[i - 1][trace[i][j]])
                        trace[i][j] = prev;
                }
                dp[i][j] = dp[i - 1][trace[i][j]] + energyAr[i][j];
                if (i == W - 1 && dp[i][j] < dp[i][lastId]) lastId = j;
            }
        }

        Stack<Integer> s = new Stack<>();
        for (int i = W - 1; i >= 0; i--) {
            s.push(lastId);
            lastId = trace[i][lastId];
        }

        int[] seam = new int[W];
        for (int i = 0; i < W; i++) seam[i] = s.pop();

        return seam;
    }

    private static double[][] flip2D(int w, int h, double[][] ar) { // new size as args
        double[][] newAr = new double[w][h];
        for (int x = 0; x < w; x++) {
            for (int y = 0; y < h; y++) {
                newAr[x][y] = ar[y][x];
            }
        }
        return newAr;
    }

    // remove horizontal seam from current picture
    public void removeHorizontalSeam(int[] seam) {
        validate(width, height, seam);

        height--;
        int[][] newPxl = new int[width][height];

        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                newPxl[x][y] = y < seam[x] ? pxl[x][y] : pxl[x][y + 1];
            }
        }
        pxl = newPxl;
        calcEnergy();
    }

    // remove vertical seam from current picture
    public void removeVerticalSeam(int[] seam) {
        validate(height, width, seam);

        width--;
        int[][] newPxl = new int[width][height];

        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                newPxl[x][y] = x < seam[y] ? pxl[x][y] : pxl[x + 1][y];
            }
        }
        pxl = newPxl;
        calcEnergy();
    }

    private void validate(int width, int height, int[] seam) {
        if (seam == null) throw new IllegalArgumentException();

        if (height <= 1) throw new IllegalArgumentException("Can't do it anymore.");
        if (seam.length != width) throw new IllegalArgumentException("Invalid seam length!");

        for (int i = 0; i < seam.length; i++) {
            if (seam[i] < 0 || seam[i] >= height) throw new IllegalArgumentException();
            if (i < seam.length - 1 && Math.abs(seam[i] - seam[i + 1]) > 1) throw new IllegalArgumentException();
        }
    }

}