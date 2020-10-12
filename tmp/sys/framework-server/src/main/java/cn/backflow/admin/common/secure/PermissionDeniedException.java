package cn.backflow.admin.common.secure;

public class PermissionDeniedException extends RuntimeException {
    public PermissionDeniedException(String msg) {
        super(msg);
    }
}
