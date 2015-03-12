package joaorodrigues.mobileimgur.model;

import java.util.List;

/**
 * Created by joao on 12-03-2015.
 */
public class RetrofitResponse {

    List<Image> data;
    String success;
    int status;

    public List<Image> getData() {
        return data;
    }

    public void setData(List<Image> data) {
        this.data = data;
    }

    public String getSuccess() {
        return success;
    }

    public void setSuccess(String success) {
        this.success = success;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }
}
