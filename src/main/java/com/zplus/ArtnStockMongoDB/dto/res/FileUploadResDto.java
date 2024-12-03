package com.zplus.ArtnStockMongoDB.dto.res;


public class FileUploadResDto {

    private String path;

    private Boolean status;

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }
}
