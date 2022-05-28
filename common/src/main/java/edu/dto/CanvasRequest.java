package edu.dto;

/**
 * request for getting canvas from server
 * @author lingxiao
 */
public class CanvasRequest {

    //the current snapshot
    long snapshotIndex;

    public CanvasRequest(long snapshotIndex) {
        this.snapshotIndex = snapshotIndex;
    }

    public long getSnapshotIndex() {
        return snapshotIndex;
    }
}
