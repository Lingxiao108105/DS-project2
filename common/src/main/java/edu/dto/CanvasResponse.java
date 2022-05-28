package edu.dto;

/**
 * response for getting canvas from server
 * @author lingxiao
 */
public class CanvasResponse {

    boolean isSuccess;
    //snapshot
    byte[] imageBytes;
    //index of snapshot
    long snapshotIndex;

    public CanvasResponse(boolean isSuccess, byte[] imageBytes, long snapshotIndex) {
        this.isSuccess = isSuccess;
        this.imageBytes = imageBytes;
        this.snapshotIndex = snapshotIndex;
    }

    public boolean isSuccess() {
        return isSuccess;
    }

    public byte[] getImageBytes() {
        return imageBytes;
    }

    public long getSnapshotIndex() {
        return snapshotIndex;
    }
}
