/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package manifest;

/**
 *
 * @author ongzy-001PC
 */
public class SupportScreen {
    private boolean resizable = false;
    private boolean smallScreens = false;
    private boolean normalScreens = false;
    private boolean largeScreens = false;
    private boolean xLargeSCreens = false;
    private boolean anyDensity = false;
    private int requiresSmallestWidthDp = 0;
    private int compatibleWidthLimitDp = 0;
    private int largestWidthLimitDp = 0;

    /**
     * @return the resizable
     */
    public boolean isResizable() {
        return resizable;
    }

    /**
     * @param resizable the resizable to set
     */
    public void setResizable(boolean resizable) {
        this.resizable = resizable;
    }

    /**
     * @return the smallScreens
     */
    public boolean isSmallScreens() {
        return smallScreens;
    }

    /**
     * @param smallScreens the smallScreens to set
     */
    public void setSmallScreens(boolean smallScreens) {
        this.smallScreens = smallScreens;
    }

    /**
     * @return the normalScreens
     */
    public boolean isNormalScreens() {
        return normalScreens;
    }

    /**
     * @param normalScreens the normalScreens to set
     */
    public void setNormalScreens(boolean normalScreens) {
        this.normalScreens = normalScreens;
    }

    /**
     * @return the largeScreens
     */
    public boolean isLargeScreens() {
        return largeScreens;
    }

    /**
     * @param largeScreens the largeScreens to set
     */
    public void setLargeScreens(boolean largeScreens) {
        this.largeScreens = largeScreens;
    }

    /**
     * @return the xLargeSCreens
     */
    public boolean isxLargeSCreens() {
        return xLargeSCreens;
    }

    /**
     * @param xLargeSCreens the xLargeSCreens to set
     */
    public void setxLargeSCreens(boolean xLargeSCreens) {
        this.xLargeSCreens = xLargeSCreens;
    }

    /**
     * @return the anyDensity
     */
    public boolean isAnyDensity() {
        return anyDensity;
    }

    /**
     * @param anyDensity the anyDensity to set
     */
    public void setAnyDensity(boolean anyDensity) {
        this.anyDensity = anyDensity;
    }

    /**
     * @return the requiresSmallestWidthDp
     */
    public int getRequiresSmallestWidthDp() {
        return requiresSmallestWidthDp;
    }

    /**
     * @param requiresSmallestWidthDp the requiresSmallestWidthDp to set
     */
    public void setRequiresSmallestWidthDp(int requiresSmallestWidthDp) {
        this.requiresSmallestWidthDp = requiresSmallestWidthDp;
    }

    /**
     * @return the compatibleWidthLimitDp
     */
    public int getCompatibleWidthLimitDp() {
        return compatibleWidthLimitDp;
    }

    /**
     * @param compatibleWidthLimitDp the compatibleWidthLimitDp to set
     */
    public void setCompatibleWidthLimitDp(int compatibleWidthLimitDp) {
        this.compatibleWidthLimitDp = compatibleWidthLimitDp;
    }

    /**
     * @return the largestWidthLimitDp
     */
    public int getLargestWidthLimitDp() {
        return largestWidthLimitDp;
    }

    /**
     * @param largestWidthLimitDp the largestWidthLimitDp to set
     */
    public void setLargestWidthLimitDp(int largestWidthLimitDp) {
        this.largestWidthLimitDp = largestWidthLimitDp;
    }
}
