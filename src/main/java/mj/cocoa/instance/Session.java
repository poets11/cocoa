package mj.cocoa.instance;

/**
 * Created by poets11 on 2016. 8. 18..
 */
public class Session {
    private int activeCount;
    private int totalCount;
    private int lockedCount;

    public Session(int activeCount, int totalCount, int lockedCount) {
        this.activeCount = activeCount;
        this.totalCount = totalCount;
        this.lockedCount = lockedCount;
    }

    public int getActiveCount() {
        return activeCount;
    }

    public void setActiveCount(int activeCount) {
        this.activeCount = activeCount;
    }

    public int getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(int totalCount) {
        this.totalCount = totalCount;
    }

    public int getLockedCount() {
        return lockedCount;
    }

    public void setLockedCount(int lockedCount) {
        this.lockedCount = lockedCount;
    }
}
