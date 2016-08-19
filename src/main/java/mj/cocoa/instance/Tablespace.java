package mj.cocoa.instance;

/**
 * Created by poets11 on 2016. 8. 18..
 */
public class Tablespace {
    private String name;
    private int fileCount;
    private double totalSize;
    private double usedSize;
    private double freeSize;
    private double usedRatio;

    public Tablespace() {
    }

    public Tablespace(String name, int fileCount, double totalSize, double freeSize, double usedSize, double usedRatio) {
        this.name = name;
        this.fileCount = fileCount;
        this.totalSize = totalSize;
        this.usedSize = usedSize;
        this.freeSize = freeSize;
        this.usedRatio = usedRatio;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getFileCount() {
        return fileCount;
    }

    public void setFileCount(int fileCount) {
        this.fileCount = fileCount;
    }

    public double getTotalSize() {
        return totalSize;
    }

    public void setTotalSize(double totalSize) {
        this.totalSize = totalSize;
    }

    public double getUsedSize() {
        return usedSize;
    }

    public void setUsedSize(double usedSize) {
        this.usedSize = usedSize;
    }

    public double getFreeSize() {
        return freeSize;
    }

    public void setFreeSize(double freeSize) {
        this.freeSize = freeSize;
    }

    public double getUsedRatio() {
        return usedRatio;
    }

    public void setUsedRatio(double usedRatio) {
        this.usedRatio = usedRatio;
    }
}
