package edu.dto;

public class CanvasRequest {

    long snapshotIndex;

    public CanvasRequest(long snapshotIndex) {
        this.snapshotIndex = snapshotIndex;
    }

    public long getSnapshotIndex() {
        return snapshotIndex;
    }
}
